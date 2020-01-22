package com.x3platform.membership;

import java.io.Serializable;
import java.util.*;

import com.x3platform.AuthorizationObject;
import com.x3platform.cachebuffer.Cacheable;

/**
 * 组织单位
 */
public interface OrganizationUnit extends AuthorizationObject, Cacheable, Serializable {
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
  void setGlobalName(String value);
  /**
   * 全称
   */
  String getFullName();

  /**
   * 拼音
   */
  String getPinYin();

  void setPinYin(String value);

  /**
   * 所属父级组织单元标识
   */
  String getParentId();

  void setParentId(String value);

  /**
   * 所属父级节点名称
   */
  String getParentName();

  /**
   * 所属父节节点全局名称
   */
  String getParentGlobalName();

  /**
   * 父级组织信息
   */
  OrganizationUnit getParent();

  /**
   * 子节点
   */
  List<AuthorizationObject> getChindNodes();

  /**
   * 角色集合
   */
  List getRoles();

  /**
   * 获取主要负责人角色标识
   *
   * @return 主要负责人角色标识
   */
  String getChiefRoleId();

  void setChiefRoleId(String value);

  List<OrganizationUnit> getChildren();

  void setChildren(List<OrganizationUnit> children);

  /**
   * 所属标准组织标识
   */
  String getStandardOrganizationUnitId();

  void setStandardOrganizationUnitId(String value);

  /**
   * 类型
   *
   * 0-公司 1-部门 2-虚拟团队 3-项目 4-项目分期 9-区域 99-公司客户
   */
  int getType();

  void setType(int value);

  /**
   * 启用邮箱
   */
  int getEnableEmail();

  void setEnableEmail(int value);

  /**
   * 排序标识
   */
  String getOrderId();

  void setOrderId(String value);

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
   * 组织单位扩展信息
   */
  ExtensionInformation getExtensionInformation();

  /**
   * 创建时间
   */
  Date getCreatedDate();

  void setCreatedDate(Date value);

  /**
   * 新增补齐
   * @return
   */
  public int getLevel();
  void setLevel(int level);
}
