package com.x3platform.apps.models;

import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.cacheBuffer.ICacheable;
import com.x3platform.util.StringUtil;
import org.dom4j.Element;

/**
 * 应用参数设置信息
 */
public class ApplicationSettingInfo extends EntityClass implements ICacheable {

  /**
   * 默认构造函数
   */
  public ApplicationSettingInfo() {
  }

  private String mId;

  /**
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private ApplicationInfo mApplication;

  /**
   * 应用
   */
  public final ApplicationInfo getApplication() {
    if (mApplication == null && StringUtil.isNullOrEmpty(this.getApplicationId())) {
      mApplication = AppsContext.getInstance().getApplicationService().findOne(this.getApplicationId());
    }

    return mApplication;
  }

  private String mApplicationId;

  /**
   *
   */
  public final String getApplicationId() {
    return mApplicationId;
  }

  public final void setApplicationId(String value) {
    mApplicationId = value;
  }

  /**
   */
  public final String getApplicationName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationName();
  }

  public final String getApplicationDisplayName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationDisplayName();
  }

  private ApplicationSettingGroupInfo mApplicationSettingGroup;

  /**
   * 应用
   */
  public final ApplicationSettingGroupInfo getApplicationSettingGroup() {
    if (mApplicationSettingGroup == null && !StringUtil.isNullOrEmpty(this.getApplicationSettingGroupId())) {
      // FIXME
      // mApplicationSettingGroup = AppsContext.getInstance().getApplicationSettingGroupService().findOne(this.getApplicationSettingGroupId());
    }

    return mApplicationSettingGroup;
  }

  private String mApplicationSettingGroupId;

  /**
   */
  public final String getApplicationSettingGroupId() {
    return mApplicationSettingGroupId;
  }

  public final void setApplicationSettingGroupId(String value) {
    mApplicationSettingGroupId = value;
  }

  public final String getApplicationSettingGroupName() {
    return this.getApplicationSettingGroup() == null ? "" : this.getApplicationSettingGroup().getName();
  }

  /**
   */
  public final String getApplicationSettingGroupDisplayName() {
    return this.getApplicationSettingGroup() == null ? "" : this.getApplicationSettingGroup().getDisplayName();
  }

  private String mCode;

  /**
   * 代码
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }

  private String mText;

  /**
   * 文本
   */
  public final String getText() {
    return mText;
  }

  public final void setText(String value) {
    mText = value;
  }

  private String mValue;

  /**
   * 值
   */
  public final String getValue() {
    return mValue;
  }

  public final void setValue(String value) {
    mValue = value;
  }

  private String mOrderId;

  /**
   */
  public final String getOrderId() {
    return mOrderId;
  }

  public final void setOrderId(String value) {
    mOrderId = value;
  }

  private int mStatus;

  /**
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }

  private String mRemark;

  /**
   */
  public final String getRemark() {
    return mRemark;
  }

  public final void setRemark(String value) {
    mRemark = value;
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
  ///#endregion

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  ///#region 属性:EntityId

  /**
   * 实体对象标识
   */
  @Override
  public String getEntityId() {
    return this.getId();
  }
  ///#endregion

  // -------------------------------------------------------
  // 显式实现 ICacheable
  // -------------------------------------------------------

  ///#region 属性:Expires
  private java.time.LocalDateTime mExpires = java.time.LocalDateTime.MAX;

  /**
   * 过期时间
   */
  @Override
  public java.time.LocalDateTime getExpires() {
    return mExpires;
  }

  @Override
  public void setExpires(java.time.LocalDateTime value) {
    mExpires = value;
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

    this.setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("updateDate").getText()));
  }
}
