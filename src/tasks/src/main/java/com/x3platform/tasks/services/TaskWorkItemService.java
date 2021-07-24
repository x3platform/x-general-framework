package com.x3platform.tasks.services;

import com.x3platform.tasks.models.TaskWorkItem;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 任务管理
 *
 * @author ruanyu
 */
public interface TaskWorkItemService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 {@link TaskWorkItem} 详细信息
   * @return 消息代码
   */
  int save(TaskWorkItem param);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码
   */
  int delete(String id);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @return 消息代码
   */
  int deleteByTaskCode(String applicationId, String taskCode);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @param receiverIds 任务接收人标识
   * @return 消息代码
   */
  int deleteByTaskCode(String applicationId, String taskCode, String receiverIds);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  TaskWorkItem findOne(String id, String receiverId);

  TaskWorkItem findOneByTaskCode(String applicationId, String taskCode, String receiverId);

  /**
   * 查询当前接收人所有未完成的记录
   *
   * @param receiverId 接收者帐号标识
   * @return {@link TaskWorkItem} 实例的详细信息
   */
  List<TaskWorkItem> findAllByReceiverId(String receiverId);

  /**
   * 查询当前接收人所有未完成的记录
   *
   * @param receiverId 接收者帐号标识
   * @param length 最大长度
   * @return {@link TaskWorkItem} 实例的详细信息
   */
  List<TaskWorkItem> findAllByReceiverId(String receiverId, int length);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @return 一个 {@link TaskWorkItem} 实例的详细信息
   */
  List<TaskWorkItem> findAllByTaskCode(String applicationId, String taskCode);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @param length 长度
   * @return {@link TaskWorkItem} 实例的列表信息
   */
  List<TaskWorkItem> findAllByTaskCode(String applicationId, String taskCode, int length);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号
   * @return {@link TaskWorkItem} 实例的列表信息
   */
  List<TaskWorkItem> findAllByTaskCodes(String applicationId, String taskCodes);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @param receiverId 接收者帐号标识
   * @return 布尔值
   */
  boolean isExist(String id, String receiverId);

  /**
   * 查询是否存在相关的记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @return 布尔值
   */
  boolean isExistTaskCode(String applicationId, String taskCode);

  int copy(String fromReceiverId, String toReceiverId, Date beginDate, Date endDate);

  int cut(String fromReceiverId, String toReceiverId, Date beginDate, Date endDate);

  /**
   * 设置消息是否已读
   *
   * @param id 标识
   * @param receiverId 接收者帐号标识
   * @param isRead 是否已读 0-未读 1-已读
   */
  void setRead(String id, String receiverId, int isRead);

  /**
   * 设置任务状态
   *
   * @param id 标识
   * @param receiverId 接收者帐号标识
   * @param status 是否已完成 0-未完成 1-已完成
   */
  void setStatus(String id, String receiverId, int status);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者帐号标识
   */
  void setFinished(String receiverId);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者帐号标识
   * @param ids 任务标识
   */
  void setFinished(String receiverId, String ids);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者帐号标识
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号
   */
  void setFinished(String receiverId, String applicationId, String taskCodes);

  /**
   * 设置任务完成
   *
   * @param type 任务类型
   * @param receiverId 接收者帐号标识
   */
  void setFinishedByType(String receiverId, String type);

  /**
   * 设置任务全部完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号
   */
  void setAllFinished(String applicationId, String taskCodes);

  /**
   * 设置任务未完成
   *
   * @param receiverId 接收者帐号标识
   * @param ids 任务标识
   */
  void setUnfinished(String receiverId, String ids);

  /**
   * 设置任务未完成
   *
   * @param receiverId 接收者帐号标识
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号
   */
  void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId);

  /**
   * 获取未完成任务的数量
   *
   * @param receiverId 接收者帐号标识
   * @return 一个包含每个类型的统计数的字典
   */
  List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId);

  /**
   * 获取 Widget 部件数据
   *
   * @param receiverId 接收者帐号标识
   * @param length 最大数量
   * @return 任务列表
   */
  List<TaskWorkItem> getWidgetData(String receiverId, int length);

  /**
   * 发送一对一的待办信息
   *
   * @param taskCode 任务编号
   * @param applicationId 第三方系统帐号标识
   * @param title 标题
   * @param content 详细信息地址
   * @param tags 标签
   * @param type 类型
   * @param senderId 发送者帐号标识
   * @param receiverId 接收者帐号标识
   * @return 消息代码
   */
  int send(String applicationId, String taskCode, String type, String title, String content, String tags,
    String senderId, String receiverId);

  /**
   * 发送一对一的待办信息
   *
   * @param taskCode 任务编号
   * @param applicationId 第三方系统帐号标识
   * @param title 标题
   * @param content 详细信息地址
   * @param tags 标签
   * @param type 类型
   * @param senderId 发送者帐号标识
   * @param receiverId 接收者帐号标识
   * @param notificationOptions 通知选项
   * @return 消息代码
   */
  int send(String applicationId, String taskCode, String type, String title, String content, String tags,
    String senderId, String receiverId, String notificationOptions);

  /**
   * 发送一对多的待办信息
   *
   * @param taskCode 任务编号
   * @param applicationId 第三方系统帐号标识
   * @param title 标题
   * @param content 详细信息地址
   * @param tags 标签
   * @param type 类型
   * @param senderId 发送者
   * @param receiverIds 接收者
   * @return 消息代码
   */
  int sendRange(String applicationId, String taskCode, String type, String title, String content, String tags,
    String senderId, String receiverIds);

  /**
   * 发送一对多的待办信息
   *
   * @param taskCode 任务编号
   * @param applicationId 第三方系统帐号标识
   * @param title 标题
   * @param content 详细信息地址
   * @param tags 标签
   * @param type 类型
   * @param senderId 发送者
   * @param receiverIds 接收者
   * @param notificationOptions 通知选项
   * @return 消息代码
   */
  int sendRange(String applicationId, String taskCode, String type, String title, String content, String tags,
    String senderId, String receiverIds, String notificationOptions);

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId 第三方系统帐号标识
   * @param taskCode 任务编号
   * @param receiverIds 接收者
   * @return 消息代码
   */
  int sendAppendRange(String applicationId, String taskCode, String receiverIds);

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId 第三方系统帐号标识
   * @param taskCode 任务编号
   * @param receiverIds 接收者
   * @param notificationOptions 通知选项
   * @return 消息代码
   */
  int sendAppendRange(String applicationId, String taskCode, String receiverIds, String notificationOptions);

  /**
   * 发送通知
   *
   * @param workItem 任务信息
   * @param options 通知选项
   * @return 消息代码
   */
  int notification(TaskWorkItem workItem, String options);

  /**
   * 异步接收待办信息
   *
   * @return 消息代码
   */
  int asyncReceive();

  /**
   * 设置任务标题
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @param title 任务标题
   */
  void setTitle(String applicationId, String taskCode, String title);

  /**
   * 获取任务标签列表
   *
   * @return 标签列表
   */
  List<String> getTags();

  /**
   * 获取任务标签列表
   *
   * @param key 匹配标签的关键字, 空值则全部匹配
   * @return 标签列表
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
