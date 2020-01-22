package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import java.util.List;
import java.util.Map;

/**
 * 应用功能服务器接口
 *
 * @author ruanyu
 */
public interface ApplicationFeatureService {

  /**
   * 保存记录
   *
   * @param entity {@link ApplicationFeature} 实例的详细信息
   * @return {@link ApplicationFeature} 实例的详细信息
   */
  public int save(ApplicationFeature entity);


  /**
   * 查询
   */
  public List<ApplicationFeature> findAll(DataQuery dataQuery);

  /**
   * @param menuId 根据菜单Id
   * @param roleId 角色Id
   * @param applicationId 应用id
   * @return 根据菜单 id 和 角色 id 进行查询 问题
   */
  // public Map<String, List<ApplicationFeature>> findAllByMenu(String menuId, String roleId, String applicationId);

  /**
   * 删除记录
   *
   * @param id 实例的标识信息
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ApplicationFeature Id号
   * @return 返回一个 ApplicationFeature 实例的详细信息
   */
  ApplicationFeature findOne(String id);

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param accountId 帐号标识
   * @return 返回所有 ApplicationFeature 实例的详细信息
   */
  List<ApplicationFeature> findAllByAccountId(String accountId);

  /**
   * 根据应用标识查询所有可用的树节点信息
   *
   * @param applicationId 应用标识
   */
  List<ApplicationFeature> findTreeNodesByApplicationId(String applicationId);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父级对象标识
   */
  List<ApplicationFeature> findTreeNodesByParentId(String parentId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 应用标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 应用名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 获取当前用户允许的功能列表
   *
   * @return 允许的功能列表
   */
  List<String> getAllowedFeatures();

  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  /**
   * 判断用户是否拥有实体对象的权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param account 帐号信息
   * @return 布尔值
   */
  boolean hasAuthority(String entityId, String authorityName, Account account);

  /**
   * 配置应用的权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  void bindAuthorizationScopeByEntityId(String entityId, String authorityName, String scopeText);

  /**
   * 配置授权对象的权限信息
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识 多个对象以逗号隔开
   */
  void bindAuthorizationScopeByAuthorizationObjectIds(String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds);

  // -------------------------------------------------------
  // 树形视图
  // -------------------------------------------------------

  /**
   * 获取树形数据信息
   *
   * @param treeViewName 树形视图名称
   * @param treeViewRootTreeNodeId 树形视图根节点标识
   * @param commandFormat 命令格式
   * @return {@link TreeView} 对象
   */
  TreeView getTreeView(String treeViewName, String treeViewRootTreeNodeId, String commandFormat);

  /**
   * 根据父级节点标识动态获取下一层级的数据信息
   *
   * @param treeViewName 树形视图名称
   * @param treeViewRootTreeNodeId 树形视图根节点标识
   * @param parentId 父级节点标识
   * @param commandFormat 命令格式
   * @return {@link DynamicTreeView} 对象
   */
  DynamicTreeView getDynamicTreeView(String treeViewName, String treeViewRootTreeNodeId, String parentId,
    String commandFormat);
}
