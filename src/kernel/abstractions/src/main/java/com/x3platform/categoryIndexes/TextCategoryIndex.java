package com.x3platform.categoryIndexes;

import com.x3platform.util.StringUtil;

import java.util.*;

///#region Using Libraries
///#endregion

/**
 * 文本分类索引
 */
public class TextCategoryIndex implements ICategoryIndex {
  ///#region 属性:Text
  private String mText = null;

  /**
   * 文本
   */
  public final String getText() {
    return mText;
  }

  public final void setText(String value) {
    mText = value;
  }
  ///#endregion

  ///#region 属性:Value
  private String mValue = null;

  /**
   * 值
   */
  public final String getValue() {
    return this.getParent() == null ? mValue : String.format("%1$s_%2$s", this.getParent().getValue(), mValue);
  }

  public final void setValue(String value) {
    mValue = value;
  }
  ///#endregion

  ///#region 属性:Parent
  private ICategoryIndex mParnet = null;

  /**
   * 父级对象
   */
  public final ICategoryIndex getParent() {
    return mParnet;
  }
  ///#endregion

  ///#region 属性:ChildNodes
  private List<ICategoryIndex> mChildNodes = null;

  public final List<ICategoryIndex> getChildNodes() {
    return mChildNodes;
  }
  ///#endregion

  ///#region 属性:HasChildren
  public final boolean getHasChildren() {
    return getChildNodes().isEmpty() ? false : true;
  }

  ///#endregion

  ///#region 函数:LoadChildNode(ICategoryIndex  node)
  public final void LoadChildNode(ICategoryIndex node) {
    boolean isExist = false;

    for (ICategoryIndex index : this.getChildNodes()) {
      if (node.getValue() == index.getValue()) {
        isExist = true;
        index.LoadChildNodes(node.getChildNodes());

        break;
      }
    }

    if (!isExist) {
      this.getChildNodes().add(node);
    }
  }
  ///#endregion

  ///#region 函数:LoadChildNodes(IList<ICategoryIndex> nodes)
  public final void LoadChildNodes(List<ICategoryIndex> nodes) {
    for (ICategoryIndex node : nodes) {
      this.LoadChildNode(node);
    }
  }
  ///#endregion

  public TextCategoryIndex() {
    mChildNodes = new ArrayList<ICategoryIndex>();
  }

  public TextCategoryIndex(ICategoryIndex parent) {
    this(parent, null);
  }

  public TextCategoryIndex(String index) {
    this(null, index);
  }

  public TextCategoryIndex(ICategoryIndex parent, String index) {
    this();
    if (parent != null) {
      this.mParnet = parent;
    }

    this.Load(index);
  }

  private void Load(String index) {
    // 去除空白信息
    if (index != null) {
      index = index.trim();
    }

    if (StringUtil.isNullOrEmpty(index)) {
      return;
    }

    // String[] keys = index.split(new char[] {'\\'}, StringSplitOptions.RemoveEmptyEntries);
    String[] keys = index.replaceAll("\\\\","/").split("/");

    if (keys.length > 0) {
      this.setText(keys[0]);

      this.setValue(keys[0]);
    }

    //
    // 子节点处理
    //

    if (keys.length > 1) {
      ICategoryIndex target = this;

      for (int x = 1; x < keys.length; x++) {
        boolean isExit = false;

        List<ICategoryIndex> nodes = target.getChildNodes();

        for (int y = 0; y < nodes.size(); y++) {
          if (keys[x].equals(nodes.get(y).getValue())) {
            isExit = true;
            target = nodes.get(y);
            break;
          }
        }

        if (!isExit) {
          target.LoadChildNode(new TextCategoryIndex(target, keys[x]));

          target = target.getChildNodes().get(target.getChildNodes().size() - 1);
        }
      }
    }
  }
}
