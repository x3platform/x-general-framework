package com.x3platform.membership.models;

import static com.x3platform.Constants.TEXT_EMPTY;
import static com.x3platform.membership.Constants.STANDARD_ORGANIZATION_UNIT_ROOT_ID;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.StandardOrganizationUnit;
import com.x3platform.util.*;

import java.math.*;
import java.time.LocalDateTime;
import java.util.*;
import org.dom4j.Element;

/**
 *
 * @author ruanyu
 */
public class StandardOrganizationUnitInfo implements StandardOrganizationUnit
{
  /**
   * 默认构造函数
   */
  public StandardOrganizationUnitInfo()
  {
  }

  private String id = "";

  /**
   * 获取
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  @JSONField(serialize = false)
  private StandardOrganizationUnit parent = null;

  public StandardOrganizationUnit getParent() {
    //
    // ParentId = "00000000-0000-0000-0000-000000000000" 表示父节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当ParentId = "00000000-0000-0000-0000-000000000000", 直接返回 null.
    if (this.getParentId() == STANDARD_ORGANIZATION_UNIT_ROOT_ID || StringUtil.isNullOrEmpty(this.getParentId())) {
      return null;
    }

    if (parent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      parent = MembershipManagement.getInstance().getStandardOrganizationUnitService().findOne(this.getParentId());
    }

    return parent;
  }

  private String parentId = "";

  /**
   * 获取
   * @return
   */
  public String getParentId() {
    return parentId;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setParentId(String value) {
    parentId = value;
  }

  @Override
  public String getParentName() {
    return this.getParent() == null ? TEXT_EMPTY : this.getParent().getName();
  }

  @Override
  public String getParentGlobalName() {
    return this.getParent() == null ? TEXT_EMPTY : this.getParent().getGlobalName();
  }

  private String code = "";

  /**
   * 获取
   * @return
   */
  public String getCode() {
    return code;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   * 获取
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setName(String value) {
    name = value;
  }

  private String globalName = "";

  /**
   * 获取
   * @return
   */
  public String getGlobalName() {
    return globalName;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setGlobalName(String value) {
    globalName = value;
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
   * @return
   */
  public int getLocking() {
    return locking;
  }

  /**
   * 设置
   * @param value 值
   */
  public void setLocking(int value) {
    locking = value;
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
   * 设置修改时间
   * @param value 值
   */
  public void setModifiedDate(LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 获取创建时间
   * @return 创建时间
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
