package com.x3platform.categoryindexes;

/**
 * 文本和值对应的分类索引
 *
 * @author ruanyu
 */
public class TextValueCategoryIndex extends TextCategoryIndex {
  /**
   * @param text
   */
  public TextValueCategoryIndex(String text) {
    this(text, text);
  }

  public TextValueCategoryIndex(String text, String value) {
    this.setText(text);
    this.setValue(value);
  }
}
