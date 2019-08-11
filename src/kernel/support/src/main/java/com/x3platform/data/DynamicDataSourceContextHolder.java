package com.x3platform.data;


import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {

  public static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {

    /** * 将 dataSource 数据源的 key 作为默认数据源的 key */
    @Override
    protected String initialValue() {
      return "dataSource";
    }
  };

  public static List<Object> dataSourceKeys = new ArrayList<>();

  // public static void putDataSource(String name) {
  //  contextHolder.set(name);
  // }

  // public static String getDataSouce() {
  //  return contextHolder.get();
  // }

  public static void setDataSourceKey(String key) {
    contextHolder.set(key);
  }

  public static String getDataSourceKey() {
    return contextHolder.get();
  }

  public static void clearDataSourceKey() {
    contextHolder.remove();
  }

  /**
   * 判断指定 DataSrouce 当前是否存在
   *
   * @param key
   */
  public static boolean containsDataSourceKey(String key) {
    return dataSourceKeys.contains(key);
  }
}
