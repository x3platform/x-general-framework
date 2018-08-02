package com.x3platform.tasks.services;

import com.x3platform.tasks.models.TaskWorkInfo;
import com.x3platform.tasks.models.TaskWorkItemInfo;

import java.util.*;

/**
 */
public interface ITaskWorkService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例<see cref="TaskWorkInfo"/>详细信息
   * @return TaskWorkInfo 实例详细信息
   */
  TaskWorkInfo save(TaskWorkInfo param);

  /**
   * 删除记录
   *
   * @param id 实例的标识信息,多个以逗号分开.
   */
  void delete(String id);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编码
   */
  void deleteByTaskCode(String applicationId, String taskCode);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编码
   * @param receiverIds   任务接收人标识
   */
  void deleteByTaskCode(String applicationId, String taskCode, String receiverIds);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id TaskWorkInfo Id号
   * @return 返回一个 TaskWorkInfo 实例的详细信息
   */
  TaskWorkInfo findOne(String id);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编码
   * @return 返回一个 TaskWorkInfo 实例的详细信息
   */
  TaskWorkInfo findOneByTaskCode(String applicationId, String taskCode);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 TaskWorkInfo 实例的详细信息
   */
  List<TaskWorkItemInfo> findAll();

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有 TaskWorkInfo 实例的详细信息
   */
  List<TaskWorkItemInfo> findAll(String whereClause);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有 TaskWorkInfo 实例的详细信息
   */
  List<TaskWorkItemInfo> findAll(String whereClause, int length);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /** 分页函数
   @param startIndex 开始行索引数,由0开始统计
   @param pageSize 页面大小
   @param whereClause WHERE 查询条件
   @param orderBy ORDER BY 排序条件.
   @param rowCount 记录行数
   @return 返回一个列表
   */
  // List<TaskWorkItemInfo> getPaging(int startIndex, int pageSize, String whereClause, String orderBy, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编码
   * @return 布尔值
   */
  boolean isExistTaskCode(String applicationId, String taskCode);

  /**
   * 发送一对一的待办信息
   *
   * @param taskCode      任务编号
   * @param applicationId 第三方系统帐号标识
   * @param title         标题
   * @param content       详细信息地址
   * @param tags          标签
   * @param type          类型
   * @param senderId      发送者帐号标识
   * @param receiverId    接收者帐号标识
   */
  void send(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverId);

  /**
   * 发送一对一的待办信息
   *
   * @param taskCode            任务编号
   * @param applicationId       第三方系统帐号标识
   * @param title               标题
   * @param content             详细信息地址
   * @param tags                标签
   * @param type                类型
   * @param senderId            发送者帐号标识
   * @param receiverId          接收者帐号标识
   * @param notificationOptions 通知选项
   */
  void send(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverId, String notificationOptions);

  /**
   * 发送一对多的待办信息
   *
   * @param taskCode      任务编号
   * @param applicationId 第三方系统帐号标识
   * @param title         标题
   * @param content       详细信息地址
   * @param tags          标签
   * @param type          类型
   * @param senderId      发送者
   * @param receiverIds   接收者
   */
  void sendRange(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverIds);

  /**
   * 发送一对多的待办信息
   *
   * @param taskCode            任务编号
   * @param applicationId       第三方系统帐号标识
   * @param title               标题
   * @param content             详细信息地址
   * @param tags                标签
   * @param type                类型
   * @param senderId            发送者
   * @param receiverIds         接收者
   * @param notificationOptions 通知选项
   */
  void sendRange(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverIds, String notificationOptions);

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId 第三方系统帐号标识
   * @param taskCode      任务编号
   * @param receiverIds   接收者
   */
  void sendAppendRange(String applicationId, String taskCode, String receiverIds);

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId       第三方系统帐号标识
   * @param taskCode            任务编号
   * @param receiverIds         接收者
   * @param notificationOptions 通知选项
   */
  void sendAppendRange(String applicationId, String taskCode, String receiverIds, String notificationOptions);

  /**
   * 发送通知
   *
   * @param task                任务信息
   * @param receiverIds         接收者
   * @param notificationOptions 通知选项
   */
  void notification(TaskWorkInfo task, String receiverIds, String notificationOptions);

  /**
   * 异步接收待办信息
   */
  void AsyncReceive();

  /**
   * 设置任务标题
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @param title         任务标题
   */
  void setTitle(String applicationId, String taskCode, String title);

  /**
   * 设置任务完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   */
  void setFinished(String applicationId, String taskCode);

  /**
   * 获取任务标签列表
   */
  List<String> getTaskTags();

  /**
   * 获取任务标签列表
   *
   * @param key 匹配标签的关键字, 空值则全部匹配
   */
  List<String> getTaskTags(String key);

  /**
   * 将任务编号转换为标识信息
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes     任务编号,多个以逗号分开
   */
  String getIdsByTaskCodes(String applicationId, String taskCodes);

  // -------------------------------------------------------
  // 归档、删除某一时段的待办记录
  // -------------------------------------------------------

  /**
   * 将已完成的待办归档到历史数据表
   */
  int archive();

  /**
   * 将归档日期之前已完成的待办归档到历史数据表
   *
   * @param archiveDate 归档日期
   */
  int archive(java.time.LocalDateTime archiveDate);

  /**
   * 删除过期时间之前未完成的工作项记录
   *
   * @param expireDate 过期时间
   */
  void removeUnfinishedWorkItems(java.time.LocalDateTime expireDate);

  /**
   * 删除过期时间之前的工作项记录
   *
   * @param expireDate 过期时间
   */
  void removeWorkItems(java.time.LocalDateTime expireDate);

  /**
   * 删除过期时间之前的历史记录
   *
   * @param expireDate 过期时间
   */
  void removeHistoryItems(java.time.LocalDateTime expireDate);
}
