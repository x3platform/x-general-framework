package com.x3platform.tasks.mappers;

import com.x3platform.tasks.models.TaskWorkItemInfo;
import com.x3platform.tasks.models.TaskWorkitemModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface TaskWorkItemMapper {
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
   * @return 返回一个 TaskWorkReceiverInfo 实例的详细信息
   */
  TaskWorkItemInfo findOneByTaskCode(String applicationId, String taskCode, String receiverId);

  /**
   * 查询所有相关记录
   *
   * @param receiverId  接收者帐号标识
   * @param params SQL 查询条件
   * @return 返回所有 TaskWorkItemInfo 实例的详细信息
   */
  List<TaskWorkItemInfo> findAll(Map params);

  /**
   * 分页函数
   *
   * @param receiverId 接收人标识
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例<see                               cref               =               "               TaskWorkItemInfo               "               />
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
  ///#endregion

  ///#region 函数:Copy(string applicationId, string fromReceiverId, string toReceiverId, DateTime beginDate, DateTime endDate)

  /**
   * 复制待办信息
   *
   * @param applicationId  所属应用标识
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId   待办目标接收者标识
   * @param beginDate      复制待办的开始时间
   * @param endDate        复制待办结束时间
   * @return
   */
  int copy(String applicationId, String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate);
  ///#endregion

  ///#region 函数:Cut(string fromReceiverId, string toReceiverId, DateTime beginDate, DateTime endDate)

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
  ///#endregion

  ///#region 函数:Cut(string applicationId, string fromReceiverId, string toReceiverId, DateTime beginDate, DateTime endDate)

  /**
   * 剪切待办信息
   *
   * @param applicationId  所属应用标识
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId   待办目标接收者标识
   * @param beginDate      复制待办的开始时间
   * @param endDate        复制待办结束时间
   * @return
   */
  int cut(String applicationId, String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate);
  ///#endregion

  ///#region 函数:SetRead(string taskId, string receiverId, bool isRead)

  /**
   * 设置任务阅读状态
   *
   * @param taskId     任务标识
   * @param receiverId 接收者的用户名
   * @param isRead     状态: true 已读 | false 未读
   */
  void setRead(String taskId, String receiverId, boolean isRead);
  ///#endregion

  ///#region 函数:SetStatus(string taskId, string receiverId, int status)

  /**
   * 强制设置任务状态
   *
   * @param taskId     任务标识
   * @param receiverId 接收者的用户名
   * @param status     状态
   */
  void setStatus(String taskId, String receiverId, int status);
  ///#endregion

  ///#region 函数:SetFinished(string receiverId)

  /**
   * 设置某个用户的任务全部完成
   *
   * @param receiverId 接收者
   */
  void setFinished(String receiverId);
  ///#endregion

  ///#region 函数:SetFinished(string receiverId, string taskIds)

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者
   * @param taskIds    任务编号,多个以逗号分开
   */
  void setFinished(String receiverId, String taskIds);
  ///#endregion

  ///#region 函数:SetFinishedByTaskCode(string applicationId, string taskCodes, string receiverId)

  /**
   * 设置任务完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes     任务编号,多个以逗号分开
   * @param receiverId    接收者
   */
  void setFinishedByTaskCode(String applicationId, String taskCodes, String receiverId);
  ///#endregion

  ///#region 函数:SetUnfinished(string receiverId, string taskIds)

  /**
   * 设置任务未完成
   *
   * @param receiverId 接收者
   * @param taskIds    任务编号,多个以逗号分开
   */
  void setUnfinished(String receiverId, String taskIds);
  ///#endregion

  ///#region 函数:SetUnfinishedByTaskCode(string applicationId, string taskCodes, string receiverId)

  /**
   * 设置任务未完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes     任务编号,多个以逗号分开
   * @param receiverId    接收者
   */
  void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId);
  ///#endregion

  ///#region 函数:GetUnfinishedQuantities(string receiverId)

  /**
   * 获取未完成任务的数量
   *
   * @param receiverId 接收人标识
   * @return 返回一个包含每个类型的统计数的字典
   */
  List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId);
}
