package com.x3platform.apps.models;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  应用功能管理
 */
public class ApplicationFeature  implements Serializable {

  public String id = ""; // 主键
  public String applicationId = "";// 应用id
  public String applicationName = "";// 应用id

  public String parentId = "";    // 父级功能
  public String parentName = "";  // 父级功能
  public String code = "";        // 功能代码
  public String name = "";        // 功能全称路径 ；  ORC应用管理平台_菜单管理_新增
  public String displayName = ""; // 显示名称
  public String type = "";        // 分类
  public String url = "";         // 连接
  public String target = "";      // target
  public String iconPath = "";    // iconPath
  public String bigIconPath = ""; // 大图标
  public String helpUrl = "";     // 帮助连接
  public boolean hidden = false;    // 是否隐藏
  public int effectScope = 1;//是否
  public int treeViewScope = 1;// 树上范围
  public int locking = 1;// 是否锁定
  public String orderId = "" ;// orderId
  public int status =1;// 状态,备注
  public String remark = "";// 备注
  public Timestamp modifiedDate;// 修改时间
  public Timestamp createdDate; // 创建时间

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
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

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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

  public String getHelpUrl() {
    return helpUrl;
  }

  public void setHelpUrl(String helpUrl) {
    this.helpUrl = helpUrl;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public int getEffectScope() {
    return effectScope;
  }

  public void setEffectScope(int effectScope) {
    this.effectScope = effectScope;
  }

  public int getTreeViewScope() {
    return treeViewScope;
  }

  public void setTreeViewScope(int treeViewScope) {
    this.treeViewScope = treeViewScope;
  }

  public int getLocking() {
    return locking;
  }

  public void setLocking(int locking) {
    this.locking = locking;
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

  public Timestamp getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Timestamp modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }
}
