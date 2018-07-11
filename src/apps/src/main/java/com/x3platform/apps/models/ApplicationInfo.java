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

  private String mId;

  /**
   * 标识
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }
  ///#endregion

  ///#region 属性:Account
  private IAccountInfo mAccount;

  /**
   * 帐号
   */
  public final IAccountInfo getAccount() {
    if (mAccount == null && !StringUtil.isNullOrEmpty(this.getAccountId())) {
      mAccount = MembershipManagement.getInstance().getAccountService().findOne(this.getAccountId());
    }

    return mAccount;
  }

  private String mAccountId = "";

  /**
   * 帐号标识
   */
  public final String getAccountId() {
    return mAccountId;
  }

  public final void setAccountId(String value) {
    mAccountId = value;
  }
  ///#endregion

  ///#region 属性:AccountName

  /**
   * 账号姓名
   */
  public final String getAccountName() {
    return this.getAccount() == null ? "" : this.getAccount().getName();
  }

  private ApplicationInfo mParent;

  /**
   * 应用
   */
  public final ApplicationInfo getParent() {
    if (mParent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      // TODO 待处理
      // mParent = AppsContext.Instance.ApplicationService.FindOne(this.getParentId());
    }

    return mParent;
  }

  private String mParentId = "";

  /**
   * 父级应用标识
   */
  public final String getParentId() {
    if (StringUtil.isNullOrEmpty(mParentId)) {
      mParentId = "00000000-0000-0000-0000-000000000001";
    }

    return mParentId;
  }

  public final void setParentId(String value) {
    mParentId = value;
  }
  ///#endregion

  ///#region 属性:ParentName

  /**
   */
  public final String getParentName() {
    return this.getParent() == null ? this.getApplicationDisplayName() : this.getParent().getApplicationName();
  }
  ///#endregion

  ///#region 属性:ParentDisplayName

  /**
   */
  public final String getParentDisplayName() {
    return this.getParent() == null ? "" : this.getParent().getApplicationDisplayName();
  }
  ///#endregion

  ///#region 属性:Code
  private String mCode = "";

  /**
   * 应用编码
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }
  ///#endregion

  ///#region 属性:ApplicationName
  private String mApplicationName = "";

  /**
   */
  public final String getApplicationName() {
    return mApplicationName;
  }

  public final void setApplicationName(String value) {
    mApplicationName = value;
  }
  ///#endregion

  ///#region 属性:ApplicationDisplayName
  private String mApplicationDisplayName = "";

  /**
   */
  public final String getApplicationDisplayName() {
    if (StringUtil.isNullOrEmpty(mApplicationDisplayName)) {
      this.mApplicationDisplayName = this.mApplicationName;
    }

    return mApplicationDisplayName;
  }

  public final void setApplicationDisplayName(String value) {
    mApplicationDisplayName = value;
  }
  ///#endregion

  ///#region 属性:ApplicationKey
  private String mApplicationKey = "";

  /**
   * 应用许可号
   */
  public final String getApplicationKey() {
    if (StringUtil.isNullOrEmpty(mApplicationKey)) {
      mApplicationKey = StringUtil.toUuid();
    }

    if (mApplicationKey.length() > 32) {
      mApplicationKey = mApplicationKey.substring(0, 32);
    }

    if (mApplicationKey.length() < 32) {
      int paddingTextCount = 32 - mApplicationKey.length();

      for (int i = 0; i < paddingTextCount; i++) {
        mApplicationKey += "0";
      }
    }

    return mApplicationKey;
  }

  public final void setApplicationKey(String value) {
    mApplicationKey = value;
  }
  ///#endregion

  ///#region 属性:ApplicationSecret
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
  ///#endregion

  ///#region 属性:PinYin
  private String mPinYin = "";

  /**
   * 拼音简写
   */
  public final String getPinYin() {
    return mPinYin;
  }

  public final void setPinYin(String value) {
    mPinYin = value;
  }

  private String mDescription = "";

  /**
   */
  public final String getDescription() {
    return mDescription;
  }

  public final void setDescription(String value) {
    mDescription = value;
  }

  private int mHasChildren = 0;

  /**
   * 是否有叶子节点
   */
  public final int getHasChildren() {
    return mHasChildren;
  }

  public final void setHasChildren(int value) {
    mHasChildren = value;
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

  private String mIconPath = "";

  /**
   * 图标文件
   */
  public final String getIconPath() {
    return mIconPath;
  }

  public final void setIconPath(String value) {
    mIconPath = value;
  }

  private String mBigIconPath = "";

  /**
   * 大图标文件
   */
  public final String getBigIconPath() {
    return mBigIconPath;
  }

  public final void setBigIconPath(String value) {
    mBigIconPath = value;
  }

  private String mHelpUrl = "";

  /**
   * 功能帮助文件
   */
  public final String getHelpUrl() {
    return mHelpUrl;
  }

  public final void setHelpUrl(String value) {
    mHelpUrl = value;
  }

  private String mLicenseStatus = "";

  /**
   * 授权状态
   */
  public final String getLicenseStatus() {
    return mLicenseStatus;
  }

  public final void setLicenseStatus(String value) {
    mLicenseStatus = value;
  }

  private int mHidden;

  /**
   * 显示为菜单列表时是否隐藏。
   */
  public final int getHidden() {
    return mHidden;
  }

  public final void setHidden(int value) {
    mHidden = value;
  }
  ///#endregion

  ///#region 属性:Locking
  private int mLocking;

  /**
   * 是否锁定 0:允许 1:锁定
   */
  public final int getLocking() {
    return this.mLocking;
  }

  public final void setLocking(int value) {
    this.mLocking = value;
  }
  ///#endregion

  ///#region 属性:OrderId
  private String mOrderId = "";

  /**
   */
  public final String getOrderId() {
    return mOrderId;
  }

  public final void setOrderId(String value) {
    mOrderId = value;
  }
  ///#endregion

  ///#region 属性:Status
  private int mStatus;

  /**
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }
  ///#endregion

  ///#region 属性:Remark
  private String mRemark = "";

  /**
   */
  public final String getRemark() {
    return mRemark;
  }

  public final void setRemark(String value) {
    mRemark = value;
  }
  ///#endregion

  ///#region 属性:ModifiedDate
  private java.time.LocalDateTime mModifiedDate = java.time.LocalDateTime.now();

  /**
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }
  ///#endregion

  ///#region 属性:CreatedDate
  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.now();

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
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
  private List<MembershipAuthorizationScopeObject> mReviewers = null;

  public final List<MembershipAuthorizationScopeObject> getReviewers() {
    if (mReviewers == null) {
      mReviewers = AppsContext.Instance.ApplicationService.GetAuthorizationScopeObjects(this.getId(), "应用_默认_审查员");
    }

    return mReviewers;
  }

  private String mReviewerScopeView = "";

  public final String getReviewerScopeView() {
    if (StringUtil.isNullOrEmpty(mReviewerScopeView) && !this.getReviewers().isEmpty()) {
      IAuthorizationObject authorizationObject = null;

      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getReviewers()) {
        authorizationObject = authorizationScopeObject.GetAuthorizationObject();

        if (authorizationObject != null) {
          mReviewerScopeView += authorizationObject.Name + ";";
        }
      }
    }

    return mReviewerScopeView;
  }

  private String mReviewerScopeText = "";

  public final String getReviewerScopeText() {
    if (StringUtil.isNullOrEmpty(mReviewerScopeText) && !this.getReviewers().isEmpty()) {
      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getReviewers()) {
        mReviewerScopeText += authorizationScopeObject.toString();
      }
    }

    return mReviewerScopeText;
  }
   */

  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------

  /**
   private List<MembershipAuthorizationScopeObject> mMembers = null;


  public final List<MembershipAuthorizationScopeObject> getMembers() {
    if (mMembers == null) {
      mMembers = AppsContext.Instance.ApplicationService.GetAuthorizationScopeObjects(this.getId(), "应用_默认_可访问成员");

      if (mMembers.isEmpty()) {
        mMembers.add(new MembershipAuthorizationScopeObject("Role", "00000000-0000-0000-0000-000000000000", "所有人"));
      }
    }

    return mMembers;
  }
   */

  /**
   private String mMemberScopeView = "";

   public final String getMemberScopeView() {
    if (StringUtil.isNullOrEmpty(mMemberScopeView) && !this.getMembers().isEmpty()) {
      IAuthorizationObject authorizationObject = null;

      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getMembers()) {
        authorizationObject = authorizationScopeObject.GetAuthorizationObject();

        if (authorizationObject != null) {
          mMemberScopeView += authorizationObject.Name + ";";
        }
      }
    }

    return mMemberScopeView;
  }
   */

  /**
   private String mMemberScopeText = "";

  public final String getMemberScopeText() {
    if (StringUtil.isNullOrEmpty(mMemberScopeText) && !this.getMembers().isEmpty()) {
      for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getMembers()) {
        mMemberScopeText += authorizationScopeObject.toString();
      }
    }

    return mMemberScopeText;
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

  private java.time.LocalDateTime mExpires = java.time.LocalDateTime.MAX;

  /**
   * 过期时间
   */
  public java.time.LocalDateTime getExpires() {
    return mExpires;
  }

  public void setExpires(java.time.LocalDateTime value) {
    mExpires = value;
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
