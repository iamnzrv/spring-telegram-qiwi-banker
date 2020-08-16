package com.iamnzrv.paybot.model.qiwi;

import lombok.Data;

import java.io.Serializable;

public @Data class TransactionList implements Serializable {
  private Transaction[] data;
  private Long nextTxnId;
  private Long nextTxnDate;
}
