package com.inventorysystem.common.customexception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
    private Object customObject;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object customObject) {
        super(message);
        this.customObject = customObject;
    }
}