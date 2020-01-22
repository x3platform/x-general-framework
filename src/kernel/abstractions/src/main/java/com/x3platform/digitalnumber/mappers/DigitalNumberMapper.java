package com.x3platform.digitalnumber.mappers;

import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.models.DigitalNumber;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DigitalNumberMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param {@link DigitalNumber} 实例的详细信息
   * @return 消息代码
   */
  int insert(DigitalNumber param);

  /**
   * 修改记录
   *
   * @param param {@link DigitalNumber} 实例的详细信息
   * @return 消息代码
   */
  int update(DigitalNumber param);

  /**
   * 删除记录
   *
   * @param name 名称
   * @return 消息代码
   */
  int delete(String name);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return 返回一个 {@link DigitalNumber} 实例的详细信息
   */
  DigitalNumber findOne(String name);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有实例的详细信息
   */
  List<DigitalNumber> findAll(Map params);

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
   * 根据前缀获取最大的编码因子
   *
   * @param entityTableName 实体数据表
   * @param prefix 前缀
   * @return 最大的编号因子
   */
  int getMaxSeedByPrefix(
    @Param("entity_table_name") String entityTableName,
    @Param("prefix") String prefix);

  /**
   * 根据类别标识获取某个类别的编码前缀
   *
   * @param entityCategoryTableName 实体类别数据表
   * @param entityCategoryId 实体类别标识
   * @return 编码前缀
   */
  String getPrefixCodeByCategoryId(
    @Param("entity_category_table_name") String entityCategoryTableName,
    @Param("entity_category_id") String entityCategoryId);
}
