package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.apps.AppsContext;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.x3platform.Constants.TEXT_EMPTY;
import static com.x3platform.apps.Constants.APPLICATION_MENU_ROOT_ID;
import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

/**
 * 应用功能管理
 */
public class ApplicationFeature implements Serializable {

  public String id = ""; // 主键

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private Application application;

  /**
   * 所属应用
   */
  @JSONField(serialize = false)
  public Application getApplication() {
    if (application == null && !StringUtil.isNullOrEmpty(getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(getApplicationId());
    }
    return application;
  }

  public String applicationId = "";

  /**
   * 所属应用标识
   */
  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String value) {
    applicationId = value;
  }

  /**
   * 所属应用名称
   */
  public String getApplicationName() {
    return getApplication() == null ? TEXT_EMPTY : getApplication().getApplicationName();
  }

  /**
   * 所属应用显示名称
   */
  public String getApplicationDisplayName() {
    return getApplication() == null ? TEXT_EMPTY : getApplication().getApplicationDisplayName();
  }

  /**
   * 父级对象
   */
  @JSONField(serialize = false)
  private ApplicationFeature parent = null;

  /**
   * 父级对象
   */
  public ApplicationFeature getParent() {
    if (APPLICATION_MENU_ROOT_ID.equals(getParentId())) {
      return null;
    }

    if (parent == null && !StringUtil.isNullOrEmpty(getParentId())) {
      parent = AppsContext.getInstance().getApplicationFeatureService().findOne(getParentId());
    }

    return parent;
  }

  public String parentId = UUIDUtil.emptyString();

  /**
   * 父级对象标识
   */
  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  /**
   * 父级对象名称
   */
  public String getParentName() {
    return getParent() == null ? getApplicationDisplayName() : getParent().getName();
  }

  public String code = "";

  /**
   * 功能代码
   */
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  // 名称
  public String name = "";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // 显示名称
  public String displayName = "";

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  // 类别
  public String type = "";

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private String typeView = "";

  /**
   * 类别视图
   */
  public String getDisplayTypeView() {
    if (StringUtil.isNullOrEmpty(typeView) && !StringUtil.isNullOrEmpty(getType())) {
      typeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用功能类别", getType());
    }
    return typeView;
  }

  // 地址
  public String url = "";

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // 目标
  public String target = "";

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  // 图标路径
  public String iconPath = "";

  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String iconPath) {
    this.iconPath = iconPath;
  }

  // 大图标路径
  public String bigIconPath = "";

  public String getBigIconPath() {
    return bigIconPath;
  }

  public void setBigIconPath(String bigIconPath) {
    this.bigIconPath = bigIconPath;
  }

  // 帮助地址
  public String helpUrl = "";

  public String getHelpUrl() {
    return helpUrl;
  }

  public void setHelpUrl(String helpUrl) {
    this.helpUrl = helpUrl;
  }

  // 隐藏
  public boolean hidden = false;

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  // 作用范围
  public int effectScope = 1;

  public int getEffectScope() {
    return effectScope;
  }

  public void setEffectScope(int effectScope) {
    this.effectScope = effectScope;
  }

  // 树视图范围
  public int treeViewScope = 1;

  public int getTreeViewScope() {
    return treeViewScope;
  }

  public void setTreeViewScope(int treeViewScope) {
    this.treeViewScope = treeViewScope;
  }

  // 是否锁定
  public int locking = 1;

  public int getLocking() {
    return locking;
  }

  public void setLocking(int locking) {
    this.locking = locking;
  }

  // 排序
  public String orderId = "";

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  // 状态
  public int status = 1;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  // 备注
  public String remark = "";

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  // 修改时间
  public LocalDateTime modifiedDate = DateUtil.getDefaultLocalDateTime();

  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(LocalDateTime modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  // 创建时间
  public LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
