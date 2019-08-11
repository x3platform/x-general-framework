package com.x3platform;

import java.util.Date;

/**
 * 权限对象接口
 *
 * @author ruanyu
 */
public interface AuthorizationObject extends SerializedObject {

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
   * 获取状态
   *
   * @return 状态
   */
  int getStatus();

  /**
   * 设置状态
   *
   * @param value 值
   */
  void setStatus(int value);

  /**
   * 备注
   */
  String getRemark();

  void setRemark(String value);

  /**
   * 更新时间
   */
  Date getModifiedDate();

  void setModifiedDate(Date value);
}
