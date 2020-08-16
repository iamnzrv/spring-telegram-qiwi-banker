package com.iamnzrv.paybot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public @Data
class DatabaseConfig {
  @Bean
  public DataSource getDataSource() {
    final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/paybot_db?useUnicode=true&serverTimezone=UTC";
    final String DB_USER = "root";
    final String DB_PASSWORD = "21RoOm7_";

    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(DB_DRIVER);
    dataSourceBuilder.url(DB_URL);
    dataSourceBuilder.username(DB_USER);
    dataSourceBuilder.password(DB_PASSWORD);
    return dataSourceBuilder.build();
  }
}
