package com.x3platform.tasks.configuration;

import com.x3platform.SpringContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 任务管理的配置信息视图
 *
 * @author ruanyu
 */
@Configuration
public class TasksConfigurationView {
  private static volatile TasksConfigurationView sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static TasksConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(TasksConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  public void reload() {
    sInstance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.tasks.configuration.TasksConfiguration")
  TasksConfiguration configuration;

  /**
   * 通知选项
   */
  public String getNotificationOptions() {
    return configuration.getNotificationOptions();
  }

  /**
   * 任务信息的地址前缀
   */
  public String getPrefixTargetUrl() {
    return configuration.getPrefixTargetUrl();
  }

  /**
   * 定时待办项发送时间间隔(单位:秒)
   */
  public int getWaitingItemSendingInterval() {
    return Integer.parseInt(configuration.getWaitingItemSendingInterval());
  }

  /**
   * 客户端刷新间隔(单位:秒)
   */
  public int getClientRefreshInterval() {
    return Integer.parseInt(configuration.getClientRefreshInterval());
  }

  /**
   * 消息队列模式
   */
  public String getMessageQueueMode() {
    return configuration.getMessageQueueMode();
  }

  /**
   * 消息队列服务器地址
   */
  public String getmessageQueueHost() {
    return configuration.getmessageQueueHost();
  }

  /**
   * 消息队列名称
   */
  public String getMessageQueueName() {
    return configuration.getMessageQueueName();
  }

  /**
   * 消息队列接收时间间隔(单位:秒)
   */
  public String getMessageQueueReceivingInterval() {
    return configuration.getMessageQueueReceivingInterval();
  }
}
