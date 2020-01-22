package com.x3platform.membership.defaults;

import com.x3platform.membership.services.AccountBindingService;
import com.x3platform.membership.services.AccountLogService;
import com.x3platform.membership.services.AccountService;
import com.x3platform.membership.services.AuthorizationObjectService;
import com.x3platform.membership.services.GroupService;
import com.x3platform.membership.services.OrganizationService;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.membership.services.RoleService;
import com.x3platform.membership.services.SettingService;
import com.x3platform.membership.services.StandardOrganizationUnitService;
import com.x3platform.membership.services.StandardRoleService;
import com.x3platform.membership.services.UserService;
import com.x3platform.membership.services.impl.AccountBindingServiceImpl;
import com.x3platform.membership.services.impl.AccountLogServiceImpl;
import com.x3platform.membership.services.impl.AccountServiceImpl;
import com.x3platform.membership.services.impl.AuthorizationObjectServiceImpl;
import com.x3platform.membership.services.impl.GroupServiceImpl;
import com.x3platform.membership.services.impl.OrganizationServiceImpl;
import com.x3platform.membership.services.impl.OrganizationUnitServiceImpl;
import com.x3platform.membership.services.impl.RoleServiceImpl;
import com.x3platform.membership.services.impl.SettingServiceImpl;
import com.x3platform.membership.services.impl.StandardOrganizationUnitServiceImpl;
import com.x3platform.membership.services.impl.StandardRoleServiceImpl;
import com.x3platform.membership.services.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 应用配置类
 *
 * @author ruanyu
 */
@Configuration("com.x3platform.membership.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.membership.services.SettingService")
  public SettingService settingService() {
    return new SettingServiceImpl();
  }

  @Bean("com.x3platform.membership.services.AuthorizationObjectService")
  public AuthorizationObjectService authorizationObjectService() {
    return new AuthorizationObjectServiceImpl();
  }

  @Bean("com.x3platform.membership.services.AccountService")
  public AccountService accountService() {
    return new AccountServiceImpl();
  }

  @Bean("com.x3platform.membership.services.AccountBindingService")
  public AccountBindingService accountBindingService() {
    return new AccountBindingServiceImpl();
  }

  @Bean("com.x3platform.membership.services.AccountLogService")
  public AccountLogService accountLogService() {
    return new AccountLogServiceImpl();
  }

  @Bean("com.x3platform.membership.services.GroupService")
  public GroupService groupService() {
    return new GroupServiceImpl();
  }

  @Bean("com.x3platform.membership.services.OrganizationService")
  public OrganizationService organizationService() {
    return new OrganizationServiceImpl();
  }

  @Bean("com.x3platform.membership.services.OrganizationUnitService")
  public OrganizationUnitService organizationUnitService() {
    return new OrganizationUnitServiceImpl();
  }

  @Bean("com.x3platform.membership.services.RoleService")
  public RoleService roleService() {
    return new RoleServiceImpl();
  }

  @Bean("com.x3platform.membership.services.StandardOrganizationUnitService")
  public StandardOrganizationUnitService standardOrganizationUnitService() {
    return new StandardOrganizationUnitServiceImpl();
  }

  @Bean("com.x3platform.membership.services.StandardRoleService")
  public StandardRoleService standardRoleService() {
    return new StandardRoleServiceImpl();
  }

  @Bean("com.x3platform.membership.services.UserService")
  public UserService userService() {
    return new UserServiceImpl();
  }
}
