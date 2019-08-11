package com.x3platform.connect.models;

import com.x3platform.EntityClass;
import com.x3platform.cachebuffer.*;
import com.x3platform.util.DateUtil;

import java.util.Date;

/**
 * 应用连接信息
 */
public class Connect extends EntityClass implements Cacheable {
  // 1 Anonymous 匿名权限 = 系统前端应用 + 匿名用户 + 读取 / 客户端标识(AppKey)
  // 2 Read 成员读取权限 = 系统前端应用 + 登录用户 + 读取 / 客户端标识(AppKey) + 客户端密钥(AppSecret)
  // 4 Write 写入权限 = 系统前端应用 + 登录用户 + 写入 / 客户端标识(AppKey) + 客户端密钥(AppSecret) + 访问令牌 (AccessToken)
  // 8 Privilege 应用管理员特权 = 系统前端应用 + 应用管理员特殊权限 / 第三方应用禁止访问

  public Connect() {

  }

  private String id;

  /**
   * 标识
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  private String accountId = "";

  /**
   * 提交人标识
   */
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String value) {
    accountId = value;
  }

  private String accountName = "";

  /**
   * 提交人姓名
   */
  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String value) {
    accountName = value;
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

  private String appSecret = "";

  /**
   * 应用私钥
   */
  public String getAppSecret() {
    return this.appSecret;
  }

  public void setAppSecret(String value) {
    this.appSecret = value;
  }

  private String appType = "";

  /**
   * 应用类型
   */
  public String getAppType() {
    return this.appType;
  }

  public void setAppType(String value) {
    this.appType = value;
  }

  private String code = "";

  /**
   * 编号
   */
  public String getCode() {
    return this.code;
  }

  public void setCode(String value) {
    this.code = value;
  }

  private String name = "";

  /**
   * 名称
   */
  public String getName() {
    return this.name;
  }

  public void setName(String value) {
    this.name = value;
  }

  private String description = "";

  /**
   * 描述
   */
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String value) {
    this.description = value;
  }

  private String domain = "";

  /**
   * 域
   */
  public String getDomain() {
    return this.domain;
  }

  public void setDomain(String value) {
    this.domain = value;
  }

  private String redirectUri = "";

  /**
   * 登录成功后重定向的地址
   */
  public String getRedirectUri() {
    return this.redirectUri;
  }

  public void setRedirectUri(String value) {
    this.redirectUri = value;
  }

  private String iconPath = "";

  /**
   * 图标文件
   */
  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String value) {
    iconPath = value;
  }

  private String options = "";

  /**
   * 自定义属性
   */
  public String getOptions() {
    return this.options;
  }

  public void setOptions(String value) {
    this.options = value;
  }

  private boolean isInternal = false;

  /**
   * 内置对象
   */
  public boolean getIsInternal() {
    return this.isInternal;
  }

  public void setIsInternal(boolean value) {
    this.isInternal = value;
  }

  private String authorizationScope = "";

  /**
   * 连接的授权范围(第三方应用默认的权限)
   */
  public String getAuthorizationScope() {
    return this.authorizationScope;
  }

  public void setAuthorizationScope(String value) {
    this.authorizationScope = value;
  }

  private String certifiedCode;

  /**
   * 验证代码
   */
  public String getCertifiedCode() {
    return certifiedCode;
  }

  public void setCertifiedCode(String value) {
    certifiedCode = value;
  }

  private int status;

  /**
   * 状态
   */
  public int getStatus() {
    return status;
  }

  public void setStatus(int value) {
    status = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改日期
   */
  public java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }
  ///#endregion

  ///#region 属性:CreatedDate
  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  /**
   * 实体对象标识
   */
  @Override
  public String getEntityId() {
    return this.getId();
  }

  // -------------------------------------------------------
  // 显式实现 ICacheable
  // -------------------------------------------------------

  private Date expires = DateUtil.getDefaultDate();

  /**
   * 过期时间
   */
  @Override
  public Date getExpires() {
    return expires;
  }

  @Override
  public void setExpires(Date value) {
    expires = value;
  }
}
