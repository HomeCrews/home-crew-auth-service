package com.homecrew.authservice.service;

import com.homecrew.authservice.dto.RegisterRequest;
import com.homecrew.authservice.dto.RegisterResponse;

public interface AuthService {

  RegisterResponse register(RegisterRequest request);

}
