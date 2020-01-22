package com.x3platform.apps.models;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统应用设置，当应用不设置的时候以数据库设置为准, 直接序列化，支持分布式结构
 */
public class ApplicationOption implements Serializable {

  private  String applicationId ;
  private  String name;
  private  String label;
  private  String description;
  private  String value;
  private  Boolean isInternal;
  private  String orderId;
  private  int  status;
  private  String  remark;
  private  Date modifiedDate;
  private  Date createdDate;

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isInternal() {
    return isInternal;
  }

  public void setInternal(boolean internal) {
    isInternal = internal;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Date getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
