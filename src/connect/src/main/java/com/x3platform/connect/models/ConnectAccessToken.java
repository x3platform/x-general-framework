package com.x3platform.connect.models;

import com.x3platform.util.DateUtil;

/**
 * 应用连接访问令牌信息
 */
public class ConnectAccessToken {
  public ConnectAccessToken() {
  }

  private String id;

  /**
   * 标识
   */
  public final String getId() {
    return this.id;
  }

  public final void setId(String value) {
    this.id = value;
  }

  private String appKey;

  /**
   * 应用标识
   */
  public final String getAppKey() {
    return this.appKey;
  }

  public final void setAppKey(String value) {
    this.appKey = value;
  }

  private String accountId = "";

  /**
   * 提交人标识
   */
  public final String getAccountId() {
    return this.accountId;
  }

  public final void setAccountId(String value) {
    this.accountId = value;
  }

  private java.time.LocalDateTime expireDate = java.time.LocalDateTime.MIN;

  /**
   * 过期时间
   */
  public final java.time.LocalDateTime getExpireDate() {
    return this.expireDate;
  }

  public final void setExpireDate(java.time.LocalDateTime value) {
    this.expireDate = value;
  }

  /**
   * 过期时间(单位:秒)
   */
  public final long getExpiresIn() {
    return (DateUtil.toDate(this.getExpireDate()).getTime() - DateUtil.toDate(java.time.LocalDateTime.now()).getTime()) / 1000;
  }

  private String refreshToken;

  /**
   * 刷新令牌
   */
  public final String getRefreshToken() {
    return this.refreshToken;
  }

  public final void setRefreshToken(String value) {
    this.refreshToken = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改日期
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return this.modifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    this.modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return this.createdDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    this.createdDate = value;
  }
}
