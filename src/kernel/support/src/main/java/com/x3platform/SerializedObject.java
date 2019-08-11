package com.x3platform;

import org.dom4j.Element;

/**
 * 序列化对象接口
 */
public interface SerializedObject {
  /**
   * 序列化对象
   *
   * @return 序列化的字符串
   */
  String serializable();

  /**
   * 序列化对象
   *
   * @param displayComment      显示注释信息
   * @param displayFriendlyName 显示友好名称信息
   * @return 序列化的字符串
   */
  String serializable(boolean displayComment, boolean displayFriendlyName);

  /**
   * 反序列化对象
   *
   * @param element Xml元素
   */
  void deserialize(Element element);
}
