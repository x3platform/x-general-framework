package com.x3platform.membership;

import java.util.Date;

/**
 * 帐户和组织的关联信息
 */
public interface AccountOrganizationUnitRelation {

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
  Date getBeginDate();

  void setBeginDate(Date value);

  /**
   * 失效时间
   */
  Date getEndDate();

  void setEndDate(Date value);

  Account getAccount();

  OrganizationUnit getOrganizationUnit();
}
