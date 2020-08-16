package com.iamnzrv.paybot.model.qiwi;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sums")
public @Data
class Sum implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;
  @Column(name = "amount")
  private Long amount;
  @Column(name = "currency")
  private String currency;
}
