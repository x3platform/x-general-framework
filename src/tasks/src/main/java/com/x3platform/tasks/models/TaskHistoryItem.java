package com.x3platform.tasks.models;

import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 * 历史项信息
 * @author ruanyu
 */
public class TaskHistoryItem
{
  /**
   * 默认构造函数
   */
  public TaskHistoryItem()
  {
  }

  private String id = "";

  /**
   * 唯一标识
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  private String applicationId = "";

  /**
   * 所属应用标识
   */
  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String value) {
    applicationId = value;
  }

  private String taskCode = "";

  /**
   * 任务编号
   */
  public String getTaskCode() {
    return taskCode;
  }

  public void setTaskCode(String value) {
    taskCode = value;
  }

  private String type = "";

  /**
   * 任务类型
   */
  public String getType() {
    return type;
  }

  public void setType(String value) {
    type = value;
  }

  private String title = "";

  /**
   * 标题
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String value) {
    title = value;
  }

  private String content = "";

  /**
   * 内容
   */
  public String getContent() {
    return content;
  }

  public void setContent(String value) {
    content = value;
  }

  private String tags = "";

  /**
   * 标签
   */
  public String getTags() {
    return tags;
  }

  public void setTags(String value) {
    tags = value;
  }

  private String senderId = "";

  /**
   * 发送者标识
   */
  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String value) {
    senderId = value;
  }

  private String receiverId = "";

  /**
   * 接收者标识
   */
  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String value) {
    receiverId = value;
  }

  private Boolean isRead = false;

  /**
   * 是否已读
   */
  public Boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(Boolean value) {
    isRead = value;
  }

  private int status = 0;

  /**
   * 状态
   */
  public int getStatus() {
    return status;
  }

  public void setStatus(int value) {
    status = value;
  }

  private Date finishTime = DateUtil.getDefaultDate();

  /**
   * 完成时间
   */
  public Date getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Date value) {
    finishTime = value;
  }

  private Date createDate = DateUtil.getDefaultDate();

  /**
   * 创建时间
   */
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date value) {
    createDate = value;
  }
}
