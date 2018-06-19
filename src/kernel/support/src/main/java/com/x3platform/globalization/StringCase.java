package com.x3platform.globalization;

/**
 * 本地化信息
 */
public enum StringCase {
  /**
   * 默认
   */
  Default,
  /**
   * 小写
   */
  Lower,
  /**
   * 大写
   */
  Upper,
  /**
   * 首字母小写
   */
  FirstLower,
  /**
   * 首字母大写
   */
  FirstUpper,
  /**
   * 标题
   */
  Title;

  public static final int SIZE = java.lang.Integer.SIZE;

  public int getValue() {
    return this.ordinal();
  }

  public static StringCase forValue(int value) {
    return values()[value];
  }
}
