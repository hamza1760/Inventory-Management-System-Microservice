package com.inventorysystem.auth.controller;

import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.AUTH;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.LOGIN;

import com.inventorysystem.auth.model.request.LoginRequest;
import com.inventorysystem.auth.model.response.LoginResponse;
import com.inventorysystem.auth.service.IAuthService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping(API + AUTH)
public class AuthController {

    @Autowired
    private IAuthService authService;

    /**
     * Summary: Get Auth Token From Keycloak.
     *
     * @param loginRequest loginRequest
     * @return ResponseEntity
     */
    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("Signing-In: {}", loginRequest.getUsername());
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
