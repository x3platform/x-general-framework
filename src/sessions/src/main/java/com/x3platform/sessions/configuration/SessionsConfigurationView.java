package com.x3platform.sessions.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 会话的配置信息视图
 */

@Configuration
public class SessionsConfigurationView {

  private static volatile SessionsConfigurationView sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static SessionsConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(SessionsConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  public void reload() {
    sInstance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.sessions.configuration.SessionsConfiguration")
  SessionsConfiguration configuration;

  /**
   * 会话持久化模式
   */
  public boolean getSessionPersistentMode() {
    return BooleanUtil.bool(configuration.getSessionPersistentMode());
  }

  /**
   * 会话定时器执行时间间隔(单位:分钟)
   */
  public int getSessionTimerInterval() {
    return Integer.parseInt(configuration.getSessionTimerInterval());
  }

  /**
   * 会话时间限制(单位:分钟)
   */
  public int getSessionTimeLimit() {
    return Integer.parseInt(configuration.getSessionTimeLimit());
  }
}
