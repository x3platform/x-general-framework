package com.x3platform.apps.models;

import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.cachebuffer.Cacheable;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.dom4j.Element;

import java.util.Date;

/**
 */
public class ApplicationSettingGroup extends EntityClass implements Cacheable {

  /**
   * 默认构造函数
   */
  public ApplicationSettingGroup() {
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

  private String mApplicationId;

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

  private ApplicationSettingGroup mParent;

  /**
   * 应用
   */
  public final ApplicationSettingGroup getParent() {
    if (mParent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      // FIXME
      // mParent = AppsContext.getInstance().getApplicationSettingGroupService().findOne(this.getParentId());
    }

    return mParent;
  }
  ///#endregion

  ///#region 属性:ParentId
  private String mParentId;

  /**
   */
  public final String getParentId() {
    return mParentId;
  }

  public final void setParentId(String value) {
    mParentId = value;
  }

  /**
   */
  public final String getParentName() {
    return this.getParent() == null ? this.getApplicationDisplayName() : this.getParent().getName();
  }

  /**
   */
  public final String getParentDisplayName() {
    return this.getParent() == null ? "" : this.getParent().getDisplayName();
  }

  private String mCode;

  /**
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }

  private String mName;

  /**
   */
  public final String getName() {
    return mName;
  }

  public final void setName(String value) {
    mName = value;
  }

  private String mDisplayName;

  /**
   */
  public final String getDisplayName() {
    if (StringUtil.isNullOrEmpty(this.mDisplayName)) {
      this.mDisplayName = this.getName();
    }

    return mDisplayName;
  }

  public final void setDisplayName(String value) {
    mDisplayName = value;
  }

  private int mContentType;

  /**
   */
  public final int getContentType() {
    return mContentType;
  }

  public final void setContentType(int value) {
    mContentType = value;
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

  private Date mExpires = DateUtil.getDefaultDate();

  /**
   * 过期时间
   */
  @Override
  public Date getExpires() {
    return mExpires;
  }

  @Override
  public void setExpires(Date value) {
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
      outString.append("<!-- 应用参数分组对象 -->");
    }
    outString.append("<settingGroup>");
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
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", this.getParentId()));
    if (displayComment) {
      outString.append("<!-- 编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", this.getCode()));
    if (displayComment) {
      outString.append("<!-- 名称 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<text><![CDATA[%1$s]]></text>", this.getName()));
    if (displayComment) {
      outString.append("<!-- 显示名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<displayName><![CDATA[%1$s]]></displayName>", this.getDisplayName()));
    if (displayComment) {
      outString.append("<!-- 内容类型 (整型) (int) -->");
    }
    outString.append(String.format("<contentType><![CDATA[%1$s]]></contentType>", this.getContentType()));
    if (displayComment) {
      outString.append("<!-- 排序编号 (字符串) (nvarchar(20)) -->");
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
    outString.append("</settingGroup>");

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
    this.setParentId(element.selectSingleNode("parentId").getText());
    this.setCode(element.selectSingleNode("code").getText());
    this.setName(element.selectSingleNode("text").getText());
    this.setDisplayName(element.selectSingleNode("displayName").getText());
    this.setContentType(Integer.parseInt(element.selectSingleNode("contentType").getText()));
    this.setOrderId(element.selectSingleNode("orderId").getText());
    this.setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    this.setRemark(element.selectSingleNode("remark").getText());
    this.setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("updateDate").getText()));
  }
}
