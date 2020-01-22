package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.AuthorizationScope;
import com.x3platform.AuthorizationScopeManagement;
import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.cachebuffer.Cacheable;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Element;

/**
 * 应用信息
 *
 * @author ruanyu
 */
public class Application extends EntityClass implements Cacheable {

  /**
   * 默认构造函数
   */
  public Application() {

  }

  private String id;

  /**
   * 标识
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  @JSONField(serialize = false)
  private Account account;

  /**
   * 帐号
   */
  public Account getAccount() {
    if (account == null && !StringUtil.isNullOrEmpty(getAccountId())) {
      account = MembershipManagement.getInstance().getAccountService().findOne(getAccountId());
    }
    return account;
  }

  private String accountId = "";

  /**
   * 帐号标识
   */
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String value) {
    accountId = value;
  }

  /**
   * 账号姓名
   */
  public String getAccountName() {
    return getAccount() == null ? "" : getAccount().getName();
  }

  @JSONField(serialize = false)
  private Application parent;

  /**
   * 应用
   */
  public Application getParent() {
    if (parent == null && !StringUtil.isNullOrEmpty(getParentId())) {
      parent = AppsContext.getInstance().getApplicationService().findOne(getParentId());
    }
    return parent;
  }

  /**
   */
  private String parentDisplayName;

  private String parentId = "";

  public void setParentId(String value) {
    parentId = value;
  }

  /**
   * 父级应用标识
   */
  public String getParentId() {
    if (StringUtil.isNullOrEmpty(parentId)) {
      parentId = "0";
    }
    return parentId;
  }

  /**
   *
   */
  public String getParentName() {
    return getParent() == null ? getApplicationDisplayName() : getParent().getApplicationName();
  }

  public String getParentDisplayName() {
    return getParent() == null ? "" : getParent().getApplicationDisplayName();
  }

  private String code = "";

  /**
   * 应用编码
   */
  public String getCode() {
    return code;
  }

  public void setCode(String value) {
    code = value;
  }

  private String applicationName = "";

  /**
   */
  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String value) {
    applicationName = value;
  }

  private String applicationDisplayName = "";

  /**
   */
  public String getApplicationDisplayName() {
    if (StringUtil.isNullOrEmpty(applicationDisplayName)) {
      applicationDisplayName = applicationName;
    }
    return applicationDisplayName;
  }

  public void setApplicationDisplayName(String value) {
    applicationDisplayName = value;
  }

  private String applicationKey = "";

  /**
   * 应用许可号
   */
  public String getApplicationKey() {
    if (StringUtil.isNullOrEmpty(applicationKey)) {
      applicationKey = StringUtil.toUuid();
    }

    if (applicationKey.length() > 32) {
      applicationKey = applicationKey.substring(0, 32);
    }

    if (applicationKey.length() < 32) {
      int paddingTextCount = 32 - applicationKey.length();

      for (int i = 0; i < paddingTextCount; i++) {
        applicationKey += "0";
      }
    }

    return applicationKey;
  }

  public void setApplicationKey(String value) {
    applicationKey = value;
  }

  private String applicationSecret = "";

  /**
   * 应用密钥
   */
  public String getApplicationSecret() {
    return applicationSecret;
  }

  public void setApplicationSecret(String value) {
    applicationSecret = value;
  }

  private String pinyin = "";

  /**
   * 拼音简写
   */
  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String value) {
    pinyin = value;
  }

  private String description = "";

  /**
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    description = value;
  }

  private int hasChildren = 0;

  /**
   * 是否有叶子节点
   */
  public int getHasChildren() {
    return hasChildren;
  }

  public void setHasChildren(int value) {
    hasChildren = value;
  }

  private String administratorEmail = "";

  /**
   */
  public String getAdministratorEmail() {
    return administratorEmail;
  }

  public void setAdministratorEmail(String value) {
    administratorEmail = value;
  }

  private String iconPath = "";

  /**
   * 图标文件
   */
  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String value) {
    iconPath = value;
  }

  private String bigIconPath = "";

  /**
   * 大图标文件
   */
  public String getBigIconPath() {
    return bigIconPath;
  }

  public void setBigIconPath(String value) {
    bigIconPath = value;
  }

  private String helpUrl = "";

  /**
   * 功能帮助文件
   */
  public String getHelpUrl() {
    return helpUrl;
  }

  public void setHelpUrl(String value) {
    helpUrl = value;
  }

  private String licenseStatus = "";

  /**
   * 授权状态
   */
  public String getLicenseStatus() {
    return licenseStatus;
  }

  public void setLicenseStatus(String value) {
    licenseStatus = value;
  }

  private int hidden;

  /**
   * 显示为菜单列表时是否隐藏。
   */
  public int getHidden() {
    return hidden;
  }

  public void setHidden(int value) {
    hidden = value;
  }

  private int locking;

  /**
   * 是否锁定 0:允许 1:锁定
   */
  public int getLocking() {
    return locking;
  }

  public void setLocking(int value) {
    locking = value;
  }

  private String orderId = "";

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

  private String remark = "";

  /**
   */
  public String getRemark() {
    return remark;
  }

  public void setRemark(String value) {
    remark = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.now();

  /**
   */
  public java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.now();

  /**
   * 创建时间
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 管理员信息
  // -------------------------------------------------------

  private List<AuthorizationScope> administrators = null;

  /**
   * 此方法适用缓存反序列化, 正式赋值建议使用相关服务的 bindAuthorizationScopeByEntityId 方法
   */
  public void setAdministrators(List<AuthorizationScope> value) {
    administrators = value;
  }

  /**
   */
  public List<AuthorizationScope> getAdministrators() {
    if (administrators == null) {
      administrators = AppsContext.getInstance().getApplicationService()
        .getAuthorizationScopes(getId(), "应用_默认_管理员");
    }
    return administrators;
  }

  private String administratorScopeView = null;

  /**
   */
  public String getAdministratorScopeView() {
    if (administratorScopeView == null) {
      administratorScopeView = AuthorizationScopeManagement.getAuthorizationScopeView(getAdministrators());
    }
    return administratorScopeView;
  }

  private String administratorScopeText = null;

  /**
   */
  public String getAdministratorScopeText() {
    if (administratorScopeText == null) {
      administratorScopeText = AuthorizationScopeManagement.getAuthorizationScopeText(getAdministrators());
    }
    return administratorScopeText;
  }

  // -------------------------------------------------------
  // 审查员信息
  // -------------------------------------------------------

  private List<AuthorizationScope> reviewers = null;

  /**
   * 此方法适用缓存反序列化, 正式赋值建议使用相关服务的 bindAuthorizationScopeByEntityId 方法
   */
  public void setReviewers(List<AuthorizationScope> value) {
    reviewers = value;
  }

  public List<AuthorizationScope> getReviewers() {
    if (reviewers == null) {
      reviewers = AppsContext.getInstance().getApplicationService().getAuthorizationScopes(getId(), "应用_默认_审查员");
    }
    return reviewers;
  }

  private String reviewerScopeView = null;

  public String getReviewerScopeView() {
    if (reviewerScopeView == null) {
      reviewerScopeView = AuthorizationScopeManagement.getAuthorizationScopeView(getReviewers());
    }
    return reviewerScopeView;
  }

  private String reviewerScopeText = null;

  public String getReviewerScopeText() {
    if (reviewerScopeText == null) {
      reviewerScopeText = AuthorizationScopeManagement.getAuthorizationScopeText(getReviewers());
    }
    return reviewerScopeText;
  }

  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------

  private List<AuthorizationScope> members = null;

  /**
   * 此方法适用缓存反序列化, 正式赋值建议使用相关服务的 bindAuthorizationScopeByEntityId 方法
   */
  public void setMembers(List<AuthorizationScope> value) {
    members = value;
  }

  public List<AuthorizationScope> getMembers() {
    if (members == null) {
      members = AppsContext.getInstance().getApplicationService().getAuthorizationScopes(getId(), "应用_默认_可访问成员");

      if (members.isEmpty()) {
        members.add(new AuthorizationScope("Role", "00000000-0000-0000-0000-000000000000", "所有人"));
      }
    }

    return members;
  }

  private String memberScopeView = null;

  public String getMemberScopeView() {
    if (memberScopeView == null) {
      memberScopeView = AuthorizationScopeManagement.getAuthorizationScopeView(getMembers());
    }
    return memberScopeView;
  }

  private String memberScopeText = null;

  public String getMemberScopeText() {
    if (memberScopeText == null) {
      memberScopeText = AuthorizationScopeManagement.getAuthorizationScopeText(getMembers());
    }
    return memberScopeText;
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

  private Date expires = DateUtil.getDefaultDate();

  /**
   * 过期时间
   */

  @Override
  public Date getExpires() {
    return expires;
  }

  @Override
  public void setExpires(Date value) {
    expires = value;
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
      outString.append("<!-- 应用功能对象 -->");
    }
    outString.append("<feature>");
    if (displayComment) {
      outString.append("<!-- 应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", getId()));
    if (displayComment) {
      outString.append("<!-- 父级应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", getParentId()));
    if (displayComment) {
      outString.append("<!-- 应用编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", getCode()));
    if (displayComment) {
      outString.append("<!-- 应用名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", getApplicationName()));
    if (displayComment) {
      outString.append("<!-- 应用显示名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<type><![CDATA[%1$s]]></type>", getApplicationDisplayName()));
    if (displayComment) {
      outString.append("<!-- 所属父级功能标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", getParentId()));
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", getStatus()));
    if (displayComment) {
      outString.append("<!-- 授权对象列表 -->");
    }
    outString.append("<authorizationObjects>");
    //if (this.mAuthorizationReadScopeObjects != null)
    //{
    //    foreach (AuthorizationScope authorizationScopeObject in this.mAuthorizationReadScopeObjects)
    //    {
    //        outString.AppendFormat("<authorizationObject id=\"{0}\" type=\"{1}\" />",
    //            authorizationScopeObject.AuthorizationObjectId,
    //            authorizationScopeObject.AuthorizationObjectType);
    //    }
    //}
    outString.append("</authorizationObjects>");
    if (displayComment) {
      outString.append("<!-- 最后更新时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<modifiedDate><![CDATA[%1$s]]></modifiedDate>",
      StringUtil.toDateFormat(getModifiedDate(), "yyyy-MM-dd HH:mm:ss")));
    outString.append("</feature>");

    return outString.toString();
  }

  /**
   * 反序列化对象
   *
   * @param element Xml元素
   */
  // @Override
  @Override
  public void deserialize(Element element) {
    /*
    this.setId(element.GetElementsByTagName("id")[0].InnerText);
    this.setCode(element.GetElementsByTagName("code")[0].InnerText);
    // this.Name = element.GetElementsByTagName("name")[0].InnerText;
    this.setStatus(Integer.parseInt(element.GetElementsByTagName("status")[0].InnerText));
    this.setModifiedDate(java.time.LocalDateTime.parse(element.GetElementsByTagName("updateDate")[0].InnerText));
    */
  }

  public List<Object> childNodes = new ArrayList<>();

  public List<Object> getChildNodes() {
    return childNodes;
  }

  public void setChildNodes(List<Object> childNodes) {
    this.childNodes = childNodes;
  }
//
//  public void setAccount(Account account) {
//    this.account = account;
//  }
//
//  public void setParentName(String parentName) {
//    this.parentName = parentName;
//  }
//
//  public void setParent(Application parent) {
//    this.parent = parent;
//  }
//
//  public void setParentDisplayName(String parentDisplayName) {
//    this.parentDisplayName = parentDisplayName;
//  }

  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
