package com.x3platform.membership.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.data.DataQuery;
import com.x3platform.membership.StandardRole;
import com.x3platform.membership.authentication.LdapAuthenticationManagement;
import com.x3platform.membership.models.StandardRoleInfo;
import com.x3platform.tests.TestConstants;
import com.x3platform.util.DateUtil;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class LdapAuthenticationManagementTests {

  @Autowired
  private StandardRoleService service;

  @Ignore
  @Test
  public void testFindOne() {
    LdapAuthenticationManagement management = new LdapAuthenticationManagement();

    management.login("ruanyu","ff",true);
  }
}
