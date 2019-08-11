package com.x3platform.tasks.services;

import com.x3platform.tasks.models.TaskHistoryItem;

import com.x3platform.data.DataQuery;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public interface TaskHistoryItemService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------
    
  /**
   * 保存记录
   *
   * @param entity 实例 TaskHistoryItem 详细信息
   * @return 返回消息代码 0=表示成功
   */
  int save(TaskHistoryItem entity);

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
   * @return 返回实例 TaskHistoryItem 的详细信息
   */
  TaskHistoryItem findOne(String id);
   
        
  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 TaskHistoryItem 的详细信息
   */
  List<TaskHistoryItem> findAll(DataQuery query);

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
