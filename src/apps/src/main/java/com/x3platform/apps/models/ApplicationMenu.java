package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.EntityClass;
import com.x3platform.apps.AppsContext;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;
/**
 * 应用菜单
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
  public final String getId() {
    return id;
  }

  public final void setId(String value) {
    id = value;
  }

  private Application application;

  /**
   * 应用
   */
  @JSONField(serialize = false)
  public final Application getApplication() {
/*    if (application == null && !StringUtil.isNullOrEmpty(this.getApplicationId())) {
      application = AppsContext.getInstance().getApplicationService().findOne(this.getApplicationId());
    }*/
    return application;
  }

  private String applicationId = "";

  /**
   */
  public final String getApplicationId() {
    return applicationId;
  }

  public final void setApplicationId(String value) {
    applicationId = value;
  }

  /**
   */
  public final String getApplicationName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationName();
  }

  /**
   */
  public final String getApplicationDisplayName() {
    return this.getApplication() == null ? "" : this.getApplication().getApplicationDisplayName();
  }

  // 父级菜单对象
  @JSONField(serialize = false)
  private ApplicationMenu parent;

  /**
   * 应用
   */
  public final ApplicationMenu getParent() {
/*
    if (UUIDUtil.emptyString().equals(this.getParentId())) {
      return null;
    }
*/

/*    if (parent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      // FIXME
       parent = AppsContext.getInstance().getApplicationMenuService().findOne(this.getParentId());
    }*/

    return parent;
  }

  private String parentId = UUIDUtil.emptyString();

  /**
   */
  public final String getParentId() {
    return parentId;
  }

  public final void setParentId(String value) {
    parentId = value;
  }


  private String parentName ;

  /**
   * 父级名称
   */
  public final String getParentName() {
    //return this.getParent() == null ? this.getApplicationDisplayName() : this.getParent().getName();
    return parentName;
  }

  private String code = "";

  /**
   */
  public final String getCode() {
    return code;
  }

  public final void setCode(String value) {
    code = value;
  }

  private String name = "";

  /**
   */
  public final String getName() {
    return name;
  }

  public final void setName(String value) {
    name = value;
  }

  private String description = "";

  /**
   */
  public final String getDescription() {
    return description;
  }

  public final void setDescription(String value) {
    description = value;
  }

  private String url = "";

  /**
   */
  public final String getUrl() {
    return url;
  }

  public final void setUrl(String value) {
    url = value;
  }

  // 显示方式
  private String target = "_self";

  /**
   */
  public final String getTarget() {
    return target;
  }

  public final void setTarget(String value) {
    target = value;
  }

  private String targetView = "";

  /**
   */
  public final String getTargetView() {
/*    if (StringUtil.isNullOrEmpty(targetView) && !StringUtil.isNullOrEmpty(this.getTarget())) {
      // FIXME 待处理
       //this.targetView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用链接打开方式", this.getTarget());
    }*/
    return targetView;
  }

  private String menuType = "ApplicationMenu";

  /**
   */
  public final String getMenuType() {
    return menuType;
  }

  public final void setMenuType(String value) {
    menuType = value;
  }

  private String menuTypeView = null;

  /**
   */
  public final String getMenuTypeView() {
  /*  if (StringUtil.isNullOrEmpty(menuTypeView) && !StringUtil.isNullOrEmpty(this.getMenuType())) {
      // FIXME 待处理
     // this.menuTypeView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用菜单类别", menuType);
    }
*/
    return menuTypeView;
  }

  private String iconPath = "";

  /**
   */
  public final String getIconPath() {
    return iconPath;
  }

  public final void setIconPath(String value) {
    iconPath = value;
  }
  ///#endregion

  ///#region 属性:BigIconPath
  private String bigIconPath = "";

  /**
   */
  public final String getBigIconPath() {
    return bigIconPath;
  }

  public final void setBigIconPath(String value) {
    bigIconPath = value;
  }
  ///#endregion

  ///#region 属性:DisplayType
  private String displayType = "";

  /**
   * 显示方式
   */
  public final String getDisplayType() {
    return displayType;
  }

  public final void setDisplayType(String value) {
    displayType = value;
  }

  private String displayTypeView = "";

  /**
   */
  public final String getDisplayTypeView() {
/*    if (StringUtil.isNullOrEmpty(displayTypeView) && !StringUtil.isNullOrEmpty(this.getDisplayType())) {
      // FIXME
       // this.displayTypeView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用菜单展现方式", this.getDisplayType());
    }*/
    return displayTypeView;
  }

  private int hasChild;

  /**
   */
  public final int getHasChild() {
    return hasChild;
  }

  public final void setHasChild(int value) {
    hasChild = value;
  }

  private String contextObject = "";

  /**
   * 上下文对象
   */
  public final String getContextObject() {
    return contextObject;
  }

  public final void setContextObject(String value) {
    contextObject = value;
  }

  private String orderId = "";

  /**
   */
  public final String getOrderId() {
    return orderId;
  }

  public final void setOrderId(String value) {
    orderId = value;
  }

  private int status;

  /**
   */
  public final int getStatus() {
    return status;
  }

  public final void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   */
  public final String getRemark() {
    return remark;
  }

  public final void setRemark(String value) {
    remark = value;
  }

  private String fullPath = "";

  /**
   */
  public final String getFullPath() {
    return fullPath;
  }

  public final void setFullPath(String value) {
    fullPath = value;
  }

  private java.time.LocalDateTime modifiedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return modifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    modifiedDate = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------





  /**
   * 权限：应用_通用_查看权限
   */
   // private Authority authorizationRead = AuthorityContext.Instance.AuthorityService["应用_通用_查看权限"];

  /**
   * 绑定查看权限
   *
   * @param scopeText
   */
  public final void BindAuthorizationReadScope(String scopeText) {

    // 清空缓存数据
    //this.authorizationReadScopeObjectText = null;
    //this.authorizationReadScopeObjectView = null;

    // TODO 待处理
    // if (this.mAuthorizationReadScopeObjects == null) {
    //   this.mAuthorizationReadScopeObjects = new ArrayList<MembershipAuthorizationScopeObject>();
    // }

    // MembershipAuthorizationScopeManagement.BindAuthorizationScopeObjects(this.mAuthorizationReadScopeObjects, scopeText);
  }

  // private List<MembershipAuthorizationScopeObject> authorizationReadScopeObjects = null;

  /**
   * 权限：应用_通用_查看权限范围
   */
  // public final List<MembershipAuthorizationScopeObject> getAuthorizationReadScopeObjects() {
  //   if (mAuthorizationReadScopeObjects == null) {
  //     authorizationReadScopeObjects = AppsContext.Instance.ApplicationMenuService.GetAuthorizationScopeObjects(this.getEntityId(), this.authorizationRead.Name);
  //
  //     // 设置默认权限是所有人
  //    if (mAuthorizationReadScopeObjects.isEmpty()) {
  //       AuthorizationObject authorizationObject = MembershipManagement.Instance.RoleService.GetEveryoneObject();

  //       authorizationReadScopeObjects.add(new MembershipAuthorizationScopeObject(authorizationObject.getAuthorizationObjectType(), authorizationObject.getAuthorizationObjectId(), authorizationObject.getAuthorizationObjectName()));
  //    }
  //  }

  //   return authorizationReadScopeObjects;
  // }

  private String authorizationReadScopeObjectText = null;

  /**
   * 权限：应用_通用_查看权限范围文本
   */
  public final String getAuthorizationReadScopeObjectText() {
/*    if (StringUtil.isNullOrEmpty(authorizationReadScopeObjectText)) {
      // FIXME 待处理
      //  authorizationReadScopeObjectText = MembershipAuthorizationScopeManagement.GetAuthorizationScopeObjectText(this.getAuthorizationReadScopeObjects());
    }*/
    return authorizationReadScopeObjectText;
  }

  public final void set(String value) {
    if (!StringUtil.isNullOrEmpty(value)) {
      BindAuthorizationReadScope(value);
    }
  }

  private String authorizationReadScopeObjectView = null;

  /**
   * 权限：应用_通用_查看权限范围视图
   */
  public final String getAuthorizationReadScopeObjectView() {

/*    if (StringUtil.isNullOrEmpty(authorizationReadScopeObjectText)) {
      List<ApplicationMenuScopeInfo> applicationMenuScopes = AppsContext.getInstance().getApplicationMenuService().getApplicationMenuScope(authorizationReadScopeObjectText);
      if(applicationMenuScopes!=null){

      }
    }*/
    return authorizationReadScopeObjectView;
  }

  public void setAuthorizationReadScopeObjectText(String authorizationReadScopeObjectText) {
    this.authorizationReadScopeObjectText = authorizationReadScopeObjectText;
  }

  public void setAuthorizationReadScopeObjectView(String authorizationReadScopeObjectView) {
    this.authorizationReadScopeObjectView = authorizationReadScopeObjectView;
  }

  ///#endregion

  // -------------------------------------------------------
  // 设置 EntityClass 标识
  // -------------------------------------------------------

  /**
   * 实体对象标识
   */
  @Override
  public String getEntityId() {
    return this.getId();
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
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   * @return
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
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    if (displayComment) {
      outString.append("<!-- 所属应用标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", this.getApplicationId()));
    if (displayComment) {
      outString.append("<!-- 所属父级菜单标识 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<parentId><![CDATA[%1$s]]></parentId>", this.getParentId()));
    if (displayComment) {
      outString.append("<!-- 应用菜单编码 (字符串) (nvarchar(36)) -->");
    }
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", this.getCode()));
    if (displayComment) {
      outString.append("<!-- 应用菜单名称 (字符串) (nvarchar(100)) -->");
    }
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", this.getName()));
    if (displayComment) {
      outString.append("<!-- 应用菜单描述 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<description><![CDATA[%1$s]]></description>", this.getDescription()));
    if (displayComment) {
      outString.append("<!-- 应用菜单地址 (字符串) (nvarchar(800)) -->");
    }
    outString.append(String.format("<url><![CDATA[%1$s]]></url>", this.getUrl()));
    if (displayComment) {
      outString.append("<!-- 应用菜单名称 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<target><![CDATA[%1$s]]></target>", this.getTarget()));
    if (displayComment) {
      outString.append("<!-- 应用菜单类型 (字符串) (nvarchar(50)) -->");
    }
    outString.append(String.format("<menuType><![CDATA[%1$s]]></menuType>", this.getMenuType()));
    if (displayComment) {
      outString.append("<!-- 图标路径 (字符串) (nvarchar(400)) -->");
    }
    outString.append(String.format("<iconPath><![CDATA[%1$s]]></iconPath>", this.getIconPath()));
    if (displayComment) {
      outString.append("<!-- 大图标路径 (字符串) (nvarchar(400)) -->");
    }
    outString.append(String.format("<bigIconPath><![CDATA[%1$s]]></bigIconPath>", this.getBigIconPath()));
    if (displayComment) {
      outString.append("<!-- 显示的类型 (字符串) (nvarchar(20)) -->");
    }
    outString.append(String.format("<displayType><![CDATA[%1$s]]></displayType>", this.getDisplayType()));
    if (displayComment) {
      outString.append("<!-- 排序 (字符串) (nvarchar(20)) -->");
    }
    outString.append(String.format("<orderId><![CDATA[%1$s]]></orderId>", this.getOrderId()));
    if (displayComment) {
      outString.append("<!-- 状态 (整型) (int) -->");
    }
    outString.append(String.format("<status><![CDATA[%1$s]]></status>", this.getStatus()));
    if (displayComment) {
      outString.append("<!-- 备注 (字符串) (nvarchar(200)) -->");
    }
    outString.append(String.format("<remark><![CDATA[%1$s]]></remark>", this.getRemark()));
    if (displayComment) {
      outString.append("<!-- 授权对象列表 -->");
    }
    // FIXME 待处理
    outString.append("<authorizationObjects>");
    // for (MembershipAuthorizationScopeObject authorizationScopeObject : this.getAuthorizationReadScopeObjects()) {
    //  outString.append(String.format("<authorizationObject id=\"%1$s\" type=\"%2$s\" authority=\"应用_通用_查看权限\" />", authorizationScopeObject.AuthorizationObjectId, authorizationScopeObject.AuthorizationObjectType));
    // }
    outString.append("</authorizationObjects>");
    if (displayComment) {
      outString.append("<!-- 最后更新时间 (时间) (datetime) -->");
    }
    outString.append(String.format("<updateDate><![CDATA[%1$s]]></updateDate>", StringUtil.toDateFormat(this.getModifiedDate(), "yyyy-MM-dd HH:mm:ss")));
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
    this.setId(element.selectSingleNode("id").getText());
    this.setApplicationId(element.selectSingleNode("applicationId").getText());
    this.setParentId(element.selectSingleNode("parentId").getText());
    this.setCode(element.selectSingleNode("code").getText());
    this.setName(element.selectSingleNode("name").getText());
    this.setDescription(element.selectSingleNode("description").getText());
    this.setUrl(element.selectSingleNode("url").getText());
    this.setTarget(element.selectSingleNode("target").getText());
    this.setMenuType(element.selectSingleNode("menuType").getText());
    this.setIconPath(element.selectSingleNode("iconPath").getText());
    this.setBigIconPath(element.selectSingleNode("bigIconPath").getText());
    this.setDisplayType(element.selectSingleNode("displayType").getText());
    this.setOrderId(element.selectSingleNode("orderId").getText());
    this.setStatus(Integer.parseInt(element.selectSingleNode("status").getText()));
    this.setRemark(element.selectSingleNode("remark").getText());

    // 设置可访问成员信息
    // FIXME 待处理
    // XmlNodeList list = element.SelectNodes("authorizationObjects/authorizationObject[@authority='应用_通用_查看权限']");

    // this.mAuthorizationReadScopeObjects = new ArrayList<MembershipAuthorizationScopeObject>();

    // for (XmlNode node : list) {
    //  this.getAuthorizationReadScopeObjects().add(new MembershipAuthorizationScopeObject(node.Attributes["type"].Value, node.Attributes["id"].Value));
    // }

    this.setModifiedDate(java.time.LocalDateTime.parse(element.selectSingleNode("updateDate").getText()));
  }

  /**
   * 树形的节点数据
   */
  public List<Object> childNodes = new ArrayList<>();

  public List<Object> getChildNodes() {
    return childNodes;
  }

  public void setChildNodes(List<Object> childNodes) {
    this.childNodes = childNodes;
  }
}
