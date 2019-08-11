package com.x3platform.apps.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 人员及权限的配置信息视图
 */

@Configuration
public class AppsConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile AppsConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static AppsConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = SpringContext.getBean(AppsConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.apps.configuration.AppsConfiguration")
  AppsConfiguration configuration;

  public String getAdministrators() {
    return configuration.getAdministrators();
  }

  public boolean getHiddenStartMenu() {
    return BooleanUtil.bool(configuration.getHiddenStartMenu());
  }

  public boolean getHiddenTopMenu() {
    return BooleanUtil.bool(configuration.getHiddenTopMenu());
  }

  public boolean getHiddenShortcutMenu() {
    return BooleanUtil.bool(configuration.getHiddenShortcutMenu());
  }
}
