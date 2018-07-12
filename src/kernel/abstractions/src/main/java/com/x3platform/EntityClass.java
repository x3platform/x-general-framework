package com.x3platform;

import org.dom4j.Element;

import java.util.*;

/**
 * 抽象实体类
 */
public abstract class EntityClass implements ISerializedObject {

  /**
   * 实体类名称
   */
  public abstract String getEntityId();

  /**
   * 实体类名称
   */
  public final String getEntityClassName() {
    return KernelContext.parseObjectType(this.getClass());
  }

  /**
   * 属性
   */
  private Hashtable propertieCache = new Hashtable(13);

  /**
   * 属性
   */
  public final Hashtable getProperties() {
    return propertieCache;
  }

  /**
   * 序列化对象
   */
  public String serializable() {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：string Serializable()。");
  }

  /**
   * 序列化对象
   *
   * @param displayComment      显示注释信息
   * @param displayFriendlyName 显示友好名称信息
   * @return
   */
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：string Serializable(bool displayComment, bool displayFriendlyName)。");
  }

  /**
   * 反序列化对象
   *
   * @param element Xml元素
   */
  public void deserialize(Element element) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：void Deserialize(XmlElement element)。");
  }

  /**
   * 查找实体对象
   *
   * @param id 标识
   */
  public void Find(String id) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：void Find(string id) 。");
  }
}
