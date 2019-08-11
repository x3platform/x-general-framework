package com.x3platform.membership.models;

import com.x3platform.membership.Account;
import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.Role;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;

import java.util.Date;

/**
 * 帐户和角色的关联信息
 */
public class AccountRoleRelationInfo implements AccountRoleRelation {

  /**
   */
  public AccountRoleRelationInfo() {
  }

  /**
   */
  public AccountRoleRelationInfo(String accountId, String roleId) {
    this.setAccountId(accountId);
    this.setRoleId(roleId);
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
   * 帐号全局名称
   */
  public String getAccountGlobalName() {
    return mAccountGlobalName;
  }

  public void setAccountGlobalName(String value) {
    mAccountGlobalName = value;
  }

  private String mRoleId = "";

  /**
   * 角色标识
   */
  public String getRoleId() {
    return mRoleId;
  }

  public void setRoleId(String value) {
    mRoleId = value;
  }

  private String mRoleGlobalName = "";

  /**
   * 角色全局名称
   */
  public String getRoleGlobalName() {
    return mRoleGlobalName;
  }

  public void setRoleGlobalName(String value) {
    mRoleGlobalName = value;
  }

  private int mIsDefault = 0;

  /**
   * 是否默认角色
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
   * 获取相关角色信息
   */
  public Role getRole() {
    // TODO 待处理
    // return MembershipManagement.getInstance().getRoleService().findOne(this.getRoleId());
    return null;
  }
}
