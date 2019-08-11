package com.x3platform.digitalnumber.models;

import java.io.Serializable;

/**
 * 流水号信息
 */
public class DigitalNumber implements Serializable {

  /**
   * 默认构造函数
   */
  public DigitalNumber() {
  }

  private String name;

  /**
   * 名称
   */
  public String getName() {
    return name;
  }

  public void setName(String value) {
    name = value;
  }

  private String expression;

  /**
   * 表达式
   */
  public String getExpression() {
    return expression;
  }

  public void setExpression(String value) {
    expression = value;
  }

  private int seed;

  /**
   * 因子
   */
  public int getSeed() {
    return seed;
  }

  public void setSeed(int value) {
    seed = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 更新日期
   */
  public java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }
}
