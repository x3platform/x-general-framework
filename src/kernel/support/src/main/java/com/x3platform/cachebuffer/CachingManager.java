package com.x3platform.cachebuffer;

import com.x3platform.cachebuffer.configuration.CacheBufferConfigurationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存管理器
 *
 * @author ruanyu
 */
public final class CachingManager {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static volatile CachingManager sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   */
  private static CachingManager getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new CachingManager();
        }
      }
    }

    return sInstance;
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

      cacheProvider = (CacheProvider) objectType.newInstance();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
      logger.error(ex.toString());
    } catch (RuntimeException ex) {
      logger.error(ex.toString());
      throw ex;
    }
  }

  // -------------------------------------------------------
  // 值
  // -------------------------------------------------------

  /**
   * 是否包含缓存记录
   *
   * @param name 名称
   * @return true | false
   */
  public static boolean contains(String name) {
    return getInstance().cacheProvider.contains(name);
  }

  /**
   * 获取缓存对象
   *
   * @param name 名称
   * @return 对象
   */
  public static Object get(String name) {
    return getInstance().cacheProvider.get(name);
  }

  /**
   * 设置缓存对象
   *
   * @param name 名称
   * @param value 值
   */
  public static void set(String name, Object value) {
    getInstance().cacheProvider.set(name, value);
  }

  /**
   * 设置缓存对象
   *
   * @param name 名称
   * @param value 值
   * @param minutes 有效分钟
   */
  public static void set(String name, Object value, int minutes) {
    getInstance().cacheProvider.set(name, value, minutes);
  }

  /**
   * 删除缓存对象
   *
   * @param name 名称
   */
  public static void delete(String name) {
    getInstance().cacheProvider.delete(name);
  }

  /**
   * 删除缓存对象
   *
   * @param pattern 模式
   */
  public static void deleteByPattern(String pattern) {
    getInstance().cacheProvider.deleteByPattern(pattern);
  }

  // -------------------------------------------------------
  // 字典
  // -------------------------------------------------------

  /**
   * 是否包含缓存记录
   *
   * @param dict 字典
   * @return true | false
   */
  public static boolean contains(String dict, String name) {
    return getInstance().cacheProvider.contains(dict, name);
  }

  /**
   * 获取缓存记录
   *
   * @param name 名称
   * @return 缓存对象的详细信息
   */
  public static Object get(String dict, String name) {
    return getInstance().cacheProvider.get(dict, name);
  }

  /**
   * 设置缓存记录
   *
   * @param dict 字典
   * @param name 名称
   * @param value 值
   */
  public static void set(String dict, String name, Object value) {
    getInstance().cacheProvider.set(dict, name, value);
  }

  /**
   * 删除缓存记录
   *
   * @param dict 字典
   * @param name 名称
   */
  public static void delete(String dict, String name) {
    getInstance().cacheProvider.delete(dict, name);
  }
}
