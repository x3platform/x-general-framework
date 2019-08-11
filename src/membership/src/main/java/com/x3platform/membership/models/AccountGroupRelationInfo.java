package com.x3platform.membership.models;

import com.x3platform.membership.AccountGroupRelation;
import com.x3platform.membership.Account;
import com.x3platform.membership.Group;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;

import java.util.Date;

/**
 * 帐户和群组的关联信息
 */
public class AccountGroupRelationInfo implements AccountGroupRelation {

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

  private String mGroupId = "";

  /**
   * 群组标识
   */
  public String getGroupId() {
    return mGroupId;
  }

  public void setGroupId(String value) {
    mGroupId = value;
  }

  private String mGroupGlobalName = "";

  /**
   * 群组全局名称
   */
  public String getGroupGlobalName() {
    return mGroupGlobalName;
  }

  public void setGroupGlobalName(String value) {
    mGroupGlobalName = value;
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
  @Override
  public Account GetAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关组织信息
   */
  public Group GetGroup() {
    // TODO 待处理
    // return MembershipManagement.getInstance().getGroupService().findOne(this.getGroupId());
    return null;
  }
}
