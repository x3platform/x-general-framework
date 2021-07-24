package com.x3platform.program.config;

import com.github.pagehelper.PageInterceptor;
import com.x3platform.data.DynamicRoutingDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

  @Autowired
  @Qualifier("dataSource")
  private DynamicRoutingDataSource dynamicRoutingDataSource;

  /**
   * Dynamic DataSource.
   *
   * @return the data source
   */
  @Bean("dynamicDataSource")
  public DataSource dynamicDataSource() {
    // 设置某个数据源作为默认指定的数据源
    // dynamicRoutingDataSource.setDefaultTargetDataSource(defaultDataSource());

    return dynamicRoutingDataSource;
  }

  @Bean
  public SqlSessionFactoryBean sqlSessionFactoryBean() {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    // 设置支持动态数据源
    bean.setDataSource(dynamicDataSource());
    // 设置支持多种数据库
    bean.setDatabaseIdProvider(databaseIdProvider());

    // 分页插件
    PageInterceptor interceptor = new PageInterceptor();

    Properties props = new Properties();
    props.setProperty("reasonable", "true");
    props.setProperty("supportMethodsArguments", "true");
    props.setProperty("returnPageInfo", "check");
    props.setProperty("params", "count=countSql");
    props.setProperty("autoRuntimeDialect", "true");

    interceptor.setProperties(props);

    // 添加插件
    bean.setPlugins(new Interceptor[]{interceptor});

    return bean;
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
  }

  @Bean
  @Override
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    return new DataSourceTransactionManager(dynamicDataSource());
  }

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
