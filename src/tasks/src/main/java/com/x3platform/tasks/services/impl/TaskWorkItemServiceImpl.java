package com.x3platform.tasks.services.impl;

import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.mappers.TaskWorkItemMapper;
import com.x3platform.tasks.models.TaskWorkItem;
import com.x3platform.tasks.services.TaskWorkItemService;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskWorkItemServiceImpl implements TaskWorkItemService {

  @Autowired
  private TaskWorkItemMapper provider;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例{@link TaskWorkItem}详细信息
   * @return TaskWorkItem 实例详细信息
   */
  @Override
  public int save(TaskWorkItem param) {
    return 0;
    // return this.provider.save(id);
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public int delete(String id) {
    return this.provider.deleteByPrimaryKey(id);
  }

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   */
  @Override
  public int deleteByTaskCode(String applicationId, String taskCode) {
    return this.provider.deleteByTaskCode(applicationId, taskCode);
  }

  /**
   * 删除记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @param receiverIds   任务接收人标识
   */
  @Override
  public int deleteByTaskCode(String applicationId, String taskCode, String receiverIds) {
    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  @Override
  public TaskWorkItem findOne(String taskId, String receiverId) {
    return null;
  }

  @Override
  public TaskWorkItem findOneByTaskCode(String applicationId, String taskCode, String receiverId) {
    return null;
  }

  @Override
  public List<TaskWorkItem> findAllByReceiverId(String receiverId, DataQuery query) {
    query.getWhere().put("receiver_id", receiverId);
    return this.provider.findAll(query.getMap());
  }

  @Override
  public List<TaskWorkItem> findAllByTaskCode(String applicationId, String taskCode) {
    return null;
  }

  @Override
  public List<TaskWorkItem> findAllByTaskCodes(String applicationId, String taskCodes) {
    return null;
  }

  /**
   * 查询某条记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @param length        长度
   * @return 返回一个 TaskWorkInfo 实例的详细信息
   */
  @Override
  public List<TaskWorkItem> findAllByTaskCode(String applicationId, String taskCode, int length) {
    DataQuery query = new DataQuery();
    // 设置查询参数
    query.getWhere().put("application_id", applicationId);
    query.getWhere().put("task_code", taskCode);
    query.setLength(1);

    return this.provider.findAll(query.getMap());
  }

  @Override
  public boolean isExist(String d) {
    return false;
  }

  @Override
  public boolean isExist(String taskId, String receiverId) {
    return false;
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @return 布尔值
   */

  @Override
  public boolean isExistTaskCode(String applicationId, String taskCode) {
    return false;
  }

  @Override
  public int copy(String fromReceiverId, String toReceiverId, Date beginDate, Date endDate) {
    return 0;
  }

  @Override
  public int cut(String fromReceiverId, String toReceiverId, Date beginDate, Date endDate) {
    return 0;
  }

  @Override
  public void setRead(String taskId, String receiverId, int isRead) {
    this.provider.setRead(taskId, receiverId, isRead);
  }

  @Override
  public void setStatus(String taskId, String receiverId, int status) {
    this.provider.setStatus(taskId, receiverId, status);
  }

  @Override
  public void setFinished(String receiverId) {
    this.provider.setFinishedByReceiverId(receiverId);
  }

  @Override
  public void setFinished(String receiverId, String taskIds) {
    this.provider.setFinished(receiverId, taskIds);
  }

  /**
   * 设置任务全部完成
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   */
  @Override
  public void setAllFinished(String applicationId, String taskCode) {
  }

  @Override
  public void setFinishedByTaskCode(String applicationId, String taskCodes, String receiverId) {

  }

  @Override
  public void setUnfinished(String receiverId, String taskIds) {

  }

  @Override
  public void setUnfinishedByTaskCode(String applicationId, String taskCodes, String receiverId) {

  }

  @Override
  public List<HashMap<Integer, Integer>> getUnfinishedQuantities(String receiverId) {
    List<HashMap<Integer, Integer>> result = this.provider.getUnfinishedQuantities(receiverId);
    return result;
  }

  @Override
  public List<TaskWorkItem> getWidgetData(String receiverId, int length) {
    return null;
  }

  /**
   * 设置任务标题
   *
   * @param applicationId 应用系统的标识
   * @param taskCode      任务编号
   * @param title         任务标题
   */
  @Override
  public void setTitle(String applicationId, String taskCode, String title) {
    // return 0;
  }

  /**
   * 获取任务标签列表
   */
  @Override
  public List<String> getTags() {
    return null;
  }

  /**
   * 获取任务标签列表
   *
   * @param key 匹配标签的关键字, 空值则全部匹配
   */
  @Override
  public List<String> getTags(String key) {
    return null;
  }

  @Override
  public int send(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverId) {
    return send(applicationId, taskCode, type, title, content, tags, senderId, receiverId, null);
  }

  @Override
  public int send(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverId, String notificationOptions) {
    TaskWorkItem entity = new TaskWorkItem();

    entity.setId(DigitalNumberContext.generate("Key_Guid"));
    entity.setApplicationId(applicationId);
    entity.setTaskCode(taskCode);
    entity.setType(type);
    entity.setTitle(title);
    entity.setContent(content);
    entity.setSenderId(senderId);
    entity.setReceiverId(receiverId);

    this.provider.insert(entity);

    // 处理发送通知选项 notificationOptions
    this.notification(entity, notificationOptions);

    return 0;
  }

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
  @Override
  public int sendRange(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverIds) {
    return sendRange(applicationId, taskCode, type, title, content, tags, senderId, receiverIds, null);
  }

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
  @Override
  public int sendRange(String applicationId, String taskCode, String type, String title, String content, String tags, String senderId, String receiverIds, String notificationOptions) {
    String[] list = receiverIds.split("[,]", -1);

    for (String item : list) {
      if (item.length() > 0) {
        send(applicationId, taskCode, type, title, content, tags, senderId, item, notificationOptions);
      }
    }

    return 0;
  }

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId 第三方系统帐号标识
   * @param taskCode      任务编号
   * @param receiverIds   接收者
   */
  @Override
  public int sendAppendRange(String applicationId, String taskCode, String receiverIds) {
    return sendAppendRange(applicationId, taskCode, receiverIds, null);
  }

  /**
   * 附加待办信息新的接收人
   *
   * @param applicationId       第三方系统帐号标识
   * @param taskCode            任务编号
   * @param receiverIds         接收者
   * @param notificationOptions 通知选项
   */
  @Override
  public int sendAppendRange(String applicationId, String taskCode, String receiverIds, String notificationOptions) {
    List<TaskWorkItem> list = findAllByTaskCode(applicationId, taskCode, 1);

    if (list.size() == 0) {
      // throw new Exception("【ApplicationId " + applicationId + " - TaskCode " + taskCode + "】任务不存在。");
      TasksContext.getLogger().error("{applicationId:{}, taskCode:{}} 任务不存在。", applicationId, taskCode);
      return 1;
    }

    TaskWorkItem item = list.get(0);

    String[] keys = receiverIds.split("[,|;]");

    for (String key : keys) {
      if (!StringUtil.isNullOrEmpty(key)) {
        send(item.getApplicationId(), item.getTaskCode(), item.getType(),
          item.getTitle(), item.getContent(), item.getTags(), item.getSenderId(),
          key, notificationOptions);
      }
    }

    return 0;
  }

  /**
   * 发送通知
   *
   * @param workItem 任务信息
   * @param options  通知选项
   */
  @Override
  public int notification(TaskWorkItem workItem, String options) {
    return 0;
  }

  /**
   * 异步接收待办信息
   */
  @Override
  public int asyncReceive() {
    return 0;
  }

  // -------------------------------------------------------
  // 归档、删除某一时段的待办记录
  // -------------------------------------------------------

  /**
   * 将已完成的待办归档到历史数据表
   */
  @Override
  public int archive() {

    return 0;
  }

  /**
   * 将归档日期之前已完成的待办归档到历史数据表
   *
   * @param archiveDate 归档日期
   */
  @Override
  public int archive(Date archiveDate) {
    return 0;
  }

  /**
   * 删除过期时间之前未完成的工作项记录
   *
   * @param expireDate 过期时间
   */
  @Override
  public int removeUnfinishedWorkItems(Date expireDate) {

    return 0;
  }

  /**
   * 删除过期时间之前的工作项记录
   *
   * @param expireDate 过期时间
   */
  @Override
  public int removeWorkItems(Date expireDate) {

    return 0;
  }

  /**
   * 删除过期时间之前的历史记录
   *
   * @param expireDate 过期时间
   */
  @Override
  public int removeHistoryItems(Date expireDate) {

    return 0;
  }
}

