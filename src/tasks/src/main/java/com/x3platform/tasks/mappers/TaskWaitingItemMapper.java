package com.x3platform.tasks.mappers;

import com.x3platform.tasks.models.TaskWaitingItem;

import java.util.*;

/**
 *
 */
public interface TaskWaitingItemMapper {
    
  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record TaskWaitingItem 实例的详细信息
   */
  int insert(TaskWaitingItem record);
  
  /**
   * 修改记录
   *
   * @param record TaskWaitingItem 实例的详细信息
   */
  int updateByPrimaryKey(TaskWaitingItem record);
    
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
   * @return 返回一个 TaskWaitingItem 实例的详细信息
   */
  TaskWaitingItem selectByPrimaryKey(String id);
    
  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 TaskWaitingItem 实例的详细信息
   */
  List<TaskWaitingItem> findAll(Map params);
  
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
