package com.iamnzrv.paybot.security.jwt;

import com.iamnzrv.paybot.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtTokenVerifier extends OncePerRequestFilter {

  private final JwtConfig jwtConfig;
  private final JwtProvider jwtProvider;

  public JwtTokenVerifier(JwtConfig jwtConfig,
                          JwtProvider jwtProvider) {
    this.jwtConfig = jwtConfig;
    this.jwtProvider = jwtProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String token = jwtProvider.resolveToken(request);
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }
    validateToken(token);
    Authentication authentication = jwtProvider.getTokenAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

  public void validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(jwtConfig.secretKey()).parseClaimsJws(token);
      if (claims.getBody().getExpiration().before(new Date())) {
        throw new IllegalStateException("Expired token");
      }
    } catch (JwtException | IllegalArgumentException e) {
      throw new IllegalStateException("Invalid token");
    }
  }
}
