package com.x3platform.categoryIndexes;

/**
 */
public class CategoryIndexWriter {
  private ICategoryIndex root = null;

  /**
   * @param name
   */
  public CategoryIndexWriter(String name) {
    root = new TextCategoryIndex(name);
  }

  /**
   * @param index
   */
  public final void Read(String index) {
    root.LoadChildNode(new TextCategoryIndex(root, index));
  }

  /**
   * @return
   */
  public final ICategoryIndex Write() {
    return root;
  }
}
