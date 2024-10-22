package com.inventorysystem.gateway.config.auth;

import com.inventorysystem.gateway.utilities.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
        return chain.filter(exchange.mutate().request(request).build());
    }
}