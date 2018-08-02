package com.x3platform.tasks.models;

/**
 * 任务工作项信息
 */
public class TaskWorkItemInfo {

  /**
   * 默认构造函数
   */
  public TaskWorkItemInfo() {
  }

  private String mId;

  /**
   */
  public final String getId() {
    return this.mId;
  }

  public final void setId(String value) {
    this.mId = value;
  }

  private String mTrackingToken;

  /**
   * 跟踪标记
   */
  public final String getTrackingToken() {
    return this.mTrackingToken;
  }

  public final void setTrackingToken(String value) {
    this.mTrackingToken = value;
  }

  private String mTaskCode;

  /**
   */
  public final String getTaskCode() {
    return this.mTaskCode;
  }

  public final void setTaskCode(String value) {
    this.mTaskCode = value;
  }

  private String mApplicationId;

  /**
   */
  public final String getApplicationId() {
    return this.mApplicationId;
  }

  public final void setApplicationId(String value) {
    this.mApplicationId = value;
  }

  private String mType;

  /**
   */
  public final String getType() {
    return this.mType;
  }

  public final void setType(String value) {
    this.mType = value;
  }

  private String mTitle;

  /**
   */
  public final String getTitle() {
    return this.mTitle;
  }

  public final void setTitle(String value) {
    this.mTitle = value;
  }

  private String mContent;

  /**
   */
  public final String getContent() {
    return this.mContent;
  }

  public final void setContent(String value) {
    this.mContent = value;
  }

  private String mTags;

  /**
   */
  public final String getTags() {
    return this.mTags;
  }

  public final void setTags(String value) {
    this.mTags = value;
  }

  private String mSenderId;

  /**
   */
  public final String getSenderId() {
    return this.mSenderId;
  }

  public final void setSenderId(String value) {
    this.mSenderId = value;
  }

  private String mReceiverId;

  /**
   */
  public final String getReceiverId() {
    return this.mReceiverId;
  }

  public final void setReceiverId(String value) {
    this.mReceiverId = value;
  }

  private boolean mIsRead;

  /**
   */
  public final boolean getIsRead() {
    return this.mIsRead;
  }

  public final void setIsRead(boolean value) {
    this.mIsRead = value;
  }

  private int mStatus;

  /**
   */
  public final int getStatus() {
    return this.mStatus;
  }

  public final void setStatus(int value) {
    this.mStatus = value;
  }

  private java.time.LocalDateTime mFinishTime = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getFinishTime() {
    return this.mFinishTime;
  }

  public final void setFinishTime(java.time.LocalDateTime value) {
    this.mFinishTime = value;
  }

  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return this.mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    this.mCreatedDate = value;
  }
}
