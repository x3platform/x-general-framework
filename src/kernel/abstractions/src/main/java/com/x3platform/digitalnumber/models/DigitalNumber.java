package com.x3platform.digitalnumber.models;

import com.x3platform.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

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

  private String description;

  /**
   * 描述
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    description = value;
  }

  /**
   * 是否锁定
   */
  public int locking = 1;

  public int getLocking() {
    return locking;
  }

  public void setLocking(int locking) {
    this.locking = locking;
  }

  private LocalDateTime modifiedDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 更新日期
   */
  public java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

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
