package com.inventorysystem.auth.utilities;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUtil {

    @Value("${keycloak.auth-server-url}")
    public String serverUrl;

    @Value("${keycloak.realm}")
    public String realm;

    @Value("${keycloak.resource}")
    public String clientId;

    @Value("${kc.admin.password}")
    private String adminPassword;

    @Value("${kc.admin.user}")
    private String adminUser;

    private Keycloak instance = null;

    public KeycloakUtil() {
        // default constructor
    }

    /**
     * Summary: Get Keycloak Singleton instance.
     *
     * @return Keycloak
     */
    public Keycloak getInstance() {
        if (instance == null) {
            instance = keycloak();
        }
        return instance;
    }

    private Keycloak keycloak() {
        return KeycloakBuilder.builder().serverUrl(serverUrl).realm(realm).username(adminUser).password(adminPassword)
            .clientId(clientId).build();
    }
}
