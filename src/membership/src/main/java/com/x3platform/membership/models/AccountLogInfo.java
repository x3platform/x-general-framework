package com.x3platform.membership.models;

import com.x3platform.membership.AccountLog;
import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 * 帐号日志信息
 *
 * @author ruanyu
 */
public class AccountLogInfo implements AccountLog
{
  /**
   * 默认构造函数
   */
  public AccountLogInfo()
  {
  }

  private String id = "";

  /**
   * 获取唯一标识
   * @return 唯一标识
   */
  public String getId() {
    return id;
  }

  /**
   * 设置唯一标识
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  private String accountId = "";

  /**
   * 获取所属帐号标识
   * @return 所属帐号标识
   */
  public String getAccountId() {
    return accountId;
  }

  /**
   * 设置所属帐号标识
   * @param value 值
   */
  public void setAccountId(String value) {
    accountId = value;
  }

  private String operationBy = "";

  /**
   * 获取操作人标识
   * @return 操作人标识
   */
  public String getOperationBy() {
    return operationBy;
  }

  /**
   * 设置操作人标识
   * @param value 值
   */
  public void setOperationBy(String value) {
    operationBy = value;
  }

  private String operationName = "";

  /**
   * 获取操作名称
   * @return 操作名称
   */
  public String getOperationName() {
    return operationName;
  }

  /**
   * 设置操作名称
   * @param value 值
   */
  public void setOperationName(String value) {
    operationName = value;
  }

  private String description = "";

  /**
   * 获取描述
   * @return 描述
   */
  public String getDescription() {
    return description;
  }

  /**
   * 设置描述
   * @param value 值
   */
  public void setDescription(String value) {
    description = value;
  }

  private int snapshot = 0;

  /**
   * 获取快照编号
   * @return 快照编号
   */
  public int getSnapshot() {
    return snapshot;
  }

  /**
   * 设置快照编号
   * @param value 值
   */
  public void setSnapshot(int value) {
    snapshot = value;
  }

  private Date createdDate = DateUtil.getDefaultDate();

  /**
   * 获取创建时间
   * @return 创建时间
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置创建时间
   * @param value 值
   */
  public void setCreatedDate(Date value) {
    createdDate = value;
  }
}
