package com.inventorysystem.common.customexception;

public class ClientIntegrationException extends RuntimeException {

    public ClientIntegrationException(String message) {
        super(message);
    }

    public ClientIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
