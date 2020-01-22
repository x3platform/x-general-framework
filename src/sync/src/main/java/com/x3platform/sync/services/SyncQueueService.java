package com.x3platform.sync.services;

import com.x3platform.data.DataQuery;
import com.x3platform.sync.models.SyncQueue;
import java.util.List;

/**
 * @author ruanyu
 */
public interface SyncQueueService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link SyncQueue} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(SyncQueue entity);

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
   * @return {@link SyncQueue} 实例的详细信息
   */
  SyncQueue findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link SyncQueue} 实例的详细信息
   */
  List<SyncQueue> findAll(DataQuery query);

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
  int setLastPkgId(String id, String lastPkgId);
}
