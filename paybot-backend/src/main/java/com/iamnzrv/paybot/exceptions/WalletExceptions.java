package com.iamnzrv.paybot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WalletExceptions {
  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Wallet was not found")
  public static class WalletNotFoundException extends RuntimeException {
  }
}
