package com.x3platform.membership;

import com.x3platform.AuthorizationObject;
import com.x3platform.util.DateUtil;

import java.io.Serializable;
import java.util.*;

/**
 * 帐号日志信息
 *
 * @author ruanyu
 */
public interface AccountLog {

  /**
   * 获取唯一标识
   *
   * @return 唯一标识
   */
  String getId();

  /**
   * 设置唯一标识
   *
   * @param value 值
   */
  void setId(String value);

  /**
   * 获取所属帐号标识
   *
   * @return 所属帐号标识
   */
  String getAccountId();

  /**
   * 设置所属帐号标识
   *
   * @param value 值
   */
  void setAccountId(String value);

  /**
   * 获取操作人标识
   *
   * @return 操作人标识
   */
  String getOperationBy();

  /**
   * 设置操作人标识
   *
   * @param value 值
   */
  void setOperationBy(String value);

  /**
   * 获取操作名称
   *
   * @return 操作名称
   */
  String getOperationName();

  /**
   * 设置操作名称
   *
   * @param value 值
   */
  void setOperationName(String value);

  /**
   * 获取描述
   *
   * @return 描述
   */
  String getDescription();

  /**
   * 设置描述
   *
   * @param value 值
   */
  void setDescription(String value);

  /**
   * 获取快照编号
   *
   * @return 快照编号
   */
  int getSnapshot();

  /**
   * 设置快照编号
   *
   * @param value 值
   */
  void setSnapshot(int value);

  /**
   * 获取创建时间
   *
   * @return 创建时间
   */
  Date getCreatedDate();

  /**
   * 设置创建时间
   *
   * @param value 值
   */
  void setCreatedDate(Date value);
}
