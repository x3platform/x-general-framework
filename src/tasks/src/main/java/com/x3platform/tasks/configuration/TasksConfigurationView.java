package com.x3platform.tasks.configuration;

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
public class TasksConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile TasksConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static TasksConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          // instance = new DigitalNumberConfigurationView();
          instance = SpringContext.getBean(TasksConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.tasks.configuration.TasksConfiguration")
  TasksConfiguration configuration;

  public String getPasswordEncryption() {
    return configuration.getPasswordEncryption();
  }


  public String getPasswordPolicyRules() {
    return configuration.getPasswordPolicyRules();
  }

  public int getPasswordPolicyCharacterRepeatedTimes() {
    return Integer.parseInt(configuration.getPasswordPolicyCharacterRepeatedTimes());
  }

  public int getPasswordPolicyMinimumLength() {
    return Integer.parseInt(configuration.getPasswordPolicyMinimumLength());
  }

  public String getAvatarVirtualFolder() {
    return configuration.getAvatarVirtualFolder();
  }
}
