package com.x3platform.membership.services;

import com.x3platform.membership.StandardOrganizationUnit;
import com.x3platform.membership.StandardRole;

import com.x3platform.data.DataQuery;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface StandardRoleService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link StandardRole} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(StandardRole entity);

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
   * @return {@link StandardRole} 实例的详细信息
   */
  StandardRole findOne(String id);


  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link StandardRole} 实例的详细信息
   */
  List<StandardRole> findAll(DataQuery query);

  /**
   * 根据标准组织单元标识查询所有相关记录
   *
   * @param standardOrganizationUnitId 标准组织单元节点标识
   * @return 所有 {@link StandardRole} 实例的详细信息
   */
  List<StandardRole> findAllByStandardOrganizationUnitId(String standardOrganizationUnitId);

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
  boolean isExistName(String name);
}
