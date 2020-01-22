package com.x3platform.membership;

import com.x3platform.AuthorizationObject;


/**
 * 标准组织
 */
public interface StandardOrganizationUnit extends AuthorizationObject {
  /**
   * 获取标识
   * @return 标识
   */
  String getId();

  /**
   * 设置标识
   * @param value 值
   */
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
   * 所属父级标准组织标识
   */
  String getParentId();

  void setParentId(String value);

  /**
   * 父节点全局名称
   */
  String getParentName();

  /**
   * 父节点全局名称
   */
  String getParentGlobalName();
}
