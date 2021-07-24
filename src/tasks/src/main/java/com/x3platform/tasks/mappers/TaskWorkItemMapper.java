package com.x3platform.tasks.mappers;

import com.x3platform.tasks.models.TaskWorkItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskWorkItemMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link TaskWorkItem} 实例的详细信息
   */
  int insert(TaskWorkItem record);

  /**
   * 修改记录
   *
   * @param record {@link TaskWorkItem} 实例的详细信息
   * @return 消息代码
   */
  int updateByPrimaryKey(TaskWorkItem record);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int deleteByPrimaryKey(String id);

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   */
  int deleteByTaskCode(@Param("applicationId") String applicationId, @Param("taskCode") String taskCode);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link TaskWorkItem} 实例的详细信息
   */
  TaskWorkItem selectByPrimaryKey(String id);

  /**
   * 查询某条记录
   *
   * @param taskId 任务标识
   * @param receiverId 接收人标识
   * @return 一个 {@link TaskWorkItem} 实例的详细信息
   */
  TaskWorkItem findOne(@Param("id") String taskId, @Param("receiver_id") String receiverId);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @param receiverId 接收人标识
   * @return 一个 {@link TaskWorkItem} 实例的详细信息
   */
  TaskWorkItem findOneByTaskCode(
    @Param("application_id") String applicationId,
    @Param("task_code") String taskCode,
    @Param("receiver_id") String receiverId);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link TaskWorkItem} 实例的详细信息
   */
  List<TaskWorkItem> findAll(Map params);

  /**
   * 查询当前接收人所有未完成的记录
   *
   * @param receiverId 接收者标识
   * @param length 最大长度
   * @return {@link TaskWorkItem} 实例的详细信息
   */
  List<TaskWorkItem> findAllByReceiverId(@Param("receiver_id") String receiverId, @Param("length") int length);

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号
   * @return {@link TaskWorkItem} 实例的详细信息
   */
  List<TaskWorkItem> findAllByTaskCodes(
    @Param("application_id") String applicationId,
    @Param("task_codes") String taskCodes);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务标识
   * @param receiverId 接收者标识
   * @return 布尔值
   */
  boolean isExist(
    @Param("id") String id,
    @Param("receiver_id") String receiverId);

  /**
   * 查询是否存在相关的记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode 任务编号
   * @return 布尔值
   */
  boolean isExistTaskCode(
    @Param("application_id") String applicationId,
    @Param("task_code") String taskCode);

  /**
   * 复制待办信息
   *
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId 待办目标接收者标识
   * @param beginDate 复制待办的开始时间
   * @param endDate 复制待办结束时间
   */
  int copy(String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate,
    java.time.LocalDateTime endDate);

  /**
   * 复制待办信息
   *
   * @param applicationId 所属应用标识
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId 待办目标接收者标识
   * @param beginDate 复制待办的开始时间
   * @param endDate 复制待办结束时间
   */
  int copy(String applicationId, String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate,
    java.time.LocalDateTime endDate);

  /**
   * 剪切待办信息
   *
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId 待办目标接收者标识
   * @param beginDate 复制待办的开始时间
   * @param endDate 复制待办结束时间
   */
  int cut(String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate,
    java.time.LocalDateTime endDate);

  /**
   * 剪切待办信息
   *
   * @param applicationId 所属应用标识
   * @param fromReceiverId 待办来源接收者标识
   * @param toReceiverId 待办目标接收者标识
   * @param beginDate 复制待办的开始时间
   * @param endDate 复制待办结束时间
   */
  int cut(String applicationId, String fromReceiverId, String toReceiverId, java.time.LocalDateTime beginDate,
    java.time.LocalDateTime endDate);

  /**
   * 设置任务阅读状态
   *
   * @param id 任务标识
   * @param receiverId 接收者标识
   * @param isRead 状态: 1 已读 | 0 未读
   */
  void setRead(@Param("id") String id, @Param("receiver_id") String receiverId, @Param("is_read") int isRead);

  /**
   * 强制设置任务状态
   *
   * @param id 任务标识
   * @param receiverId 接收者标识
   * @param status 状态
   */
  void setStatus(@Param("id") String id, @Param("receiver_id") String receiverId, @Param("status") int status);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者帐号标识
   */
  void setFinished(@Param("receiver_id") String receiverId);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者帐号标识
   * @param ids 任务标识
   */
  void setFinishedByIds(@Param("receiver_id") String receiverId, @Param("ids") String ids);

  /**
   * 设置任务完成
   *
   * @param receiverId 接收者帐号标识
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号
   */
  void setFinishedByTaskCodes(
    @Param("receiver_id") String receiverId,
    @Param("application_id") String applicationId,
    @Param("task_codes") String taskCodes);

  /**
   * 设置某个用户某一类型的任务全部完成
   *
   * @param receiverId 接收者标识
   */
  void setFinishedByType(@Param("receiver_id") String receiverId, @Param("type") String type);

  /**
   * 设置任务全部完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号,多个以逗号分开
   */
  void setAllFinished(@Param("application_id") String applicationId, @Param("task_codes") String taskCodes);

  /**
   * 设置任务未完成
   *
   * @param receiverId 接收者
   * @param taskIds 任务编号,多个以逗号分开
   */
  void setUnfinished(String receiverId, String taskIds);

  /**
   * 设置任务未完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCodes 任务编号,多个以逗号分开
   * @param receiverId 接收者
   */
  void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId);

  /**
   * 获取未完成任务的数量
   *
   * @param receiverId 接收人标识
   * @return 一个包含每个类型的统计数的字典
   */
  List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId);

  /**
   * 获取任务标签列表
   *
   * @param key 匹配标签的关键字, 空值则全部匹配
   */
  List<String> getTags(String key);
}
