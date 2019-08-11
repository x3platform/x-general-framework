package com.x3platform.membership;

import com.x3platform.SerializedObject;

import java.util.Date;

/**
 * 帐号委托信息
 */
public interface AccountGrant extends SerializedObject {

  /**
   * 委托标识
   */
  String getId();

  void setId(String value);

  /**
   * 编码
   */
  String getCode();

  void setCode(String value);

  /**
   * 委托人信息
   */
  Account getGrantor();

  /**
   * 委托人
   */
  String getGrantorId();

  void setGrantorId(String value);

  /**
   * 委托人姓名
   */
  String getGrantorName();

  /**
   * 被委托人信息
   */
  Account getGrantee();

  /**
   * 被委托人
   */
  String getGranteeId();

  void setGranteeId(String value);

  /**
   * 被委托人姓名
   */
  String getGranteeName();

  /**
   */
  Date getGrantedTimeFrom();

  void setGrantedTimeFrom(Date value);

  /**
   */
  Date getGrantedTimeTo();

  void setGrantedTimeTo(Date value);

  /**
   * 流程审批委托类别：1 未激活的流程审批只发待办给被委托的人 | 2 未激活的流程审批同时发送待办给委托人和被委托人 | 4 已激活的流程审批移交给被委托人 | 8 已激活的流程审批不移交给被委托人
   */
  int getWorkflowGrantMode();

  void setWorkflowGrantMode(int value);

  /**
   * 数据查询的模式：0 不委托 | 1 委托
   */
  int getDataQueryGrantMode();

  void setDataQueryGrantMode(int value);

  /**
   * 是否中止
   */
  boolean getIsAborted();

  void setIsAborted(boolean value);

  /**
   * 状态
   */
  int getStatus();

  void setStatus(int value);

  /**
   * 备注信息
   */
  String getRemark();

  void setRemark(String value);

  /**
   * 修改时间
   */
  Date getModifiedDate();

  void setModifiedDate(Date value);

  /**
   * 创建时间
   */
  Date getCreatedDate();

  void setCreatedDate(Date value);
}
