package com.x3platform.membership.models;

import static com.x3platform.Constants.TEXT_EMPTY;
import static com.x3platform.membership.Constants.STANDARD_ORGANIZATION_UNIT_ROOT_ID;
import static com.x3platform.membership.Constants.STANDARD_ROLE_ROOT_ID;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.StandardOrganizationUnit;
import com.x3platform.membership.StandardRole;
import com.x3platform.util.*;

import java.math.*;
import java.time.LocalDateTime;
import java.util.*;
import org.dom4j.Element;

/**
 * @author ruanyu
 */
public class StandardRoleInfo implements StandardRole {

  /**
   * 默认构造函数
   */
  public StandardRoleInfo() {
  }

  private String id = "";

  /**
   * 获取
   */
  public String getId() {
    return id;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  @JSONField(serialize = false)
  private StandardRole parent = null;

  public StandardRole getParent() {
    //
    // ParentId = "00000000-0000-0000-0000-000000000000" 表示父节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当ParentId = "00000000-0000-0000-0000-000000000000", 直接返回 null.
    if (this.getParentId() == STANDARD_ROLE_ROOT_ID || StringUtil.isNullOrEmpty(this.getParentId())) {
      return null;
    }

    if (parent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      parent = MembershipManagement.getInstance().getStandardRoleService().findOne(this.getParentId());
    }

    return parent;
  }

  private String parentId = "";

  /**
   * 获取
   */
  public String getParentId() {
    return parentId;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setParentId(String value) {
    parentId = value;
  }

  public String getParentName() {
    return this.getParent() == null ? TEXT_EMPTY : this.getParent().getName();
  }

  private String code = "";

  /**
   * 获取
   */
  public String getCode() {
    return code;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   * 获取
   */
  public String getName() {
    return name;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setName(String value) {
    name = value;
  }

  private int type = 0;

  /**
   * 获取
   */
  public int getType() {
    return type;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setType(int value) {
    type = value;
  }

  private int priority = 0;

  /**
   * 获取
   */
  public int getPriority() {
    return priority;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setPriority(int value) {
    priority = value;
  }

  @JSONField(serialize = false)
  private StandardOrganizationUnit standardOrganizationUnit = null;

  public StandardOrganizationUnit getStandardOrganizationUnit() {
    //
    // ParentId = "00000000-0000-0000-0000-000000000000" 表示父节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当ParentId = "00000000-0000-0000-0000-000000000000", 直接返回 null.
    if (STANDARD_ORGANIZATION_UNIT_ROOT_ID.equals(this.getStandardOrganizationUnitId())
      || StringUtil.isNullOrEmpty(this.getStandardOrganizationUnitId())) {
      return null;
    }

    if (standardOrganizationUnit == null && !StringUtil.isNullOrEmpty(this.getStandardOrganizationUnitId())) {
      standardOrganizationUnit = MembershipManagement.getInstance().getStandardOrganizationUnitService()
        .findOne(this.getStandardOrganizationUnitId());
    }

    return standardOrganizationUnit;
  }

  private String standardOrganizationUnitId = "";

  /**
   * 获取
   */
  @Override
  public String getStandardOrganizationUnitId() {
    return standardOrganizationUnitId;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  @Override
  public void setStandardOrganizationUnitId(String value) {
    standardOrganizationUnitId = value;
  }

  public String getStandardOrganizationUnitName() {
    return this.getStandardOrganizationUnit() == null ? TEXT_EMPTY : this.getStandardOrganizationUnit().getName();
  }

  public String getStandardOrganizationUnitGlobalName() {
    return this.getStandardOrganizationUnit() == null ? TEXT_EMPTY : this.getStandardOrganizationUnit().getGlobalName();
  }

  private int isKey = 0;

  /**
   * 获取
   */
  public int getIsKey() {
    return isKey;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setIsKey(int value) {
    isKey = value;
  }

  private int locking = 0;

  @Override
  public String getAuthorizationObjectType() {
    return null;
  }

  @Override
  public String getAuthorizationObjectId() {
    return null;
  }

  @Override
  public void setAuthorizationObjectId(String value) {

  }

  @Override
  public String getAuthorizationObjectName() {
    return null;
  }

  @Override
  public void setAuthorizationObjectName(String value) {

  }

  /**
   * 获取
   */
  public int getLocking() {
    return locking;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setLocking(int value) {
    locking = value;
  }

  private String orderId = "";

  /**
   * 获取
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setOrderId(String value) {
    orderId = value;
  }

  private int status = 0;

  /**
   * 获取
   */
  public int getStatus() {
    return status;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   * 获取
   */
  public String getRemark() {
    return remark;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setRemark(String value) {
    remark = value;
  }

  private LocalDateTime modifiedDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取
   */
  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setModifiedDate(LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取
   */
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置
   *
   * @param value 值
   */
  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }

  @Override
  public String serializable() {
    return null;
  }

  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    return null;
  }

  @Override
  public void deserialize(Element element) {

  }
}
