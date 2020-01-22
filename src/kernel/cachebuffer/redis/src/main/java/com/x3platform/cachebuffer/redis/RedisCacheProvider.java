package com.x3platform.cachebuffer.redis;

import com.x3platform.cachebuffer.CacheProvider;
import com.x3platform.cachebuffer.redis.services.RedisTemplateService;

/**
 * Redis 缓存提供器
 *
 * @author ruanyu
 */
public class RedisCacheProvider implements CacheProvider {

  // 默认字典名字
  private String DEFAULT_DICT_NAME = "__default_dict__";

  RedisTemplateService service = RedisContext.getInstance().getRedisTemplateService();

  @Override
  public boolean contains(String name) {
    return service.hasKey(name);
  }

  @Override
  public Object get(String name) {
    return service.get(name);
  }

  @Override
  public void set(String name, Object value) {
    service.set(name, value);
  }

  @Override
  public void set(String name, Object value, int minutes) {
    service.set(name, value, minutes);
  }

  @Override
  public void delete(String name) {
    service.delete(name);
  }

  @Override
  public void deleteByPattern(String pattern) {
    service.deleteByPattern(pattern);
  }

  @Override
  public boolean contains(String dict, String name) {
    return service.hashHasKey(dict, name);
  }

  @Override
  public Object get(String dict, String name) {
    return service.hashGet(dict, name);
  }

  @Override
  public void set(String dict, String name, Object value) {
    service.hashPut(dict, name, value);
  }

  @Override
  public void delete(String dict, String name) {
    service.hashDelete(dict, name);
  }
}
