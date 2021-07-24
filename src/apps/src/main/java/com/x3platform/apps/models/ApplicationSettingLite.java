package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.apps.AppsContext;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;

/**
 * 应用参数设置精简信息
 */
public class ApplicationSettingLite {

  /**
   * 默认构造函数
   */
  public ApplicationSettingLite() {
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

  @JSONField(serialize = false)
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

  private String mCode = "";

  /**
   */
  public String getCode() {
    return mCode;
  }

  public void setCode(String value) {
    mCode = value;
  }

  private String mName = "";

  /**
   */
  public String getName() {
    return mName;
  }

  public void setName(String value) {
    mName = value;
  }

  private String mDescription = "";

  /**
   */
  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String value) {
    mDescription = value;
  }

  private String mUrl = "";

  /**
   */
  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String value) {
    mUrl = value;
  }

  private String mTarget = "_self";

  /**
   */
  public String getTarget() {
    return mTarget;
  }

  public void setTarget(String value) {
    mTarget = value;
  }

  private String mTargetView = "";

  /**
   */
  public String getTargetView() {
    if (StringUtil.isNullOrEmpty(mTargetView) && !StringUtil.isNullOrEmpty(getTarget())) {
      mTargetView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(),
        "应用管理_应用链接打开方式", getTarget());
    }

    return mTargetView;
  }

  private String mMenuType = "ApplicationMenu";

  /**
   */
  public String getMenuType() {
    return mMenuType;
  }

  public void setMenuType(String value) {
    mMenuType = value;
  }

  private String mMenuTypeView = null;

  /**
   */
  public String getMenuTypeView() {
    if (StringUtil.isNullOrEmpty(mMenuTypeView) && !StringUtil.isNullOrEmpty(getMenuType())) {
      mMenuTypeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(),
        "应用管理_应用菜单类别", mMenuType);
    }

    return mMenuTypeView;
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

  private String mBigIconPath = "";

  /**
   */
  public String getBigIconPath() {
    return mBigIconPath;
  }

  public void setBigIconPath(String value) {
    mBigIconPath = value;
  }
  ///#endregion

  ///#region 属性:DisplayType
  private String mDisplayType = "";

  /**
   * 显示方式
   */
  public String getDisplayType() {
    return mDisplayType;
  }

  public void setDisplayType(String value) {
    mDisplayType = value;
  }
  ///#endregion

  ///#region 属性:DisplayTypeView
  private String mDisplayTypeView = "";

  /**
   */
  public String getDisplayTypeView() {
    if (StringUtil.isNullOrEmpty(mDisplayTypeView) && !StringUtil.isNullOrEmpty(getDisplayType())) {
      mDisplayTypeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(),
        "应用管理_应用菜单展现方式", getDisplayType());
    }

    return mDisplayTypeView;
  }

  private int mHasChild;

  /**
   */
  public int getHasChild() {
    return mHasChild;
  }

  public void setHasChild(int value) {
    mHasChild = value;
  }

  private String mContextObject = "";

  /**
   * 上下文对象
   */
  public String getContextObject() {
    return mContextObject;
  }

  public void setContextObject(String value) {
    mContextObject = value;
  }

  private String mOrderId = "";

  /**
   */
  public String getOrderId() {
    return mOrderId;
  }

  public void setOrderId(String value) {
    mOrderId = value;
  }

  private int mStatus;

  /**
   */
  public int getStatus() {
    return mStatus;
  }

  public void setStatus(int value) {
    mStatus = value;
  }

  private String mRemark = "";

  /**
   */
  public String getRemark() {
    return mRemark;
  }

  public void setRemark(String value) {
    mRemark = value;
  }

  private String mFullPath = "";

  /**
   */
  public String getFullPath() {
    return mFullPath;
  }

  public void setFullPath(String value) {
    mFullPath = value;
  }

  private java.time.LocalDateTime mModifiedDate = DateUtil.getDefaultLocalDateTime();

  /**
   */
  public java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }

  private java.time.LocalDateTime mCreatedDate = DateUtil.getDefaultLocalDateTime();

  /**
   */
  public java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
  }
}
