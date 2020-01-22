package com.x3platform.apps.services;

import static com.x3platform.tests.TestConstants.APPLICATION_FEATURE_FUNCTION_1_ID;
import static com.x3platform.tests.TestConstants.APPLICATION_MENU_1_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.pagehelper.PageHelper;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.membership.services.AccountService;
import com.x3platform.tests.TestConstants;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
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
public class ApplicationFeatureServiceTests {

  @Autowired
  private ApplicationFeatureService service;

  @Test
  public void testFindOne() {
    ApplicationFeature entity = service.findOne(APPLICATION_FEATURE_FUNCTION_1_ID);

    assertNotNull("entity is not null.", entity);

    assertNotNull("entity name is '应用管理'.", entity.getName().equals("应用管理"));

    entity = service.findOne(APPLICATION_MENU_1_ID);

    assertNotNull("entity is not null.", entity);

    assertNotNull("entity name is '应用管理'.", entity.getName().equals("应用管理"));
  }

  @Test
  public void testFindAll() {

    ApplicationFeatureService service = AppsContext.getInstance().getApplicationFeatureService();

    DataQuery query = new DataQuery();
    // 默认情况
    List<ApplicationFeature> list = service.findAll(query);

    assertNotNull("list is not null.", list);

    // 测试条件
    query = DataQuery.create(
      "{scene:'findAll',where:[{name:'menu_type',value:'ApplicationFeature'},{name:'application_id',value:'00000000-0000-0000-0000-000000000001'},{name:'status',value:1}], orders:'order_id, code'}");

    list = service.findAll(query);

    assertNotNull("list is not null.", list);

    // 测试长度
    query = DataQuery.create("{scene:'default', orders:'order_id, code', length:10}");

    list = service.findAll(query);

    assertEquals("list.size() is 10.", 10, list.size());
  }

  @Test
  public void testQuery() {
    ApplicationFeatureService service = AppsContext.getInstance().getApplicationFeatureService();

    int total = -1;

    DataQuery query = new DataQuery();

    PageHelper.startPage(2, 4);

    List<ApplicationFeature> list = service.findAll(query);

    assertEquals(4, list.size());

    total = DataPagingUtil.getTotal(list);
  }

  @Test
  public void testIsExist() {
    // 存在的情况
    String id = APPLICATION_FEATURE_FUNCTION_1_ID;

    boolean isExist = service.isExist(id);

    assertTrue("entity(id:" + id + ") is exist.", isExist);

    // 不存在的情况
    id = DigitalNumberContext.generate("Key_32DigitGuid");

    isExist = service.isExist(id);

    assertFalse("entity(id:" + id + ") is not exist.", isExist);
  }

  @Ignore
  @Test
  public void testGetFeatures() {
    // List<ApplicationFeature> list = service
    //  .getFeaturesByParentId("00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000000",
    //    "ApplicationFeature");

    // assertEquals(4, list.size());

    TreeView treeView = service.getTreeView("", TestConstants.APPLICATION_ID, "");
  }

  @Ignore
  @Test
  public void testGetTreeView() {
    TreeView treeView = service.getTreeView("", TestConstants.APPLICATION_ID, "");

    assertNotNull(treeView);
    assertTrue(treeView.getChildNodes().size() > 0);
  }

  @Ignore
  @Test
  public void testGetDynamicTreeView() {
    DynamicTreeView treeView = service.getDynamicTreeView("", TestConstants.APPLICATION_ID, "0", "");

    assertNotNull(treeView);
    assertTrue(treeView.getChildNodes().size() > 0);
  }
}
