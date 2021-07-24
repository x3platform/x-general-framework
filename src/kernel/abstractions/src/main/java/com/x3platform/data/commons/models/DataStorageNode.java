package com.x3platform.data.commons.models;

import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 *
 * @author ruanyu
 */
public class DataStorageNode
{
  /**
   * 默认构造函数
   */
  public DataStorageNode()
  {
  }

  private String id = "";

  /**
   * 获取唯一标识
   * @return 唯一标识
   */
  public String getId() {
    return id;
  }

  /**
   * 设置唯一标识
   * @param value 值
   */
  public void setId(String value) {
    id = value;
  }

  private String applicationId = "";

  /**
   * 获取所属应用
   * @return 所属应用
   */
  public String getApplicationId() {
    return applicationId;
  }

  /**
   * 设置所属应用
   * @param value 值
   */
  public void setApplicationId(String value) {
    applicationId = value;
  }

  private String accountId = "";

  /**
   * 获取所属帐号
   * @return 所属帐号
   */
  public String getAccountId() {
    return accountId;
  }

  /**
   * 设置所属帐号
   * @param value 值
   */
  public void setAccountId(String value) {
    accountId = value;
  }

  private String storageSchemaId = "";

  /**
   * 获取存储架构唯一标识
   * @return 存储架构唯一标识
   */
  public String getStorageSchemaId() {
    return storageSchemaId;
  }

  /**
   * 设置存储架构唯一标识
   * @param value 值
   */
  public void setStorageSchemaId(String value) {
    storageSchemaId = value;
  }

  private String name = "";

  /**
   * 获取名称
   * @return 名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置名称
   * @param value 值
   */
  public void setName(String value) {
    name = value;
  }

  private String type = "";

  /**
   * 获取类型
   * @return 类型
   */
  public String getType() {
    return type;
  }

  /**
   * 设置类型
   * @param value 值
   */
  public void setType(String value) {
    type = value;
  }

  private String providerName = "";

  /**
   * 获取提供器名称
   * @return 提供器名称
   */
  public String getProviderName() {
    return providerName;
  }

  /**
   * 设置提供器名称
   * @param value 值
   */
  public void setProviderName(String value) {
    providerName = value;
  }

  private String host = "";

  /**
   * 获取服务器
   * @return 服务器
   */
  public String getHost() {
    return host;
  }

  /**
   * 设置服务器
   * @param value 值
   */
  public void setHost(String value) {
    host = value;
  }

  private int port = 0;

  /**
   * 获取端口
   * @return 端口
   */
  public int getPort() {
    return port;
  }

  /**
   * 设置端口
   * @param value 值
   */
  public void setPort(int value) {
    port = value;
  }

  private String database = "";

  /**
   * 获取数据库
   * @return 数据库
   */
  public String getDatabase() {
    return database;
  }

  /**
   * 设置数据库
   * @param value 值
   */
  public void setDatabase(String value) {
    database = value;
  }

  private String username = "";

  /**
   * 获取用户名
   * @return 用户名
   */
  public String getUsername() {
    return username;
  }

  /**
   * 设置用户名
   * @param value 值
   */
  public void setUsername(String value) {
    username = value;
  }

  private String password = "";

  /**
   * 获取密码
   * @return 密码
   */
  public String getPassword() {
    return password;
  }

  /**
   * 设置密码
   * @param value 值
   */
  public void setPassword(String value) {
    password = value;
  }

  private String connectionString = "";

  /**
   * 获取连接字符串格式
   * @return 连接字符串格式
   */
  public String getConnectionString() {
    return connectionString;
  }

  /**
   * 设置连接字符串格式
   * @param value 值
   */
  public void setConnectionString(String value) {
    connectionString = value;
  }

  private int connectionState = 0;

  /**
   * 获取连接状态
   * @return 连接状态
   */
  public int getConnectionState() {
    return connectionState;
  }

  /**
   * 设置连接状态
   * @param value 值
   */
  public void setConnectionState(int value) {
    connectionState = value;
  }

  private String orderId = "";

  /**
   * 获取排序
   * @return 排序
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * 设置排序
   * @param value 值
   */
  public void setOrderId(String value) {
    orderId = value;
  }

  private int status = 0;

  /**
   * 获取状态
   * @return 状态
   */
  public int getStatus() {
    return status;
  }

  /**
   * 设置状态
   * @param value 值
   */
  public void setStatus(int value) {
    status = value;
  }

  private String remark = "";

  /**
   * 获取备注
   * @return 备注
   */
  public String getRemark() {
    return remark;
  }

  /**
   * 设置备注
   * @param value 值
   */
  public void setRemark(String value) {
    remark = value;
  }

  private Date modifiedDate = DateUtil.getDefaultDate();

  /**
   * 获取修改时间
   * @return 修改时间
   */
  public Date getModifiedDate() {
    return modifiedDate;
  }

  /**
   * 设置修改时间
   * @param value 值
   */
  public void setModifiedDate(Date value) {
    modifiedDate = value;
  }

  private Date createdDate = DateUtil.getDefaultDate();

  /**
   * 获取创建时间
   * @return 创建时间
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * 设置创建时间
   * @param value 值
   */
  public void setCreatedDate(Date value) {
    createdDate = value;
  }
}
