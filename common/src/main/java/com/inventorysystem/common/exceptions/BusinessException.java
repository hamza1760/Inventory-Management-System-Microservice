package com.inventorysystem.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private Object customObject;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object customObject) {
        super(message);
        this.customObject = customObject;
    }
}
