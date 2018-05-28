package com.x3platform.digitalNumber.config;

// import com.x3platform.Configuration.*;
// import com.x3platform.Yaml.RepresentationModel.*;
// import com.x3platform.Util.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 流水号配置信息
 */
@Configuration
public class DigitalNumberConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String ApplicationName = "DigitalNumber";

  /**
   * 配置区的名称
   */
  public static final String SectionName = "digital-number";


  @Value("${x3platform." + SectionName + ".ignore-increment-seed}")
  private String m_IgnoreIncrementSeed;

  public String getIgnoreIncrementSeed() {
    return m_IgnoreIncrementSeed;
  }
}
