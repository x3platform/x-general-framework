package com.x3platform.apps.models;

import static com.x3platform.apps.Constants.APPLICATION_MENU_ROOT_ID;
import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.AuthorizationObjectType;
import com.x3platform.AuthorizationScope;
import com.x3platform.AuthorizationScopeManagement;
import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.security.authority.Authority;
import com.x3platform.security.authority.AuthorityContext;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;

/**
 * 应用菜单
 *
 * @author ruanyu
 */
public class ApplicationMenu extends EntityClass {

  /**
   * 默认构造函数
   */
  public ApplicationMenu() {
  }

  private String id = "";

  /**
   *
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  @JSONField(serialize = false)
  private Application application;

  /**
   * 所属应用
   */
  public Application getApplication() {
    if (application == null && !StringUtil.isNullOrEmpty(getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(getApplicationId());
    }
    return application;
  }

  private String applicationId = "";

  /**
   * 所属应用标识
   */
  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String value) {
    applicationId = value;
  }

  /**
   * 所属应用名称
   */
  public String getApplicationName() {
    return getApplication() == null ? "" : getApplication().getApplicationName();
  }

  /**
   * 所属应用显示名称
   */
  public String getApplicationDisplayName() {
    return getApplication() == null ? "" : getApplication().getApplicationDisplayName();
  }

  /**
   * 父级对象
   */
  @JSONField(serialize = false)
  private ApplicationMenu parent = null;

  /**
   * 父级对象
   */
  public ApplicationMenu getParent() {
    if (APPLICATION_MENU_ROOT_ID.equals(getParentId())) {
      return null;
    }

    if (parent == null && !StringUtil.isNullOrEmpty(getParentId())) {
      parent = AppsContext.getInstance().getApplicationMenuService().findOne(getParentId());
    }

    return parent;
  }

  private String parentId = UUIDUtil.emptyString();

  /**
   * 父级对象标识
   */
  public String getParentId() {
    return parentId;
  }

  public void setParentId(String value) {
    parentId = value;
  }

  /**
   * 父级对象名称
   */
  public String getParentName() {
    return getParent() == null ? getApplicationDisplayName() : getParent().getName();
  }

  private String code = "";

  /**
   */
  public String getCode() {
    return code;
  }

  public void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   */
  public String getName() {
    return name;
  }

  public void setName(String value) {
    name = value;
  }

  private String description = "";

  /**
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    description = value;
  }

  private String url = "";

  /**
   */
  public String getUrl() {
    return url;
  }

  public void setUrl(String value) {
    url = value;
  }

  // 显示方式
  private String target = "_self";

  /**
   */
  public String getTarget() {
    return target;
  }

  public void setTarget(String value) {
    target = value;
  }

  private String targetView = "";

  /**
   */
  public String getTargetView() {
    if (StringUtil.isNullOrEmpty(targetView) && !StringUtil.isNullOrEmpty(getTarget())) {
      targetView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用链接打开方式", getTarget());
    }
    return targetView;
  }

  private String menuType = "ApplicationMenu";

  /**
   */
  public String getMenuType() {
    return menuType;
  }

  public void setMenuType(String value) {
    menuType = value;
  }

  private String menuTypeView = null;

  /**
   */
  public String getMenuTypeView() {
    if (StringUtil.isNullOrEmpty(menuTypeView) && !StringUtil.isNullOrEmpty(getMenuType())) {
      menuTypeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用菜单类别", menuType);
    }

    return menuTypeView;
  }

  private String iconPath = "";

  /**
   */
  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String value) {
    iconPath = value;
  }

  private String bigIconPath = "";

  /**
   */
  public String getBigIconPath() {
    return bigIconPath;
  }

  public void setBigIconPath(String value) {
    bigIconPath = value;
  }

  private String displayType = "";

  /**
   * 显示方式
   */
  public String getDisplayType() {
    return displayType;
  }

  public void setDisplayType(String value) {
    displayType = value;
  }

  private String displayTypeView = "";

  /**
   * 显示方式视图
   */
  public String getDisplayTypeView() {
    if (StringUtil.isNullOrEmpty(displayTypeView) && !StringUtil.isNullOrEmpty(getDisplayType())) {
      displayTypeView = AppsContext.getInstance().getApplicationSettingService().getText(
        AppsContext.getInstance().getApplicationService().findOneByApplicationName(APPLICATION_NAME).getId(),
        "应用管理_应用菜单展现方式", getDisplayType());
    }
    return displayTypeView;
  }

  private int hasChild;

  /**
   */
  public int getHasChild() {
    return hasChild;
  }

  public void setHasChild(int value) {
    hasChild = value;
  }

  private String contextObject = "";

  /**
   * 上下文对象
   */
  public String getContextObject() {
    return contextObject;
  }

  public void setContextObject(String value) {
    contextObject = value;
  }

  private String orderId = "";

  /**
   */
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String value) {
    orderId = value;
  }

  private int status;

  /**
   */
  public int getStatus() {
    return status;
  }

  public void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   */
  public String getRemark() {
    return remark;
  }

  public void setRemark(String value) {
    remark = value;
  }

  private String fullPath = "";

  /**
   */
  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String value) {
    fullPath = value;
  }

  private LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------

  /**
   * 权限：应用_通用_查看权限
   */
  private Authority authorizationRead = AuthorityContext.getInstance()
    .getAuthorityService().findOneByName("应用_通用_查看权限");

  /**
   * 绑定查看权限
   */
  public void bindAuthorizationReadScope(String scopeText) {

    // 清空缓存数据
    authorizationReadScopeText = null;
    authorizationReadScopeView = null;

    if (authorizationReadScopes == null) {
      authorizationReadScopes = new ArrayList<AuthorizationScope>();
    }

    AuthorizationScopeManagement.bindAuthorizationScopes(authorizationReadScopes, scopeText);
  }

  private List<AuthorizationScope> authorizationReadScopes = null;

  /**
   * 权限：应用_通用_查看权限范围
   */
  @JSONField(serialize = false)
  public List<AuthorizationScope> getAuthorizationReadScopes() {
    if (authorizationReadScopes == null) {
      authorizationReadScopes = AppsContext.getInstance().getApplicationMenuService()
        .getAuthorizationScopes(getEntityId(), authorizationRead.getName());

      // 设置默认权限是所有人
      if (authorizationReadScopes.isEmpty()) {
        authorizationReadScopes.add(new AuthorizationScope(AuthorizationObjectType.ROLE.getValue(),
          "00000000-0000-0000-0000-000000000000", "所有人"));
      }
    }

    return authorizationReadScopes;
  }

  private String authorizationReadScopeText = null;

  /**
   * 权限：应用_通用_查看权限范围文本
   */
  public String getAuthorizationReadScopeText() {
    if (authorizationReadScopeText == null) {
      authorizationReadScopeText = AuthorizationScopeManagement
        .getAuthorizationScopeText(getAuthorizationReadScopes());
    }
    return authorizationReadScopeText;
  }

  private String authorizationReadScopeView = null;

  /**
   * 权限：应用_通用_查看权限范围视图
   */
  public String getAuthorizationReadScopeView() {

    if (authorizationReadScopeView == null) {
      authorizationReadScopeView = AuthorizationScopeManagement
        .getAuthorizationScopeView(getAuthorizationReadScopes());
    }
    return authorizationReadScopeView;
  }

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  /**
   * 实体对象标识
   */
  @Override
  public String getEntityId() {
    return getId();
  }

  // -------------------------------------------------------
  // 实现 EntityClass 序列化
  // -------------------------------------------------------

  /**
   * 序列化对象
   */
  @Override
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 序列化对象
   *
   * @param displayComment 显示注释
   * @param displayFriendlyName 显示友好名称
   */
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();
    if (displayComment) {
      outString.append("<!-- 应用菜单对象 -->");
    }
    outString.append("<menu>");
    if (displayComment) {
      outString.append("<!-- 应用菜单标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", getId()));
    if (displayComment) {
      outString.append("<!-- 所属应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", getApplicationId()));
    if (displayComment) {
      outString.append("<!-- 所属父级菜单标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", getParentId()));
    if (displayComment) {
      outString.append("<!-- 应用菜单编码 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", getCode()));
    if (displayComment) {
      outString.append("<!-- 应用菜单名称 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", getName()));
    if (displayComment) {
      outString.append("<!-- 应用菜单描述 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<description><![CDATA[%1$s]]></description>", getDescription()));
    if (displayComment) {
      outString.append("<!-- 应用菜单地址 (字符串) (nvarchar(800)) -->");
    }
    outString.append(String.format("<url><![CDATA[%1$s]]></url>", getUrl()));
    if (displayComment) {
      outString.append("<!-- 应用菜单名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<target><![CDATA[%1$s]]></target>", getTarget()));
    if (displayComment) {
      outString.append("<!-- 应用菜单类型 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<menuType><![CDATA[%1$s]]></menuType>", getMenuType()));
    if (displayComment) {
      outString.append("<!-- 图标路径 (字符串) (nvarchar(400)) -->");
    }
    outString.append(String.format("<iconPath><![CDATA[%1$s]]></iconPath>", getIconPath()));
    if (displayComment) {
      outString.append("<!-- 大图标路径 (字符串) (nvarchar(400)) -->");
    }
    outString.append(String.format("<bigIconPath><![CDATA[%1$s]]></bigIconPath>", getBigIconPath()));
    if (displayComment) {
      outString.append("<!-- 显示的类型 (字符串) (nvarchar(20)) -->");
    }
    outString.append(String.format("<displayType><![CDATA[%1$s]]></displayType>", getDisplayType()));
    if (displayComment) {
      outString.append("<!-- 排序 (字符串) (nvarchar(20)) -->");
    }
    outString.append(String.format("<orderId><![CDATA[%1$s]]></orderId>", getOrderId()));
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", getStatus()));
    if (displayComment) {
      outString.append("<!-- 备注 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<remark><![CDATA[%1$s]]></remark>", getRemark()));
    if (displayComment) {
      outString.append("<!-- 授权对象列表 -->");
    }
    // FIXME 待处理
    outString.append("<authorizationObjects>");
    // for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getAuthorizationReadScopes()) {
    //  outString.append(String.format("<authorizationObject id=\"%1$s\" type=\"%2$s\" authority=\"应用_通用_查看权限\" />", authorizationScopeObject.AuthorizationObjectId, authorizationScopeObject.AuthorizationObjectType));
    // }
    outString.append("</authorizationObjects>");
    if (displayComment) {
      outString.append("<!-- 最后更新时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<updateDate><![CDATA[%1$s]]></updateDate>",
      StringUtil.toDateFormat(getModifiedDate(), "yyyy-MM-dd HH:mm:ss")));
    outString.append("</menu>");

    return outString.toString();
  }

  /**
   * 反序列化对象
   *
   * @param element Xml元素
   */
  @Override
  public void deserialize(Element element) {
    setId(element.selectSingleNode("id").getText());
    setApplicationId(element.selectSingleNode("applicationId").getText());
    setParentId(element.selectSingleNode("parentId").getText());
    setCode(element.selectSingleNode("code").getText());
    setName(element.selectSingleNode("name").getText());
    setDescription(element.selectSingleNode("description").getText());
    setUrl(element.selectSingleNode("url").getText());
    setTarget(element.selectSingleNode("target").getText());
    setMenuType(element.selectSingleNode("menuType").getText());
    setIconPath(element.selectSingleNode("iconPath").getText());
    setBigIconPath(element.selectSingleNode("bigIconPath").getText());
    setDisplayType(element.selectSingleNode("displayType").getText());
    setOrderId(element.selectSingleNode("orderId").getText());
    setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    setRemark(element.selectSingleNode("remark").getText());

    // 设置可访问成员信息
    // FIXME 待处理
    // XmlNodeList list = element.SelectNodes("authorizationObjects/authorizationObject[@authority='应用_通用_查看权限']");

    // this.mAuthorizationReadScopes = new ArrayList<MembershipAuthorizationScopeObject>();

    // for (XmlNode node : list) {
    //  this.getAuthorizationReadScopes().add(new MembershipAuthorizationScopeObject(node.Attributes["type"].Value, node.Attributes["id"].Value));
    // }

    setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("updateDate").getText()));
  }

  /**
   * 树形的节点数据
   */
//  public List<Object> childNodes = new ArrayList<>();
//
//  public List<Object> getChildNodes() {
//    return childNodes;
//  }
//
//  public void setChildNodes(List<Object> childNodes) {
//    this.childNodes = childNodes;
//  }
}
