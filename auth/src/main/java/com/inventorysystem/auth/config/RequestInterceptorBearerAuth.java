package com.inventorysystem.auth.config;


import static com.inventorysystem.common.utilities.Constants.PREFERRED_USERNAME;
import static com.inventorysystem.common.utilities.Constants.USER_ID;

import com.inventorysystem.common.dto.RequestMetaData;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestInterceptorBearerAuth implements RequestInterceptor {

    @Autowired
    RequestMetaData requestMetaData;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(PREFERRED_USERNAME, requestMetaData.getUsername());
        requestTemplate.header(USER_ID, Objects.toString(requestMetaData.getUserId(), null));
    }
}
