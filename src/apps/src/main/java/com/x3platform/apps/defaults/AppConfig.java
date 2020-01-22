package com.x3platform.apps.defaults;

import com.x3platform.apps.services.*;
import com.x3platform.apps.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ruanyu
 */
@Configuration("com.x3platform.apps.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.apps.services.ApplicationService")
  public ApplicationService applicationService() {
    return new ApplicationServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationEventService")
  public ApplicationEventService applicationEventService() {
    return new ApplicationEventServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationFeatureService")
  public ApplicationFeatureService applicationFeatureService() {
    return new ApplicationFeatureServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationMenuService")
  public ApplicationMenuService applicationMenuService() {
    return new ApplicationMenuServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationMethodService")
  public ApplicationMethodService applicationMethodService() {
    return new ApplicationMethodServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationOptionService")
  public ApplicationOptionService applicationOptionService() {
    return new ApplicationOptionServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationSettingGroupService")
  public ApplicationSettingGroupService applicationSettingGroupService() {
    return new ApplicationSettingGroupServiceImpl();
  }

  @Bean("com.x3platform.apps.services.ApplicationSettingService")
  public ApplicationSettingService applicationSettingService() {
    return new ApplicationSettingServiceImpl();
  }
}
