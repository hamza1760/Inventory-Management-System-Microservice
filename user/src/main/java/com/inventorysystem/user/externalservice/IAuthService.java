package com.inventorysystem.user.externalservice;


import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.REALM;

import com.inventorysystem.user.dto.UserDTO;
import com.inventorysystem.user.fallback.AuthServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", fallbackFactory = AuthServiceFallback.class)
public interface IAuthService {

    @PostMapping(API + REALM)
    void addRealmUser(@RequestBody UserDTO userDto);

}
