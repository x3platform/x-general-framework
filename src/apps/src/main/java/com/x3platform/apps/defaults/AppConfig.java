package com.x3platform.apps.defaults;

import com.x3platform.apps.services.*;
import com.x3platform.apps.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.apps.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.apps.services.IApplicationService")
  public IApplicationService iApplicationService() {
    return new ApplicationService();
  }

  @Bean("com.x3platform.apps.services.IApplicationMenuService")
  public IApplicationMenuService iApplicationMenuService() {
    return new ApplicationMenuService();
  }

  @Bean("com.x3platform.apps.services.IApplicationSettingService")
  public IApplicationSettingService iApplicationSettingService() {
    return new ApplicationSettingService();
  }
}
