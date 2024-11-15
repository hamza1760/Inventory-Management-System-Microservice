package com.inventorysystem.user.fallback;


import com.inventorysystem.common.customexception.BusinessException;
import com.inventorysystem.user.externalservice.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthServiceFallback implements FallbackFactory<IAuthService> {
    @Override
    public IAuthService create(Throwable cause) {
        log.error("An exception occurred when calling the AuthService", cause);
        throw new BusinessException("Error while adding user in keycloak");
    }
}
