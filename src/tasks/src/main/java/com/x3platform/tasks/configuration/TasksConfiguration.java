package com.x3platform.tasks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 任务管理的配置信息
 *
 * @author ruanyu
 */
@Component("com.x3platform.tasks.configuration.TasksConfiguration")
public class TasksConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "tasks";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "tasks";

  @Value("${x3platform." + SECTION_NAME + ".notification-options:{}}")
  private String notificationOptions;

  /**
   * 通知选项
   */
  public String getNotificationOptions() {
    return notificationOptions;
  }

  @Value("${x3platform." + SECTION_NAME + ".prefix-target-url:@host}")
  private String prefixTargetUrl;

  /**
   * 任务信息的地址前缀
   */
  public String getPrefixTargetUrl() {
    return prefixTargetUrl;
  }

  @Value("${x3platform." + SECTION_NAME + ".waiting-item-sending-interval:120}")
  private String waitingItemSendingInterval;

  /**
   * 定时待办项发送时间间隔(单位:秒)
   */
  public String getWaitingItemSendingInterval() {
    return waitingItemSendingInterval;
  }

  @Value("${x3platform." + SECTION_NAME + ".client-refresh-interval:120}")
  private String clientRefreshInterval;

  /**
   * 客户端刷新间隔(单位:秒)
   */
  public String getClientRefreshInterval() {
    return clientRefreshInterval;
  }

  @Value("${x3platform." + SECTION_NAME + ".message-queue-mode:no}")
  private String messageQueueMode;

  /**
   * 消息队列模式
   */
  public String getMessageQueueMode() {
    return messageQueueMode;
  }

  @Value("${x3platform." + SECTION_NAME + ".message-queue-host:localhost}")
  private String messageQueueHost;

  /**
   * 消息队列服务器地址
   */
  public String getmessageQueueHost() {
    return messageQueueHost;
  }

  @Value("${x3platform." + SECTION_NAME + ".message-queue-name:tasks-work-item-queue}")
  private String messageQueueName;

  /**
   * 消息队列名称
   */
  public String getMessageQueueName() {
    return messageQueueName;
  }

  @Value("${x3platform." + SECTION_NAME + ".message-queue-receiving-interval:120}")
  private String messageQueueReceivingInterval;

  /**
   * 消息队列接收时间间隔(单位:秒)
   */
  public String getMessageQueueReceivingInterval() {
    return messageQueueReceivingInterval;
  }
}
