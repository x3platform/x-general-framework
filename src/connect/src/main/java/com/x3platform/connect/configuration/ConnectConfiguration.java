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

  @Value("${x3platform." + APPLICATION_NAME + ".session-timer-interval:15}")
  private String sessionTimerInterval;

  /**
   * 会话定时器执行时间间隔(单位:分钟)
   */
  public String getSessionTimerInterval() {
    return sessionTimerInterval;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".session-time-limit:86400}")
  private String sessionTimeLimit;

  /**
   * 会话时间限制 (单位:秒)
   */
  public String getSessionTimeLimit() {
    return sessionTimeLimit;
  }
  
  @Value("${x3platform." + APPLICATION_NAME + ".auth-fail-limit:5}")
  private String authFailLimit;
  
  /**
   * 认证失败最大次数
   */
  public String getAuthFailLimit() {
    return authFailLimit;
  }
  
  @Value("${x3platform." + APPLICATION_NAME + ".auth-fail-duration:5}")
  private String authFailDuration;
  
  /**
   * 认证失败统计持续的时间(单位:分钟)
   */
  public String getAuthFailDuration() {
    return authFailDuration;
  }
  
  @Value("${x3platform." + APPLICATION_NAME + ".enable-captcha:Off}")
  private String enableCaptcha;
  
  /**
   * 启用图形验证码
   */
  public String getEnableCaptcha() {
    return enableCaptcha;
  }
}
