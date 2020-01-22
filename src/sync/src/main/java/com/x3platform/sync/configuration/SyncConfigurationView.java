package com.x3platform.sync.configuration;

import com.x3platform.SpringContext;
import com.x3platform.configuration.KernelConfigurationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 同步管理的配置信息视图
 *
 * @author ruanyu
 */
@Configuration
public class SyncConfigurationView {

  private static volatile SyncConfigurationView sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static SyncConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(SyncConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  public void reload() {
    sInstance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.sync.configuration.SyncConfiguration")
  SyncConfiguration configuration;

  /**
   * 数据存储目录
   */
  public String getDataFolder() {
    if (configuration.getDataFolder().startsWith("/")) {
      return configuration.getDataFolder();
    } else {
      return KernelConfigurationView.getInstance().getApplicationPathRoot() + configuration.getDataFolder();
    }
  }

  /**
   * 通用应用标识
   */
  public String getGeneralApplicationId() {
    return configuration.getGeneralApplicationId();
  }

  /**
   * 接收消息队列名称
   */
  public String getReceiveQueueName() {
    return configuration.getReceiveQueueName();
  }

  /**
   * 接收消息队列接收时间间隔(单位:秒)
   */
  public String getReceiveQueueReceivingInterval() {
    return configuration.getReceiveQueueReceivingInterval();
  }
}
