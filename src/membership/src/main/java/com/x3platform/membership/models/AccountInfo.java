package com.x3platform.membership.models;

import java.util.*;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.IAuthorizationObject;
import com.x3platform.IAuthorizationScope;
import com.x3platform.membership.*;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.dom4j.Element;
import org.dom4j.Node;

// import com.x3platform.CacheBuffer.*;
// import com.x3platform.Util.*;
// import com.x3platform.Membership.Configuration.*;

/**
 * 帐户信息
 */
public class AccountInfo implements IAccountInfo {

  /**
   */
  public AccountInfo() {
  }

  //
  // 属性, 可存放扩展属性, 临时属性
  //
  // 可添加的属性 例如: OriginalPassword, OriginalNickName
  //

  private HashMap<String, Object> mProperties = new HashMap<String, Object>();

  /**
   * 属性
   */
  public final HashMap<String, Object> getProperties() {
    return mProperties;
  }

  //
  // 具体属性
  //

  private String mId = "";

  /**
   * 用户标识
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  ///#region 属性:Code
  private String mCode = "";

  /**
   * 编码
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }
  ///#endregion

  ///#region 属性:Name
  private String mName = "";

  /**
   * 名称
   */
  @Override
  public final String getName() {
    return mName;
  }

  @Override
  public final void setName(String value) {
    mName = value;
  }
  ///#endregion

  ///#region 属性:GlobalName
  private String mGlobalName = "";

  /**
   * 全局名称
   */
  public final String getGlobalName() {
    return mGlobalName;
  }

  public final void setGlobalName(String value) {
    mGlobalName = value;
  }

  private String mDisplayName = "";

  /**
   * 显示名称
   */
  public final String getDisplayName() {
    if (StringUtil.isNullOrEmpty(mDisplayName)) {
      mDisplayName = this.getName();
    }

    return mDisplayName;
  }

  public final void setDisplayName(String value) {
    mDisplayName = value;
  }

  private String mPinYin = "";

  /**
   * 拼音
   */
  public final String getPinYin() {
    return mPinYin;
  }

  public final void setPinYin(String value) {
    mPinYin = value;
  }

  private String mLoginName = "";

  /**
   * 登录名
   */
  public final String getLoginName() {
    return mLoginName;
  }

  public final void setLoginName(String value) {
    mLoginName = value;
  }

  private String mPassword;

  /**
   * 密码
   */
  public final String getPassword() {
    return this.mPassword;
  }

  public final void setPassword(String value) {
    this.mPassword = value;
  }

  private java.time.LocalDateTime mPasswordChangedDate =  DateUtil.getDefaultTime();

  /**
   * 密码更新时间
   */
  public final java.time.LocalDateTime getPasswordChangedDate() {
    return this.mPasswordChangedDate;
  }

  public final void setPasswordChangedDate(java.time.LocalDateTime value) {
    this.mPasswordChangedDate = value;
  }

  private String mIdentityCard = "";

  /**
   * 身份证
   */
  public final String getIdentityCard() {
    return mIdentityCard;
  }

  public final void setIdentityCard(String value) {
    mIdentityCard = value;
  }
  ///#endregion

  ///#region 属性:Type
  private int mType = 0;

  /**
   * 帐号类型 0:普通帐号 1:邮箱帐号 2:Rtx帐号 3:CRM帐号 1000:供应商帐号 2000:客户帐号
   */
  public final int getType() {
    return mType;
  }

  public final void setType(int value) {
    mType = value;
  }

  private String mTypeView;

  /**
   * 帐号类别视图 0:普通帐号 1:邮箱帐号 2:CRM帐号 3:RTX帐号 1000:供应商帐号 2000:客户帐号
   */
  @JSONField(serialize=false)
  public final String getTypeView() {
    if (StringUtil.isNullOrEmpty(mTypeView)) {
      mTypeView = MembershipManagement.getInstance().getSettingService().getText("应用管理_协同平台_人员及权限管理_帐号管理_帐号类别", String.valueOf(this.getType()));
    }

    return mTypeView;
  }

  private String mCertifiedMobile;

  /**
   * 已验证的电话
   */
  public final String getCertifiedMobile() {
    return mCertifiedMobile;
  }

  public final void setCertifiedMobile(String value) {
    mCertifiedMobile = value;
  }
  ///#endregion

  ///#region 属性:CertifiedEmail
  private String mCertifiedEmail;

  /**
   * 已验证的邮箱
   */
  public final String getCertifiedEmail() {
    return mCertifiedEmail;
  }

  public final void setCertifiedEmail(String value) {
    mCertifiedEmail = value;
  }
  ///#endregion

  ///#region 属性:CertifiedAvatar
  private String mCertifiedAvatar = "";

  /**
   * 已验证的头像
   */
  public final String getCertifiedAvatar() {
    return mCertifiedAvatar;
  }

  public final void setCertifiedAvatar(String value) {
    mCertifiedAvatar = value;
  }

  /**
   * 已验证的头像虚拟路径
   */
  public final String getCertifiedAvatarView() {
    return this.getCertifiedAvatar().replace("{avatar}", MembershipConfigurationView.getInstance().getAvatarVirtualFolder());
  }

  private int mEnableEmail;

  /**
   * 启用企业邮箱
   */
  public final int getEnableEmail() {
    return mEnableEmail;
  }

  public final void setEnableEmail(int value) {
    mEnableEmail = value;
  }

  private IAuthorizationObject mParent;

  /**
   * 父级权限对象接口集合
   */
  public final IAuthorizationObject getParent() {
    return mParent;
  }

  public final void setParent(IAuthorizationObject value) {
    mParent = value;
  }

  private List<IAccountOrganizationUnitRelationInfo> mOrganizationUnitRelations = null;

  /**
   * 帐号和角色接口集合
   */
  @JSONField(serialize=false)
  public final List<IAccountOrganizationUnitRelationInfo> getOrganizationUnitRelations() {
    if (mOrganizationUnitRelations == null && !StringUtil.isNullOrEmpty(this.getId())) {
      // TODO 待处理
      mOrganizationUnitRelations = MembershipManagement.getInstance().getOrganizationUnitService().findAllRelationByAccountId(this.getId());
    }

    return mOrganizationUnitRelations;
  }

  private String mOrganizationUnitText = "";

  /**
   * 所属组织视图
   */
  @JSONField(serialize=false)
  public final String getOrganizationUnitText() {
    if (StringUtil.isNullOrEmpty(mOrganizationUnitText) && !this.getOrganizationUnitRelations().isEmpty()) {
      for (IAccountOrganizationUnitRelationInfo relation : this.getOrganizationUnitRelations()) {
        mOrganizationUnitText += String.format("organization#%1$s#%2$s#%3$s#%4$s,", relation.getOrganizationUnitId(), relation.getOrganizationUnitGlobalName(), StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss.fff"), relation.getIsDefault());
      }
    }

    return mOrganizationUnitText;
  }

  private String mOrganizationUnitView = "";

  /**
   * 所属组织视图
   */
  @JSONField(serialize=false)
  public final String getOrganizationUnitView() {
    if (StringUtil.isNullOrEmpty(mOrganizationUnitView) && !this.getOrganizationUnitRelations().isEmpty()) {
      for (IAccountOrganizationUnitRelationInfo relation : this.getOrganizationUnitRelations()) {
        mOrganizationUnitView += relation.getOrganizationUnitGlobalName() + ((StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss").equals(StringUtil.toDateFormat(java.time.LocalDateTime.MAX, "yyyy-MM-dd HH:mm:ss"))) ? "" : ("(" + StringUtil.toDate(relation.getEndDate()) + ")")) + ";";
      }
    }

    return mOrganizationUnitView;
  }

  private List<IAccountRoleRelationInfo> mRoleRelations = null;

  /**
   * 帐号和角色接口集合
   */
  @JSONField(serialize=false)
  public final List<IAccountRoleRelationInfo> getRoleRelations() {
    if (mRoleRelations == null && !StringUtil.isNullOrEmpty(this.getId())) {
      mRoleRelations = MembershipManagement.getInstance().getRoleService().findAllRelationByAccountId(this.getId());
    }

    return mRoleRelations;
  }

  private String mRoleText = "";

  /**
   * 所属角色视图
   */
  @JSONField(serialize=false)
  public final String getRoleText() {
    if (StringUtil.isNullOrEmpty(mRoleText) && !this.getRoleRelations().isEmpty()) {
      for (IAccountRoleRelationInfo relation : this.getRoleRelations()) {
        mRoleText += String.format("role#%1$s#%2$s#%3$s#%4$s,", relation.getRoleId(), relation.getRoleGlobalName(), StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss.fff"), relation.getIsDefault());
      }
    }

    return mRoleText;
  }
  ///#endregion

  ///#region 属性:RoleView
  private String mRoleView = "";

  /**
   * 所属角色视图
   */
  @JSONField(serialize=false)
  public final String getRoleView() {
    if (StringUtil.isNullOrEmpty(mRoleView) && !this.getRoleRelations().isEmpty()) {
      for (IAccountRoleRelationInfo relation : this.getRoleRelations()) {
        mRoleView += relation.getRoleGlobalName() + ((StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss").equals(StringUtil.toDateFormat(java.time.LocalDateTime.MAX, "yyyy-MM-dd HH:mm:ss"))) ? "" : ("(" + StringUtil.toDate(relation.getEndDate()) + ")")) + ";";
      }
    }

    return mRoleView;
  }
  ///#endregion

  ///#region 属性:GroupRelations
  private List<IAccountGroupRelationInfo> mGroupRelations = null;

  /**
   * 帐号和角色接口集合
   */
  @JSONField(serialize=false)
  public final List<IAccountGroupRelationInfo> getGroupRelations() {
    if (mGroupRelations == null && !StringUtil.isNullOrEmpty(this.getId())) {
      // TODO 待处理
      // mGroupRelations = MembershipManagement.getInstance().getGroupService().FindAllRelationByAccountId(this.getId());
      mGroupRelations = new ArrayList<IAccountGroupRelationInfo>();
    }

    return mGroupRelations;
  }
  ///#endregion

  ///#region 属性:GroupText
  private String mGroupText;

  /**
   * 所属群组文本信息
   */
  @JSONField(serialize=false)
  public final String getGroupText() {
    if (StringUtil.isNullOrEmpty(mGroupText) && !this.getGroupRelations().isEmpty()) {
      for (IAccountGroupRelationInfo relation : this.getGroupRelations()) {
        mGroupText += String.format("group#%1$s#%2$s#%3$s,", relation.getGroupId(), relation.getGroupGlobalName(), StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss.fff"));
      }
    }

    return mGroupText;
  }
  ///#endregion

  ///#region 属性:GroupView
  private String mGroupView;

  /**
   * 所属群组视图
   */
  @JSONField(serialize=false)
  public final String getGroupView() {
    if (StringUtil.isNullOrEmpty(mGroupView) && !this.getGroupRelations().isEmpty()) {
      for (IAccountGroupRelationInfo relation : this.getGroupRelations()) {
        // TODO 待处理
        // mGroupView += relation.getGroupGlobalName() + ((relation.EndDate.toString("yyyy-MM-dd HH:mm:ss").equals(java.time.LocalDateTime.MAX.toString("yyyy-MM-dd HH:mm:ss"))) ? "" : ("(" + StringUtil.ToDate(relation.getEndDate()) + ")")) + ";";
      }
    }

    return mGroupView;
  }
  ///#endregion

  ///#region 属性:Scopes
  private List<IAuthorizationScope> mScopes = new ArrayList<IAuthorizationScope>();

  /**
   * 范围接口集合
   */
  public final List<IAuthorizationScope> getScopes() {
    return mScopes;
  }
  ///#endregion

  ///#region 属性:IsDraft
  private boolean mIsDraft;

  /**
   * 是否是草稿
   */
  public final boolean getIsDraft() {
    return mIsDraft;
  }

  public final void setIsDraft(boolean value) {
    mIsDraft = value;
  }
  ///#endregion

  ///#region 属性:Locking
  private int mLocking = 1;

  /**
   * 防止意外删除 0 不锁定 | 1 锁定(默认)
   */
  public final int getLocking() {
    return mLocking;
  }

  public final void setLocking(int value) {
    mLocking = value;
  }
  ///#endregion

  ///#region 属性:OrderId
  private String mOrderId;

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
   * 状态
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }
  ///#endregion

  ///#region 属性:Remark
  private String mRemark;

  /**
   * 备注
   */
  public final String getRemark() {
    return mRemark;
  }

  public final void setRemark(String value) {
    mRemark = value;
  }
  ///#endregion

  ///#region 属性:IP
  private String mIP;

  /**
   * IP
   */
  public final String getIP() {
    return mIP;
  }

  public final void setIP(String value) {
    mIP = value;
  }

  private java.time.LocalDateTime mLoginDate = DateUtil.getDefaultTime();

  /**
   * 最近一次的登录时间
   */
  public final java.time.LocalDateTime getLoginDate() {
    return mLoginDate;
  }

  public final void setLoginDate(java.time.LocalDateTime value) {
    mLoginDate = value;
  }
  ///#endregion

  ///#region 属性:DistinguishedName
  private String mDistinguishedName = null;

  /**
   * 唯一名称
   */
  public final String getDistinguishedName() {
    return mDistinguishedName;
  }

  public final void setDistinguishedName(String value) {
    mDistinguishedName = value;
  }
  ///#endregion

  ///#region 属性:ModifiedDate
  private java.time.LocalDateTime mModifiedDate = DateUtil.getDefaultTime();

  /**
   * 修改时间
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }

  private java.time.LocalDateTime mCreatedDate = DateUtil.getDefaultTime();

  /**
   * 创建时间
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
  }

  //
  // 重置关系
  //

  /**
   * @param relationType
   * @param relationText
   */
  public final void ResetRelations(String relationType, String relationText) {
    String[] list = relationText.split(",|;");

    if (relationType.equals("organization")) {
      this.getOrganizationUnitRelations().clear();
    }

    if (relationType.equals("role")) {
      this.getRoleRelations().clear();
    }

    if (relationType.equals("group")) {
      this.getGroupRelations().clear();
    }

    // 设置组织关系

    for (String item : list) {
      String[] keys = item.split("#");

      if (keys.length > 2) {
        switch (keys[0]) {
          case "organization":
            if (relationType.equals("organization")) {
              this.getOrganizationUnitRelations().add(new AccountOrganizationUnitRelationInfo(this.getId(), keys[1]));
            }
            break;
          case "role":
            if (relationType.equals("role")) {
              this.getRoleRelations().add(new AccountRoleRelationInfo(this.getId(), keys[1]));
            }
            break;
          case "group":
            if (relationType.equals("group")) {
              this.getGroupRelations().add(new AccountGroupRelationInfo(this.getId(), keys[1]));
            }
            break;
        }
      }
    }
  }

  // -------------------------------------------------------
  // 实现 IIdentity 接口
  // -------------------------------------------------------

  ///#region 属性:AuthenticationType

  /**
   */
  public final String getAuthenticationType() {
    return "MembershipAuthentication";
  }
  ///#endregion

  ///#region 属性:IsAuthenticated

  /**
   */
  public final boolean getIsAuthenticated() {
    return this.getId().equals("00000000-0000-0000-0000-000000000000") ? false : true;
  }
  ///#endregion

  // -------------------------------------------------------
  // 显式实现 ICacheable 接口
  // -------------------------------------------------------

  ///#region 属性:Expires
  private java.time.LocalDateTime mExpires = java.time.LocalDateTime.now().plusHours(6);

  private java.time.LocalDateTime getExpires() {
    return mExpires;
  }

  private void setExpires(java.time.LocalDateTime value) {
    mExpires = value;
  }
  ///#endregion

  // -------------------------------------------------------
  // 实现 IAuthorizationObject 接口
  // -------------------------------------------------------

  /**
   * 类型
   */
  public String getAuthorizationObjectType() {
    return "Account";
  }

  /**
   * 名称
   */
  public String getAuthorizationObjectId() {
    return this.getId();
  }

  public void setAuthorizationObjectId(String value) {
    this.setAuthorizationObjectId(value);
  }

  /**
   * 名称
   */
  public String getAuthorizationObjectName() {
    return String.format("%1$s", this.getName(), this.getLoginName());
  }

  public void setAuthorizationObjectName(String value) {
    int index = value.indexOf("(");
    if (index > -1) {
      this.setName(value.substring(0, index));
      this.setLoginName(value.substring((index + 1), (index + 1) + (value.length() - index - 2)));
    }
  }

  // -------------------------------------------------------
  // 实现 ISerializedObject 序列化
  // -------------------------------------------------------

  /**
   * 根据对象导出Xml元素
   *
   * @return
   */
  public final String serializable() {
    return serializable(false, false);
  }

  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   * @return
   */
  public final String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    outString.append("<account>");
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", this.getCode()));
    outString.append(String.format("<loginName><![CDATA[%1$s]]></loginName>", this.getLoginName()));
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", this.getName()));
    outString.append(String.format("<globalName><![CDATA[%1$s]]></globalName>", this.getGlobalName()));
    outString.append(String.format("<alias><![CDATA[%1$s]]></alias>", this.getDisplayName()));
    outString.append(String.format("<pinyin><![CDATA[%1$s]]></pinyin>", this.getPinYin()));
    outString.append(String.format("<orderId><![CDATA[%1$s]]></orderId>", this.getOrderId()));
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", this.getStatus()));
    outString.append(String.format("<remark><![CDATA[%1$s]]></remark>", this.getRemark()));
    outString.append(String.format("<updateDate><![CDATA[%1$s]]></updateDate>", this.getModifiedDate()));
    outString.append("</account>");

    return outString.toString();
  }

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  public final void deserialize(Element element) {
    this.setId(element.selectSingleNode("id").getText());

    this.setCode(element.selectSingleNode("code").getText());

    if (element.selectSingleNode("loginName") != null) {
      this.setLoginName(element.selectSingleNode("loginName").getText());
    }

    this.setName(element.selectSingleNode("name").getText());

    if (element.selectSingleNode("globalName") != null) {
      this.setGlobalName(element.selectSingleNode("globalName").getText());
    }

    if (element.selectSingleNode("alias") != null) {
      this.setDisplayName(element.selectSingleNode("alias").getText());
    }

    if (element.selectSingleNode("pinyin") != null) {
      this.setPinYin(element.selectSingleNode("pinyin").getText());
    }

    // 关系列表
    List list = element.selectNodes("relationObjects/relationObject");

    // 由于设置了Id
    this.mOrganizationUnitRelations = new ArrayList<IAccountOrganizationUnitRelationInfo>();
    this.mRoleRelations = new ArrayList<IAccountRoleRelationInfo>();
    this.mGroupRelations = new ArrayList<IAccountGroupRelationInfo>();

    for (Object item : list) {
      Node node = (Node) item;
      /*
      switch (node.Attributes["type"].Value) {
        case "OrganizationUnit":
          this.getOrganizationUnitRelations().add(new AccountOrganizationUnitRelationInfo(this.getId(), node.Attributes["id"].Value));
          break;

        case "Role":
          this.getRoleRelations().add(new AccountRoleRelationInfo(this.getId(), node.Attributes["id"].Value));
          break;

        case "Group":
          this.getGroupRelations().add(new AccountGroupRelationInfo(this.getId(), node.Attributes["id"].Value));
          break;
      }*/
    }

    this.setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
  }
}
