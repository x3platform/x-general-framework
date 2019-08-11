package com.x3platform.membership.services;

import com.alibaba.fastjson.JSON;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.util.DateUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class OrganizationUnitServiceTests {

  // @Autowired
  // private OrganizationUnitService service;

  @Test
  public void testFindOne() {
    /*
    OrganizationUnitService service = MembershipManagement.getInstance().getOrganizationUnitService();

    OrganizationUnit entity = service.findOne("00000000-0000-0000-0000-000000000001");

    assertNotNull("result is not null.", entity);
    assertEquals("result.name is \"组织架构\".", "组织结构", entity.getName());

    // String json = JSON.toJSONString(entity);
    */
  }

  @Test
  public void testFindAll() {
    /*OrganizationUnitService service = MembershipManagement.getInstance().getOrganizationUnitService();

    List<OrganizationUnit> list = service.findAll();

    assertNotNull("result is not null.", list);

    assertTrue("result is not null.", list.size() > 0);
    */
  }


  /**
   * 测试 保存
   */

  @Test
  @Ignore
  public void testSave() {
    /*OrganizationUnitService service = MembershipManagement.getInstance()
      .getOrganizationUnitService();

    String id = "test_" + DateUtil.getTimestamp();

    OrganizationUnit param = new OrganizationUnitInfo();

    param.setId(id);

    //service.save(param);

    // 更新数据

    param.setName(id);

    //service.save(param);

    // 删除数据
    service.delete(id);
    */
  }
}
