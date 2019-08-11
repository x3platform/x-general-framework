package com.x3platform.categoryindexes;

/**
 * @author ruanyu
 */
public class CategoryIndexWriter {
  private CategoryIndex root = null;

  /**
   * @param name
   */
  public CategoryIndexWriter(String name) {
    root = new TextCategoryIndex(name);
  }

  /**
   * @param index
   */
  public void read(String index) {
    root.loadChildNode(new TextCategoryIndex(root, index));
  }

  /**
   * @return
   */
  public CategoryIndex write() {
    return root;
  }
}
