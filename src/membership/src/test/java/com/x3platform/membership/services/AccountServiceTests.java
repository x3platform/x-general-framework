package com.x3platform.membership.services;

import static com.x3platform.tests.TestConstants.TEST_ID_PREFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.models.AccountInfo;
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
public class AccountServiceTests {

  @Autowired
  private AccountService service;

  @Test
  public void testFindOne() {
    Account entity = service.findOne(TestConstants.GENERAL_ACCOUNT_ID);

    assertNotNull(entity);
    assertEquals(TestConstants.GENERAL_ACCOUNT_NAME, entity.getName());

    entity = service.findOne(TestConstants.ANONYMOUS_ACCOUNT_ID);

    assertNotNull(entity);
    assertNotNull(JSON.toJSONString(entity));

    assertEquals("匿名用户", entity.getName());
  }

  @Test
  public void testFindOneByLoginName() {
    Account entity = service.findOneByLoginName(TestConstants.GENERAL_ACCOUNT_LOGIN_NAME);

    assertNotNull(entity);
    assertEquals(TestConstants.GENERAL_ACCOUNT_NAME, entity.getName());
  }

  @Test
  public void testFindAll() {
    List<Account> list = service.findAll();

    assertNotNull(list);
    assertTrue(list.size() > 0);

    DataQuery query = new DataQuery();

    query.getWhere().put("status", 1);

    list = service.findAll(query);

    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  /**
   * 测试 保存
   */
  @Test
  public void testSave() throws Exception {
    String id = TEST_ID_PREFIX + DateUtil.getTimestamp();

    Account param = new AccountInfo();

    param.setId(id);

    param.resetRelations("role",
      "role#00000000-0000-0000-0000-000000000001#abc,role#00000000-0000-0000-0000-000000000002#abd");
    // param.resetRelations("organizationUnit", "organizationUnit#" + TEST_ID_PREFIX + "123456#abc");
    param.resetRelations("group", "group#" + TEST_ID_PREFIX + "123456#abc,group#" + TEST_ID_PREFIX + "123457#abd");

    service.save(param);

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

  @Test
  public void testSetPassword() {
    // 设置 SHA1 方式加密的密码
    String password = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();

    Account account = service.findOne(TestConstants.ANONYMOUS_ACCOUNT_ID);

    password = DigestUtils.sha1Hex(password + account.getPasswordSalt());

    service.setPassword(TestConstants.ANONYMOUS_ACCOUNT_ID, "{SSHA}" + password);

    String confirmPassword = service.getPassword("anonymous");

    assertEquals(password, confirmPassword);

    // 设置明文密码
    password = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();

    service.setPassword(TestConstants.ANONYMOUS_ACCOUNT_ID, password);

    confirmPassword = service.getPassword("anonymous");

    assertEquals(DigestUtils.sha1Hex(password + account.getPasswordSalt()), confirmPassword);
  }
}
