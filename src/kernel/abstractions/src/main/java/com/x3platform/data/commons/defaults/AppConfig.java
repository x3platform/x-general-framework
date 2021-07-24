package com.x3platform.data.commons.defaults;

import com.x3platform.data.commons.services.DataDictService;
import com.x3platform.data.commons.services.DataSourceService;
import com.x3platform.data.commons.services.DataStorageNodeService;
import com.x3platform.data.commons.services.DataStorageSchemaService;
import com.x3platform.data.commons.services.impl.DataDictServiceImpl;
import com.x3platform.data.commons.services.impl.DataSourceServiceImpl;
import com.x3platform.data.commons.services.impl.DataStorageNodeServiceImpl;
import com.x3platform.data.commons.services.impl.DataStorageSchemaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 应用配置类
 *
 * @author ruanyu
 */
@Configuration("com.x3platform.data.commons.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.data.commons.services.DataSourceService")
  public DataSourceService dataSourceService() {
    return new DataSourceServiceImpl();
  }

  @Bean("com.x3platform.data.commons.services.DataDictService")
  public DataDictService dataDictService() {
    return new DataDictServiceImpl();
  }

  @Bean("com.x3platform.data.commons.services.DataStorageSchemaService")
  public DataStorageSchemaService dataStorageSchemaService() {
    return new DataStorageSchemaServiceImpl();
  }

  @Bean("com.x3platform.data.commons.services.DataStorageNodeService")
  public DataStorageNodeService dataStorageNodeService() {
    return new DataStorageNodeServiceImpl();
  }
}
