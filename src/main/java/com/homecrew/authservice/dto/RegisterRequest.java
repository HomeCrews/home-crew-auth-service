package com.homecrew.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class RegisterRequest {

  @NotNull
  @Email
  private String email;

  @NotNull
  @Pattern(regexp = "^\\d{10}$")
  private String phone;

  @NotNull
  private String password;

}
