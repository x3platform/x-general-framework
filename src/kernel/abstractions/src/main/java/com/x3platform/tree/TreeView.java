package com.x3platform.tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.SerializedJSON;
import java.util.*;

import com.x3platform.util.StringUtil;

/**
 * 树视图
 *
 * @author ruanyu
 */
public class TreeView implements SerializedJSON {

  private String name;

  /**
   * 树的名称
   */
  public String getName() {
    return name;
  }

  public void setName(String value) {
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

  /**
   */
  public  String getParentId() {
    return rootTreeNodeId;
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

  private List<TreeNode> childNodes = new ArrayList<TreeNode>();

  /**
   * 子节点信息
   */
  public List<TreeNode> getChildNodes() {
    return childNodes;
  }

  /**
   * 构造函数
   */
  public TreeView() {
  }

  /**
   * 构造函数
   *
   * @param name 树名称
   * @param rootTreeNodeId 根节点标识
   * @param commandFormat 命令输出格式化
   */
  public TreeView(String name, String rootTreeNodeId, String commandFormat) {
    this.name = name;
    this.rootTreeNodeId = rootTreeNodeId;
    this.commandFormat = commandFormat;
  }

  /**
   * 新增树的节点
   *
   * @param node 树节点
   */
  public void add(TreeNode node) {
    this.childNodes.add(node);
  }

  /**
   * 新增树的节点
   *
   * @param nodes 树节点
   */
  public void add(Collection<TreeNode> nodes) {
    this.childNodes.addAll(nodes);
  }

  @Override
  public String toJSON() {
    return null;
  }

  @Override
  public void fromJSON(String json) {

  }
}
