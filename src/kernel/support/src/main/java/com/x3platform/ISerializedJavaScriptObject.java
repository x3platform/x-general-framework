package com.x3platform;

/**
 * 序列化JSON对象接口
 */
public interface ISerializedJavaScriptObject {
  /**
   * 序列化JSON对象
   *
   * @return JSON 格式字符串
   */
  String toJSON();

  /**
   * 反序列化JSON对象
   *
   * @param json JSON对象
   */
  void fromJSON(String json);
}
