package com.x3platform.membership.models;

import com.x3platform.membership.Account;
import com.x3platform.membership.AccountOrganizationUnitRelation;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;

import java.util.Date;

/**
 * 帐户和组织的关联信息
 */
public class AccountOrganizationUnitRelationInfo implements AccountOrganizationUnitRelation {
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
  public String getAccountId() {
    return mAccountId;
  }

  public void setAccountId(String value) {
    mAccountId = value;
  }

  private String mAccountGlobalName = "";

  /**
   * 帐号名称
   */
  public String getAccountGlobalName() {
    return mAccountGlobalName;
  }

  public void setAccountGlobalName(String value) {
    mAccountGlobalName = value;
  }

  private String mOrganizationUnitId = "";

  /**
   * 组织标识
   */
  public String getOrganizationUnitId() {
    return mOrganizationUnitId;
  }

  public void setOrganizationUnitId(String value) {
    mOrganizationUnitId = value;
  }

  private String mOrganizationUnitGlobalName = "";

  /**
   * 组织全局名称
   */
  public String getOrganizationUnitGlobalName() {
    return mOrganizationUnitGlobalName;
  }

  public void setOrganizationUnitGlobalName(String value) {
    mOrganizationUnitGlobalName = value;
  }

  private int mIsDefault = 0;

  /**
   * 是否默认组织
   */
  public int getIsDefault() {
    return mIsDefault;
  }

  public void setIsDefault(int value) {
    mIsDefault = value;
  }

  private Date mBeginDate = DateUtil.getDefaultDate();

  /**
   * 生效时间
   */
  public Date getBeginDate() {
    return mBeginDate;
  }

  public void setBeginDate(Date value) {
    mBeginDate = value;
  }

  private Date mEndDate = DateUtil.getDefaultDate();

  /**
   * 失效时间
   */
  public Date getEndDate() {
    return mEndDate;
  }

  public void setEndDate(Date value) {
    mEndDate = value;
  }

  /**
   * 获取相关帐号信息
   */
  public Account getAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关组织信息
   */
  public OrganizationUnit getOrganizationUnit() {
    // TODO 待处理
    // return MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getOrganizationUnitId());
    return null;
  }
}
