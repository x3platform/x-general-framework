package com.x3platform.membership.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.data.DataQuery;
import com.x3platform.membership.StandardRole;
import com.x3platform.membership.models.StandardRoleInfo;
import com.x3platform.tests.TestConstants;
import com.x3platform.util.DateUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class StandardRoleServiceTests {

  @Autowired
  private StandardRoleService service;

  @Test
  public void testFindOne() {
    StandardRole entity = service.findOne(TestConstants.STANDARD_ROLE_CORPORATION_ID);

    assertNotNull( entity);
    assertEquals("公司负责人", entity.getName());

    entity = service.findOne(TestConstants.STANDARD_ROLE_TEAM_ID);

    assertNotNull( entity);
    assertEquals("虚拟团队负责人", entity.getName());
  }

  @Test
  public void testFindAll() {
    DataQuery query = new DataQuery();

    List<StandardRole> list = service.findAll(query);

    assertNotNull("result is not null.", list);

    assertTrue("result is not null.", list.size() > 0);
  }

  @Test
  public void testSave() {
    String id = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();

    StandardRoleInfo param = new StandardRoleInfo();

    param.setId(id);
    // param.setParentId(TestConstants.ORGANIZATION_ID);

    service.save(param);

    // 更新数据
    param.setName(id);

    service.save(param);

    // 删除数据
    service.delete(id);
  }
}
