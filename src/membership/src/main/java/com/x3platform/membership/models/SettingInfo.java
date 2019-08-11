package com.x3platform.membership.models;

import com.x3platform.util.DateUtil;

import java.util.Date;

/**
 * 参数设置信息
 */
public class SettingInfo {

  /**
   * 默认构造函数
   */
  public SettingInfo() {

  }

  private String mId;

  /**
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private String mApplicationId = "00000000-0000-0000-0000-000000000100";

  /**
   */
  public final String getApplicationId() {
    return mApplicationId;
  }

  private String mApplicationSettingGroupId;

  /**
   */
  public final String getApplicationSettingGroupId() {
    return mApplicationSettingGroupId;
  }

  public final void setApplicationSettingGroupId(String value) {
    mApplicationSettingGroupId = value;
  }

  private String mCode;

  /**
   * 代码
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }

  private String mText;

  /**
   * 文本
   */
  public final String getText() {
    return mText;
  }

  public final void setText(String value) {
    mText = value;
  }

  private String mValue;

  /**
   * 值
   */
  public final String getValue() {
    return mValue;
  }

  public final void setValue(String value) {
    mValue = value;
  }

  private String mOrderId;

  /**
   */
  public final String getOrderId() {
    return mOrderId;
  }

  public final void setOrderId(String value) {
    mOrderId = value;
  }

  private int mStatus;

  /**
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }

  private String mRemark;

  /**
   */
  public final String getRemark() {
    return mRemark;
  }

  public final void setRemark(String value) {
    mRemark = value;
  }

  private Date mModifiedDate = DateUtil.getDefaultDate();

  /**
   */
  public final Date getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(Date value) {
    mModifiedDate = value;
  }

  private Date mCreatedDate = DateUtil.getDefaultDate();

  /**
   */
  public final Date getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(Date value) {
    mCreatedDate = value;
  }
}
