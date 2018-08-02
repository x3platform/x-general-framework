package com.x3platform.tasks.defaults;

import com.x3platform.tasks.services.*;
import com.x3platform.tasks.services.impl.*;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration("com.x3platform.tasks.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.tasks.services.ITaskWorkItemService")
  public ITaskWorkItemService iApplicationService() {
    return new TaskWorkItemService();
  }
}
