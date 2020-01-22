package com.x3platform.membership.models;

import com.alibaba.fastjson.annotation.JSONField;
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

  private String roleId = "";

  /**
   * 角色标识
   */
  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String value) {
    roleId = value;
  }

  private String roleGlobalName = "";

  /**
   * 角色全局名称
   */
  public String getRoleGlobalName() {
    return roleGlobalName;
  }

  public void setRoleGlobalName(String value) {
    roleGlobalName = value;
  }

  private int isDefault = 0;

  /**
   * 是否默认角色
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
  @Override
  @JSONField(serialize = false)
  public Account getAccount() {
    return MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
  }

  /**
   * 获取相关角色信息
   */
  @Override
  @JSONField(serialize = false)
  public Role getRole() {
    return MembershipManagement.getInstance().getRoleService().findOne(this.getRoleId());
  }
}
