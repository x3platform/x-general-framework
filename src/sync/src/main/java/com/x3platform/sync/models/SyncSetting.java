package com.x3platform.sync.models;

import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.util.Date;

/**
 * @author ruanyu
 */
public class SyncSetting {

  /**
   * 默认构造函数
   */
  public SyncSetting() {
  }

  private String id = "";

  /**
   * 获取唯一标识
   *
   * @return 唯一标识
   */
  public String getId() {
    return id;
  }

  /**
   * 设置唯一标识
   *
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  private String originDataSourceName = "";

  /**
   * 获取源数据源名称
   *
   * @return 源数据源名称
   */
  public String getOriginDataSourceName() {
    return originDataSourceName;
  }

  /**
   * 设置源数据源名称
   *
   * @param value 值
   */
  public void setOriginDataSourceName(String value) {
    originDataSourceName = value;
  }

  private String targetDataSourceName = "";

  /**
   * 获取目标数据源名称
   *
   * @return 目标数据源名称
   */
  public String getTargetDataSourceName() {
    return StringUtil.isNullOrEmpty(targetDataSourceName) ? getOriginDataSourceName() : targetDataSourceName;
  }

  /**
   * 设置目标数据源名称
   *
   * @param value 值
   */
  public void setTargetDataSourceName(String value) {
    targetDataSourceName = value;
  }

  private String tableName = "";

  /**
   * 获取数据表名称
   *
   * @return 数据表名称
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * 设置数据表名称
   *
   * @param value 值
   */
  public void setTableName(String value) {
    tableName = value;
  }

  private String fields = "";

  /**
   * 获取数据字段
   *
   * @return 数据字段
   */
  public String getFields() {
    return fields;
  }

  /**
   * 设置数据字段
   *
   * @param value 值
   */
  public void setFields(String value) {
    fields = value;
  }

  private String modifiedDateField = "";

  /**
   * 获取修改时间的字段名称
   *
   * @return 修改时间的字段名称
   */
  public String getModifiedDateField() {
    return modifiedDateField;
  }

  /**
   * 设置修改时间的字段名称
   *
   * @param value 值
   */
  public void setModifiedDateField(String value) {
    modifiedDateField = value;
  }

  private String applicationIdField = "";

  /**
   * 获取应用标识的字段名称
   *
   * @return 应用标识的字段名称
   */
  public String getApplicationIdField() {
    return applicationIdField;
  }

  /**
   * 设置应用标识的字段名称
   *
   * @param value 值
   */
  public void setApplicationIdField(String value) {
    applicationIdField = value;
  }

  private int syncType = 0;

  /**
   * 获取同步类型 0-默认同步 1-根据组织标识同步
   *
   * @return 同步类型 0-默认同步 1-根据组织标识同步
   */
  public int getSyncType() {
    return syncType;
  }

  /**
   * 设置同步类型 0-默认同步 1-根据组织标识同步
   *
   * @param value 值
   */
  public void setSyncType(int value) {
    syncType = value;
  }

  private String orderId = "";

  /**
   * 获取排序
   *
   * @return 排序
   */
  public String getOrderId() {
    return orderId;
  }

  private int status;

  /**
   * 状态
   */
  public int getStatus() {
    return status;
  }

  public void setStatus(int value) {
    status = value;
  }

  /**
   * 设置排序
   *
   * @param value 值
   */
  public void setOrderId(String value) {
    orderId = value;
  }

  private Date modifiedDate = DateUtil.getDefaultDate();

  /**
   * 获取修改时间
   *
   * @return 修改时间
   */
  public Date getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置修改时间
   *
   * @param value 值
   */
  public void setModifiedDate(Date value) {
    modifiedDate = value;
  }

  private Date createdDate = DateUtil.getDefaultDate();

  /**
   * 获取创建时间
   *
   * @return 创建时间
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置创建时间
   *
   * @param value 值
   */
  public void setCreatedDate(Date value) {
    createdDate = value;
  }
}
