package com.x3platform.messages;

import javax.xml.bind.annotation.XmlElement;

import com.x3platform.configuration.KernelConfigurationView;
import org.dom4j.Element;

// import com.x3platform.Ajax.*;
// import com.x3platform.Ajax.Configuration.*;
// import com.x3platform.cachebuffer.*;
// import com.x3platform.Configuration.*;
import com.x3platform.util.*;

/**
 * 消息对象
 *
 * @author ruanyu
 */
public class MessageObject implements com.x3platform.messages.abstractions.MessageObject {

  /**
   * 返回的代码
   */
  private String mCode;

  public String getCode() {
    return mCode;
  }

  public void setCode(String value) {
    mCode = value;
  }

  /**
   * 返回的消息
   */
  private String mMessage;

  public String getMessage() {
    return mMessage;
  }

  public void setMessage(String value) {
    mMessage = value;
  }

  /**
   * 设置信息
   *
   * @param code    代码
   * @param message 消息
   */
  public void set(String code, String message) {
    this.setCode(code);
    this.setMessage(message);
  }

  /**
   * 转换为JSON格式的字符串对象.
   *
   * @return
   */
  @Override
  public String toString() {
    return this.toString(false);
  }

  /**
   * 转换为字符串
   *
   * @param nobrace 对象不包含最外面的大括号
   * @return
   */
  public String toString(boolean nobrace) {
    StringBuilder outString = new StringBuilder();

    if (!nobrace) {
      outString.append("{");
    }

    outString.append(String.format("\"code\":\"%1$s\",", StringUtil.toSafeJson(this.getCode())));

    outString.append(String.format("\"message\":\"%1$s\"", StringUtil.toSafeJson(this.getMessage())));

    if (!nobrace) {
      outString.append("}");
    }

    String result = null;

    try {
      MessageObjectFormatter formatter = null;

      formatter = (MessageObjectFormatter) Class.forName(KernelConfigurationView.getInstance().getMessageObjectFormatter()).newInstance();

      result = !nobrace ? formatter.format(outString.toString(), nobrace) : formatter.format("{" + outString.toString() + "}", nobrace);
    } catch (InstantiationException ex) {
      ex.printStackTrace();
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }

    return result;
  }

  // -------------------------------------------------------
  // 实现 EntityClass 序列化
  // -------------------------------------------------------

  /**
   * 序列化对象
   */
  @Override
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 序列化对象
   *
   * @param displayComment      显示注释信息
   * @param displayFriendlyName 显示友好名称信息
   * @return
   */
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    outString.append("<response>");
    outString.append("<code>" + this.getCode() + "</code>");
    outString.append("<message>" + this.getMessage() + "</message>");
    outString.append("</response>");

    return outString.toString();
  }

  /**
   * 反序列化对象
   *
   * @param element Xml 元素
   */
  @Override
  public void deserialize(Element element) {
    this.setCode(element.selectSingleNode("code").getText());
    this.setMessage(element.selectSingleNode("message").getText());
  }

  // -------------------------------------------------------
  // 静态方法
  // -------------------------------------------------------

  /**
   * 格式化为 JOSN 格式字符串
   *
   * @param code    返回的代码
   * @param message 消息
   * @return
   */
  public static String stringify(String code, String message) {
    MessageObject m = new MessageObject();

    m.setCode(code);
    m.setMessage(message);

    return m.toString();
  }

  /**
   * 格式化为 JOSN 格式字符串
   *
   * @param code    返回的代码
   * @param message 消息
   * @param nobrace 对象不包含最外面的大括号
   * @return JOSN 格式字符串
   */
  public static String stringify(String code, String message, boolean nobrace) {
    MessageObject m = new MessageObject();

    m.setCode(code);
    m.setMessage(message);

    return m.toString(nobrace);
  }
}
