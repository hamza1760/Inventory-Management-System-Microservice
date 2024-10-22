package com.inventorysystem.common.exceptions;

public class Exception extends RuntimeException {

    String message;
    String detail;

    public Exception(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }
}
