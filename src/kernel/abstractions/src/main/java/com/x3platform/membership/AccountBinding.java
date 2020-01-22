package com.x3platform.membership;

import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 * @author ruanyu
 */
public interface AccountBinding {

  /**
   * 获取帐号标识
   *
   * @return 帐号标识
   */
  String getAccountId();

  /**
   * 设置帐号标识
   *
   * @param value 值
   */
  void setAccountId(String value);

  /**
   * 获取绑定类型 WeChat - 微信, GeTui - 个推
   *
   * @return 绑定类型 WeChat - 微信, GeTui - 个推
   */
  String getBindingType();

  /**
   * 设置绑定类型 WeChat - 微信, GeTui - 个推
   *
   * @param value 值
   */
  void setBindingType(String value);

  /**
   * 获取绑定对象唯一标识
   *
   * @return 绑定对象唯一标识
   */
  String getBindingObjectId();

  /**
   * 设置绑定对象唯一标识
   *
   * @param value 值
   */
  void setBindingObjectId(String value);

  /**
   * 获取绑定选项数据
   *
   * @return 绑定选项数据
   */
  String getBindingOptions();

  /**
   * 设置绑定选项数据
   *
   * @param value 值
   */
  void setBindingOptions(String value);

  /**
   * 获取修改时间
   *
   * @return 修改时间
   */
  Date getModifiedDate();

  /**
   * 设置修改时间
   *
   * @param value 值
   */
  void setModifiedDate(Date value);

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
