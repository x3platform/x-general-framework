package com.x3platform.cacheBuffer;

/**
 * 对象缓存接口
 */
public interface ICacheable {

  /**
   * 过期时间
   */
  java.time.LocalDateTime getExpires();

  void setExpires(java.time.LocalDateTime value);
}
