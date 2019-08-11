package com.x3platform.data.storages.configuration;

import com.x3platform.SpringContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 数据存储的配置信息视图
 *
 * @author ruanyu
 */
@Configuration
public class DataStoragesConfigurationView {

  private static volatile DataStoragesConfigurationView sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   *
   * @return {@link DataStoragesConfigurationView} 实例
   */
  public static DataStoragesConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(DataStoragesConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  /**
   * 重新加载
   */
  public void reload() {
    sInstance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.data.storages.configuration.DataStoragesConfiguration")
  DataStoragesConfiguration configuration;

  /**
   * 获取默认选项
   *
   * @return 配置的值
   */
  public String getDefaultOption() {
    return configuration.getDefaultOption();
  }
}
