package com.x3platform.tree;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.*;

import com.x3platform.util.StringUtil;

/**
 * 动态加载的树视图
 *
 * @author ruanyu
 */
public class DynamicTreeView {
  private String name;

  /**
   */
  public  String getName() {
    return name;
  }

  public  void setName(String value) {
    name = value;
  }

  private String rootTreeNodeId;

  /**
   * 根节点标识
   */
  public String getRootTreeNodeId() {
    return rootTreeNodeId;
  }

  public void setRootTreeNodeId(String value) {
    rootTreeNodeId = value;
  }

  private String parentId;

  /**
   */
  public  String getParentId() {
    return parentId;
  }

  public  void setParentId(String value) {
    parentId = value;
  }

  @JSONField(serialize = false)
  private String commandFormat;

  /**
   * 命令格式
   */
  public String getCommadFormat() {
    return commandFormat;
  }

  public void setCommadFormat(String value) {
    commandFormat = value;
  }

  private List<DynamicTreeNode> childNodes= new ArrayList<DynamicTreeNode>();

  /**
   * 子节点信息
   */
  public List<DynamicTreeNode> getChildNodes() {
    return childNodes;
  }

  /**
   * 构造函数
   *
   * @param name 树名称
   * @param rootTreeNodeId 根节点标识
   * @param commandFormat 命令输出格式化
   */
  public DynamicTreeView(String name, String rootTreeNodeId,String parentId, String commandFormat) {
    this.name = name;
    this.rootTreeNodeId = rootTreeNodeId;
    this.parentId = parentId;
    this.commandFormat = commandFormat;
  }

  /**
   * 新增树的节点
   *
   * @param node
   */
  public  void add(DynamicTreeNode node) {
    this.childNodes.add(node);
  }
}
