package com.x3platform.membership;

import java.util.Date;

/**
 * 帐户和角色的关联信息
 */
public interface AccountRoleRelation {

  /**
   * 帐号标识
   */
  String getAccountId();

  void setAccountId(String value);

  /**
   * 帐号全局名称
   */
  String getAccountGlobalName();

  void setAccountGlobalName(String value);

  /**
   * 角色标识
   */
  String getRoleId();

  void setRoleId(String value);

  /**
   * 角色全局名称
   */
  String getRoleGlobalName();

  void setRoleGlobalName(String value);

  /**
   * 是否默认角色
   */
  int getIsDefault();

  void setIsDefault(int value);

  /**
   * 生效时间
   */
  Date getBeginDate();

  void setBeginDate(Date value);

  /**
   * 失效时间
   */
  Date getEndDate();

  void setEndDate(Date value);

  Account getAccount();

  Role getRole();
}
