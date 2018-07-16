package com.x3platform.membership;

import com.x3platform.membership.MembershipManagement;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MembershipManagementTests {

  @Test
  public void testLoad() {
    MembershipManagement instance = MembershipManagement.getInstance();

    assertNotNull("OrganizationUnitService is not null.", instance.getOrganizationUnitService());
    assertNotNull("RoleService is not null.", instance.getRoleService());
    assertNotNull("AccountService is not null.", instance.getAccountService());
  }
}
