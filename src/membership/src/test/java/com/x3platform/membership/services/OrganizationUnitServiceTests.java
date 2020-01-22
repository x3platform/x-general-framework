package com.x3platform.membership.services;

import com.alibaba.fastjson.JSON;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.tests.TestConstants;
import com.x3platform.tree.TreeView;
import com.x3platform.util.DateUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class OrganizationUnitServiceTests {

  @Autowired
  private OrganizationUnitService service;

  @Test
  public void testFindOne() {
    OrganizationUnit entity = service.findOne(TestConstants.ORGANIZATION_ROOT_ID);

    assertNotNull( entity);
    assertEquals("组织架构", entity.getName());

    entity = service.findOne(TestConstants.ORGANIZATION_ID);

    assertNotNull( entity);
    //assertEquals("X Group", entity.getName());
  }

  @Test
  public void testFindAll() {
    List<OrganizationUnit> list = service.findAll();

    assertNotNull("result is not null.", list);

    assertTrue("result is not null.", list.size() > 0);
  }

  @Test
  public void testSave() {
    String id = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();

    OrganizationUnitInfo param = new OrganizationUnitInfo();

    param.setId(id);
    param.setParentId(TestConstants.ORGANIZATION_ID);
    param.setStatus(1);

    service.save(param);

    // 更新数据
    param.setName(id);

    service.save(param);

    // 删除数据
    service.delete(id);
  }

  @Test
  public void testGetTreeView() {
    TreeView treeView = service.getTreeView("组织架构", TestConstants.ORGANIZATION_ROOT_ID, "");

    assertNotNull(treeView);

    // 第二次读取 测试缓存
    treeView = service.getTreeView("组织架构", TestConstants.ORGANIZATION_ROOT_ID, "");

    assertNotNull(treeView);
  }
}
