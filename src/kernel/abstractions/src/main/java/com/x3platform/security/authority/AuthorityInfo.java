package com.x3platform.security.authority;

import com.x3platform.cacheBuffer.ICacheable;

/**
 * 权限信息
 */
public class AuthorityInfo implements ICacheable {

  /**
   * 构造函数
   */
  public AuthorityInfo() {
  }

  private String mId;

  /**
   * 唯一标识
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private String mName;

  /**
   * 名称
   */
  public final String getName() {
    return this.mName;
  }

  public final void setName(String value) {
    this.mName = value;
  }

  private String mDescription;

  /**
   * 描述信息
   */
  public final String getDescription() {
    return mDescription;
  }

  public final void setDescription(String value) {
    mDescription = value;
  }

  private int mLocking;

  /**
   * 锁定 0=未锁定, 1=锁定
   */
  public final int getLocking() {
    return mLocking;
  }

  public final void setLocking(int value) {
    mLocking = value;
  }

  private String mTags;

  /**
   * 标签
   */
  public final String getTags() {
    return mTags;
  }

  public final void setTags(String value) {
    mTags = value;
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

  private java.time.LocalDateTime mModifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改时间
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }

  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.MIN;

  /**
   * 创建时间
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
  }

  // -------------------------------------------------------
  // 显式实现 ICacheable
  // -------------------------------------------------------

  private java.time.LocalDateTime mExpires = java.time.LocalDateTime.MAX;

  /**
   * 过期时间
   */
  public java.time.LocalDateTime getExpires() {
    return mExpires;
  }

  public void setExpires(java.time.LocalDateTime value) {
    mExpires = value;
  }
}
