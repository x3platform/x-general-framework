package com.x3platform.config;

import com.x3platform.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.x3platform.SpringContext;

import java.net.URL;
import java.net.URLDecoder;

/**
 * 核心的配置信息
 */

@Configuration
public class KernelConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile KernelConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static KernelConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          // instance = new KernelConfigurationView();
          instance = SpringContext.getBean(KernelConfigurationView.class);
        }
      }
    }

    return instance;
  }

  @Autowired
  KernelConfiguration configuration;

  public String getCultureName() {
    return configuration.getCultureName();
  }

  public String getMessageObjectFormatter() {
    return configuration.getMessageObjectFormatter();
  }

  public String getApplicationPathRoot() {
    String path = configuration.getApplicationPathRoot();

    if (path.equals(".")) {
      path = PathUtil.getProgramPath() + PathUtil.getFileSeparator();
    }

    return path;
  }
}
