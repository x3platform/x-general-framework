package com.x3platform.apps.services;

import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationInfo;
import com.x3platform.apps.models.ApplicationSettingInfo;
import com.x3platform.apps.services.IApplicationService;
import com.x3platform.apps.services.impl.ApplicationService;
import com.x3platform.data.DataQuery;
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
public class ApplicationSettingServiceTests {

  @Test
  public void testFindOne() {
    // 测试应用配置 标识:20fc0681-9537-4955-a9ec-080205cc0865 名称:开始菜单
    IApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    ApplicationSettingInfo entity = service.findOne("20fc0681-9537-4955-a9ec-080205cc0865");

    assertNotNull("entity is not null.", entity);
  }

  @Test
  public void testFindAll() {

    IApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    DataQuery query = new DataQuery();

    List<ApplicationSettingInfo> list = service.findAll();

    assertNotNull("entity is not null.", list);
  }

  @Test
  public void testGetText() {
    IApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    DataQuery query = new DataQuery();

    String result = service.getText("00000000-0000-0000-0000-000000000001", "应用管理_应用菜单类别", "StartMenu");

    assertNotNull("result is not null.", result);
    assertEquals("result = 'StartMenu'.", "开始菜单", result);
  }

  @Test
  public void testGetValue() {
    IApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    DataQuery query = new DataQuery();

    String result = service.getValue("00000000-0000-0000-0000-000000000001", "应用管理_应用菜单类别", "开始菜单");

    assertNotNull("result is not null.", result);
    assertEquals("result = 'StartMenu'.", "StartMenu", result);
  }
}
