package com.x3platform.membership.models;

import com.x3platform.membership.IAccountGroupRelationInfo;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.IGroupInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;

/**
 * 帐户和群组的关联信息
 */
public class AccountGroupRelationInfo implements IAccountGroupRelationInfo {

  /**
   */
  public AccountGroupRelationInfo() {
  }

  /**
   */
  public AccountGroupRelationInfo(String accountId, String groupId) {
    this.setAccountId(accountId);
    this.setGroupId(groupId);
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

  private String mGroupId = "";

  /**
   * 群组标识
   */
  public final String getGroupId() {
    return mGroupId;
  }

  public final void setGroupId(String value) {
    mGroupId = value;
  }

  private String mGroupGlobalName = "";

  /**
   * 群组全局名称
   */
  public final String getGroupGlobalName() {
    return mGroupGlobalName;
  }

  public final void setGroupGlobalName(String value) {
    mGroupGlobalName = value;
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
  public final IAccountInfo GetAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关组织信息
   */
  public final IGroupInfo GetGroup() {
    // TODO 待处理
    // return MembershipManagement.getInstance().getGroupService().findOne(this.getGroupId());
    return null;
  }
}
