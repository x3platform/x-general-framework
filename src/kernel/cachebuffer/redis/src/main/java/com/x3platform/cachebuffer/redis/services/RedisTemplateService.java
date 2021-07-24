package com.x3platform.cachebuffer.redis.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ruanyu
 */
public interface RedisTemplateService {
  
  /**
   * 获取键信息
   *
   * @return 缓存对象的键信息
   */
  Set<String> keys();
  
  /**
   * 获取键信息
   *
   * @return 缓存对象的键信息
   */
  Set<String> keys(String pattern);
  
  /**
   * 是否包含相关键值的缓存记录
   *
   * @param key 键
   * @return true 或 false
   */
  boolean hasKey(String key);
  
  /**
   * 获取缓存记录
   *
   * @param key 键
   * @return 值
   */
  Object get(String key);
  
  /**
   * 设置缓存记录
   *
   * @param key   键
   * @param value 值
   */
  void set(String key, Object value);
  
  /**
   * 设置缓存记录
   *
   * @param key   键
   * @param value 值
   * @param time  时间(分钟) time 要大于 0 如果 time 小于等于 0 将设置无限期
   */
  void set(String key, Object value, long time);
  
  /**
   * 删除键为 key 的缓存
   *
   * @param key
   */
  void delete(String key);
  
  /**
   * 删除键为指定模式的缓存
   *
   * @param pattern 模式
   */
  void deleteByPattern(String pattern);
  
  /**
   * 查看哈希表 hKey 中，给定域 hashKey 是否存在。
   *
   * @param hKey    哈希表名称
   * @param hashKey 域的hashKey
   * @return 如果哈希表含有给定域，返回 1 。如果哈希表不含有给定域，或 hashKey 不存在，返回 0 。 ning 创建于  2017年11月8日 上午10:50:44
   */
  boolean hashHasKey(String hKey, String hashKey);
  
  /**
   * 查询哈希表 hKey 中给定域 hashKey 的值。
   *
   * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。 ning 创建于  2017年11月8日 上午11:02:08
   */
  Object hashGet(String hKey, String hashKey);
  
  /**
   * 获取所有的散列值
   *
   * @return ning 创建于  2017年11月8日 上午11:57:33
   */
  Map<Object, Object> hashGetAll(String key);
  
  /**
   * TODO 哈希表 hKey 中的域 hashKey 的值加上增量 delta 。 <p> 增量也可以为负数，相当于对给定域进行减法操作。  如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果域
   * field 不存在，那么在执行命令前，域的值被初始化为 0 。  对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
   *
   * @return 执行 HINCRBY 命令之后，哈希表 hKey 中域 hashKey 的值。 ning 创建于  2017年11月8日 上午11:21:32
   */
  Long hashIncrement(String hKey, String hashKey, Long delta);
  
  /**
   * TODO 哈希表 hKey 中的域 hashKey 的值加上浮点值 增量 delta 。
   *
   * @return 执行 HINCRBY 命令之后，哈希表 hKey 中域 hashKey 的值。 ning 创建于  2017年11月8日 上午11:42:20
   */
  Double hashIncrement(String hKey, String hashKey, Double delta);
  
  /**
   * TODO 添加键值对到哈希表key中
   *
   * @param key
   * @param hashKey   Hash Key
   * @param hashValue Hash Value
   */
  void hashPut(String key, Object hashKey, Object hashValue);
  
  /**
   * TODO 获取哈希表key中的所有域
   *
   * @return ning 创建于  2017年11月8日 上午11:48:18
   */
  Set<Object> hashGetKeys(String key);
  
  /**
   * TODO 获取散列中的字段数量
   *
   * @return ning 创建于  2017年11月8日 上午11:50:34
   */
  Long hashGetSize(String key);
  
  /**
   * TODO 获取哈希中的所有值
   *
   * @return ning 创建于  2017年11月8日 上午11:51:12
   */
  List<Object> hashGetValues(String key);
  
  /**
   * TODO 删除一个或多个哈希字段
   *
   * @return 返回值为被成功删除的数量 ning 创建于  2017年11月8日 上午11:52:22
   */
  Long hashDelete(String key, Object... hashKeys);
  
  //================================================List================================================
  
  /**
   * TODO 从右向左存压栈
   *
   * @param value ning 创建于  2017年11月8日 下午12:21:06
   */
  void listRightPushList(String key, String value);
  
  /**
   * TODO 从右出栈
   *
   * @return ning 创建于  2017年11月8日 下午12:21:37
   */
  Object listRightPopList(String key);
  
  /**
   * TODO 从左向右存压栈
   *
   * @param value ning 创建于  2017年11月8日 下午12:05:25
   */
  void listLeftPushList(String key, String value);
  
  /**
   * TODO 从左出栈
   *
   * @return ning 创建于  2017年11月8日 下午1:56:40
   */
  Object listLeftPopList(String key);
  
  /**
   * TODO 集合list的长度
   *
   * @return ning 创建于  2017年11月8日 下午12:08:05
   */
  Long listSize(String key);
  
  /**
   * TODO 查询列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
   *
   * @return ning 创建于  2017年11月8日 下午12:09:56
   */
  List<Object> listRangeList(String key, Long start, Long end);
  
  /**
   * TODO 移除key中值为value的i个,返回删除的个数；如果没有这个元素则返回0
   *
   * @return ning 创建于  2017年11月8日 下午12:12:39
   */
  Long listRemoveFromList(String key, long i, Object value);
  
  /**
   * TODO 根据下标查询list中某个值
   *
   * @return ning 创建于  2017年11月8日 下午12:13:40
   */
  Object listIndexFromList(String key, long index);
  
  /**
   * TODO 根据下标设置value
   *
   * @param value ning 创建于  2017年11月8日 下午12:15:28
   */
  void listSetValueToList(String key, long index, String value);
  
  /**
   * TODO 裁剪(删除), 删除 除了[start,end]以外的所有元素
   *
   * @param end ning 创建于  2017年11月8日 下午12:17:23
   */
  void listTrimByRange(String key, Long start, Long end);
  
  //================================================Set================================================
  
  /**
   * TODO 将一个或多个 value 元素加入到集合 key 当中，已经存在于集合的 value 元素将被忽略。
   *
   * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。 ning 创建于  2017年11月8日 下午1:59:05
   */
  Long setAddSetMap(String key, String... values);
  
  /**
   * TODO 获取set集合的大小
   *
   * @return ning 创建于  2017年11月8日 下午2:01:38
   */
  Long setGetSizeForSetMap(String key);
  
  /**
   * TODO 获取set集合中的元素
   *
   * @return ning 创建于  2017年11月8日 下午2:02:40
   */
  Set<Object> setGetMemberOfSetMap(String key);
  
  /**
   * TODO 检查元素是不是set集合中的
   *
   * @return ning 创建于  2017年11月8日 下午2:03:37
   */
  Boolean setCheckIsMemberOfSet(String key, Object o);
  
  //================================================String================================================
  
  /**
   * TODO 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key
   * value 一样。
   *
   * @return 追加 value 之后， key 中字符串的长度 ning 创建于  2017年11月8日 下午2:14:03
   */
  Integer stringAppendString(String key, String value);
  
  /**
   * TODO 获取指定键的值
   *
   * @return ning 创建于  2017年11月8日 下午2:15:09
   */
  Object stringGetStringByKey(String key);
  
  /**
   * TODO 获取存储在键上的字符串的子字符串
   *
   * @return 截取后的子字符串 ning 创建于  2017年11月8日 下午2:16:11
   */
  String stringGetSubStringFromString(String key, long start, long end);
  
  /**
   * TODO 将键的整数值按给定的长整型数值增加
   *
   * @return 返回增长后的结果值 ning 创建于  2017年11月8日 下午2:18:00
   */
  Long stringIncrementLongString(String key, Long delta);
  
  /**
   * TODO 将键的整数值按给定的浮点型数值增加
   *
   * @return 返回增长后的结果值 ning 创建于  2017年11月8日 下午2:18:24
   */
  Double stringIncrementDoubleString(String key, Double delta);
  
  /**
   * TODO 设置指定键的值
   *
   * @param value ning 创建于  2017年11月8日 下午2:19:28
   */
  void stringSetString(String key, String value);
  
  /**
   * TODO 设置键的字符串值并返回其旧值
   *
   * @return ning 创建于  2017年11月8日 下午2:20:07
   */
  Object stringGetAndSet(String key, String value);
  
  /**
   * TODO 使用键和到期时间来设置值,时间单位默认为毫秒
   */
  void stringSetValueAndExpireTime(String key, String value, long timeout);
  
  long listLeftSetAll(Object key, Collection object);
  
  List<Object> listGetRange(Object key, long start, long end);
}
