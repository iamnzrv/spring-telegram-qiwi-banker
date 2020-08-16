package com.iamnzrv.paybot.service.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TaskExecutor {
  @Bean
  public static ThreadPoolTaskExecutor getTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(1);
    threadPoolTaskExecutor.setMaxPoolSize(5);
    return threadPoolTaskExecutor;
  }
}
