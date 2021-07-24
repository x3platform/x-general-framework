package com.x3platform.digitalnumber.services.impl;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.x3platform.RefObject;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.data.GenericSqlCommand;
import com.x3platform.digitalnumber.DigitalNumberScript;
import com.x3platform.digitalnumber.configuration.DigitalNumberConfigurationView;
import com.x3platform.digitalnumber.mappers.DigitalNumberMapper;
import com.x3platform.digitalnumber.models.DigitalNumber;
import com.x3platform.digitalnumber.services.DigitalNumberService;
import com.x3platform.sessions.Ticket;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

public class DigitalNumberServiceImpl implements DigitalNumberService {

  private static final String CACHE_KEY_DIGITALNUMBER_NAME_PREFIX = "x3platform:digitalnumber:name:";

  @Autowired
  private DigitalNumberMapper provider = null;

  public DigitalNumberServiceImpl() {
  }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity DigitalNumber 实例详细信息
   * @return DigitalNumber 实例详细信息
   */
  @Override
  public DigitalNumber save(DigitalNumber entity) {
    String name = entity.getName();

    // 添加缓存项
    if (!StringUtil.isNullOrEmpty(name)) {
      String key = CACHE_KEY_DIGITALNUMBER_NAME_PREFIX + name;
      CachingManager.set(key, entity);
    }

    // 更新数据库信息
    if (provider.isExistName(name)) {
      provider.update(entity);
    } else {
      provider.insert(entity);
    }

    return entity;
  }

  /**
   * 删除记录
   *
   * @param name 名称
   */
  @Override
  public void delete(String name) {
    // 删除缓存项
    if (!StringUtil.isNullOrEmpty(name)) {
      String key = CACHE_KEY_DIGITALNUMBER_NAME_PREFIX + name;
      CachingManager.delete(key);
    }

    provider.delete(name);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param name DigitalNumber Id号
   * @return 返回一个{@link DigitalNumber} 实例的详细信息
   */
  @Override
  public DigitalNumber findOne(String name) {
    // 过滤空值
    if (StringUtil.isNullOrEmpty(name)) {
      return null;
    }

    String key = CACHE_KEY_DIGITALNUMBER_NAME_PREFIX + name;

    if (CachingManager.contains(key)) {
      return (DigitalNumber) CachingManager.get(key);
    } else {
      DigitalNumber entity = provider.findOne(name);

      if (entity != null && !CachingManager.contains(key)) {
        CachingManager.set(key, entity);
      }

      return entity;
    }
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有{@link DigitalNumber} 实例的详细信息
   */
  @Override
  public List<DigitalNumber> findAll() {
    DataQuery query = new DataQuery();
    query.setLength(1000);
    return findAll(query);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有{@link DigitalNumber} 实例的详细信息
   */
  @Override
  public List<DigitalNumber> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录.
   *
   * @param name 名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  private Object lockObject = new Object();

  /**
   * 生成数字编码
   *
   * @param name 规则名称
   * @return 数字编码
   */
  @Override
  public String generate(String name) {
    List<String> list = generates(name, 1);

    return list.size() > 0 ? list.get(0) : null;
  }

  @Override
  public List<String> generates(String name, int length) {
    List<String> results = new ArrayList<>();

    synchronized (lockObject) {
      DigitalNumber entity = findOne(name);

      if (entity == null) {
        throw new RuntimeException(StringUtil.format("未找到相关配置信息，请联系管理员配置相关编号 [{}] 参数信息。", name));
      } else {
        int seed = entity.getSeed();
        RefObject<Integer> refSeed = new RefObject<Integer>(seed);
        while (length > 0) {
          String result = DigitalNumberScript.runScript(entity.getExpression(), entity.getModifiedDate(), refSeed);
          entity.setModifiedDate(LocalDateTime.now());
          results.add(result);
          length--;
        }

        seed = refSeed.value;

        entity.setSeed(seed);

        // 忽略不需要自增的编号和更新时间的编号
        String ignoreIncrementSeed = DigitalNumberConfigurationView.getInstance().getIgnoreIncrementSeed();

        if (!(ignoreIncrementSeed.contains(entity.getName()) || entity.getSeed() == -1)) {
          // 保存信息
          save(entity);
        }

        return results;
      }
    }
  }

  /**
   * 根据前缀生成数字编码
   *
   * @param entityTableName 实体数据表
   * @param prefixCode      前缀编号
   * @param expression      规则表达式
   * @return 数字编码
   */
  @Override
  public String generateCodeByPrefixCode(String entityTableName, String prefixCode, String expression) {
    // 获取前缀
    String prefix = DigitalNumberScript.runPrefixScript(expression, prefixCode.toUpperCase(), LocalDateTime.now());

    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      // code = provider.generateCodeByPrefixCode(entityTableName, prefixCode, expression);

      // 根据前缀信息查询当前最大的编号
      // Dictionary<string, object> args = new Dictionary<string, object>();

      // args.Add("EntityTableName", entityTableName);
      // args.Add("Prefix", prefix);

      // int seed = Convert.ToInt32(this.ibatisMapper.QueryForObject(StringHelper.ToProcedurePrefix(string.Format("{0}_GetMaxSeedByPrefix", tableName)), args));
      int seedValue = provider.getMaxSeedByPrefix(entityTableName, prefix);

      RefObject<Integer> seed = new RefObject<>(seedValue);

      code = DigitalNumberScript.runScript(expression, prefixCode, LocalDateTime.now(), seed);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }

  /**
   * 根据前缀生成数字编码
   *
   * @param command         通用 SQL 命令对象
   * @param entityTableName 实体数据表
   * @param prefixCode      前缀编号
   * @param expression      规则表达式
   * @return 数字编码
   */
  @Override
  public String generateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode,
                                         String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      // code = this.provider.generateCodeByPrefixCode(command, entityTableName, prefixCode, expression);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }

  /**
   * 根据类别标识成数字编码
   *
   * @param entityTableName         实体数据表
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId        实体类别标识
   * @param expression              规则表达式
   * @return 数字编码
   */
  @Override
  public String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName,
                                         String entityCategoryId, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      // code = provider.generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, expression);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }

  /**
   * 根据类别标识成数字编码
   *
   * @param command                 通用SQL命令对象
   * @param entityTableName         实体数据表
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId        实体类别标识
   * @param expression              规则表达式
   * @return 数字编码
   */
  @Override
  public String generateCodeByCategoryId(GenericSqlCommand command, String entityTableName,
                                         String entityCategoryTableName, String entityCategoryId, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      // code = this.provider
      //  .generateCodeByCategoryId(command, entityTableName, entityCategoryTableName, entityCategoryId, expression);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }
}
