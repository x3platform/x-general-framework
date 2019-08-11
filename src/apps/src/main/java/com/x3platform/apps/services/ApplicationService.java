package com.x3platform.apps.services;

import java.util.*;

import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationScopeInfo;
import com.x3platform.apps.models.ApplicationViewInfo;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;

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
   * @return Application 实例详细信息
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
   * @return 返回一个 Application 实例的详细信息
   */
  Application findOne(String id);

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 Application 实例的详细信息
   */
  Application findOneByApplicationName(String applicationName);


  /**
   * 查询某条记录
   *
   * @param applicationKey applicationName
   * @return 返回一个 Application 实例的详细信息
   */
  Application findOneByApplicationKey(String applicationKey);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 Application 实例的详细信息
   */
  List<Application> findAll();

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 Application 实例的详细信息
   */
  List<Application> findAll(DataQuery query);


  /**
   * 查询所有相关记录 未带查询参数情况
   * @param query 数据查询参数
   * @return 返回所有 Application 实例的详细信息
   */
  List<ApplicationViewInfo> findApplicationViewInfoAll(DataQuery query);


  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param accountId 帐号标识
   * @return 返回所有 Application 实例的详细信息
   */
  List<Application> findAllByAccountId(String accountId);

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 返回所有 Application 实例的详细信息
   */
  List<Application> findAllByRoleIds(String roleIds);

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
   * @return 返回一个列表 Application
   */
  // List<Application> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

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
  boolean hasAuthority(Account account, String applicationId, String authorityName);

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
   *
   * @param applicationName 应用名称
   * @param accountInfo 用户信息
   * @param authorityId 权限信息
   */
  void bindAuthorizationScopeObjectByAccount(String applicationName,Account accountInfo,String authorityId);

  /**
   * 配置应用的权限信息
   * @param applicationId 应用标识
   * @param list     权限范围的所有文本对象
   */
  void bindAuthorizationScopeObjects(String applicationId, List<ApplicationScopeInfo> list);

  /**
   * 查询应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return
   */
   //List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String applicationId, String authorityName);

  /**
   * 判断用户是否是应用的默认管理员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isAdministrator(Account account, String applicationId);

  /**
   * 判断用户是否是应用的默认审查员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isReviewer(Account account, String applicationId);

  /**
   * 判断当前应用是否为均可访问应用
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  public boolean isEveryOne(String applicationId);

  /**
   * 判断用户是否是应用的默认可访问成员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  boolean isMember(Account account, String applicationId);

  /**
   * 根据应用id 查询对应的管理员 或者 可访问者 或者 审核者
   * @param applicationId 应用id
   * @param applicationMembersType isAdministrator ; isReviewer ; isMember
   * @return
   */
  List<Account> findAllAccountByApplicationId(String applicationId,String applicationMembersType);
}
