package com.x3platform.apps;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.services.ApplicationFeatureService;
import com.x3platform.apps.services.ApplicationMenuService;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.apps.services.ApplicationSettingService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 应用管理上下文
 */
public class AppsContext extends CustomPlugin {

  @Override
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

  private ApplicationService mApplicationService = null;

  /**
   * 应用服务提供者
   */
  public ApplicationService getApplicationService() {
    return mApplicationService;
  }

  private ApplicationFeatureService mApplicationFeatureService = null;

  /**
   * 应用功能服务提供者
   */
  public ApplicationFeatureService getApplicationFeatureService() {
    return mApplicationFeatureService;
  }

  private ApplicationMenuService mApplicationMenuService = null;

  /**
   * 应用菜单服务提供者
   */
  public ApplicationMenuService getApplicationMenuService() {
    return mApplicationMenuService;
  }

  private ApplicationSettingService mApplicationSettingService = null;

  /**
   * 应用设置服务提供者
   */
  public ApplicationSettingService getApplicationSettingService() {
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
        AppsConfiguration.APPLICATION_NAME);
      // 重新加载配置信息
      AppsConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"),
        AppsConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mApplicationService = SpringContext.getBean("com.x3platform.apps.services.ApplicationService",
      ApplicationService.class);
    mApplicationMenuService = SpringContext.getBean("com.x3platform.apps.services.ApplicationMenuService",
      ApplicationMenuService.class);
    mApplicationSettingService = SpringContext.getBean("com.x3platform.apps.services.ApplicationSettingService",
      ApplicationSettingService.class);
    mApplicationFeatureService = SpringContext.getBean("com.x3platform.apps.services.ApplicationFeatureService",
      ApplicationFeatureService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"),
      AppsConfiguration.APPLICATION_NAME);
  }
}
