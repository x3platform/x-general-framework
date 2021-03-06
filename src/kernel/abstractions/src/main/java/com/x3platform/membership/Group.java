package com.x3platform.membership;

import com.x3platform.AuthorizationObject;

import java.util.*;

/**
 * 群组信息
 */
public interface Group extends AuthorizationObject {

  /**
   * 编号
   */
  String getCode();

  void setCode(String value);

  /**
   * 全局名称
   */
  String getGlobalName();

  /**
   * 拼音
   */
  String getPinYin();

  void setPinYin(String value);

  /**
   * 分组类别节点标识
   */
  String getCatalogItemId();

  void setCatalogItemId(String value);

  /**
   * 启用企业邮箱
   */
  int getEnableExchangeEmail();

  void setEnableExchangeEmail(int value);

  /**
   * 所属组织架构全路径
   */
  String getFullPath();

  void setFullPath(String value);

  /**
   * 唯一名称
   */
  String getDistinguishedName();

  void setDistinguishedName(String value);

  /**
   * 成员列表
   */
  List<Account> getMembers();

  // -------------------------------------------------------
  // 重置成员关系
  // -------------------------------------------------------

  /**
   * 重置成员关系
   *
   * @param relationText
   */
  void resetMemberRelations(String relationText);
}
