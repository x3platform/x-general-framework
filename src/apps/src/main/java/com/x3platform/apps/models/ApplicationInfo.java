package com.x3platform.apps.models;

import java.util.*;

import com.x3platform.EntityClass;
import com.x3platform.IAuthorizationObject;
import com.x3platform.apps.AppsContext;
import com.x3platform.cacheBuffer.ICacheable;
import com.x3platform.membership.*;

import com.x3platform.util.StringUtil;
import org.dom4j.Element;

/**
 * 应用
 */
public class ApplicationInfo extends EntityClass implements ICacheable {

  /**
   * 默认构造函数
   */
  public ApplicationInfo() {
  }

  private String id;

  /**
   * 标识
   */
  public final String getId() {
    return id;
  }

  public final void setId(String value) {
    id = value;
  }

  private IAccountInfo account;

  /**
   * 帐号
   */
  public final IAccountInfo getAccount() {
    if (account == null && !StringUtil.isNullOrEmpty(this.getAccountId())) {
      account = MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
    }

    return account;
  }

  private String accountId = "";

  /**
   * 帐号标识
   */
  public final String getAccountId() {
    return accountId;
  }

  public final void setAccountId(String value) {
    accountId = value;
  }

  /**
   * 账号姓名
   */
  public final String getAccountName() {
    return this.getAccount() == null ? "" : this.getAccount().getName();
  }

  private ApplicationInfo parent;

  /**
   * 应用
   */
  public final ApplicationInfo getParent() {
    if (parent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
       parent = AppsContext.getInstance().getApplicationService().findOne(this.getParentId());
    }

    return parent;
  }

  private String parentId = "";

  /**
   * 父级应用标识
   */
  public final String getParentId() {
    if (StringUtil.isNullOrEmpty(parentId)) {
      parentId = "00000000-0000-0000-0000-000000000001";
    }

    return parentId;
  }

  public final void setParentId(String value) {
    parentId = value;
  }

  /**
   */
  public final String getParentName() {
    return this.getParent() == null ? this.getApplicationDisplayName() : this.getParent().getApplicationName();
  }

  /**
   */
  public final String getParentDisplayName() {
    return this.getParent() == null ? "" : this.getParent().getApplicationDisplayName();
  }

  private String code = "";

  /**
   * 应用编码
   */
  public final String getCode() {
    return code;
  }

  public final void setCode(String value) {
    code = value;
  }

  private String applicationName = "";

  /**
   */
  public final String getApplicationName() {
    return applicationName;
  }

  public final void setApplicationName(String value) {
    applicationName = value;
  }

  private String applicationDisplayName = "";

  /**
   */
  public final String getApplicationDisplayName() {
    if (StringUtil.isNullOrEmpty(applicationDisplayName)) {
      this.applicationDisplayName = this.applicationName;
    }

    return applicationDisplayName;
  }

  public final void setApplicationDisplayName(String value) {
    applicationDisplayName = value;
  }

  private String applicationKey = "";

  /**
   * 应用许可号
   */
  public final String getApplicationKey() {
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

  public final void setApplicationKey(String value) {
    applicationKey = value;
  }

  private String mApplicationSecret = "";

  /**
   * 应用密钥
   */
  public final String getApplicationSecret() {
    return mApplicationSecret;
  }

  public final void setApplicationSecret(String value) {
    mApplicationSecret = value;
  }

  private String pinyin = "";

  /**
   * 拼音简写
   */
  public final String getPinYin() {
    return pinyin;
  }

  public final void setPinYin(String value) {
    pinyin = value;
  }

  private String description = "";

  /**
   */
  public final String getDescription() {
    return description;
  }

  public final void setDescription(String value) {
    description = value;
  }

  private int hasChildren = 0;

  /**
   * 是否有叶子节点
   */
  public final int getHasChildren() {
    return hasChildren;
  }

  public final void setHasChildren(int value) {
    hasChildren = value;
  }

  private String mAdministratorEmail = "";

  /**
   */
  public final String getAdministratorEmail() {
    return mAdministratorEmail;
  }

  public final void setAdministratorEmail(String value) {
    mAdministratorEmail = value;
  }

  private String iconPath = "";

  /**
   * 图标文件
   */
  public final String getIconPath() {
    return iconPath;
  }

  public final void setIconPath(String value) {
    iconPath = value;
  }

  private String bigIconPath = "";

  /**
   * 大图标文件
   */
  public final String getBigIconPath() {
    return bigIconPath;
  }

  public final void setBigIconPath(String value) {
    bigIconPath = value;
  }

  private String helpUrl = "";

  /**
   * 功能帮助文件
   */
  public final String getHelpUrl() {
    return helpUrl;
  }

  public final void setHelpUrl(String value) {
    helpUrl = value;
  }

  private String licenseStatus = "";

  /**
   * 授权状态
   */
  public final String getLicenseStatus() {
    return licenseStatus;
  }

  public final void setLicenseStatus(String value) {
    licenseStatus = value;
  }

  private int hidden;

  /**
   * 显示为菜单列表时是否隐藏。
   */
  public final int getHidden() {
    return hidden;
  }

  public final void setHidden(int value) {
    hidden = value;
  }

  private int locking;

  /**
   * 是否锁定 0:允许 1:锁定
   */
  public final int getLocking() {
    return this.locking;
  }

  public final void setLocking(int value) {
    this.locking = value;
  }

  private String orderId = "";

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

  private String remark = "";

  /**
   */
  public final String getRemark() {
    return remark;
  }

  public final void setRemark(String value) {
    remark = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.now();

  /**
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.now();

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 管理员信息
  // -------------------------------------------------------

  ///#region 属性:Administrators
  // private List<MembershipAuthorizationScopeObject> mAdministrators = null;

  /**
   */
  // public final List<MembershipAuthorizationScopeObject> getAdministrators() {
  //   if (mAdministrators == null) {
  //     mAdministrators = AppsContext.Instance.ApplicationService.GetAuthorizationScopeObjects(this.getId(), "应用_默认_管理员");
  //   }
  //
  //   return mAdministrators;
  // }

  private String mAdministratorScopeView = "";

  /**
   */
  public final String getAdministratorScopeView() {
    /*
    if (StringUtil.isNullOrEmpty(mAdministratorScopeView) && !this.getAdministrators().isEmpty()) {
      IAuthorizationObject authorizationObject = null;

      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getAdministrators()) {
        authorizationObject = authorizationScopeObject.GetAuthorizationObject();

        if (authorizationObject != null) {
          mAdministratorScopeView += authorizationObject.Name + ";";
        }
      }
    }
    */

    return mAdministratorScopeView;
  }
  ///#endregion

  ///#region 属性:AdministratorScopeText
  private String mAdministratorScopeText = "";

  /**
   */
  public final String getAdministratorScopeText() {
    /*
    if (StringUtil.isNullOrEmpty(mAdministratorScopeText) && !this.getAdministrators().isEmpty()) {
      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getAdministrators()) {
        mAdministratorScopeText += authorizationScopeObject.toString();
      }
    }
    */

    return mAdministratorScopeText;
  }
  ///#endregion

  // -------------------------------------------------------
  // 审查员信息
  // -------------------------------------------------------

  /**
  private List<MembershipAuthorizationScopeObject> reviewers = null;

  public final List<MembershipAuthorizationScopeObject> getReviewers() {
    if (reviewers == null) {
      reviewers = AppsContext.Instance.ApplicationService.GetAuthorizationScopeObjects(this.getId(), "应用_默认_审查员");
    }

    return reviewers;
  }

  private String reviewerScopeView = "";

  public final String getReviewerScopeView() {
    if (StringUtil.isNullOrEmpty(reviewerScopeView) && !this.getReviewers().isEmpty()) {
      IAuthorizationObject authorizationObject = null;

      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getReviewers()) {
        authorizationObject = authorizationScopeObject.GetAuthorizationObject();

        if (authorizationObject != null) {
          reviewerScopeView += authorizationObject.Name + ";";
        }
      }
    }

    return reviewerScopeView;
  }

  private String reviewerScopeText = "";

  public final String getReviewerScopeText() {
    if (StringUtil.isNullOrEmpty(reviewerScopeText) && !this.getReviewers().isEmpty()) {
      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getReviewers()) {
        reviewerScopeText += authorizationScopeObject.toString();
      }
    }

    return reviewerScopeText;
  }
   */

  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------

  /**
   private List<MembershipAuthorizationScopeObject> members = null;


  public final List<MembershipAuthorizationScopeObject> getMembers() {
    if (members == null) {
      members = AppsContext.Instance.ApplicationService.GetAuthorizationScopeObjects(this.getId(), "应用_默认_可访问成员");

      if (members.isEmpty()) {
        members.add(new MembershipAuthorizationScopeObject("Role", "00000000-0000-0000-0000-000000000000", "所有人"));
      }
    }

    return members;
  }
   */

  /**
   private String memberScopeView = "";

   public final String getMemberScopeView() {
    if (StringUtil.isNullOrEmpty(memberScopeView) && !this.getMembers().isEmpty()) {
      IAuthorizationObject authorizationObject = null;

      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getMembers()) {
        authorizationObject = authorizationScopeObject.GetAuthorizationObject();

        if (authorizationObject != null) {
          memberScopeView += authorizationObject.Name + ";";
        }
      }
    }

    return memberScopeView;
  }
   */

  /**
   private String memberScopeText = "";

  public final String getMemberScopeText() {
    if (StringUtil.isNullOrEmpty(memberScopeText) && !this.getMembers().isEmpty()) {
      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getMembers()) {
        memberScopeText += authorizationScopeObject.toString();
      }
    }

    return memberScopeText;
  }
   */

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  /**
   * 实体对象标识
   */
  // @Override
  public String getEntityId() {
    return this.getId();
  }
  ///#endregion

  // -------------------------------------------------------
  // 显式实现 ICacheable
  // -------------------------------------------------------

  private java.time.LocalDateTime expires = java.time.LocalDateTime.MAX;

  /**
   * 过期时间
   */
  public java.time.LocalDateTime getExpires() {
    return expires;
  }

  public void setExpires(java.time.LocalDateTime value) {
    expires = value;
  }

  // -------------------------------------------------------
  // 实现 EntityClass 序列化
  // -------------------------------------------------------

  /**
   * 序列化对象
   */
  // @Override
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
  // @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();
    if (displayComment) {
      outString.append("<!-- 应用功能对象 -->");
    }
    outString.append("<feature>");
    if (displayComment) {
      outString.append("<!-- 应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    if (displayComment) {
      outString.append("<!-- 父级应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", this.getParentId()));
    if (displayComment) {
      outString.append("<!-- 应用编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", this.getCode()));
    if (displayComment) {
      outString.append("<!-- 应用名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", this.getApplicationName()));
    if (displayComment) {
      outString.append("<!-- 应用显示名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<type><![CDATA[%1$s]]></type>", this.getApplicationDisplayName()));
    if (displayComment) {
      outString.append("<!-- 所属父级功能标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", this.getParentId()));
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", this.getStatus()));
    if (displayComment) {
      outString.append("<!-- 授权对象列表 -->");
    }
    outString.append("<authorizationObjects>");
    //if (this.mAuthorizationReadScopeObjects != null)
    //{
    //    foreach (MembershipAuthorizationScopeObject authorizationScopeObject in this.mAuthorizationReadScopeObjects)
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
    outString.append(String.format("<modifiedDate><![CDATA[%1$s]]></modifiedDate>", StringUtil.toDateFormat(this.getModifiedDate(), "yyyy-MM-dd HH:mm:ss")));
    outString.append("</feature>");

    return outString.toString();
  }

  /**
   * 反序列化对象
   *
   * @param element Xml元素
   */
  // @Override
  public void deserialize(Element element) {
    /*
    this.setId(element.GetElementsByTagName("id")[0].InnerText);
    this.setCode(element.GetElementsByTagName("code")[0].InnerText);
    // this.Name = element.GetElementsByTagName("name")[0].InnerText;
    this.setStatus(Integer.parseInt(element.GetElementsByTagName("status")[0].InnerText));
    this.setModifiedDate(java.time.LocalDateTime.parse(element.GetElementsByTagName("updateDate")[0].InnerText));
    */
  }
}
