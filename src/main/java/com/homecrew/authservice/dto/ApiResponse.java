package com.homecrew.authservice.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public final class ApiResponse<T> {

  private int status;
  private boolean success;
  private String message;
  private T data;
  private ApiError error;

  private ApiResponse(int status, boolean success, String message, T data, ApiError error) {
    this.status = status;
    this.success = success;
    this.message = message;
    this.data = data;
    this.error = error;
  }

  public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
    return new ApiResponse<>(status.value(), true, message, data, null);
  }

  public static <T> ApiResponse<T> error(HttpStatus status, String message, ApiError error) {
    return new ApiResponse<>(status.value(), false, message, null, error);
  }

}
