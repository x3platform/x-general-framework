package com.x3platform.cachebuffer;

import java.util.Date;

/**
 * 对象缓存接口
 *
 * @author ruanyu
 */
public interface Cacheable {

  /**
   * 获取过期时间
   * @return 过期时间
   */
  Date getExpires();

  /**
   * 设置过期时间
   */
  void setExpires(Date value);
}
