package com.x3platform.data;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author ruanyu
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
  
  /**
   * 所有数据库
   */
  protected Map<Object, Object> targetDataSources = null;
  
  public static String DEFAULT_DATASOURCE = "defaultDataSource";
  
  @Override
  protected Object determineCurrentLookupKey() {
    String dataSourceKey = DynamicDataSourceContextHolder.getDataSourceKey();
    if (dataSourceKey == null) {
      dataSourceKey = DEFAULT_DATASOURCE;
    }
    return dataSourceKey;
  }
  
  @Override
  public void setTargetDataSources(Map<Object, Object> targetDataSources) {
    
    this.targetDataSources = targetDataSources;
    
    super.setTargetDataSources(this.targetDataSources);
  }
  
  /**
   * 添加数据源
   */
  public void addDataSource(Object key, Object dataSource) {
    if (this.targetDataSources == null) {
      this.targetDataSources = new HashMap<Object, Object>();
    }
    
    this.targetDataSources.put(key, dataSource);
    
    super.setTargetDataSources(this.targetDataSources);
  }
  
  /**
   * 移除数据源
   **/
  public void removeDataSource(Object key) {
    if (this.targetDataSources == null) {
      return;
    }
    
    this.targetDataSources.remove(key);
    
    super.setTargetDataSources(this.targetDataSources);
  }
}
