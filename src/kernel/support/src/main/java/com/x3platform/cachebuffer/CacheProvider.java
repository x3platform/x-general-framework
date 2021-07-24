package com.x3platform.cachebuffer;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 缓存提供者
 *
 * @author ruanyu
 */
public interface CacheProvider {
  
  /**
   * 获取键信息
   *
   * @return 缓存对象的键信息
   */
  Set<String> keys(String pattern);
  
  // -------------------------------------------------------
  // 值
  // -------------------------------------------------------
  
  /**
   * 是否包含缓存记录
   *
   * @param name 名称
   * @return true | false
   */
  boolean contains(String name);
  
  /**
   * 获取缓存记录
   *
   * @param name 名称
   * @return 缓存对象的详细信息
   */
  Object get(String name);
  
  /**
   * 设置缓存记录
   *
   * @param name  名称
   * @param value 值
   */
  void set(String name, Object value);
  
  /**
   * 设置缓存记录
   *
   * @param name    名称
   * @param value   值
   * @param minutes 有效分钟数
   */
  void set(String name, Object value, long minutes);
  
  /**
   * 删除缓存记录
   *
   * @param name 名称
   */
  void delete(String name);
  
  /**
   * 删除缓存记录
   *
   * @param pattern 模式
   */
  void deleteByPattern(String pattern);
  
  // -------------------------------------------------------
  // 字典
  // -------------------------------------------------------
  
  /**
   * 是否包含缓存记录
   *
   * @param dict 字典
   * @return true 或 false
   */
  boolean contains(String dict, String name);
  
  /**
   * 获取缓存记录
   *
   * @param name 名称
   * @return 缓存对象的详细信息
   */
  Object get(String dict, String name);
  
  /**
   * 设置缓存记录
   *
   * @param dict  字典
   * @param name  名称
   * @param value 值
   */
  void set(String dict, String name, Object value);
  
  /**
   * 删除缓存记录
   *
   * @param dict 字典
   * @param name 名称
   */
  void delete(String dict, String name);
  
  // -------------------------------------------------------
  // 列表
  // -------------------------------------------------------
  
  /**
   * 设置列表集合
   *
   * @param key 列表
   * @param value 列表
   */
  long listLeftSetAll(String key, List value);
  
  /**
   * 获取列表内容
   *
   * @param key 列表的键
   * @param start 开始
   * @param end 结束
   */
  List listGetRange(String key, long start, long end);
}
