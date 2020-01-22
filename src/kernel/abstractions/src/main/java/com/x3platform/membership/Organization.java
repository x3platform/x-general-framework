package com.x3platform.membership;

/**
 * 组织信息
 */
public interface Organization {
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
   * 获取编码
   * @return 标识
   */
  String getCode();

  /**
   * 设置编码
   * @param value 值
   */
  void setCode(String value);

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
   * 许可信息
   */
  String getLicense();

  /**
   * 设置许可信息
   * @param value 值
   */
  void setLicense(String value);

  /**
   * 获取状态
   *
   * @return 状态
   */
  int getStatus();

  /**
   * 设置状态
   *
   * @param value 值
   */
  void setStatus(int value);
}
