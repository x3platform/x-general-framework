package com.x3platform.membership.defaults;

import com.x3platform.KernelContext;
import com.x3platform.membership.services.*;
import com.x3platform.membership.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.membership.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.membership.services.IOrganizationUnitService")
  public IOrganizationUnitService iApplicationService() {
    return new OrganizationUnitService();
  }

  @Bean("com.x3platform.membership.services.IRoleService")
  public IRoleService iRoleService() {
    return new RoleService();
  }

  @Bean("com.x3platform.membership.services.IAccountService")
  public IAccountService iAccountService() {
    return new AccountService();
  }
}
