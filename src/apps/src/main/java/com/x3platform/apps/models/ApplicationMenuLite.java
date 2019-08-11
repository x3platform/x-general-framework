package com.x3platform.apps.models;

import com.x3platform.apps.AppsContext;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;

import java.util.*;

/**
 * 应用菜单查询类
 *
 * @author ruanyu
 */
public class ApplicationMenuLite {

  /**
   * 默认构造函数
   */
  public ApplicationMenuLite() {
  }

  private String mId = "";

  /**
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private Application mApplication;

  /**
   * 应用
   */
  public final Application getApplication() {
    if (mApplication == null && !StringUtil.isNullOrEmpty(this.getApplicationId())) {
      mApplication = AppsContext.getInstance().getApplicationService().findOne(this.getApplicationId());
    }

    return mApplication;
  }
  ///#endregion

  ///#region 属性:ApplicationId
  private String mApplicationId = "";

  /**
   */
  public final String getApplicationId() {
    return mApplicationId;
  }

  public final void setApplicationId(String value) {
    mApplicationId = value;
  }
  ///#endregion

  ///#region 属性:ApplicationName

  /**
   */
  public final String getApplicationName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationName();
  }
  ///#endregion

  ///#region 属性:ApplicationDisplayName

  /**
   */
  public final String getApplicationDisplayName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationDisplayName();
  }
  ///#endregion

  ///#region 属性:Parent
  private ApplicationMenu mParent;

  /**
   * 应用
   */
  public final ApplicationMenu getParent() {
    if (UUIDUtil.emptyString().equals(this.getParentId())) {
      return null;
    }

    if (mParent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      mParent = AppsContext.getInstance().getApplicationMenuService().findOne(this.getParentId());
    }

    return mParent;
  }
  ///#endregion

  ///#region 属性:ParentId
  private String mParentId = UUIDUtil.emptyString();

  /**
   */
  public final String getParentId() {
    return mParentId;
  }

  public final void setParentId(String value) {
    mParentId = value;
  }
  ///#endregion

  ///#region 属性:ParentName

  /**
   */
  public final String getParentName() {
    return this.getParent() == null ? this.getApplicationDisplayName() : this.getParent().getName();
  }
  ///#endregion

  ///#region 属性:Code
  private String mCode = "";

  /**
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }
  ///#endregion

  ///#region 属性:Name
  private String mName = "";

  /**
   */
  public final String getName() {
    return mName;
  }

  public final void setName(String value) {
    mName = value;
  }
  ///#endregion

  ///#region 属性:Description
  private String mDescription = "";

  /**
   */
  public final String getDescription() {
    return mDescription;
  }

  public final void setDescription(String value) {
    mDescription = value;
  }
  ///#endregion

  ///#region 属性:Url
  private String mUrl = "";

  /**
   */
  public final String getUrl() {
    return mUrl;
  }

  public final void setUrl(String value) {
    mUrl = value;
  }
  ///#endregion

  ///#region 属性:Target
  private String mTarget = "_self";

  /**
   */
  public final String getTarget() {
    return mTarget;
  }

  public final void setTarget(String value) {
    mTarget = value;
  }
  ///#endregion

  ///#region 属性:TargetView
  private String mTargetView = "";

  /**
   */
  public final String getTargetView() {
    if (StringUtil.isNullOrEmpty(mTargetView) && !StringUtil.isNullOrEmpty(this.getTarget())) {
      this.mTargetView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用链接打开方式", this.getTarget());
    }

    return mTargetView;
  }

  private String mMenuType = "ApplicationMenu";

  /**
   */
  public final String getMenuType() {
    return mMenuType;
  }

  public final void setMenuType(String value) {
    mMenuType = value;
  }

  private String mMenuTypeView = null;

  /**
   */
  public final String getMenuTypeView() {
    if (StringUtil.isNullOrEmpty(mMenuTypeView) && !StringUtil.isNullOrEmpty(this.getMenuType())) {
      this.mMenuTypeView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用菜单类别", mMenuType);
    }

    return mMenuTypeView;
  }
  ///#endregion

  ///#region 属性:IconPath
  private String mIconPath = "";

  /**
   */
  public final String getIconPath() {
    return mIconPath;
  }

  public final void setIconPath(String value) {
    mIconPath = value;
  }
  ///#endregion

  ///#region 属性:BigIconPath
  private String mBigIconPath = "";

  /**
   */
  public final String getBigIconPath() {
    return mBigIconPath;
  }

  public final void setBigIconPath(String value) {
    mBigIconPath = value;
  }
  ///#endregion

  ///#region 属性:DisplayType
  private String mDisplayType = "";

  /**
   * 显示方式
   */
  public final String getDisplayType() {
    return mDisplayType;
  }

  public final void setDisplayType(String value) {
    mDisplayType = value;
  }
  ///#endregion

  ///#region 属性:DisplayTypeView
  private String mDisplayTypeView = "";

  /**
   */
  public final String getDisplayTypeView() {
    if (StringUtil.isNullOrEmpty(mDisplayTypeView) && !StringUtil.isNullOrEmpty(this.getDisplayType())) {
      this.mDisplayTypeView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用菜单展现方式", this.getDisplayType());
    }

    return mDisplayTypeView;
  }

  private int mHasChild;

  /**
   */
  public final int getHasChild() {
    return mHasChild;
  }

  public final void setHasChild(int value) {
    mHasChild = value;
  }
  ///#endregion

  ///#region 属性:ContextObject
  private String mContextObject = "";

  /**
   * 上下文对象
   */
  public final String getContextObject() {
    return mContextObject;
  }

  public final void setContextObject(String value) {
    mContextObject = value;
  }
  ///#endregion

  ///#region 属性:OrderId
  private String mOrderId = "";

  /**
   */
  public final String getOrderId() {
    return mOrderId;
  }

  public final void setOrderId(String value) {
    mOrderId = value;
  }
  ///#endregion

  ///#region 属性:Status
  private int mStatus;

  /**
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }
  ///#endregion

  ///#region 属性:Remark
  private String mRemark = "";

  /**
   */
  public final String getRemark() {
    return mRemark;
  }

  public final void setRemark(String value) {
    mRemark = value;
  }

  private String mFullPath = "";

  /**
   */
  public final String getFullPath() {
    return mFullPath;
  }

  public final void setFullPath(String value) {
    mFullPath = value;
  }

  private java.time.LocalDateTime mModifiedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }

  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
  }
}
