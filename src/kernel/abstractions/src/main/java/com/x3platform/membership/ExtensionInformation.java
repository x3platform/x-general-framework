package com.x3platform.membership;

import org.dom4j.Document;

import java.util.*;

/**
 * 扩展属性
 */
public interface ExtensionInformation {
  /**
   * 属性索引信息
   *
   * @param name 属性名称
   * @return 属性值m
   */
  Object getItem(String name);

  void setItem(String name, Object value);

  /**
   * 从Xml文档中加载扩展信息
   */
  void Load(Document doc);

  /**
   * 根据参数从数据库中加载扩展信息
   */
  void Load(HashMap<String, Object> args);

  /**
   * 保存
   */
  void Save();

  /**
   * 删除
   */
  void Delete();
}
