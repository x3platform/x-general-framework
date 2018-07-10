package com.x3platform.categoryIndexes;

import java.util.*;
import com.x3platform.util.StringUtil;

/** 异步加载的树
 */
public class DynamicTreeView
{
  private String m_TreeName;

  /**
   */
  public final String getTreeName()
  {
    return m_TreeName;
  }
  public final void setTreeName(String value)
  {
    m_TreeName = value;
  }

  private String m_ParentId;

  /**
   */
  public final String getParentId()
  {
    return m_ParentId;
  }
  public final void setParentId(String value)
  {
    m_ParentId = value;
  }

  private List<DynamicTreeNode> nodes;

  /**
   @param treeName
   @param parentId
   */
  public DynamicTreeView(String treeName, String parentId)
  {
    this.m_TreeName = treeName;

    this.m_ParentId = parentId;

    this.nodes = new ArrayList<DynamicTreeNode>();
  }

  /** 新增树的节点
   @param node
   */
  public final void Add(DynamicTreeNode node)
  {
    this.nodes.add(node);
  }

  ///#region 函数:ToString()
  /** 返回表示当前对象的字符串
   @return
   */
  @Override
  public String toString()
  {
    StringBuilder outString = new StringBuilder();

    outString.append("{\"tree\":\"" + this.getTreeName() + "\",\"parentId\":\"" + this.getParentId() + "\",");

    outString.append("childNodes:[");

    for (DynamicTreeNode node : nodes)
    {
      outString.append(node.toString() + ",");
    }

    if (StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(","))
    {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    outString.append("]}");

    return outString.toString();
  }
  ///#endregion
}
