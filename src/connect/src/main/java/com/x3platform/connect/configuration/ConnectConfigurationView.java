package com.x3platform.connect.configuration;

import com.x3platform.SpringContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 连接器的配置信息视图
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

  public String getApiHostName() {
    return configuration.getApiHostName();
  }

  public int getSessionTimerInterval() {
    return Integer.parseInt(configuration.getSessionTimerInterval());
  }

  public int getSessionTimeLimit() {
    return Integer.parseInt(configuration.getSessionTimeLimit());
  }

  public String getMessageQueueUsername() {
    return configuration.getMessageQueueUsername();
  }

  public int getMessageQueuePassword() {
    return Integer.parseInt(configuration.getMessageQueuePassword());
  }

  public int getMessageQueueReceivingInterval() {
    return Integer.parseInt(configuration.getMessageQueueReceivingInterval());
  }
}
