package com.x3platform.apps.services;

import com.x3platform.AuthorizationScope;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import java.util.List;

/**
 * 应用菜单服务接口
 *
 * @author ruanyu
 */
public interface ApplicationMenuService {
  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 {@link ApplicationMenu} 详细信息
   * @return 实例 {@link ApplicationMenu} 详细信息
   */
  int save(ApplicationMenu param);

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回实例 {@link ApplicationMenu} 的详细信息
   */
  ApplicationMenu findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 {@link ApplicationMenu} 的详细信息
   */
  List<ApplicationMenu> findAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 {@link ApplicationMenuLite} 的详细信息
   */
  List<ApplicationMenuLite> findAllLites(DataQuery query);

  /**
   * 根据应用标识查询所有可用的树节点信息
   *
   * @param applicationId 应用标识
   */
  List<ApplicationMenuLite> findTreeNodesByApplicationId(String applicationId, String menuType);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父级对象标识
   */
  List<ApplicationMenuLite> findTreeNodesByParentId(String parentId, String menuType);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 组合菜单全路径
   *
   * @param param 菜单信息
   * @return 全路径
   */
  String combineFullPath(ApplicationMenu param);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param parentId 所属父级菜单标识
   * @param menuType 菜单类型
   * @return 返回所有实例 {@link ApplicationMenu} 的详细信息
   */
  List<ApplicationMenu> getMenusByParentId(String applicationId, String parentId, String menuType);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param fullPath 所属父级完整路径
   * @param menuType 菜单类型
   * @return 返回所有实例 {@link ApplicationMenu} 的详细信息
   */
  List<ApplicationMenu> getMenusByFullPath(String applicationId, String fullPath, String menuType);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @return 返回所有实例 {@link ApplicationMenu} 的详细信息
   */
  List<ApplicationMenu> getMenusByApplicationId(String applicationId);

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
   * 绑定实体对象的权限信息
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

  /**
   * 查询实体对象的权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @returns
   */
  List<AuthorizationScope> getAuthorizationScopes(String entityId, String authorityName);

  /**
   * @param applicationId 所属应用
   * @param parentId 父级菜单
   * @param menuType 所属菜单
   */
//  List<ApplicationMenu> getMenusScopeByParentId(String applicationId, String parentId, String menuType,
//    String accountId);

  /**
   * @param roleId 角色id
   * @param menuId 菜单id
   */
  // boolean isExistMenusScopeByRoleIdAndMenuId(String roleId, String menuId);

  /**
   * @param id 角色
   */
  // List<String> getMenusScopeByRoleId(String id);

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
