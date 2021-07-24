package com.x3platform.membership.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.AuthorizationObject;
import com.x3platform.AuthorizationScope;
import com.x3platform.membership.Account;
import com.x3platform.membership.AccountGroupRelation;
import com.x3platform.membership.AccountOrganizationUnitRelation;
import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * 帐户信息
 *
 * @author ruanyu
 */
public class AccountInfo implements Account {

  /**
   *
   */
  public AccountInfo() {

  }

  /**
   * 属性, 可存放扩展属性, 临时属性
   *
   * 可添加的属性 例如: OriginalPassword, OriginalNickName
   */
  @JSONField(serialize = false)
  private HashMap<String, Object> properties = new HashMap<String, Object>();

  /**
   * 属性
   */
  public HashMap<String, Object> getProperties() {
    return properties;
  }

  private String id = "";
  private String code = "";

  /**
   * 具体属性
   */
  private String name = "";
  private String globalName = "";
  private String displayName = "";
  private String pinyin = "";
  private String loginName = "";
  @JSONField(serialize = false)
  private String password = "";
  private Date passwordChangedDate = DateUtil.getDefaultDate();
  private String identityCard = "";
  private int type = 0;
  private String typeView;
  private String certifiedMobile = "";
  private String certifiedEmail = "";

  private String certifiedAvatar = "";
  private int enableEmail;
  private AuthorizationObject parent;

  private List<AuthorizationScope> scopes = new ArrayList<AuthorizationScope>();
  private int locking = 1;
  private String orderId = "";
  private int status;
  private String remark = "";
  private String ip = "";
  private Date loginDate = DateUtil.getDefaultDate();
  private String distinguishedName = "";

  private Date expires = new Date((new Date()).getTime() + 6 * 60 * 60 * 1000L);

  /**
   * 用户标识
   */
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String value) {
    id = value;
  }

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

  /**
   * 全局名称
   */
  @Override
  public String getGlobalName() {
    if (StringUtil.isNullOrEmpty(globalName)) {
      globalName = getDisplayName();
    }
    return globalName;
  }

  public void setGlobalName(String value) {
    globalName = value;
  }

  /**
   * 显示名称
   */
  @Override
  public String getDisplayName() {
    if (StringUtil.isNullOrEmpty(displayName)) {
      displayName = getName();
    }

    return displayName;
  }

  @Override
  public void setDisplayName(String value) {
    displayName = value;
  }

  /**
   * 拼音
   */
  @Override
  public String getPinYin() {
    return pinyin;
  }

  @Override
  public void setPinYin(String value) {
    pinyin = value;
  }

  /**
   * 登录名
   */
  @Override
  public String getLoginName() {
    return loginName;
  }

  @Override
  public void setLoginName(String value) {
    loginName = value;
  }

  /**
   * 密码
   */
  public String getPassword() {
    return password;
  }

  public void setPassword(String value) {
    password = value;
  }

  private String passwordSalt = "";

  /**
   * 密码盐值
   */
  @Override
  public String getPasswordSalt() {
    return passwordSalt;
  }

  @Override
  public void setPasswordSalt(String value) {
    passwordSalt = value;
  }

  /**
   * 密码更新时间
   */
  @Override
  public Date getPasswordChangedDate() {
    return passwordChangedDate;
  }

  @Override
  public void setPasswordChangedDate(Date value) {
    passwordChangedDate = value;
  }

  /**
   * 身份证
   */
  @Override
  public String getIdentityCard() {
    return identityCard;
  }

  @Override
  public void setIdentityCard(String value) {
    identityCard = value;
  }

  /**
   * 帐号类型 0:普通帐号 1:邮箱帐号 2:Rtx帐号 3:CRM帐号 1000:供应商帐号 2000:客户帐号
   */
  @Override
  public int getType() {
    return type;
  }

  @Override
  public void setType(int value) {
    type = value;
  }

  /**
   * 帐号类别视图 0:普通帐号 1:邮箱帐号 2:CRM帐号 3:RTX帐号 1000:供应商帐号 2000:客户帐号
   */
  public String getTypeView() {
    if (StringUtil.isNullOrEmpty(typeView)) {
      typeView = MembershipManagement.getInstance().getSettingService()
        .getText("应用管理_基础支撑平台_人员及权限管理_帐号管理_帐号类别", String.valueOf(getType()));
    }

    return typeView;
  }

  /**
   * 已验证的电话
   */

  @Override
  public String getCertifiedMobile() {
    return certifiedMobile;
  }

  @Override
  public void setCertifiedMobile(String value) {
    certifiedMobile = value;
  }

  /**
   * 已验证的邮箱
   */

  @Override
  public String getCertifiedEmail() {
    return certifiedEmail;
  }

  @Override
  public void setCertifiedEmail(String value) {
    certifiedEmail = value;
  }

  /**
   * 已验证的头像
   */
  @Override
  public String getCertifiedAvatar() {
    return certifiedAvatar;
  }

  @Override
  public void setCertifiedAvatar(String value) {
    certifiedAvatar = value;
  }

  /**
   * 已验证的头像虚拟路径
   */
  @Override
  public String getCertifiedAvatarView() {
    return getCertifiedAvatar().replace("{avatar}", MembershipConfigurationView.getInstance().getAvatarVirtualFolder());
  }

  /**
   * 启用企业邮箱
   */
  @Override
  public int getEnableEmail() {
    return enableEmail;
  }

  @Override
  public void setEnableEmail(int value) {
    enableEmail = value;
  }

  /**
   * 范围接口集合
   */
  public List<AuthorizationScope> getScopes() {
    return scopes;
  }

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

  /**
   *
   */
  @Override
  public String getOrderId() {
    return orderId;
  }

  @Override
  public void setOrderId(String value) {
    orderId = value;
  }

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

  /**
   * IP
   */
  @Override
  public String getIP() {
    return ip;
  }

  @Override
  public void setIP(String value) {
    ip = value;
  }

  /**
   * 最近一次的登录时间
   */
  @Override
  public Date getLoginDate() {
    return loginDate;
  }

  @Override
  public void setLoginDate(Date value) {
    loginDate = value;
  }

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
  @Override
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 关系管理
  // -------------------------------------------------------

  /**
   * 父级权限对象接口集合
   */
  public AuthorizationObject getParent() {
    return parent;
  }

  public void setParent(AuthorizationObject value) {
    parent = value;
  }

  private List<AccountOrganizationUnitRelation> organizationUnitRelations = new ArrayList<AccountOrganizationUnitRelation>();

  private String organizationUnitIds = "";

  /**
   * 帐号和组织关系集合
   */
  @Override
  public List<AccountOrganizationUnitRelation> getOrganizationUnitRelations() {
    if (organizationUnitRelations.isEmpty() && !StringUtil.isNullOrEmpty(getId())) {
      organizationUnitRelations = MembershipManagement.getInstance().getOrganizationUnitService()
        .findAllRelationByAccountId(getId());
    }

    return organizationUnitRelations;
  }

  /**
   * 所属组织信息
   */
  private String organizationUnitText = "";

  public String getOrganizationUnitText() {
    if (StringUtil.isNullOrEmpty(organizationUnitText) && !getOrganizationUnitRelations().isEmpty()) {
      for (AccountOrganizationUnitRelation relation : getOrganizationUnitRelations()) {
        organizationUnitText += StringUtil.format("organizationUnit#{}#{}#{}#{},",
          relation.getOrganizationUnitId(),
          relation.getOrganizationUnitGlobalName(),
          StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss"),
          relation.getIsDefault());
      }
      organizationUnitText = organizationUnitText.length() > 0 ? organizationUnitText = organizationUnitText
        .substring(0, organizationUnitText.length() - 1) : organizationUnitText;
    }

    return organizationUnitText;
  }

  private String organizationUnitView = "";

  public String getOrganizationUnitView() {
    if (StringUtil.isNullOrEmpty(organizationUnitView) && getOrganizationUnitRelations().size() > 0) {
      for (AccountOrganizationUnitRelation relation : getOrganizationUnitRelations()) {
        organizationUnitView += relation.getOrganizationUnitGlobalName() + ",";
      }
      organizationUnitView = organizationUnitView.length() > 0 ? organizationUnitView = organizationUnitView
        .substring(0, organizationUnitView.length() - 1) : organizationUnitView;
    }

    return organizationUnitView;
  }

  public void setOrganizationUnitView(String organizationUnitView) {
    this.organizationUnitView = organizationUnitView;
  }

  private List<AccountRoleRelation> roleRelations = new ArrayList<AccountRoleRelation>();

  /**
   * 帐号和角色关系集合
   */
  @Override
  public List<AccountRoleRelation> getRoleRelations() {
    if (roleRelations.isEmpty() && !StringUtil.isNullOrEmpty(getId())) {
      roleRelations = MembershipManagement.getInstance().getRoleService()
        .findAllRelationByAccountId(getId());
    }

    return roleRelations;
  }

  /**
   * 所属角色信息
   */
  private String roleText = "";

  public String getRoleText() {
    if (StringUtil.isNullOrEmpty(roleText) && getRoleRelations().size() > 0) {
      for (AccountRoleRelation relation : getRoleRelations()) {
        roleText += StringUtil.format("role#{}#{}#{}#{},", relation.getRoleId(),
          relation.getRoleGlobalName(),
          StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss"),
          relation.getIsDefault());
      }
      roleText = roleText.length() > 0 ? roleText = roleText.substring(0, roleText.length() - 1) : roleText;
    }

    return roleText;
  }

  /**
   * 所属角色名称
   */
  private String roleView = "";

  /**
   * 所属角色视图
   */
  public String getRoleView() {
    if (StringUtil.isNullOrEmpty(roleView) && getRoleRelations().size() > 0) {
      for (AccountRoleRelation relation : getRoleRelations()) {
        roleView += relation.getRoleGlobalName() + ",";
      }
      roleView = roleView.length() > 0 ? roleView = roleView.substring(0, roleView.length() - 1) : roleView;
    }
    return roleView;
  }

  private List<AccountGroupRelation> groupRelations = new ArrayList<AccountGroupRelation>();

  /**
   * 帐号和群组关系集合
   */
  @Override
  public List<AccountGroupRelation> getGroupRelations() {
    if (groupRelations.isEmpty() && !StringUtil.isNullOrEmpty(getId())) {
      groupRelations = MembershipManagement.getInstance().getGroupService()
        .findAllRelationByAccountId(getId());
    }

    return groupRelations;
  }

  private String groupText = "";

  /**
   * 所属群组文本信息
   */
  public String getGroupText() {
    if (StringUtil.isNullOrEmpty(groupText) && getGroupRelations().size() > 0) {
      for (AccountGroupRelation relation : getGroupRelations()) {
        groupText += StringUtil.format("group#{}#{}#{},", relation.getGroupId(),
          relation.getGroupGlobalName(),
          StringUtil.toDateFormat(relation.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
      }
      groupText = groupText.length() > 0 ? groupText = groupText.substring(0, groupText.length() - 1) : groupText;
    }
    return groupText;
  }

  private String groupView = "";

  /**
   * 所属群组视图
   */
  public String getGroupView() {
    if (StringUtil.isNullOrEmpty(groupView) && getGroupRelations().size() > 0) {
      for (AccountGroupRelation relation : getGroupRelations()) {
        groupView += relation.getGroupGlobalName() + ",";
      }
      groupView = groupView.length() > 0 ? groupView = groupView.substring(0, groupView.length() - 1) : groupView;
    }

    return groupView;
  }

  /**
   * 重置关系
   *
   * @param relationType 关系类型
   * @param relationText 文本格式的关系数据
   */
  @Override
  public void resetRelations(String relationType, String relationText) {
    String[] list = StringUtil.isNullOrEmpty(relationText) ? new String[0] : relationText.split(",|;");

    if (relationType.equals("organizationUnit")) {
      getOrganizationUnitRelations().clear();
    }

    if (relationType.equals("role")) {
      getRoleRelations().clear();
    }

    if (relationType.equals("group")) {
      getGroupRelations().clear();
    }

    // 设置组织关系

    for (String item : list) {
      String[] keys = item.split("#");

      if (keys.length > 2) {
        switch (keys[0]) {
          case "organizationUnit":
            if (relationType.equals("organizationUnit")) {
              getOrganizationUnitRelations().add(new AccountOrganizationUnitRelationInfo(getId(), keys[1]));
            }
            break;
          case "role":
            if (relationType.equals("role")) {
              getRoleRelations().add(new AccountRoleRelationInfo(getId(), keys[1]));
            }
            break;
          case "group":
            if (relationType.equals("group")) {
              getGroupRelations().add(new AccountGroupRelationInfo(getId(), keys[1]));
            }
            break;
          default:
            break;
        }
      }
    }
  }

  // -------------------------------------------------------
  // 实现 IIdentity 接口
  // -------------------------------------------------------

  /**
   *
   */
  public String getAuthenticationType() {
    return "MembershipAuthentication";
  }

  // -------------------------------------------------------
  // 显式实现 ICacheable 接口
  // -------------------------------------------------------

  /**
   *
   */
  public boolean getIsAuthenticated() {
    return getId() != "00000000-0000-0000-0000-000000000000";
  }

  private Date getExpires() {
    return expires;
  }
  
  public String getOrganizationUnitIds() {
    return organizationUnitIds;
  }

  public void setOrganizationUnitIds(String organizationUnitIds) {
    this.organizationUnitIds = organizationUnitIds;
  }

  private void setExpires(Date value) {
    expires = value;
  }

  // -------------------------------------------------------
  // 实现 AuthorizationObject 接口
  // -------------------------------------------------------

  /**
   * 类型
   */
  @Override
  public String getAuthorizationObjectType() {
    return "Account";
  }

  /**
   * 名称
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
   * 名称
   */
  @Override
  public String getAuthorizationObjectName() {
    return String.format("%1$s", getName(), getLoginName());
  }

  @Override
  public void setAuthorizationObjectName(String value) {
    int index = value.indexOf("(");
    if (index > -1) {
      setName(value.substring(0, index));
      setLoginName(value.substring((index + 1), (index + 1) + (value.length() - index - 2)));
    }
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
   * @param displayComment 显示注释
   * @param displayFriendlyName 显示友好名称
   */
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    outString.append("<account>");
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", getId()));
    /// 临时移除
    // outString.append(String.format("<roleId><![CDATA[%1$s]]></roleId>", this.getRoleText()));
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", getCode()));
    outString.append(String.format("<loginName><![CDATA[%1$s]]></loginName>", getLoginName()));
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", getName()));
    outString.append(String.format("<globalName><![CDATA[%1$s]]></globalName>", getGlobalName()));
    outString.append(String.format("<alias><![CDATA[%1$s]]></alias>", getDisplayName()));
    outString.append(String.format("<pinyin><![CDATA[%1$s]]></pinyin>", getPinYin()));
    outString.append(String.format("<orderId><![CDATA[%1$s]]></orderId>", getOrderId()));
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", getStatus()));
    outString.append(String.format("<remark><![CDATA[%1$s]]></remark>", getRemark()));
    outString.append(String.format("<modifiedDate><![CDATA[%1$s]]></modifiedDate>", getModifiedDate()));
    outString.append("</account>");

    return outString.toString();
  }

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  @Override
  public void deserialize(Element element) {
    Node node = null;
    setId(element.selectSingleNode("id").getText());
    setPinYin(element.selectSingleNode("roleId").getText());
    setCode(element.selectSingleNode("code").getText());
    node = element.selectSingleNode("loginName");
    if (node != null) {
      setLoginName(node.getText());
    }

    setName(element.selectSingleNode("name").getText());

    node = element.selectSingleNode("globalName");
    if (node != null) {
      setGlobalName(node.getText());
    }

    node = element.selectSingleNode("alias");
    if (node != null) {
      setDisplayName(node.getText());
    }

    node = element.selectSingleNode("pinyin");
    if (node != null) {
      setPinYin(node.getText());
    }

    // 关系列表
    List list = element.selectNodes("relationObjects/relationObject");

    // 由于设置了Id
    setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
  }
}
