package com.x3platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * UUID处理辅助类
 */
public class UUIDUtil {

  /**
   * 格式化输出UUID
   */
  public static String toString(UUID uuid) {
    return toString(uuid, "D");
  }

  /**
   * 格式化输出UUID
   */
  public static String toString(UUID uuid, String foramt) {
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
    // X Four hexadecimal values enclosed in braces, where the fourth value is a subset of eight hexadecimal values that is also enclosed in braces:
    // {0x00000000,0x0000,0x0000,{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}}

    switch (foramt.toUpperCase()) {
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
}
