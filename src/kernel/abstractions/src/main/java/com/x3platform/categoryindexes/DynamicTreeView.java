package com.x3platform.categoryindexes;

import java.util.*;

import com.x3platform.util.StringUtil;

/**
 * 异步加载的树
 *
 * @author ruanyu
 */
public class DynamicTreeView {
  private String mTreeName;

  /**
   */
  public final String getTreeName() {
    return mTreeName;
  }

  public final void setTreeName(String value) {
    mTreeName = value;
  }

  private String mParentId;

  /**
   */
  public final String getParentId() {
    return mParentId;
  }

  public final void setParentId(String value) {
    mParentId = value;
  }

  private List<DynamicTreeNode> nodes;

  /**
   * @param treeName
   * @param parentId
   */
  public DynamicTreeView(String treeName, String parentId) {
    this.mTreeName = treeName;

    this.mParentId = parentId;

    this.nodes = new ArrayList<DynamicTreeNode>();
  }

  /**
   * 新增树的节点
   *
   * @param node
   */
  public final void add(DynamicTreeNode node) {
    this.nodes.add(node);
  }

  /**
   * 返回表示当前对象的字符串
   *
   * @return
   */
  @Override
  public String toString() {
    StringBuilder outString = new StringBuilder();

    outString.append("{\"tree\":\"" + this.getTreeName() + "\",\"parentId\":\"" + this.getParentId() + "\",");

    outString.append("childNodes:[");

    for (DynamicTreeNode node : nodes) {
      outString.append(node.toString() + ",");
    }

    StringUtil.trimEnd(outString, ",");

    outString.append("]}");

    return outString.toString();
  }
}
