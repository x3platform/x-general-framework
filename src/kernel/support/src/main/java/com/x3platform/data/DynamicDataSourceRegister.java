package com.x3platform.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.bind.Binder;
// import org.springframework.boot.bind.RelaxedDataBinder;
// import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.validation.DataBinder;

/**
 * 动态数据源注册<br/>
 * 启动动态数据源请在启动类中（如SpringBootSampleApplication）
 * 添加 @Import(DynamicDataSourceRegister.class)
 */
public class DynamicDataSourceRegister
  implements ImportBeanDefinitionRegistrar, EnvironmentAware {

  private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

  private ConversionService conversionService = new DefaultConversionService();
  private PropertyValues dataSourcePropertyValues;

  // 如配置文件中未指定数据源类型，使用该默认值
  private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
  // private static final Object DATASOURCE_TYPE_DEFAULT =
  // "com.zaxxer.hikari.HikariDataSource";

  // 数据源
  private DataSource defaultDataSource;
  private Map<String, DataSource> customDataSources = new HashMap<>();

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    // 将主数据源添加到更多数据源中
    targetDataSources.put("dataSource", defaultDataSource);
    DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
    // 添加更多数据源
    targetDataSources.putAll(customDataSources);
    for (String key : customDataSources.keySet()) {
      DynamicDataSourceContextHolder.dataSourceIds.add(key);
    }

    // 创建DynamicDataSource
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(DynamicDataSource.class);
    beanDefinition.setSynthetic(true);
    MutablePropertyValues mpv = beanDefinition.getPropertyValues();
    mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
    mpv.addPropertyValue("targetDataSources", targetDataSources);
    registry.registerBeanDefinition("dataSource", beanDefinition);

    logger.info("Dynamic DataSource Registry");
  }

  /**
   * 创建DataSource
   *
   * @param type
   * @param driverClassName
   * @param url
   * @param username
   * @param password
   * @return
   * @author SHANHY
   * @create 2016年1月24日
   */
  @SuppressWarnings("unchecked")
  public DataSource buildDataSource(Map<String, Object> map) {
    try {
      Object type = map.get("type");
      if (type == null)
        type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

      Class<? extends DataSource> dataSourceType;
      dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

      // String driverClassName = map.get("driver-class-name").toString();
      // String url = map.get("url").toString();
      // String username = map.get("username").toString();
      // String password = map.get("password").toString();

      DataSourceBuilder factory = DataSourceBuilder.create().type(dataSourceType);

      if(map.containsKey("driver-class-name")) {
        factory.driverClassName(map.get("driver-class-name").toString());
      }

      if(map.containsKey("url")) {
        factory.url(map.get("url").toString());
      }

      if(map.containsKey("username")) {
        factory.username(map.get("username").toString());
      }

      if(map.containsKey("password")) {
        factory.password(map.get("password").toString());
      }

      //factory.username(username);
      //factory.password(password);

      return factory.build();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
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
    Map<String, Object> dsMap = Binder.get(env).bind("spring.datasource", Bindable.mapOf(String.class, Object.class)).get();

    defaultDataSource = buildDataSource(dsMap);

    dataBinder(defaultDataSource, env);
  }

  /**
   * 为DataSource绑定更多数据
   *
   * @param dataSource
   * @param env
   */
  private void dataBinder(DataSource dataSource, Environment env) {
    /*
    RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
    //dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
    dataBinder.setConversionService(conversionService);
    dataBinder.setIgnoreNestedProperties(false);//false
    dataBinder.setIgnoreInvalidFields(false);//false
    dataBinder.setIgnoreUnknownFields(true);//true
    if(dataSourcePropertyValues == null){
      Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
      Map<String, Object> values = new HashMap<>(rpr);
      // 排除已经设置的属性
      values.remove("type");
      values.remove("driver-class-name");
      values.remove("url");
      values.remove("username");
      values.remove("password");
      dataSourcePropertyValues = new MutablePropertyValues(values);
    }
    dataBinder.bind(dataSourcePropertyValues);
    */
    DataBinder dataBinder = new DataBinder(dataSource);

    dataBinder.setConversionService(conversionService);
    // dataBinder.setIgnoreNestedProperties(false);//false
    dataBinder.setIgnoreInvalidFields(false); //false
    dataBinder.setIgnoreUnknownFields(true); //true
    if (dataSourcePropertyValues == null) {
      // Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
      Map<String, Object> rpr = Binder.get(env).bind("spring.datasource", Bindable.mapOf(String.class, Object.class)).get();
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
    // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
    /*
    RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
    String dsPrefixs = propertyResolver.getProperty("names");
    for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
      Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
      DataSource ds = buildDataSource(dsMap);
      customDataSources.put(dsPrefix, ds);
      dataBinder(ds, env);
    }
    */
    Map<String, Object> datasources = Binder.get(env).bind("spring.datasource", Bindable.mapOf(String.class, Object.class)).get();

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

    for (String dsPrefix : dsPrefixs) {
      // 多个数据源
      // Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
      Map<String, Object> dsMap = Binder.get(env).bind("spring.datasource." + dsPrefix, Bindable.mapOf(String.class, Object.class)).get();
      DataSource ds = buildDataSource(dsMap);
      customDataSources.put(dsPrefix, ds);
      dataBinder(ds, env);
    }
  }

}
