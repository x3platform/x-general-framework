package com.x3platform.data;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenericSqlCommandTests {

  @Autowired
  Environment env;

  // private static String url = "jdbc:mysql://localhost:3306/x3_base?user=root&password=root&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true";

  // 需要测试时，取消注释 @Ignore 注解，然后手动测试。
  @Ignore
  @Test
  public void testCreateConnection() throws SQLException {
    String url = env.getProperty("spring.datasource.url");

    GenericSqlCommand command = new GenericSqlCommand(
      "com.alibaba.druid.pool.DruidDataSource",
      "com.mysql.jdbc.Driver",
      "jdbc:mysql://mysql.dev.x3platform.com:3306/x3_base?useUnicode=true&characterEncoding=utf-8&useSSL=false",
      "[USER]","[PASSWORD]");

    String commandText = "select version();";

    command.openConnection();

    command.executeNonQuery(commandText);

    Object result = command.executeScalar(commandText);

    assertNotNull(result);

    command.closeConnection();
  }

  // 需要测试时，取消注释 @Ignore 注解，然后手动测试。
  @Ignore
  @Test
  public void testExecute() throws SQLException {
    String url = env.getProperty("spring.datasource.url");

    GenericSqlCommand command = new GenericSqlCommand(url);

    String commandText = "select version();";

    command.openConnection();

    command.executeNonQuery(commandText);

    Object result = command.executeScalar(commandText);

    assertNotNull(result);

    command.closeConnection();
  }
}
