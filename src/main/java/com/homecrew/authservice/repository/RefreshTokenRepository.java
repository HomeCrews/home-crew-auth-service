package com.homecrew.authservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homecrew.authservice.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {


}
