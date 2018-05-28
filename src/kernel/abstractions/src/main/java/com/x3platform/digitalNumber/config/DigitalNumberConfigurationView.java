package com.x3platform.digitalNumber.config;

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
public class DigitalNumberConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile DigitalNumberConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static DigitalNumberConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          // instance = new DigitalNumberConfigurationView();
          instance = SpringContext.getBean(DigitalNumberConfigurationView.class);
        }
      }
    }

    return instance;
  }

  @Autowired
  DigitalNumberConfiguration configuration;

  public String getIgnoreIncrementSeed() {
    return configuration.getIgnoreIncrementSeed();
  }
}
