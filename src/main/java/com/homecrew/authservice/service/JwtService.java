package com.homecrew.authservice.service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.homecrew.authservice.enums.TokenType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  public String secret;

  @Value("${jwt.access-token-expiry}")
  public Duration accessTokenExpiry;

  @Value("${jwt.refresh-token-expiry}")
  public Duration refreshTokenExpiry;

  public String generateToken(String email, TokenType tokenType) {
    Map<String, Object> claims = new HashMap<>();

    return switch (tokenType) {
      case ACCESS_TOKEN -> this.createAccessToken(claims, email);
      case REFRESH_TOKEN -> this.createRefreshToken(claims, email);
    };
  }

  private String createAccessToken(Map<String, Object> claims, String email) {
    return Jwts.builder().claims(claims).subject(email).issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + this.accessTokenExpiry.toMillis()))
        .signWith(this.getSignKey()).compact();
  }

  private String createRefreshToken(Map<String, Object> claims, String email) {
    return Jwts.builder().claims(claims).subject(email).issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + this.refreshTokenExpiry.toMillis()))
        .signWith(this.getSignKey()).compact();
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractSubject(String token) {
    return this.extractClaim(token, claims -> claims.getSubject());
  }

  public Date extractExpiration(String token) {
    return this.extractClaim(token, claims -> claims.getExpiration());
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = this.extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(this.getSignKey()).build().parseSignedClaims(token)
        .getPayload();
  }

  private Boolean isTokenExpired(String token) {
    return this.extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String email = this.extractSubject(token);
    return email.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
  }
}
