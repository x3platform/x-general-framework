package com.x3platform.apps.models;

import java.io.Serializable;

public class ApplicationScopeInfo implements Serializable {

  private String entityId ;   // 对应应用id

  private String entityClassName ; // 对应反射类  ? 没搞明白, 对象

  private String authorityId ;      // 对应  sys_authority  中id

  private String authorizationObjectType ; //  isAdministrator 管理员  isReviewer 审核员   isMember 可访问成员

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
