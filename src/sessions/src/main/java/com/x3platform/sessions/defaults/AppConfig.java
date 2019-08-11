package com.x3platform.sessions.defaults;

import com.x3platform.sessions.services.TicketService;
import com.x3platform.sessions.services.impl.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.sessions.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.sessions.services.TicketService")
  public TicketService accountCacheService() {
    return new TicketServiceImpl();
  }
}
