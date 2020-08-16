package com.iamnzrv.paybot.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public @Data
class JwtConfig {
  private final String secretKey = "helloworldhelloworldhelloworldhelloworldhelloworld";
  private final String tokenPrefix = "Bearer ";
  private final Integer tokenExpirationAfterDays = 10;

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }

  public SecretKey secretKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}
