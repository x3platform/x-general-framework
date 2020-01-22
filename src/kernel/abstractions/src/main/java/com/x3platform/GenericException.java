package com.x3platform;

import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.messages.MessageObject;
import com.x3platform.messages.abstractions.MessageObjectFormatter;
import com.x3platform.util.StringUtil;
import java.io.Serializable;

/**
 * 通常的异常信息
 */
public class GenericException extends RuntimeException implements Serializable {

  /**
   * 返回的代码
   */
  private String returnCode = "1";

  /**
   * 返回的代码
   */
  public final String getReturnCode() {
    return this.returnCode;
  }

  /**
   * 构造函数
   *
   * @param message 消息
   */
  public GenericException(String message) {
    super(message);
  }

  /**
   * 构造函数
   *
   * @param returnCode 返回的异常代码
   * @param message 消息
   */
  public GenericException(String returnCode, String message) {
    super(message);
    this.returnCode = returnCode;
  }

  /**
   * 构造函数
   *
   * @param returnCode 返回的异常代码
   * @param innerException 内部异常
   */
  public GenericException(String returnCode, RuntimeException innerException) {
    super(innerException.getMessage(), innerException);
    this.returnCode = returnCode;
  }

  /**
   * 转换为字符串
   */
  @Override
  public String toString() {
    return this.toString(false);
  }

  /**
   * 转换为字符串
   *
   * @param nobrace 对象不包含最外面的大括号
   */
  public final String toString(boolean nobrace) {
    String message = this.getCause() == null ? this.getMessage() : this.getCause().toString();
    return MessageObject.stringify(returnCode, message, nobrace);
  }

  // -------------------------------------------------------
  // 静态方法
  // -------------------------------------------------------

  /**
   * 格式化为 JOSN 格式字符串
   *
   * @param returnCode 返回的异常代码
   * @param message 消息
   */
  public static String stringify(String returnCode, String message) {
    return (new GenericException(returnCode, message)).toString();
  }

  /**
   * 格式化为 JOSN 格式字符串
   *
   * @param returnCode 返回的异常代码
   * @param message 消息
   * @param nobrace 对象不包含最外面的大括号
   */
  public static String stringify(String returnCode, String message, boolean nobrace) {
    return (new GenericException(returnCode, message)).toString(nobrace);
  }
}
