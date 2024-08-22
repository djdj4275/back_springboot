package com.example.ex31_jpa_qnaboard_rest_api_security.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.ex31_jpa_qnaboard_rest_api_security.user.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {
  private static final String SECRET_KEY = "05EuiznZ/kRKSNe1MEVEm4sSsKBAyxTc+yVWzobvHeBYKdQdZu+Jfy5pdOXJnQJql4JNMdWZs5O4423puaALb8QKzv0cgv+GXpU9S7BXq0A=";

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String create(UserEntity userEntity) {
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

    return Jwts.builder()
            .setSubject(String.valueOf(userEntity.getId()))
            .setIssuedAt(new Date()) // 발행날짜
            .setExpiration(expiryDate) // 유효기간
            .signWith(getSigningKey(), SignatureAlgorithm.HS512) //
            .compact(); 
  }

  public String validateAndGetUserId(String token) {
    Claims claims = Jwts.parserBuilder()
                      .setSigningKey(getSigningKey())
                      .build()
                      .parseClaimsJws(token)
                      .getBody();

    return claims.getSubject();
  }
}
