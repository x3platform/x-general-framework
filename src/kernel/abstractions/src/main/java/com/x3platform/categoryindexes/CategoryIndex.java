package com.x3platform.categoryindexes;

import java.util.*;

/**
 * 类别索引接口
 *
 * @author ruanyu
 */
public interface CategoryIndex {
  /**
   * 获取文本信息
   *
   * @return 返回文本信息
   */
  String getText();

  /**
   * 获取文本信息
   *
   * @param value 文本信息
   */
  void setText(String value);

  /**
   * 获取值信息
   *
   * @return 返回值信息
   */
  String getValue();

  /**
   * 设置值信息
   *
   * @param value 值信息
   */
  void setValue(String value);

  /**
   * 获取父节对象信息
   *
   * @return 返回父级对象信息
   */
  CategoryIndex getParent();

  /**
   * 获取子节点信息
   *
   * @return 返回子节点对象信息
   */
  List<CategoryIndex> getChildNodes();

  /**
   * 获取文本信息
   *
   * @return 是否存在子节点
   */
  boolean getHasChildren();

  /**
   * 加载子节点
   *
   * @param node 类别索引信息
   */
  void loadChildNode(CategoryIndex node);

  /**
   * 加载子节点数组
   *
   * @param nodes 类别索引素组信息
   */
  void loadChildNodes(List<CategoryIndex> nodes);
}
