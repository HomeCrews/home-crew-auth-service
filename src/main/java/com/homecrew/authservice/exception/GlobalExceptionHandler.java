package com.homecrew.authservice.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homecrew.authservice.dto.ApiError;
import com.homecrew.authservice.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(exception = MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException exception) {

    Map<String, List<String>> errors = new HashMap<>();

    exception.getFieldErrors().forEach(fieldError -> {
      errors.computeIfAbsent(fieldError.getField(), _ -> new ArrayList<String>())
          .add(fieldError.getDefaultMessage());
    });

    ApiError apiError = new ApiError("INVALID_INPUT", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid input", apiError));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<?>> constraintViolationHandler(
      ConstraintViolationException exception) {

    Map<String, List<String>> errors = new HashMap<>();

    exception.getConstraintViolations()
        .forEach(violation -> errors
            .computeIfAbsent(violation.getPropertyPath().toString(), key -> new ArrayList<>())
            .add(violation.getMessage()));

    ApiError apiError = new ApiError("INVALID_INPUT", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid input", apiError));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<?>> userAlreadyExistsExceptionHandler(
      UserAlreadyExistsException exception) {

    ApiError apiError = new ApiError("USER_ALREADY_EXISTS", exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(HttpStatus.CONFLICT, "User already exists", apiError));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> exceptionHandler(Exception exception) {
    ApiError apiError = new ApiError("INTERNAL_SERVER_ERROR", "Something went wrong");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid input", apiError));
  }

}
