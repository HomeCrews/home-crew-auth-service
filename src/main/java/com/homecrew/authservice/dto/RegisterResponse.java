package com.homecrew.authservice.dto;


import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {

  private UUID id;

}
