package com.x3platform.data.storages.mappers;

import com.x3platform.data.storages.models.DataStorageNode;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

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
