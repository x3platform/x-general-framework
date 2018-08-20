package com.amiintellect.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;

public class MyBatisConfig {
  
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
