package com.x3platform.apps.services;

import java.util.*;

import com.x3platform.apps.models.ApplicationInfo;
import com.x3platform.membership.IAccountInfo;

/**
 */
public interface IApplicationService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例<see cref="ApplicationInfo"/>详细信息
   * @return ApplicationInfo 实例详细信息
   */
  ApplicationInfo save(ApplicationInfo param);

  /**
   * 删除记录
   *
   * @param id 实例的标识信息
   */
  void delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ApplicationInfo Id号
   * @return 返回一个 ApplicationInfo 实例的详细信息
   */
  ApplicationInfo findOne(String id);

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 ApplicationInfo 实例的详细信息
   */
  ApplicationInfo findOneByApplicationName(String applicationName);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 ApplicationInfo 实例的详细信息
   */
  List<ApplicationInfo> findAll();

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有 ApplicationInfo 实例的详细信息
   */
  List<ApplicationInfo> findAll(String whereClause);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有<see cref="ApplicationInfo"/>实例的详细信息
   */
  List<ApplicationInfo> findAll(String whereClause, int length);

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param accountId 帐号标识
   * @return 返回所有<see cref="ApplicationInfo"/>实例的详细信息
   */
  List<ApplicationInfo> findAllByAccountId(String accountId);

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 返回所有<see cref="ApplicationInfo"/>实例的详细信息
   */
  List<ApplicationInfo> findAllByRoleIds(String roleIds);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   记录行数
   * @return 返回一个列表<see cref="ApplicationInfo"/>
   */
  // List<ApplicationInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

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
   * 判断用户是否拥应用有权限信息
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  boolean hasAuthority(IAccountInfo account, String applicationId, String authorityName);

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param accountId     帐号标识
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  boolean hasAuthority(String accountId, String applicationId, String authorityName);

  /**
   * 配置应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  void bindAuthorizationScopeObjects(String applicationId, String authorityName, String scopeText);

  /**
   * 查询应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return
   */
  // List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String applicationId, String authorityName);

  /**
   * 判断用户是否是应用的默认管理员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isAdministrator(IAccountInfo account, String applicationId);

  /**
   * 判断用户是否是应用的默认审查员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isReviewer(IAccountInfo account, String applicationId);

  /**
   * 判断用户是否是应用的默认可访问成员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isMember(IAccountInfo account, String applicationId);
}
