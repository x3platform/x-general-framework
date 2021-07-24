package com.x3platform.membership.models;

import com.x3platform.membership.Account;
import com.x3platform.membership.Organization;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 组织信息
 */
public class OrganizationInfo implements Organization {

  private String id;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String value) {
    this.id = value;
  }

  private String code;

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(String value) {
    this.code = value;
  }

  private String name;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String value) {
    this.name = value;
  }

  private String domain;

  @Override
  public String getDomain() {
    return domain;
  }

  @Override
  public void setDomain(String value) {
    this.domain = value;
  }

  private String license;

  @Override
  public String getLicense() {
    return license;
  }

  @Override
  public void setLicense(String value) {
    this.license = value;
  }

  private int status = 0;

  /**
   * 获取状态
   *
   * @return 状态
   */
  @Override
  public int getStatus() {
    return status;
  }

  /**
   * 设置状态
   *
   * @param value 值
   */
  @Override
  public void setStatus(int value) {
    status = value;
  }

  private LocalDateTime modifiedDate;

  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(LocalDateTime value) {
    this.modifiedDate = value;
  }

  private LocalDateTime createdDate;

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime value) {
    this.createdDate = value;
  }
}
