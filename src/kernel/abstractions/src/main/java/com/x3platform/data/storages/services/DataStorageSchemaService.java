package com.x3platform.data.storages.services;

import com.x3platform.data.storages.models.DataStorageNode;
import com.x3platform.data.storages.models.DataStorageSchema;

import com.x3platform.data.DataQuery;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public interface DataStorageSchemaService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 DataStorageSchema 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(DataStorageSchema entity);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 实例 DataStorageSchema 的详细信息
   */
  DataStorageSchema findOne(String id);

  /**
   * 查询某条记录
   *
   * @param applicationId 所属应用标识
   * @return 实例 DataStorageSchema 的详细信息
   */
  DataStorageSchema findOneByApplicationId(String applicationId);

  /**
   * 查询某条记录
   *
   * @param applicationName 所属应用名称
   * @return 实例 DataStorageSchema 的详细信息
   */
  DataStorageSchema findOneByApplicationName(String applicationName);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有实例 DataStorageSchema 的详细信息
   */
  List<DataStorageSchema> findAll(DataQuery query);

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
}
