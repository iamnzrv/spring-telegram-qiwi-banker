package com.iamnzrv.paybot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wallet")
public @Data
class WalletConfig {
  private final String number = "Your wallet's number...";
  private final String token = "Your wallet's token...";
}