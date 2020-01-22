package com.x3platform.data;

import com.x3platform.util.StringUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 通用的 SQL 命令
 *
 * @author ruanyu
 */
public class GenericSqlCommand {

  private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

  private static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

  DataSource dataSource;
  /**
   * 数据连接
   */
  Connection connection;

  public GenericSqlCommand(String url) {
    this(DATASOURCE_TYPE_DEFAULT, url);
  }

  public GenericSqlCommand(String type, String url) {
    this(type, null, url, null, null);
  }

  public GenericSqlCommand(String type, String driverClassName, String url, String username, String password) {
    // 获取数据源信息
    Map<String, Object> dsMap = new HashMap<>();

    dsMap.put("type", StringUtil.isNullOrEmpty(type) ? DATASOURCE_TYPE_DEFAULT : type);

    if (!StringUtil.isNullOrEmpty(driverClassName)) {
      dsMap.put("driver-class-name", driverClassName);
    }

    dsMap.put("url", url);

    if (!StringUtil.isNullOrEmpty(username)) {
      dsMap.put("username", username);
    }

    if (!StringUtil.isNullOrEmpty(password)) {
      dsMap.put("password", password);
    }

    dataSource = buildDataSource(dsMap);
  }

  public static GenericSqlCommand create() {
    return create("default");
  }

  public static GenericSqlCommand create(String datasourceName) {
    return null;
  }

  public DataSource buildDataSource(Map<String, Object> map) {
    try {
      Object type = map.get("type");
      if (type == null) {
        // 默认DataSource
        type = DATASOURCE_TYPE_DEFAULT;
      }
      Class<? extends DataSource> dataSourceType;
      dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

      DataSourceBuilder factory = DataSourceBuilder.create().type(dataSourceType);

      if (map.containsKey("driver-class-name")) {
        factory.driverClassName(map.get("driver-class-name").toString());
      }

      if (map.containsKey("url")) {
        factory.url(map.get("url").toString());
      }

      if (map.containsKey("username")) {
        factory.username(map.get("username").toString());
      }

      if (map.containsKey("password")) {
        factory.password(map.get("password").toString());
      }

      return factory.build();
    } catch (ClassNotFoundException ex) {
      logger.error(ex.toString());
    }

    return null;
  }

  /**
   * 打开连接
   */
  public void openConnection() throws SQLException {
    this.connection = this.dataSource.getConnection();
  }

  /**
   * 关闭连接
   */
  public void closeConnection() throws SQLException {
    if (this.connection == null) {
      return;
    }

    if (!this.connection.isClosed()) {
      this.connection.close();
    }
  }

  // -------------------------------------------------------
  // ExecuteNonQuery
  // 执行命令，并返回受影响的行数。
  // -------------------------------------------------------

  public int executeNonQuery(String commandText) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(commandText);
    preparedStatement.execute();
    preparedStatement.close();
    return 0;
  }

  // -------------------------------------------------------
  // ExecuteScalar
  // 执行查询命令，并返回查询所返回的结果集的第一行的第一列。
  // -------------------------------------------------------

  public Object executeScalar(String commandText) throws SQLException {
    Object result = null;

    PreparedStatement preparedStatement = connection.prepareStatement(commandText);

    ResultSet rs = preparedStatement.executeQuery();

    while (rs.next()) {
      result = rs.getObject(1);
      break;
    }

    preparedStatement.close();

    return result;
  }

  // -------------------------------------------------------
  // ExecuteMap
  // 执行查询命令，并返回查询所返回的结果集的第一列消息。
  // -------------------------------------------------------

  public Map executeMap(String commandText) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(commandText);

    ResultSet rs = preparedStatement.executeQuery();

    // 获取键名
    ResultSetMetaData md = rs.getMetaData();
    // 获取行的数量
    int columnCount = md.getColumnCount();
    // 声明 Map
    Map rowData = null;

    while (rs.next()) {
      rowData = new HashMap();
      for (int i = 1; i <= columnCount; i++) {
        // 获取键名及值
        rowData.put(md.getColumnName(i), rs.getObject(i));
      }
      break;
    }

    preparedStatement.close();

    return rowData;
  }

  // -------------------------------------------------------
  // ExecuteList
  // 执行查询命令，并返回查询所返回的结果集。
  // -------------------------------------------------------

  public List executeList(String commandText) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(commandText);

    ResultSet rs = preparedStatement.executeQuery();

    List list = new ArrayList();
    // 获取键名
    ResultSetMetaData md = rs.getMetaData();
    // 获取行的数量
    int columnCount = md.getColumnCount();

    while (rs.next()) {
      // 声明 Map
      Map rowData = new HashMap();
      for (int i = 1; i <= columnCount; i++) {
        //获取键名及值
        rowData.put(md.getColumnName(i), rs.getObject(i));
      }
      list.add(rowData);
    }

    preparedStatement.close();

    return list;
  }
}
