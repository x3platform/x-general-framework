package com.x3platform.data;

import static com.x3platform.Constants.TEXT_EMPTY;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.SerializedJSON;
import com.x3platform.SerializedObject;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * 数据查询参数对象
 */
public class DataQuery implements SerializedObject, SerializedJSON {
  
  public DataQuery() {
    variables.put("elevatedPrivileges", "0");
    variables.put("scene", "default");
  }
  
  private HashMap<String, String> variables = new HashMap<String, String>();
  
  /**
   * 查询上下文环境变量集合
   */
  public final HashMap<String, String> getVariables() {
    return variables;
  }
  
  private String table = "";
  
  /**
   * 数据表名称
   */
  public final String getTable() {
    return table;
  }
  
  public final void setTable(String value) {
    table = value;
  }
  
  private List<String> fields = new ArrayList<String>();
  
  /**
   * 字段列表
   */
  public final List<String> getFields() {
    return fields;
  }
  
  private HashMap<String, Object> where = new HashMap<String, Object>();
  
  /**
   * 过滤规则
   */
  public final HashMap<String, Object> getWhere() {
    return where;
  }
  
  private List<String> groups = new ArrayList<String>();
  
  /**
   * 分组规则
   */
  public final List<String> getGroups() {
    return groups;
  }
  
  private List<String> orders = new ArrayList<String>();
  
  /**
   * 排序规则
   */
  public final List<String> getOrders() {
    return orders;
  }
  
  private int length = 0;
  
  /**
   * 查询记录最大函数限制 (默认值:0)
   */
  public final int getLength() {
    return length;
  }
  
  public final void setLength(int value) {
    length = value;
  }
  
  /**
   * 获取适用于 MyBaits 的参数集合
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
    
    results.put("groups", getGroups());
    results.put("orders", getOrders());
    results.put("length", getLength());
    
    return results;
  }
  
  /**
   * 获取过滤规则 SQL 表达式
   */
  public final String getWhereSql() {
    return getWhereSql(new HashMap<String, String>());
  }
  
  /**
   * 获取过滤规则 SQL 表达式
   *
   * @param operators 关键字操作符
   */
  public final String getWhereSql(HashMap<String, String> operators) {
    StringBuilder outString = new StringBuilder();
    
    for (Map.Entry<String, Object> item : getWhere().entrySet()) {
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
          outString
            .append(String.format("%1$s %2$s '%3$s'", item.getKey(), op, (java.time.LocalDateTime) item.getValue()));
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
              outString.append(String
                .format("%1$s IN (%2$s)", item.getKey(), "'" + StringUtil.toSafeSQL(value).replace(",", "','") + "'"))
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
    if (outString.length() >= 5 && StringUtil.substring(outString.toString(), outString.length() - 5, 5)
      .equals(" AND ")) {
      outString = outString.delete(outString.length() - 5, outString.length() - 5 + 5);
    }
    
    return outString.toString();
  }
  
  /**
   * 获取排序规则 SQL 表达式
   */
  public final String getOrderBySql() {
    return getOrderBySql("");
  }
  
  /**
   * 获取排序规则 SQL 表达式
   *
   * @param defaults 默认规则
   */
  public final String getOrderBySql(String defaults) {
    if (getOrders().isEmpty()) {
      return defaults;
    }
    
    String orderBy = "";
    
    for (String item : getOrders()) {
      orderBy += StringUtil.toSafeSQL(item, true) + ",";
    }
    
    orderBy = StringUtil.trimEnd(orderBy, ",");
    
    return orderBy;
  }
  
  // -------------------------------------------------------
  // 实现 SerializedObject 接口
  // -------------------------------------------------------
  
  /**
   * 根据对象导出Xml元素
   */
  @Override
  public final String serializable() {
    return serializable(false, false);
  }
  
  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   */
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();
    
    String innerText = null;
    
    outString.append("<query>");
    
    // Table
    outString.append(String.format("<table><![CDATA[%1$s]]></table>", getTable()));
    
    // Fields
    innerText = "";
    
    for (String item : getFields()) {
      innerText += item + ",";
    }
    
    innerText = StringUtil.trimEnd(innerText, ",");
    
    outString.append(String.format("<fields><![CDATA[%1$s]]></fields>", innerText));
    
    // Where
    innerText = "";
    
    for (Map.Entry<String, Object> item : getWhere().entrySet()) {
      innerText +=
        "<key name=\"" + item.getKey() + "\" type=\"" + item.getValue().getClass() + "\" ><![CDATA[" + item.getValue()
          + "]]></key>";
    }
    
    outString.append(String.format("<where><![CDATA[%1$s]]></where>", innerText));
    
    // Orders
    innerText = "";
    
    for (String item : getOrders()) {
      innerText += item + ",";
    }
    
    innerText = StringUtil.trimEnd(innerText, ",");
    
    outString.append(String.format("<orders><![CDATA[%1$s]]></orders>", innerText));
    
    // length
    if (getLength() > 0) {
      outString.append(String.format("<length><![CDATA[%1$s]]></length>", getLength()));
    }
    
    outString.append("</query>");
    
    return outString.toString();
  }
  
  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  @Override
  public final void deserialize(Element element) {
    Node node = null;
    
    getVariables().clear();
    
    // Scence
    node = element.selectSingleNode("scene");
    
    getVariables().put("scene", (node == null) ? "default" : node.getText());
    
    // Table
    node = element.selectSingleNode("table");
    
    setTable((node == null) ? "" : node.getText());
    
    // Fields
    node = element.selectSingleNode("fileds");
    
    getFields().clear();
    
    if (node != null) {
      String[] fields = node.getText().split(",");
      
      for (String field : fields) {
        getFields().add(field);
      }
    }
    
    node = element.selectSingleNode("where");
    
    getWhere().clear();
    
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
        
        if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
          name = StringUtil.camelToUnderline(name);
        }
        
        getWhere().put(name, convertParamType(type, value));
      }
    }
    
    // Orders
    node = element.selectSingleNode("orders");
    
    getOrders().clear();
    
    if (node != null) {
      String[] orders = node.getText().split(",");
      
      for (String order : orders) {
        if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
          order = StringUtil.camelToUnderline(order);
        }
        getOrders().add(order);
      }
    }
    
    // Length
    node = element.selectSingleNode("length");
    
    if (node != null) {
      setLength(Integer.parseInt(node.getText()));
    }
  }
  
  // -------------------------------------------------------
  // 实现 SerializedJSON 接口
  // -------------------------------------------------------
  
  @Override
  public String toJSON() {
    return null;
  }
  
  @Override
  public void fromJSON(String json) {
    JSONObject data = JSON.parseObject(json);
    
    getVariables().clear();
    
    // Scence
    if (data.containsKey("scene")) {
      getVariables().put("scene", data.getString("scene"));
    } else {
      getVariables().put("scene", "default");
    }
    
    if (!data.containsKey("table") && !data.containsKey("fields")
      && !data.containsKey("where") && !data.containsKey("orders")) {
      // 快速模式对象结构
      // { "field1":"abc", "field2":0, field3:"1970-01-01" }
      getFields().clear();
      getWhere().clear();
      getOrders().clear();
      
      Set<String> keys = data.keySet();
      
      for (String key : keys) {
        String name, value;
        name = key;
        value = data.getString(key);
        if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
          name = StringUtil.camelToUnderline(name);
        }
        
        getWhere().put(name, value);
      }
    } else {
      // 复杂模式结构
      // {
      //   "fields":"name,type,modified_date",
      //   "where":[
      //     {"name":"search_text", "value":"%ss%"},
      //     {"name":"type", "value":"1", type:"int"}
      //   ],
      //   "orders":"modified_date desc"
      // }
      
      // Table
      if (data.containsKey("table")) {
        setTable(data.getString("table"));
      } else {
        setTable(TEXT_EMPTY);
      }
      
      // Fields
      getFields().clear();
      
      if (data.containsKey("fields")) {
        String[] fields = data.getString("fields").split(",");
        
        for (String field : fields) {
          if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
            field = StringUtil.camelToUnderline(field);
          }
          
          getFields().add(field);
        }
      }
      
      getWhere().clear();
      
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
          
          if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
            name = StringUtil.camelToUnderline(name);
          }
          
          getWhere().put(name, convertParamType(type, value));
        }
      }
      
      // Groups
      getGroups().clear();
      
      if (data.containsKey("groups")) {
        String[] groups = data.getString("groups").split(",");
        
        for (String group : groups) {
          if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
            group = StringUtil.camelToUnderline(group);
          }
          getGroups().add(StringUtil.toSafeSQL(group));
        }
      }
      
      // Orders
      getOrders().clear();
      
      if (data.containsKey("orders") && !StringUtil.isNullOrEmpty(data.getString("orders"))) {
        String[] orders = data.getString("orders").split(",");
      
        for (String order : orders) {
          if (KernelConfigurationView.getInstance().getDataQueryUnderlineCase()) {
            order = StringUtil.camelToUnderline(order);
          }
          
          getOrders().add(StringUtil.toSafeSQL(order));
        }
      }
      
      // Length
      if (data.containsKey("length")) {
        setLength(data.getInteger("length"));
      }
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
