package com.x3platform.data;

import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;

import com.x3platform.*;
import com.x3platform.util.*;

/**
 * 数据查询参数对象
 */
public class DataQuery implements ISerializedObject, ISerializedJavaScriptObject {

  public DataQuery() {
    this.mVariables.put("elevatedPrivileges", "0");
    this.mVariables.put("scence", "default");
  }

  private HashMap<String, String> mVariables = new HashMap<String, String>();

  /**
   * 查询上下文环境变量集合
   */
  public final HashMap<String, String> getVariables() {
    return this.mVariables;
  }

  private String mTable = "";

  /**
   * 数据表名称
   */
  public final String getTable() {
    return this.mTable;
  }

  public final void setTable(String value) {
    this.mTable = value;
  }

  private List<String> mFields = new ArrayList<String>();

  /**
   * 字段列表
   */
  public final List<String> getFields() {
    return this.mFields;
  }

  private HashMap<String, Object> mWhere = new HashMap<String, Object>();

  /**
   * 过滤规则
   */
  public final HashMap<String, Object> getWhere() {
    return this.mWhere;
  }

  private List<String> mOrders = new ArrayList<String>();

  /**
   * 排序规则
   */
  public final List<String> getOrders() {
    return this.mOrders;
  }

  private int mLength = 0;

  /**
   * 查询记录最大函数限制 (默认值:0)
   */
  public final int getLength() {
    return this.mLength;
  }

  public final void setLength(int value) {
    this.mLength = value;
  }

  /**
   * 获取适用于 MyBaits 的参数集合
   *
   * @return
   */
  public final HashMap<String, Object> getMap() {
    HashMap<String, Object> results = new HashMap<String, Object>();

    // 设置 where 参数
    HashMap<String, String> mapVariables = getVariables();

    Iterator iterator = mapVariables.entrySet().iterator();

    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry) iterator.next();
      results.put("var_" + entry.getKey(), entry.getValue());
    }

    results.put("table", getTable());
    results.put("fields", getFields());

    // 设置 where 参数
    HashMap<String, Object> mapWhere = getWhere();

    iterator = mapWhere.entrySet().iterator();

    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry) iterator.next();
      results.put("param_" + entry.getKey(), entry.getValue());
    }

    results.put("length", getLength());
    results.put("orders", getOrders());

    return results;
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
  // 实现 ISerializedObject 接口
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

    this.getVariables().clear();

    // Scence
    node = element.selectSingleNode("scence");

    this.getVariables().put("scence", (node == null) ? "default" : node.getText());

    // Table
    node = element.selectSingleNode("table");

    this.setTable((node == null) ? "" : node.getText());

    // Fields
    node = element.selectSingleNode("fileds");

    this.getFields().clear();

    if (node != null) {
      String[] fields = node.getText().split(",");

      for (String field : fields) {
        this.getFields().add(field);
      }
    }

    node = element.selectSingleNode("where");

    this.getWhere().clear();

    if (node != null) {
      // Where
      // XmlNodeList nodes = node.ChildNodes;
      List<Node> nodes = node.selectNodes("");

      for (Node nodeItem : nodes) {
        String name, value, type;
        Element item = (Element) nodeItem;

        if (item.attribute("name") == null) {
          // 支持
          // <item><name></name><value><value><type><type></item>
          name = item.selectSingleNode("name") == null ? "" : item.selectSingleNode("name").getText();
          value = item.selectSingleNode("value") == null ? "" : item.selectSingleNode("value").getText();
          type = item.selectSingleNode("type") == null ? "string" : item.selectSingleNode("type").getText();
        } else {
          // 支持
          // <key name="name1" type="int" ></key>
          name = item.attribute("name").getValue();
          value = item.getText();
          type = item.attribute("type") == null ? "string" : item.attribute("type").getValue();
        }

        // 忽略名称为空的参数
        if (StringUtil.isNullOrEmpty(name)) {
          continue;
        }

        this.getWhere().put(name, convertParamType(type, value));
      }
    }

    // Orders
    node = element.selectSingleNode("orders");

    this.getOrders().clear();

    if (node != null) {
      String[] orders = node.getText().split(",");

      for (String order : orders) {
        this.getOrders().add(order);
      }
    }

    // Length
    node = element.selectSingleNode("length");

    if (node != null) {
      this.setLength(Integer.parseInt(node.getText()));
    }
  }

  // -------------------------------------------------------
  // 实现 ISerializedJavaScriptObject 接口
  // -------------------------------------------------------

  @Override
  public String toJSON() {
    return null;
  }

  @Override
  public void fromJSON(String json) {
    JSONObject data = JSON.parseObject(json);

    this.getVariables().clear();

    // Scence
    if (data.containsKey("scence")) {
      this.getVariables().put("scence", data.getString("scence"));
    } else {
      this.getVariables().put("scence", "default");

    }

    // Table
    if (data.containsKey("table")) {
      this.setTable(data.getString("table"));
    }

    // Fields
    this.getFields().clear();

    if (data.containsKey("fields")) {
      String[] fields = data.getString("fields").split(",");

      for (String field : fields) {
        this.getFields().add(field);
      }
    }

    this.getWhere().clear();

    if (data.containsKey("where")) {
      // Where
      JSONArray nodes = data.getJSONArray("where");

      for (Object nodeItem : nodes) {
        String name, value, type;
        JSONObject item = (JSONObject) nodeItem;

        name = !item.containsKey("name") ? "" : item.getString("name");
        value = !item.containsKey("value") ? "" : item.getString("value");
        type = !item.containsKey("type") ? "string" : item.getString("type");

        // 忽略名称为空的参数
        if (StringUtil.isNullOrEmpty(name)) {
          continue;
        }

        this.getWhere().put(name, convertParamType(type, value));
      }
    }

    // Orders
    this.getOrders().clear();

    if (data.containsKey("orders")) {
      String[] orders = data.getString("orders").split(",");

      for (String order : orders) {
        this.getOrders().add(order);
      }
    }

    // Length
    if (data.containsKey("length")) {
      this.setLength(data.getInteger("length"));
    }
  }

  /**
   * 将参数字符串类型的值转为实际类型
   */
  private Object convertParamType(String type, String value) {
    switch (type) {
      case "int":
        return Integer.parseInt(value);
      case "long":
      case "number":
        return Long.parseLong(value);
      case "decimal":
        return new BigDecimal(value);
      case "date":
        return java.time.LocalDateTime.parse(value);
      case "string":
      default:
        return value;
    }
  }

  /**
   * 创建查询参数
   *
   * @param queryText JSON 或 XML 格式文本
   */
  public static DataQuery create(String queryText) {
    DataQuery query = new DataQuery();

    try {
      // 默认为 JSON 解析
      query.fromJSON(queryText);

    } catch (Exception ex) {

      String text = String.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n<root>%1$s</root>", queryText);

      Document doc = null;

      try {
        doc = DocumentHelper.parseText(text);
      } catch (DocumentException documentException) {
        documentException.printStackTrace();
      }

      query.deserialize(doc.getRootElement());
    }

    return query;
  }
}
