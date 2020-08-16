package com.iamnzrv.paybot.security.jwt;

import lombok.Data;

public @Data
class UsernameAndPasswordAuthenticationRequest {
  private String username;
  private String password;
}
