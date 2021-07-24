package com.x3platform.membership.models;

import static com.x3platform.Constants.TEXT_EMPTY;
import static com.x3platform.membership.Constants.STANDARD_ROLE_ROOT_ID;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.membership.*;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
   *
   */
  public RoleInfo() {
  }

  /**
   *
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
  public ExtensionInformation getExtensionInformation() {
    return mExtensionInformation;
  }

  //
  // 具体属性
  //

  private String id;

  /**
   *
   */
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String value) {
    id = value;
  }

  private String code;

  /**
   * 编码
   */
  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(String value) {
    code = value;
  }

  private String name;

  /**
   * 名称
   */
  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String value) {
    name = value;
  }

  private String globalName;

  /**
   * 全局名称
   */
  @Override
  public String getGlobalName() {
    return globalName;
  }

  public void setGlobalName(String value) {
    globalName = value;
  }

  private String pinYin;

  /**
   * 拼音
   */
  @Override
  public String getPinYin() {
    return pinYin;
  }

  @Override
  public void setPinYin(String value) {
    pinYin = value;
  }

  private int type;

  /**
   * 类型
   */
  public int getType() {
    return type;
  }

  public void setType(int value) {
    type = value;
  }

  private String parentId;

  /**
   * 父节点标识
   */
  public String getParentId() {
    return parentId;
  }

  public void setParentId(String value) {
    // 未设置父及角色，则设置成 00000000-0000-0000-0000-000000000000
    if (StringUtil.isNullOrEmpty(value)) {
      value = "0";
    }
    parentId = value;
  }

  /**
   * 父节点名称
   */
  private String parentName;

  public String getParentName() {
    return getParent() == null ? TEXT_EMPTY : getParent().getName();
  }

  private Role parent = null;

  /**
   * 父级对象
   */
  @JSONField(serialize = false)
  @Override
  public Role getParent() {
    if (parent == null && !StringUtil.isNullOrEmpty(parentId)) {
      parent = MembershipManagement.getInstance().getRoleService().findOne(parentId);
    }
    return parent;
  }

  /**
   * 公司标识
   */

  private String corporationId;

  public String getCorporationId() {
    return corporationId;
  }

  public void setCorporationId(String value) {
    corporationId = value;
  }

  /**
   * 公司名称
   */
  private String corporationName;

  public String getCorporationName() {
    return corporationName;
  }

  public void setCorporationName(String value) {
    corporationName = value;
  }

  private OrganizationUnit corporation = null;

  /**
   * 公司
   */
  public OrganizationUnit getCorporation() {
    return corporation;
  }

  public void setCorporation(OrganizationUnit value) {
    corporation = value;
  }

  /**
   * 所属通用角色标识
   */
  private String generalRoleId;

  public String getGeneralRoleId() {
    return generalRoleId;
  }

  public void setGeneralRoleId(String value) {
    generalRoleId = value;
  }

  /**
   * 所属标准角色
   */
  @JSONField(serialize = false)
  private StandardRole standardRole = null;

  public StandardRole getStandardRole() {
    //
    // StandardRoleId = "00000000-0000-0000-0000-000000000000" 表示根节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当 StandardRoleId = "00000000-0000-0000-0000-000000000000", 直接返回 null.
    if (StringUtil.isNullOrEmpty(this.getStandardRoleId()) || STANDARD_ROLE_ROOT_ID.equals(this.getStandardRoleId())) {
      return null;
    }

    if (standardRole == null && !StringUtil.isNullOrEmpty(this.getStandardRoleId())) {
      standardRole = MembershipManagement.getInstance().getStandardRoleService().findOne(this.getStandardRoleId());
    }

    return standardRole;
  }

  /**
   * 所属角色标识
   */
  private String standardRoleId;

  /**
   * 标准角色标识
   */
  @Override
  public String getStandardRoleId() {
    return standardRoleId;
  }

  @Override
  public void setStandardRoleId(String value) {
    standardRoleId = value;
  }

  public String getStandardRoleName() {
    return this.getStandardRole() == null ? TEXT_EMPTY : this.getStandardRole().getName();
  }

  /**
   * 所属组织标识
   */
  private String organizationUnitId;

  /**
   * 所属组织标识
   */
  @Override
  public String getOrganizationUnitId() {
    return organizationUnitId;
  }

  @Override
  public void setOrganizationUnitId(String value) {
    organizationUnitId = value;
  }

  public String getOrganizationUnitName() {
    return this.getOrganizationUnit() == null ? TEXT_EMPTY : this.getOrganizationUnit().getName();
  }

  private OrganizationUnit organizationUnit = null;

  /**
   * 所属组织对象
   */
  @JSONField(serialize = false)
  @Override
  public OrganizationUnit getOrganizationUnit() {
    if (organizationUnit == null && !StringUtil.isNullOrEmpty(organizationUnitId)) {
      organizationUnit = MembershipManagement.getInstance().getOrganizationUnitService().findOne(organizationUnitId);
    }

    return organizationUnit;
  }

  /**
   * 所属组织全局名称
   */
  public String getOrganizationUnitGlobalName() {
    return this.getOrganizationUnit() == null ? TEXT_EMPTY : this.getOrganizationUnit().getGlobalName();
  }

  /**
   * 所属岗位名称
   */
  private int enableEmail;

  /**
   * 是否启用邮件
   */
  @Override
  public int getEnableEmail() {
    return enableEmail;
  }

  @Override
  public void setEnableEmail(int value) {
    enableEmail = value;
  }

  private int effectScope = 0;

  /**
   * 作用范围 0:local | 1:deep
   */
  public int getEffectScope() {
    return effectScope;
  }

  public void setEffectScope(int value) {
    effectScope = value;
  }

  private String effectScopeView;

  /**
   * 作用范围视图, 查看作用范围视图
   */
  public String getEffectScopeView() {
    return effectScopeView;
  }

  private int locking = 1;

  /**
   * 防止意外删除 0 不锁定 | 1 锁定(默认)
   */
  @Override
  public int getLocking() {
    return locking;
  }

  @Override
  public void setLocking(int value) {
    locking = value;
  }

  private String orderId;

  /**
   * 排序
   */
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String value) {
    orderId = value;
  }

  private int status;

  /**
   * 状态
   */
  @Override
  public int getStatus() {
    return status;
  }

  @Override
  public void setStatus(int value) {
    status = value;
  }

  private String remark;

  /**
   * 备注
   */
  @Override
  public String getRemark() {
    return remark;
  }

  @Override
  public void setRemark(String value) {
    remark = value;
  }

  private String fullPath = null;

  /**
   * 所属组织架构全路径
   */
  @Override
  public String getFullPath() {
    return fullPath;
  }

  @Override
  public void setFullPath(String value) {
    fullPath = value;
  }

  private String distinguishedName = null;

  /**
   * 唯一名称
   */
  @Override
  public String getDistinguishedName() {
    return distinguishedName;
  }

  @Override
  public void setDistinguishedName(String value) {
    distinguishedName = value;
  }

  private LocalDateTime modifiedDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 修改时间
   */
  @Override
  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  @Override
  public void setModifiedDate(LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = DateUtil.getDefaultLocalDateTime();

  /**
   * 创建时间
   */
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 重置成员关系
  // -------------------------------------------------------

  /**
   * 重置成员关系
   */
  @Override
  public void resetMemberRelations(String relationText) {
    String[] list = StringUtil.isNullOrEmpty(relationText) ? new String[0] : relationText.split(",|;");

    this.getMembers().clear();

    for (String item : list) {
      String[] keys = item.split("#");

      if (keys.length > 2 && "account".equals(keys[0])) {
        this.getMembers().add(MembershipManagement.getInstance().getAccountService().findOne(keys[1]));
      }
    }
  }

  // -------------------------------------------------------
  // 角色所拥有的成员
  // -------------------------------------------------------

  @JSONField(serialize = false)
  private List<Account> members = null;

  /**
   * 成员信息
   */
  @Override
  public List<Account> getMembers() {
    if (members == null) {
      this.members = MembershipManagement.getInstance().getAccountService().findAllByRoleId(this.getId());
    }
    return members;
  }

  private String memberText = "";

  /**
   * 成员文本信息
   */
  public String getMemberText() {
    if (StringUtil.isNullOrEmpty(this.memberText) && !this.getMembers().isEmpty()) {
      for (Account account : this.getMembers()) {
        this.memberText += StringUtil.format("account#{}#{},", account.getId(), account.getGlobalName());
      }

      this.memberText = StringUtil.trimEnd(this.memberText, ",");
    }

    return this.memberText;
  }

  private String memberView = "";

  /**
   * 成员视图
   */
  public String getMemberView() {
    if (StringUtil.isNullOrEmpty(this.memberView) && !this.getMembers().isEmpty()) {
      for (Account account : this.getMembers()) {
        this.memberView += account.getGlobalName() + ",";
      }

      this.memberView = StringUtil.trimEnd(this.memberView, ",");
    }

    return this.memberView;
  }

  // -------------------------------------------------------
  // 实现 AuthorizationObject 接口
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
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
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
  public void deserialize(Element element) {
    setId(element.selectSingleNode("id").getText());
    setCode(element.selectSingleNode("code").getText());
    setName(element.selectSingleNode("name").getText());
    setPinYin(element.selectSingleNode("pinyin").getText());
    setOrganizationUnitId(element.selectSingleNode("organizationId").getText());
    setStandardRoleId(element.selectSingleNode("standardRoleId").getText());
    setParentId(element.selectSingleNode("parentId").getText());
    setOrderId(element.selectSingleNode("orderId").getText());
    setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    setRemark(element.selectSingleNode("remark").getText());
    setModifiedDate(LocalDateTime.parse(element.selectSingleNode("modifiedDate").getText()));
  }
}
