package com.x3platform.membership;

/**
 * 帐户和角色的关联信息
 */
public interface IAccountRoleRelationInfo {

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
  java.time.LocalDateTime getBeginDate();

  void setBeginDate(java.time.LocalDateTime value);

  /**
   * 失效时间
   */
  java.time.LocalDateTime getEndDate();

  void setEndDate(java.time.LocalDateTime value);

  IAccountInfo getAccount();

  IRoleInfo getRole();
}
