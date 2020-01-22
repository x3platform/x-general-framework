package com.x3platform.apps.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationMethod;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationMethodServiceTests {

  @Autowired
  private ApplicationMethodService service;

  @Test
  public void testFindOne() {

    ApplicationMethod entity = service.findOneByName("sys.digitalNumber.generate");

    assertNotNull("entity is not null.", entity);

    assertNotNull("entity name is '应用管理'.", entity.getName().equals("应用管理"));
  }

  @Test
  public void testFindAll() {

    ApplicationMethodService service = AppsContext.getInstance().getApplicationMethodService();

    DataQuery query = new DataQuery();
    // 默认情况
    List<ApplicationMethod> list = service.findAll(query);

    assertNotNull("list is not null.", list);

    // 测试条件
    query = DataQuery.create(
      "{scene:'findAll',where:[{name:'menu_type',value:'ApplicationMethod'},{name:'application_id',value:'00000000-0000-0000-0000-000000000001'},{name:'status',value:1}], orders:'order_id, code'}");

    list = service.findAll(query);

    assertNotNull("list is not null.", list);

    // 测试长度
    query = DataQuery.create("{scene:'default', orders:'order_id, code', length:10}");

    list = service.findAll(query);

    assertEquals("list.size() is 10.", 10, list.size());
  }

  @Test
  public void testGetPaging() {
    int total = -1;

    DataQuery query = new DataQuery();

    PageHelper.startPage(2, 4);

    List<ApplicationMethod> list = service.findAll(query);

    assertEquals(4, list.size());

    total = DataPagingUtil.getTotal(list);
  }

  @Ignore
  @Test
  public void testIsExist() {
    ApplicationMethodService service = AppsContext.getInstance().getApplicationMethodService();

    // 存在的情况
    String id = "2feced95-48de-4f01-adb5-15ed92678bc9";

    boolean isExist = service.isExist(id);

    assertTrue("entity(id:" + id + ") is exist.", isExist);

    // 不存在的情况
    id = DigitalNumberContext.generate("Key_32DigitGuid");

    isExist = service.isExist(id);

    assertFalse("entity(id:" + id + ") is not exist.", isExist);
  }

  @Test
  public void testParseOptions() {
    String optionsText = "{'requiredParams':'id,name'}";

    JSONObject options = JSON.parseObject(optionsText);
    if (options.containsKey("requiredParams")) {
      String[] requiredParams = options.getString("requiredParams").split(",");
    }
  }
}
