package com.x3platform.apps.services;

import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.*;
import com.x3platform.apps.services.*;
import com.x3platform.data.DataQuery;

import com.x3platform.tests.TestConstants;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  ApplicationSettingService service;

  @Ignore
  @Test
  public void testFindOne() {
    ApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    // ApplicationSetting entity = service.findOne(TestConstants.APPLICATION_ID);

    // assertNotNull("entity is not null.", entity);
  }

  @Test
  public void testFindAll() {

    ApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    DataQuery query = new DataQuery();

    List<ApplicationSetting> list = service.findAll(query);

    assertNotNull(list);
  }

  @Test
  public void testFindAllByApplicationSettingGroupName() {

    List<ApplicationSetting> list = service.findAllByApplicationSettingGroupName("应用管理_应用菜单类别");

    assertNotNull(list);

    // 验证读取缓存
    list = service.findAllByApplicationSettingGroupName("应用管理_应用菜单类别");

    assertNotNull(list);
  }

  @Test
  public void testGetText() {
    ApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    String result = service.getText("00000000-0000-0000-0000-000000000001", "应用管理_应用菜单类别", "StartMenu");

    assertNotNull(result);
    assertEquals("开始菜单", result);
  }

  @Test
  public void testGetValue() {
    ApplicationSettingService service = AppsContext.getInstance().getApplicationSettingService();

    String result = service.getValue("00000000-0000-0000-0000-000000000001", "应用管理_应用菜单类别", "开始菜单");

    assertNotNull(result);
    assertEquals("StartMenu", result);
  }
}
