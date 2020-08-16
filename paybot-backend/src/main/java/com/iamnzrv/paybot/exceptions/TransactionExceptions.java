package com.iamnzrv.paybot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TransactionExceptions {
  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Transaction was not found")
  public static class TransactionNotFoundException extends RuntimeException {
  }
}
