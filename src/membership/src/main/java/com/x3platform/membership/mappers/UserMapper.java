package com.x3platform.membership.mappers;

import com.x3platform.membership.User;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface UserMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link User} 实例的详细信息
   * @return 受影响的行数
   */
  int insert(User record);

  /**
   * 修改记录
   *
   * @param record {@link User} 实例的详细信息
   * @return 受影响的行数
   */
  int updateByPrimaryKey(User record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 受影响的行数
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link User} 实例的详细信息
   */
  User selectByPrimaryKey(String id);
  
  /**
   * 根据登录名查询某条记录
   *
   * @param loginName 登录名
   * @return 一个 {@link User} 实例的详细信息
   */
  User findOneByLoginName(String loginName);
  
  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link User} 实例的详细信息
   */
  List<User> findAll(Map params);

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
   * 设置默认公司和部门
   *
   * @param accountId 帐号标识
   * @param corporationId 公司标识
   * @param departmentId 一级部门标识
   * @param department2Id 二级部门标识
   * @param department3Id 三级部门标识
   * @return 消息代码
   */
  int setDefaultCorporationAndDepartments(@Param("account_id") String accountId,
    @Param("corporation_id") String corporationId,
    @Param("department_id") String departmentId,
    @Param("department2_id") String department2Id,
    @Param("department3_id") String department3Id);

  /**
   * 设置默认组织单位
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 帐号标识
   * @return 消息代码
   */
  int setDefaultOrganizationUnit(@Param("account_id") String accountId,
    @Param("organization_unit_id") String organizationUnitId);

  /**
   * 设置默认角色
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   * @return 消息代码
   */
  int setDefaultRole(@Param("account_id") String accountId, @Param("role_id") String roleId);
  
  /**
   * 设置帐号的默认岗位
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   * @return 受影响的行数
   */
  //
  
  /**
   * 设置帐号的默认岗位
   *
   * @param accountId 帐号标识
   * @return 受影响的行数
   */
  int setNullDefaultRelation(@Param("account_id") String accountId);
}
