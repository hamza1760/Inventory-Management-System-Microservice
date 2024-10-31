package com.inventorysystem.auth.service;


import com.inventorysystem.auth.model.request.LoginRequest;
import com.inventorysystem.auth.model.response.LoginResponse;

public interface IAuthService {

    LoginResponse login(LoginRequest loginRequest);
}
