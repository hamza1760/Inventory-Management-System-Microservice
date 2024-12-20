package com.inventorysystem.auth.externalservice;





import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.USER;

import com.inventorysystem.auth.config.RequestInterceptorBearerAuth;
import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mg-user", configuration = RequestInterceptorBearerAuth.class, fallbackFactory = UserServiceFallback.class)
public interface IUserService {

    /**
     * User Service.
     */
    @GetMapping(API + USER + "/by-username/{username}")
    UserDTO getUserByUsername(@PathVariable String username);

}
