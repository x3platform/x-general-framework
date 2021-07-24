package com.x3platform.cachebuffer.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.x3platform.InternalLogger;
import com.x3platform.cachebuffer.CacheProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Guava 缓存提供器
 *
 * @author ruanyu
 */
public class GuavaCacheProvider implements CacheProvider {
  
  /**
   * 默认字典名字
   */
  private String DEFAULT_DICT_NAME = "__default_dict__";
  
  LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
    // 设置缓存的最大容量
    .maximumSize(1000)
    // 设置缓存在写入 30 分钟后失效
    .expireAfterWrite(30, TimeUnit.MINUTES)
    // 缓存被访问后,一定时间后失效
    .expireAfterAccess(30, TimeUnit.MINUTES)
    // 设置并发级别为10
    .concurrencyLevel(10)
    // 开启缓存统计
    .recordStats()
    .build(new CacheLoader<String, Object>() {
      @Override
      public Object load(String key) throws Exception {
        return null;
      }
    });
  
  @Override
  public Set<String> keys(String pattern) {
    String regex = pattern.replace("?", "[0-9a-z]").replace("*", "[0-9a-z]{0,}");
    
    Set<String> result = new HashSet<String>();
    
    Set<String> keys = cache.asMap().keySet();
    
    // 遍历 keys 中的键
    for (String key : keys) {
      if (key.matches(regex)) {
        result.add(key);
      }
    }
    
    return result;
  }
  
  @Override
  public boolean contains(String name) {
    return cache.asMap().containsKey(name);
  }
  
  @Override
  public Object get(String name) {
    return cache.getIfPresent(name);
  }
  
  @Override
  public void set(String name, Object value) {
    cache.put(name, value);
  }
  
  @Override
  public void set(String name, Object value, long minutes) {
    cache.put(name, value);
  }
  
  @Override
  public void delete(String name) {
    cache.invalidate(name);
  }
  
  @Override
  public void deleteByPattern(String pattern) {
    String regex = pattern.replace("?", "[0-9a-z]").replace("*", "[0-9a-z]{0,}");
    
    Set<String> keys = cache.asMap().keySet();
    
    // 遍历 map 中的键
    for (String key : keys) {
      if (key.matches(regex)) {
        cache.invalidate(key);
      }
    }
    
    keys = dicts.asMap().keySet();
    
    // 遍历 dicts 中的键
    for (String key : keys) {
      if (key.matches(regex)) {
        dicts.invalidate(key);
      }
    }
    
  }
  
  // -------------------------------------------------------
  // 字典
  // -------------------------------------------------------
  
  LoadingCache<String, ConcurrentMap<String, Object>> dicts = CacheBuilder.newBuilder()
    // 设置缓存的最大容量
    .maximumSize(1000)
    // 设置缓存在写入 30 分钟后失效
    .expireAfterWrite(30, TimeUnit.MINUTES)
    // 缓存被访问后,一定时间后失效
    .expireAfterAccess(30, TimeUnit.MINUTES)
    // 设置并发级别为10
    .concurrencyLevel(10)
    // 开启缓存统计
    .recordStats()
    .build(new CacheLoader<String, ConcurrentMap<String, Object>>() {
      @Override
      public ConcurrentMap<String, Object> load(String key) throws Exception {
        return null;
      }
    });
  
  @Override
  public boolean contains(String dict, String name) {
    if (dicts.asMap().containsKey(dict)) {
      return dicts.getIfPresent(dict).containsKey(name);
    }
    return false;
  }
  
  @Override
  public Object get(String dict, String name) {
    if (dicts.asMap().containsKey(dict)) {
      return dicts.getIfPresent(dict).get(name);
    }
    return null;
  }
  
  @Override
  public void set(String dict, String name, Object value) {
    if (dicts.asMap().containsKey(dict)) {
      dicts.getIfPresent(dict).put(name, value);
    }
  }
  
  @Override
  public void delete(String dict, String name) {
    if (dicts.asMap().containsKey(dict)) {
      dicts.getIfPresent(dict).remove(name);
    }
  }
  
  // -------------------------------------------------------
  // 列表
  // -------------------------------------------------------
  
  LoadingCache<String, List> lists = CacheBuilder.newBuilder()
    // 设置缓存的最大容量
    .maximumSize(1000)
    // 设置缓存在写入 30 分钟后失效
    .expireAfterWrite(30, TimeUnit.MINUTES)
    // 缓存被访问后,一定时间后失效
    .expireAfterAccess(30, TimeUnit.MINUTES)
    // 设置并发级别为10
    .concurrencyLevel(10)
    // 开启缓存统计
    .recordStats()
    .build(new CacheLoader<String, List>() {
      @Override
      public List load(String key) throws Exception {
        return null;
      }
    });
  
  @Override
  public long listLeftSetAll(String key, List value) {
    lists.put(key, value);
    return 0;
  }
  
  @Override
  public List listGetRange(String key, long start, long end) {
    if (lists.asMap().containsKey(key)) {
      return lists.getIfPresent(key).subList((int) start, (int) end);
    }
    return null;
  }
}
