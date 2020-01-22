package com.x3platform.membership.services;

import com.x3platform.membership.StandardOrganizationUnit;

import com.x3platform.data.DataQuery;

import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;
import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author ruanyu
 */
public interface StandardOrganizationUnitService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link StandardOrganizationUnit} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(StandardOrganizationUnit entity);

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
   * @return {@link StandardOrganizationUnit} 实例的详细信息
   */
  StandardOrganizationUnit findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link StandardOrganizationUnit} 实例的详细信息
   */
  List<StandardOrganizationUnit> findAll(DataQuery query);

  /**
   * 根据父节点标识查询所有相关记录
   *
   * @param parentId 父节点标识
   * @return 所有 {@link StandardOrganizationUnit} 实例的详细信息
   */
  List<StandardOrganizationUnit> findAllByParentId(String parentId);

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
  boolean isExistName( String name);

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
