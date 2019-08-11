package com.x3platform.cachebuffer;

import java.util.*;

import com.x3platform.cachebuffer.configuration.*;

/**
 * 缓存管理器
 */
public final class CachingManager {

  private static volatile CachingManager instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  private static CachingManager getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = new CachingManager();
        }
      }
    }

    return instance;
  }

  /**
   * 缓存提供器
   */
  private CacheProvider cacheProvider;

  private CachingManager() {
    reload();
  }

  /**
   * 重新加载
   */
  public void reload() {
    try {
      Class objectType = Class.forName(CacheBufferConfigurationView.getInstance().getCacheProvider());

      this.cacheProvider = (CacheProvider) objectType.newInstance();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    } catch (InstantiationException ex) {
      ex.printStackTrace();
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (RuntimeException ex) {
      throw ex;
    }

  }

  /**
   * 包含
   *
   * @param name 名称
   * @return
   */
  public static boolean contains(String name) {
    return getInstance().cacheProvider.Contains(name);
  }

  /**
   * @param name
   * @return
   */
  public static Object get(String name) {
    return getInstance().cacheProvider.Get(name);
  }

  /**
   * @param name
   * @return
   */
  public static void set(String name, Object value) {
    getInstance().cacheProvider.Set(name, value);
  }

  /**
   * 写入缓存项
   *
   * @param name  名称
   * @param value 值
   */
  public static void add(String name, Object value) {
    getInstance().cacheProvider.Add(name, value);
  }

  /**
   * 写入缓存项
   *
   * @param name    名称
   * @param value   值
   * @param minutes 有效分钟
   */
  public static void add(String name, Object value, int minutes) {
    getInstance().cacheProvider.Add(name, value, minutes);
  }

  /**
   * 删除缓存项
   *
   * @param name 名称
   */
  public static void remove(String name) {
    getInstance().cacheProvider.Remove(name);
  }
}
