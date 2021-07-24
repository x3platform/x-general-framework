package com.x3platform.data.commons.mappers;

import com.x3platform.data.commons.models.DataStorageNode;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author ruanyu
 */
@Mapper
public interface DataStorageNodeMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record DataStorageNode 实例的详细信息
   * @return 返回消息代码 0=表示成功
   */
  int insert(DataStorageNode record);

  /**
   * 修改记录
   *
   * @param record DataStorageNode 实例的详细信息
   * @return 返回消息代码 0=表示成功
   */
  int updateByPrimaryKey(DataStorageNode record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 返回消息代码 0=表示成功
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回一个 DataStorageNode 实例的详细信息
   */
  DataStorageNode selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 DataStorageNode 实例的详细信息
   */
  List<DataStorageNode> findAll(Map params);

  /**
   * 根据存储架构标识查询所有相关记录
   *
   * @param schemaId 存储架构标识
   * @return 返回所有实例 {@link DataStorageNode} 的详细信息
   */
  List<DataStorageNode> findAllBySchemaId(@Param("storage_schema_id") String schemaId);

  /**
   * 根据应用标识查询所有相关记录
   *
   * @param applicationId 应用标识
   * @return 返回所有实例 {@link DataStorageNode} 的详细信息
   */
  List<DataStorageNode> findAllByApplicationId(@Param("application_id") String applicationId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 根据应用标识获取下拉列表信息
   *
   * @param applicationId 应用标识
   * @return 所有 {@link DataStorageNode} 实例的详细信息
   */
  List<DataStorageNode> getCombobox(String applicationId);
}
