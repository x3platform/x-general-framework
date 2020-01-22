package com.x3platform;

import com.x3platform.security.authority.*;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权范围管理
 *
 * @author ruanyu
 */
public class AuthorizationScopeManagement {

  /**
   * 绑定授权范围
   *
   * @param authorizationScopes 授权范围列表
   * @param scopeText 授权范围文本
   */
  public static void bindAuthorizationScopes(List<AuthorizationScope> authorizationScopes, String scopeText) {
    List<AuthorizationScope> list = getAuthorizationScopes(scopeText);

    authorizationScopes.clear();

    for (AuthorizationScope item : list) {
      authorizationScopes.add(new AuthorizationScope(item.getAuthorizationObjectType(),
        item.getAuthorizationObjectId(), item.getAuthorizationObjectName()));
    }
  }

  /**
   * 绑定授权范围
   *
   * @param scopeText 授权范围文本
   * @return 授权范围列表
   */
  public static List<AuthorizationScope> getAuthorizationScopes(String scopeText) {
    List<AuthorizationScope> scopeArray = new ArrayList<AuthorizationScope>();

    if (!StringUtil.isNullOrEmpty(scopeText)) {
      String[] scope = scopeText.split(",|;");

      for (String scopeItemText : scope) {
        scopeArray.add(new AuthorizationScope(scopeItemText));
      }
    }

    return scopeArray;
  }

  /**
   * 获取授权范围文本信息
   *
   * @param authorizationScopes 授权范围列表
   * @return 授权范围文本信息
   */
  public static String getAuthorizationScopeText(List<AuthorizationScope> authorizationScopes) {
    StringBuilder outString = new StringBuilder();

    for (AuthorizationScope authorizationScope : authorizationScopes) {
      if (authorizationScope.getAuthorizationObjectType() != null
        && !StringUtil.isNullOrEmpty(authorizationScope.getAuthorizationObjectId())) {
        outString.append(String.format("%1$s,", authorizationScope));
      }
    }

    return StringUtil.trimEnd(outString.toString(), ",");
  }

  /**
   * 获取授权范围文本信息
   *
   * @param authorizationScopes 授权范围列表
   * @return 授权范围视图信息
   */
  public static String getAuthorizationScopeView(List<AuthorizationScope> authorizationScopes) {
    StringBuilder outString = new StringBuilder();

    for (AuthorizationScope authorizationScopeObject : authorizationScopes) {
      if (authorizationScopeObject.getAuthorizationObjectType() != null
        && !StringUtil.isNullOrEmpty(authorizationScopeObject.getAuthorizationObjectId())
        && !StringUtil.isNullOrEmpty(authorizationScopeObject.getAuthorizationObjectName())) {
        outString.append(String.format("%1$s,", authorizationScopeObject.getAuthorizationObjectName()));
      }
    }

    return StringUtil.trimEnd(outString.toString(), ",");
  }
}
