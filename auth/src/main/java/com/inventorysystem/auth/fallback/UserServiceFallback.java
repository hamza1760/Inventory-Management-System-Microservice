package com.inventorysystem.auth.fallback;


import com.inventorysystem.auth.externalservice.IUserService;
import com.inventorysystem.auth.fallback.handler.UserServiceFeignFallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserServiceFallback implements FallbackFactory<IUserService> {

    @Override
    public IUserService create(Throwable cause) {
        return new UserServiceFeignFallbackHandler(cause);
    }
}
