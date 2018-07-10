package com.x3platform.membership;

import java.util.*;

import com.x3platform.IAuthorizationObject;

/**
 * 角色
 */
public interface IRoleInfo extends IAuthorizationObject {

  /**
   * 标识
   */
  String getId();

  void setId(String value);

  /**
   * 编号
   */
  String getCode();

  void setCode(String value);

  /**
   * 名称
   */
  String getName();

  void setName(String value);

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
   * 父级信息
   */
  IRoleInfo getParent();

  /**
   * 所属组织标识
   */
  String getOrganizationUnitId();

  void setOrganizationUnitId(String value);

  /**
   * 所属组织信息
   */
  IOrganizationUnitInfo getOrganizationUnit();

  /**
   * 所属标准角色标识
   */
  String getStandardRoleId();

  void setStandardRoleId(String value);

  /** 所属标准角色信息
   */
  // IStandardRoleInfo getStandardRole();
  ///#endregion

  /**
   * 启用企业邮箱
   */
  int getEnableEmail();

  void setEnableEmail(int value);

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
  List<IAccountInfo> getMembers();

  /**
   * 角色的扩展信息
   */
  IExtensionInformation getExtensionInformation();

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
