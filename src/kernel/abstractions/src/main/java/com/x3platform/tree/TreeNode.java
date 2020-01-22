package com.x3platform.tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 树节点
 *
 * @author ruanyu
 */
public class TreeNode {

  private String id;

  /**
   * 节点标识
   */
  public String getId() {
    return id;
  }

  public void setId(String value) {
    id = value;
  }

  private String mParentId;

  /**
   * 父级节点标识
   */
  public String getParentId() {
    return mParentId;
  }

  public void setParentId(String value) {
    mParentId = value;
  }

  private String name;

  /**
   * 名称
   */
  public String getName() {
    return name;
  }

  public void setName(String value) {
    name = value;
  }

  private String token;

  /**
   * 标记
   */
  public String getToken() {
    return token;
  }

  public void setToken(String value) {
    token = value;
  }

  private String categoryIndex;

  /**
   * 分类索引
   */
  public String getCategoryIndex() {
    return categoryIndex;
  }

  public void setCategoryIndex(String value) {
    categoryIndex = value;
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

  private String command;

  /**
   * 命令 可以是链接地址，也可能是脚本函数。
   */
  public String getCommand() {
    return command;
  }

  public void setCommand(String value) {
    command = value;
  }

  private String description;

  /**
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    description = value;
  }

  private String target = "_self";

  /**
   */
  public String getTarget() {
    return this.target;
  }

  public void setTarget(String value) {
    this.target = value;
  }

  private List<TreeNode> childNodes = new ArrayList<TreeNode>();

  /**
   * 子节点信息
   */
  public List<TreeNode> getChildNodes() {
    return childNodes;
  }

  /**
   * 是否有叶子节点
   */
  public boolean getHasChildren() {
    return childNodes.isEmpty() ? false : true;
  }

  private int disabled;

  /**
   * 禁止
   */
  public int getDisabled() {
    return disabled;
  }

  public void setDisabled(int value) {
    disabled = value;
  }

  public TreeNode() {
  }

  public TreeNode(String id, String parentId, String name, String description, String commadFormat) {
    this(id, parentId, name, description, commadFormat, 0);
  }

  public TreeNode(String id, String parentId, String name, String description, String commadFormat, int disabled) {
    this.setId(id);
    this.setParentId(parentId);
    this.setName(name);
    this.setDescription(description);
    this.setDisabled(0);
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

  /**
   * 格式化为 JSON 格式字符串
   */
  @Override
  public String toString() {
    if (StringUtil.isNullOrEmpty(this.getDescription())) {
      this.setDescription(this.getName());
    }

    if (this.getHasChildren() && this.getDisabled() == 1) {
      String text = "javascript:";
      this.setCommand(String.format("javascript:void(0)"));
    } else {
      String text = "javascript:";
      if (this.getCommand().toLowerCase().indexOf(text) == 0) {
        this.setCommand(this.getCommand().replace("{treeNodeId}", this.getId().replace("$", "\\\\"))
          .replace("{treeNodeParentId}", this.getParentId().replace("$", "\\\\"))
          .replace("{treeNodeName}", this.getName().replace("\\", "\\\\").replace("$", "\\\\"))
          .replace("{treeNodeToken}", this.getToken().replace("$", "\\\\"))
          .replace("{treeNodeCategoryIndex}", this.getCategoryIndex().replace("\\", "\\\\").replace("$", "\\\\")));
      } else {
        this.setCommand(
          this.getCommand().replace("{treeNodeId}", this.getId()).replace("{treeNodeParentId}", this.getParentId())
            .replace("{treeNodeName}", this.getName()).replace("{treeNodeToken}", this.getToken())
            .replace("{treeNodeCategoryIndex}", this.getCategoryIndex()));
      }
    }

    StringBuilder outString = new StringBuilder();

    outString.append("{");
    outString.append("\"id\":\"" + StringUtil.toSafeJson(this.getId()) + "\",");
    outString.append("\"parentId\":\"" + StringUtil.toSafeJson(this.getParentId()) + "\",");
    outString.append("\"name\":\"" + StringUtil.toSafeJson(this.getName()) + "\",");
    outString.append("\"description\":\"" + StringUtil.toSafeJson(this.getDescription()) + "\",");
    outString.append("\"url\":\"" + StringUtil.toSafeJson(this.getCommand()) + "\",");
    outString.append("\"target\":\"" + StringUtil.toSafeJson(this.getTarget()) + "\",");
    outString.append("\"hasChildren\":\"" + String.valueOf(this.getHasChildren()).toLowerCase() + "\" ");
    outString.append("}");

    return outString.toString();
  }
}
