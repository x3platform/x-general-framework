package com.x3platform.membership.services;

import static com.x3platform.tests.TestConstants.TEST_ID_PREFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.AccountBinding;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.tests.TestConstants;
import com.x3platform.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class AccountBindingServiceTests {

  @Autowired
  private AccountBindingService service;

  @Test
  public void testFindOne() {
    AccountBinding entity = service.findOne(TestConstants.GENERAL_ACCOUNT_ID, "wechat");

    assertNotNull(entity);
    assertEquals(TestConstants.GENERAL_ACCOUNT_ID, entity.getAccountId());

    assertEquals("demo-wechat", entity.getBindingObjectId());
  }

  @Test
  public void testFindAll() {
    List<AccountBinding> list = service.findAllByAccountId(TestConstants.GENERAL_ACCOUNT_ID);

    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void testFindAllBindingObjectIds() {
    List<String> accountIds = new ArrayList<String>();

    accountIds.add(TestConstants.GENERAL_ACCOUNT_ID);

    List<String> list = service.findAllBindingObjectIds(accountIds, "wechat");

    assertNotNull(list);
    assertTrue(list.size() > 0);
  }

  @Test
  public void testBind() {
    int result = service.bind(TestConstants.GENERAL_ACCOUNT_ID, "test", "demo-binding-test", "{}");

    AccountBinding entity = service.findOne(TestConstants.GENERAL_ACCOUNT_ID, "test");

    assertNotNull(entity);
    assertEquals("demo-binding-test", entity.getBindingObjectId());

    result = service.unbind(TestConstants.GENERAL_ACCOUNT_ID, "test", "demo-binding-test");

    entity = service.findOne(TestConstants.GENERAL_ACCOUNT_ID, "test");

    assertNull(entity);
  }
}
