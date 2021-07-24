package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_SETTING_GROUP_ROOT_ID;
import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.mappers.ApplicationSettingGroupMapper;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.ApplicationMethod;
import com.x3platform.apps.models.ApplicationSettingGroup;
import com.x3platform.apps.services.ApplicationSettingGroupService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
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
public class ApplicationSettingGroupServiceImpl implements ApplicationSettingGroupService {

  private static final String CACHE_KEY_ID_PREFIX = "x3platform:apps:application-feature:id:";

  private static final String CACHE_KEY_TREEVIEW_PREFIX = "x3platform:apps:application-setting-group:treeview:";

  private static final String TREEVIEW_ROOT_ID = "settingGroup#applicationId#00000000-0000-0000-0000-000000000001#settingGroupId#00000000-0000-0000-0000-000000000000";

  private static final String TREENODE_ID_FORMAT = "{}#applicationId#{}#settingGroupId#{}";

  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationSettingGroupMapper provider = null;

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(ApplicationSettingGroup item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(ApplicationSettingGroup item) {
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
   * @param entity {@link ApplicationSettingGroup} 实例的详细信息
   * @return {@link ApplicationSettingGroup} 实例的详细信息
   */
  @Override
  public int save(ApplicationSettingGroup entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    boolean isExist = provider.isExist(entity.getId());

    if (!isExist) {
      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'" + id + "', affectedRows:" + affectedRows);

    // 保存数据后, 更新缓存信息
    entity = provider.selectByPrimaryKey(entity.getId());

    if (entity != null) {
      removeCacheItem(entity);

      addCacheItem(entity);

      // 如果单个分组信息缓存信息出现更新，清除所有树节点的缓存。
      CachingManager.deleteByPattern(CACHE_KEY_TREEVIEW_PREFIX + "*");
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
    ApplicationSettingGroup entity = provider.selectByPrimaryKey(id);

    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);

      // 如果单个组织信息缓存信息出现更新，清除所有树节点的缓存。
      CachingManager.deleteByPattern(CACHE_KEY_TREEVIEW_PREFIX + "*");

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
   * @return {@link ApplicationSettingGroup} 实例的详细信息
   */
  @Override
  public ApplicationSettingGroup findOne(String id) {
    ApplicationSettingGroup entity = null;

    // 根节点快速处理
    if (APPLICATION_SETTING_GROUP_ROOT_ID.equals(id)) {
      return null;
    }

    String key = CACHE_KEY_ID_PREFIX + id;

    if (CachingManager.contains(key)) {
      entity = (ApplicationSettingGroup) CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if(entity == null) {
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
   * @return 所有相关 {@link ApplicationSettingGroup} 实例的详细信息
   */
  @Override
  public List<ApplicationSettingGroup> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  // @Override
  public List<ApplicationSettingGroup> findTreeNodesByApplicationId(String applicationId) {
    return provider.findTreeNodesByApplicationId(applicationId);
  }

  /**
   * 根据父节点标识获取可用的树形节点数据
   *
   * @param parentId 父级节点标识
   * @return 所有 {@link ApplicationSettingGroup} 实例的详细信息
   */
  @Override
  public List<ApplicationSettingGroup> findTreeNodesByParentId(String parentId) {
    return provider.findTreeNodesByParentId(parentId);
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

    List<ApplicationSettingGroup> list = null;

    // 功能项
    if (UUIDUtil.emptyString("D").equals(keys[4])) {
      list = provider.findTreeNodesByApplicationId(keys[2]);
    } else {
      list = provider.findTreeNodesByParentId(keys[4]);
    }

    for (ApplicationSettingGroup item : list) {
      TreeNode treeNode = new TreeNode(
        StringUtil.format(TREENODE_ID_FORMAT, "settingGroup", item.getApplicationId(), item.getId()),
        StringUtil.format(TREENODE_ID_FORMAT, "settingGroup", item.getApplicationId(), item.getParentId()),
        item.getDisplayName(), item.getDisplayName(), commandFormat);

      List<TreeNode> childNodes = getTreeNodes(StringUtil.format(TREENODE_ID_FORMAT,
        "settingGroup", item.getApplicationId(), item.getId()), commandFormat);

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
    // "settingGroup#applicationId#00000000-0000-0000-0000-000000000001#settingGroupId#00000000-0000-0000-0000-000000000000"
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
        APPLICATION_SETTING_GROUP_ROOT_ID, application.getApplicationDisplayName(),
        application.getApplicationDisplayName(),
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
        && APPLICATION_SETTING_GROUP_ROOT_ID.equals(keys[4])) {
        List<Application> list = AppsContext.getInstance().getApplicationService().findTreeNodesByParentId(keys[2]);

        for (Application item : list) {
          DynamicTreeNode treeNode = new DynamicTreeNode(
            StringUtil.format(TREENODE_ID_FORMAT, "application", item.getId(), UUIDUtil.emptyString("D")),
            StringUtil.format(TREENODE_ID_FORMAT, "application", keys[2], UUIDUtil.emptyString("D")),
            item.getApplicationDisplayName(), item.getApplicationDisplayName(), commandFormat, true);

          treeView.add(treeNode);
        }
      }

      List<ApplicationSettingGroup> list = null;

      // 功能项
      if (APPLICATION_SETTING_GROUP_ROOT_ID.equals(keys[4])) {
        list = provider.findTreeNodesByApplicationId(keys[2]);
      } else {
        list = provider.findTreeNodesByParentId(keys[4]);
      }

      for (ApplicationSettingGroup item : list) {
        DynamicTreeNode treeNode = new DynamicTreeNode(
          StringUtil.format(TREENODE_ID_FORMAT, "settingGroup", item.getApplicationId(), item.getId()),
          StringUtil.format(TREENODE_ID_FORMAT, "settingGroup", item.getApplicationId(), item.getParentId()),
          item.getDisplayName(), item.getDisplayName(), commandFormat, true);

        treeView.add(treeNode);
      }
    }

    return treeView;
  }
}
