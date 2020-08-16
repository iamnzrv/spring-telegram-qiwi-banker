package com.iamnzrv.paybot.security.permissions;

public enum ApplicationUserPermission {
  USER_WRITE("user:write");

  private final String permission;

  ApplicationUserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}