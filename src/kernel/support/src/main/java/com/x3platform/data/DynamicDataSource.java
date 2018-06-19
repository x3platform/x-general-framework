package com.x3platform.data;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  public static String DEFAULT_DATASOURCE = "defaultDataSource";

  @Override
  protected Object determineCurrentLookupKey() {
    String dataSourceKey = DynamicDataSourceContextHolder.getDataSouce();
    if (dataSourceKey == null) {
      dataSourceKey = DEFAULT_DATASOURCE;
    }
    return dataSourceKey;
  }
}
