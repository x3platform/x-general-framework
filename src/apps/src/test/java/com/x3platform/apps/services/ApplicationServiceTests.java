package com.x3platform.apps.services;

import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.apps.services.ApplicationService;

import com.x3platform.data.DataQuery;
import com.x3platform.tests.TestConstants;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.x3platform.tests.TestConstants.APPLICATION_ID;
import static com.x3platform.tests.TestConstants.APPLICATION_NAME;
import static org.junit.Assert.*;

/**
 * 公共组件 配置数据库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationServiceTests {

  @Test
  public void testFindOne() {
    ApplicationService service = AppsContext.getInstance().getApplicationService();

    Application param = service.findOne(APPLICATION_ID);

    assertNotNull("entity is not null.", param);
  }

  @Test
  public void testFindOneByApplicationName() {

    ApplicationService service = AppsContext.getInstance().getApplicationService();

    Application entity = service.findOneByApplicationName(APPLICATION_NAME);

    assertNotNull(entity);
  }

  @Test
  public void testFindAll() {
    int rowCount = -1;

    DataQuery query = new DataQuery();

     List<Application> list = AppsContext.getInstance().getApplicationService().findAll(query);
  }

  @Test
  public void testGetAuthorizationScopes() {
     AppsContext.getInstance().getApplicationService()
       .getAuthorizationScopes(APPLICATION_ID, "应用_默认_管理员");
  }
}
