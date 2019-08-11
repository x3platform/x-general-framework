package com.x3platform.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.PathUtil;
import com.x3platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
  private Environment env;

  @Autowired
  private KernelConfiguration configuration;

  public String getSystemName() {
    return configuration.getSystemName();
  }

  public String getCultureName() {
    return configuration.getCultureName();
  }

  public String getHost() {
    String host = configuration.getHost();

    if (host.equals("@host")) {
      host = "http://localhost";
      String port = env.getProperty("server.port");
      if (!StringUtil.isNullOrEmpty(port)) {
        host = host + ":" + port;
      }
    }

    return host;
  }

  public String getFileHost() {
    String host = configuration.getFileHost();

    if (host.equals("@host")) {
      host = getHost();
    }

    return host;
  }

  public String getStaticFileHost() {
    String host = configuration.getStaticFileHost();

    if (host.equals("@host")) {
      host = getHost();
    }

    return host;
  }

  public String getDomain() {
    return configuration.getDomain();
  }

  public String getMessageObjectFormatter() {
    return configuration.getMessageObjectFormatter();
  }

  public String getApplicationPathRoot() {
    String path = configuration.getApplicationPathRoot();

    if (path.equals(".")) {
      path = PathUtil.getProgramPath();
    }

    return path;
  }

  public String getAuthenticationManagementType() {
    return configuration.getAuthenticationManagementType();
  }
}
