package com.x3platform.cachebuffer.memory;

import com.x3platform.cachebuffer.CacheProvider;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 内存缓存提供者
 *
 * @author ruanyu
 */
public class MemoryCacheProvider implements CacheProvider {

  // -------------------------------------------------------
  // 值
  // -------------------------------------------------------

  ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();

  /**
   * 是否包含缓存记录
   *
   * @return 返回是否包含
   */
  @Override
  public boolean contains(String name) {
    return map.containsKey(name);
  }

  /**
   * 获取项
   *
   * @param name 名称
   * @return 对象
   */
  @Override
  public Object get(String name) {
    return map.get(name);
  }

  /**
   * 设置项
   *
   * @param name 名称
   * @param value 值
   */
  @Override
  public void set(String name, Object value) {
    if (map.containsKey(name)) {
      map.replace(name, value);
    } else {
      map.put(name, value);
    }
  }

  /**
   * 添加缓存记录
   *
   * @param name 标志
   * @param value 值
   * @param minutes 有效分钟数
   */
  @Override
  public void set(String name, Object value, int minutes) {
    map.put(name, value);
  }

  /**
   * 移除缓存记录
   *
   * @param name 名称
   */
  @Override
  public void delete(String name) {
    map.remove(name);
  }

  @Override
  public void deleteByPattern(String pattern) {
    String regex = pattern.replace("?", "[0-9a-z]").replace("*", "[0-9a-z]{0,}");

    Set<String> keys = map.keySet();

    // 遍历 map 中的键
    for (String key : keys) {
      if (key.matches(regex)) {
        map.remove(key);
      }
    }

    keys = dicts.keySet();

    // 遍历 dicts 中的键
    for (String key : keys) {
      if (key.matches(regex)) {
        dicts.remove(key);
      }
    }
  }

  // -------------------------------------------------------
  // 字典
  // -------------------------------------------------------

  ConcurrentMap<String, ConcurrentMap<String, Object>> dicts = new ConcurrentHashMap<String, ConcurrentMap<String, Object>>();

  @Override
  public boolean contains(String dict, String name) {
    if (dicts.containsKey(dict)) {
      return dicts.get(dict).containsKey(name);
    }
    return false;
  }

  @Override
  public Object get(String dict, String name) {
    if (dicts.containsKey(dict)) {
      return dicts.get(dict).get(name);
    }
    return null;
  }

  @Override
  public void set(String dict, String name, Object value) {
    if (!dicts.containsKey(dict)) {
      dicts.put(dict, new ConcurrentHashMap<String, Object>());
    }
    dicts.get(dict).put(name, value);
  }

  @Override
  public void delete(String dict, String name) {
    if (dicts.containsKey(dict)) {
      dicts.get(dict).remove(name);
    }
  }
}
