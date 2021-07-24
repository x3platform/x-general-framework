package com.x3platform.cachebuffer.redis.services.impl;

import com.x3platform.cachebuffer.redis.FastJsonRedisSerializer;
import com.x3platform.cachebuffer.redis.services.RedisTemplateService;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ruanyu
 */
public class RedisTemplateServiceImpl implements RedisTemplateService {
  
  private RedisTemplate<Object, Object> redisTemplate;
  
  @Autowired(required = false)
  public void setRedisTemplate(RedisTemplate redisTemplate) {
    // 设置键（key）的序列化采用 StringRedisSerializer。
    RedisSerializer stringSerializer = new StringRedisSerializer();
    
    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setHashKeySerializer(stringSerializer);
    
    // 设置值（value）的序列化采用 FastJsonRedisSerializer。
    
    FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
    
    redisTemplate.setValueSerializer(fastJsonRedisSerializer);
    redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
    
    redisTemplate.afterPropertiesSet();
    
    this.redisTemplate = redisTemplate;
  }
  
  /**
   * 获取键信息
   *
   * @return 缓存对象的键信息
   */
  @Override
  public Set<String> keys() {
    return keys("*");
  }
  
  /**
   * 获取键信息
   *
   * @return 缓存对象的键信息
   */
  @Override
  public Set<String> keys(String pattern) {
    Set<String> result = new HashSet<String>();
    
    Set<Object> keys = redisTemplate.keys(pattern);
    
    // 遍历 keys 中的键
    for (Object key : keys) {
      result.add(key.toString());
    }
    
    return result;
  }
  
  /**
   * 是否包含相关键值的缓存记录
   *
   * @param key 键
   * @return true 或 false
   */
  @Override
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  /**
   * 获取缓存记录
   *
   * @param key 键
   * @return 值
   */
  @Override
  public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }
  
  /**
   * 设置缓存记录
   *
   * @param key   键
   * @param value 值
   */
  @Override
  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }
  
  /**
   * 设置缓存记录
   *
   * @param key   键
   * @param value 值
   * @param time  时间(分钟) time 要大于 0 如果 time 小于等于 0 将设置无限期
   */
  @Override
  public void set(String key, Object value, long time) {
    if (time > 0) {
      redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    } else {
      set(key, value);
    }
  }
  
  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }
  
  @Override
  public void deleteByPattern(String pattern) {
    Set<Object> keys = redisTemplate.keys(pattern);
    redisTemplate.delete(keys);
  }
  
  /**
   * 查看哈希表 hKey 中，给定域 hashKey 是否存在。
   *
   * @param key     哈希表名称
   * @param hashKey 域的hashKey
   * @return 如果哈希表含有给定域，返回 1 。如果哈希表不含有给定域，或 hashKey 不存在，返回 0 。 huangxueqian 创建于  2018年12月9日 上午10:50:44
   */
  @Override
  public boolean hashHasKey(String key, String hashKey) {
    return redisTemplate.opsForHash().hasKey(key, hashKey);
  }
  
  /**
   * TODO 查询哈希表 hKey 中给定域 hashKey 的值。
   *
   * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。 huangxueqian 创建于  2018年12月9日 上午11:02:08
   */
  @Override
  public Object hashGet(String tableName, String hashKey) {
    return redisTemplate.opsForHash().get(tableName, hashKey);
  }
  
  /**
   * 获取所有的散列值
   */
  @Override
  public Map<Object, Object> hashGetAll(String key) {
    return redisTemplate.opsForHash().entries(key);
  }
  
  /**
   * 哈希表 hKey 中的域 hashKey 的值加上增量 delta 。 <p> 增量也可以为负数，相当于对给定域进行减法操作。  如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果域 field
   * 不存在，那么在执行命令前，域的值被初始化为 0 。  对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
   *
   * @return 执行 HINCRBY 命令之后，哈希表 hKey 中域 hashKey 的值。
   */
  @Override
  public Long hashIncrement(String hKey, String hashKey, Long delta) {
    return redisTemplate.opsForHash().increment(hKey, hashKey, delta);
  }
  
  /**
   * 哈希表 hKey 中的域 hashKey 的值加上浮点值 增量 delta 。
   *
   * @return 执行 HINCRBY 命令之后，哈希表 hKey 中域 hashKey 的值。
   */
  @Override
  public Double hashIncrement(String hKey, String hashKey, Double delta) {
    return redisTemplate.opsForHash().increment(hKey, hashKey, delta);
  }
  
  /**
   * 添加键值对到哈希表 key 中
   *
   * @param key 哈希表名称
   */
  @Override
  public void hashPut(String key, Object hashKey, Object hashValue) {
    redisTemplate.opsForHash().put(key, hashKey, hashValue);
  }
  
  /**
   * TODO 获取哈希表key中的所有域
   *
   * @return huangxueqian 创建于  2018年12月9日 上午11:48:18
   */
  @Override
  public Set<Object> hashGetKeys(String key) {
    return redisTemplate.opsForHash().keys(key);
  }
  
  /**
   * 获取散列中的字段数量
   */
  @Override
  public Long hashGetSize(String key) {
    return redisTemplate.opsForHash().size(key);
  }
  
  /**
   * 获取哈希中的所有值
   */
  @Override
  public List<Object> hashGetValues(String key) {
    return redisTemplate.opsForHash().values(key);
  }
  
  /**
   * 删除一个或多个哈希字段
   *
   * @return 返回值为被成功删除的数量
   */
  @Override
  public Long hashDelete(String key, Object... hashKeys) {
    return redisTemplate.opsForHash().delete(key, hashKeys);
  }
  
  /**
   * 从右向左存压栈
   *
   * @param value huangxueqian 创建于  2018年12月9日 下午12:21:06
   */
  @Override
  public void listLeftPushList(String key, String value) {
    redisTemplate.opsForList().leftPush(key, value);
  }
  
  /**
   * TODO 从右出栈
   *
   * @return huangxueqian 创建于  2018年12月9日 下午12:21:37
   */
  @Override
  public Object listLeftPopList(String key) {
    return redisTemplate.opsForList().leftPop(key);
  }
  
  /**
   * TODO 从左向右存压栈
   *
   * @param key huangxueqian 创建于  2018年12月9日 下午12:05:25
   */
  @Override
  public Long listSize(String key) {
    return redisTemplate.opsForList().size(key);
  }
  
  /**
   * TODO 从左出栈
   *
   * @return huangxueqian 创建于  2018年12月9日 下午1:56:40
   */
  @Override
  public List<Object> listRangeList(String key, Long start, Long end) {
    return redisTemplate.opsForList().range(key, start, end);
  }
  
  /**
   * TODO 集合list的长度
   *
   * @return huangxueqian 创建于  2018年12月9日 下午12:08:05
   */
  @Override
  public Long listRemoveFromList(String key, long i, Object value) {
    return redisTemplate.opsForList().remove(key, i, value);
  }
  
  /**
   * TODO 查询列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
   *
   * @return huangxueqian 创建于  2018年12月9日 下午12:09:56
   */
  @Override
  public Object listIndexFromList(String key, long index) {
    return redisTemplate.opsForList().index(key, index);
  }
  
  /**
   * 移除 key 中值为 value 的 i 个,返回删除的个数；如果没有这个元素则返回0
   */
  @Override
  public void listSetValueToList(String key, long index, String value) {
    redisTemplate.opsForList().set(key, index, value);
  }
  
  /**
   * 根据下标查询list中某个值
   */
  @Override
  public void listTrimByRange(String key, Long start, Long end) {
    redisTemplate.opsForList().trim(key, start, end);
  }
  
  /**
   * 根据下标设置 value
   *
   * @param key
   * @param value
   */
  @Override
  public void listRightPushList(String key, String value) {
    redisTemplate.opsForList().rightPush(key, value);
  }
  
  /**
   * TODO 裁剪(删除), 删除 除了[start,end]以外的所有元素
   *
   * @param key huangxueqian 创建于  2018年12月9日 下午12:17:23
   */
  @Override
  public Object listRightPopList(String key) {
    return redisTemplate.opsForList().rightPop(key);
  }
  
  /**
   * TODO 将一个或多个 value 元素加入到集合 key 当中，已经存在于集合的 value 元素将被忽略。
   *
   * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。 huangxueqian 创建于  2018年12月9日 下午1:59:05
   */
  @Override
  public Long setAddSetMap(String key, String... values) {
    return redisTemplate.opsForSet().add(key, values);
  }
  
  /**
   * TODO 获取set集合的大小
   *
   * @return huangxueqian 创建于  2018年12月9日 下午2:01:38
   */
  @Override
  public Long setGetSizeForSetMap(String key) {
    return redisTemplate.opsForSet().size(key);
  }
  
  /**
   * TODO 获取set集合中的元素
   *
   * @return huangxueqian 创建于  2018年12月9日 下午2:02:40
   */
  @Override
  public Set<Object> setGetMemberOfSetMap(String key) {
    return redisTemplate.opsForSet().members(key);
  }
  
  /**
   * TODO 检查元素是不是set集合中的
   *
   * @return huangxueqian 创建于  2018年12月9日 下午2:03:37
   */
  @Override
  public Boolean setCheckIsMemberOfSet(String key, Object o) {
    return redisTemplate.opsForSet().isMember(key, o);
  }
  
  /**
   * TODO 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key
   * value 一样。
   *
   * @return 追加 value 之后， key 中字符串的长度 huangxueqian 创建于  2018年12月9日 下午2:14:03
   */
  @Override
  public Integer stringAppendString(String key, String value) {
    return redisTemplate.opsForValue().append(key, value);
  }
  
  /**
   * TODO 获取指定键的值
   *
   * @return huangxueqian 创建于  2018年12月9日 下午2:15:09
   */
  @Override
  public Object stringGetStringByKey(String key) {
    return redisTemplate.opsForValue().get(key);
  }
  
  /**
   * TODO 获取存储在键上的字符串的子字符串
   *
   * @return 截取后的子字符串 huangxueqian 创建于  2018年12月9日 下午2:16:11
   */
  @Override
  public String stringGetSubStringFromString(String key, long start, long end) {
    return redisTemplate.opsForValue().get(key, start, end);
  }
  
  /**
   * TODO 将键的整数值按给定的长整型数值增加
   *
   * @return 返回增长后的结果值 huangxueqian 创建于  2018年12月9日 下午2:18:00
   */
  @Override
  public Long stringIncrementLongString(String key, Long delta) {
    return redisTemplate.opsForValue().increment(key, delta);
  }
  
  /**
   * TODO 将键的整数值按给定的浮点型数值增加
   *
   * @return 返回增长后的结果值 huangxueqian 创建于  2018年12月9日 下午2:18:24
   */
  @Override
  public Double stringIncrementDoubleString(String key, Double delta) {
    return redisTemplate.opsForValue().increment(key, delta);
  }
  
  /**
   * TODO 设置指定键的值
   *
   * @param value huangxueqian 创建于  2018年12月9日 下午2:19:28
   */
  @Override
  public void stringSetString(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }
  
  /**
   * TODO 设置键的字符串值并返回其旧值
   *
   * @return huangxueqian 创建于  2018年12月9日 下午2:20:07
   */
  @Override
  public Object stringGetAndSet(String key, String value) {
    return redisTemplate.opsForValue().getAndSet(key, value);
  }
  
  /**
   * 使用键和到期时间来设置值, 时间单位默认为毫秒
   */
  @Override
  public void stringSetValueAndExpireTime(String key, String value, long timeout) {
    redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
  }
  
  
  /**
   * redis  List 形式数据设置
   *
   * @param key
   * @param object
   * @return
   */
  
  @Override
  public long listLeftSetAll(Object key, Collection object) {
    return redisTemplate.opsForList().leftPushAll(key, object);
  }
  
  /**
   * redis List 形式数据获取
   *
   * @param key
   * @param start
   * @param end
   * @return
   */
  @Override
  public List<Object> listGetRange(Object key, long start, long end) {
    return redisTemplate.opsForList().range(key, start, end);
  }
}
