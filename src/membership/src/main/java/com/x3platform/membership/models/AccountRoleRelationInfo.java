package com.x3platform.membership.models;

import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.IAccountRoleRelationInfo;
import com.x3platform.membership.IRoleInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;

/**
 * 帐户和角色的关联信息
 */
public class AccountRoleRelationInfo implements IAccountRoleRelationInfo {

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
  public final String getAccountId() {
    return mAccountId;
  }

  public final void setAccountId(String value) {
    mAccountId = value;
  }

  private String mAccountGlobalName = "";

  /**
   * 帐号全局名称
   */
  public final String getAccountGlobalName() {
    return mAccountGlobalName;
  }

  public final void setAccountGlobalName(String value) {
    mAccountGlobalName = value;
  }

  private String mRoleId = "";

  /**
   * 角色标识
   */
  public final String getRoleId() {
    return mRoleId;
  }

  public final void setRoleId(String value) {
    mRoleId = value;
  }

  private String mRoleGlobalName = "";

  /**
   * 角色全局名称
   */
  public final String getRoleGlobalName() {
    return mRoleGlobalName;
  }

  public final void setRoleGlobalName(String value) {
    mRoleGlobalName = value;
  }

  private int mIsDefault = 0;

  /**
   * 是否默认角色
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
   * 获取相关角色信息
   */
  public final IRoleInfo getRole() {
    // TODO 待处理
    // return MembershipManagement.getInstance().getRoleService().findOne(this.getRoleId());
    return null;
  }
}
