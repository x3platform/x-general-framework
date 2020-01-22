package com.x3platform.security.authority.defaults;

import com.x3platform.digitalnumber.services.*;
import com.x3platform.digitalnumber.services.impl.*;
import com.x3platform.security.authority.services.AuthorityService;
import com.x3platform.security.authority.services.impl.AuthorityServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration("com.x3platform.security.authority.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.security.authority.services.AuthorityService")
  public AuthorityService authorityService() {
    return new AuthorityServiceImpl();
  }
}
