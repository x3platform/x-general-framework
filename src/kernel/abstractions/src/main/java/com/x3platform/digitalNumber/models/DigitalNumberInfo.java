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

  private String m_Name;

  /**
   * 名称
   */
  public final String getName() {
    return m_Name;
  }

  public final void setName(String value) {
    m_Name = value;
  }

  private String m_Expression;

  /**
   * 表达式
   */
  public final String getExpression() {
    return m_Expression;
  }

  public final void setExpression(String value) {
    m_Expression = value;
  }

  private int m_Seed;

  /**
   * 因子
   */
  public final int getSeed() {
    return m_Seed;
  }

  public final void setSeed(int value) {
    m_Seed = value;
  }

  private java.time.LocalDateTime m_ModifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 更新日期
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return m_ModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    m_ModifiedDate = value;
  }

  private java.time.LocalDateTime m_CreatedDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return m_CreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    m_CreatedDate = value;
  }
}
