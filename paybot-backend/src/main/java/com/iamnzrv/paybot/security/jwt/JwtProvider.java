package com.iamnzrv.paybot.security.jwt;

import com.iamnzrv.paybot.config.JwtConfig;
import com.iamnzrv.paybot.service.ApplicationUserService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtProvider {

  JwtConfig jwtConfig;
  ApplicationUserService applicationUserService;

  public JwtProvider(JwtConfig jwtConfig, ApplicationUserService applicationUserService) {
    this.jwtConfig = jwtConfig;
    this.applicationUserService = applicationUserService;
  }

  public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
    return Jwts.builder()
        .setSubject(username)
        .claim("authorities", authorities)
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
        .signWith(jwtConfig.secretKey())
        .compact();
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(jwtConfig.secretKey()).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    String header = request.getHeader(jwtConfig.getAuthorizationHeader());
    if (header != null && header.startsWith(jwtConfig.getTokenPrefix())) {
      return header.substring(jwtConfig.getTokenPrefix().length());
    }
    return null;
  }

  public String resolveToken(String header) {
    if (header != null && header.startsWith(jwtConfig.getTokenPrefix())) {
      return header.substring(jwtConfig.getTokenPrefix().length());
    }
    return null;
  }

  public Authentication getTokenAuthentication(String token) {
    UserDetails userDetails = applicationUserService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );
  }

  public Authentication getUsernamePasswordAuthentication(String username, String password) {
    return new UsernamePasswordAuthenticationToken(
        username,
        password
    );
  }
}
