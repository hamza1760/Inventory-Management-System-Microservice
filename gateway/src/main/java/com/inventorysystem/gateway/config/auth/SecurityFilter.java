package com.inventorysystem.gateway.config.auth;

import static com.inventorysystem.common.utilities.Constants.PREFERRED_USERNAME;
import static com.inventorysystem.common.utilities.Constants.REALM_ACCESS;
import static com.inventorysystem.common.utilities.Constants.ROLES;
import static com.inventorysystem.common.utilities.Constants.USER_ID;
import static com.inventorysystem.common.utilities.Constants.USER_ROLE;
import static com.inventorysystem.common.utilities.Constants.USER_TYPE;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inventorysystem.common.enums.RolesEnum;
import com.inventorysystem.gateway.utilities.GatewayUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class SecurityFilter implements WebFilter {

    @Autowired
    private GatewayUtil gatewayUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            //Assuming the request is other than login and forgot password.
            String token = gatewayUtil.getAuthorizationToken(exchange);
            request = getHttpHeaders(exchange, token);
        }

        return chain.filter(exchange.mutate().request(request).build());

    }

    private ServerHttpRequest getHttpHeaders(ServerWebExchange exchange, String token) {

        DecodedJWT jwt = gatewayUtil.getDecodedJWT(token);
        String username = jwt.getClaim(PREFERRED_USERNAME).asString();
        Claim userId = jwt.getClaim(USER_ID);
        Map<String, Object> rolesMap = jwt.getClaim(REALM_ACCESS).asMap();
        List<String> rolesList = (List<String>) rolesMap.get(ROLES);
        String userRole = jwt.getClaim(USER_ROLE).asString();

        return exchange.getRequest().mutate()
            .header(PREFERRED_USERNAME, Objects.toString(username, null))
            .header(USER_ID, Objects.toString(userId, null))
            .header(USER_TYPE, getUserRole(rolesList))
            .header(USER_ROLE, Objects.toString(userRole, null))
            .build();
    }

    private String getUserRole(List<String> roles) {
        return roles.stream()
            .filter(RolesEnum::isExists)
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Keycloak is missing defined userTypes"));
    }

}
