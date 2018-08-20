package com.x3platform.connect;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.connect.configuration.ConnectConfiguration;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 应用连接器管理上下文环境
 */
public class ConnectContext extends CustomPlugin {
  @Override
  public String getName() {
    return "应用连接器管理";
  }

  private static volatile ConnectContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static ConnectContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new ConnectContext();
        }
      }
    }

    return sInstance;
  }

  // private IApplicationService mApplicationService = null;

  /**
   * 应用连接器服务提供者
   */
  // public final IApplicationService getApplicationService() {
  //  return mApplicationService;
  // }

  private ConnectContext() {
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
  // @Override
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
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_reloading"), ConnectConfiguration.ApplicationName));

      // 重新加载配置信息
      // AppsConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_loading"), ConnectConfiguration.ApplicationName));
    }

    // 创建数据服务对象
    // this.mApplicationService = SpringContext.getBean("com.x3platform.apps.services.IApplicationService", IApplicationService.class);

    KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_successfully_loaded"), ConnectConfiguration.ApplicationName));
  }
}
