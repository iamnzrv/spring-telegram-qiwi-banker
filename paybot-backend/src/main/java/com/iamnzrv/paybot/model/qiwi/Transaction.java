package com.iamnzrv.paybot.model.qiwi;

import com.iamnzrv.paybot.model.user.Wallet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transactions")
public @Data class Transaction implements Serializable {
  @Id
  @Column(name = "txn_id", unique = true, nullable = false)
  private Long txnId;
  @Column(name = "person_id", nullable = false)
  private Long personId;
  @Temporal(TemporalType.DATE)
  @Column(name = "date")
  private Date date;
  @Column(name = "error_code")
  private Long errorCode;
  @Column(name = "error")
  private String error;
  @Column(name = "type")
  private String type;
  @Column(name = "status")
  private String status;
  @Column(name = "statusText")
  private String statusText;
  @Column(name = "trm_txn_id")
  private String trmTxnId;
  @Column(name = "account")
  private String account;
  @ManyToOne
  @EqualsAndHashCode.Exclude
  Wallet wallet;
  @OneToOne
  @JoinColumn(name = "sum_id")
  private Sum sum;
  @OneToOne
  @JoinColumn(name = "commission_id")
  private Commission commission;
  @OneToOne
  @JoinColumn(name = "total_id")
  private Total total;
  @OneToOne
  @JoinColumn(name = "id")
  private Provider provider;
  @Column(name = "comment")
  private String comment;
  @Column(name = "currency_rate")
  private Long currencyRate;
  @Column(name = "cheque_ready")
  private Boolean chequeReady;
  @Column(name = "bank_document_available")
  private Boolean bankDocumentAvailable;
  @Column(name = "repeat_payment_enabled")
  private Boolean repeatPaymentEnabled;
  @Column(name = "favorite_payment_enabled")
  private Boolean favoritePaymentEnabled;
  @Column(name = "regular_payment_enabled")
  private Boolean regularPaymentEnabled;
}
