package com.x3platform.digitalnumber.defaults;

import com.x3platform.digitalnumber.services.*;
import com.x3platform.digitalnumber.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration("com.x3platform.digitalnumber.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.digitalnumber.services.DigitalNumberService")
  public DigitalNumberService dataStorageSchemaService() {
    return new DigitalNumberServiceImpl();
  }
}
