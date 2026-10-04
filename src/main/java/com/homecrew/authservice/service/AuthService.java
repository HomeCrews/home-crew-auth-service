package com.homecrew.authservice.service;

import com.homecrew.authservice.dto.LoginRequest;
import com.homecrew.authservice.dto.LoginResponse;
import com.homecrew.authservice.dto.RegisterRequest;
import com.homecrew.authservice.dto.RegisterResponse;

public interface AuthService {

  RegisterResponse register(RegisterRequest request);

  LoginResponse login(LoginRequest request);

}
