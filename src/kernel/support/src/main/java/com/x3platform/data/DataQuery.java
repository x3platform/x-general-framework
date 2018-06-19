package com.x3platform.data;

import java.util.*;

import org.dom4j.*;

import com.x3platform.*;
import com.x3platform.util.*;

/**
 * 数据查询参数对象
 */
public class DataQuery implements ISerializedObject {

  // private HashMap<String, String> m_Variables = new HashMap<String, String>() {
  //  {  "elevatedPrivileges", "0" },
  //  {   "scence", "default" }
  // };
  private HashMap<String, String> m_Variables = new HashMap<String, String>();

  /**
   * 查询上下文环境变量集合
   */
  public final HashMap<String, String> getVariables() {
    return this.m_Variables;
  }

  private String m_Table = "";

  /**
   * 数据表名称
   */
  public final String getTable() {
    return this.m_Table;
  }

  public final void setTable(String value) {
    this.m_Table = value;
  }

  private List<String> m_Fields = new ArrayList<String>();

  /**
   * 字段列表
   */
  public final List<String> getFields() {
    return this.m_Fields;
  }

  private HashMap<String, Object> m_Where = new HashMap<String, Object>();

  /**
   * 过滤规则
   */
  public final HashMap<String, Object> getWhere() {
    return this.m_Where;
  }

  private List<String> m_Orders = new ArrayList<String>();

  /**
   * 排序规则
   */
  public final List<String> getOrders() {
    return this.m_Orders;
  }

  private int m_Length = 1000;

  /**
   * 查询记录最大函数限制 (默认值:1000)
   */
  public final int getLength() {
    return this.m_Length;
  }

  public final void setLength(int value) {
    this.m_Length = value;
  }

  /**
   * 获取过滤规则 SQL 表达式
   *
   * @return
   */
  public final String getWhereSql() {
    return this.getWhereSql(new HashMap<String, String>());
  }

  /**
   * 获取过滤规则 SQL 表达式
   *
   * @param operators 关键字操作符
   * @return
   */
  public final String getWhereSql(HashMap<String, String> operators) {
    StringBuilder outString = new StringBuilder();

    for (Map.Entry<String, Object> item : this.getWhere().entrySet()) {
      if (item.getValue() == null) {
        continue;
      }

      String typeName = item.getValue().getClass().getName();

      String op = "=";

      for (Map.Entry<String, String> childoperator : operators.entrySet()) {
        if (item.getKey().toUpperCase().equals(childoperator.getKey().toUpperCase())) {
          if (childoperator.getValue().toUpperCase().equals("LIKE") && typeName.equals("System.String")) {
            op = childoperator.getValue().toUpperCase();
          } else {
            op = childoperator.getValue();
          }
        }
      }

      switch (typeName) {
        case "System.Int16":
        case "System.Int32":
        case "System.Int64":
        case "System.Double":
        case "System.Decimal":
          outString.append(String.format("%1$s %2$s %3$s", item.getKey(), op, item.getValue()));
          break;
        case "System.Boolean":
          outString.append(String.format("%1$s %2$s %3$s", item.getKey(), op, (boolean) item.getValue() ? 1 : 0));
          break;
        case "System.Guid":
          outString.append(String.format("%1$s %2$s '%3$s'", item.getKey(), op, item.getValue()));
          break;
        case "System.DateTime":
          // outString.append(String.format("%1$s %2$s '%3$s'", item.getKey(), op, (java.time.LocalDateTime) item.getValue().toString("yyyy-MM-dd HH:mm:ss")));
          outString.append(String.format("%1$s %2$s '%3$s'", item.getKey(), op, (java.time.LocalDateTime) item.getValue()));
          break;
        case "System.String":

          String value = item.getValue().toString();

          if (!StringUtil.isNullOrEmpty(value)) {
            value = value.replace("\\", "\\\\");
          }

          if (op.equals("LIKE")) {
            // 字符串 LIKE 查询内容必须不为空
            if (!StringUtil.isNullOrEmpty(value)) {
              outString.append(String.format("%1$s LIKE '%%%2$s%%'", item.getKey(), StringUtil.toSafeSQL(value)));
            }
          } else if (op.equals("IN")) {
            // 字符串 LIKE 查询内容必须不为空
            if (!StringUtil.isNullOrEmpty(value)) {
              outString.append(String.format("%1$s IN (%2$s)", item.getKey(), "'" + StringUtil.toSafeSQL(value).replace(",", "','") + "'"))
              ;
            }
          } else {
            outString.append(String.format("%1$s %2$s '%3$s'", item.getKey(), op, StringUtil.toSafeSQL(value)));
          }
          break;
        case "System.Array":
          break;
        default:
          outString.append(String.format("%1$s %2$s '%3$s'", item.getKey(), op, item.getValue()));
          break;
      }

      if (outString.length() > 0) {
        outString.append(" AND ");
      }
    }

    // 移除最后的 AND 标记
    if (outString.length() >= 5 && StringUtil.substring(outString.toString(), outString.length() - 5, 5).equals(" AND ")) {
      outString = outString.delete(outString.length() - 5, outString.length() - 5 + 5);
    }

    return outString.toString();
  }

  /**
   * 获取排序规则 SQL 表达式
   *
   * @return
   */
  public final String getOrderBySql() {
    return this.getOrderBySql("");
  }

  /**
   * 获取排序规则 SQL 表达式
   *
   * @param defaults 默认规则
   * @return
   */
  public final String getOrderBySql(String defaults) {
    if (this.getOrders().isEmpty()) {
      return defaults;
    }

    String orderBy = "";

    for (String item : this.getOrders()) {
      orderBy += StringUtil.toSafeSQL(item, true) + ",";
    }

    orderBy = StringUtil.trimEnd(orderBy, ",");

    return orderBy;
  }

  // -------------------------------------------------------
  // 实现 ISerializedObject 序列化
  // -------------------------------------------------------

  /**
   * 根据对象导出Xml元素
   *
   * @return
   */
  public final String serializable() {
    return serializable(false, false);
  }

  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   * @return
   */
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    String innerText = null;

    outString.append("<query>");

    // Table
    outString.append(String.format("<table><![CDATA[%1$s]]></table>", this.getTable()));

    // Fields
    innerText = "";

    for (String item : this.getFields()) {
      innerText += item + ",";
    }

    innerText = StringUtil.trimEnd(innerText, ",");

    outString.append(String.format("<fields><![CDATA[%1$s]]></fields>", innerText));

    // Where
    innerText = "";

    for (Map.Entry<String, Object> item : this.getWhere().entrySet()) {
      innerText += "<key name=\"" + item.getKey() + "\" type=\"" + item.getValue().getClass() + "\" ><![CDATA[" + item.getValue() + "]]></key>";
    }

    outString.append(String.format("<where><![CDATA[%1$s]]></where>", innerText));

    // Orders
    innerText = "";

    for (String item : this.getOrders()) {
      innerText += item + ",";
    }

    innerText = StringUtil.trimEnd(innerText, ",");

    outString.append(String.format("<orders><![CDATA[%1$s]]></orders>", innerText));

    // length
    if (this.getLength() > 0) {
      outString.append(String.format("<length><![CDATA[%1$s]]></length>", this.getLength()));
    }

    outString.append("</query>");

    return outString.toString();
  }

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  public final void deserialize(Element element) {
    Node node = null;

    // Scence
    node = element.selectSingleNode("scence");

    this.getVariables().put("scence", (node == null) ? "default" : node.getText());

    // Table
    node = element.selectSingleNode("table");

    this.setTable((node == null) ? "" : node.getText());
/*
    // Fields
    node = element.SelectSingleNode("fileds");

    this.getFields().clear();

    if (node != null) {
      String[] fields = node.InnerText.split(new char[]{','}, StringSplitOptions.RemoveEmptyEntries);

      for (String field : fields) {
        this.getFields().add(field);
      }
    }

    node = element.SelectSingleNode("where");

    if (node != null) {
      // Where
      XmlNodeList nodes = node.ChildNodes;

      this.getWhere().clear();

      for (XmlNode item : nodes) {
        String name, value, type;

        if (item.Attributes["name"] == null) {
          // 支持
          // <item><name></name><value><value><type><type></item>
          name = item.SelectSingleNode("name") == null ? "" : item.SelectSingleNode("name").InnerText;
          value = item.SelectSingleNode("value") == null ? "" : item.SelectSingleNode("value").InnerText;
          type = item.SelectSingleNode("type") == null ? "string" : item.SelectSingleNode("type").InnerText;
        } else {
          // 支持
          // <key name="name1" type="int" ></key>
          name = item.Attributes["name"].Value;
          value = item.InnerText;
          type = item.Attributes["type"] == null ? "string" : item.Attributes["type"].Value;
        }

        // 忽略名称为空的参数
        if (StringUtil.isNullOrEmpty(name)) {
          continue;
        }

        switch (type) {
          case "int":
            this.getWhere().put(name, Integer.parseInt(value));
            break;
          case "long":
          case "number":
            this.getWhere().put(name, Long.parseLong(value));
            break;
          case "decimal":
            this.getWhere().put(name, (java.math.BigDecimal) value);
            break;
          case "date":
            this.getWhere().put(name, java.time.LocalDateTime.parse(value));
            break;
          default:
            this.getWhere().put(name, value);
            break;
        }
      }
    }

    // Orders
    node = element.SelectSingleNode("orders");

    this.getOrders().clear();

    if (node != null) {
      String[] orders = node.InnerText.split(new char[]{','}, StringSplitOptions.RemoveEmptyEntries);

      for (String order : orders) {
        this.getOrders().add(order);
      }
    }
    */

    // Length
    node = element.selectSingleNode("length");

    if (node != null) {
      this.setLength(Integer.parseInt(node.getText()));
    }
  }

  /**
   * 创建查询参数
   *
   * @param queryXml Xml文本格式
   */
  public static DataQuery Create(String queryXml) {
    DataQuery query = new DataQuery();

    // Document doc = new Document();

    // doc.LoadXml(String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n<root>%1$s</root>", queryXml));

    String text = String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n<root>%1$s</root>", queryXml);

    Document doc = null;

    try {
      doc = DocumentHelper.parseText(text);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    query.deserialize(doc.getRootElement());

    return query;
  }
}
