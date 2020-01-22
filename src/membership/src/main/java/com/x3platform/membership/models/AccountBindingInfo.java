package com.x3platform.membership.models;

import com.x3platform.membership.AccountBinding;
import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 *
 * @author ruanyu
 */
public class AccountBindingInfo implements AccountBinding
{
  /**
   * 默认构造函数
   */
  public AccountBindingInfo()
  {
  }

  private String accountId = "";

  /**
   * 获取帐号标识
   * @return 帐号标识
   */
  public String getAccountId() {
    return accountId;
  }

  /**
   * 设置帐号标识
   * @param value 值
   */
  public void setAccountId(String value) {
    accountId = value;
  }

  private String bindingType = "";

  /**
   * 获取绑定类型 WeChat - 微信, GeTui - 个推
   * @return 绑定类型 WeChat - 微信, GeTui - 个推
   */
  public String getBindingType() {
    return bindingType;
  }

  /**
   * 设置绑定类型 WeChat - 微信, GeTui - 个推
   * @param value 值
   */
  public void setBindingType(String value) {
    bindingType = value;
  }

  private String bindingObjectId = "";

  /**
   * 获取绑定对象唯一标识
   * @return 绑定对象唯一标识
   */
  public String getBindingObjectId() {
    return bindingObjectId;
  }

  /**
   * 设置绑定对象唯一标识
   * @param value 值
   */
  public void setBindingObjectId(String value) {
    bindingObjectId = value;
  }

  private String bindingOptions = "";

  /**
   * 获取绑定选项数据
   * @return 绑定选项数据
   */
  public String getBindingOptions() {
    return bindingOptions;
  }

  /**
   * 设置绑定选项数据
   * @param value 值
   */
  public void setBindingOptions(String value) {
    bindingOptions = value;
  }

  private Date modifiedDate = DateUtil.getDefaultDate();

  /**
   * 获取修改时间
   * @return 修改时间
   */
  public Date getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置修改时间
   * @param value 值
   */
  public void setModifiedDate(Date value) {
    modifiedDate = value;
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
