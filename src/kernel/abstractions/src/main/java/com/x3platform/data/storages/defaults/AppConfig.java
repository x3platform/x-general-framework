package com.x3platform.data.storages.defaults;

import com.x3platform.data.storages.services.DataStorageNodeService;
import com.x3platform.data.storages.services.DataStorageSchemaService;
import com.x3platform.data.storages.services.impl.DataStorageNodeServiceImpl;
import com.x3platform.data.storages.services.impl.DataStorageSchemaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 应用配置类
 *
 * @author ruanyu
 */
@Configuration("com.x3platform.data.storages.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.data.storages.services.DataStorageSchemaService")
  public DataStorageSchemaService dataStorageSchemaService() {
    return new DataStorageSchemaServiceImpl();
  }

  @Bean("com.x3platform.data.storages.services.DataStorageNodeService")
  public DataStorageNodeService dataStorageNodeService() {
    return new DataStorageNodeServiceImpl();
  }
}
