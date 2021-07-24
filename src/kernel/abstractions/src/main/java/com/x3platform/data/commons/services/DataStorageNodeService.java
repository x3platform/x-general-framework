package com.x3platform.data.commons.services;

import com.x3platform.data.DataQuery;
import com.x3platform.data.commons.models.DataStorageNode;
import java.util.List;

/**
 * @author ruanyu
 */
public interface DataStorageNodeService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 DataStorageNode 详细信息
   * @return 返回消息代码 0=表示成功
   */
  int save(DataStorageNode entity);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 返回消息代码 0=表示成功
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回实例 DataStorageNode 的详细信息
   */
  DataStorageNode findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 {@link DataStorageNode} 的详细信息
   */
  List<DataStorageNode> findAll(DataQuery query);

  /**
   * 根据存储架构标识查询所有相关记录
   *
   * @param schemaId 存储架构标识
   * @return 返回所有实例 {@link DataStorageNode} 的详细信息
   */
  List<DataStorageNode> findAllBySchemaId(String schemaId);

  /**
   * 根据应用标识查询所有相关记录
   *
   * @param applicationId 应用标识
   * @return 返回所有实例 {@link DataStorageNode} 的详细信息
   */
  List<DataStorageNode> findAllByApplicationId(String applicationId);

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

  /**
   * 获取默认的存储节点信息
   *
   * @return 返回实例 {@link DataStorageNode} 的详细信息
   */
  DataStorageNode getDefault();

  /**
   * 根据存储架构标识自动获取默认的存储节点信息
   *
   * @param schemaId 存储架构标识
   * @return 返回 {@link DataStorageNode} 的详细信息
   */
  DataStorageNode getDefaultNodeBySchemaId(String schemaId);

  /**
   * 根据存储架构标识自动获取默认的存储节点信息
   *
   * @param applicationId 所属应用标识
   * @return 返回 {@link DataStorageNode} 的详细信息
   */
  DataStorageNode getDefaultNodeByApplicationId(String applicationId);

  /**
   * 根据存储架构标识自动获取默认的存储节点信息
   *
   * @param applicationName 存储架构标识
   * @return 返回 {@link DataStorageNode} 的详细信息
   */
  DataStorageNode getDefaultNodeByApplicationName(String applicationName);
}
