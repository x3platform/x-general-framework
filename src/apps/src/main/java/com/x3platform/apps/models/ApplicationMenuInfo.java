package com.x3platform.apps.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.EntityClass;
import com.x3platform.IAuthorizationObject;
import com.x3platform.apps.AppsContext;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.security.authority.AuthorityInfo;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.dom4j.Element;

import java.util.*;

/**
 * 应用菜单
 */
public class ApplicationMenuInfo extends EntityClass {

  /**
   * 默认构造函数
   */
  public ApplicationMenuInfo() {
  }

  private String mId = "";

  /**
   *
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }

  private ApplicationInfo mApplication;

  /**
   * 应用
   */
  @JSONField(serialize = false)
  public final ApplicationInfo getApplication() {
    if (mApplication == null && !StringUtil.isNullOrEmpty(this.getApplicationId())) {
      mApplication = AppsContext.getInstance().getApplicationService().findOne(this.getApplicationId());
    }

    return mApplication;
  }

  private String mApplicationId = "";

  /**
   */
  public final String getApplicationId() {
    return mApplicationId;
  }

  public final void setApplicationId(String value) {
    mApplicationId = value;
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

  @JSONField(serialize = false)
  private ApplicationMenuInfo mParent;

  /**
   * 应用
   */
  public final ApplicationMenuInfo getParent() {
    if (UUIDUtil.emptyString().equals(this.getParentId())) {
      return null;
    }

    if (mParent == null && !StringUtil.isNullOrEmpty(this.getParentId())) {
      // FIXME
      // mParent = AppsContext.getInstance().getApplicationMenuService().FindOne(this.getParentId());
    }

    return mParent;
  }

  private String mParentId = UUIDUtil.emptyString();

  /**
   */
  public final String getParentId() {
    return mParentId;
  }

  public final void setParentId(String value) {
    mParentId = value;
  }

  /**
   */
  public final String getParentName() {
    return this.getParent() == null ? this.getApplicationDisplayName() : this.getParent().getName();
  }

  private String mCode = "";

  /**
   */
  public final String getCode() {
    return mCode;
  }

  public final void setCode(String value) {
    mCode = value;
  }

  private String mName = "";

  /**
   */
  public final String getName() {
    return mName;
  }

  public final void setName(String value) {
    mName = value;
  }

  private String mDescription = "";

  /**
   */
  public final String getDescription() {
    return mDescription;
  }

  public final void setDescription(String value) {
    mDescription = value;
  }

  private String mUrl = "";

  /**
   */
  public final String getUrl() {
    return mUrl;
  }

  public final void setUrl(String value) {
    mUrl = value;
  }

  private String mTarget = "_self";

  /**
   */
  public final String getTarget() {
    return mTarget;
  }

  public final void setTarget(String value) {
    mTarget = value;
  }

  private String mTargetView = "";

  /**
   */
  public final String getTargetView() {
    if (StringUtil.isNullOrEmpty(mTargetView) && !StringUtil.isNullOrEmpty(this.getTarget())) {
      // FIXME 待处理
      // this.mTargetView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用链接打开方式", this.getTarget());
    }

    return mTargetView;
  }

  private String mMenuType = "ApplicationMenu";

  /**
   */
  public final String getMenuType() {
    return mMenuType;
  }

  public final void setMenuType(String value) {
    mMenuType = value;
  }

  private String mMenuTypeView = null;

  /**
   */
  public final String getMenuTypeView() {
    if (StringUtil.isNullOrEmpty(mMenuTypeView) && !StringUtil.isNullOrEmpty(this.getMenuType())) {
      // FIXME 待处理
      // this.mMenuTypeView = AppsContext.getInstance().getApplicationSettingService().GetText(AppsContext.Instance.ApplicationService["ApplicationManagement"].Id, "应用管理_应用菜单类别", mMenuType);
    }

    return mMenuTypeView;
  }

  private String mIconPath = "";

  /**
   */
  public final String getIconPath() {
    return mIconPath;
  }

  public final void setIconPath(String value) {
    mIconPath = value;
  }
  ///#endregion

  ///#region 属性:BigIconPath
  private String mBigIconPath = "";

  /**
   */
  public final String getBigIconPath() {
    return mBigIconPath;
  }

  public final void setBigIconPath(String value) {
    mBigIconPath = value;
  }
  ///#endregion

  ///#region 属性:DisplayType
  private String mDisplayType = "";

  /**
   * 显示方式
   */
  public final String getDisplayType() {
    return mDisplayType;
  }

  public final void setDisplayType(String value) {
    mDisplayType = value;
  }

  private String mDisplayTypeView = "";

  /**
   */
  public final String getDisplayTypeView() {
    if (StringUtil.isNullOrEmpty(mDisplayTypeView) && !StringUtil.isNullOrEmpty(this.getDisplayType())) {
      // FIXME
      // this.mDisplayTypeView = AppsContext.getInstance().getApplicationSettingService().getText(AppsContext.getInstance().getApplicationService().findOneByApplicationName("ApplicationManagement").getId(), "应用管理_应用菜单展现方式", this.getDisplayType());
    }

    return mDisplayTypeView;
  }

  private int mHasChild;

  /**
   */
  public final int getHasChild() {
    return mHasChild;
  }

  public final void setHasChild(int value) {
    mHasChild = value;
  }

  private String mContextObject = "";

  /**
   * 上下文对象
   */
  public final String getContextObject() {
    return mContextObject;
  }

  public final void setContextObject(String value) {
    mContextObject = value;
  }

  private String mOrderId = "";

  /**
   */
  public final String getOrderId() {
    return mOrderId;
  }

  public final void setOrderId(String value) {
    mOrderId = value;
  }

  private int mStatus;

  /**
   */
  public final int getStatus() {
    return mStatus;
  }

  public final void setStatus(int value) {
    mStatus = value;
  }

  private String mRemark = "";

  /**
   */
  public final String getRemark() {
    return mRemark;
  }

  public final void setRemark(String value) {
    mRemark = value;
  }

  private String mFullPath = "";

  /**
   */
  public final String getFullPath() {
    return mFullPath;
  }

  public final void setFullPath(String value) {
    mFullPath = value;
  }

  private java.time.LocalDateTime mModifiedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getModifiedDate() {
    return mModifiedDate;
  }

  public final void setModifiedDate(java.time.LocalDateTime value) {
    mModifiedDate = value;
  }

  private java.time.LocalDateTime mCreatedDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getCreatedDate() {
    return mCreatedDate;
  }

  public final void setCreatedDate(java.time.LocalDateTime value) {
    mCreatedDate = value;
  }

  // -------------------------------------------------------
  // 可访问成员信息
  // -------------------------------------------------------

  /**
   * 权限：应用_通用_查看权限
   */
  // private AuthorityInfo authorizationRead = AuthorityContext.Instance.AuthorityService["应用_通用_查看权限"];

  /**
   * 绑定查看权限
   *
   * @param scopeText
   */
  public final void BindAuthorizationReadScope(String scopeText) {
    // 清空缓存数据
    this.mAuthorizationReadScopeObjectText = null;
    this.mAuthorizationReadScopeObjectView = null;

    // TODO 待处理
    // if (this.mAuthorizationReadScopeObjects == null) {
    //   this.mAuthorizationReadScopeObjects = new ArrayList<MembershipAuthorizationScopeObject>();
    // }

    // MembershipAuthorizationScopeManagement.BindAuthorizationScopeObjects(this.mAuthorizationReadScopeObjects, scopeText);
  }

  // private List<MembershipAuthorizationScopeObject> mAuthorizationReadScopeObjects = null;

  /**
   * 权限：应用_通用_查看权限范围
   */
  // public final List<MembershipAuthorizationScopeObject> getAuthorizationReadScopeObjects() {
  //   if (mAuthorizationReadScopeObjects == null) {
  //     mAuthorizationReadScopeObjects = AppsContext.Instance.ApplicationMenuService.GetAuthorizationScopeObjects(this.getEntityId(), this.authorizationRead.Name);
  //
  //     // 设置默认权限是所有人
  //    if (mAuthorizationReadScopeObjects.isEmpty()) {
  //       IAuthorizationObject authorizationObject = MembershipManagement.Instance.RoleService.GetEveryoneObject();

  //       mAuthorizationReadScopeObjects.add(new MembershipAuthorizationScopeObject(authorizationObject.getAuthorizationObjectType(), authorizationObject.getAuthorizationObjectId(), authorizationObject.getAuthorizationObjectName()));
  //    }
  //  }

  //   return mAuthorizationReadScopeObjects;
  // }

  private String mAuthorizationReadScopeObjectText = null;

  /**
   * 权限：应用_通用_查看权限范围文本
   */
  public final String getAuthorizationReadScopeObjectText() {
    if (StringUtil.isNullOrEmpty(mAuthorizationReadScopeObjectText)) {
      // FIXME 待处理
      // mAuthorizationReadScopeObjectText = MembershipAuthorizationScopeManagement.GetAuthorizationScopeObjectText(this.getAuthorizationReadScopeObjects());
    }

    return mAuthorizationReadScopeObjectText;
  }

  public final void setAuthorizationReadScopeObjectText(String value) {
    if (!StringUtil.isNullOrEmpty(value)) {
      BindAuthorizationReadScope(value);
    }
  }

  private String mAuthorizationReadScopeObjectView = null;

  /**
   * 权限：应用_通用_查看权限范围视图
   */
  public final String getAuthorizationReadScopeObjectView() {
    if (StringUtil.isNullOrEmpty(mAuthorizationReadScopeObjectView)) {
      // mAuthorizationReadScopeObjectView = MembershipAuthorizationScopeManagement.GetAuthorizationScopeObjectView(this.getAuthorizationReadScopeObjects());
    }

    return mAuthorizationReadScopeObjectView;
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
}
