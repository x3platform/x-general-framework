package com.x3platform.apps.models;

import java.io.Serializable;

/**
 * 菜单权限
 */
public class ApplicationMenuScopeInfo implements Serializable {

  private String entityId ;   // 对应id

  private String entityClassName ; // xxx ? 没搞明白, 对象

  private String authorityId ;      // 对应 菜单 权限 , or 按钮权限

  private String authorizationObjectType ; //  角色 Role[角色] , Account[人员]

  private String authorizationObjectId ;   //  RoleId or  AccountId


  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getEntityClassName() {
    return entityClassName;
  }

  public void setEntityClassName(String entityClassName) {
    this.entityClassName = entityClassName;
  }

  public String getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(String authorityId) {
    this.authorityId = authorityId;
  }

  public String getAuthorizationObjectType() {
    return authorizationObjectType;
  }

  public void setAuthorizationObjectType(String authorizationObjectType) {
    this.authorizationObjectType = authorizationObjectType;
  }

  public String getAuthorizationObjectId() {
    return authorizationObjectId;
  }

  public void setAuthorizationObjectId(String authorizationObjectId) {
    this.authorizationObjectId = authorizationObjectId;
  }
}
