package com.x3platform.tasks.services;

import com.x3platform.data.DataQuery;
import com.x3platform.tasks.models.TaskWorkItem;

import java.util.*;

/**
 * @author ruanyu
 */
public interface TaskWorkItemService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例{@link TaskWorkItem}详细信息
   * @return TaskWorkItem 实例详细信息
   */
  int save(TaskWorkItem param);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int delete(String id);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   */
  int deleteByTaskCode(String applicationId, String taskCode);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @param receiverIds   任务接收人标识
   */
  int deleteByTaskCode(String applicationId, String taskCode, String receiverIds);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------
  TaskWorkItem findOne(String taskId, String receiverId);

  TaskWorkItem findOneByTaskCode(String applicationId, String taskCode, String receiverId);

  List<TaskWorkItem> findAllByReceiverId(String receiverId, DataQuery query);

  List<TaskWorkItem> findAllByTaskCode(String applicationId, String taskCode);

  public List<TaskWorkItem> findAllByTaskCodes(String applicationId, String taskCodes);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @param length        长度
   * @return 返回一个 TaskWorkInfo 实例的详细信息
   */
  List<TaskWorkItem> findAllByTaskCode(String applicationId, String taskCode, int length);

  boolean isExist(String taskId, String receiverId);

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
   * 查询是否存在相关的记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @return 布尔值
   */
  boolean isExistTaskCode(String applicationId, String taskCode);

  int copy(String fromReceiverId, String toReceiverId, Date beginDate, Date endDate);

  int cut(String fromReceiverId, String toReceiverId, Date beginDate, Date endDate);

  void setRead(String taskId, String receiverId, int isRead);

  void setStatus(String taskId, String receiverId, int status);

  void setFinished(String receiverId);

  /**
   * 设置任务全部完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   */
  void setAllFinished(String applicationId, String taskCode);

  void setFinishedByTaskCode(String applicationId, String taskCodes, String receiverId);

  void setUnfinished(String receiverId, String taskIds);

  void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId);

  List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId);

  List<TaskWorkItem> getWidgetData(String receiverId, int length);

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
  int send(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverId);

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
  int send(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverId, String notificationOptions);

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
  int sendRange(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverIds);

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
  int sendRange(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverIds, String notificationOptions);

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId 第三方系统帐号标识
   * @param taskCode      任务编号
   * @param receiverIds   接收者
   */
  int sendAppendRange(String applicationId, String taskCode, String receiverIds);

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId       第三方系统帐号标识
   * @param taskCode            任务编号
   * @param receiverIds         接收者
   * @param notificationOptions 通知选项
   */
  int sendAppendRange(String applicationId, String taskCode, String receiverIds, String notificationOptions);

  /**
   * 发送通知
   *
   * @param workItem 任务信息
   * @param options  通知选项
   */
  int notification(TaskWorkItem workItem, String options);

  /**
   * 异步接收待办信息
   */
  int asyncReceive();

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
  List<String> getTags();

  /**
   * 获取任务标签列表
   *
   * @param key 匹配标签的关键字, 空值则全部匹配
   */
  List<String> getTags(String key);

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
  public int archive(Date archiveDate);

  /**
   * 删除过期时间之前未完成的工作项记录
   *
   * @param expireDate 过期时间
   */
  int removeUnfinishedWorkItems(Date expireDate);

  /**
   * 删除过期时间之前的工作项记录
   *
   * @param expireDate 过期时间
   */
  int removeWorkItems(Date expireDate);

  /**
   * 删除过期时间之前的历史记录
   *
   * @param expireDate 过期时间
   */
  int removeHistoryItems(Date expireDate);
}
