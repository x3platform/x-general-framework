package com.x3platform.cacheBuffer;

/**

 */
public interface ICacheProvider {

  /**
   * 索引
   */
  Object getItem(String name);

  void setItem(String name, Object value);

  /**
   * 是否包含缓存记录
   *
   * @param name
   * @return
   */
  boolean Contains(String name);

  /**
   * 设置缓存记录
   *
   * @param name  名称
   * @param value 值
   * @return 返回缓存对象的详细信息
   */
  void Set(String name, Object value);

  /**
   * 获取缓存记录
   *
   * @param name 名称
   * @return 返回缓存对象的详细信息
   */
  Object Get(String name);

  /**
   * 添加缓存记录
   *
   * @param name  标志
   * @param value 值
   */
  void Add(String name, Object value);

  /**
   * 添加缓存记录
   *
   * @param name    标志
   * @param value   值
   * @param minutes 有效分钟数
   */
  void Add(String name, Object value, int minutes);

  /**
   * 移除缓存记录
   *
   * @param name 名称
   */
  void Remove(String name);
}
