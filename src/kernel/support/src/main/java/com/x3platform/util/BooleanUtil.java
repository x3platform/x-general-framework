package com.x3platform.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * 布尔值处理辅助类
 */
public class BooleanUtil {

  /**
   * 字符串参数值转为布尔值
   */
  public static boolean bool(String text) {
    text = text.toUpperCase();
    // 支持的格式
    // 支持 1 或 0
    // 支持 Yes 或 No
    // 支持 On 或 Off
    if (text.equals("1") || text.equals("TRUE") || text.equals("YES") || text.equals("ON")) {
      return true;
    }

    return false;
  }
}
