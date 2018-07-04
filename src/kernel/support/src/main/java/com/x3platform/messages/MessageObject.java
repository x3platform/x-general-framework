package com.x3platform.messages;

import javax.xml.bind.annotation.XmlElement;

import com.x3platform.config.KernelConfigurationView;
import org.dom4j.Element;

// import com.x3platform.Ajax.*;
// import com.x3platform.Ajax.Configuration.*;
// import com.x3platform.CacheBuffer.*;
// import com.x3platform.Configuration.*;
import com.x3platform.util.*;

/**
 * 消息对象
 */
public final class MessageObject implements IMessageObject {

  /**
   * 返回的代码
   */
  private String m_Code;

  public String getCode() {
    return m_Code;
  }

  public void setCode(String value) {
    m_Code = value;
  }

  /**
   * 返回的消息
   */
  private String m_Message;

  public String getMessage() {
    return m_Message;
  }

  public void setMessage(String value) {
    m_Message = value;
  }

  /**
   * 设置信息
   *
   * @param code   代码
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

    // TODO 需要修改
    // Code:
    // if (AjaxConfigurationView.Instance.NamingRule.equals("underline")) {
    //   outString.append(String.format("\"return_code\":\"%1$s\",", StringUtil.toSafeJson(this.getReturnCode())));
    // } else {
    //   outString.append(String.format("\"returnCode\":\"%1$s\",", StringUtil.toSafeJson(this.getReturnCode())));
    // }
    outString.append(String.format("\"code\":\"%1$s\",", StringUtil.toSafeJson(this.getCode())));

    outString.append(String.format("\"message\":\"%1$s\"", StringUtil.toSafeJson(this.getMessage())));

    // 是否成功执行
    // outString.append("\"success\":1,\"msg\":\"success\"");

    if (!nobrace) {
      outString.append("}");
    }

    String result = null;

    try {
      IMessageObjectFormatter formatter = null;

      formatter = (IMessageObjectFormatter) Class.forName(KernelConfigurationView.getInstance().getMessageObjectFormatter()).newInstance();

      result = !nobrace ? formatter.Format(outString.toString(), nobrace) : formatter.Format("{" + outString.toString() + "}", nobrace);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return result;
  }

  // -------------------------------------------------------
  // 实现 EntityClass 序列化
  // -------------------------------------------------------

  /**
   * 序列化对象
   */
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
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    outString.append("<response>");

    outString.append("<code>" + this.getCode() + "</code>");
    outString.append("<message>" + this.getMessage() + "</message>");
    //outString.Append("<description>" + this.Description + "</description>");
    //outString.Append("<url>" + this.Url + "</url>");
    outString.append("</response>");

    return outString.toString();
  }

  /**
   * 反序列化对象
   *
   * @param element Xml 元素
   */
  public void deserialize(Element element) {
    // TODO 需求修改
    // this.setValue(XmlHelper.Fetch("value", element));
    // this.setReturnCode(XmlHelper.Fetch("returnCode", element));
  }

  // -------------------------------------------------------
  // 静态方法
  // -------------------------------------------------------

  /**
   * 格式化为 JOSN 格式字符串
   *
   * @param returnCode 返回的代码
   * @param value      消息
   * @return
   */
  public static String stringify(String returnCode, String value) {
    MessageObject message = new MessageObject();

    message.setCode(returnCode);
    message.setMessage(value);

    return message.toString();
  }

  /**
   * 格式化为 JOSN 格式字符串
   *
   * @param returnCode 返回的代码
   * @param value      消息
   * @param nobrace    对象不包含最外面的大括号
   * @return JOSN 格式字符串
   */
  public static String stringify(String returnCode, String value, boolean nobrace) {
    MessageObject message = new MessageObject();
    message.setCode(returnCode);
    message.setMessage(value);

    return message.toString(nobrace);
  }
}
