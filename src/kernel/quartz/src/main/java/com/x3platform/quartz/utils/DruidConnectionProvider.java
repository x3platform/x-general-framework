package com.x3platform.quartz.utils;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.quartz.SchedulerException;
import org.quartz.utils.ConnectionProvider;

/**
 * Druid 连接池的 Quartz 扩展类
 *
 * @author ruanyu
 */
public class DruidConnectionProvider implements ConnectionProvider {

  /*
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *
   * 常量配置，与 quartz.properties 文件的 key 保持一致(去掉前缀)，同时提供 set 方法，Quartz 框架自动注入值。
   *
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   */

  // JDBC驱动
  public String driver;
  //JDBC连接串
  public String URL;
  //数据库用户名
  public String user;
  //数据库用户密码
  public String password;
  // 数据库最大连接数
  public int maxConnections;
  //数据库SQL查询每次连接返回执行到连接池，以确保它仍然是有效的。
  public String validationQuery;
  private boolean validateOnCheckout;
  private int idleConnectionValidationSeconds;
  public String maxCachedStatementsPerConnection;
  private String discardIdleConnectionsSeconds;
  public static final int DEFAULT_DB_MAX_CONNECTIONS = 10;
  public static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;
  //Druid连接池
  private DruidDataSource datasource;

  /*
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *
   * 接口实现
   *
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   */

  @Override
  public Connection getConnection() throws SQLException {
    return datasource.getConnection();
  }

  @Override
  public void shutdown() throws SQLException {
    datasource.close();
  }

  @Override
  public void initialize() throws SQLException {
    if (URL == null) {
      throw new SQLException("DBPool could not be created: DB URL cannot be null");
    }
    if (driver == null) {
      throw new SQLException("DBPool driver could not be created: DB driver class name cannot be null!");
    }
    if (maxConnections < 0) {
      throw new SQLException("DBPool maxConnectins could not be created: Max connections must be greater than zero!");
    }
    datasource = new DruidDataSource();
    try {
      datasource.setDriverClassName(driver);
    } catch (Exception e) {
      try {
        throw new SchedulerException("Problem setting driver class name on datasource: " + e.getMessage(), e);
      } catch (SchedulerException e1) {
      }
    }

    datasource.setUrl(URL);
    datasource.setUsername(user);
    datasource.setPassword(password);
    datasource.setMaxActive(maxConnections);
    datasource.setMinIdle(1);
    datasource.setMaxWait(0);
    datasource.setMaxPoolPreparedStatementPerConnectionSize(DEFAULT_DB_MAX_CONNECTIONS);
    if (validationQuery != null) {
      datasource.setValidationQuery(validationQuery);
      if (!validateOnCheckout) {
        datasource.setTestOnReturn(true);
      } else {
        datasource.setTestOnBorrow(true);
      }
      datasource.setValidationQueryTimeout(idleConnectionValidationSeconds);
    }
  }

  /*
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *
   * 提供 get set方法
   *
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   */

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getMaxConnections() {
    return maxConnections;
  }

  public void setMaxConnections(int maxConnections) {
    this.maxConnections = maxConnections;
  }

  public String getValidationQuery() {
    return validationQuery;
  }

  public void setValidationQuery(String validationQuery) {
    this.validationQuery = validationQuery;
  }

  public boolean isValidateOnCheckout() {
    return validateOnCheckout;
  }

  public void setValidateOnCheckout(boolean validateOnCheckout) {
    this.validateOnCheckout = validateOnCheckout;
  }

  public int getIdleConnectionValidationSeconds() {
    return idleConnectionValidationSeconds;
  }

  public void setIdleConnectionValidationSeconds(int idleConnectionValidationSeconds) {
    this.idleConnectionValidationSeconds = idleConnectionValidationSeconds;
  }

  public DruidDataSource getDatasource() {
    return datasource;
  }

  public void setDatasource(DruidDataSource datasource) {
    this.datasource = datasource;
  }

  public String getDiscardIdleConnectionsSeconds() {
    return discardIdleConnectionsSeconds;
  }

  public void setDiscardIdleConnectionsSeconds(String discardIdleConnectionsSeconds) {
    this.discardIdleConnectionsSeconds = discardIdleConnectionsSeconds;
  }
}
