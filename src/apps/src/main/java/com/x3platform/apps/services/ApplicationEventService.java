package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationEvent;

import com.x3platform.data.DataQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author ruanyu
 */
public interface ApplicationEventService {
  
  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------
  
  /**
   * 保存记录
   *
   * @param entity {@link ApplicationEvent} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(ApplicationEvent entity);
  
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
   * @return {@link ApplicationEvent} 实例的详细信息
   */
  ApplicationEvent findOne(String id);
  
  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link ApplicationEvent} 实例的详细信息
   */
  List<ApplicationEvent> findAll(DataQuery query);
  
  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------
  
  /**
   * 写入应用事件信息
   *
   * @param applicationId 所属应用标识
   * @param level         事件级别
   * @param description   描述
   * @param startTime     开始时间
   * @param finishTime    结束时间
   * @return 消息代码 0=表示成功
   */
  int write(String applicationId, String level, String description, LocalDateTime startTime, LocalDateTime finishTime);
  
  /**
   * 写入应用事件信息
   *
   * @param accountId               帐号标识
   * @param accountName             帐号名称
   * @param applicationId           所属应用标识
   * @param applicationFeatureName 所属应用功能索引
   * @param level                   事件级别
   * @param description             描述
   * @param startTime               开始时间
   * @param finishTime              结束时间
   * @param ip                      IP
   * @return 消息代码 0=表示成功
   */
  int write(String accountId, String accountName, String applicationId, String applicationFeatureName,
            String level, String description, LocalDateTime startTime, LocalDateTime finishTime, String ip);
}
