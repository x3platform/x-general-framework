package com.x3platform;

/**
 * 权限对象接口
 */
public interface IAuthorizationObject extends ISerializedObject {

  /**
   * 类型
   */
  String getAuthorizationObjectType();

  /**
   * 对象唯一标识
   */
  String getAuthorizationObjectId();

  void setAuthorizationObjectId(String value);

  /**
   * 名称
   */
  String getAuthorizationObjectName();

  void setAuthorizationObjectName(String value);

  /**
   * 防止意外删除 0 不锁定 | 1 锁定(默认)
   */
  int getLocking();

  void setLocking(int value);

  /**
   * 状态
   */
  int getStatus();

  void setStatus(int value);

  /**
   * 备注
   */
  String getRemark();

  void setRemark(String value);

  /**
   * 更新时间
   */
  java.time.LocalDateTime getModifiedDate();

  void setModifiedDate(java.time.LocalDateTime value);
}
