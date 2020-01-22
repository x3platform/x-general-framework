package com.x3platform.security.authority;

import com.x3platform.cachebuffer.Cacheable;
import com.x3platform.util.DateUtil;

import java.util.Date;

/**
 * 权限信息
 *
 * @author ruanyu
 */
public class Authority {

  /**
   * 构造函数
   */
  public Authority() {
  }

  private String id;

  /**
   * 唯一标识
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  private String name;

  /**
   * 名称
   */
  public String getName() {
    return this.name;
  }

  public void setName(String value) {
    this.name = value;
  }

  private String description;

  /**
   * 描述信息
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    description = value;
  }

  private int locking;

  /**
   * 锁定 0=未锁定, 1=锁定
   */
  public int getLocking() {
    return locking;
  }

  public void setLocking(int value) {
    locking = value;
  }

  private String tags;

  /**
   * 标签
   */
  public String getTags() {
    return tags;
  }

  public void setTags(String value) {
    tags = value;
  }

  private String orderId;

  /**
   */
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String value) {
    orderId = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改时间
   */
  public java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建时间
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }
}
