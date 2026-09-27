package com.homecrew.authservice.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.homecrew.authservice.dto.RegisterRequest;
import com.homecrew.authservice.dto.RegisterResponse;
import com.homecrew.authservice.entity.Account;
import com.homecrew.authservice.exception.UserAlreadyExistsException;
import com.homecrew.authservice.repository.AccountRepository;
import com.homecrew.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;

  private final AccountRepository accountRepository;

  @Override
  public RegisterResponse register(RegisterRequest request) {
    boolean emailExists = this.accountRepository.existsByEmail(request.getEmail());

    if (emailExists) {
      throw new UserAlreadyExistsException("User already exists by email");
    }

    boolean phoneExists = this.accountRepository.existsByPhone(request.getPhone());

    if (phoneExists) {
      throw new UserAlreadyExistsException("User already exists by phone");
    }

    String passwordHash = this.passwordEncoder.encode(request.getPassword());

    Account account = Account.builder().email(request.getEmail()).passwordHash(passwordHash)
        .phone(request.getPhone()).build();

    account = this.accountRepository.save(account);

    // TODO: create user (microservice call)

    // TODO: handler user creation failure

    return RegisterResponse.builder().id(account.getId()).build();
  }

}
