package com.inventorysystem.auth.fallback.handler;


import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.externalservice.IUserService;
import com.inventorysystem.common.customexception.ClientIntegrationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceFeignFallbackHandler implements IUserService {

    private final Throwable cause;

    public UserServiceFeignFallbackHandler(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        throw new ClientIntegrationException(
            "Exception occurred while calling getUserByUsername with username: " + username, cause);
    }


}
