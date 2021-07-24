package com.x3platform.tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.x3platform.util.*;

/**
 * 动态加载的树节点
 *
 * @author ruanyu
 */
public class DynamicTreeNode {
  
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
  
  private String parentId;
  
  /**
   * 父级节点标识
   */
  public String getParentId() {
    return parentId;
  }
  
  public void setParentId(String value) {
    parentId = value;
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
   * 描述
   */
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String value) {
    description = value;
  }
  
  private String target = "_self";
  
  /**
   *
   */
  public String getTarget() {
    return this.target;
  }
  
  public void setTarget(String value) {
    this.target = value;
  }
  
  private boolean hasChildren = true;
  
  /**
   * 是否有叶子节点
   */
  public boolean getHasChildren() {
    return hasChildren;
  }
  
  public void setHasChildren(boolean value) {
    hasChildren = value;
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
  
  public DynamicTreeNode() {
  }
  
  public DynamicTreeNode(String id, String parentId, String name, String description, String commadFormat, boolean hasChildren) {
    this(id, parentId, name, description, commadFormat, hasChildren, 0);
  }
  
  public DynamicTreeNode(String id, String parentId, String name, String description, String commadFormat, boolean hasChildren, int disabled) {
    this.setId(id);
    this.setParentId(parentId);
    this.setName(name);
    this.setDescription(description);
    this.setHasChildren(hasChildren);
    this.setCommand(commadFormat);
    this.setDisabled(disabled);
  }
  
  /**
   * 返回表示当前对象的字符串
   *
   * @return
   */
  @Override
  public String toString() {
    if (StringUtil.isNullOrEmpty(this.getDescription())) {
      this.setDescription(this.getName());
    }
    
    if (this.getHasChildren() && this.getDisabled() == 1) {
      this.setCommand(String.format("javascript:void(0)"));
    } else {
      String text = "javascript:";
      if (this.getCommand().toLowerCase().indexOf(text) == 0) {
        this.setCommand(this.getCommand()
          .replace("{treeNodeId}", this.getId().replace("$", "\\\\"))
          .replace("{treeNodeParentId}", this.getParentId().replace("$", "\\\\"))
          .replace("{treeNodeName}", this.getName().replace("\\", "\\\\").replace("$", "\\\\"))
          .replace("{treeNodeToken}", this.getToken().replace("$", "\\\\"))
          .replace("{treeNodeCategoryIndex}", this.getCategoryIndex().replace("\\", "\\\\").replace("$", "\\\\")));
      } else {
        this.setCommand(this.getCommand()
          .replace("{treeNodeId}", this.getId())
          .replace("{treeNodeParentId}", this.getParentId())
          .replace("{treeNodeName}", this.getName())
          .replace("{treeNodeToken}", this.getToken())
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
