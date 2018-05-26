package com.x3platform.digitalNumber.services;

import java.util.*;

import com.x3platform.digitalNumber.models.*;

/**
 */
public interface IDigitalNumberService {

  /**
   * 索引
   *
   * @param name
   * @return
   */
  DigitalNumberInfo getItem(String name);

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------


  /**
   * 保存记录
   *
   * @param param 实例<see cref="DigitalNumberInfo"/>详细信息
   * @return <see cref="DigitalNumberInfo"/> 实例详细信息
   */
  DigitalNumberInfo Save(DigitalNumberInfo param);

  /**
   * 删除记录
   *
   * @param name 名称
   */
  void Delete(String name);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#endregion

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#region 函数:FindOne(string name)

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return 返回一个<see cref="DigitalNumberInfo"/> 实例的详细信息
   */
  DigitalNumberInfo FindOne(String name);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#region 函数:FindAll()

  /**
   * 查询所有相关记录
   *
   * @return 返回所有<see cref="DigitalNumberInfo"/> 实例的详细信息
   */
  List<DigitalNumberInfo> FindAll();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#region 函数:FindAll(DataQuery query)

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有<see cref="DigitalNumberInfo"/> 实例的详细信息
   */
  // List<DigitalNumberInfo> FindAll(DataQuery query);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例
   */
  // List<DigitalNumberInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录.
   *
   * @param name 名称
   * @return 布尔值
   */
  boolean IsExistName(String name);

  /**
   * 生成数字编码
   *
   * @param name 规则名称
   * @return 数字编码
   */
  String Generate(String name);

  /**
   * 根据前缀生成数字编码
   *
   * @param entityTableName 实体数据表
   * @param prefixCode      前缀编号
   * @param expression      规则表达式
   * @return 数字编码
   */
  String GenerateCodeByPrefixCode(String entityTableName, String prefixCode, String expression);

  /**
   * 根据前缀生成数字编码
   *
   * @param command         通用SQL命令对象
   * @param entityTableName 实体数据表
   * @param prefixCode      前缀编号
   * @param expression      规则表达式
   * @return 数字编码
   */
  // String GenerateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode, String expression);

  /**
   * 根据类别标识成数字编码
   *
   * @param entityTableName         实体数据表
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId        实体类别标识
   * @param expression              规则表达式
   * @return 数字编码
   */
  String GenerateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, String expression);

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
  // String GenerateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId, String expression);
}
