package com.x3platform.data.commons.models;

import com.x3platform.util.*;

import java.math.*;
import java.util.*;

/**
 *
 * @author ruanyu
 */
public class DataStorageSchema
{
  /**
   * 默认构造函数
   */
  public DataStorageSchema()
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

  private String options = "";

  /**
   * 获取选项
   * @return 选项
   */
  public String getOptions() {
    return options;
  }

  /**
   * 设置选项
   * @param value 值
   */
  public void setOptions(String value) {
    options = value;
  }

  private String storageAdapterClassName = "";

  /**
   * 获取存储适配器类名
   * @return 存储适配器类名
   */
  public String getStorageAdapterClassName() {
    return storageAdapterClassName;
  }

  /**
   * 设置存储适配器类名
   * @param value 值
   */
  public void setStorageAdapterClassName(String value) {
    storageAdapterClassName = value;
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
