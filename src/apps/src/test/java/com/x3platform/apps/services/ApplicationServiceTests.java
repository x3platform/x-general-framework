package com.x3platform.apps.services;

import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.apps.services.ApplicationService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 *  公共组件 配置数据库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationServiceTests {

  @Test
  public void testFindOne() {
    // 测试应用配置 标识:52cf89ba-7db5-4e64-9c64-3c868b6e7a99
    ApplicationService service = AppsContext.getInstance().getApplicationService();

    Application param = service.findOne("00000000-0000-0000-0000-000000000001");

    assertNotNull("entity is not null.", param);
  }

  @Test
  public void testFindOneByApplicationName() {

    ApplicationService service = AppsContext.getInstance().getApplicationService();

    Application entity = service.findOneByApplicationName("Test");

    assertNotNull("entity is not null.", entity);
  }

  @Test
  public void testGetPaging() {
    int rowCount = -1;

    // DataQuery query = new DataQuery();

    // IList<Application> list = AppsContext.Instance.ApplicationService.GetPaging(0, 10, query, out rowCount);
  }

  @Test
  public void testGetAuthorizationScopeObjects() {
    // AppsContext.Instance.ApplicationService.GetAuthorizationScopeObjects("52cf89ba-7db5-4e64-9c64-3c868b6e7a99", "应用_默认_管理员");
  }
}
