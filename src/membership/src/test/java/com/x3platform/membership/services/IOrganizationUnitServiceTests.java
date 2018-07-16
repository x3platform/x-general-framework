package com.x3platform.membership.services;

import com.x3platform.data.DataQuery;
import com.x3platform.digitalNumber.DigitalNumberContext;
import com.x3platform.digitalNumber.models.DigitalNumberInfo;
import com.x3platform.digitalNumber.services.IDigitalNumberService;
import com.x3platform.membership.IOrganizationUnitInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class IOrganizationUnitServiceTests {

  // @Autowired
  // private IOrganizationUnitService service;

  @Test
  public void testFindOne() {
    IOrganizationUnitService service = MembershipManagement.getInstance().getOrganizationUnitService();

    IOrganizationUnitInfo entity = service.findOne("00000000-0000-0000-0000-000000000001");

    assertNotNull("result is not null.", entity);
    assertEquals("result.name is \"组织架构\".", "组织结构", entity.getName());
  }

  @Test
  public void testFindAll() {
    IOrganizationUnitService service = MembershipManagement.getInstance().getOrganizationUnitService();

    List<IOrganizationUnitInfo> list = service.findAll();

    assertNotNull("result is not null.", list);

    DataQuery query = new DataQuery();

    query.setLength(5);

    list = service.findAll();

    assertEquals("result is not null.", 5, list.size());
  }

  /**
   * 测试 保存
   */
  @Test
  public void testSave() {
    IOrganizationUnitService service = MembershipManagement.getInstance().getOrganizationUnitService();

    String name = "test_" + DateUtil.getTimestamp();

    IOrganizationUnitInfo param = new OrganizationUnitInfo();

    param.setName(name);
    //param.setExpression("{guid}");
    // param.setSeed(0);

    service.save(param);

    // 更新数据
    param.setName(name);
    // param.setExpression("{int:seed:8}");
    // param.setSeed(999);

    service.save(param);

    // 删除数据
    service.delete(name);
  }
}
