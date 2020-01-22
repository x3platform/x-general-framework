package com.x3platform.apps.models;

import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 *
 * @author ruanyu
 */
public class ApplicationEvent
{
  /**
   * 默认构造函数
   */
  public ApplicationEvent()
  {
  }        

  private String id = "";

  /**
   * 获取唯一标识
   * @return 唯一标识
   */
  public String getId() {
    return id;
  }
  
  /**
   * 设置唯一标识
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }  

  private String applicationId = "";

  /**
   * 获取所属应用标识
   * @return 所属应用标识
   */
  public String getApplicationId() {
    return applicationId;
  }
  
  /**
   * 设置所属应用标识
   * @param value 值
   */
  public void setApplicationId(String value) {
    applicationId = value;
  }  

  private String level = "";

  /**
   * 获取事件级别
   * @return 事件级别
   */
  public String getLevel() {
    return level;
  }
  
  /**
   * 设置事件级别
   * @param value 值
   */
  public void setLevel(String value) {
    level = value;
  }  

  private String description = "";

  /**
   * 获取描述
   * @return 描述
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * 设置描述
   * @param value 值
   */
  public void setDescription(String value) {
    description = value;
  }  

  private Date startTime = DateUtil.getDefaultDate();

  /**
   * 获取开始时间
   * @return 开始时间
   */
  public Date getStartTime() {
    return startTime;
  }
  
  /**
   * 设置开始时间
   * @param value 值
   */
  public void setStartTime(Date value) {
    startTime = value;
  }  

  private Date finishTime = DateUtil.getDefaultDate();

  /**
   * 获取结束时间
   * @return 结束时间
   */
  public Date getFinishTime() {
    return finishTime;
  }
  
  /**
   * 设置结束时间
   * @param value 值
   */
  public void setFinishTime(Date value) {
    finishTime = value;
  }  

  private BigDecimal timespan = new BigDecimal(0.0);

  /**
   * 获取时间总计
   * @return 时间总计
   */
  public BigDecimal getTimespan() {
    return timespan;
  }
  
  /**
   * 设置时间总计
   * @param value 值
   */
  public void setTimespan(BigDecimal value) {
    timespan = value;
  }  

  private String ip = "";

  /**
   * 获取IP
   * @return IP
   */
  public String getIp() {
    return ip;
  }
  
  /**
   * 设置IP
   * @param value 值
   */
  public void setIp(String value) {
    ip = value;
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
