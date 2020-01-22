package com.x3platform.security.verificationcode.defaults;

import com.x3platform.digitalnumber.services.*;
import com.x3platform.digitalnumber.services.impl.*;
import com.x3platform.security.authority.services.AuthorityService;
import com.x3platform.security.authority.services.impl.AuthorityServiceImpl;
import com.x3platform.security.verificationcode.services.VerificationCodeService;
import com.x3platform.security.verificationcode.services.impl.VerificationCodeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration("com.x3platform.security.verificationcode.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.security.verificationcode.services.VerificationCodeService")
  public VerificationCodeService verificationCodeService() {
    return new VerificationCodeServiceImpl();
  }
}
