package com.x3platform.tasks.models;

import com.x3platform.util.DateUtil;

/**
 * 任务接收者
 */
public class TaskWorkReceiverInfo {
  /**
   */
  public TaskWorkReceiverInfo() {
    this.mStatus = 0;
    this.mFinishTime = DateUtil.getDefaultLocalDateTime();
  }

  /**
   */
  public TaskWorkReceiverInfo(String taskId, String receiverId) {
    this();
    this.mTaskId = taskId;
    this.mReceiverId = receiverId;
  }

  ///#region 属性:TaskId
  private String mTaskId;

  /**
   */
  public final String getTaskId() {
    return mTaskId;
  }

  public final void setTaskId(String value) {
    mTaskId = value;
  }
  ///#endregion

  ///#region 属性:ReceiverId
  private String mReceiverId;

  /**
   */
  public final String getReceiverId() {
    return mReceiverId;
  }

  public final void setReceiverId(String value) {
    mReceiverId = value;
  }
  ///#endregion

  ///#region 属性:Status
  private int mStatus;

  /**
   * 任务状态, 0表示未完成, 1表示完成.
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }
  ///#endregion

  ///#region 属性:IsRead
  private boolean mIsRead;

  /**
   */
  public final boolean getIsRead() {
    return mIsRead;
  }

  public final void setIsRead(boolean value) {
    mIsRead = value;
  }

  private java.time.LocalDateTime mFinishTime = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getFinishTime() {
    return mFinishTime;
  }

  public final void setFinishTime(java.time.LocalDateTime value) {
    mFinishTime = value;
  }
}
