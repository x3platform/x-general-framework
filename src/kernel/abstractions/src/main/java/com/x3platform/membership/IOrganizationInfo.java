package com.x3platform.membership;

/**
 * 组织信息
 */
public interface IOrganizationInfo {
  /**
   * 标识
   */
  String getId();

  void setId(String value);

  /**
   * 名称
   */
  String getName();

  void setName(String value);

  /**
   * 域
   */
  String getDomain();

  void setDomain(String value);

  /**
   * 模式
   */
  String getPattern();

  void setPattern(String value);

}
