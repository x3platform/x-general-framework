package com.x3platform.sessions.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 会话的配置信息
 */
@Component("com.x3platform.sessions.configuration.SessionsConfiguration")
public class SessionsConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "sessions";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "sessions";

  @Value("${x3platform." + SECTION_NAME + ".session-persistent-mode:off}")
  private String sessionPersistentMode;

  /**
   * 会话持久化模式
   */
  public String getSessionPersistentMode() {
    return sessionPersistentMode;
  }

  @Value("${x3platform." + SECTION_NAME + ".session-timer-interval:15}")
  private String sessionTimerInterval;

  /**
   * 会话定时器执行时间间隔(单位:分钟)
   */
  public String getSessionTimerInterval() {
    return sessionTimerInterval;
  }

  @Value("${x3platform." + SECTION_NAME + ".session-time-limit:3600}")
  private String sessionTimeLimit;

  /**
   * 会话时间限制(单位:分钟)
   */
  public String getSessionTimeLimit() {
    return sessionTimeLimit;
  }
}
