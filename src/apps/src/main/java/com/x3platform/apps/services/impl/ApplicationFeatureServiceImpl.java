package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_FEATURE_ROOT_ID;
import static com.x3platform.apps.Constants.APPLICATION_MENU_ROOT_ID;
import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.mappers.ApplicationFeatureMapper;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationSetting;
import com.x3platform.apps.services.ApplicationFeatureService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.security.authority.Authority;
import com.x3platform.security.authority.AuthorityContext;
import com.x3platform.tree.DynamicTreeNode;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeNode;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用功能服务
 *
 * @author ruanyu
 */
public class ApplicationFeatureServiceImpl implements ApplicationFeatureService {

  private static final String CACHE_KEY_NAME_PREFIX = "x3platform:apps:application-feature:name:";

  private static final String CACHE_KEY_TREEVIEW_PREFIX = "x3platform:apps:application-feature:treeview:";

  private static final String TREEVIEW_ROOT_ID = "function#applicationId#00000000-0000-0000-0000-000000000001#featureId#00000000-0000-0000-0000-000000000000";

  private static final String TREENODE_ID_FORMAT = "{}#applicationId#{}#featureId#{}";

  private static final String CACHE_KEY_ID_PREFIX = "x3platform:apps:application-feature:id:";

  private static final String DATA_SCOPE_TABLE_NAME = "application_feature_scope";

  private static final String DIGITAL_NUMBER_KEY_CODE = "table_application_feature_key_code";

  @Autowired(required = false)
  ApplicationFeatureMapper provider;

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(ApplicationFeature item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item);
    }

    if (!StringUtil.isNullOrEmpty(item.getName())) {
      String key = CACHE_KEY_NAME_PREFIX + item.getName();
      CachingManager.set(key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(ApplicationFeature item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }

    if (!StringUtil.isNullOrEmpty(item.getName())) {
      String key = CACHE_KEY_NAME_PREFIX + item.getName();
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
   * @param entity {@link ApplicationFeature} 实例的详细信息
   * @return {@link ApplicationFeature} 实例的详细信息
   */
  @Override
  public int save(ApplicationFeature entity) {
    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new NullPointerException("标识不能为空");
    }

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !provider.isExist(entity.getId());

    int affectedRows = -1;

    String id = entity.getId();

    // 如果 ParentId 为空 或者 ParentId = Application Id 则设置为应用的根节点菜单
    if (StringUtil.isNullOrEmpty(entity.getParentId()) || entity.getParentId().equals(entity.getApplicationId())) {
      entity.setParentId(APPLICATION_FEATURE_ROOT_ID);
    }

    if (!isExist(id)) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }

      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    if (AppsConfigurationView.getInstance().getSyncFeatureToMenu()) {
      ApplicationMenu menu = AppsContext.getInstance().getApplicationMenuService().findOne(id);
      if (menu == null) {
        menu = new ApplicationMenu();
      }
      menu.setId(entity.getId());
      menu.setApplicationId(entity.getApplicationId());
      menu.setParentId(entity.getParentId());
      menu.setCode(entity.getCode());
      menu.setName(entity.getDisplayName());
      menu.setUrl(entity.getUrl());
      menu.setTarget(entity.getTarget());
      menu.setIconPath(entity.getIconPath());
      menu.setBigIconPath(entity.getBigIconPath());
      menu.setOrderId(entity.getOrderId());
      menu.setStatus(entity.getStatus());

      AppsContext.getInstance().getApplicationMenuService().save(menu);
    }

    return 0;
  }

  @Override
  public int delete(String id) {
    ApplicationFeature entity = provider.selectByPrimaryKey(id);

    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);

      // 删除数据库记录
      int affectedRows = provider.deleteByPrimaryKey(id);

      KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);

      if (AppsConfigurationView.getInstance().getSyncFeatureToMenu()) {
        AppsContext.getInstance().getApplicationMenuService().delete(id);
      }
    }

    return 0;
  }

  @Override
  public ApplicationFeature findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  @Override
  public List<ApplicationFeature> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * 获取授权对象允许的功能列表
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId   授权对象标识
   * @param authorityName           权限名称
   * @return 允许的功能列表
   */
  @Override
  public List<ApplicationFeature> findAllAllowedByAuthorizationObjectIds(String authorizationObjectType,
                                                                         String authorizationObjectId, String authorityName) {
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    return provider.findAllAllowedByAuthorizationObjectIds(
      authorizationObjectType, authorizationObjectId, authority.getId());
  }

  /**
   * 查询所有记录信息
   *
   * @param query 查询参数集合
   */
  @Override
  public List<ApplicationFeature> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  @Override
  public boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  /**
   * 获取允许的功能列表
   *
   * @return 允许的功能列表
   */
  @Override
  public List<String> getAllowedFeatures() {
    return provider.getAllowedFeatures(null, bindAuthorizationScopeSql());
  }

  @Override
  public boolean hasAuthority(String entityId, String authorityName, Account account) {
    return MembershipManagement.getInstance().getAuthorizationObjectService().hasAuthority(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationFeature.class),
      authorityName,
      account);
  }

  /**
   * 配置应用功能的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeByEntityId(String entityId, String authorityName, String scopeText) {
    MembershipManagement.getInstance().getAuthorizationObjectService().bindAuthorizationScopeByEntityId(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationFeature.class),
      authorityName,
      scopeText);

    if (AppsConfigurationView.getInstance().getSyncFeatureToMenu()) {
      AppsContext.getInstance().getApplicationMenuService()
        .bindAuthorizationScopeByEntityId(entityId, authorityName, scopeText);
    }
  }

  /**
   * 配置应用功能的权限信息
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId   授权对象标识
   * @param authorityName           权限名称
   * @param entityIds               实体标识 多个对象以逗号隔开
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

    if (AppsConfigurationView.getInstance().getSyncFeatureToMenu()) {
      AppsContext.getInstance().getApplicationMenuService()
        .bindAuthorizationScopeByAuthorizationObjectIds(
          authorizationObjectType,
          authorizationObjectId,
          authorityName,
          entityIds);
    }
  }

  /**
   * 绑定 SQL 查询条件
   */
  private String bindAuthorizationScopeSql() {
    Account account = KernelContext.getCurrent().getUser();
    if (AppsSecurity.isAdministrator(account, AppsConfiguration.APPLICATION_NAME)) {
      return "";
    } else {
      String accountId = account == null ? UUIDUtil.emptyString() : account.getId();

      return String.format(" ("
        + "(T.id IN ( "
        + "   SELECT entity_id "
        + "     FROM view_authobject_account View1, application_feature_scope Scope"
        + "    WHERE View1.account_id = '%s'"
        + "      AND View1.authorization_object_id = Scope.authorization_object_id"
        + "      AND View1.authorization_object_type = Scope.authorization_object_type "
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

    if (TREEVIEW_ROOT_ID.equals(parentId)) {
      TreeNode treeNode = null;
      // 特殊类型 开始菜单 顶部菜单 快捷菜单
      if (!AppsConfigurationView.getInstance().getHiddenStartMenu()) {
        treeNode = new TreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "startMenu", application.getId(), UUIDUtil.emptyString("D")),
          APPLICATION_FEATURE_ROOT_ID, "开始菜单", "开始菜单", commandFormat);

        List<TreeNode> childNodes = getTreeNodes(
          StringUtil.format(TREENODE_ID_FORMAT, "startMenu", application.getId(), UUIDUtil.emptyString("D")),
          commandFormat);

        if (!childNodes.isEmpty()) {
          treeNode.add(childNodes);
        }

        treeNodes.add(treeNode);
      }

      if (!AppsConfigurationView.getInstance().getHiddenTopMenu()) {
        treeNode = new TreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "topMenu", application.getId(), UUIDUtil.emptyString("D")),
          APPLICATION_FEATURE_ROOT_ID, "顶部菜单", "顶部菜单", commandFormat);

        List<TreeNode> childNodes = getTreeNodes(
          StringUtil.format(TREENODE_ID_FORMAT, "topMenu", application.getId(), UUIDUtil.emptyString("D")),
          commandFormat);

        if (!childNodes.isEmpty()) {
          treeNode.add(childNodes);
        }

        treeNodes.add(treeNode);
      }

      if (!AppsConfigurationView.getInstance().getHiddenShortcutMenu()) {
        treeNode = new TreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "shortcutMenu", application.getId(), UUIDUtil.emptyString("D")),
          APPLICATION_FEATURE_ROOT_ID, "快捷菜单", "快捷菜单", commandFormat);

        List<TreeNode> childNodes = getTreeNodes(
          StringUtil.format(TREENODE_ID_FORMAT, "shortcutMenu", application.getId(), UUIDUtil.emptyString("D")),
          commandFormat);

        if (!childNodes.isEmpty()) {
          treeNode.add(childNodes);
        }

        treeNodes.add(treeNode);
      }

      // 添加应用管理
      treeNode = new TreeNode(
        StringUtil.format(TREENODE_ID_FORMAT, "application", application.getId(), UUIDUtil.emptyString("D")),
        APPLICATION_FEATURE_ROOT_ID, application.getApplicationDisplayName(), application.getApplicationDisplayName(),
        commandFormat);

      List<TreeNode> childNodes = getTreeNodes(
        StringUtil.format(TREENODE_ID_FORMAT, "application", application.getId(), UUIDUtil.emptyString("D")),
        commandFormat);

      if (!childNodes.isEmpty()) {
        treeNode.add(childNodes);
      }

      treeNodes.add(treeNode);

      List<Application> list = AppsContext.getInstance().getApplicationService().findTreeNodesByParentId(keys[2]);

      for (Application item : list) {
        treeNode = new TreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "application", item.getId(), UUIDUtil.emptyString("D")),
          StringUtil.format(TREENODE_ID_FORMAT, "application", keys[2], UUIDUtil.emptyString("D")),
          item.getApplicationDisplayName(), item.getApplicationDisplayName(), commandFormat);

        childNodes = getTreeNodes(StringUtil.format(TREENODE_ID_FORMAT,
          "application", item.getId(), UUIDUtil.emptyString("D")), commandFormat);

        if (!childNodes.isEmpty()) {
          treeNode.add(childNodes);
        }

        treeNodes.add(treeNode);
      }
    } else {
      if ("application".equals(keys[0]) && !application.getId().equals(keys[2])
        && UUIDUtil.emptyString("D").equals(keys[4])) {
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
      List<ApplicationFeature> list = null;

      String type = StringUtil.toFirstUpper(keys[0]);

      // 特殊菜单
      if ("StartMenu".equals(type) || "TopMenu".equals(type) || "ShortcutMenu".equals(type)) {
        if (UUIDUtil.emptyString("D").equals(keys[4])) {
          list = provider.findTreeNodesByApplicationIdAndType(keys[2], type);
        } else {
          list = provider.findTreeNodesByParentIdAndType(keys[4], type);
        }
      } else {
        // 功能项
        if (UUIDUtil.emptyString("D").equals(keys[4])) {
          list = provider.findTreeNodesByApplicationId(keys[2]);
        } else {
          list = provider.findTreeNodesByParentId(keys[4]);
        }
      }

      for (ApplicationFeature item : list) {
        TreeNode treeNode = new TreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, item.getType(), item.getApplicationId(), item.getId()),
          StringUtil.format(TREENODE_ID_FORMAT, item.getType(), item.getApplicationId(), item.getParentId()),
          item.getDisplayName(), item.getDisplayName(), commandFormat);

        List<TreeNode> childNodes = getTreeNodes(StringUtil.format(TREENODE_ID_FORMAT,
          item.getType(), item.getApplicationId(), item.getId()), commandFormat);

        if (!childNodes.isEmpty()) {
          treeNode.add(childNodes);
        }

        treeNodes.add(treeNode);
      }
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

      // 添加应用管理
      treeNode = new DynamicTreeNode(
        StringUtil.format(TREENODE_ID_FORMAT, "application", application.getId(), UUIDUtil.emptyString("D")),
        APPLICATION_FEATURE_ROOT_ID, application.getApplicationDisplayName(), application.getApplicationDisplayName(),
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
      if ("application".equals(keys[0]) && !application.getId().equals(keys[2])
        && APPLICATION_FEATURE_ROOT_ID.equals(keys[4])) {
        List<Application> list = AppsContext.getInstance().getApplicationService().findTreeNodesByParentId(keys[2]);

        for (Application item : list) {
          DynamicTreeNode treeNode = new DynamicTreeNode(
            StringUtil.format(TREENODE_ID_FORMAT, "application", item.getId(), UUIDUtil.emptyString("D")),
            StringUtil.format(TREENODE_ID_FORMAT, "application", keys[2], UUIDUtil.emptyString("D")),
            item.getApplicationDisplayName(), item.getApplicationDisplayName(), commandFormat, true);

          treeView.add(treeNode);
        }
      }

      // 获取应用功能节点
      List<ApplicationFeature> list = null;

      String featureType = StringUtil.toFirstUpper(keys[0]);

      if ("Application".equals(featureType)) {
        featureType = "ApplicationMenu";
      }

      // 功能项
      if (APPLICATION_FEATURE_ROOT_ID.equals(keys[4])) {
        list = provider.findTreeNodesByApplicationId(keys[2]);
      } else {
        list = provider.findTreeNodesByParentId(keys[4]);
      }

      for (ApplicationFeature item : list) {
        DynamicTreeNode treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, item.getType(), item.getApplicationId(), item.getId()),
          StringUtil.format(TREENODE_ID_FORMAT, item.getType(), item.getApplicationId(), item.getParentId()),
          item.getDisplayName(), item.getDisplayName(), commandFormat,
          "function".equals(item.getType()) ? true : false);

        treeView.add(treeNode);
      }
    }

    return treeView;
  }

  @Override
  public List<ApplicationFeature> findTreeNodesByApplicationId(String applicationId) {
    return provider.findTreeNodesByApplicationId(applicationId);
  }

  @Override
  public List<ApplicationFeature> findTreeNodesByParentId(String parentId) {
    return provider.findTreeNodesByParentId(parentId);
  }
}
