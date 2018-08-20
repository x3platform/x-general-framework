package com.x3platform.connect.configuration;

import com.x3platform.SpringContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 人员及权限的配置信息视图
 */

@Configuration
public class ConnectConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile ConnectConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static ConnectConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          // instance = new DigitalNumberConfigurationView();
          instance = SpringContext.getBean(ConnectConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.connect.configuration.ConnectConfiguration")
  ConnectConfiguration configuration;

  public String getAdministrators() {
    return configuration.getAdministrators();
  }

  public String getHiddenStartMenu() {
    return configuration.getHiddenStartMenu();
  }

  public int getHiddenTopMenu() {
    return Integer.parseInt(configuration.getHiddenTopMenu());
  }

  public int getHiddenShortcutMenu() {
    return Integer.parseInt(configuration.getHiddenShortcutMenu());
  }
}
