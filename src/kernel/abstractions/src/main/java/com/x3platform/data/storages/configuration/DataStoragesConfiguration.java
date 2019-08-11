package com.x3platform.data.storages.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 数据存储的配置信息
 *
 * @author ruanyu
 */
@Component("com.x3platform.data.storages.configuration.DataStoragesConfiguration")
public class DataStoragesConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "data-storages";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "data.storages";

  @Value("${x3platform." + SECTION_NAME + ".default-option:yes}")
  private String mDefaultOption;

  /**
   * 获取默认选项
   * @return 默认选项
   */
  public String getDefaultOption() {
    return mDefaultOption;
  }
}
