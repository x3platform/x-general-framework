package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.cachebuffer.Cacheable;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.util.Date;
import org.dom4j.Element;

import static com.x3platform.Constants.TEXT_EMPTY;

/**
 */
public class ApplicationSettingGroup extends EntityClass implements Cacheable {

  /**
   * 默认构造函数
   */
  public ApplicationSettingGroup() {
  }

  private String id;

  /**
   */
  public final String getId() {
    return id;
  }

  public final void setId(String value) {
    id = value;
  }

  @JSONField(serialize = false)
  private Application application;

  /**
   * 应用
   */
  public final Application getApplication() {
    if (application == null && !StringUtil.isNullOrEmpty(getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(getApplicationId());
    }

    return application;
  }

  private String applicationId;

  /**
   */
  public final String getApplicationId() {
    return applicationId;
  }

  public final void setApplicationId(String value) {
    applicationId = value;
  }

  /**
   */
  public final String getApplicationName() {
    return getApplication() == null ? "" : getApplication().getApplicationName();
  }

  /**
   */
  public final String getApplicationDisplayName() {
    return getApplication() == null ? "" : getApplication().getApplicationDisplayName();
  }

  @JSONField(serialize = false)
  private ApplicationSettingGroup parent;

  /**
   * 应用
   */
  public final ApplicationSettingGroup getParent() {
    if (parent == null && !StringUtil.isNullOrEmpty(getParentId())) {
      parent = AppsContext.getInstance().getApplicationSettingGroupService().findOne(getParentId());
    }

    return parent;
  }

  private String parentId;

  /**
   */
  public final String getParentId() {
    return parentId;
  }

  public final void setParentId(String value) {
    parentId = value;
  }

  /**
   */
  public final String getParentName() {
    return getParent() == null ? getApplicationDisplayName() : getParent().getName();
  }

  /**
   */
  public final String getParentDisplayName() {
    return getParent() == null ? TEXT_EMPTY : getParent().getDisplayName();
  }

  private String code;

  /**
   */
  public final String getCode() {
    return code;
  }

  public final void setCode(String value) {
    code = value;
  }

  private String name;

  /**
   */
  public final String getName() {
    return name;
  }

  public final void setName(String value) {
    name = value;
  }

  private String displayName;

  /**
   */
  public final String getDisplayName() {
    if (StringUtil.isNullOrEmpty(displayName)) {
      displayName = getName();
    }

    return displayName;
  }

  public final void setDisplayName(String value) {
    displayName = value;
  }

  private int contentType;

  /**
   */
  public final int getContentType() {
    return contentType;
  }

  public final void setContentType(int value) {
    contentType = value;
  }

  private String orderId;

  /**
   */
  public final String getOrderId() {
    return orderId;
  }

  public final void setOrderId(String value) {
    orderId = value;
  }

  private int status;

  /**
   */
  public final int getStatus() {
    return status;
  }

  public final void setStatus(int value) {
    status = value;
  }

  private String remark;

  /**
   */
  public final String getRemark() {
    return remark;
  }

  public final void setRemark(String value) {
    remark = value;
  }

  private java.time.LocalDateTime modifiedDate = DateUtil.getDefaultLocalDateTime();

  /**
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  /**
   * 实体对象标识
   */
  @Override
  public String getEntityId() {
    return getId();
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
   */
  @Override
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 序列化对象
   *
   * @param displayComment 显示注释
   * @param displayFriendlyName 显示友好名称
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
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", getId()));
    if (displayComment) {
      outString.append("<!-- 所属应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", getApplicationId()));
    if (displayComment) {
      outString.append("<!-- 所属应用参数分组标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", getParentId()));
    if (displayComment) {
      outString.append("<!-- 编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", getCode()));
    if (displayComment) {
      outString.append("<!-- 名称 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<text><![CDATA[%1$s]]></text>", getName()));
    if (displayComment) {
      outString.append("<!-- 显示名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<displayName><![CDATA[%1$s]]></displayName>", getDisplayName()));
    if (displayComment) {
      outString.append("<!-- 内容类型 (整型) (int) -->");
    }
    outString.append(String.format("<contentType><![CDATA[%1$s]]></contentType>", getContentType()));
    if (displayComment) {
      outString.append("<!-- 排序编号 (字符串) (nvarchar(20)) -->");
    }
    outString.append(String.format("<orderId><![CDATA[%1$s]]></orderId>", getOrderId()));
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", getStatus()));
    if (displayComment) {
      outString.append("<!-- 备注信息 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<remark><![CDATA[%1$s]]></remark>", getRemark()));
    if (displayComment) {
      outString.append("<!-- 最后更新时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<updateDate><![CDATA[%1$s]]></updateDate>", getModifiedDate()));
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
    setId(element.selectSingleNode("id").getText());
    setApplicationId(element.selectSingleNode("applicationId").getText());
    setParentId(element.selectSingleNode("parentId").getText());
    setCode(element.selectSingleNode("code").getText());
    setName(element.selectSingleNode("text").getText());
    setDisplayName(element.selectSingleNode("displayName").getText());
    setContentType(Integer.parseInt(element.selectSingleNode("contentType").getText()));
    setOrderId(element.selectSingleNode("orderId").getText());
    setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    setRemark(element.selectSingleNode("remark").getText());
    setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("updateDate").getText()));
  }
}
