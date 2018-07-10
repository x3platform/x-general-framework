package com.x3platform.cacheBuffer.configuration;

// import com.x3platform.Configuration.*;
// import com.x3platform.Yaml.RepresentationModel.*;
// import com.x3platform.Util.*;

import com.x3platform.SpringContext;
import com.x3platform.util.ApplicationContextUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 核心的配置信息
 */

@Configuration
public class CacheBufferConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile CacheBufferConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static CacheBufferConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          // instance = new CacheBufferConfigurationView();
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
  CacheBufferConfiguration configuration;

  public String getCacheProvider() {
    return configuration.getCacheProvider();
  }

  public String getCacheDefaultDuration() {
    return configuration.getCacheDefaultDuration();
  }
}
