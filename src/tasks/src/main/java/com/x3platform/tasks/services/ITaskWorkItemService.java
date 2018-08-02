package com.x3platform.tasks.services;

import com.x3platform.tasks.models.TaskWorkItemInfo;

import java.util.*;

/**
 * 任务工作项服务接口
 */
public interface ITaskWorkItemService {

  /**
   * 查询某条记录
   *
   * @param taskId     任务标识
   * @param receiverId 接收人标识
   * @return 返回一个 TaskWorkItemInfo 实例的详细信息
   */
  TaskWorkItemInfo findOne(String taskId, String receiverId);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编码
   * @param receiverId    接收人标识
   * @return 返回一个 TaskWorkItemInfo 实例的详细信息
   */
  TaskWorkItemInfo findOneByTaskCode(String applicationId, String taskCode, String receiverId);

  /**
   * 查询所有相关记录
   *
   * @param receiverId  接收者帐号标识
   * @param whereClause SQL 查询条件
   * @return 返回所有 TaskWorkItemInfo 实例的详细信息
   */
  List<TaskWorkItemInfo> findAllByReceiverId(String receiverId, HashMap<String, Object> params);

  /**
   * 查询所有相关记录
   *
   * @param receiverId  接收者帐号标识
   * @param whereClause SQL 查询条件
   * @param length      长度
   * @return 返回所有 TaskWorkItemInfo 实例的详细信息
   */
  List<TaskWorkItemInfo> findAllByReceiverId(String receiverId, String whereClause, int length);

  /**
   * 分页函数
   *
   * @param receiverId 接收人标识
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例<see                                                                                                                               cref                                                               =                                                               "                                                               TaskWorkItemInfo                                                               "                                                               />
   */
  // List<TaskWorkItemInfo> GetPaging(String receiverId, int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 检测是否存在相关的记录
   *
   * @param taskId     任务标识
   * @param receiverId 接收者标识
   * @return 布尔值
   */
  boolean isExist(String taskId, String receiverId);

  /**
   * 复制待办信息
   *
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId   待办目标接收者标识
   * @param beginDate      复制待办的开始时间
   * @param endDate        复制待办结束时间
   * @return
   */
  int copy(String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate);

  /**
   * 剪切待办信息
   *
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId   待办目标接收者标识
   * @param beginDate      复制待办的开始时间
   * @param endDate        复制待办结束时间
   * @return
   */
  int cut(String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate);

  /**
   * 设置任务阅读状态
   *
   * @param taskId     任务标识
   * @param receiverId 接收者的用户名
   * @param isRead     状态: true 已读 | false 未读
   */
  void setRead(String taskId, String receiverId, boolean isRead);

  /**
   * 设置任务状态
   *
   * @param taskId     任务标识
   * @param receiverId 接收者的用户名
   * @param status     状态
   */
  void setStatus(String taskId, String receiverId, int status);

  /**
   * 设置某个用户的任务全部完成
   *
   * @param receiverId 接收者
   */
  void setFinished(String receiverId);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者
   * @param taskIds    任务标识,多个以逗号分开
   */
  void setFinished(String receiverId, String taskIds);

  /**
   * 设置任务完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes     任务编号,多个以逗号分开
   * @param receiverId    接收者
   */
  void setFinishedByTaskCode(String applicationId, String taskCodes, String receiverId);

  /**
   * 设置任务未完成
   *
   * @param receiverId 接收者
   * @param taskIds    任务编号,多个以逗号分开
   */
  void setUnfinished(String receiverId, String taskIds);

  /**
   * 设置任务未完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes     任务编号,多个以逗号分开
   * @param receiverId    接收者
   */
  void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId);

  /**
   * 获取未完成任务的数量
   *
   * @param receiverId 接收人标识
   * @return 返回一个包含每个类型的统计数的字典
   */
  List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId);

  /**
   * 获取任务部件的数据
   *
   * @param receiverId 接收人标识
   * @param length     显示的最大条数
   * @return 返回所有 TaskWorkItemInfo 实例的列表信息
   */
  List<TaskWorkItemInfo> getWidgetData(String receiverId, int length);
}
