package com.inventorysystem.user.utilities;

import com.inventorysystem.user.dto.UserDTO;
import com.inventorysystem.user.entity.User;
import com.inventorysystem.user.externalservice.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceUtils {

    @Autowired
    private IAuthService authService;


    public void addRealmUserInKeycloak(UserDTO userDto) {
        authService.addRealmUser(userDto);
    }
}
