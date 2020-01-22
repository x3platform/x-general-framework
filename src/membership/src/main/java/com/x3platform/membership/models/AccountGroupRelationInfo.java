package com.x3platform.membership.models;

import com.alibaba.fastjson.annotation.JSONField;
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
   * 帐号全局名称
   */
  public String getAccountGlobalName() {
    return accountGlobalName;
  }

  public void setAccountGlobalName(String value) {
    accountGlobalName = value;
  }

  private String groupId = "";

  /**
   * 群组标识
   */
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String value) {
    groupId = value;
  }

  private String groupGlobalName = "";

  /**
   * 群组全局名称
   */
  public String getGroupGlobalName() {
    return groupGlobalName;
  }

  public void setGroupGlobalName(String value) {
    groupGlobalName = value;
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
  @Override
  @JSONField(serialize = false)
  public Account getAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关组织信息
   */
  @Override
  @JSONField(serialize = false)
  public Group getGroup() {
    return MembershipManagement.getInstance().getGroupService().findOne(this.getGroupId());
  }
}
