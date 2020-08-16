package com.iamnzrv.paybot.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iamnzrv.paybot.model.qiwi.Transaction;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "wallets")
public class Wallet {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private ApplicationUser user;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  @JoinColumn(name = "wallet_id")
  private Set<Transaction> transactions;

  @Column(name = "money_amount")
  private Long moneyAmount;

  public Wallet(Long moneyAmount) {
    this.moneyAmount = moneyAmount;
    this.transactions = new HashSet<>();
  }

  public Wallet() {
    this.moneyAmount = 0L;
    this.transactions = new HashSet<>();
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }
}
