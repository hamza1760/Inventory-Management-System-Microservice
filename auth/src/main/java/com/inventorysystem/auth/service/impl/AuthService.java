package com.inventorysystem.auth.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.externalservice.IUserService;
import com.inventorysystem.auth.model.request.LoginRequest;
import com.inventorysystem.auth.model.response.LoginResponse;
import com.inventorysystem.auth.service.IAuthService;
import com.inventorysystem.auth.service.IRealmService;
import com.inventorysystem.common.customexception.ClientIntegrationException;
import com.inventorysystem.common.customexception.InvalidCredentialsException;
import com.inventorysystem.common.dto.TokenDto;
import com.inventorysystem.common.exceptions.BusinessException;
import com.inventorysystem.common.utilities.Constants;
import com.inventorysystem.common.utilities.Utils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService implements IAuthService {

    @Autowired
    private IRealmService realmService;

    @Autowired
    private IUserService userService;
    @Autowired
    private Utils commonUtils;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername();
            log.info("Getting AuthDetail for username : {}", username);
            String realmName = Constants.REALM_NAME;
            LoginResponse loginResponse = new LoginResponse();
            TokenDto token = realmService.getToken(loginRequest);
            loginResponse.setToken(token.getAccessToken());
            loginResponse.setRefreshToken(token.getRefreshToken());
            loginResponse.setExpiresIn(token.getExpiresIn());
            loginResponse.setRefreshExpiresIn(token.getRefreshExpiresIn());
            UserRepresentation user = realmService.getUserAttributes(username, realmName);
//            Map<String, List<String>> userAttributes = user.getAttributes();

            loginResponse.setUserType(realmService.getUserRole(user.getId(), realmName));

            return loginResponse;


        } catch (ClientIntegrationException e) {
            log.error(e.getMessage());
            throw new InvalidCredentialsException("Incorrect Credentials: Please check your username and password");
        }

    }


    private Date getTokenExpiry(String token) {
        log.debug("Getting token expiry for token: {}", token);
        Date date;
        String[] jwtParts = token.split("\\.");
        String payload = new String(Base64.decodeBase64(jwtParts[1]));
        try {
            JsonNode node = mapper.readTree(payload);
            date = mapper.convertValue(node.get("exp"), Date.class);
        } catch (JsonProcessingException e) {
            log.error("Token Parsing Exception occurred", e);
            throw new BusinessException("Token Parsing Exception");
        }
        log.debug("Token expiry retrieved for token: {}", token);
        return date;
    }
}