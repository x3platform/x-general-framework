package com.x3platform.membership;

import static com.x3platform.membership.configuration.MembershipConfiguration.APPLICATION_NAME;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.configuration.MembershipConfiguration;
import com.x3platform.membership.configuration.MembershipConfigurationView;
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
import com.x3platform.plugins.CustomPlugin;

/**
 * 人员及权限管理
 *
 * @author ruanyu
 */
public class MembershipManagement extends CustomPlugin {

  @Override
  public String getName() {
    return "人员及权限管理";
  }

  private static volatile MembershipManagement sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   */
  public static MembershipManagement getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new MembershipManagement();
        }
      }
    }

    return sInstance;
  }

  private AuthorizationObjectService mAuthorizationObjectService = null;

  /**
   * 授权对象服务提供者
   */
  public AuthorizationObjectService getAuthorizationObjectService() {
    return mAuthorizationObjectService;
  }

  private AccountService mAccountService = null;

  /**
   * 帐号服务提供者
   */
  public AccountService getAccountService() {
    return mAccountService;
  }

  private AccountBindingService mAccountBindingService = null;

  /**
   * 帐号第三方帐号绑定服务提供者
   */
  public AccountBindingService getAccountBindingService() {
    return mAccountBindingService;
  }

  private AccountLogService mAccountLogService = null;

  /**
   * 帐号日志服务提供者
   */
  public AccountLogService getAccountLogService() {
    return mAccountLogService;
  }

  private GroupService mGroupService = null;

  /**
   * 群组服务提供者
   */
  public GroupService getGroupService() {
    return mGroupService;
  }


  private OrganizationService mOrganizationService = null;

  /**
   * 组织单元服务提供者
   */
  public OrganizationService getOrganizationService() {
    return mOrganizationService;
  }

  private OrganizationUnitService mOrganizationUnitService = null;

  /**
   * 组织单元服务提供者
   */
  public OrganizationUnitService getOrganizationUnitService() {
    return mOrganizationUnitService;
  }

  private RoleService mRoleService = null;

  /**
   * 角色服务提供者
   */
  public RoleService getRoleService() {
    return mRoleService;
  }

  private StandardOrganizationUnitService mStandardOrganizationUnitService = null;

  /**
   * 标准组织单元服务提供者
   */
  public StandardOrganizationUnitService getStandardOrganizationUnitService() {
    return mStandardOrganizationUnitService;
  }

  private StandardRoleService mStandardRoleService = null;

  /**
   * 标准角色服务提供者
   */
  public StandardRoleService getStandardRoleService() {
    return mStandardRoleService;
  }

  private UserService mUserService = null;

  /**
   * 用户服务提供者
   */
  public UserService getUserService() {
    return mUserService;
  }

  private SettingService mSettingService = null;

  /**
   * 配置参数服务提供者
   */
  public SettingService getSettingService() {
    return mSettingService;
  }

  private MembershipManagement() {
    restart();
  }

  /**
   * 重启次数计数器
   */
  private int restartCount = 0;

  /**
   * 重启插件
   *
   * @return 消息代码 0-重启成功, 大于0-重启失败.
   */
  @Override
  public int restart() {
    try {
      reload();

      // 自增重启次数计数器
      restartCount++;
    } catch (RuntimeException ex) {
      KernelContext.getLog().error(ex.toString());
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (restartCount > 0) {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"), APPLICATION_NAME);

      // 重新加载配置信息
      MembershipConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"), APPLICATION_NAME);
    }

    // 创建数据服务对象
    mSettingService = SpringContext.getBean("com.x3platform.membership.services.SettingService",
      SettingService.class);

    mAuthorizationObjectService = SpringContext
      .getBean("com.x3platform.membership.services.AuthorizationObjectService",
        AuthorizationObjectService.class);
    mAccountService = SpringContext.getBean("com.x3platform.membership.services.AccountService",
      AccountService.class);
    mAccountBindingService = SpringContext.getBean("com.x3platform.membership.services.AccountBindingService",
      AccountBindingService.class);
    mAccountLogService = SpringContext.getBean("com.x3platform.membership.services.AccountLogService",
      AccountLogService.class);
    mGroupService = SpringContext.getBean("com.x3platform.membership.services.GroupService",
      GroupService.class);
    mOrganizationService = SpringContext.getBean("com.x3platform.membership.services.OrganizationService",
      OrganizationService.class);
    mOrganizationUnitService = SpringContext.getBean("com.x3platform.membership.services.OrganizationUnitService",
      OrganizationUnitService.class);
    mRoleService = SpringContext.getBean("com.x3platform.membership.services.RoleService",
      RoleService.class);
    mStandardOrganizationUnitService = SpringContext
      .getBean("com.x3platform.membership.services.StandardOrganizationUnitService",
        StandardOrganizationUnitService.class);
    mStandardRoleService = SpringContext.getBean("com.x3platform.membership.services.StandardRoleService",
      StandardRoleService.class);
    mUserService = SpringContext.getBean("com.x3platform.membership.services.UserService",
      UserService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
