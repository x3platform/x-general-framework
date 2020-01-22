package com.x3platform;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.x3platform.data.DynamicDataSourceRegister;
import com.x3platform.membership.defaults.AppConfig;
import java.util.Properties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Spring Boot Application
 */
@SpringBootApplication
@ComponentScan({"com.x3platform.configuration", "com.x3platform"})
@MapperScan({"com.x3platform.*.mappers", "com.x3platform.*.*.mappers", "com.x3platform.*.*.*.mappers"})
@Import({AppConfig.class, DynamicDataSourceRegister.class})
public class SpringTestApp {

  /**
   * 自动识别使用的数据库类型
   * 在 mapper.xml 中设置 databaseId 的值对应这里的配置，
   * 如果没有 databaseId 选择则说明该 sql 适用所有数据库
   */
  @Bean
  public DatabaseIdProvider databaseIdProvider() {
    DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

    Properties properties = new Properties();

    properties.setProperty("MySQL", "mysql");
    properties.setProperty("Oracle", "oracle");
    properties.setProperty("SqlServer", "sqlserver");

    databaseIdProvider.setProperties(properties);

    return databaseIdProvider;
  }
}
