package com.x3platform.sessions;

/**
 * 帐号缓存信息
 */
public class AccountCacheInfo {
  /**
   * 构造函数
   */
  public AccountCacheInfo() {
  }

  private String accountIdentity;

  /**
   * 帐号会话唯一标识
   */
  public final String getAccountIdentity() {
    return accountIdentity;
  }

  public final void setAccountIdentity(String value) {
    accountIdentity = value;
  }

  private String appKey;

  /**
   * 应用标识
   */
  public final String getAppKey() {
    return appKey;
  }

  public final void setAppKey(String value) {
    appKey = value;
  }

  private String accountCacheValue;

  /**
   * 帐号会话的缓存信息
   */
  public final String getAccountCacheValue() {
    return accountCacheValue;
  }

  public final void setAccountCacheValue(String value) {
    accountCacheValue = value;
  }

  private String accountObject;

  /**
   * 帐号会话的缓存对象
   */
  public final String getAccountObject() {
    return accountObject;
  }

  public final void setAccountObject(String value) {
    accountObject = value;
  }

  private String accountObjectType;

  /**
   * AccountObject的类型
   */
  public final String getAccountObjectType() {
    return accountObjectType;
  }

  public final void setAccountObjectType(String value) {
    accountObjectType = value;
  }

  private String ip;

  /**
   * 帐号会话的IP地址
   */
  public final String getIP() {
    return ip;
  }

  public final void setIP(String value) {
    ip = value;
  }

  private String httpUserAgent;

  /**
   * 帐号会话的Http用户代理信息
   */
  public final String getHttpUserAgent() {
    return httpUserAgent;
  }

  public final void setHttpUserAgent(String value) {
    httpUserAgent = value;
  }

  private String location;

  /**
   * 帐号会话的位置信息
   */
  public final String getLocation() {
    return location;
  }

  public final void setLocation(String value) {
    location = value;
  }

  private java.time.LocalDateTime validFrom = java.time.LocalDateTime.MIN;

  /**
   * 开始时间
   */
  public final java.time.LocalDateTime getValidFrom() {
    return validFrom;
  }

  public final void setValidFrom(java.time.LocalDateTime value) {
    validFrom = value;
  }

  private java.time.LocalDateTime validTo = java.time.LocalDateTime.MIN;

  /**
   * 结束时间
   */
  public final java.time.LocalDateTime getValidTo() {
    return validTo;
  }

  public final void setValidTo(java.time.LocalDateTime value) {
    validTo = value;
  }

  private java.time.LocalDateTime date = java.time.LocalDateTime.MIN;

  /**
   * 更新时间
   */
  public final java.time.LocalDateTime getDate() {
    return date;
  }

  public final void setDate(java.time.LocalDateTime value) {
    date = value;
  }

  // -------------------------------------------------------
  // 显式实现 ICacheable
  // -------------------------------------------------------

  private java.time.LocalDateTime expires = java.time.LocalDateTime.now().plusDays(1);

  private java.time.LocalDateTime getExpires() {
    return expires;
  }

  private void setExpires(java.time.LocalDateTime value) {
    expires = value;
  }
}
