package com.x3platform.membership.services;

import com.x3platform.membership.User;

import com.x3platform.data.DataQuery;

import java.util.*;

/**
 * @author ruanyu
 */
public interface UserService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link User} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(User entity);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link User} 实例的详细信息
   */
  User findOne(String id);


  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link User} 实例的详细信息
   */
  List<User> findAll(DataQuery query);

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
   * 查询是否存在相关的记录
   *
   * @param accountId 标识
   * @return 用户信息
   */
  User createEmptyUser(String accountId);

  /**
   * 设置默认公司和部门
   *
   * @param accountId 帐号标识
   * @param organizationUnitIds 组织单位标识，[0]公司标识，[1]一级部门标识，[2]二级部门标识，[3]三级部门标识。
   * @return 消息代码
   */
  int setDefaultCorporationAndDepartments(String accountId, String organizationUnitIds);

  /**
   * 设置默认组织单位
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 帐号标识
   * @return 消息代码
   */
  int setDefaultOrganizationUnit(String accountId, String organizationUnitId);

  /**
   * 设置默认角色
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   * @return 消息代码
   */
  int setDefaultRole(String accountId, String roleId);
}
