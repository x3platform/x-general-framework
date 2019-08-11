package com.x3platform.digitalnumber.configuration;

// import com.x3platform.Configuration.*;
// import com.x3platform.Yaml.RepresentationModel.*;
// import com.x3platform.Util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 流水号配置信息
 */
@Configuration
public class DigitalNumberConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "digital-number";

  @Value("${x3platform." + APPLICATION_NAME + ".ignore-increment-seed:Key_32DigitGuid,Key_Guid,Key_Nonce,Key_Random_10,Key_Session}")
  private String ignoreIncrementSeed;

  /**
   * 忽略自增因子的流水号
   */
  public String getIgnoreIncrementSeed() {
    return ignoreIncrementSeed;
  }
}
