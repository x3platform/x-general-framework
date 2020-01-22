package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_ROOT_ID;

import com.x3platform.AuthorizationScope;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.mappers.ApplicationMapper;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationLite;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.tree.DynamicTreeNode;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeNode;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用配置服务
 *
 * @author ruanyu
 */
public class ApplicationServiceImpl implements ApplicationService {

  private static final String CACHE_KEY_ID_PREFIX = "x3platform:apps:application:id:";

  private static final String CACHE_KEY_NAME_PREFIX = "x3platform:apps:application:name:";

  private static final String CACHE_KEY_AUTH_PREFIX = "x3platform:apps:application:authority:";

  private static final String CACHE_KEY_TREEVIEW_PREFIX = "x3platform:apps:application:treeview:";

  private static final String DATA_SCOPE_TABLE_NAME = "application_scope";

  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMapper provider = null;

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(Application item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item);
    }

    if (!StringUtil.isNullOrEmpty(item.getApplicationName())) {
      String key = CACHE_KEY_NAME_PREFIX + item.getApplicationName();
      CachingManager.set(key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(Application item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }

      // 删除权限信息
      key = CACHE_KEY_AUTH_PREFIX + item.getId();
      CachingManager.deleteByPattern(key + ":*");
    }

    if (!StringUtil.isNullOrEmpty(item.getApplicationName())) {
      String key = CACHE_KEY_NAME_PREFIX + item.getApplicationName();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }
  }

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 {@link Application} 详细信息
   * @return {@link Application} 实例详细信息
   */
  @Override
  public int save(Application entity) {
    int affectedRows;
    String id = entity.getId();
    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }
    if (provider.selectByPrimaryKey(id) == null) {
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
   * @param id 实例的标识信息
   */
  @Override
  public int delete(String id) {
    Application entity = provider.selectByPrimaryKey(id);

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
   * @param id Application Id号
   * @return 返回一个 Application 实例的详细信息
   */
  @Override
  public Application findOne(String id) {
    Application entity = null;

    // 根节点快速处理
    if (APPLICATION_ROOT_ID.equals(id)) {
      return null;
    }

    String key = CACHE_KEY_ID_PREFIX + id;

    if (CachingManager.contains(key)) {
      entity = (Application) CachingManager.get(key);
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
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 Application 实例的详细信息
   */
  @Override
  public Application findOneByApplicationName(String applicationName) {
    Application entity = null;

    String key = CACHE_KEY_NAME_PREFIX + applicationName;

    if (CachingManager.contains(key)) {
      entity = (Application) CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (entity == null) {
      entity = provider.findOneByApplicationName(applicationName);

      if (entity != null) {
        addCacheItem(entity);
      }
    }

    return entity;
  }

  /**
   * 查询某条记录
   *
   * @param applicationKey 应用key
   * @return 返回一个 applicationKey 实例的详细信息
   */
  @Override
  public Application findOneByApplicationKey(String applicationKey) {
    return provider.findOneByApplicationKey(applicationKey);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 Application 实例的详细信息
   */
  @Override
  public List<Application> findAll() {
    return provider.findAll(new HashMap(16));
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 Application 实例的详细信息
   */
  @Override
  public List<Application> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  @Override
  public List<ApplicationLite> findAllLites(DataQuery query) {
    return provider.findAllLites(query.getMap());
  }

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息. 根据账号，判断是否为EveryOne,然后查询Role，然后查询人
   *
   * @param accountId 帐号标识
   * @return 返回所有{@link Application}实例的详细信息
   */
  @Override
  public List<Application> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 返回所有 {@link Application} 实例的详细信息
   */
  @Override
  public List<Application> findAllByRoleIds(String roleIds) {
    return provider.findAllByRoleIds(roleIds);
  }

  /**
   * 根据父节点标识获取可用的树形节点数据
   *
   * @param parentId 父级节点标识
   * @return 所有 {@link Application} 实例的详细信息
   */
  @Override
  public List<Application> findTreeNodesByParentId(String parentId) {
    return provider.findTreeNodesByParentId(parentId);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务编码
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param applicationName 应用名称
   * @return 布尔值
   */
  @Override
  public boolean isExistApplicationName(String applicationName) {
    return provider.isExistApplicationName(applicationName);
  }

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param account 帐号
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(String entityId, String authorityName, Account account) {
    boolean result = false;

    Object cacheFlag = null;

    String key = StringUtil.format("{}:{}:{}:{}", CACHE_KEY_AUTH_PREFIX, entityId, authorityName, account.getId());

    if (CachingManager.contains(key)) {
      cacheFlag = CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (cacheFlag == null) {
      result = MembershipManagement.getInstance().getAuthorizationObjectService().hasAuthority(
        DATA_SCOPE_TABLE_NAME,
        entityId,
        KernelContext.parseObjectType(Application.class),
        authorityName,
        account);

      CachingManager.set(key, result);
    }

    return result;
  }

  /**
   * 配置应用的权限信息
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
      KernelContext.parseObjectType(Application.class),
      authorityName,
      scopeText);

    // [重点] 设置完管理员后, 重置缓存数据.
    Application application = findOne(entityId);
    if (application != null) {
      AppsSecurity.resetApplicationAdministrators(application.getApplicationName());
    }
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
      KernelContext.parseObjectType(Application.class),
      authorityName);
  }

  /**
   * 判断用户是否是应用的默认管理员 和  内置超级管理员
   *
   * @param account 帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isAdministrator(Account account, String applicationId) {
    if (account == null) {
      return false;
    }

    String loginName = account.getLoginName();

    String administrators = AppsConfigurationView.getInstance().getAdministrators();

    administrators = "[" + administrators.replace(",", "],[") + "]";

    // 如果为内置超级管理员则返回 True。
    return administrators.contains(loginName) || hasAuthority(applicationId, "应用_默认_管理员", account);
  }

  /**
   * 判断用户是否是应用的默认审查员
   *
   * @param account 帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isReviewer(Account account, String applicationId) {
    return isAdministrator(account, applicationId) || hasAuthority(applicationId, "应用_默认_审查员", account);
  }

  /**
   * 判断用户是否是应用的默认可访问成员
   *
   * @param account 帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isMember(Account account, String applicationId) {
    return isReviewer(account, applicationId) || hasAuthority(applicationId, "应用_默认_可访问成员", account);
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
    List<Application> list = provider.findTreeNodesByParentId(parentId);

    List<TreeNode> treeNodes = new ArrayList<>();

    for (Application item : list) {
      TreeNode treeNode = new TreeNode(item.getId(), item.getParentId(), item.getApplicationDisplayName(),
        item.getDescription(), commandFormat);

      List<TreeNode> childNodes = getTreeNodes(item.getId(), commandFormat);

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

    parentId = (StringUtil.isNullOrEmpty(parentId) || "0".equals(parentId)) ? treeViewRootTreeNodeId : parentId;

    List<Application> list = provider.findTreeNodesByParentId(parentId);

    DynamicTreeView treeView = new DynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId, commandFormat);

    for (Application item : list) {
      DynamicTreeNode treeNode = new DynamicTreeNode(item.getId(), item.getParentId(), item.getApplicationDisplayName(),
        item.getDescription(), commandFormat, true);

      treeView.add(treeNode);
    }

    return treeView;
  }
}
