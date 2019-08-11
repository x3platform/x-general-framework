package com.x3platform.connect.models;

/**
 * 应用连接授权码信息
 */
public class ConnectAuthorizationCode {

  private String id;

  /**
   * 标识
   */
  public String getId() {
    return this.id;
  }

  public void setId(String value) {
    this.id = value;
  }

  private String appKey;

  /**
   * 应用标识
   */
  public String getAppKey() {
    return this.appKey;
  }

  public void setAppKey(String value) {
    this.appKey = value;
  }

  private String accountId = "";

  /**
   * 提交人标识
   */
  public String getAccountId() {
    return this.accountId;
  }

  public void setAccountId(String value) {
    this.accountId = value;
  }

  private String authorizationScope;

  /**
   * 授权范围(除第三方应用默认的权限外，用户授予的权限)
   */
  public String getAuthorizationScope() {
    return this.authorizationScope;
  }

  public void setAuthorizationScope(String value) {
    this.authorizationScope = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改日期
   */
  public java.time.LocalDateTime getModifiedDate() {
    return this.modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    this.modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public java.time.LocalDateTime getCreatedDate() {
    return this.createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    this.createdDate = value;
  }
}
