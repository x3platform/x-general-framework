package com.x3platform.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 核心的配置信息
 */
@Configuration
public class KernelConfiguration {

  @Value("${x3platform.system-name:x3platform}")
  private String SystemName;

  /***
   * 系统名称
   */
  public String getSystemName() {
    return SystemName;
  }

  @Value("${x3platform.culture-name:zh-cn}")
  private String CultureName;

  /***
   * 默认区域性名称
   */
  public String getCultureName() {
    return CultureName;
  }

  @Value("${x3platform.messages.message-object-formatter:com.x3platform.messages.MessageObjectFormatter}")
  private String MessageObjectFormatter;

  public String getMessageObjectFormatter() {
    return MessageObjectFormatter;
  }

  @Value("${x3platform.application-path-root:.}")
  private String ApplicationPathRoot;

  public String getApplicationPathRoot() {
    return ApplicationPathRoot;
  }

}
