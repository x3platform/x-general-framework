package com.x3platform.security.verificationcode;

import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 *
 * @author ruanyu
 */
public class VerificationCode
{
  /**
   * 默认构造函数
   */
  public VerificationCode()
  {
  }        

  private String id = "";

  /**
   * 获取记录唯一标识
   * @return 记录唯一标识
   */
  public String getId() {
    return id;
  }
  
  /**
   * 设置记录唯一标识
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }  

  private String ip = "";

  /**
   * 获取IP 地址
   * @return IP 地址
   */
  public String getIp() {
    return ip;
  }
  
  /**
   * 设置IP 地址
   * @param value 值
   */
  public void setIp(String value) {
    ip = value;
  }  

  private String objectType = "";

  /**
   * 获取对象类型 Mobile 手机 Mail 邮箱
   * @return 对象类型 Mobile 手机 Mail 邮箱
   */
  public String getObjectType() {
    return objectType;
  }
  
  /**
   * 设置对象类型 Mobile 手机 Mail 邮箱
   * @param value 值
   */
  public void setObjectType(String value) {
    objectType = value;
  }  

  private String objectValue = "";

  /**
   * 获取对象值
   * @return 对象值
   */
  public String getObjectValue() {
    return objectValue;
  }
  
  /**
   * 设置对象值
   * @param value 值
   */
  public void setObjectValue(String value) {
    objectValue = value;
  }  

  private String code = "";

  /**
   * 获取验证码
   * @return 验证码
   */
  public String getCode() {
    return code;
  }
  
  /**
   * 设置验证码
   * @param value 值
   */
  public void setCode(String value) {
    code = value;
  }  

  private String type = "";

  /**
   * 获取验证类型
   * @return 验证类型
   */
  public String getType() {
    return type;
  }
  
  /**
   * 设置验证类型
   * @param value 值
   */
  public void setType(String value) {
    type = value;
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
