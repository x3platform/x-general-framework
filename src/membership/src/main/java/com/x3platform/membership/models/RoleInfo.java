package com.x3platform.membership.models;

import java.util.*;

import com.x3platform.SpringContext;
import com.x3platform.membership.*;

import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.dom4j.Element;

import com.x3platform.membership.configuration.*;
import com.x3platform.membership.configuration.*;
import com.x3platform.security.authority.*;

/**
 * 普通角色信息
 */
public class RoleInfo implements IRoleInfo {
  /**
   */
  public RoleInfo() {
  }

  /**
   */
  public RoleInfo(String id) {
    this.setId(id);
  }

  //
  // 属性, 可存放扩展属性, 临时属性
  //
  // 可添加的属性 例如: ParentId , ParentName
  //

  ///#region 属性:Properties
  private IExtensionInformation mExtensionInformation = null;

  /**
   * 属性
   */
  public final IExtensionInformation getExtensionInformation() {
    if (mExtensionInformation == null) {
      // String springObjectFile = MembershipConfigurationView.getInstance().Configuration.keySet()["SpringObjectFile"].Value;
      // SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(MembershipConfiguration.ApplicationName, springObjectFile);

      // String extensionInformationName = "X3Platform.Membership.Model.RoleInfo.ExtensionInformation";

      // mExtensionInformation = objectBuilder.<IExtensionInformation>GetObject(extensionInformationName);

      // TODO 待处理
      // SpringContext.getBean("com.x3platform.membership.models.RoleInfo.ExtensionInformation", IExtensionInformation.class);
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
  ///#endregion

  ///#region 属性:Code
  private String mCode;

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

  ///#region 属性:GlobalName
  private String mGlobalName;

  /**
   * 全局名称
   */
  public final String getGlobalName() {
    return StringUtil.isNullOrEmpty(mGlobalName) ? this.getName() : this.mGlobalName;
  }

  public final void setGlobalName(String value) {
    mGlobalName = value;
  }
  ///#endregion

  ///#region 属性:PinYin
  private String mPinYin;

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
  private int mType;

  /**
   * 类型
   */
  public final int getType() {
    return mType;
  }

  public final void setType(int value) {
    mType = value;
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

  /**
   * 父节点名称
   */
  public final String getParentName() {
    return this.getParent() == null ? "" : this.getParent().getName();
  }
  ///#endregion

  ///#region 属性:Parent
  private IRoleInfo mParent = null;

  /**
   * 父级对象
   */
  public final IRoleInfo getParent() {
    //
    // ParentId = "00000000-0000-0000-0000-000000000000" 表示父节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当ParentId = "00000000-0000-0000-0000-000000000000", 直接返回null.
    //
    if (this.getParentId().equals("00000000-0000-0000-0000-000000000000")) {
      return null;
    }

    if (mParent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      // TODO 待处理
      // mParent = MembershipManagement.getInstance().getRoleService().findOne(this.getParentId());
    }

    return mParent;
  }

  /**
   * 公司标识
   */
  public final String getCorporationId() {
    return this.getCorporation() == null ? "" : this.getCorporation().getId();
  }

  /**
   * 公司名称
   */
  public final String getCorporationName() {
    return this.getCorporation() == null ? "" : this.getCorporation().getName();
  }

  private IOrganizationUnitInfo mCorporation = null;

  /**
   * 公司
   */
  public final IOrganizationUnitInfo getCorporation() {
    if (mCorporation == null && !StringUtil.isNullOrEmpty(this.getOrganizationUnitId())) {
      // TODO 待处理
      // mCorporation = MembershipManagement.getInstance().getOrganizationUnitService().findCorporationByOrganizationUnitId(this.getOrganizationUnitId());
    }

    return mCorporation;
  }
  ///#endregion

  ///#region 属性:GeneralRoleId
  private String mGeneralRoleId;

  /**
   * 所属通用角色标识
   */
  // public final String getGeneralRoleId() {
  //  if (StringUtil.isNullOrEmpty(this.mGeneralRoleId)) {
  //    this.mGeneralRoleId = MembershipConfigurationView.getInstance().getDefaultGeneralRoleId();
  //  }
  //
  //  return mGeneralRoleId;
  // }
  public final void setGeneralRoleId(String value) {
    mGeneralRoleId = value;
  }
  ///#endregion

  ///#region 属性:GeneralRoleName

  /**
   * 所属通用角色名称
   */
  // public final String getGeneralRoleName() {
  //  return this.getGeneralRole() == null ? "" : this.getGeneralRole().Name;
  // }

  // private GeneralRoleInfo mGeneralRole = null;

  /**
   * 所属通用角色
   */
  // public final GeneralRoleInfo getGeneralRole() {
  //  if (mGeneralRole == null && !StringUtil.isNullOrEmpty(this.getGeneralRoleId())) {
  //    mGeneralRole = MembershipManagement.Instance.GeneralRoleService[this.getGeneralRoleId()];
  //  }
  //
  //  return mGeneralRole;
  // }

  private String mStandardRoleId;

  /**
   * 标准角色标识
   */
  public final String getStandardRoleId() {
    if (StringUtil.isNullOrEmpty(this.mStandardRoleId)) {
      // TODO 待处理
      // this.mStandardRoleId = MembershipConfigurationView.getInstance().getDefaultStandardRoleId();
    }

    return mStandardRoleId;
  }

  public final void setStandardRoleId(String value) {
    mStandardRoleId = value;
  }

  /**
   * 标准角色名称
   */
  // public final String getStandardRoleName() {
  //  return this.getStandardRole() == null ? "" : this.getStandardRole().Name;
  // }

  // private IStandardRoleInfo mStandardRole = null;

  /**
   * 标准组织对象
   */
  // public final IStandardRoleInfo getStandardRole() {
  //  if (mStandardRole == null && !StringUtil.isNullOrEmpty(this.getStandardRoleId())) {
  //    mStandardRole = MembershipManagement.Instance.StandardRoleService[this.getStandardRoleId()];
  //  }

  //  return mStandardRole;
  // }

  private String mOrganizationUnitId;

  /**
   * 所属组织标识
   */
  public final String getOrganizationUnitId() {
    return mOrganizationUnitId;
  }

  public final void setOrganizationUnitId(String value) {
    mOrganizationUnitId = value;
  }

  /**
   * 所属组织全局名称
   */
  public final String getOrganizationUnitGlobalName() {
    return this.getOrganizationUnit() == null ? "" : this.getOrganizationUnit().getGlobalName();
  }

  private IOrganizationUnitInfo mOrganizationUnit = null;

  /**
   * 父级对象
   */
  public final IOrganizationUnitInfo getOrganizationUnit() {
    if (mOrganizationUnit == null && !StringUtil.isNullOrEmpty(this.getOrganizationUnitId())) {
      // TODO 待处理
      // mOrganizationUnit = MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getOrganizationUnitId());
    }

    return mOrganizationUnit;
  }

  // private List<IAssignedJobInfo> mAssignedJobs = null;

  /**
   * 相关岗位信息
   */
  // public final List<IAssignedJobInfo> getAssignedJobs() {
  //  if (mAssignedJobs == null && !StringUtil.isNullOrEmpty(this.getStandardRoleId())) {
  //    mAssignedJobs = MembershipManagement.Instance.AssignedJobService.FindAllByRoleId(this.getId());
  //  }

  //  return mAssignedJobs;
  // }

  /**
   * 所属岗位名称
   */
  // public final String getAssignedJobNames() {
  //  if (this.getAssignedJobs() == null) {
  //    return "";
  //  }
  //
  //  String outString = null;
  //
  // for (IAssignedJobInfo item : this.getAssignedJobs()) {
  //    outString += item.Name + ";";
  //  }

  //  return outString;
  // }

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
  ///#endregion

  ///#region 属性:EffectScope
  private int mEffectScope;

  /**
   * 作用范围 0:local | 1:deep
   */
  public final int getEffectScope() {
    return mEffectScope;
  }

  public final void setEffectScope(int value) {
    mEffectScope = value;
  }
  ///#endregion

  ///#region 属性:EffectScopeView
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
  // 重置成员关系
  // -------------------------------------------------------

  /**
   * 重置成员关系
   *
   * @param relationText
   */
  public final void resetMemberRelations(String relationText) {
    String[] list = relationText.split(",|;");

    // 清空旧的成员关系
    this.getMembers().clear();

    String[] keys = null;

    // 设置新的成员关系
    for (String item : list) {
      keys = item.split("#");

      if (keys.length > 2 && keys[0].equals("account")) {
        this.getMembers().add(MembershipManagement.getInstance().getAccountService().findOne(keys[1]));
      }
    }
  }

  // -------------------------------------------------------
  // 角色所拥有的成员
  // -------------------------------------------------------

  private List<IAccountInfo> mMembers = null;

  /**
   * 权限
   */
  public final List<IAccountInfo> getMembers() {
    if (mMembers == null && !StringUtil.isNullOrEmpty(this.getId())) {
      mMembers = MembershipManagement.getInstance().getAccountService().findAllByRoleId(this.getId());
    }

    return mMembers;
  }

  private String mMemberView;

  /**
   * 权限视图
   */
  public final String getMemberView() {
    if (this.getMembers() != null && StringUtil.isNullOrEmpty(this.mMemberView) && !this.getMembers().isEmpty()) {
      for (IAccountInfo item : getMembers()) {
        this.mMemberView += item.getGlobalName() + ";";
      }

      this.mMemberView = StringUtil.trimEnd(this.mMemberView, ";");
    }

    return this.mMemberView;
  }

  private String mMemberText;

  /**
   * 成员视图
   */
  public final String getMemberText() {
    if (this.getMembers() != null && StringUtil.isNullOrEmpty(this.mMemberText) && !this.getMembers().isEmpty()) {
      for (IAccountInfo item : getMembers()) {
        this.mMemberText += String.format("account#%1$s#%2$s,", item.getId(), item.getGlobalName());
      }

      this.mMemberText = StringUtil.trimEnd(this.mMemberText, ",");
    }

    return this.mMemberText;
  }

  // -------------------------------------------------------
  // 显式实现 IAuthorizationObject Type
  // -------------------------------------------------------

  /**
   * 授权对象类型
   */
  public String getAuthorizationObjectType() {
    return "Role";
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
      outString.append("<!-- 角色对象 -->");
    }
    outString.append("<role>");
    if (displayComment) {
      outString.append("<!-- 角色标识 (字符串) (nvarchar(36)) -->");
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
      outString.append("<!-- 拼音 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<pinyin><![CDATA[%1$s]]></pinyin>", this.getPinYin()));
    if (displayComment) {
      outString.append("<!-- 所属标准角色标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<standardRoleId><![CDATA[%1$s]]></standardRoleId>", this.getStandardRoleId()));
    if (displayComment) {
      outString.append("<!-- 所属组织标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<organizationId><![CDATA[%1$s]]></organizationId>", this.getOrganizationUnitId()));
    if (displayComment) {
      outString.append("<!-- 上级角色标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", this.getParentId()));
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
    outString.append(String.format("<modifiedDate><![CDATA[%1$s]]></modifiedDate>", this.getModifiedDate()));
    outString.append("</role>");

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
    this.setName(element.selectSingleNode("name").getText());
    this.setPinYin(element.selectSingleNode("pinyin").getText());
    this.setOrganizationUnitId(element.selectSingleNode("organizationId").getText());
    // this.setStandardRoleId(element.selectSingleNode("standardRoleId").getText());
    this.setParentId(element.selectSingleNode("parentId").getText());
    this.setOrderId(element.selectSingleNode("orderId").getText());
    this.setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    this.setRemark(element.selectSingleNode("remark").getText());
    this.setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("modifiedDate").getText()));
  }
}
