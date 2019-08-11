package com.x3platform.connect.defaults;

import com.x3platform.connect.services.*;
import com.x3platform.connect.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.connect.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.connect.services.ConnectService")
  public ConnectService connectService() {
    return new ConnectServiceImpl();
  }

  @Bean("com.x3platform.connect.services.ConnectAccessTokenService")
  public ConnectAccessTokenService connectAccessTokenService() {
    return new ConnectAccessTokenServiceImpl();
  }

  @Bean("com.x3platform.connect.services.ConnectAuthorizationCodeService")
  public ConnectAuthorizationCodeService connectAuthorizationCodeService() {
    return new ConnectAuthorizationCodeServiceImpl();
  }

  @Bean("com.x3platform.connect.services.ConnectCallService")
  public ConnectCallService connectCallService() {
    return new ConnectCallServiceImpl();
  }

}
