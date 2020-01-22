package com.x3platform.membership.mappers;

import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.StandardOrganizationUnit;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface StandardOrganizationUnitMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link StandardOrganizationUnit} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int insert(StandardOrganizationUnit record);

  /**
   * 修改记录
   *
   * @param record {@link StandardOrganizationUnit} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int updateByPrimaryKey(StandardOrganizationUnit record);

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
   * @return 一个 {@link StandardOrganizationUnit} 实例的详细信息
   */
  StandardOrganizationUnit selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link StandardOrganizationUnit} 实例的详细信息
   */
  List<StandardOrganizationUnit> findAll(Map params);

  /**
   * 根据父节点标识查询所有相关记录
   *
   * @param parentId 父节点标识
   * @return 所有 {@link StandardOrganizationUnit} 实例的详细信息
   */
  List<StandardOrganizationUnit> findAllByParentId(@Param("parent_id") String parentId);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @return 所有实例的详细信息
   */
  List<StandardOrganizationUnit> findTreeNodesByParentId(@Param("parent_id") String parentId);

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

  /**
   * 查询是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(@Param("global_name") String globalName);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 组织标识
   * @param name 组织名称
   * @return 0-代表成功 1-代表已存在相同名称
   */
  int rename(@Param("id") String id,@Param("name") String name);

  /**
   * 设置全局名称
   *
   * @param id 组织标识
   * @param globalName 全局名称
   * @return 修改成功,  0, 修改失败,  1.
   */
  int setGlobalName(@Param("id") String id,@Param("global_name")  String globalName);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 组织标识
   * @param parentId 父级组织标识
   * @return 修改成功,  0, 修改失败,  1.
   */
  int setParentId(@Param("id")  String id,@Param("parent_id")  String parentId);
}
