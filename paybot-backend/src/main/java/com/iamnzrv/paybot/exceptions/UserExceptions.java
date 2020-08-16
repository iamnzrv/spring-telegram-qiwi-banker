package com.iamnzrv.paybot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserExceptions {
  @ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
  public static class UserAlreadyExistsException extends RuntimeException {
  }

  @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Provided data is invalid")
  public static class UserDataIsInvalidException extends RuntimeException {
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User was not found")
  public static class UserNotFoundException extends RuntimeException {
  }
}