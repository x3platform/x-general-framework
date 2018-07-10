package com.x3platform.cacheBuffer;

import java.util.*;

import com.x3platform.cacheBuffer.configuration.*;

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
  private ICacheProvider cacheProvider;

  private CachingManager() {
    Reload();
  }

  /**
   * 重新加载
   */
  public void Reload() {
    try {
      Class objectType = Class.forName(CacheBufferConfigurationView.getInstance().getCacheProvider());

      this.cacheProvider = (ICacheProvider) objectType.newInstance();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
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
  public static boolean Contains(String name) {
    return getInstance().cacheProvider.Contains(name);
  }

  /**
   * @param name
   * @return
   */
  public static Object Get(String name) {
    return getInstance().cacheProvider.Get(name);
  }

  /**
   * @param name
   * @return
   */
  public static void Set(String name, Object value) {
    getInstance().cacheProvider.Set(name, value);
  }

  /**
   * 写入缓存项
   *
   * @param name  名称
   * @param value 值
   */
  public static void Add(String name, Object value) {
    getInstance().cacheProvider.Add(name, value);
  }

  /**
   * 写入缓存项
   *
   * @param name    名称
   * @param value   值
   * @param minutes 有效分钟
   */
  public static void Add(String name, Object value, int minutes) {
    getInstance().cacheProvider.Add(name, value, minutes);
  }

  /**
   * 删除缓存项
   *
   * @param name 名称
   */
  public static void Remove(String name) {
    getInstance().cacheProvider.Remove(name);
  }
}
