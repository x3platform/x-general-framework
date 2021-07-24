package com.x3platform.apps.models;

import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.apps.AppsContext;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;

/**
 * 应用菜单精简信息
 *
 * @author ruanyu
 */
public class ApplicationMenuLite {

  /**
   * 默认构造函数
   */
  public ApplicationMenuLite() {
  }

  private String id = "";

  /**
   */
  public String getId() {
    return id;
  }

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
   */
  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String value) {
    applicationId = value;
  }

  /**
   */
  public String getApplicationName() {
    return getApplication() == null ? "" : getApplication().getApplicationName();
  }

  /**
   */
  public String getApplicationDisplayName() {
    return getApplication() == null ? "" : getApplication().getApplicationDisplayName();
  }

  private ApplicationMenu parent;

  /**
   * 应用
   */
  public ApplicationMenu getParent() {
    if (UUIDUtil.emptyString().equals(getParentId())) {
      return null;
    }

    if (parent == null && !StringUtil.isNullOrEmpty(getParentId())) {
      parent = AppsContext.getInstance().getApplicationMenuService().findOne(getParentId());
    }

    return parent;
  }

  private String parentId = UUIDUtil.emptyString();

  /**
   */
  public String getParentId() {
    return parentId;
  }

  public void setParentId(String value) {
    parentId = value;
  }

  /**
   */
  public String getParentName() {
    return getParent() == null ? getApplicationDisplayName() : getParent().getName();
  }

  private String code = "";

  /**
   */
  public String getCode() {
    return code;
  }

  public void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   */
  public String getName() {
    return name;
  }

  public void setName(String value) {
    name = value;
  }

  private String description = "";

  /**
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    description = value;
  }

  private String url = "";

  /**
   */
  public String getUrl() {
    return url;
  }

  public void setUrl(String value) {
    url = value;
  }

  private String target = "_self";

  /**
   */
  public String getTarget() {
    return target;
  }

  public void setTarget(String value) {
    target = value;
  }

  private String targetView = "";

  /**
   */
  public String getTargetView() {
    if (StringUtil.isNullOrEmpty(targetView) && !StringUtil.isNullOrEmpty(getTarget())) {
      targetView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用链接打开方式",
        getTarget());
    }

    return targetView;
  }

  private String menuType = "ApplicationMenu";

  /**
   */
  public String getMenuType() {
    return menuType;
  }

  public void setMenuType(String value) {
    menuType = value;
  }

  private String menuTypeView = null;

  /**
   */
  public String getMenuTypeView() {
    if (StringUtil.isNullOrEmpty(menuTypeView) && !StringUtil.isNullOrEmpty(getMenuType())) {
      menuTypeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用菜单类别", menuType);
    }

    return menuTypeView;
  }

  private String iconPath = "";

  /**
   */
  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String value) {
    iconPath = value;
  }

  private String bigIconPath = "";

  /**
   */
  public String getBigIconPath() {
    return bigIconPath;
  }

  public void setBigIconPath(String value) {
    bigIconPath = value;
  }

  private String displayType = "";

  /**
   * 显示方式
   */
  public String getDisplayType() {
    return displayType;
  }

  public void setDisplayType(String value) {
    displayType = value;
  }

  private String displayTypeView = "";

  /**
   */
  public String getDisplayTypeView() {
    if (StringUtil.isNullOrEmpty(displayTypeView) && !StringUtil.isNullOrEmpty(getDisplayType())) {
      displayTypeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用菜单展现方式",
        getDisplayType());
    }

    return displayTypeView;
  }

  private int hasChild;

  /**
   */
  public int getHasChild() {
    return hasChild;
  }

  public void setHasChild(int value) {
    hasChild = value;
  }

  private String contextObject = "";

  /**
   * 上下文对象
   */
  public String getContextObject() {
    return contextObject;
  }

  public void setContextObject(String value) {
    contextObject = value;
  }

  private String orderId = "";

  /**
   */
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String value) {
    orderId = value;
  }

  private int status;

  /**
   */
  public int getStatus() {
    return status;
  }

  public void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   */
  public String getRemark() {
    return remark;
  }

  public void setRemark(String value) {
    remark = value;
  }

  private String fullPath = "";

  /**
   */
  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String value) {
    fullPath = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }
}
