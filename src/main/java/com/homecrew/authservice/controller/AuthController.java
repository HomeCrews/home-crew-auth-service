package com.homecrew.authservice.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecrew.authservice.dto.ApiResponse;
import com.homecrew.authservice.dto.LoginRequest;
import com.homecrew.authservice.dto.LoginResponse;
import com.homecrew.authservice.dto.RegisterRequest;
import com.homecrew.authservice.dto.RegisterResponse;
import com.homecrew.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final AuthService authService;

  @GetMapping("/test")
  public String test() {
    return "Auth Service is working";
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<RegisterResponse>> register(
      @RequestBody @Valid RegisterRequest request) {

    RegisterResponse response = this.authService.register(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(HttpStatus.CREATED, "User registered successfully", response));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
      @RequestBody @Valid LoginRequest request) {

    LoginResponse response = this.authService.login(request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(HttpStatus.OK, "User logged in successfully", response));
  }

}
