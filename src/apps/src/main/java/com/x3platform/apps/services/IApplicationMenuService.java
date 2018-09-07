package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationMenuInfo;
import com.x3platform.apps.models.ApplicationMenuQueryInfo;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.IAccountInfo;

import java.util.*;

/**
 */
public interface IApplicationMenuService {
  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 ApplicationMenuInfo 详细信息
   * @return 实例 ApplicationMenuInfo 详细信息
   */
  int save(ApplicationMenuInfo param);

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
   * @return 返回实例ApplicationMenuInfo的详细信息
   */
  ApplicationMenuInfo findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例ApplicationMenuInfo的详细信息
   */
  List<ApplicationMenuInfo> findAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例ApplicationMenuInfo的详细信息
   */
  List<ApplicationMenuQueryInfo> findAllQueryObject(DataQuery query);

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
  String combineFullPath(ApplicationMenuInfo param);

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
  boolean hasAuthority(String entityId, String authorityName, IAccountInfo account);

  /**
   * 绑定实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  void bindAuthorizationScopeObjects(String entityId, String authorityName, String scopeText);

  /**
   * 查询实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @return
   */
  // List<MembershipAuthorizationScopeObject> getAuthorizationScopeObjects(String entityId, String authorityName);
}
