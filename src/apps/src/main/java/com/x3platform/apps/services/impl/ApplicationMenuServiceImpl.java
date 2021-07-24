package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_MENU_ROOT_ID;
import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

import com.x3platform.AuthorizationScope;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.mappers.ApplicationMenuMapper;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import com.x3platform.apps.services.ApplicationMenuService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.tree.DynamicTreeNode;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeNode;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class ApplicationMenuServiceImpl implements ApplicationMenuService {

  private static final String CACHE_KEY_TREEVIEW_PREFIX = "x3platform:apps:application-menu:treeview:";

  private static final String TREEVIEW_ROOT_ID = "menu#applicationId#00000000-0000-0000-0000-000000000001#menuId#00000000-0000-0000-0000-000000000000";

  private static final String TREENODE_ID_FORMAT = "{}#applicationId#{}#menuId#{}";

  private static final String CACHE_KEY_ID_PREFIX = "x3platform:apps:application-menu:id:";

  private static final String DATA_SCOPE_TABLE_NAME = "application_menu_scope";

  private static final String DIGITAL_NUMBER_KEY_CODE = "table_application_menu_key_code";

  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMenuMapper provider = null;

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(ApplicationMenu item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(ApplicationMenu item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }
  }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 ApplicationMenu 详细信息
   */
  @Override
  public int save(ApplicationMenu entity) throws NullPointerException {
    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new NullPointerException("标识不能为空");
    }

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !provider.isExist(entity.getId());

    int affectedRows;

    String id = entity.getId();

    // 如果 ParentId 为空 或者 ParentId = Application Id 则设置为应用的根节点菜单
    if(StringUtil.isNullOrEmpty(entity.getParentId()) || entity.getParentId().equals(entity.getApplicationId()))
    {
      entity.setParentId(APPLICATION_MENU_ROOT_ID);
    }

    if (StringUtil.isNullOrEmpty(entity.getTarget())) {
      entity.setTarget("_self");
    }
    if (StringUtil.isNullOrEmpty(entity.getDisplayType())) {
      entity.setDisplayType("MenuItem");
    }

    // 计算完整路径
    entity.setFullPath(combineFullPath(entity));

    if (isNewObject) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }

      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    // 保存数据后, 更新缓存信息
    entity = provider.selectByPrimaryKey(entity.getId());

    if (entity != null) {
      removeCacheItem(entity);

      addCacheItem(entity);
    }

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    ApplicationMenu entity = provider.selectByPrimaryKey(id);

    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);

      // 删除数据库记录
      int affectedRows = provider.deleteByPrimaryKey(id);

      KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);
    }

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   */
  @Override
  public ApplicationMenu findOne(String id) {
    ApplicationMenu entity = null;

    // 根节点快速处理
    if (APPLICATION_MENU_ROOT_ID.equals(id)) {
      return null;
    }

    String key = CACHE_KEY_ID_PREFIX + id;

    if (CachingManager.contains(key)) {
      entity = (ApplicationMenu) CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (entity == null) {
      entity = provider.selectByPrimaryKey(id);

      if (entity != null) {
        addCacheItem(entity);
      }
    }

    return entity;
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   */
  @Override
  public List<ApplicationMenu> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   */
  @Override
  public List<ApplicationMenuLite> findAllLites(DataQuery query) {
    return provider.findAllLites(query.getMap());
  }

  @Override
  public List<ApplicationMenuLite> findTreeNodesByApplicationId(String applicationId, String menuType) {
    return provider.findTreeNodesByApplicationId(applicationId, menuType);
  }

  @Override
  public List<ApplicationMenuLite> findTreeNodesByParentId(String parentId, String menuType) {
    return provider.findTreeNodesByParentId(parentId, menuType);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 组合菜单全路径
   *
   * @param param 菜单信息
   */
  @Override
  public String combineFullPath(ApplicationMenu param) {
    String fullPath = "";

    fullPath = param.getName();

    ApplicationMenu parent = param.getParent();

    int depthCount = 0;

    while (parent != null) {
      fullPath = String.format("%1$s\\%2$s", parent.getName(), fullPath);

      parent = parent.getParent();

      // 如果深度超过50层，则跳出循环。(可能陷入死循环)
      if (depthCount++ > 50) {
        break;
      }
    }

    if (param.getMenuType().equals("ApplicationMenu")) {
      fullPath = String.format("%1$s\\%2$s", param.getApplicationDisplayName(), fullPath);
    } else {
      fullPath = String.format("%1$s\\%2$s", param.getMenuTypeView(), fullPath);
    }

    return fullPath;
  }

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param parentId 所属父级菜单标识
   * @param menuType 菜单类型
   * @return 所有实例 {@link ApplicationMenu} 的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByParentId(String applicationId, String parentId, String menuType) {
    return provider.getMenusByParentId(applicationId, parentId, menuType, bindAuthorizationScopeSql());
  }

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param fullPath 所属父级菜单标识
   * @param menuType 菜单类型
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByFullPath(String applicationId, String fullPath, String menuType) {
    return provider.getMenusByFullPath(applicationId, fullPath, menuType);
  }

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByApplicationId(String applicationId) {
    return provider.getMenusByApplicationId(applicationId);
  }

  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  /**
   * 判断用户是否拥应用菜单有权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param account 帐号
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(String entityId, String authorityName, Account account) {
    return MembershipManagement.getInstance().getAuthorizationObjectService().hasAuthority(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationMenu.class),
      authorityName,
      account);
  }

  /**
   * 配置应用菜单的权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeByEntityId(String entityId, String authorityName, String scopeText) {
    MembershipManagement.getInstance().getAuthorizationObjectService().bindAuthorizationScopeByEntityId(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationMenu.class),
      authorityName,
      scopeText);
  }

  /**
   * 配置应用菜单的权限信息
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识 多个对象以逗号隔开
   */
  @Override
  public void bindAuthorizationScopeByAuthorizationObjectIds(String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds) {
    MembershipManagement.getInstance().getAuthorizationObjectService().bindAuthorizationScopeByAuthorizationObjectIds(
      DATA_SCOPE_TABLE_NAME,
      authorizationObjectType,
      authorizationObjectId,
      authorityName,
      entityIds,
      KernelContext.parseObjectType(ApplicationMenu.class));
  }

  /**
   * 查询实体对象的授权信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   */
  @Override
  public List<AuthorizationScope> getAuthorizationScopes(String entityId, String authorityName) {
    return MembershipManagement.getInstance().getAuthorizationObjectService().getAuthorizationScopes(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationMenu.class),
      authorityName);
  }

  // -------------------------------------------------------
  // 权限
  // -------------------------------------------------------

  ///#region 私有函数:GetAuthorizationReadObject(ApplicationMenu param)

  /**
   * 验证对象的权限
   *
   * @param param 需验证的对象
   * @return 对象
   */
  private ApplicationMenu getAuthorizationReadObject(ApplicationMenu param) {
    // FIXME
    /*
    Account account = KernelContext.Current.User;
    if (AppsSecurity.isAdministrator(account, AppsConfiguration.ApplicationName)) {
      return param;
    } else {
      if (MembershipAuthorizationScopeManagement.Authenticate(param.AuthorizationReadScopeObjects, account)) {
        return param;
      }
      return null;
    }
    */
    return null;
  }

  /**
   * 绑定 SQL 查询条件
   */
  private String bindAuthorizationScopeSql() {
    if (AppsSecurity.isAdministrator(KernelContext.getCurrent().getUser(), APPLICATION_NAME)) {
      return "";
    } else {
      String accountId = KernelContext.getCurrent().getUser() == null ?
        UUIDUtil.emptyString() : KernelContext.getCurrent().getUser().getId();

      return String.format(" ("
        + "(t.id IN ( "
        + "   SELECT entity_id "
        + "     FROM view_authobject_account view1, application_menu_scope scope"
        + "    WHERE view1.account_id = '%s'"
        + "      AND view1.authorization_object_id = scope.authorization_object_id"
        + "      AND view1.authorization_object_type = scope.authorization_object_type "
        + " GROUP BY entity_id)) "
        + ") ", accountId);
    }
  }

  // -------------------------------------------------------
  // 树形视图
  // -------------------------------------------------------

  @Override
  public TreeView getTreeView(String treeViewName, String treeViewRootTreeNodeId, String commandFormat) {
    String key = CACHE_KEY_TREEVIEW_PREFIX + treeViewRootTreeNodeId;

    // 读取缓存信息
    if (CachingManager.contains(key)) {
      return (TreeView) CachingManager.get(key);
    }

    TreeView treeView = new TreeView(treeViewName, treeViewRootTreeNodeId, commandFormat);

    List<TreeNode> childNodes = getTreeNodes(treeViewRootTreeNodeId, commandFormat);

    if (!childNodes.isEmpty()) {
      treeView.add(childNodes);
      // 设置缓存信息
      CachingManager.set(key, treeView);
    }

    return treeView;
  }

  private List<TreeNode> getTreeNodes(String parentId, String commandFormat) {
    // ParentId 内容格式如下
    // "function#applicationId#00000000-0000-0000-0000-000000000001#featureId#00000000-0000-0000-0000-000000000000"
    // keys[0] -
    // keys[1] - 所属应用标识标签
    // keys[2] - 所属应用标识
    // keys[3] - 所属应用标识标签
    // keys[4] - 所属应用功能标识
    String[] keys = parentId.split("#");

    List<TreeNode> treeNodes = new ArrayList<TreeNode>();

    Application application = AppsContext.getInstance().getApplicationService()
      .findOneByApplicationName(APPLICATION_NAME);

    if (UUIDUtil.emptyString("D").equals(keys[4])) {
      List<Application> list = AppsContext.getInstance().getApplicationService().findTreeNodesByParentId(keys[2]);

      for (Application item : list) {
        TreeNode treeNode = new TreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "application", item.getId(), UUIDUtil.emptyString("D")),
          StringUtil.format(TREENODE_ID_FORMAT, "application", keys[2], UUIDUtil.emptyString("D")),
          item.getApplicationDisplayName(), item.getApplicationDisplayName(), commandFormat);

        List<TreeNode> childNodes = getTreeNodes(StringUtil.format(TREENODE_ID_FORMAT,
          "application", item.getId(), UUIDUtil.emptyString("D")), commandFormat);

        if (!childNodes.isEmpty()) {
          treeNode.add(childNodes);
        }

        treeNodes.add(treeNode);
      }
    }

    // 获取应用功能节点
    List<ApplicationMenuLite> list = null;

    // 功能项
    if (UUIDUtil.emptyString("D").equals(keys[4])) {
      list = provider.findTreeNodesByApplicationId(keys[2], keys[0]);
    } else {
      list = provider.findTreeNodesByParentId(keys[4], keys[0]);
    }

    for (ApplicationMenuLite item : list) {
      TreeNode treeNode = new TreeNode(
        StringUtil.format(TREENODE_ID_FORMAT, "menu", item.getApplicationId(), item.getId()),
        StringUtil.format(TREENODE_ID_FORMAT, "menu", item.getApplicationId(), item.getParentId()),
        item.getName(), item.getName(), commandFormat);

      List<TreeNode> childNodes = getTreeNodes(StringUtil.format(TREENODE_ID_FORMAT,
        "function", item.getApplicationId(), item.getId()), commandFormat);

      if (!childNodes.isEmpty()) {
        treeNode.add(childNodes);
      }

      treeNodes.add(treeNode);
    }

    return treeNodes;
  }

  @Override
  public DynamicTreeView getDynamicTreeView(String treeViewName, String treeViewRootTreeNodeId, String parentId,
    String commandFormat) {
    // ParentId 内容格式如下
    // "function#applicationId#00000000-0000-0000-0000-000000000001#featureId#00000000-0000-0000-0000-000000000000"
    // keys[0] - 对象类型
    // keys[1] - 所属应用标识标签
    // keys[2] - 所属应用标识
    // keys[3] - 所属应用标识标签
    // keys[4] - 所属应用功能标识
    parentId = (StringUtil.isNullOrEmpty(parentId) || "0".equals(parentId)) ? treeViewRootTreeNodeId : parentId;

    String[] keys = parentId.split("#");

    DynamicTreeView treeView = new DynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId, commandFormat);

    Application application = AppsContext.getInstance().getApplicationService()
      .findOneByApplicationName(APPLICATION_NAME);

    if (TREEVIEW_ROOT_ID.equals(parentId)) {
      DynamicTreeNode treeNode = null;

      // 特殊类型 开始菜单 顶部菜单 快捷菜单
      if (!AppsConfigurationView.getInstance().getHiddenStartMenu()) {
        treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "startMenu", application.getId(), UUIDUtil.emptyString("D")),
          APPLICATION_MENU_ROOT_ID, "开始菜单", "开始菜单", commandFormat, true);

        treeView.add(treeNode);
      }

      if (!AppsConfigurationView.getInstance().getHiddenTopMenu()) {
        treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "topMenu", application.getId(), UUIDUtil.emptyString("D")),
          APPLICATION_MENU_ROOT_ID, "顶部菜单", "顶部菜单", commandFormat, true);

        treeView.add(treeNode);
      }

      if (!AppsConfigurationView.getInstance().getHiddenShortcutMenu()) {
        treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "shortcutMenu", application.getId(), UUIDUtil.emptyString("D")),
          APPLICATION_MENU_ROOT_ID, "快捷菜单", "快捷菜单", commandFormat, true);

        treeView.add(treeNode);
      }

      // 添加应用管理
      treeNode = new DynamicTreeNode(
        StringUtil.format(TREENODE_ID_FORMAT, "application", application.getId(), UUIDUtil.emptyString("D")),
        APPLICATION_MENU_ROOT_ID, application.getApplicationDisplayName(), application.getApplicationDisplayName(),
        commandFormat, true);

      treeView.add(treeNode);

      List<Application> list = AppsContext.getInstance().getApplicationService().findTreeNodesByParentId(keys[2]);

      for (Application item : list) {
        treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "application", item.getId(), UUIDUtil.emptyString("D")),
          StringUtil.format(TREENODE_ID_FORMAT, "application", keys[2], UUIDUtil.emptyString("D")),
          item.getApplicationDisplayName(), item.getApplicationDisplayName(), commandFormat, true);

        treeView.add(treeNode);
      }
    } else {
      // 应用节点处理
      if ("application".equals(keys[0]) && !application.getId().equals(keys[2])
        && APPLICATION_MENU_ROOT_ID.equals(keys[4])) {
        List<Application> list = AppsContext.getInstance().getApplicationService().findTreeNodesByParentId(keys[2]);

        for (Application item : list) {
          DynamicTreeNode treeNode = new DynamicTreeNode(
            StringUtil.format(TREENODE_ID_FORMAT, "application", item.getId(), UUIDUtil.emptyString("D")),
            StringUtil.format(TREENODE_ID_FORMAT, "application", keys[2], UUIDUtil.emptyString("D")),
            item.getApplicationDisplayName(), item.getApplicationDisplayName(), commandFormat, true);

          treeView.add(treeNode);
        }
      }

      // 获取应用菜单节点
      List<ApplicationMenuLite> list = null;

      String menuType = StringUtil.toFirstUpper(keys[0]);

      if ("Application".equals(menuType)) {
        menuType = "ApplicationMenu";
      }

      // 菜单项
      if (APPLICATION_MENU_ROOT_ID.equals(keys[4])) {
        list = provider.findTreeNodesByApplicationId(keys[2], menuType);
      } else {
        list = provider.findTreeNodesByParentId(keys[4], menuType);
      }

      for (ApplicationMenuLite item : list) {
        DynamicTreeNode treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, item.getMenuType(), item.getApplicationId(), item.getId()),
          StringUtil.format(TREENODE_ID_FORMAT, item.getMenuType(), item.getApplicationId(), item.getParentId()),
          item.getName(), item.getName(), commandFormat, true);

        treeView.add(treeNode);
      }
    }

    return treeView;
  }
}
