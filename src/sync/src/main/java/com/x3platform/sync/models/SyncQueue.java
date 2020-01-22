package com.x3platform.sync.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.models.Application;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.util.Date;

/**
 * @author ruanyu
 */
public class SyncQueue {

  /**
   * 默认构造函数
   */
  public SyncQueue() {
  }

  private String id = "";

  /**
   * 获取唯一标识
   *
   * @return 唯一标识
   */
  public String getId() {
    return id;
  }

  /**
   * 设置唯一标识
   *
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  private String applicationId = "";

  /**
   * 获取所属应用标识
   *
   * @return 所属应用标识
   */
  public String getApplicationId() {
    return applicationId;
  }

  private Application application;

  /**
   * 应用
   */
  @JSONField(serialize = false)
  public Application getApplication() {
    if (application == null && !StringUtil.isNullOrEmpty(getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(getApplicationId());
    }
    return application;
  }

  /**
   * 所属应用名称
   */
  public String getApplicationName() {
    return getApplication() == null ? "" : getApplication().getApplicationName();
  }

  /**
   * 所属应用显示名称
   */
  public String getApplicationDisplayName() {
    return getApplication() == null ? "" : getApplication().getApplicationDisplayName();
  }

  /**
   * 设置所属应用标识
   *
   * @param value 值
   */
  public void setApplicationId(String value) {
    applicationId = value;
  }

  private String queueName = "";

  /**
   * 获取队列名称
   *
   * @return 队列名称
   */
  public String getQueueName() {
    return queueName;
  }

  /**
   * 设置队列名称
   *
   * @param value 值
   */
  public void setQueueName(String value) {
    queueName = value;
  }

  private int priority = 0;

  /**
   * 获取优先级 1-低 | 2-中 | 3-高 | 4-最高
   *
   * @return 优先级 1-低 | 2-中 | 3-高 | 4-最高
   */
  public int getPriority() {
    return priority;
  }

  /**
   * 设置优先级 1-低 | 2-中 | 3-高 | 4-最高
   *
   * @param value 值
   */
  public void setPriority(int value) {
    priority = value;
  }

  private String lastPkgId = "";

  /**
   * 获取上一个包的标识
   *
   * @return 上一个包的标识
   */
  public String getLastPkgId() {
    return lastPkgId;
  }

  /**
   * 设置上一个包的标识
   *
   * @param value 值
   */
  public void setLastPkgId(String value) {
    lastPkgId = value;
  }

  private String orderId = "";

  /**
   * 获取排序
   *
   * @return 排序
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * 设置排序
   *
   * @param value 值
   */
  public void setOrderId(String value) {
    orderId = value;
  }

  private int status = 0;

  /**
   * 获取状态
   *
   * @return 状态
   */
  public int getStatus() {
    return status;
  }

  /**
   * 设置状态
   *
   * @param value 值
   */
  public void setStatus(int value) {
    status = value;
  }

  private Date modifiedDate = DateUtil.getDefaultDate();

  /**
   * 获取修改时间
   *
   * @return 修改时间
   */
  public Date getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置修改时间
   *
   * @param value 值
   */
  public void setModifiedDate(Date value) {
    modifiedDate = value;
  }

  private Date createdDate = DateUtil.getDefaultDate();

  /**
   * 获取创建时间
   *
   * @return 创建时间
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置创建时间
   *
   * @param value 值
   */
  public void setCreatedDate(Date value) {
    createdDate = value;
  }
}
