package com.x3platform.membership;

/**
 * 帐户和组织的关联信息
 */
public interface IAccountOrganizationUnitRelationInfo {

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
   * 组织标识
   */
  String getOrganizationUnitId();

  void setOrganizationUnitId(String value);

  /**
   * 组织全局名称
   */
  String getOrganizationUnitGlobalName();

  void setOrganizationUnitGlobalName(String value);

  /**
   * 是否默认组织
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

  IOrganizationUnitInfo getOrganizationUnit();
}
