package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import com.x3platform.apps.models.ApplicationMenuScopeInfo;
import com.x3platform.apps.models.ApplicationMenuViewInfo;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import java.util.List;

/**
 * 应用菜单服务器接口
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
   * @param param 实例 ApplicationMenu 详细信息
   * @return 实例 ApplicationMenu 详细信息
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
   * @return 返回实例ApplicationMenu的详细信息
   */
  ApplicationMenu findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenu> findAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenuViewInfo> findApplicationMenuViewInfoAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenuLite> findAllQueryObject(DataQuery query);

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
   * @return
   */
  String combineFullPath(ApplicationMenu param);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param parentId      所属父级菜单标识
   * @param menuType      菜单类型
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenu> getMenusByParentId(String applicationId, String parentId, String menuType);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param fullPath      所属父级完整路径
   * @param menuType      菜单类型
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenu> getMenusByFullPath(String applicationId, String fullPath, String menuType);

  /**
   * 获取有效的菜单信息
   * @param applicationId 所属应用标识
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenu> getMenusByApplicationId(String applicationId);


  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  /**
   * 判断用户是否拥有实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param account       帐号信息
   * @return 布尔值
   */
  boolean hasAuthority(String entityId, String authorityName, Account account);

  /**
   * 绑定实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  void bindAuthorizationScopeObjects(String entityId, String authorityName, String scopeText);


  /**
   * 根据角色绑定菜单
   * @param roleId  角色id
   * @param bindScopeList 新增绑定
   * @param unBindScopeList 删除绑定
   */
  void bindAuthorizationScopeByRole(String roleId,List<String> bindScopeList,List<String> unBindScopeList);


  /**
   * 根据角色绑定菜单,物理全部新增 ， 全部删除 ；
   * @param roleId  角色id
   * @param bindScopeList 新增绑定
   */
  void bindMenuScopeByRole(String roleId,List<String> bindScopeList);



  /**
   * 查询实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @return
   */
  // List<MembershipAuthorizationScopeObject> getAuthorizationScopeObjects(String entityId, String authorityName);

  /**
   * @param value 根据 value 获取 当前应用范围
   */
  List<ApplicationMenuScopeInfo> getApplicationMenuScope(String value);


  /**
   * @param applicationId 所属应用
   * @param parentId 父级菜单
   * @param menuType 所属菜单
   * @param accountId
   * @return
   */
  List<ApplicationMenu> getMenusScopeByParentId(String applicationId,String parentId, String menuType,String accountId);

  /**
   * @param roleId 角色id
   * @param menuId 菜单id
   */
  boolean isExistMenusScopeByRoleIdAndMenuId(String roleId, String menuId);


  /**
   * @param id 角色
   * @return
   */
  List<String > getMenusScopeByRoleId(String id);


}
