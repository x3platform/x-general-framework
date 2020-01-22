package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_FEATURE_ROOT_ID;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.mappers.ApplicationFeatureMapper;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.services.ApplicationFeatureService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
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

  @Autowired(required = false)
  ApplicationFeatureMapper provider;

  /**
   * 保存记录
   *
   * @param entity {@link ApplicationFeature} 实例的详细信息
   * @return {@link ApplicationFeature} 实例的详细信息
   */
  @Override
  public int save(ApplicationFeature entity) {
    int affectedRows = -1;

    String id = entity.getId();
    if (!isExist(id)) {
      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    return 0;
  }

  /**
   * 查询所有记录信息
   *
   * @param query 查询参数集合
   * @return
   */
  @Override
  public List<ApplicationFeature> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  @Override
  public int delete(String id) {
    return provider.deleteByPrimaryKey(id);
  }

  @Override
  public ApplicationFeature findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  @Override
  public List<ApplicationFeature> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
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
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeByEntityId(String entityId, String authorityName, String scopeText) {
    MembershipManagement.getInstance().getAuthorizationObjectService().bindAuthorizationScopeByEntityId(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationFeature.class),
      authorityName,
      scopeText);
  }

  /**
   * 配置应用功能的权限信息
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
    List<ApplicationFeature> list = null;

    // 功能项
    if (UUIDUtil.emptyString("D").equals(keys[4])) {
      list = provider.findTreeNodesByApplicationId(keys[2]);
    } else {
      list = provider.findTreeNodesByParentId(keys[4]);
    }

    for (ApplicationFeature item : list) {
      TreeNode treeNode = new TreeNode(
        StringUtil.format(TREENODE_ID_FORMAT, item.getType(), item.getApplicationId(), item.getId()),
        StringUtil.format(TREENODE_ID_FORMAT, item.getType(), item.getApplicationId(), item.getParentId()),
        item.getDisplayName(), item.getDisplayName(), commandFormat);

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

    if (APPLICATION_FEATURE_ROOT_ID.equals(keys[4])) {
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
        item.getDisplayName(), item.getDisplayName(), commandFormat, "function".equals(item.getType()) ? true : false);

      treeView.add(treeNode);
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
