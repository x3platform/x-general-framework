package com.x3platform.connect.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 连接器的配置信息
 */
@Component("com.x3platform.connect.configuration.ConnectConfiguration")
public class ConnectConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "connect";

  /**
   * 配置区的名称
   */
  // public static final String SECTION_NAME = "connect";

  @Value("${x3platform." + APPLICATION_NAME + ".api-host-name:apis.x3platform.com}")
  private String mApiHostName;

  /**
   * API主机名
   */
  public String getApiHostName() {
    return mApiHostName;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".session-timer-interval:15}")
  private String mSessionTimerInterval;

  /**
   * 会话定时器执行时间间隔(单位:分钟)
   */
  public String getSessionTimerInterval() {
    return mSessionTimerInterval;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".session-time-limit:86400}")
  private String mSessionTimeLimit;

  /**
   * 会话时间限制 (单位:秒)
   */
  public String getSessionTimeLimit() {
    return mSessionTimeLimit;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".enable-call-log:Off}")
  private String mEnableCallLog;

  /**
   * 启用调用日志
   */
  public String getEnableCallLog() {
    return mEnableCallLog;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".message-queue-mode:Off}")
  private String mMessageQueueMode;

  /**
   * 消息队列模式
   */
  public String getMessageQueueMode() {
    return mMessageQueueMode;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".message-queue-host-name:-}")
  private String mMessageQueueHostName;

  /**
   * 消息队列机器名称
   */
  public String getMessageQueueHostName() {
    return mMessageQueueHostName;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".message-queue-port:-}")
  private String mMessageQueuePort;

  /**
   * 消息队列端口
   */
  public String getMessageQueuePort() {
    return mMessageQueuePort;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".message-queue-username:username}")
  private String mMessageQueueUsername;

  /**
   * 消息队列用户名
   */
  public String getMessageQueueUsername() {
    return mMessageQueueUsername;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".message-queue-password:password}")
  private String mMessageQueuePassword;

  /**
   * 消息队列密码
   */
  public String getMessageQueuePassword() {
    return mMessageQueuePassword;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".message-queue-receiving-interval:120}")
  private String mMessageQueueReceivingInterval;

  /**
   * 消息队列接收时间间隔(单位:秒)
   */
  public String getMessageQueueReceivingInterval() {
    return mMessageQueueReceivingInterval;
  }
}
