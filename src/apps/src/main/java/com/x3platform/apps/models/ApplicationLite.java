package com.x3platform.apps.models;

import com.x3platform.membership.Account;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用精简信息
 */
public class ApplicationLite implements Serializable {

  /**
   * 默认构造函数
   */
  public ApplicationLite() {
  }

  private String id;
  private Account account;
  private String accountId = "";
  private String parentId = "";
  private String code = "";
  private String applicationName = "";
  private String applicationDisplayName = "";
  private String applicationKey = "";
  private String applicationSecret = "";
  private String pinyin = "";
  /**
   * 拼音简写
   */
  private String description = "";

  /**
   *
   */
  private int hasChildren = 0;
  private String administratorEmail = "";
  private String iconPath = "";

  /**
   * 图标文件
   */
  private String bigIconPath = "";

  /**
   * 大图标文件
   */
  private String helpUrl = "";

  /**
   * 功能帮助文件
   */
  private String licenseStatus = "";

  /**
   * 授权状态
   */
  private int hidden;

  /**
   * 显示为菜单列表时是否隐藏。
   */
  private int locking;

  /**
   * 是否锁定 0:允许 1:锁定
   */
  private String orderId = "";

  /**
   *
   */
  private int status;

  /**
   *
   */
  private String remark = "";

  /**
   *
   */
  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.now();
  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.now();


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getApplicationDisplayName() {
    return applicationDisplayName;
  }

  public void setApplicationDisplayName(String applicationDisplayName) {
    this.applicationDisplayName = applicationDisplayName;
  }

  public String getApplicationKey() {
    return applicationKey;
  }

  public void setApplicationKey(String applicationKey) {
    this.applicationKey = applicationKey;
  }

  public String getApplicationSecret() {
    return applicationSecret;
  }

  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getHasChildren() {
    return hasChildren;
  }

  public void setHasChildren(int hasChildren) {
    this.hasChildren = hasChildren;
  }

  public String getAdministratorEmail() {
    return administratorEmail;
  }

  public void setAdministratorEmail(String administratorEmail) {
    this.administratorEmail = administratorEmail;
  }

  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String iconPath) {
    this.iconPath = iconPath;
  }

  public String getBigIconPath() {
    return bigIconPath;
  }

  public void setBigIconPath(String bigIconPath) {
    this.bigIconPath = bigIconPath;
  }

  public String getHelpUrl() {
    return helpUrl;
  }

  public void setHelpUrl(String helpUrl) {
    this.helpUrl = helpUrl;
  }

  public String getLicenseStatus() {
    return licenseStatus;
  }

  public void setLicenseStatus(String licenseStatus) {
    this.licenseStatus = licenseStatus;
  }

  public int getHidden() {
    return hidden;
  }

  public void setHidden(int hidden) {
    this.hidden = hidden;
  }

  public int getLocking() {
    return locking;
  }

  public void setLocking(int locking) {
    this.locking = locking;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(LocalDateTime modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
