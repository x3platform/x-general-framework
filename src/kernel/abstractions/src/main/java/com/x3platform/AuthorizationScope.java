package com.x3platform;

import com.x3platform.util.StringUtil;

/**
 * 授权范围
 *
 * @author ruanyu
 */
public class AuthorizationScope {

  public AuthorizationScope() {
  }

  /**
   */
  public AuthorizationScope(String scopeText) {
    String[] args = scopeText.split("#");

    setAuthorizationObjectType(AuthorizationObjectType.getValue(args[0] == null ? "" : args[0]));
    setAuthorizationObjectId(args[1] == null ? "" : args[1]);

    if (args.length == 3) {
      setAuthorizationObjectName(args[2] == null ? "" : args[2]);
    }
  }

  public AuthorizationScope(String authorizationObjectType,
    String authorizationObjectId, String authorizationObjectName) {

    setAuthorizationObjectType(authorizationObjectType);
    setAuthorizationObjectId(authorizationObjectId);
    setAuthorizationObjectName(authorizationObjectName);
  }

  public AuthorizationScope(AuthorizationObjectType authorizationObjectType,
    String authorizationObjectId, String authorizationObjectName) {
    setAuthorizationObjectType(authorizationObjectType.getValue());
    setAuthorizationObjectId(authorizationObjectId);
    setAuthorizationObjectName(authorizationObjectName);
  }

  private String authorizationObjectType;

  /**
   * 类型
   */
  public String getAuthorizationObjectType() {
    return authorizationObjectType;
  }

  public void setAuthorizationObjectType(String value) {
    authorizationObjectType = value;
  }

  private String authorizationObjectId;

  /**
   * 对象唯一标识
   */
  public String getAuthorizationObjectId() {
    return authorizationObjectId;
  }

  public void setAuthorizationObjectId(String value) {
    authorizationObjectId = value;
  }

  private String authorizationObjectName;

  /**
   * 名称
   */
  public String getAuthorizationObjectName() {
    return authorizationObjectName;
  }

  public void setAuthorizationObjectName(String value) {
    authorizationObjectName = value;
  }

  /**
   * @return
   */
  @Override
  public String toString() {
    return StringUtil.toFirstLower(getAuthorizationObjectType())
      + "#" + getAuthorizationObjectId()
      + "#" + (StringUtil.isNullOrEmpty(getAuthorizationObjectName()) ? getAuthorizationObjectId()
      : getAuthorizationObjectName());
  }
}
