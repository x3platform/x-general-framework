package com.x3platform.digitalnumber.services.impl;

import com.x3platform.RefObject;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberScript;
import com.x3platform.digitalnumber.configuration.DigitalNumberConfigurationView;
import com.x3platform.digitalnumber.mappers.DigitalNumberMapper;
import com.x3platform.digitalnumber.models.DigitalNumber;
import com.x3platform.digitalnumber.services.DigitalNumberService;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DigitalNumberServiceImpl implements DigitalNumberService {

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
   * @param param DigitalNumber 实例详细信息
   * @return DigitalNumber 实例详细信息
   */
  @Override
  public DigitalNumber save(DigitalNumber param) {
    if (!provider.isExistName(param.getName())) {
      provider.insert(param);
    } else {
      provider.update(param);
    }

    return param;
  }

  /**
   * 删除记录
   *
   * @param name 名称
   */
  @Override
  public void delete(String name) {
    provider.delete(name);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param name DigitalNumber Id号
   * @return 返回一个<see       cref   =   "   DigitalNumber   "   /> 实例的详细信息
   */
  @Override
  public DigitalNumber findOne(String name) {
    return provider.findOne(name);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有<see       cref   =   "   DigitalNumber   "   /> 实例的详细信息
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
   * @return 返回所有<see       cref   =   "   DigitalNumber   "   /> 实例的详细信息
   */
  @Override
  public List<DigitalNumber> findAll(DataQuery query) {
    return provider.findAll(query.getWhereSql(), query.getOrderBySql(), query.getLength());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @return 返回一个列表
   */
  // public List<DigitalNumber> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

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
    String result = null;

    synchronized (lockObject) {
      DigitalNumber param = findOne(name);

      if (param == null) {
        throw new RuntimeException(StringUtil.format("未找到相关配置信息，请联系管理员配置相关编号 [{}] 参数信息。", name));
      } else {
        int seed = param.getSeed();

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
  @Override
  public String generateCodeByPrefixCode(String entityTableName, String prefixCode, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      code = provider.generateCodeByPrefixCode(entityTableName, prefixCode, expression);

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
  /*public String GenerateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode, String expression) {
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
  @Override
  public String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, String expression) {
    String code = "";

    int count = 0;

    // 有可能生成编号失败，所以 while。
    while (StringUtil.isNullOrEmpty(code)) {
      code = provider.generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, expression);

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
  public String generateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId, String expression) {
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
