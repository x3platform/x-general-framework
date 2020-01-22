package com.x3platform.data;

import com.x3platform.SpringContext;

import com.x3platform.configuration.KernelConfiguration;
import com.x3platform.data.DataQuery;
import java.sql.SQLException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.x3platform.configuration.KernelConfigurationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenericSqlCommandTests {

  @Autowired
  Environment env;

  // private static String url = "jdbc:mysql://localhost:3306/x3_base?user=root&password=root&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true";

  // @Ignore
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
