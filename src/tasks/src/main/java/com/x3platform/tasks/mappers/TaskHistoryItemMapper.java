package com.x3platform.tasks.mappers;

import com.x3platform.tasks.models.TaskHistoryItem;

import java.util.*;

/**
 *
 */
public interface TaskHistoryItemMapper {
    
  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record TaskHistoryItem 实例的详细信息
   */
  int insert(TaskHistoryItem record);
  
  /**
   * 修改记录
   *
   * @param record TaskHistoryItem 实例的详细信息
   */
  int updateByPrimaryKey(TaskHistoryItem record);
    
  /**
   * 删除记录
   *
   * @param id 标识
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------
    
  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回一个 TaskHistoryItem 实例的详细信息
   */
  TaskHistoryItem selectByPrimaryKey(String id);
    
  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 TaskHistoryItem 实例的详细信息
   */
  List<TaskHistoryItem> findAll(Map params);
  
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
