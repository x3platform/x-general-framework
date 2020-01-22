package com.x3platform.membership.mappers;

import com.x3platform.membership.StandardRole;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface StandardRoleMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link StandardRole} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int insert(StandardRole record);

  /**
   * 修改记录
   *
   * @param record {@link StandardRole} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int updateByPrimaryKey(StandardRole record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0-成功
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link StandardRole} 实例的详细信息
   */
  StandardRole selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link StandardRole} 实例的详细信息
   */
  List<StandardRole> findAll(Map params);

  /**
   * 根据标准组织单元标识查询所有相关记录
   *
   * @param standardOrganizationUnitId 标准组织单元节点标识
   * @return 所有 {@link StandardRole} 实例的详细信息
   */
  List<StandardRole> findAllByStandardOrganizationUnitId(
    @Param("standard_organization_unit_id") String standardOrganizationUnitId);

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
   * @param name 名称
   * @return 布尔值
   */
  boolean isExistName(@Param("name") String name);
}
