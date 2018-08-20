package com.x3platform.apps.services;

import com.github.pagehelper.PageHelper;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationInfo;
import com.x3platform.apps.models.ApplicationMenuInfo;
import com.x3platform.apps.services.IApplicationService;
import com.x3platform.apps.services.impl.ApplicationService;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalNumber.DigitalNumberContext;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationMenuServiceTests {

  @Test
  public void testFindOne() {
    // 测试应用配置 标识:52cf89ba-7db5-4e64-9c64-Service3c868b6e7a99
    IApplicationMenuService service = AppsContext.getInstance().getApplicationMenuService();

    ApplicationMenuInfo entity = service.findOne("2feced95-48de-4f01-adb5-15ed92678bc9");

    assertNotNull("entity is not null.", entity);

    assertNotNull("entity name is '应用管理'.", entity.getName().equals("应用管理"));
  }

  @Test
  public void testFindAll() {

    IApplicationMenuService service = AppsContext.getInstance().getApplicationMenuService();

    DataQuery query = new DataQuery();
    // 默认情况
    List<ApplicationMenuInfo> list = service.findAll(query);

    assertNotNull("list is not null.", list);

    // 测试条件
    query = DataQuery.create("{scence:'findAll',where:[{name:'MenuType',value:'ApplicationMenu'},{name:'applicationId',value:'00000000-0000-0000-0000-000000000001'},{name:'status',value:1}], orders:'OrderId,Code'}");

    list = service.findAll(query);

    assertNotNull("list is not null.", list);

    // 测试长度
    query = DataQuery.create("{scence:'default', orders:'OrderId, Code', length:10}");

    list = service.findAll(query);

    assertEquals("list.size() is 10.", 10, list.size());
  }

  @Test
  public void testGetPaging() {
    IApplicationMenuService service = AppsContext.getInstance().getApplicationMenuService();

    int total = -1;

    DataQuery query = new DataQuery();

    PageHelper.startPage(2, 4);

    List<ApplicationMenuInfo> list = service.findAll(query);

    assertEquals(4, list.size());

    total = DataPagingUtil.getTotal(list);
  }

  @Test
  public void testIsExist() {
    IApplicationMenuService service = AppsContext.getInstance().getApplicationMenuService();

    // 存在的情况
    String id = "2feced95-48de-4f01-adb5-15ed92678bc9";

    boolean isExist = service.isExist(id);

    assertTrue("entity(id:" + id + ") is exist.", isExist);

    // 不存在的情况
    id = DigitalNumberContext.generate("Key_32DigitGuid");

    isExist = service.isExist(id);

    assertFalse("entity(id:" + id + ") is not exist.", isExist);
  }
}
