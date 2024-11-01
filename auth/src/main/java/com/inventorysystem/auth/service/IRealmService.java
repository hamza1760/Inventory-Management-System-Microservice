package com.inventorysystem.auth.service;


import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.model.request.LoginRequest;
import com.inventorysystem.common.dto.TokenDto;
import org.keycloak.representations.idm.UserRepresentation;

public interface IRealmService {

    void addUser(UserDTO userDTO);
    AuthDetailDto addRealm(String realm);
    TokenDto getToken(LoginRequest loginRequest);
    UserRepresentation getUserAttributes(String email, String realm);
    String getUserRole(String userId, String realm);


}
