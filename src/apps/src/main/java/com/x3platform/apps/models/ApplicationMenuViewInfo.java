package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.dom4j.Element;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用菜单
 */
public class ApplicationMenuViewInfo implements Serializable {



  /**
   * 默认构造函数
   */
  public ApplicationMenuViewInfo() {
  }

  private String id = "";
  private Application application;
  private String applicationId = "";


  // 父级菜单对象
  @JSONField(serialize = false)
  private ApplicationMenuViewInfo parent;

  /**
   * 应用
   */


  private String parentId = UUIDUtil.emptyString();


  /**
   * 父级名称
   */

  private String code = "";

  /**
   */
  private String name = "";

  /**
   */
  private String description = "";

  /**
   */
  private String url = "";

  /**
   */
  // 显示方式
  private String target = "_self";

  /**
   */
  private String targetView = "";

  /**
   */

  private String menuType = "ApplicationMenu";

  /**
   */
  private String menuTypeView = null;

  private String iconPath = "";

  /**
   */
  ///#endregion

  ///#region 属性:BigIconPath
  private String bigIconPath = "";

  /**
   */
  ///#endregion

  ///#region 属性:DisplayType
  private String displayType = "";

  /**
   * 显示方式
   */
  private String displayTypeView = "";

  private int hasChild;

  /**
   */
  private String contextObject = "";

  /**
   * 上下文对象
   */
  private String orderId = "";

  /**
   */
  private int status;

  /**
   */
  private String remark = "";

  /**
   */
  private String fullPath = "";

  /**
   */

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   */
  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   */
  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------
  private String authorizationReadScopeObjectText = null;
  private String authorizationReadScopeObjectView = null;
  ///#endregion

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------
  /**
   * 树形的节点数据
   */
  public List<Object> childNodes = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public ApplicationMenuViewInfo getParent() {
    return parent;
  }

  public void setParent(ApplicationMenuViewInfo parent) {
    this.parent = parent;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getTargetView() {
    return targetView;
  }

  public void setTargetView(String targetView) {
    this.targetView = targetView;
  }

  public String getMenuType() {
    return menuType;
  }

  public void setMenuType(String menuType) {
    this.menuType = menuType;
  }

  public String getMenuTypeView() {
    return menuTypeView;
  }

  public void setMenuTypeView(String menuTypeView) {
    this.menuTypeView = menuTypeView;
  }

  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String iconPath) {
    this.iconPath = iconPath;
  }

  public String getBigIconPath() {
    return bigIconPath;
  }

  public void setBigIconPath(String bigIconPath) {
    this.bigIconPath = bigIconPath;
  }

  public String getDisplayType() {
    return displayType;
  }

  public void setDisplayType(String displayType) {
    this.displayType = displayType;
  }

  public String getDisplayTypeView() {
    return displayTypeView;
  }

  public void setDisplayTypeView(String displayTypeView) {
    this.displayTypeView = displayTypeView;
  }

  public int getHasChild() {
    return hasChild;
  }

  public void setHasChild(int hasChild) {
    this.hasChild = hasChild;
  }

  public String getContextObject() {
    return contextObject;
  }

  public void setContextObject(String contextObject) {
    this.contextObject = contextObject;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(LocalDateTime modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getAuthorizationReadScopeObjectText() {
    return authorizationReadScopeObjectText;
  }

  public void setAuthorizationReadScopeObjectText(String authorizationReadScopeObjectText) {
    this.authorizationReadScopeObjectText = authorizationReadScopeObjectText;
  }

  public String getAuthorizationReadScopeObjectView() {
    return authorizationReadScopeObjectView;
  }

  public void setAuthorizationReadScopeObjectView(String authorizationReadScopeObjectView) {
    this.authorizationReadScopeObjectView = authorizationReadScopeObjectView;
  }

  public List<Object> getChildNodes() {
    return childNodes;
  }

  public void setChildNodes(List<Object> childNodes) {
    this.childNodes = childNodes;
  }
}
