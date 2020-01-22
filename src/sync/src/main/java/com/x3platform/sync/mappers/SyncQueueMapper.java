package com.x3platform.sync.mappers;

import com.x3platform.sync.models.SyncQueue;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface SyncQueueMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link SyncQueue} 实例的详细信息
   * @return 受影响的行数
   */
  int insert(SyncQueue record);

  /**
   * 修改记录
   *
   * @param record {@link SyncQueue} 实例的详细信息
   * @return 受影响的行数
   */
  int updateByPrimaryKey(SyncQueue record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 受影响的行数
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link SyncQueue} 实例的详细信息
   */
  SyncQueue selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link SyncQueue} 实例的详细信息
   */
  List<SyncQueue> findAll(Map params);

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
   * 获取有效的队列信息
   *
   * @return 所有 {@link SyncQueue} 实例的详细信息
   */
  List<SyncQueue> getQueues();

  /**
   * 获取有效的队列应用标识
   *
   * @return 所有相关应用标识
   */
  List<String> getApplicationIds();

  /**
   * 设置最后一个更新包的标识
   *
   * @param id 标识
   * @param lastPkgId 最后一个更新包的标识
   * @return 消息代码
   */
  int setLastPkgId(@Param("id") String id, @Param("last_pkg_id") String lastPkgId);
}
