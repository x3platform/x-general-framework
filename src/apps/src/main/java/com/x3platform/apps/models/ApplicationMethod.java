package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.apps.AppsContext;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 应用方法信息
 *
 * @author ruanyu
 */
public class ApplicationMethod
{
  /**
   * 默认构造函数
   */
  public ApplicationMethod()
  {
  }

  private String id = "";

  /**
   * 获取方法的唯一标识
   * @return 方法的唯一标识
   */
  public String getId() {
    return id;
  }

  /**
   * 设置方法的唯一标识
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  @JSONField(serialize = false)
  private Application application;

  /**
   * 应用
   */
  public Application getApplication() {
    if (application == null && !StringUtil.isNullOrEmpty(getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(getApplicationId());
    }
    return application;
  }

  private String applicationId = "";

  /**
   * 获取所属应用唯一标识
   * @return 所属应用唯一标识
   */
  public String getApplicationId() {
    return applicationId;
  }

  /**
   * 设置所属应用唯一标识
   * @param value 值
   */
  public void setApplicationId(String value) {
    applicationId = value;
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

  private String code = "";

  /**
   * 获取代码
   * @return 代码
   */
  public String getCode() {
    return code;
  }

  /**
   * 设置代码
   * @param value 值
   */
  public void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   * 获取名称
   * @return 名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置名称
   * @param value 值
   */
  public void setName(String value) {
    name = value;
  }

  private String displayName = "";

  /**
   * 获取
   * @return
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setDisplayName(String value) {
    displayName = value;
  }

  private String description = "";

  /**
   * 获取
   * @return
   */
  public String getDescription() {
    return description;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setDescription(String value) {
    description = value;
  }

  private String detail = "";

  /**
   * 获取
   * @return
   */
  public String getDetail() {
    return detail;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setDetail(String value) {
    detail = value;
  }

  private String type = "";

  /**
   * 获取
   * @return
   */
  public String getType() {
    return type;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setType(String value) {
    type = value;
  }

  private String options = "";

  /**
   * 获取
   * @return
   */
  public String getOptions() {
    return options;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setOptions(String value) {
    options = value;
  }

  private int effectScope = 0;

  /**
   * 获取作用范围 1-匿名用户 2-登录用户 4-应用可访问成员 8-应用审查员 16-应用管理员
   * @return 作用范围
   */
  public int getEffectScope() {
    return effectScope;
  }

  /**
   * 设置作用范围
   * @param value 值
   */
  public void setEffectScope(int value) {
    effectScope = value;
  }

  private int version = 0;

  /**
   * 获取版本
   * @return 版本
   */
  public int getVersion() {
    return version;
  }

  /**
   * 设置版本
   * @param value 值
   */
  public void setVersion(int value) {
    version = value;
  }

  private String orderId = "";

  /**
   * 获取
   * @return
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setOrderId(String value) {
    orderId = value;
  }

  private int status = 0;

  /**
   * 获取
   * @return
   */
  public int getStatus() {
    return status;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   * 获取
   * @return
   */
  public String getRemark() {
    return remark;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setRemark(String value) {
    remark = value;
  }

  private LocalDateTime modifiedDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取
   * @return
   */
  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setModifiedDate(LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取
   * @return
   */
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }
}
