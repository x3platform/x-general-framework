package com.x3platform.digitalNumber.models;

/**
 * 流水号信息
 */
public class DigitalNumberInfo {

  /**
   * 默认构造函数
   */
  public DigitalNumberInfo() {
  }

  private String name;

  /**
   * 名称
   */
  public final String getName() {
    return name;
  }

  public final void setName(String value) {
    name = value;
  }

  private String expression;

  /**
   * 表达式
   */
  public final String getExpression() {
    return expression;
  }

  public final void setExpression(String value) {
    expression = value;
  }

  private int seed;

  /**
   * 因子
   */
  public final int getSeed() {
    return seed;
  }

  public final void setSeed(int value) {
    seed = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 更新日期
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }
}
