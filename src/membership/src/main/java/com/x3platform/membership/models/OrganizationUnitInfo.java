package com.x3platform.membership.models;

import java.util.*;

import com.x3platform.IAuthorizationObject;
import com.x3platform.membership.*;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.dom4j.Element;

/**
 * 组织单位信息
 */
public class OrganizationUnitInfo implements IOrganizationUnitInfo {
  /**
   */
  public OrganizationUnitInfo() {
  }

  /**
   */
  public OrganizationUnitInfo(String id) {
    this.setId(id);
  }

  //
  // 属性, 可存放扩展属性, 临时属性
  //
  // 可添加的属性 例如: ParentId , ParentName
  //

  ///#region 属性:ExtensionInformation
  private IExtensionInformation mExtensionInformation = null;

  /**
   * 属性
   */
  public final IExtensionInformation getExtensionInformation() {
    if (mExtensionInformation == null) {
      // String springObjectFile = MembershipConfigurationView.Instance.Configuration.keySet()["SpringObjectFile"].Value;

      // SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(MembershipConfiguration.ApplicationName, springObjectFile);

      // String extensionInformationName = "X3Platform.Membership.Model.OrganizationUnitInfo.ExtensionInformation";

      // TODO 待处理
      // mExtensionInformation = objectBuilder.<IExtensionInformation>GetObject(extensionInformationName);
    }

    return mExtensionInformation;
  }
  ///#endregion

  //
  // 具体属性
  //

  ///#region 属性:Id
  private String mId;

  /**
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private String mStandardOrganizationUnitId;

  /**
   * 标准组织标识
   */
  public final String getStandardOrganizationUnitId() {
    return mStandardOrganizationUnitId;
  }

  public final void setStandardOrganizationUnitId(String value) {
    mStandardOrganizationUnitId = value;
  }
  ///#endregion

  ///#region 属性:StandardOrganizationUnitName

  /**
   * 父节点名称
   */
  // public final String getStandardOrganizationUnitName() {
  //  return this.getStandardOrganizationUnit() == null ? "" : this.getStandardOrganizationUnit().Name;
  // }
  ///#endregion

  ///#region 属性:StandardOrganizationUnit
  // private IStandardOrganizationUnitInfo mStandardOrganizationUnit = null;

  /**
   * 所属标准组织
   */
  // public final IStandardOrganizationUnitInfo getStandardOrganizationUnit() {
  //
  // StandardOrganizationUnitId = "00000000-0000-0000-0000-000000000000" 表示所属标准组织为空
  //

  // if (UUID.Empty.toString().equals(this.getStandardOrganizationUnitId())) {
  //   return null;
  // }

  // if (mStandardOrganizationUnit == null && !StringUtil.isNullOrEmpty(this.getStandardOrganizationUnitId())) {
  //    mStandardOrganizationUnit = MembershipManagement.Instance.StandardOrganizationUnitService[this.getStandardOrganizationUnitId()];
  // }

  // return mStandardOrganizationUnit;
  // }
  ///#endregion

  ///#region 属性:Code
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
  ///#endregion

  ///#region 属性:Name
  private String mName;

  /**
   * 名称
   */
  public final String getName() {
    return mName;
  }

  public final void setName(String value) {
    mName = value;
  }
  ///#endregion

  ///#region 属性:FullName
  private String mFullName = "";

  /**
   * 全称
   */
  public final String getFullName() {
    if (StringUtil.isNullOrEmpty(mFullName)) {
      mFullName = this.getName();
    }

    return mFullName;
  }

  public final void setFullName(String value) {
    mFullName = value;
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
  ///#endregion

  ///#region 属性:PinYin
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
  ///#endregion

  ///#region 属性:Type
  private int mType = 0;

  /**
   * 类型 -1:区域 0:公司 1:部门 2:项目团队 3:项目 4:项目分期
   */
  public final int getType() {
    return mType;
  }

  public final void setType(int value) {
    mType = value;
  }
  ///#endregion

  ///#region 属性:TypeView
  private String mTypeView;

  /**
   * 类型视图
   */
  public final String getTypeView() {
    if (StringUtil.isNullOrEmpty(mTypeView) && this.getType() != -1) {
      mTypeView = MembershipManagement.getInstance().getSettingService().getText("应用管理_协同平台_人员及权限管理_组织管理_组织类别", String.valueOf(this.getType()));
    }

    return mTypeView;
  }
  ///#endregion

  ///#region 属性:Level
  private int mLevel = 0;

  /**
   * 层级
   */
  public final int getLevel() {
    return mLevel;
  }

  public final void setLevel(int value) {
    mLevel = value;
  }

  private String mParentId;

  /**
   * 父节点标识
   */
  public final String getParentId() {
    if (StringUtil.isNullOrEmpty(this.mParentId)) {
      this.mParentId = UUIDUtil.emptyString();
    }

    return mParentId;
  }

  public final void setParentId(String value) {
    mParentId = value;
  }
  ///#endregion

  ///#region 属性:ParentGlobalName

  /**
   * 父节点全局名称
   */
  public final String getParentGlobalName() {
    return this.getParent() == null ? "" : this.getParent().getGlobalName();
  }
  ///#endregion

  ///#region 属性:Parent
  private IOrganizationUnitInfo mParent = null;

  /**
   * 父级对象
   */
  public final IOrganizationUnitInfo getParent() {
    //
    // ParentId = "00000000-0000-0000-0000-000000000000" 表示父节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当ParentId = "00000000-0000-0000-0000-000000000000", 直接返回null.
    //
    if (UUIDUtil.emptyString().equals(this.getParentId())) {
      return null;
    }

    if (mParent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      mParent = MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getParentId());
    }

    return mParent;
  }
  ///#endregion

  ///#region 属性:CorporationId

  /**
   * 公司标识
   */
  public final String getCorporationId() {
    return this.getCorporation() == null ? "" : this.getCorporation().getId();
  }
  ///#endregion

  ///#region 属性:CorporationName

  /**
   * 公司名称
   */
  public final String getCorporationName() {
    return this.getCorporation() == null ? "" : this.getCorporation().getName();
  }
  ///#endregion

  ///#region 属性:Corporation
  private IOrganizationUnitInfo mCorporation = null;

  /**
   * 公司
   */
  public final IOrganizationUnitInfo getCorporation() {
    if (mCorporation == null && !StringUtil.isNullOrEmpty(this.getId())) {
      mCorporation = MembershipManagement.getInstance().getOrganizationUnitService().findCorporationByOrganizationUnitId(this.getId());
    }

    return mCorporation;
  }

  private List<IAuthorizationObject> mChindNodes = null;

  /**
   * 子节点
   */
  public final List<IAuthorizationObject> getChindNodes() {
    if (mChindNodes == null) {
      mChindNodes = MembershipManagement.getInstance().getOrganizationUnitService().getChildNodes(this.getId());
    }

    return mChindNodes;
  }

  ///#endregion

  ///#region 属性:Roles
  private List<IRoleInfo> mRoles = null;

  /**
   * 所属角色信息
   */
  public final List<IRoleInfo> getRoles() {
    if (mRoles == null) {
      this.mRoles = MembershipManagement.getInstance().getRoleService().findAllByOrganizationUnitId(this.getId());
    }

    return this.mRoles;
  }

  private String mRoleText;

  /**
   * 所属角色视图
   */
  public final String getRoleText() {
    if (StringUtil.isNullOrEmpty(this.mRoleText) && !this.getRoles().isEmpty()) {
      for (IRoleInfo role : this.getRoles()) {
        this.mRoleText += String.format("role#%1$s#%2$s,", role.getId(), role.getGlobalName());
      }

      this.mRoleText = StringUtil.trimEnd(this.mRoleText, ",");
    }

    return this.mRoleText;
  }

  private String mRoleView;

  /**
   * 所属角色视图
   */
  public final String getRoleView() {
    if (StringUtil.isNullOrEmpty(this.mRoleView) && !this.getRoles().isEmpty()) {
      for (IRoleInfo role : this.getRoles()) {
        this.mRoleView += role.getGlobalName() + ";";
      }

      this.mRoleView = StringUtil.trimEnd(this.mRoleView, ";");
    }

    return this.mRoleView;
  }
  ///#endregion

  ///#region 属性:RoleMemberView
  private String mRoleMemberView = "";

  /**
   * 所属角色的成员视图
   */
  public final String getRoleMemberView() {
    if (StringUtil.isNullOrEmpty(this.mRoleMemberView) && !this.getRoles().isEmpty()) {
      List<IAccountInfo> list = MembershipManagement.getInstance().getAccountService().findAllByOrganizationUnitId(this.getId());

      for (IAccountInfo item : list) {
        this.mRoleMemberView += (StringUtil.isNullOrEmpty(item.getGlobalName()) ? item.getName() : item.getGlobalName()) + ";";
      }

      this.mRoleMemberView = StringUtil.trimEnd(this.mRoleMemberView, ";");
    }

    return this.mRoleMemberView;
  }

  private int mEnableEmail;

  /**
   * 是否启用邮件
   */
  public final int getEnableEmail() {
    return mEnableEmail;
  }

  public final void setEnableEmail(int value) {
    mEnableEmail = value;
  }

  private int mEffectScope = -1;

  /**
   * 作用范围 0:local | 1:deep
   */
  public final int getEffectScope() {
    return mEffectScope;
  }

  public final void setEffectScope(int value) {
    mEffectScope = value;
  }

  private String mEffectScopeView;

  /**
   * 作用范围视图
   */
  public final String getEffectScopeView() {
    if (StringUtil.isNullOrEmpty(mEffectScopeView) && this.getEffectScope() != -1) {
      mEffectScopeView = MembershipManagement.getInstance().getSettingService().getText("应用管理_协同平台_人员及权限管理_权限作用范围", String.valueOf(this.getEffectScope()));
    }

    return mEffectScopeView;
  }

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
   * 排序
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

  ///#region 属性:FullPath
  private String mFullPath = null;

  /**
   * 所属组织架构全路径
   */
  public final String getFullPath() {
    return mFullPath;
  }

  public final void setFullPath(String value) {
    mFullPath = value;
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
  private java.time.LocalDateTime mModifiedDate = java.time.LocalDateTime.MIN;

  /**
   * 修改时间
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }
  ///#endregion

  ///#region 属性:CreatedDate
  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.MIN;

  /**
   * 创建时间
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
  }

  // -------------------------------------------------------
  // 显示实现 ICacheable
  // -------------------------------------------------------

  public java.time.LocalDateTime mExpires = java.time.LocalDateTime.now().plusHours(6);

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
  // 实现 IAuthorizationObject
  // -------------------------------------------------------

  /**
   * 授权对象类型
   */
  public String getAuthorizationObjectType() {
    return "OrganizationUnit";
  }

  /**
   * 授权对象唯一标识
   */
  public String getAuthorizationObjectId() {
    return this.getId();
  }

  public void setAuthorizationObjectId(String value) {
    this.setId(value);
  }

  /**
   * 授权对象名称
   */
  public String getAuthorizationObjectName() {
    return this.getName();
  }

  public void setAuthorizationObjectName(String value) {
    this.setName(value);
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
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    if (displayComment) {
      outString.append("<!-- 组织对象 -->");
    }
    outString.append("<organization>");
    if (displayComment) {
      outString.append("<!-- 组织标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    if (displayComment) {
      outString.append("<!-- 编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", this.getCode()));
    if (displayComment) {
      outString.append("<!-- 名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", this.getName()));
    if (displayComment) {
      outString.append("<!-- 全称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<fullName><![CDATA[%1$s]]></fullName>", this.getFullName()));
    if (displayComment) {
      outString.append("<!-- 全局名称 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<globalName><![CDATA[%1$s]]></globalName>", this.getGlobalName()));
    if (displayComment) {
      outString.append("<!-- 拼音 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<pinyin><![CDATA[%1$s]]></pinyin>", this.getPinYin()));
    if (displayComment) {
      outString.append("<!-- 类型 (整型) (int) -->");
    }
    outString.append(String.format("<type><![CDATA[%1$s]]></type>", this.getType()));
    if (displayComment) {
      outString.append("<!-- 父级对象标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", this.getParentId()));
    if (displayFriendlyName) {
      if (displayComment) {
        outString.append("<!-- 父级对象名称 (字符串) (nvarchar(50)) -->");
      }
      outString.append(String.format("<parentName><![CDATA[%1$s]]></parentName>", this.getParentGlobalName()));
    }
    if (displayComment) {
      outString.append("<!-- 排序编号 (字符串) nvarchar(20) -->");
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
    outString.append("</organization>");

    return outString.toString();
  }

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  public final void deserialize(Element element) {
    // 以下数据由人力资源输入.
    this.setId(element.selectSingleNode("id").getText());

    if (element.selectSingleNode("standardOrganizationUnitId") != null) {
      this.setStandardOrganizationUnitId(element.selectSingleNode("standardOrganizationUnitId").getText());
    }

    this.setCode(element.selectSingleNode("code").getText());
    this.setName(element.selectSingleNode("name").getText());

    if (element.selectSingleNode("fullName") != null) {
      this.setFullName(element.selectSingleNode("fullName").getText());
    }
    if (element.selectSingleNode("globalName") != null) {
      this.setGlobalName(element.selectSingleNode("globalName").getText());
    }
    if (element.selectSingleNode("pinyin") != null) {
      this.setPinYin(element.selectSingleNode("pinyin").getText());
    }
    this.setType(Integer.parseInt(element.selectSingleNode("type").getText()));
    this.setParentId(element.selectSingleNode("parentId").getText());
    this.setOrderId(element.selectSingleNode("orderId").getText());
    this.setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    if (element.selectSingleNode("remark") != null) {
      this.setRemark(element.selectSingleNode("remark").getText());
    }
    this.setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("updateDate").getText()));
  }
}
