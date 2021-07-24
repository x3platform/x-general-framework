package com.x3platform.connect.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
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

  /**
   * 会话定时器执行时间间隔(单位:分钟)
   */
  public int getSessionTimerInterval() {
    return Integer.parseInt(configuration.getSessionTimerInterval());
  }
  
  /**
   * 会话时间限制 (单位:秒)
   */
  public int getSessionTimeLimit() {
    return Integer.parseInt(configuration.getSessionTimeLimit());
  }

  /**
   * 认证失败最大次数
   */
  public int getAuthFailLimit() {
    return Integer.parseInt(configuration.getAuthFailLimit());
  }
  
  /**
   * 认证失败统计持续的时间(单位:分钟)
   */
  public int getAuthFailDuration() {
    return Integer.parseInt(configuration.getAuthFailDuration());
  }
  
  /**
   * 启用图形验证码
   */
  public boolean getEnableCaptcha() {
    return BooleanUtil.bool(configuration.getEnableCaptcha());
  }
}
