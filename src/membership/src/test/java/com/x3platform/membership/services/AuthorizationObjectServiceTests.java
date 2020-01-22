package com.x3platform.membership.services;

import static com.x3platform.tests.TestConstants.APPLICATION_FEATURE_FUNCTION_1_ID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.AuthorizationObjectType;
import com.x3platform.AuthorizationScope;
import com.x3platform.KernelContext;
import com.x3platform.tests.TestConstants;
import com.x3platform.tests.TestEntityObject;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class AuthorizationObjectServiceTests {

  @Autowired
  private AuthorizationObjectService service;

  @Test
  public void testBindAuthorizationScopes() {
    String scopeTableName = "application_feature_scope";
    String entityClassName = KernelContext.parseObjectType(TestEntityObject.class);
    // 根据实体对象标识绑定关系
    String entityId = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();
    // 2-0001 应用_通用_查看权限
    String authorityName = "应用_通用_查看权限";
    String scopeText = generateScopeText(AuthorizationObjectType.STANDARD_ROLE, 5);

    service.bindAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authorityName, scopeText);

    service.removeAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authorityName);

    // 根据授权对象绑定关系
    String entityIds = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();
    String authorizationObjectType = AuthorizationObjectType.STANDARD_ROLE.getValue();
    String authorizationObjectId = TestConstants.TEST_ID_PREFIX + DateUtil.getTimestamp();

    service.bindAuthorizationScopeByAuthorizationObjectId(scopeTableName, authorizationObjectType,
      authorizationObjectId, authorityName, entityIds, entityClassName);

    service.removeAuthorizationScopeByAuthorizationObjectId(scopeTableName, authorizationObjectType,
      authorizationObjectId, authorityName);
  }

  @Test
  public void testGetAuthorizationScopes() {
    String scopeTableName = "application_feature_scope";
    // APPLICATION_MENU_1_ID
    String entityClassName = KernelContext.parseObjectType(TestEntityObject.class);
    // 根据实体对象标识绑定关系
    String entityId = APPLICATION_FEATURE_FUNCTION_1_ID;
    // 2-0001 应用_通用_查看权限
    String authorityName = "应用_通用_查看权限";

    String scopeText = generateScopeText(AuthorizationObjectType.STANDARD_ROLE, 5);

    // 添加测试数据
    service.bindAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authorityName, scopeText);

    List<AuthorizationScope> list = service
      .getAuthorizationScopes(scopeTableName, entityId, entityClassName, authorityName);
    assertTrue(!list.isEmpty());

    String text = service.getAuthorizationScopeText(scopeTableName, entityId, entityClassName, authorityName);
    assertNotNull(text);

    String view = service.getAuthorizationScopeView(scopeTableName, entityId, entityClassName, authorityName);
    assertNotNull(view);

    // 添加删除数据
    service.removeAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authorityName);
  }

  String generateScopeText(AuthorizationObjectType type, int len) {
    StringBuilder outString = new StringBuilder();

    for (int i = 0; i < len; i++) {
      outString.append(type.getValue().toLowerCase() + "#" + StringUtil.toUuid() + "#" + StringUtil.toUuid() + ",");
    }

    return StringUtil.trimEnd(outString.toString(), ",");
  }
}
