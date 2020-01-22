package com.x3platform.membership;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MembershipManagementTests {

  @Test
  public void testLoad() {
    MembershipManagement instance = MembershipManagement.getInstance();

    assertNotNull(instance.getSettingService());
    assertNotNull(instance.getAccountService());
    assertNotNull(instance.getAccountLogService());
    assertNotNull(instance.getGroupService());
    assertNotNull(instance.getOrganizationService());
    assertNotNull(instance.getOrganizationUnitService());
    assertNotNull(instance.getRoleService());
    assertNotNull(instance.getStandardOrganizationUnitService());
    assertNotNull(instance.getStandardRoleService());
  }
}

