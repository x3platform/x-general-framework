package com.x3platform.categoryIndexes;

import java.util.*;

/**
 * 类别索引接口
 */
public interface ICategoryIndex {
  String getText();

  void setText(String value);

  String getValue();

  void setValue(String value);

  ICategoryIndex getParent();

  List<ICategoryIndex> getChildNodes();

  boolean getHasChildren();

  void LoadChildNode(ICategoryIndex node);

  void LoadChildNodes(List<ICategoryIndex> nodes);
}
