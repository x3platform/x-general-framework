package com.x3platform.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期处理辅助类
 */
public class DateUtil {

  private static final LocalDateTime BASE_DATE_TIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
  private static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59, 999);

  /**
   * 默认时间 (LocalDateTime 格式)
   */
  public static LocalDateTime getDefaultLocalDateTime() {
    return BASE_DATE_TIME;
  }

  /**
   * 最大时间 (LocalDateTime 格式)
   */
  public static LocalDateTime getMaxLocalDateTime() {
    return MAX_DATE_TIME;
  }

  /**
   * 默认时间 (Date 格式)
   */
  public static Date getDefaultDate() {
    return toDate(getDefaultLocalDateTime());
  }

  /**
   * 最大时间 (Date 格式)
   */
  public static Date getMaxDate() {
    return toDate(getMaxLocalDateTime());
  }

  /**
   * 获取当前时间的时间戳 UNIX 时间戳, 即从 1970年1月1日 00:00:00 到当前时间的秒数之和.
   */
  public static long getTimestamp() {
    return getTimestamp(LocalDateTime.now());
  }

  /**
   * 获取某个时间的 UNIX 时间戳, 即从 1970年1月1日 00:00:00 到某个时间的秒数之和.
   *
   * @param datetime 时间
   */
  public static long getTimestamp(LocalDateTime datetime) {
    // return (dateTime.Ticks - baseTime.Ticks) / 10000000 - 8 * 60 * 60;
    // return (long)(datetime - baseTime.ToLocalTime()).TotalSeconds;
    // return (toDate(datetime).getTime() - toDate(baseTime).getTime()) / 10000000;
    return (toDate(datetime).getTime() - toDate(BASE_DATE_TIME).getTime()) / 1000;
  }

  /**
   * 获取某个 UNIX 时间戳的时间格式.
   *
   * @param timestamp 从 1970年1月1日 00:00:00 到某个时间的秒数之和 长度:10
   */
  public static LocalDateTime toLocalDateTime(long timestamp) {
    // return new DateTime((timeStamp + 8 * 60 * 60) * 10000000 + BaseTime.Ticks);
    // return (LocalDateTime.of(timestamp * 10000000 + baseTime.getTime())).ToLocalTime();
    // return (LocalDateTime.of(timestamp * 10000000 + toDate(baseTime).getTime())).ToLocalTime();

    return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
  }

  /**
   * 获取某个时间到当前时间的时间间隔
   */
  public static Duration getDuration(LocalDateTime beginTime) {
    return getDuration(beginTime, LocalDateTime.now());
  }

  /**
   * 获取某段时间的时间间隔
   */
  public static Duration getDuration(LocalDateTime beginTime, LocalDateTime endTime) {
    // return ((new TimeSpan(endTime.getTime())).Subtract(new TimeSpan(beginTime.getTime())));
    return Duration.between(beginTime, endTime);
  }

  public static LocalDateTime toLocalDateTime(Date date) {
    Instant instant = date.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    return LocalDateTime.ofInstant(instant, zone);
  }

  public static Date toDate(LocalDateTime localDateTime) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = localDateTime.atZone(zone).toInstant();
    return Date.from(instant);
  }
}
