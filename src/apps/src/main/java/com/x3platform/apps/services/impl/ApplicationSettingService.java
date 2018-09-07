package com.x3platform.apps.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.mappers.ApplicationSettingMapper;
import com.x3platform.apps.models.ApplicationSettingInfo;
import com.x3platform.apps.models.ApplicationSettingQueryInfo;
import com.x3platform.apps.services.IApplicationSettingService;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 */
public class ApplicationSettingService implements IApplicationSettingService {
  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationSettingMapper provider = null;

  /**
   * 参数文本信息缓存存储
   */
  private HashMap<String, ApplicationSettingInfo> settingTextDictionary = new HashMap<String, ApplicationSettingInfo>();

  /**
   * 参数值信息缓存存储
   */
  private HashMap<String, ApplicationSettingInfo> settingValueDictionary = new HashMap<String, ApplicationSettingInfo>();

  private Object lockObject = new Object();

  /**
   * 同步本地数据信息
   */
  private void syncLocalDb() {
    // 初始化缓存
    if (settingTextDictionary.isEmpty()) {
      synchronized (lockObject) {
        if (settingTextDictionary.isEmpty()) {
          List<ApplicationSettingInfo> list = this.findAll();

          for (ApplicationSettingInfo item : list) {
            String prefix = String.format("%1$s$%2$s", item.getApplicationId(), item.getApplicationSettingGroupName());

            if (this.settingValueDictionary.containsKey(String.format("%1$s$text$%2$s", prefix, item.getText()))) {
              KernelContext.getLog().warn(String.format("%1$s$text$%2$s", prefix, item.getText()) + " is exists.");
            } else {
              this.settingTextDictionary.put(String.format("%1$s$text$%2$s", prefix, item.getText()), item);
            }

            if (this.settingValueDictionary.containsKey(String.format("%1$s$value$%2$s", prefix, item.getValue()))) {
              KernelContext.getLog().warn(String.format("%1$s$value$%2$s", prefix, item.getValue()) + " is exists.");
            } else {
              this.settingValueDictionary.put(String.format("%1$s$value$%2$s", prefix, item.getValue()), item);
            }
          }
        }
      }
    }
  }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 ApplicationSettingInfo 详细信息
   * @return 实例 ApplicationSettingInfo 详细信息
   */
  @Override
  public int save(ApplicationSettingInfo entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (this.provider.selectByPrimaryKey(id) == null) {
      affectedRows = this.provider.insert(entity);
    } else {
      affectedRows = this.provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'" + id + "', affectedRows:" + affectedRows);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    int affectedRows = this.provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'" + id + "', affectedRows:" + affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回实例 ApplicationSettingInfo 的详细信息
   */
  @Override
  public ApplicationSettingInfo findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  @Override
  public List<ApplicationSettingInfo> findAll() {
    return this.provider.findAll(new HashMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  // public List<ApplicationSettingInfo> findAll(String whereClause) {
  //  return findAll(whereClause, 0);
  // }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  // public List<ApplicationSettingInfo> findAll(String whereClause, int length) {
  //   return this.provider.findAll(whereClause, length);
  // }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有实例 ApplicationSettingQueryInfo 的详细信息
   */
  // public List<ApplicationSettingQueryInfo> findAllQueryObject(String whereClause, int length) {
  //   return this.provider.findAllQueryObject(whereClause, length);
  // }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  @Override
  public List<ApplicationSettingInfo> findAllByApplicationSettingGroupId(String applicationSettingGroupId) {
    return this.provider.findAllByApplicationSettingGroupId(applicationSettingGroupId, null);
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @param keyword                   文本信息关键字匹配
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  @Override
  public List<ApplicationSettingInfo> findAllByApplicationSettingGroupId(String applicationSettingGroupId, String keyword) {
    if (!StringUtil.isNullOrEmpty(keyword)) {
      keyword = "%" + keyword + "%";
    }

    return this.provider.findAllByApplicationSettingGroupId(applicationSettingGroupId, keyword);
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  @Override
  public List<ApplicationSettingInfo> findAllByApplicationSettingGroupName(String applicationSettingGroupName) {
    return this.provider.findAllByApplicationSettingGroupName(applicationSettingGroupName, null);
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @param keyword                     文本信息关键字匹配
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  @Override
  public List<ApplicationSettingInfo> findAllByApplicationSettingGroupName(String applicationSettingGroupName, String keyword) {
    if (!StringUtil.isNullOrEmpty(keyword)) {
      keyword = "%" + keyword + "%";
    }

    return this.provider.findAllByApplicationSettingGroupName(applicationSettingGroupName, keyword);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例 ApplicationSettingInfo
   */
  // public List<ApplicationSettingInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 分页函数
   *
   * @param startIndex  开始行索引数,由0开始统计
   * @param pageSize    页面大小
   * @param whereClause WHERE 查询条件
   * @param orderBy     ORDER BY 排序条件
   * @param rowCount    行数
   * @return 返回一个列表实例<see                                                                                                                                                                                                                                                               cref                                                                                                                               =                                                                                                                               "                                                                                                                               ApplicationSettingQueryInfo                                                                                                                               "                                                                                                                               />
   */
  // public List<ApplicationSettingQueryInfo> GetQueryObjectPaging(int startIndex, int pageSize, String whereClause, String orderBy, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetQueryObjectPaging(startIndex, pageSize, whereClause, orderBy, rowCount);
  // }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 根据配置的文本获取值信息
   *
   * @param applicationId
   * @param applicationSettingGroupName
   * @param value
   * @return
   */
  @Override
  public String getText(String applicationId, String applicationSettingGroupName, String value) {
    ApplicationSettingInfo param = FetchDictionaryItem(applicationId, applicationSettingGroupName, "value", value);

    // 如果缓存中未找到相关数据，则查找数据库内容
    return param == null ? this.provider.getText(applicationId, applicationSettingGroupName, value) : param.getText();
  }

  /**
   * 根据配置的文本获取值信息
   *
   * @param applicationId
   * @param applicationSettingGroupName
   * @param text
   * @return
   */
  @Override
  public String getValue(String applicationId, String applicationSettingGroupName, String text) {
    ApplicationSettingInfo param = FetchDictionaryItem(applicationId, applicationSettingGroupName, "value", text);

    // 如果缓存中未找到相关数据，则查找数据库内容
    return param == null ? this.provider.getValue(applicationId, applicationSettingGroupName, text) : param.getValue();
  }

  /**
   * 获取需要同步的数据
   */
  private ApplicationSettingInfo FetchDictionaryItem(String applicationId, String applicationSettingGroupName, String fetchItemType, String fetchItemValue) {
    ApplicationSettingInfo param = null;

    String cacheKey = String.format("%1$s$%2$s$%3$s$%4$s", applicationId, applicationSettingGroupName, fetchItemType, fetchItemValue);

    // 初始化缓存
    syncLocalDb();

    // 查找缓存数据
    if (fetchItemType.equals("text")) {
      if (this.settingTextDictionary.containsKey(cacheKey)) {
        param = this.settingTextDictionary.get(cacheKey);
      }
    } else if (fetchItemType.equals("value")) {
      if (this.settingValueDictionary.containsKey(cacheKey)) {
        param = this.settingValueDictionary.get(cacheKey);
      }
    }

    return param;
  }
}
