package com.x3platform.cachebuffer.configuration;

import com.x3platform.InternalLogger;
import com.x3platform.SpringContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 核心的配置信息
 */

@Component
public class CacheBufferConfigurationView {
  private final Logger logger = InternalLogger.getLogger();

  private static volatile CacheBufferConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static CacheBufferConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = SpringContext.getBean(CacheBufferConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void Reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.cachebuffer.configuration.CacheBufferConfiguration")
  CacheBufferConfiguration configuration;

  public String getCacheProvider() {
    return configuration.getCacheProvider();
  }

  public String getCacheDefaultDuration() {
    return configuration.getCacheDefaultDuration();
  }
}
