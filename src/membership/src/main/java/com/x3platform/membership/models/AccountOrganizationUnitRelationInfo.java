package com.x3platform.membership.models;

import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.IAccountOrganizationUnitRelationInfo;
import com.x3platform.membership.IOrganizationUnitInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;

/**
 * 帐户和组织的关联信息
 */
public class AccountOrganizationUnitRelationInfo implements IAccountOrganizationUnitRelationInfo {
  /**
   */
  public AccountOrganizationUnitRelationInfo() {
  }

  /**
   */
  public AccountOrganizationUnitRelationInfo(String accountId, String organizationId) {
    this.setAccountId(accountId);
    this.setOrganizationUnitId(organizationId);
  }

  private String mAccountId = "";

  /**
   * 帐号标识
   */
  public final String getAccountId() {
    return mAccountId;
  }

  public final void setAccountId(String value) {
    mAccountId = value;
  }

  private String mAccountGlobalName = "";

  /**
   * 帐号名称
   */
  public final String getAccountGlobalName() {
    return mAccountGlobalName;
  }

  public final void setAccountGlobalName(String value) {
    mAccountGlobalName = value;
  }

  private String mOrganizationUnitId = "";

  /**
   * 组织标识
   */
  public final String getOrganizationUnitId() {
    return mOrganizationUnitId;
  }

  public final void setOrganizationUnitId(String value) {
    mOrganizationUnitId = value;
  }

  private String mOrganizationUnitGlobalName = "";

  /**
   * 组织全局名称
   */
  public final String getOrganizationUnitGlobalName() {
    return mOrganizationUnitGlobalName;
  }

  public final void setOrganizationUnitGlobalName(String value) {
    mOrganizationUnitGlobalName = value;
  }

  private int mIsDefault = 0;

  /**
   * 是否默认组织
   */
  public final int getIsDefault() {
    return mIsDefault;
  }

  public final void setIsDefault(int value) {
    mIsDefault = value;
  }

  private java.time.LocalDateTime mBeginDate = DateUtil.getDefaultTime();

  /**
   * 生效时间
   */
  public final java.time.LocalDateTime getBeginDate() {
    return mBeginDate;
  }

  public final void setBeginDate(java.time.LocalDateTime value) {
    mBeginDate = value;
  }

  private java.time.LocalDateTime mEndDate = java.time.LocalDateTime.MAX;

  /**
   * 失效时间
   */
  public final java.time.LocalDateTime getEndDate() {
    return mEndDate;
  }

  public final void setEndDate(java.time.LocalDateTime value) {
    mEndDate = value;
  }

  /**
   * 获取相关帐号信息
   */
  public final IAccountInfo getAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关组织信息
   */
  public final IOrganizationUnitInfo getOrganizationUnit() {
    // TODO 待处理
    // return MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getOrganizationUnitId());
    return null;
  }
}
