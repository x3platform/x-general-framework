package com.x3platform.apps.defaults;

import com.x3platform.apps.services.*;
import com.x3platform.apps.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.apps.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.apps.services.ApplicationService")
  public ApplicationService ApplicationService() {
    return new ApplicationServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationMenuService")
  public ApplicationMenuService ApplicationMenuService() {
    return new ApplicationMenuServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationSettingService")
  public ApplicationSettingService ApplicationSettingService() {
    return new ApplicationSettingServiceImpl();
  }
  /**
   * 应用功能管理
   */
  @Bean("com.x3platform.apps.services.ApplicationFeatureService")
  public ApplicationFeatureService ApplicationFeatureService() {
    return new ApplicationFeatureServiceImpl();
  }


}
