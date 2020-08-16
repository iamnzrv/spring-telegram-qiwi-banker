package com.iamnzrv.paybot.model.qiwi;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "providers")
public @Data
class Provider implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long dbId;
  @Column(name = "provider_id")
  private Long id;
  @Column(name = "short_name")
  private String shortName;
  @Column(name = "long_name")
  private String longName;
  @Column(name = "logo_url")
  private String logoUrl;
  @Column(name = "description")
  private String description;
  @Column(name = "provider_keys")
  private String keys;
  @Column(name = "site_url")
  private String siteUrl;
}
