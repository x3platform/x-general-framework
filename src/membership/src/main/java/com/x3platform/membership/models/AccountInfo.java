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
  private String password;
  private Date passwordChangedDate = DateUtil.getDefaultDate();
  private String identityCard = "";
  private int type = 0;
  private String typeView;
  private String certifiedMobile;
  private String certifiedEmail;

  private String certifiedAvatar = "";
  private int enableEmail;
  private AuthorizationObject parent;

  private List<AccountOrganizationUnitRelation> organizationUnitRelations = null;
  private String organizationUnitIds = "";

  /**
   * 所属组织信息
   */
  private String[] organizationUnitText;
  private String organizationUnitView;

  private List<AccountRoleRelation> roleRelations = null;

  /**
   * 所属角色信息
   */
  private String[] roleText;
  /**
   * 所属角色名称
   */
  private String roleView = "";

  private List<AccountGroupRelation> groupRelations = null;
  private String groupText;

  private String groupView;
  private List<AuthorizationScope> scopes = new ArrayList<AuthorizationScope>();
  private boolean isDraft;
  private int locking = 1;
  private String orderId;
  private int status;
  private String remark;
  private String ip;
  private String createdBy;
  private Date loginDate = DateUtil.getDefaultDate();
  private String distinguishedName = null;
  private Date modifiedDate = DateUtil.getDefaultDate();
  private Date createdDate = DateUtil.getDefaultDate();
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

  @Override
  public String getCreatedBy() {
    return createdBy;
  }

  @Override
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
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
  @JSONField(serialize = false)
  public String getTypeView() {
    if (StringUtil.isNullOrEmpty(typeView)) {
      typeView = MembershipManagement.getInstance().getSettingService()
        .getText("应用管理_协同平台_人员及权限管理_帐号管理_帐号类别", String.valueOf(
          getType()));
    }

    return typeView;
  }
  ///#endregion

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
   * 父级权限对象接口集合
   */
  public AuthorizationObject getParent() {
    return parent;
  }

  public void setParent(AuthorizationObject value) {
    parent = value;
  }

  /**
   * 帐号和角色接口集合
   */
  @Override
  public List<AccountOrganizationUnitRelation> getOrganizationUnitRelations() {
    return organizationUnitRelations;
  }

  public String getOrganizationUnitView() {
    return organizationUnitView;
  }

  public void setOrganizationUnitView(String organizationUnitView) {
    this.organizationUnitView = organizationUnitView;
  }

  /**
   * 帐号和角色接口集合
   */
  @Override
  public List<AccountRoleRelation> getRoleRelations() {
    return roleRelations;
  }


  /**
   * 所属角色视图
   */
  public String getRoleView() {
    return roleView;
  }

  /**
   * 帐号和角色接口集合
   */
  @Override
  public List<AccountGroupRelation> getGroupRelations() {
    return groupRelations;
  }

  /**
   * 所属群组文本信息
   */
  public String getGroupText() {
    return groupText;
  }

  /**
   * 所属群组视图
   */
  public String getGroupView() {
    return groupView;
  }

  /**
   * 范围接口集合
   */
  public List<AuthorizationScope> getScopes() {
    return scopes;
  }

  /**
   * 是否是草稿
   */
  public boolean getIsDraft() {
    return isDraft;
  }

  public void setIsDraft(boolean value) {
    isDraft = value;
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

  /**
   * 修改时间
   */
  @Override
  public Date getModifiedDate() {
    return modifiedDate;
  }

  @Override
  public void setModifiedDate(Date value) {
    modifiedDate = value;
  }


  /**
   * 创建时间
   */
  @Override
  public Date getCreatedDate() {
    return createdDate;
  }

  // -------------------------------------------------------
  // 重置关系
  // -------------------------------------------------------

  @Override
  public void setCreatedDate(Date value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 实现 IIdentity 接口
  // -------------------------------------------------------

  ///#region 属性:AuthenticationType

  /**
   * @param relationType
   * @param relationText
   */
  public void resetRelations(String relationType, String relationText) {
  }

  /**
   *
   */
  public String getAuthenticationType() {
    return "MembershipAuthentication";
  }
  ///#endregion

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


  public void setRoleView(String roleView) {
    this.roleView = roleView;
  }

  public String[] getOrganizationUnitText() {
    return organizationUnitText;
  }

  public void setOrganizationUnitText(String[] organizationUnitText) {
    this.organizationUnitText = organizationUnitText;
  }

  public String[] getRoleText() {
    return roleText;
  }

  public void setRoleText(String[] roleText) {
    this.roleText = roleText;
  }

  // -------------------------------------------------------
  // 实现 SerializedObject 序列化
  // -------------------------------------------------------

  /**
   * 根据对象导出Xml元素
   *
   * @return
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
   * @return
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
