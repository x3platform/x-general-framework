package com.x3platform.apps;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.services.IApplicationMenuService;
import com.x3platform.apps.services.IApplicationService;
import com.x3platform.apps.services.IApplicationSettingService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 应用管理上下文
 */
public class AppsContext extends CustomPlugin {
  // @Override
  public String getName() {
    return "应用管理";
  }

  private static volatile AppsContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static AppsContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new AppsContext();
        }
      }
    }

    return sInstance;
  }

  private IApplicationService mApplicationService = null;

  /**
   * 应用服务提供者
   */
  public final IApplicationService getApplicationService() {
    return mApplicationService;
  }

  private IApplicationMenuService mApplicationMenuService = null;

  /**
   * 应用菜单服务提供者
   */
  public final IApplicationMenuService getApplicationMenuService() {
    return mApplicationMenuService;
  }

  private IApplicationSettingService mApplicationSettingService = null;

  /**
   * 应用设置服务提供者
   */
  public final IApplicationSettingService getApplicationSettingService() {
    return mApplicationSettingService;
  }

  private AppsContext() {
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
      this.reload();

      // 自增重启次数计数器
      this.restartCount++;
    } catch (RuntimeException ex) {
      KernelContext.getLog().error(ex.toString());
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (this.restartCount > 0) {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_reloading"), AppsConfiguration.ApplicationName));

      // 重新加载配置信息
      AppsConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_loading"), AppsConfiguration.ApplicationName));
    }

    // 创建数据服务对象
    this.mApplicationService = SpringContext.getBean("com.x3platform.apps.services.IApplicationService", IApplicationService.class);
    this.mApplicationMenuService = SpringContext.getBean("com.x3platform.apps.services.IApplicationMenuService", IApplicationMenuService.class);
    this.mApplicationSettingService = SpringContext.getBean("com.x3platform.apps.services.IApplicationSettingService", IApplicationSettingService.class);

    KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_successfully_loaded"), AppsConfiguration.ApplicationName));
  }
}
