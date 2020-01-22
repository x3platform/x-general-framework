package com.x3platform.membership;

import java.time.LocalDateTime;

/**
 * 用户
 */
public interface User {

  /**
   * 用户标识
   */
  String getId();

  void setId(String value);

  /**
   * 姓名
   */
  String getName();

  void setName(String value);

  /**
   * 帐号
   */
  Account getAccount();

  void setAccount(Account value);

  /**
   * 帐户标识
   */
  String getAccountId();

  void setAccountId(String value);

  /**
   * 帐号名称
   */
  String getAccountName();

  /**
   * 公司标识
   */
  String getCorporationId();

  void setCorporationId(String value);

  /**
   * 公司名称
   */
  String getCorporationName();

  /**
   * 部门标识
   */
  String getDepartmentId();

  void setDepartmentId(String value);

  /**
   * 一级部门名称
   */
  String getDepartmentName();

  /**
   * 二级部门标识
   */
  String getDepartment2Id();

  void setDepartment2Id(String value);

  /**
   * 二级部门名称
   */
  String getDepartment2Name();

  /**
   * 三级部门标识
   */
  String getDepartment3Id();

  void setDepartment3Id(String value);

  /**
   * 三级部门名称
   */
  String getDepartment3Name();

  /**
   * 默认的组织单位标识
   */
  String getOrganizationUnitId();

  void setOrganizationUnitId(String value);

  /**
   * 默认的组织路径
   */
  String getOrganizationPath();

  /**
   * 默认的角色标识
   */
  String getRoleId();

  void setRoleId(String value);

  /**
   * 默认角色名称
   */
  String getRoleName();

  /**
   * 职务 | 头衔
   */
  String getHeadship();

  void setHeadship(String value);

  /**
   * 性别
   */
  String getGender();

  void setGender(String value);

  /**
   * 生日
   */
  LocalDateTime getBirthday();

  void setBirthday(LocalDateTime value);

  /**
   * 毕业时间
   */
  LocalDateTime getGraduationDate();

  void setGraduationDate(LocalDateTime value);

  /**
   * 入职时间
   */
  LocalDateTime getEntryDate();

  void setEntryDate(LocalDateTime value);

  /**
   * 最近一次晋升时间，如果刚入职则等于入职时间
   */
  LocalDateTime getPromotionDate();

  void setPromotionDate(LocalDateTime value);

  /**
   * 居住城市
   */
  String getCity();

  void setCity(String value);

  /**
   * 所属组织架构全路径
   */
  String getFullPath();

  void setFullPath(String value);

  /**
   * 修改时间
   */
  LocalDateTime getModifiedDate();

  void setModifiedDate(LocalDateTime value);

  /**
   * 创建时间
   */
  LocalDateTime getCreatedDate();

  void setCreatedDate(LocalDateTime value);
}
