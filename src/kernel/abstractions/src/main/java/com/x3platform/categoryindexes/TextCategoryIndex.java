package com.x3platform.categoryindexes;

import com.x3platform.util.StringUtil;

import java.util.*;

/**
 * 文本分类索引
 *
 * @author ruanyu
 */
public class TextCategoryIndex implements CategoryIndex {

  private String mText = null;

  /**
   * 文本
   */
  @Override
  public String getText() {
    return mText;
  }

  @Override
  public void setText(String value) {
    mText = value;
  }

  private String mValue = null;

  /**
   * 值
   */
  @Override
  public String getValue() {
    return this.getParent() == null ? mValue : String.format("%1$s_%2$s", this.getParent().getValue(), mValue);
  }

  @Override
  public void setValue(String value) {
    mValue = value;
  }

  private CategoryIndex mParnet = null;

  /**
   * 父级对象
   */
  @Override
  public CategoryIndex getParent() {
    return mParnet;
  }

  private List<CategoryIndex> mChildNodes = null;

  @Override
  public List<CategoryIndex> getChildNodes() {
    return mChildNodes;
  }

  @Override
  public boolean getHasChildren() {
    return getChildNodes().isEmpty() ? false : true;
  }

  @Override
  public void loadChildNode(CategoryIndex node) {
    boolean isExist = false;

    for (CategoryIndex index : this.getChildNodes()) {
      if (node.getValue().equals(index.getValue())) {
        isExist = true;
        index.loadChildNodes(node.getChildNodes());
        break;
      }
    }

    if (!isExist) {
      this.getChildNodes().add(node);
    }
  }

  @Override
  public void loadChildNodes(List<CategoryIndex> nodes) {
    for (CategoryIndex node : nodes) {
      this.loadChildNode(node);
    }
  }

  public TextCategoryIndex() {
    mChildNodes = new ArrayList<CategoryIndex>();
  }

  public TextCategoryIndex(CategoryIndex parent) {
    this(parent, null);
  }

  public TextCategoryIndex(String index) {
    this(null, index);
  }

  public TextCategoryIndex(CategoryIndex parent, String index) {
    this();
    if (parent != null) {
      this.mParnet = parent;
    }

    this.load(index);
  }

  private void load(String index) {
    // 去除空白信息
    if (index != null) {
      index = index.trim();
    }

    if (StringUtil.isNullOrEmpty(index)) {
      return;
    }

    /// 原始代码
    // String[] keys = index.split(new char[] {'\\'}, StringSplitOptions.RemoveEmptyEntries);
    String[] keys = index.replaceAll("\\\\", "/").split("/");

    if (keys.length > 0) {
      this.setText(keys[0]);

      this.setValue(keys[0]);
    }

    //
    // 子节点处理
    //

    if (keys.length > 1) {
      CategoryIndex target = this;

      for (int x = 1; x < keys.length; x++) {
        boolean isExit = false;

        List<CategoryIndex> nodes = target.getChildNodes();

        for (int y = 0; y < nodes.size(); y++) {
          if (keys[x].equals(nodes.get(y).getValue())) {
            isExit = true;
            target = nodes.get(y);
            break;
          }
        }

        if (!isExit) {
          target.loadChildNode(new TextCategoryIndex(target, keys[x]));

          target = target.getChildNodes().get(target.getChildNodes().size() - 1);
        }
      }
    }
  }
}
