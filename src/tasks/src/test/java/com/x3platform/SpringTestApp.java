package com.x3platform.tasks;

import com.x3platform.data.DynamicDataSourceRegister;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = {"com.x3platform"})
@MapperScan({"com.x3platform"})
@Import(DynamicDataSourceRegister.class)
public class SpringTestApp {

  /**
   * 自动识别使用的数据库类型
   * 在 mapper.xml 中设置 databaseId 的值对应这里的配置，
   * 如果没有 databaseId 选择则说明该 sql 适用所有数据库
   */
  @Bean
  public DatabaseIdProvider getDatabaseIdProvider() {
    DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

    Properties properties = new Properties();

    properties.setProperty("MySQL", "mysql");
    properties.setProperty("Oracle", "oracle");

    databaseIdProvider.setProperties(properties);

    return databaseIdProvider;
  }
}
