package com.inventorysystem.auth.service.impl;

import static com.inventorysystem.common.utilities.Constants.ADMIN;
import static com.inventorysystem.common.utilities.Constants.ALGO_RS256;
import static com.inventorysystem.common.utilities.Constants.CLIENT_SECRET;
import static com.inventorysystem.common.utilities.Constants.INVENT0RY_CLIENT;
import static com.inventorysystem.common.utilities.Constants.OFFLINE_ACCESS;
import static com.inventorysystem.common.utilities.Constants.PUBLIC_KEY;
import static com.inventorysystem.common.utilities.Constants.REALM_NAME;
import static com.inventorysystem.common.utilities.Constants.UMA_AUTHORIZATION;
import static com.inventorysystem.common.utilities.Constants.USER_ROLE;

import com.inventorysystem.auth.dto.AuthDetailDto;
import com.inventorysystem.auth.dto.UserDTO;
import com.inventorysystem.auth.model.request.LoginRequest;
import com.inventorysystem.auth.service.IRealmService;
import com.inventorysystem.auth.utilities.KeycloakUtil;
import com.inventorysystem.common.customexception.InvalidCredentialsException;
import com.inventorysystem.common.dto.TokenDto;
import com.inventorysystem.common.enums.RolesEnum;
import com.inventorysystem.common.exceptions.BusinessException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.KeysMetadataRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RealmService implements IRealmService {

    @Autowired
    KeycloakUtil keycloakUtil;

    @Value("${kc.inventory-client}")
    public String inventoryClient;

    @Override
    public void addUser(UserDTO userDTO) {
        String email = userDTO.getEmail();

        CredentialRepresentation credential = new CredentialRepresentation();

        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCredentials(Collections.singletonList(credential));
        user.setEmailVerified(true);
        user.setEnabled(true);

        Map<String, List<String>> attributes = new HashMap<>();

        attributes.put(USER_ROLE, Arrays.asList(RolesEnum.Admin.name()).stream()
            .collect(Collectors.toList()));
        user.setAttributes(attributes);

        Keycloak keycloak = keycloakUtil.getInstance();
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        UsersResource usersResource = realmResource.users();

        String userId = null;
        try (Response response = usersResource.create(user)) {
            if (response.getStatus() == HttpStatus.CREATED.value()) {
                // UserId sample: 3ac374e2-0e1b-4ed3-b80f-e593affc37ef"
                userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                log.info("User Created with Id {}", userId);
                RolesResource roles = realmResource.roles();
                log.debug("Roles in Keycloak realm {} : {}", REALM_NAME, roles.list());
                RoleResource roleResource = roles.get(RolesEnum.Admin.name());
                List<RoleRepresentation> rolesToAdd = Arrays.asList(roleResource.toRepresentation());
                UserResource createdUserResource = usersResource.get(userId);
                createdUserResource.roles().realmLevel().add(rolesToAdd);
            } else if (response.getStatus() == HttpStatus.CONFLICT.value()) {
                log.error("CONFLICT in creating user with email[{}] username[{}] firstname[{}] lastname[{}] emailVerified[{}] enabled[{}] {}",
                    user.getEmail(), user.getUsername(), user.getFirstName(), user.getLastName(), user.isEmailVerified(), user.isEnabled(),
                    user.getAttributes().keySet().stream().map(a -> a + user.getAttributes().get(a)).collect(Collectors.joining(" ")));
                throw new BusinessException("User Not Created!");
            } else {
                throw new BusinessException("User Not Created!");
            }
        } catch (javax.ws.rs.NotFoundException e) {
            log.error("Either role {} OR newly created user with id {} not found in KeyCloak", RolesEnum.Admin.name(), userId);
            throw new BusinessException("User Not Created!", e.getCause());
        } catch (Exception e) {
            log.error("Unexpected happened while creating user in KeyCloak", e);
            throw new BusinessException("User Not Created!", e.getCause());
        }

    }

    @Override
    public AuthDetailDto addRealm(String realm) {
        log.info("Adding realm: {}", realm);
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setId(realm);
        realmRepresentation.setRealm(realm);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setAccessTokenLifespan(7200);
        Keycloak keycloak = keycloakUtil.getInstance();
        keycloak.realms().create(realmRepresentation);

        AuthDetailDto authDetailDto = new AuthDetailDto();
        authDetailDto.setRealm(realm);
        // Add Realm Role
        addRealmRoles(realm);
        Map<String, String> keys = addRealmClient(realm);
        authDetailDto.setClientSecret(keys.get(CLIENT_SECRET));
        authDetailDto.setPublicKey(keys.get(PUBLIC_KEY));
        return authDetailDto;
    }
    private void addRealmRoles(String realmName) {
        log.info("Adding realm roles for company: {}", realmName);
        RealmResource realmResource = getRealm(realmName);
        RolesResource rolesResource = realmResource.roles();
        // delete default realm roles
        rolesResource.deleteRole(OFFLINE_ACCESS);
        rolesResource.deleteRole(UMA_AUTHORIZATION);
        // add admin role in realm
        RoleRepresentation spRoleRepresentation = new RoleRepresentation();
        spRoleRepresentation.setName(ADMIN);
        rolesResource.create(spRoleRepresentation);

    }

    private RealmResource getRealm(String realm) {
        log.info("Getting realm: {}", realm);
        Keycloak keycloak = keycloakUtil.getInstance();
        RealmsResource realmsResource = keycloak.realms();
        return realmsResource.realm(realm);
    }

    private Map<String, String> addRealmClient(String realm) {
        log.info("Adding realm client for realm: {}", realm);
        Map<String, String> keys = new HashMap<>();
        RealmResource realmResource = getRealm(realm);
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(INVENT0RY_CLIENT);
        clientRepresentation.setName(INVENT0RY_CLIENT);
        clientRepresentation.setDirectAccessGrantsEnabled(true);
//        clientRepresentation.setProtocolMappers(tokenProtocolMappers(realm));

        ClientsResource clientsResource = realmResource.clients();
        clientsResource.create(clientRepresentation);
        List<ClientRepresentation> clients = clientsResource.findByClientId(clientRepresentation.getClientId());
        if (!clients.isEmpty()) {
            keys.put(CLIENT_SECRET, clients.get(0).getSecret());
        }
        List<KeysMetadataRepresentation.KeyMetadataRepresentation> keysRepresentation = realmResource.keys().getKeyMetadata().getKeys().stream()
            .filter(key -> key.getAlgorithm().equals(ALGO_RS256)).toList();
        if (!keysRepresentation.isEmpty()) {
            keys.put(PUBLIC_KEY, keysRepresentation.get(0).getPublicKey());
        }
        return keys;
    }
    @Override
    public TokenDto getToken(LoginRequest loginRequest, AuthDetailDto authDetailDto) {
        log.info("Getting token for user: {}", loginRequest.getUsername());
        TokenDto token;
        String username = loginRequest.getUsername();
        String realmName = authDetailDto.getRealm();
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(keycloakUtil.serverUrl).realm(realmName)
            .grantType(OAuth2Constants.PASSWORD).clientId(inventoryClient).clientSecret(authDetailDto.getClientSecret())
            .username(username).password(loginRequest.getPassword()).build();
        try {
            TokenManager tokenManager = keycloak.tokenManager();
            AccessTokenResponse accessToken = tokenManager.getAccessToken();
            String accessTokenString = accessToken.getToken();
            token = TokenDto.builder().accessToken(accessTokenString)
                .refreshToken(accessToken.getRefreshToken())
                .expiresIn(accessToken.getExpiresIn())
                .refreshExpiresIn(accessToken.getRefreshExpiresIn()).build();
        } catch (javax.ws.rs.NotFoundException ex) {
            log.error("KeyCloak not authorized for user: {}", loginRequest.getUsername());
            throw new InvalidCredentialsException("Incorrect Credentials: Please check your username and password");
        } catch (javax.ws.rs.NotAuthorizedException ex) {
            log.error("KeyCloak not authorized for user: {}", loginRequest.getUsername());
            throw new InvalidCredentialsException("Incorrect Credentials: Please check your username and password");
        }
        return token;
    }
    @Override
    public UserRepresentation getUserAttributes(String email, String realm) {
        log.info("Getting attributes for user: {}", email);
        Keycloak keycloak = keycloakUtil.getInstance();
        RealmResource realmResource = keycloak.realm(realm);
        // Get realm users
        UsersResource usersResource = realmResource.users();
        // Get user
        UserRepresentation user = usersResource.searchByEmail(email, true).get(0);
        if (user == null) {
            log.error("Error while getting attributes for user: {}", email, "User Not Found!");
            throw new BusinessException("User Not Found!");
        }
        return user;
    }
}
