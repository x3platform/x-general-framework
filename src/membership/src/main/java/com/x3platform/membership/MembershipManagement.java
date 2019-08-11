package com.x3platform.membership;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.configuration.MembershipConfiguration;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.membership.services.AccountLogService;
import com.x3platform.membership.services.AccountService;
import com.x3platform.membership.services.OrganizationService;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.membership.services.RoleService;
import com.x3platform.membership.services.SettingService;
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

  private static Object lockObject = new Object();

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

  private AccountService mAccountService = null;

  /**
   * 帐号服务提供者
   */
  public AccountService getAccountService() {
    return mAccountService;
  }

  private AccountLogService mAccountLogService = null;

  /**
   * 帐号日志服务提供者
   */
  public AccountLogService getAccountLogService() {
    return mAccountLogService;
  }

  private OrganizationService mOrganizationService = null;

  /**
   * 组织服务提供者
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
   * @return 返回信息. =0代表重启成功, >0代表重启失败.
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
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"),
        MembershipConfiguration.APPLICATION_NAME);

      // 重新加载配置信息
      MembershipConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"),
        MembershipConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mSettingService = SpringContext.getBean("com.x3platform.membership.services.SettingService",
      SettingService.class);
    mAccountService = SpringContext.getBean("com.x3platform.membership.services.AccountService",
      AccountService.class);
    mAccountLogService = SpringContext.getBean("com.x3platform.membership.services.AccountLogService",
      AccountLogService.class);
    mRoleService = SpringContext.getBean("com.x3platform.membership.services.RoleService",
      RoleService.class);
    mOrganizationService = SpringContext.getBean("com.x3platform.membership.services.OrganizationService",
      OrganizationService.class);
    mOrganizationUnitService = SpringContext.getBean("com.x3platform.membership.services.OrganizationUnitService",
      OrganizationUnitService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"),
      MembershipConfiguration.APPLICATION_NAME);
  }
}
