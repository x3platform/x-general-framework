package com.x3platform.membership;

/**
 * 组织信息
 */
public interface Organization {
  /**
   * 获取标识
   */
  String getId();

  /**
   * 设置标识
   * @param value 值
   */
  void setId(String value);

  /**
   * 获取名称
   */
  String getName();

  /**
   * 设置名称
   * @param value 值
   */
  void setName(String value);

  /**
   * 获取域
   */
  String getDomain();

  /**
   * 设置域
   * @param value 值
   */
  void setDomain(String value);

  /**
   * 模式
   */
  String getPattern();

  /**
   * 设置模式
   * @param value 值
   */
  void setPattern(String value);
}
