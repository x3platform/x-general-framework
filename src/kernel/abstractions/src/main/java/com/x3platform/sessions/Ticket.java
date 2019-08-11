package com.x3platform.sessions;

/**
 * 会话票据信息
 *
 * @author ruanyu
 */
public class Ticket {
  /**
   * 构造函数
   */
  public Ticket() {
  }

  private String ticketId;

  /**
   * 帐号会话唯一标识
   */
  public String getTicketId() {
    return ticketId;
  }

  public void setTicketId(String value) {
    ticketId = value;
  }

  private String appKey;

  /**
   * 应用标识
   */
  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String value) {
    appKey = value;
  }

  private String ticketValue;

  /**
   * 帐号会话的缓存信息
   */
  public String getTicketValue() {
    return ticketValue;
  }

  public void setTicketValue(String value) {
    ticketValue = value;
  }

  private String accountObject;

  /**
   * 帐号会话的缓存对象
   */
  public String getAccountObject() {
    return accountObject;
  }

  public void setAccountObject(String value) {
    accountObject = value;
  }

  private String accountObjectType;

  /**
   * AccountObject的类型
   */
  public String getAccountObjectType() {
    return accountObjectType;
  }

  public void setAccountObjectType(String value) {
    accountObjectType = value;
  }

  private String ip;

  /**
   * 帐号会话的IP地址
   */
  public String getIP() {
    return ip;
  }

  public void setIP(String value) {
    ip = value;
  }

  private String httpUserAgent;

  /**
   * 帐号会话的Http用户代理信息
   */
  public String getHttpUserAgent() {
    return httpUserAgent;
  }

  public void setHttpUserAgent(String value) {
    httpUserAgent = value;
  }

  private String location;

  /**
   * 帐号会话的位置信息
   */
  public String getLocation() {
    return location;
  }

  public void setLocation(String value) {
    location = value;
  }

  private java.time.LocalDateTime validFrom = java.time.LocalDateTime.MIN;

  /**
   * 开始时间
   */
  public java.time.LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(java.time.LocalDateTime value) {
    validFrom = value;
  }

  private java.time.LocalDateTime validTo = java.time.LocalDateTime.MIN;

  /**
   * 结束时间
   */
  public java.time.LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(java.time.LocalDateTime value) {
    validTo = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 更新时间
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
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
