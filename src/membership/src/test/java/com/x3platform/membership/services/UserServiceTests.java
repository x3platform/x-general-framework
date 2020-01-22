package com.x3platform.membership.services;

import static com.x3platform.tests.TestConstants.TEST_ID_PREFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.User;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.models.UserInfo;
import com.x3platform.tests.TestConstants;
import com.x3platform.util.DateUtil;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserServiceTests {

  @Autowired
  private UserService service;

  /**
   * 测试 保存
   */
  @Test
  public void testSave() throws Exception {
    String id = TEST_ID_PREFIX + DateUtil.getTimestamp();

    User param = new UserInfo();

    param.setId(id);

    // param.resetRelations("role",
    //  "role#00000000-0000-0000-0000-000000000001#abc,role#00000000-0000-0000-0000-000000000002#abd");
    // param.resetRelations("organizationUnit", "organizationUnit#" + TEST_ID_PREFIX + "123456#abc");
    // param.resetRelations("group", "group#" + TEST_ID_PREFIX + "123456#abc,group#" + TEST_ID_PREFIX + "123457#abd");

    service.save(param);
    service.setDefaultRole(id, TestConstants.GENERAL_ROLE_ID);
    // 验证帐号是否创建成功
    param = service.findOne(id);

    assertNotNull(param);

    // 更新数据
    param.setName(id);

    service.save(param);

    // 删除数据
    service.delete(id);

    param = service.findOne(id);

    assertNull(param);
  }
}
