package com.x3platform.connect.models;

import com.x3platform.EntityClass;
import com.x3platform.cacheBuffer.*;

/**
 * 应用连接信息
 */
public class ConnectInfo extends EntityClass implements ICacheable {
  // 1 Anonymous 匿名权限 = 系统前端应用 + 匿名用户 + 读取 / 客户端标识(AppKey)
  // 2 Read 成员读取权限 = 系统前端应用 + 登录用户 + 读取 / 客户端标识(AppKey) + 客户端密钥(AppSecret)
  // 4 Write 写入权限 = 系统前端应用 + 登录用户 + 写入 / 客户端标识(AppKey) + 客户端密钥(AppSecret) + 访问令牌 (AccessToken)
  // 8 Privilege 应用管理员特权 = 系统前端应用 + 应用管理员特殊权限 / 第三方应用禁止访问

  public ConnectInfo() {
  }

  private String mId;

  /**
   * 标识
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private String mAccountId = "";

  /**
   * 提交人标识
   */
  public final String getAccountId() {
    return mAccountId;
  }

  public final void setAccountId(String value) {
    mAccountId = value;
  }

  private String mAccountName = "";

  /**
   * 提交人姓名
   */
  public final String getAccountName() {
    return mAccountName;
  }

  public final void setAccountName(String value) {
    mAccountName = value;
  }

  private String mAppKey;

  /**
   * 应用标识
   */
  public final String getAppKey() {
    return this.mAppKey;
  }

  public final void setAppKey(String value) {
    this.mAppKey = value;
  }

  private String mAppSecret = "";

  /**
   * 应用私钥
   */
  public final String getAppSecret() {
    return this.mAppSecret;
  }

  public final void setAppSecret(String value) {
    this.mAppSecret = value;
  }

  private String mAppType = "";

  /**
   * 应用类型
   */
  public final String getAppType() {
    return this.mAppType;
  }

  public final void setAppType(String value) {
    this.mAppType = value;
  }

  private String mCode = "";

  /**
   * 编号
   */
  public final String getCode() {
    return this.mCode;
  }

  public final void setCode(String value) {
    this.mCode = value;
  }

  private String mName = "";

  /**
   * 名称
   */
  public final String getName() {
    return this.mName;
  }

  public final void setName(String value) {
    this.mName = value;
  }

  private String mDescription = "";

  /**
   * 描述
   */
  public final String getDescription() {
    return this.mDescription;
  }

  public final void setDescription(String value) {
    this.mDescription = value;
  }

  private String mDomain = "";

  /**
   * 域
   */
  public final String getDomain() {
    return this.mDomain;
  }

  public final void setDomain(String value) {
    this.mDomain = value;
  }

  private String mRedirectUri = "";

  /**
   * 登录成功后重定向的地址
   */
  public final String getRedirectUri() {
    return this.mRedirectUri;
  }

  public final void setRedirectUri(String value) {
    this.mRedirectUri = value;
  }

  private String mIconPath = "";

  /**
   * 图标文件
   */
  public final String getIconPath() {
    return mIconPath;
  }

  public final void setIconPath(String value) {
    mIconPath = value;
  }

  private String mOptions = "";

  /**
   * 自定义属性
   */
  public final String getOptions() {
    return this.mOptions;
  }

  public final void setOptions(String value) {
    this.mOptions = value;
  }

  private boolean mIsInternal = false;

  /**
   * 内置对象
   */
  public final boolean getIsInternal() {
    return this.mIsInternal;
  }

  public final void setIsInternal(boolean value) {
    this.mIsInternal = value;
  }

  private String mAuthorizationScope = "";

  /**
   * 连接的授权范围(第三方应用默认的权限)
   */
  public final String getAuthorizationScope() {
    return this.mAuthorizationScope;
  }

  public final void setAuthorizationScope(String value) {
    this.mAuthorizationScope = value;
  }

  private String mCertifiedCode;

  /**
   * 验证代码
   */
  public final String getCertifiedCode() {
    return mCertifiedCode;
  }

  public final void setCertifiedCode(String value) {
    mCertifiedCode = value;
  }

  private int mStatus;

  /**
   * 状态
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }

  private java.time.LocalDateTime mModifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改日期
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }
  ///#endregion

  ///#region 属性:CreatedDate
  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
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

  private java.time.LocalDateTime mExpires = java.time.LocalDateTime.MAX;

  /**
   * 过期时间
   */
  @Override
  public java.time.LocalDateTime getExpires() {
    return mExpires;
  }

  @Override
  public void setExpires(java.time.LocalDateTime value) {
    mExpires = value;
  }
}
