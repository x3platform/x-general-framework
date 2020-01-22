package com.x3platform.membership;

import com.x3platform.AuthorizationObject;


/**
 * 标准角色
 *
 * @author ruanyu
 */
public interface StandardRole extends AuthorizationObject {
  /**
   * 获取标识
   * @return 标识
   */
  String getId();

  /**
   * 设置标识
   * @param value 值
   */
  void setId(String value);

  /**
   * 名称
   */
  String getName();

  void setName(String value);

  /**
   * 编号
   */
  String getCode();

  void setCode(String value);

  /**
   * 类型 0:集团总部 1:地区地产公司 2:地区地产项目团队 11:地区商业公司 12:地区地产项目团队 21:地区物业公司 22:地区物业项目团队 65535:其他
   */
  int getType();

  void setType(int value);

  /**
   * 优先级
   */
  int getPriority();

  void setPriority(int value);

  /**
   * 父节点标识
   */
  String getParentId();

  void setParentId(String value);

  /**
   * 标准组织标识
   */
  String getStandardOrganizationUnitId();

  void setStandardOrganizationUnitId(String value);

  /**
   * 是否是关键角色
   */
  int getIsKey();

  void setIsKey(int value);

  /**
   * 排序标识
   */
  String getOrderId();

  void setOrderId(String value);
}
