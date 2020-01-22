package com.x3platform.membership.models;

import com.alibaba.fastjson.annotation.JSONField;
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

  private String accountId = "";

  /**
   * 帐号标识
   */
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String value) {
    accountId = value;
  }

  private String accountGlobalName = "";

  /**
   * 帐号名称
   */
  public String getAccountGlobalName() {
    return accountGlobalName;
  }

  public void setAccountGlobalName(String value) {
    accountGlobalName = value;
  }

  private String organizationUnitId = "";

  /**
   * 组织标识
   */
  public String getOrganizationUnitId() {
    return organizationUnitId;
  }

  public void setOrganizationUnitId(String value) {
    organizationUnitId = value;
  }

  private String organizationUnitGlobalName = "";

  /**
   * 组织全局名称
   */
  public String getOrganizationUnitGlobalName() {
    return organizationUnitGlobalName;
  }

  public void setOrganizationUnitGlobalName(String value) {
    organizationUnitGlobalName = value;
  }

  private int isDefault = 0;

  /**
   * 是否默认组织
   */
  public int getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(int value) {
    isDefault = value;
  }

  private Date beginDate = DateUtil.getDefaultDate();

  /**
   * 生效时间
   */
  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date value) {
    beginDate = value;
  }

  private Date endDate = DateUtil.getDefaultDate();

  /**
   * 失效时间
   */
  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date value) {
    endDate = value;
  }

  /**
   * 获取相关帐号信息
   */
  @JSONField(serialize = false)
  public Account getAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关组织信息
   */
  @JSONField(serialize = false)
  public OrganizationUnit getOrganizationUnit() {
    return MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getOrganizationUnitId());
  }
}
