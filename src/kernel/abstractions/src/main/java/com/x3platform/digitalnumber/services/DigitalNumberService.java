package com.x3platform.digitalnumber.services;

import com.x3platform.data.DataQuery;
import com.x3platform.data.GenericSqlCommand;
import com.x3platform.digitalnumber.models.DigitalNumber;
import java.util.List;

/**
 */
public interface DigitalNumberService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------


  /**
   * 保存记录
   *
   * @param param 实例 {@link DigitalNumber}详细信息
   * @return {@link DigitalNumber} 实例详细信息
   */
  DigitalNumber save(DigitalNumber param);

  /**
   * 删除记录
   *
   * @param name 名称
   */
  void delete(String name);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return 一个 {@link DigitalNumber} 实例的详细信息
   */
  DigitalNumber findOne(String name);

  /**
   * 查询所有相关记录
   *
   * @return 所有 {@link DigitalNumber} 实例的详细信息
   */
  List<DigitalNumber> findAll();

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link DigitalNumber} 实例的详细信息
   */
  List<DigitalNumber> findAll(DataQuery query);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录.
   *
   * @param name 名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 生成数字编码
   *
   * @param name 规则名称
   * @return 数字编码
   */
  String generate(String name);

  /**
   * 根据前缀生成数字编码
   *
   * @param entityTableName 实体数据表
   * @param prefixCode 前缀编号
   * @param expression 规则表达式
   * @return 数字编码
   */
  String generateCodeByPrefixCode(String entityTableName, String prefixCode, String expression);

  /**
   * 根据前缀生成数字编码
   *
   * @param command 通用SQL命令对象
   * @param entityTableName 实体数据表
   * @param prefixCode 前缀编号
   * @param expression 规则表达式
   * @return 数字编码
   */
  String generateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode,
    String expression);

  /**
   * 根据类别标识成数字编码
   *
   * @param entityTableName 实体数据表
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId 实体类别标识
   * @param expression 规则表达式
   * @return 数字编码
   */
  String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId,
    String expression);

  /**
   * 根据类别标识成数字编码
   *
   * @param command 通用 SQL 命令对象
   * @param entityTableName 实体数据表
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId 实体类别标识
   * @param expression 规则表达式
   * @return 数字编码
   */
  String generateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName,
    String entityCategoryId, String expression);
}
