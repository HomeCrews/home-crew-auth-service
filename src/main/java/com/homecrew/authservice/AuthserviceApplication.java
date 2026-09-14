package com.homecrew.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AuthserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthserviceApplication.class, args);
  }
}
