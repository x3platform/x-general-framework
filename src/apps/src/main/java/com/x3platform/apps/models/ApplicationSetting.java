package com.x3platform.apps.models;

import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.cachebuffer.Cacheable;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.dom4j.Element;

import java.util.Date;

/**
 * 应用参数设置信息
 */
public class ApplicationSetting extends EntityClass implements Cacheable {

  /**
   * 默认构造函数
   */
  public ApplicationSetting() {
  }

  private String id;

  /**
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  private Application application;

  /**
   * 应用
   */
  public Application getApplication() {
    if (application == null && StringUtil.isNullOrEmpty(this.getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(this.getApplicationId());
    }

    return application;
  }

  private String applicationId;

  /**
   *
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
    return this.getApplication() == null ? "" : this.getApplication().getApplicationName();
  }

  public String getApplicationDisplayName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationDisplayName();
  }

  private ApplicationSettingGroup applicationSettingGroup;

  /**
   * 应用
   */
  public ApplicationSettingGroup getApplicationSettingGroup() {
    if (applicationSettingGroup == null && !StringUtil.isNullOrEmpty(this.getApplicationSettingGroupId())) {
      // FIXME
      // applicationSettingGroup = AppsContext.getInstance().getApplicationSettingGroupService().findOne(this.getApplicationSettingGroupId());
    }

    return applicationSettingGroup;
  }

  private String applicationSettingGroupId;

  /**
   */
  public String getApplicationSettingGroupId() {
    return applicationSettingGroupId;
  }

  public void setApplicationSettingGroupId(String value) {
    applicationSettingGroupId = value;
  }

  public String getApplicationSettingGroupName() {
    return this.getApplicationSettingGroup() == null ? "" : this.getApplicationSettingGroup().getName();
  }

  /**
   */
  public String getApplicationSettingGroupDisplayName() {
    return this.getApplicationSettingGroup() == null ? "" : this.getApplicationSettingGroup().getDisplayName();
  }

  private String code;

  /**
   * 代码
   */
  public String getCode() {
    return code;
  }

  public void setCode(String value) {
    code = value;
  }

  private String text;

  /**
   * 文本
   */
  public String getText() {
    return text;
  }

  public void setText(String value) {
    text = value;
  }

  private String value;

  /**
   * 值
   */
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  private String orderId;

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

  private String remark;

  /**
   */
  public String getRemark() {
    return remark;
  }

  public void setRemark(String value) {
    remark = value;
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
  ///#endregion

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  /**
   * 实体对象标识
   */
  @Override
  public String getEntityId() {
    return this.getId();
  }

  // -------------------------------------------------------
  // 显式实现 Cacheable
  // -------------------------------------------------------

  private Date expires = DateUtil.getDefaultDate();

  /**
   * 获取过期时间
   */
  @Override
  public Date getExpires() {
    return expires;
  }

  /**
   * 设置过期时间
   */
  @Override
  public void setExpires(Date value) {
    expires = value;
  }

  // -------------------------------------------------------
  // 实现 EntityClass 序列化
  // -------------------------------------------------------

  /**
   * 序列化对象
   *
   * @return
   */
  @Override
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 序列化对象
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   * @return
   */
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    if (displayComment) {
      outString.append("<!-- 应用参数对象 -->");
    }
    outString.append("<setting>");
    if (displayComment) {
      outString.append("<!-- 应用参数标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    if (displayComment) {
      outString.append("<!-- 所属应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", this.getApplicationId()));
    if (displayComment) {
      outString.append("<!-- 所属应用参数分组标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationSettingGroupId><![CDATA[%1$s]]></applicationSettingGroupId>", this.getApplicationSettingGroupId()));
    if (displayComment) {
      outString.append("<!-- 编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", this.getCode()));
    if (displayComment) {
      outString.append("<!-- 参数文本信息 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<text><![CDATA[%1$s]]></text>", this.getText()));
    if (displayComment) {
      outString.append("<!-- 参数值信息 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<value><![CDATA[%1$s]]></value>", this.getValue()));
    if (displayComment) {
      outString.append("<!-- 排序编号(字符串) nvarchar(20) -->");
    }
    outString.append(String.format("<orderId><![CDATA[%1$s]]></orderId>", this.getOrderId()));
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", this.getStatus()));
    if (displayComment) {
      outString.append("<!-- 备注信息 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<remark><![CDATA[%1$s]]></remark>", this.getRemark()));
    if (displayComment) {
      outString.append("<!-- 最后更新时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<updateDate><![CDATA[%1$s]]></updateDate>", this.getModifiedDate()));
    outString.append("</setting>");

    return outString.toString();
  }

  /**
   * 反序列化对象
   *
   * @param element Xml元素
   */
  @Override
  public void deserialize(Element element) {
    this.setId(element.selectSingleNode("id").getText());
    this.setApplicationId(element.selectSingleNode("applicationId").getText());
    this.setApplicationSettingGroupId(element.selectSingleNode("applicationSettingGroupId").getText());
    this.setCode(element.selectSingleNode("code").getText());
    this.setText(element.selectSingleNode("text").getText());
    this.setValue(element.selectSingleNode("value").getText());
    this.setOrderId(element.selectSingleNode("orderId").getText());
    this.setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    this.setRemark(element.selectSingleNode("remark").getText());

    this.setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("modifiedDate").getText()));
  }
}
