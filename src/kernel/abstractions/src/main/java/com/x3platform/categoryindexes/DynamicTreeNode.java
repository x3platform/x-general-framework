package com.x3platform.categoryindexes;

import com.x3platform.util.*;

/**
 * 异步加载的树节点
 *
 * @author ruanyu
 */
public class DynamicTreeNode {

  private String mId;

  /**
   * 节点标识
   */
  public String getId() {
    return mId;
  }

  public void setId(String value) {
    mId = value;
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

  private String mName;

  /**
   * 名称
   */
  public String getName() {
    return mName;
  }

  public void setName(String value) {
    mName = value;
  }

  private String mToken;

  /**
   * 标记
   */
  public String getToken() {
    return mToken;
  }

  public void setToken(String value) {
    mToken = value;
  }

  private String mCategoryIndex;

  /**
   * 分类索引
   */
  public String getCategoryIndex() {
    return mCategoryIndex;
  }

  public void setCategoryIndex(String value) {
    mCategoryIndex = value;
  }

  private String mUrl;

  /**
   * 链接地址，也可能是脚本函数
   */
  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String value) {
    mUrl = value;
  }

  private String mTitle;

  /**
   */
  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String value) {
    mTitle = value;
  }

  private String mTarget = "_self";

  /**
   */
  public String getTarget() {
    return this.mTarget;
  }

  public void setTarget(String value) {
    this.mTarget = value;
  }

  private boolean mHasChildren = true;

  /**
   * 是否有叶子节点
   */
  public boolean getHasChildren() {
    return mHasChildren;
  }

  public void setHasChildren(boolean value) {
    mHasChildren = value;
  }

  private int mDisabled;

  /**
   * 禁止
   */
  public int getDisabled() {
    return mDisabled;
  }

  public void setDisabled(int value) {
    mDisabled = value;
  }

  public DynamicTreeNode() {
  }

  public DynamicTreeNode(String id, String parentId, String name, String title, String url, boolean hasChildren) {
    this(id, parentId, name, title, url, hasChildren, -1);
  }

  public DynamicTreeNode(String id, String parentId, String name, String title, String url, boolean hasChildren, int disabled) {
    this.setId(id);
    this.setParentId(parentId);
    this.setName(name);
    this.setTitle(title);
    this.setHasChildren(hasChildren);
    this.setDisabled(-1);
  }

  /**
   * 返回表示当前对象的字符串
   *
   * @return
   */
  @Override
  public String toString() {
    if (StringUtil.isNullOrEmpty(this.getTitle())) {
      this.setTitle(this.getName());
    }

    if (this.getHasChildren() && this.getDisabled() == 1) {
      String text = "javascript:";
      this.setUrl(String.format("javascript:void(0)"));
    } else {
      String text = "javascript:";
      if (this.getUrl().toLowerCase().indexOf(text) == 0) {
        this.setUrl(this.getUrl().replace("{treeNodeId}", this.getId().replace("$", "\\\\")).replace("{treeNodeParentId}", this.getParentId().replace("$", "\\\\")).replace("{treeNodeName}", this.getName().replace("\\", "\\\\").replace("$", "\\\\")).replace("{treeNodeToken}", this.getToken().replace("$", "\\\\")).replace("{treeNodeCategoryIndex}", this.getCategoryIndex().replace("\\", "\\\\").replace("$", "\\\\")));
      } else {
        this.setUrl(this.getUrl().replace("{treeNodeId}", this.getId()).replace("{treeNodeParentId}", this.getParentId()).replace("{treeNodeName}", this.getName()).replace("{treeNodeToken}", this.getToken()).replace("{treeNodeCategoryIndex}", this.getCategoryIndex()));
      }
    }

    StringBuilder outString = new StringBuilder();

    outString.append("{");
    outString.append("\"id\":\"" + StringUtil.toSafeJson(this.getId()) + "\",");
    outString.append("\"parentId\":\"" + StringUtil.toSafeJson(this.getParentId()) + "\",");
    outString.append("\"name\":\"" + StringUtil.toSafeJson(this.getName()) + "\",");
    outString.append("\"title\":\"" + StringUtil.toSafeJson(this.getTitle()) + "\",");
    outString.append("\"url\":\"" + StringUtil.toSafeJson(this.getUrl()) + "\",");
    outString.append("\"target\":\"" + StringUtil.toSafeJson(this.getTarget()) + "\",");
    outString.append("\"hasChildren\":\"" + String.valueOf(this.getHasChildren()).toLowerCase() + "\" ");
    outString.append("}");

    return outString.toString();
  }
}
