package com.x3platform.util;

import java.util.UUID;
import org.apache.http.annotation.Obsolete;

/**
 * UUID 处理辅助类
 */
public class UUIDUtil {

  private static final String EMPTY_STRING = "00000000-0000-0000-0000-000000000000";

  /**
   * 创建空的UUID
   */
  public static UUID empty() {
    // 请勿修改此方法；该方法使用在顶级和父级为空的情况；
    return UUID.fromString(EMPTY_STRING);
  }

  /**
   * 创建空的 UUID 字符串
   */
  public static String emptyString() {
    return stringify(empty(), "N");
  }

  /**
   * 创建空的 UUID 字符串
   */
  public static String emptyString(String format) {
    return stringify(empty(), format);
  }

  /**
   * 创建随机的 UUID 字符串
   */
  public static UUID random() {
    return UUID.randomUUID();
  }

  /**
   * 创建随机的 UUID 字符串
   */
  public static String randomString(String format) {
    return stringify(random(), format);
  }

  /**
   * 格式化输出 UUID
   * @param uuid UUID 格式数据
   */
  public static String stringify(UUID uuid) {
    return stringify(uuid, "D");
  }

  /**
   * 格式化输出 UUID
   * @param uuid UUID 格式数据
   * @param foramt 格式 N D B P
   */
  public static String stringify(UUID uuid, String format) {
    // N - 32 digits:
    // 00000000000000000000000000000000
    //
    // D - 32 digits separated by hyphens:
    // 00000000-0000-0000-0000-000000000000
    //
    // B - 32 digits separated by hyphens, enclosed in braces:
    // {00000000-0000-0000-0000-000000000000}
    //
    // P 32 digits separated by hyphens, enclosed in parentheses:
    //  (00000000-0000-0000-0000-000000000000)
    //
    //  X Four hexadecimal values enclosed in braces, where the fourth value is a subset of eight hexadecimal values that is also enclosed in braces:
    // {0x00000000,0x0000,0x0000,{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}}

    switch (format.toUpperCase()) {
      case "N":
        return uuid.toString().replace("-", "");
      case "D":
        return uuid.toString();
      case "B":
        return "{" + uuid.toString() + "}";
      case "P":
        return "(" + uuid.toString() + ")";
      case "X":
        // TODO 待处理
        return "{" + uuid.toString() + "}";
      default:
        return uuid.toString();
    }
  }

  // -------------------------------------------------------
  // 兼容老代码
  // -------------------------------------------------------

  /**
   * 格式化输出 UUID
   *
   * @deprecated use stringify(uuid) instead
   */
  @Deprecated()
  public static String toString(UUID uuid) {
    return stringify(uuid, "D");
  }

  /**
   * 格式化输出UUID
   *
   * @deprecated use stringify(uuid, format) instead
   */
  @Deprecated()
  public static String toString(UUID uuid, String format) {
    return stringify(uuid, format);
  }
}
