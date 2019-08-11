package com.x3platform.membership.models;

import com.x3platform.membership.Account;
import com.x3platform.membership.ExtensionInformation;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.util.Date;
import java.util.List;
import org.apache.http.client.utils.DateUtils;
import org.dom4j.Element;

/**
 * 角色信息
 *
 * @author ruanyu
 */
public class RoleInfo implements Role {
  /**
   */
  public RoleInfo() {
  }

  /**
   */
  public RoleInfo(String id) {
    setId(id);
  }

  //
  // 属性, 可存放扩展属性, 临时属性
  //
  // 可添加的属性 例如: ParentId , ParentName
  //

  private ExtensionInformation mExtensionInformation = null;

  /**
   * 属性
   */
  @Override
  public final ExtensionInformation getExtensionInformation() {
    return mExtensionInformation;
  }

  //
  // 具体属性
  //

  private String id;

  /**
   */
  @Override
  public final String getId() {
    return id;
  }

  @Override
  public final void setId(String value) {
    id = value;
  }

  private String code;

  /**
   * 编码
   */
  @Override
  public final String getCode() {
    return code;
  }

  @Override
  public final void setCode(String value) {
    code = value;
  }
  ///#endregion

  ///#region 属性:Name
  private String name;

  /**
   * 名称
   */
  @Override
  public final String getName() {
    return name;
  }

  @Override
  public final void setName(String value) {
    name = value;
  }
  ///#endregion

  ///#region 属性:GlobalName
  private String globalName;

  /**
   * 全局名称
   */
  @Override
  public final String getGlobalName() {
    return globalName;
  }

  public final void setGlobalName(String value) {
    globalName = value;
  }
  ///#endregion

  ///#region 属性:PinYin
  private String pinYin;

  /**
   * 拼音
   */
  @Override
  public final String getPinYin() {
    return pinYin;
  }

  @Override
  public final void setPinYin(String value) {
    pinYin = value;
  }
  ///#endregion

  ///#region 属性:Type
  private int type;

  /**
   * 类型
   */
  public final int getType() {
    return type;
  }

  public final void setType(int value) {
    type = value;
  }

  private String parentId;

  /**
   * 父节点标识
   */
  public final String getParentId() {
    return parentId;
  }
  public final void setParentId(String value) {
    // 未设置父及角色，则设置成 00000000-0000-0000-0000-000000000000
    if(StringUtil.isNullOrEmpty(value)){
      value = "0";
    }
    parentId = value;
  }

  /**
   * 父节点名称
   */
  private String parentName;

  public final String getParentName() {
    return parentName;
  }

  public final void setParentName(String value) {
    parentName = value;
  }


  ///#endregion
  ///#region 属性:Parent
  private Role parent = null;


  /**
   * 父级对象
   */
  @Override
  public final Role getParent() {
    return parent;
  }

  public final Role setParent(Role value) {
    parent = value;
    return parent;
  }

  /**
   * 公司标识
   */

  private String corporationId;
  public final String getCorporationId() {
    return corporationId;
  }

  public final String setCorporationId(String value) {
    corporationId = value;
    return corporationId;
  }

  /**
   * 公司名称
   */
  private String corporationName;
  public final String getCorporationName() {
    return corporationName;
  }

  public final String setCorporationName(String value) {
    corporationName = value;
    return corporationName;
  }

  private OrganizationUnit mCorporation = null;

  /**
   * 公司
   */
  public final OrganizationUnit getCorporation() {
    return mCorporation;
  }

  public final OrganizationUnit setCorporation(OrganizationUnit value) {
    mCorporation = value;
    return mCorporation;
  }

  ///#endregion

  ///#region 属性:GeneralRoleId
  /**
   * 所属通用角色标识
   */
  private String generalRoleId;
   public final String getGeneralRoleId() {
    return generalRoleId;
   }
  public final void setGeneralRoleId(String value) {
    generalRoleId = value;
  }
  ///#endregion
  ///#region 属性:GeneralRoleName
  /**
   * 所属通用角色名称
   */

  /**
   * 所属通用角色
   */
  private String standardRoleId;

  /**
   * 标准角色标识
   */
  @Override
  public final String getStandardRoleId() {
    return standardRoleId;
  }

  @Override
  public final void setStandardRoleId(String value) {
    standardRoleId = value;
  }

  /**
   * 标准角色名称
   */
  /**
   * 标准组织对象
   */
  private String organizationUnitId;

  /**
   * 所属组织标识
   */
  @Override
  public final String getOrganizationUnitId() {
    return organizationUnitId;
  }

  @Override
  public final void setOrganizationUnitId(String value) {
    organizationUnitId = value;
  }

  private String organizationUnitName ;

  public String getOrganizationUnitName() {
    return organizationUnitName;
  }

  public void setOrganizationUnitName(String organizationUnitName) {
    this.organizationUnitName = organizationUnitName;
  }

  private OrganizationUnit mOrganizationUnit = null;
  /**
   *  所属组织对象
   */
  @Override
  public final OrganizationUnit getOrganizationUnit() {
    return mOrganizationUnit;
  }
  /**
   * 所属组织全局名称
   */
  private String organizationUnitGlobalName;
  public final String getOrganizationUnitGlobalName() {
    return organizationUnitGlobalName;
  }
  /**
   * 所属岗位名称
   */
  private int enableEmail;

  /**
   * 是否启用邮件
   */
  @Override
  public final int getEnableEmail() {
    return enableEmail;
  }

  @Override
  public final void setEnableEmail(int value) {
    enableEmail = value;
  }
  ///#endregion

  ///#region 属性:EffectScope
  private int effectScope=0;

  /**
   * 作用范围 0:local | 1:deep
   */
  public final int getEffectScope() {
    return effectScope;
  }

  public final void setEffectScope(int value) {
    effectScope = value;
  }
  ///#endregion

  ///#region 属性:EffectScopeView
  private String effectScopeView;

  /**
   * 作用范围视图, 查看作用范围视图
   */
  public final String getEffectScopeView() {
    return effectScopeView;
  }

  private int locking = 1;

  /**
   *  防止意外删除 0 不锁定 | 1 锁定(默认)
   */
  @Override
  public final int getLocking() {
    return locking;
  }

  @Override
  public final void setLocking(int value) {
    locking = value;
  }

  private String orderId;

  /**
   * 排序
   */
  public final String getOrderId() {
    return orderId;
  }

  public final void setOrderId(String value) {
    orderId = value;
  }

  private int status;

  /**
   * 状态
   */
  @Override
  public final int getStatus() {
    return status;
  }

  @Override
  public final void setStatus(int value) {
    status = value;
  }

  private String remark;

  /**
   * 备注
   */
  @Override
  public final String getRemark() {
    return remark;
  }

  @Override
  public final void setRemark(String value) {
    remark = value;
  }

  private String fullPath = null;

  /**
   * 所属组织架构全路径
   */
  @Override
  public final String getFullPath() {
    return fullPath;
  }

  @Override
  public final void setFullPath(String value) {
    fullPath = value;
  }

  private String distinguishedName = null;

  /**
   * 唯一名称
   */
  @Override
  public final String getDistinguishedName() {
    return distinguishedName;
  }

  @Override
  public final void setDistinguishedName(String value) {
    distinguishedName = value;
  }

  private Date modifiedDate = DateUtil.getDefaultDate();

  /**
   * 修改时间
   */
  @Override
  public final Date getModifiedDate() {
    return modifiedDate;
  }

  @Override
  public final void setModifiedDate(Date value) {
    modifiedDate = value;
  }

  private Date createdDate = DateUtil.getDefaultDate();

  /**
   * 创建时间
   */
  public final Date getCreatedDate() {
    return createdDate;
  }

  public final void setCreatedDate(Date value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 重置成员关系
  // -------------------------------------------------------

  /**
   * 重置成员关系
   *
   * @param relationText
   */
  @Override
  public final void resetMemberRelations(String relationText) {
  }

  // -------------------------------------------------------
  // 角色所拥有的成员
  // -------------------------------------------------------

  private List<Account> mMembers = null;

  /**
   * 权限
   */
  @Override
  public final List<Account> getMembers() {
    return mMembers;
  }

  private String mMemberView;

  /**
   * 权限视图
   */
  public final String getMemberView() {
    return mMemberView;
  }

  private String mMemberText;

  /**
   * 成员视图
   */
  public final String getMemberText() {
    return mMemberText;
  }

  // -------------------------------------------------------
  // 显式实现 AuthorizationObject Type
  // -------------------------------------------------------

  /**
   * 授权对象类型
   */
  @Override
  public String getAuthorizationObjectType() {
    return "Role";
  }

  /**
   * 授权对象唯一标识
   */
  @Override
  public String getAuthorizationObjectId() {
    return getId();
  }

  @Override
  public void setAuthorizationObjectId(String value) {
    setId(value);
  }

  /**
   * 授权对象名称
   */
  @Override
  public String getAuthorizationObjectName() {
    return getName();
  }

  @Override
  public void setAuthorizationObjectName(String value) {
    setName(value);
  }

  // -------------------------------------------------------
  // 实现 SerializedObject 序列化
  // -------------------------------------------------------

  /**
   * 根据对象导出Xml元素
   */
  @Override
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
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    if (displayComment) {
      outString.append("<!-- 角色对象 -->");
    }
    outString.append("<role>");
    if (displayComment) {
      outString.append("<!-- 角色标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", getId()));
    if (displayComment) {
      outString.append("<!-- 编码 (字符串) (nvarchar(30)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", getCode()));
    if (displayComment) {
      outString.append("<!-- 名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", getName()));
    if (displayComment) {
      outString.append("<!-- 拼音 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<pinyin><![CDATA[%1$s]]></pinyin>", getPinYin()));
    if (displayComment) {
      outString.append("<!-- 所属标准角色标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<standardRoleId><![CDATA[%1$s]]></standardRoleId>", getStandardRoleId()));
    if (displayComment) {
      outString.append("<!-- 所属组织标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<organizationId><![CDATA[%1$s]]></organizationId>", getOrganizationUnitId()));
    if (displayComment) {
      outString.append("<!-- 上级角色标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", getParentId()));
    if (displayComment) {
      outString.append("<!-- 排序编号(字符串) nvarchar(20) -->");
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
    outString.append(String.format("<modifiedDate><![CDATA[%1$s]]></modifiedDate>", getModifiedDate()));
    outString.append("</role>");

    return outString.toString();
  }

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  @Override
  public final void deserialize(Element element) {
    setId(element.selectSingleNode("id").getText());
    setCode(element.selectSingleNode("code").getText());
    setName(element.selectSingleNode("name").getText());
    setPinYin(element.selectSingleNode("pinyin").getText());
    setOrganizationUnitId(element.selectSingleNode("organizationId").getText());
    // this.setStandardRoleId(element.selectSingleNode("standardRoleId").getText());
    setParentId(element.selectSingleNode("parentId").getText());
    setOrderId(element.selectSingleNode("orderId").getText());
    setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    setRemark(element.selectSingleNode("remark").getText());
    setModifiedDate(DateUtils.parseDate(element.selectSingleNode("modifiedDate").getText()));
  }
}
