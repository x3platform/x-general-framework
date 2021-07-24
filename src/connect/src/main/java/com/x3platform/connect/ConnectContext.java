package com.x3platform.connect;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.connect.configuration.ConnectConfiguration;
import com.x3platform.connect.configuration.ConnectConfigurationView;
import com.x3platform.connect.services.ConnectAccessTokenService;
import com.x3platform.connect.services.ConnectAuthorizationCodeService;
import com.x3platform.connect.services.ConnectCallService;
import com.x3platform.connect.services.ConnectService;
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

  private ConnectService mConnectService = null;

  /**
   * 应用连接器服务提供
   */
  public ConnectService getConnectService() {
    return mConnectService;
  }

  private ConnectAuthorizationCodeService mConnectAuthorizationCodeService = null;

  /**
   * 应用连接授权码服务提供
   */
  public ConnectAuthorizationCodeService getConnectAuthorizationCodeService() {
    return mConnectAuthorizationCodeService;
  }

  private ConnectAccessTokenService mConnectAccessTokenService = null;

  /**
   * 应用连接授权码服务提供者
   */
  public ConnectAccessTokenService getConnectAccessTokenService() {
    return mConnectAccessTokenService;
  }

  private ConnectCallService mConnectCallService = null;

  /**
   * 应用连接调用服务提供者
   */
  public ConnectCallService getConnectCallService() {
    return mConnectCallService;
  }

  private ConnectContext() {
    restart();
  }

  /**
   * 重启次数计数
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
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"),
        ConnectConfiguration.APPLICATION_NAME);
      // 重新加载配置信息
      ConnectConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"),
        ConnectConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mConnectService = SpringContext.getBean("com.x3platform.connect.services.ConnectService",
      ConnectService.class);
    mConnectAuthorizationCodeService = SpringContext.getBean(
      "com.x3platform.connect.services.ConnectAuthorizationCodeService",
      ConnectAuthorizationCodeService.class);
    mConnectAccessTokenService = SpringContext.getBean("com.x3platform.connect.services.ConnectAccessTokenService",
      ConnectAccessTokenService.class);
    mConnectCallService = SpringContext.getBean("com.x3platform.connect.services.ConnectCallService",
      ConnectCallService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"),
      ConnectConfiguration.APPLICATION_NAME);
  }
}
