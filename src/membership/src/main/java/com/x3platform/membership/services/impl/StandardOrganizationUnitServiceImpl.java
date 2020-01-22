package com.x3platform.membership.services.impl;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.membership.*;
import com.x3platform.membership.services.*;
import com.x3platform.membership.mappers.*;

import com.x3platform.KernelContext;
import com.x3platform.data.*;
import com.x3platform.tree.DynamicTreeNode;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeNode;
import com.x3platform.tree.TreeView;
import com.x3platform.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author ruanyu
 */
public class StandardOrganizationUnitServiceImpl implements StandardOrganizationUnitService {

  private static String CACHE_KEY_ID_PREFIX = "x3platform:membership:standard-organization-unit:id:";

  private static String CACHE_KEY_TREEVIEW_PREFIX = "x3platform:membership:standard-organization-unit:treeview:";

  private static String DIGITAL_NUMBER_KEY_CODE = "Table_StandardOrganizationUnit_Key_Code";

  /**
   * 数据提供器
   */
  @Autowired
  private StandardOrganizationUnitMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link StandardOrganizationUnit} 实例的详细信息
   * @return {@link StandardOrganizationUnit} 实例的详细信息
   */
  @Override
  public int save(StandardOrganizationUnit entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (this.provider.selectByPrimaryKey(id) == null) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }

      affectedRows = this.provider.insert(entity);
    } else {
      affectedRows = this.provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'" + id + "', affectedRows:" + affectedRows);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    int affectedRows = this.provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'" + id + "', affectedRows:" + affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link StandardOrganizationUnit} 实例的详细信息
   */
  @Override
  public StandardOrganizationUnit findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 根据父节点标识查询所有相关记录
   *
   * @param parentId 父节点标识
   * @return 所有相关 {@link StandardOrganizationUnit} 实例的详细信息
   */
  @Override
  public List<StandardOrganizationUnit> findAllByParentId(String parentId) {
    return this.provider.findAllByParentId(parentId);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link StandardOrganizationUnit} 实例的详细信息
   */
  @Override
  public List<StandardOrganizationUnit> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：boolean isExist(String id)。");
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param name 名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  // -------------------------------------------------------
  // 树形视图
  // -------------------------------------------------------

  @Override
  public TreeView getTreeView(String treeViewName, String treeViewRootTreeNodeId, String commandFormat) {
    String key = CACHE_KEY_TREEVIEW_PREFIX + treeViewRootTreeNodeId;

    // 读取缓存信息
    if (CachingManager.contains(key)) {
      return (TreeView) CachingManager.get(key);
    }

    TreeView treeView = new TreeView(treeViewName, treeViewRootTreeNodeId, commandFormat);

    List<TreeNode> childNodes = getTreeNodes(treeViewRootTreeNodeId, commandFormat);

    if (!childNodes.isEmpty()) {
      treeView.add(childNodes);
      // 设置缓存信息
      CachingManager.set(key, treeView);
    }

    return treeView;
  }

  private List<TreeNode> getTreeNodes(String parentId, String commandFormat) {
    List<StandardOrganizationUnit> list = provider.findTreeNodesByParentId(parentId);

    List<TreeNode> treeNodes = new ArrayList<TreeNode>();

    for (StandardOrganizationUnit item : list) {
      TreeNode treeNode = new TreeNode(item.getId(), item.getParentId(), item.getName(), item.getName(), commandFormat);

      List<TreeNode> childNodes = getTreeNodes(item.getId(), commandFormat);

      if (!childNodes.isEmpty()) {
        treeNode.add(childNodes);
      }

      treeNodes.add(treeNode);
    }

    return treeNodes;
  }

  @Override
  public DynamicTreeView getDynamicTreeView(String treeViewName, String treeViewRootTreeNodeId, String parentId,
    String commandFormat) {

    parentId = (StringUtil.isNullOrEmpty(parentId) || "0".equals(parentId)) ? treeViewRootTreeNodeId : parentId;

    List<StandardOrganizationUnit> list = provider.findTreeNodesByParentId(parentId);

    DynamicTreeView treeView = new DynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId, commandFormat);

    for (StandardOrganizationUnit item : list) {
      DynamicTreeNode treeNode = new DynamicTreeNode(item.getId(), item.getParentId(), item.getName(),
        item.getGlobalName(), commandFormat, true);

      treeView.add(treeNode);
    }

    return treeView;
  }
}
