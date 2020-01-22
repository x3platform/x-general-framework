package com.x3platform.membership.models;

import static com.x3platform.Constants.TEXT_EMPTY;
import static com.x3platform.membership.Constants.ORGANIZATION_UNIT_ROOT_ID;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.AuthorizationObject;
import com.x3platform.membership.Account;
import com.x3platform.membership.ExtensionInformation;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织单元信息
 *
 * @author ruanyu
 */
public class OrganizationUnitInfo implements OrganizationUnit {

  private String id = "";
  private String code = "";
  private String name = "";
  private String globalName = "";
  private String fullName = "";
  private String pinYin = "";
  private int level = 0;
  private String standardOrganizationUnitId = "";
  private String standardOrganizationUnitName = "";
  private int enableEmail = 0;
  private int effectScope = 0;
  private int treeViewScope = 0;
  private int locking = 1;
  private String orderId = "";
  private int status = 1;
  private String remark = "";
  private String fullPath = "";
  private String distinguishedName = "";
  private Date modifiedDate = DateUtil.getDefaultDate();
  private Date createdDate = DateUtil.getDefaultDate();

  private List<OrganizationUnit> children = new ArrayList<>();

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @JSONField(serialize = false)
  private OrganizationUnit parent = null;

  @Override
  public OrganizationUnit getParent() {
    //
    // ParentId = "00000000-0000-0000-0000-000000000000" 表示父节点为空
    // 系统中的特殊角色[所有人]的Id为"00000000-0000-0000-0000-000000000000".
    // 所以为避免错误, 当ParentId = "00000000-0000-0000-0000-000000000000", 直接返回 null.
    if (this.getParentId() == ORGANIZATION_UNIT_ROOT_ID || StringUtil.isNullOrEmpty(this.getParentId())) {
      return null;
    }

    if (parent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      parent = MembershipManagement.getInstance().getOrganizationUnitService().findOne(this.getParentId());
    }

    return parent;
  }

  private String parentId = "";

  @Override
  public String getParentId() {
    return parentId;
  }

  @Override
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  @Override
  public String getParentName() {
    return this.getParent() == null ? TEXT_EMPTY : this.getParent().getName();
  }

  @Override
  public String getParentGlobalName() {
    return this.getParent() == null ? TEXT_EMPTY : this.getParent().getGlobalName();
  }

  private String chiefRoleId = "";

  @Override
  public String getChiefRoleId() {
    return chiefRoleId;
  }

  @Override
  public void setChiefRoleId(String value) {
    chiefRoleId = value;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getGlobalName() {
    return globalName;
  }

  @Override
  public void setGlobalName(String globalName) {
    this.globalName = globalName;
  }

  @Override
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  private int type = 0;

  @Override
  public int getType() {
    return type;
  }

  @Override
  public void setType(int value) {
    this.type = value;
  }

  private String typeView = "";

  public String getTypeView() {
    if (StringUtil.isNullOrEmpty(typeView)) {
      typeView = MembershipManagement.getInstance().getSettingService()
        .getText("应用管理_基础支撑平台_人员及权限管理_组织单元管理_组织单元类别", String.valueOf(getType()));
    }

    return typeView;
  }

  public void setTypeView(String typeView) {
    this.typeView = typeView;
  }

  @Override
  public int getLevel() {
    return level;
  }

  @Override
  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public String getStandardOrganizationUnitId() {
    return standardOrganizationUnitId;
  }

  @Override
  public void setStandardOrganizationUnitId(String standardOrganizationUnitId) {
    this.standardOrganizationUnitId = standardOrganizationUnitId;
  }

  public String getStandardOrganizationUnitName() {
    return standardOrganizationUnitName;
  }

  public void setStandardOrganizationUnitName(String standardOrganizationUnitName) {
    this.standardOrganizationUnitName = standardOrganizationUnitName;
  }

  @Override
  public int getEnableEmail() {
    return enableEmail;
  }

  @Override
  public void setEnableEmail(int value) {

  }

  public int getEffectScope() {
    return effectScope;
  }

  public void setEffectScope(int effectScope) {
    this.effectScope = effectScope;
  }

  public int getTreeViewScope() {
    return treeViewScope;
  }

  public void setTreeViewScope(int treeViewScope) {
    this.treeViewScope = treeViewScope;
  }

  @Override
  public String getAuthorizationObjectType() {
    return null;
  }

  @Override
  public String getAuthorizationObjectId() {
    return null;
  }

  @Override
  public void setAuthorizationObjectId(String value) {

  }

  @Override
  public String getAuthorizationObjectName() {
    return null;
  }

  @Override
  public void setAuthorizationObjectName(String value) {

  }

  public int getLocking() {
    return locking;
  }

  public void setLocking(int locking) {
    this.locking = locking;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  public String getDistinguishedName() {
    return distinguishedName;
  }

  public void setDistinguishedName(String distinguishedName) {
    this.distinguishedName = distinguishedName;
  }

  @Override
  public ExtensionInformation getExtensionInformation() {
    return null;
  }

  public Date getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String serializable() {
    return null;
  }

  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    return null;
  }

  @Override
  public void deserialize(Element element) {

  }

  @Override
  public Date getExpires() {
    return null;
  }

  @Override
  public void setExpires(Date value) {

  }

  public List<OrganizationUnit> getChildren() {
    return children;
  }

  public void setChildren(List<OrganizationUnit> children) {
    this.children = children;
  }

  @Override
  public String getPinYin() {
    return pinYin;
  }

  @Override
  public void setPinYin(String value) {
    this.pinYin = value;
  }

  @Override
  public List<AuthorizationObject> getChindNodes() {
    return null;
  }

  @JSONField(serialize = false)
  private List<Role> roles = null;

  /**
   * 所属角色信息
   */
  public List<Role> getRoles() {
    if (roles == null) {
      this.roles = MembershipManagement.getInstance().getRoleService().findAllByOrganizationUnitId(this.getId());
    }

    return this.roles;
  }

  private String roleText = "";

  /**
   * 所属角色视图
   */
  public String getRoleText() {
    if (StringUtil.isNullOrEmpty(this.roleText) && !this.getRoles().isEmpty()) {
      for (Role role : this.getRoles()) {
        this.roleText += String.format("role#%1$s#%2$s,", role.getId(), role.getGlobalName());
      }

      this.roleText = StringUtil.trimEnd(this.roleText, ",");
    }

    return this.roleText;
  }

  private String roleView = "";

  /**
   * 所属角色视图
   */
  public String getRoleView() {
    if (StringUtil.isNullOrEmpty(this.roleView) && !this.getRoles().isEmpty()) {
      for (Role role : this.getRoles()) {
        this.roleView += role.getGlobalName() + ",";
      }

      this.roleView = StringUtil.trimEnd(this.roleView, ",");
    }

    return this.roleView;
  }

  private String roleMemberView = "";

  /**
   * 所属角色的成员视图
   */
  public String getRoleMemberView() {
    if (StringUtil.isNullOrEmpty(this.roleMemberView) && !this.getRoles().isEmpty()) {
      List<Account> list = MembershipManagement.getInstance().getAccountService()
        .findAllByOrganizationUnitId(this.getId());

      for (Account item : list) {
        this.roleMemberView += (StringUtil.isNullOrEmpty(item.getGlobalName()) ?
          item.getName() : item.getGlobalName()) + ",";
      }

      this.roleMemberView = StringUtil.trimEnd(this.roleMemberView, ",");
    }

    return this.roleMemberView;
  }
}
