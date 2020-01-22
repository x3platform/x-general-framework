package com.x3platform.data;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.x3platform.InternalLogger;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.validation.DataBinder;

// import org.springframework.boot.bind.RelaxedDataBinder;
// import org.springframework.boot.bind.RelaxedPropertyResolver;

/**
 * 动态数据源注册 启动动态数据源请在启动类中（如SpringBootSampleApplication） 添加 @Import(DynamicDataSourceRegister.class)
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

  private static final Logger logger = InternalLogger.getLogger();

  /**
   * 如配置文件中未指定数据源类型，使用该默认值
   */
  private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

  private static final String DATASOURCE_PROPERTY_NAME = "spring.datasource";

  private static final String DRUIDDATASOURCE_PROPERTY_NAME = "spring.datasource.druid";

  private static final String DRUIDDATASOURCE_KEY_INITIAL_SIZE = "initial-size";
  private static final String DRUIDDATASOURCE_KEY_MIN_IDLE = "min-idle";
  private static final String DRUIDDATASOURCE_KEY_MAX_ACTIVE = "max-active";
  private static final String DRUIDDATASOURCE_KEY_MAX_WAIT = "max-wait";
  private static final String DRUIDDATASOURCE_KEY_POOL_PREPARED_STATEMENTS = "pool-prepared-statements";
  private static final String DRUIDDATASOURCE_KEY_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = "max-pool-prepared-statement-per-connection-size";
  private static final String DRUIDDATASOURCE_KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS = "time-between-eviction-runs-millis";
  private static final String DRUIDDATASOURCE_KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS = "min-evictable-idle-time-millis";
  private static final String DRUIDDATASOURCE_KEY_KEEP_ALIVE = "keep-alive";
  private static final String DRUIDDATASOURCE_KEY_REMOVE_ABANDONED = "remove-abandoned";
  private static final String DRUIDDATASOURCE_KEY_REMOVE_ABANDONED_TIMEOUT = "remove-abandoned-timeout";
  private static final String DRUIDDATASOURCE_KEY_LOG_ABANDONED = "log-abandoned";
  private static final String DRUIDDATASOURCE_KEY_VALIDATION_QUERY = "validation-query";
  private static final String DRUIDDATASOURCE_KEY_TEST_WHILE_IDLE = "test-while-idle";
  private static final String DRUIDDATASOURCE_KEY_TEST_ON_BORROW = "test-on-borrow";
  private static final String DRUIDDATASOURCE_KEY_TEST_ON_RETURN = "test-on-return";
  private static final String DRUIDDATASOURCE_KEY_FILTERS = "filters";
  private static final String DRUIDDATASOURCE_KEY_CONNECTION_PROPERTIES = "connection-properties";

  private ConversionService conversionService = new DefaultConversionService();
  private PropertyValues dataSourcePropertyValues;

  // 数据源
  private DataSource defaultDataSource;
  private Map<String, DataSource> customDataSources = new HashMap<>();

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    // 将主数据源添加到更多数据源中
    targetDataSources.put("dataSource", defaultDataSource);
    DynamicDataSourceContextHolder.dataSourceKeys.add("dataSource");
    // 添加更多数据源
    targetDataSources.putAll(customDataSources);
    for (String key : customDataSources.keySet()) {
      DynamicDataSourceContextHolder.dataSourceKeys.add(key);
    }

    // 创建 DynamicRoutingDataSource
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(DynamicRoutingDataSource.class);
    beanDefinition.setSynthetic(true);
    MutablePropertyValues mpv = beanDefinition.getPropertyValues();
    mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
    mpv.addPropertyValue("targetDataSources", targetDataSources);
    registry.registerBeanDefinition("dataSource", beanDefinition);

    logger.info("Dynamic DataSource Registry");
  }

  /**
   * 创建 DataSource
   *
   * @param map 数据源参数, 可选的键值: type | driverClassName | url | username | password
   * @return 返回 {@link DataSource} 对象
   */
  @SuppressWarnings("unchecked")
  public DataSource buildDataSource(Map<String, Object> map) {
    try {
      Object type = map.get("type");

      if (type == null) {
        // 默认 DataSource 类型
        type = DATASOURCE_TYPE_DEFAULT;
      }

      Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

      DataSourceBuilder factory = DataSourceBuilder.create().type(dataSourceType);
      // 驱动类名称
      if (map.containsKey("driver-class-name")) {
        factory.driverClassName(map.get("driver-class-name").toString());
      }
      // 数据库连接
      if (map.containsKey("url")) {
        factory.url(map.get("url").toString());
      }

      if (map.containsKey("username")) {
        factory.username(map.get("username").toString());
      }

      if (map.containsKey("password")) {
        factory.password(map.get("password").toString());
      }

      DataSource dataSource = factory.build();

      // 对 DruidDataSource 数据源参数优化
      // 具体参考 https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
      if (dataSource.getClass().equals(DruidDataSource.class)) {
        try {
          DruidDataSource druidDataSource = (DruidDataSource) dataSource;

          if (map.containsKey(DRUIDDATASOURCE_KEY_INITIAL_SIZE)) {
            druidDataSource.setInitialSize(Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_INITIAL_SIZE).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_MIN_IDLE)) {
            druidDataSource.setMinIdle(Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_MIN_IDLE).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_MAX_ACTIVE)) {
            druidDataSource.setMaxActive(Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_MAX_ACTIVE).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_MAX_WAIT)) {
            druidDataSource.setMaxWait(Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_MAX_WAIT).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_POOL_PREPARED_STATEMENTS)) {
            druidDataSource.setPoolPreparedStatements(
              Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_POOL_PREPARED_STATEMENTS).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE)) {
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
              Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS)) {
            druidDataSource.setTimeBetweenEvictionRunsMillis(
              Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS)) {
            druidDataSource.setMinEvictableIdleTimeMillis(
              Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_KEEP_ALIVE)) {
            druidDataSource.setKeepAlive(Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_KEEP_ALIVE).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_REMOVE_ABANDONED)) {
            druidDataSource.setRemoveAbandoned(
              Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_REMOVE_ABANDONED).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_REMOVE_ABANDONED_TIMEOUT)) {
            druidDataSource.setRemoveAbandonedTimeout(
              Integer.valueOf(map.get(DRUIDDATASOURCE_KEY_REMOVE_ABANDONED_TIMEOUT).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_LOG_ABANDONED)) {
            druidDataSource.setLogAbandoned(
              Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_LOG_ABANDONED).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_VALIDATION_QUERY)) {
            druidDataSource.setValidationQuery(map.get(DRUIDDATASOURCE_KEY_VALIDATION_QUERY).toString());
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_TEST_WHILE_IDLE)) {
            druidDataSource.setTestWhileIdle(
              Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_TEST_WHILE_IDLE).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_TEST_ON_BORROW)) {
            druidDataSource.setTestOnBorrow(
              Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_TEST_ON_BORROW).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_TEST_ON_RETURN)) {
            druidDataSource.setTestOnReturn(
              Boolean.valueOf(map.get(DRUIDDATASOURCE_KEY_TEST_ON_RETURN).toString()));
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_FILTERS)) {
            druidDataSource.setFilters(map.get(DRUIDDATASOURCE_KEY_FILTERS).toString());
          }
          if (map.containsKey(DRUIDDATASOURCE_KEY_CONNECTION_PROPERTIES)) {
            Properties properties = new Properties();
            properties
              .load(new ByteArrayInputStream(map.get(DRUIDDATASOURCE_KEY_CONNECTION_PROPERTIES).toString().getBytes()));

            druidDataSource.setConnectProperties(properties);
          }
        } catch (SQLException | IOException e) {
          logger.error("DruidDataSource setProperties", e);
        }
      }

      return dataSource;
    } catch (ClassNotFoundException ex) {
      logger.error("DataSource build", ex);
    }

    return null;
  }

  /**
   * 加载多数据源配置
   */
  @Override
  public void setEnvironment(Environment env) {
    initDefaultDataSource(env);
    initCustomDataSources(env);
  }

  /**
   * 初始化主数据源
   */
  private void initDefaultDataSource(Environment env) {
    // 读取主数据源
    Map<String, Object> dsMap = Binder.get(env).bind(DATASOURCE_PROPERTY_NAME,
      Bindable.mapOf(String.class, Object.class)).get();

    // 读取 Druid 配置信息
    if (Binder.get(env).bind(DRUIDDATASOURCE_PROPERTY_NAME, Bindable.mapOf(String.class, Object.class)).isBound()) {
      Map<String, Object> druidMap = Binder.get(env).bind(DRUIDDATASOURCE_PROPERTY_NAME,
        Bindable.mapOf(String.class, Object.class)).get();

      dsMap.putAll(druidMap);
    }

    defaultDataSource = buildDataSource(dsMap);

    dataBinder(defaultDataSource, env);
  }

  /**
   * 为 DataSource 绑定更多数据
   */
  private void dataBinder(DataSource dataSource, Environment env) {
    DataBinder dataBinder = new DataBinder(dataSource);

    dataBinder.setConversionService(conversionService);
    //  忽略嵌套的属性
    /// dataBinder.setIgnoreNestedProperties(false);
    // 忽略错误的字段
    dataBinder.setIgnoreInvalidFields(false);
    // 忽略未知的字段
    dataBinder.setIgnoreUnknownFields(true);

    if (dataSourcePropertyValues == null) {
      Map<String, Object> rpr = Binder.get(env).bind(DATASOURCE_PROPERTY_NAME,
        Bindable.mapOf(String.class, Object.class)).get();

      Map<String, Object> values = new HashMap<>(rpr);
      // 排除已经设置的属性
      values.remove("type");
      values.remove("driver-class-name");
      values.remove("url");
      values.remove("username");
      values.remove("password");

      values.remove("initial-size");
      values.remove("min-idle");
      values.remove("max-idle");
      values.remove("max-active");
      values.remove("test-on-borrow");
      values.remove("validation-query");
      values.remove("validation-interval");

      dataSourcePropertyValues = new MutablePropertyValues(values);
    }

    dataBinder.bind(dataSourcePropertyValues);
  }

  /**
   * 初始化更多数据源
   */
  private void initCustomDataSources(Environment env) {
    // 读取配置文件获取更多数据源，也可以通过 defaultDataSource 读取数据库获取更多数据源
    Map<String, Object> datasources = Binder.get(env).bind(DATASOURCE_PROPERTY_NAME,
      Bindable.mapOf(String.class, Object.class)).get();

    Set<String> dsPrefixs = datasources.keySet();
    // 移除默认属性
    dsPrefixs.remove("type");
    dsPrefixs.remove("driver-class-name");
    dsPrefixs.remove("url");
    dsPrefixs.remove("username");
    dsPrefixs.remove("password");

    dsPrefixs.remove("initial-size");
    dsPrefixs.remove("min-idle");
    dsPrefixs.remove("max-idle");
    dsPrefixs.remove("max-active");
    dsPrefixs.remove("test-on-borrow");
    dsPrefixs.remove("validation-query");
    dsPrefixs.remove("validation-interval");
    dsPrefixs.remove("druid");

    for (String dsPrefix : dsPrefixs) {
      // 多个数据源
      Map<String, Object> dsMap = Binder.get(env).bind("spring.datasource." + dsPrefix,
        Bindable.mapOf(String.class, Object.class)).get();

      // 读取 Druid 配置信息
      if (Binder.get(env).bind(DRUIDDATASOURCE_PROPERTY_NAME, Bindable.mapOf(String.class, Object.class)).isBound()) {
        Map<String, Object> druidMap = Binder.get(env).bind(DRUIDDATASOURCE_PROPERTY_NAME,
          Bindable.mapOf(String.class, Object.class)).get();

        // 以 Druid 默认参数配置为主 Map 合并自定义数据源和 Druid 默认参数配置
        druidMap.putAll(dsMap);
        dsMap = druidMap;
      }

      DataSource ds = buildDataSource(dsMap);
      customDataSources.put(dsPrefix, ds);
      dataBinder(ds, env);
    }
  }
}
