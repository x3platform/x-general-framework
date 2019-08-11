package com.x3platform.membership.models;
import java.io.Serializable;
import java.util.Date;
/**
 *  组织单元信息
 */
public class OrganizationUnitInfo implements Serializable {

  private String id ;
  private String parentId;
  private String parentName;
  private String code ;
  private String name ;
  private String globalName ;
  private String fullName ;
  private String pinyin ;
  private Integer type ;
  private String typeView ;
  private Integer level ;
  private String standardOrganizationId ;
  private String standardOrganizationName ;
  private Integer enableEmail ;
  private Integer effectScope ;
  private Integer treeViewScope ;
  private int locking ;
  private String orderId ;
  private int status ;
  private String remark ;
  private String fullPath ;
  private String distinguishedName ;
  private Date modifiedDate ;
  private Date createdDate ;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
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

  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getTypeView() {
    return typeView;
  }

  public void setTypeView(String typeView) {
    this.typeView = typeView;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public String getStandardOrganizationId() {
    return standardOrganizationId;
  }

  public void setStandardOrganizationId(String standardOrganizationId) {
    this.standardOrganizationId = standardOrganizationId;
  }

  public String getStandardOrganizationName() {
    return standardOrganizationName;
  }

  public void setStandardOrganizationName(String standardOrganizationName) {
    this.standardOrganizationName = standardOrganizationName;
  }

  public Integer getEnableEmail() {
    return enableEmail;
  }

  public void setEnableEmail(Integer enableEmail) {
    this.enableEmail = enableEmail;
  }

  public Integer getEffectScope() {
    return effectScope;
  }

  public void setEffectScope(Integer effectScope) {
    this.effectScope = effectScope;
  }

  public Integer getTreeViewScope() {
    return treeViewScope;
  }

  public void setTreeViewScope(Integer treeViewScope) {
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

  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  public String getDistinguishedName() {
    return distinguishedName;
  }

  public void setDistinguishedName(String distinguishedName) {
    this.distinguishedName = distinguishedName;
  }

  public Date getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
