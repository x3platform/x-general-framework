package com.x3platform.connect.models;

import com.x3platform.SerializedObject;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.dom4j.Element;

/**
 * 应用连接调用信息
 */
public class ConnectCall implements SerializedObject {
  public ConnectCall() {
    this.id = StringUtil.toDateFormat(java.time.LocalDateTime.now(), "yyyyMMddHHmmssff") + StringUtil.toRandom("0123456789", 6);
  }

  public ConnectCall(String appKey, String requestUri, String requestData) {
    this();
    this.appKey = appKey;
    this.requestUri = requestUri;
    this.requestData = requestData;
  }

  private String id;

  /**
   * 标识
   */
  public String getId() {
    return this.id;
  }

  public void setId(String value) {
    this.id = value;
  }

  private String accessToken;

  /**
   * 访问令牌
   */
  public String getAccessToken() {
    return this.accessToken;
  }

  public void setAccessToken(String value) {
    this.accessToken = value;
  }

  private String appKey;

  /**
   * 应用标识
   */
  public String getAppKey() {
    return this.appKey;
  }

  public void setAppKey(String value) {
    this.appKey = value;
  }

  private String requestUri = "";

  /**
   * 请求的地址信息
   */
  public String getRequestUri() {
    return this.requestUri;
  }

  public void setRequestUri(String value) {
    this.requestUri = value;
  }

  private String requestData = "";

  /**
   * 请求的数据信息
   */
  public String getRequestData() {
    return this.requestData;
  }

  public void setRequestData(String value) {
    this.requestData = value;
  }

  private String responseData = "";

  /**
   * 响应的数据信息
   */
  public String getResponseData() {
    return this.responseData;
  }

  public void setResponseData(String value) {
    this.responseData = value;
  }

  private java.time.LocalDateTime startTime = java.time.LocalDateTime.MIN;

  /**
   * 开始时间
   */
  public java.time.LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(java.time.LocalDateTime value) {
    startTime = value;
  }

  private java.time.LocalDateTime finishTime = java.time.LocalDateTime.MIN;

  /**
   * 结束时间
   */
  public java.time.LocalDateTime getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(java.time.LocalDateTime value) {
    finishTime = value;
  }

  private double timeSpan;

  /**
   * 时间跨度
   */
  public double getTimeSpan() {
    return timeSpan;
  }

  public void setTimeSpan(double value) {
    timeSpan = value;
  }

  private String ip = "0.0.0.0";

  /**
   */
  public String getIP() {
    return ip;
  }

  public void setIP(String value) {
    ip = value;
  }

  private int returnCode = 0;

  /**
   */
  public int getReturnCode() {
    return this.returnCode;
  }

  public void setReturnCode(int value) {
    this.returnCode = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   */
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  // -------------------------------------------------------
  // 计算时间运行时间
  // -------------------------------------------------------

  /**
   */
  public void Start() {
    this.setStartTime(java.time.LocalDateTime.now());
  }

  /**
   */
  public void Finish() {
    this.setFinishTime(java.time.LocalDateTime.now());

    // this.TimeSpan = this.timeSpan.Subtract(new TimeSpan(this.FinishTime.Ticks)).Duration().TotalSeconds;
    // this.setTimeSpan(DateUtil.getTimeSpan(getStartTime(), getFinishTime()).TotalSeconds);
  }

  // -------------------------------------------------------
  // Xml 元素的导入和导出
  // -------------------------------------------------------

  ///#region 函数:Deserialize(XmlElement element)

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  @Override
  public void deserialize(Element element) {
    StringBuilder outString = new StringBuilder();

//    this.setId(element.SelectSingleNode("id").InnerText);
//    this.setAppKey(element.SelectSingleNode("appKey").InnerText);
//    this.setRequestUri(element.SelectSingleNode("requestUri").InnerText);
//    this.setRequestData(StringHelper.FromBase64(element.SelectSingleNode("requestData").InnerText));
//    this.setStartTime(java.time.LocalDateTime.parse(element.SelectSingleNode("startTime").InnerText));
//    this.setFinishTime(java.time.LocalDateTime.parse(element.SelectSingleNode("finishTime").InnerText));
//    this.setTimeSpan(Double.parseDouble(element.SelectSingleNode("timeSpan").InnerText));
//    this.setIP(element.SelectSingleNode("ip").InnerText);
//    this.setReturnCode(Integer.parseInt(element.SelectSingleNode("returnCode").InnerText));
//    this.setCreatedDate(java.time.LocalDateTime.parse(element.SelectSingleNode("createdDate").InnerText));
  }

  /**
   * 根据对象导出Xml元素
   *
   * @return
   */
  @Override
  public String serializable() {
    return serializable(false, false);
  }

  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   * @return
   */
  @Override
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    outString.append("<connectCall>");
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", this.getId()));
    outString.append(String.format("<appKey><![CDATA[%1$s]]></appKey>", this.getAppKey()));
    outString.append(String.format("<requestUri><![CDATA[%1$s]]></requestUri>", this.getRequestUri()));
    outString.append(String.format("<requestData><![CDATA[%1$s]]></requestData>", StringUtil.toBase64(this.getRequestData())));
    outString.append(String.format("<startTime><![CDATA[%1$s]]></startTime>", this.getStartTime()));
    outString.append(String.format("<finishTime><![CDATA[%1$s]]></finishTime>", this.getFinishTime()));
    outString.append(String.format("<timeSpan><![CDATA[%1$s]]></timeSpan>", this.getTimeSpan()));
    outString.append(String.format("<ip><![CDATA[%1$s]]></ip>", this.getIP()));
    outString.append(String.format("<returnCode><![CDATA[%1$s]]></returnCode>", this.getReturnCode()));
    outString.append(String.format("<createdDate><![CDATA[%1$s]]></createdDate>", this.getCreatedDate()));
    outString.append("</connectCall>");

    return outString.toString();
  }
}
