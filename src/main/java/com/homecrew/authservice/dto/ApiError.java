package com.homecrew.authservice.dto;

import lombok.Data;

@Data
public class ApiError {

  private String code;
  private Object details;

  public ApiError(String code, Object details) {
    this.code = code;
    this.details = details;
  }
}
