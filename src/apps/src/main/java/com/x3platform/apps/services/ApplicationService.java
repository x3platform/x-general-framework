package com.x3platform.apps.services;

import com.x3platform.AuthorizationScope;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationLite;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import java.util.List;

/**
 */
public interface ApplicationService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 Application 详细信息
   * @return {@link Application} 实例详细信息
   */
  int save(Application param);

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
   * @param id Application Id号
   * @return 返回一个 {@link Application} 实例的详细信息
   */
  Application findOne(String id);

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 {@link Application} 实例的详细信息
   */
  Application findOneByApplicationName(String applicationName);


  /**
   * 查询某条记录
   *
   * @param applicationKey applicationName
   * @return 返回一个 {@link Application} 实例的详细信息
   */
  Application findOneByApplicationKey(String applicationKey);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 {@link Application} 实例的详细信息
   */
  List<Application> findAll();

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 {@link Application} 实例的详细信息
   */
  List<Application> findAll(DataQuery query);

  /**
   * 查询所有相关记录 未带查询参数情况
   *
   * @param query 数据查询参数
   * @return 所有 {@link ApplicationLite} 实例的详细信息
   */
  List<ApplicationLite> findAllLites(DataQuery query);

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param accountId 帐号标识
   * @return 返回所有 {@link Application} 实例的详细信息
   */
  List<Application> findAllByAccountId(String accountId);

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 所有 {@link Application} 实例的详细信息
   */
  List<Application> findAllByRoleIds(String roleIds);

  /**
   * 根据父节点标识获取可用的树形节点数据
   *
   * @param parentId 父级节点标识
   * @return 所有 {@link Application} 实例的详细信息
   */
  List<Application> findTreeNodesByParentId(String parentId);

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
   * @param applicationName 应用名称
   * @return 布尔值
   */
  boolean isExistApplicationName(String applicationName);

  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param account 帐号
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
   * 查询应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return 授权范围列表
   */
  List<AuthorizationScope> getAuthorizationScopes(String applicationId, String authorityName);

  /**
   * 判断用户是否是应用的默认管理员
   *
   * @param account 帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isAdministrator(Account account, String applicationId);

  /**
   * 判断用户是否是应用的默认审查员
   *
   * @param account 帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isReviewer(Account account, String applicationId);

  /**
   * 判断用户是否是应用的默认可访问成员
   *
   * @param account 帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isMember(Account account, String applicationId);

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
  DynamicTreeView getDynamicTreeView(String treeViewName, String treeViewRootTreeNodeId,
    String parentId, String commandFormat);
}
