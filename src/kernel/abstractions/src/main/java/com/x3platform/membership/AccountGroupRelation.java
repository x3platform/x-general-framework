package com.x3platform.membership;

import java.util.Date;

/**
 * 帐户和群组的关联信息
 */
public interface AccountGroupRelation {
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
   * 群组标识
   */
  String getGroupId();

  void setGroupId(String value);

  /**
   * 群组全局名称
   */
  String getGroupGlobalName();

  void setGroupGlobalName(String value);

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

  Account GetAccount();

  Group GetGroup();
}
