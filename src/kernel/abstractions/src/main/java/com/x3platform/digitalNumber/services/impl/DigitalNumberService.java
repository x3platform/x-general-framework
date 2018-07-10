package com.x3platform.digitalNumber.services.impl;

import java.util.*;

import com.x3platform.RefObject;
import com.x3platform.SpringContext;
import com.x3platform.data.*;
import com.x3platform.digitalNumber.DigitalNumberScript;
import com.x3platform.util.*;

import com.x3platform.digitalNumber.configuration.DigitalNumberConfiguration;
import com.x3platform.digitalNumber.configuration.DigitalNumberConfigurationView;
import com.x3platform.digitalNumber.mappers.DigitalNumberMapper;
import com.x3platform.digitalNumber.models.*;
import com.x3platform.digitalNumber.services.IDigitalNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DigitalNumberService implements IDigitalNumberService {
  @Autowired
  private DigitalNumberConfiguration configuration = null;

  @Autowired
  private DigitalNumberMapper provider = null;

  // public DigitalNumberService() {
    // this.configuration = DigitalNumberConfigurationView.getInstance().getConfiguration();

  //  this.provider = SpringContext.getBean(DigitalNumberMapper.class);
  // }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param <see cref="DigitalNumberInfo"/> 实例详细信息
   * @return <see cref="DigitalNumberInfo"/> 实例详细信息
   */
  public final DigitalNumberInfo save(DigitalNumberInfo param) {
    if (!this.provider.isExistName(param.getName())) {
      this.provider.insert(param);
    } else {
      this.provider.update(param);
    }

    return param;
  }

  /**
   * 删除记录
   *
   * @param name 名称
   */
  public final void delete(String name) {
    this.provider.delete(name);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param name DigitalNumberInfo Id号
   * @return 返回一个<see cref="DigitalNumberInfo"/> 实例的详细信息
   */
  public final DigitalNumberInfo findOne(String name) {
    return this.provider.findOne(name);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有<see cref="DigitalNumberInfo"/> 实例的详细信息
   */
  public final List<DigitalNumberInfo> findAll() {
    DataQuery query = new DataQuery();
    query.setLength(1000);
    return findAll(query);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有<see cref="DigitalNumberInfo"/> 实例的详细信息
   */
  public final List<DigitalNumberInfo> findAll(DataQuery query) {
    return this.provider.findAll(query.getWhereSql(), query.getOrderBySql(), query.getLength());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @return 返回一个列表
   */
  // public final List<DigitalNumberInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 查询是否存在相关的记录.
   *
   * @param name 名称
   * @return 布尔值
   */
  public final boolean isExistName(String name) {
    return this.provider.isExistName(name);
  }

  private Object lockObject = new Object();

  /**
   * 生成数字编码
   *
   * @param name 规则名称
   * @return 数字编码
   */
  public final String generate(String name) {
    String result = null;

    synchronized (lockObject) {
      DigitalNumberInfo param = findOne(name);

      if (param == null) {
        throw new RuntimeException(String.format("未找到相关配置信息，请联系管理员配置相关编号【%1$s】参数信息。", name));
      } else {
        int seed = param.getSeed();

        // TODO 需要修改, 源码如下
        RefObject<Integer> refSeed = new RefObject<Integer>(seed);
        result = DigitalNumberScript.runScript(param.getExpression(), param.getModifiedDate(), refSeed);
        seed = refSeed.value;

        param.setSeed(seed);

        // 忽略不需要自增的编号和更新时间的编号
        String ignoreIncrementSeed = DigitalNumberConfigurationView.getInstance().getIgnoreIncrementSeed();

        if (!(ignoreIncrementSeed.indexOf(param.getName()) > -1 || param.getSeed() == -1)) {
          save(param);
        }

        return result;
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
  public final String generateCodeByPrefixCode(String entityTableName, String prefixCode, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      code = this.provider.generateCodeByPrefixCode(entityTableName, prefixCode, expression);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }

  /**
   * 根据前缀生成数字编码
   *
   * @param command         通用SQL命令对象
   * @param entityTableName 实体数据表
   * @param prefixCode      前缀编号
   * @param expression      规则表达式
   * @return 数字编码
   */
  /*public final String GenerateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (tangible.DotNetToJavaStringHelper.isNullOrEmpty(code)) {
      code = this.provider.GenerateCodeByPrefixCode(command, entityTableName, prefixCode, expression);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }*/

  /**
   * 根据类别标识成数字编码
   *
   * @param entityTableName         实体数据表
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId        实体类别标识
   * @param expression              规则表达式
   * @return 数字编码
   */
  public final String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      code = this.provider.generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, expression);

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
  /*
  public final String generateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (tangible.DotNetToJavaStringHelper.isNullOrEmpty(code)) {
      code = this.provider.GenerateCodeByCategoryId(command, entityTableName, entityCategoryTableName, entityCategoryId, expression);

      if (count++ > 10) {
        break;
      }
    }

    return code;
  }*/
}
