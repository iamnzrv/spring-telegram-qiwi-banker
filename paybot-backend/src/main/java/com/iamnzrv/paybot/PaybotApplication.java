package com.iamnzrv.paybot;

import com.iamnzrv.paybot.config.DatabaseConfig;
import com.iamnzrv.paybot.config.JwtConfig;
import com.iamnzrv.paybot.config.WalletConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
    {
        DatabaseConfig.class,
        WalletConfig.class,
        JwtConfig.class
    }
)
public class PaybotApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaybotApplication.class, args);
  }
}
