package com.x3platform.messages;

/**
 * 消息对象输出为符合规定的 JSON 字符串
 */
public interface IMessageObjectFormatter {
  /**
   * 消息格式化
   *
   * @param json    JSON 格式字符串
   * @param nobrace 对象不包含最外面的大括号
   * @return 格式化后的字符串
   */
  String Format(String json, boolean nobrace);
}
