package com.x3platform.apps.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.apps.mappers.ApplicationSettingMapper;
import com.x3platform.apps.models.ApplicationSetting;
import com.x3platform.apps.services.ApplicationSettingService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class ApplicationSettingServiceImpl implements ApplicationSettingService {

  private static final String CACHE_KEY_GROUP_PREFIX = "x3platform:apps:application-setting:group:";

  private static final String CACHE_DICT_PREFIX = "x3platform:apps:application-setting:";

  private static final String CACHE_KEY_VALUE_PREFIX = "x3platform:apps:application-setting:value:";

  private static final String CACHE_KEY_TEXT_PREFIX = "x3platform:apps:application-setting:text:";

  private static final String DIGITAL_NUMBER_KEY_CODE = "Table_Application_Setting_Key_Code";

  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationSettingMapper provider = null;

  /**
   * 获取缓存的数据
   */
  private ApplicationSetting getCacheItem(String applicationId, String applicationSettingGroupName,
    String fetchItemType, String fetchItemValue) {
    ApplicationSetting result = null;

    String dict = null;
    String key = String.format("%1$s$%2$s$%3$s$%4$s", applicationId, applicationSettingGroupName,
      fetchItemType, fetchItemValue);

    // 查找缓存数据
    if ("text".equals(fetchItemType)) {
      dict = CACHE_DICT_PREFIX + "text";
    } else if ("value".equals(fetchItemType)) {
      dict = CACHE_DICT_PREFIX + "value";
    }

    if (StringUtil.isNullOrEmpty(dict)) {
      return null;
    }

    if (CachingManager.contains(dict, key)) {
      result = (ApplicationSetting) CachingManager.get(dict, key);
    }

    if (result == null) {
      List<ApplicationSetting> list = findAllByApplicationSettingGroupName(applicationSettingGroupName);

      for (ApplicationSetting item : list) {
        addCacheItem(item);
      }
    }

    return result;
  }

  /**
   * 添加缓存项
   */
  private void addCacheItem(ApplicationSetting item) {
    String prefix = String.format("%1$s$%2$s", item.getApplicationId(), item.getApplicationSettingGroupName());

    String dict = CACHE_DICT_PREFIX + "text";
    String key = String.format("%1$s$text$%2$s", prefix, item.getText());

    if (CachingManager.contains(dict, key)) {
      KernelContext.getLog().warn("dict:{}, key:{} is exists.", dict, key);
    } else {
      CachingManager.set(dict, key, item);
    }

    dict = CACHE_DICT_PREFIX + "value";
    key = String.format("%1$s$value$%2$s", prefix, item.getValue());

    if (CachingManager.contains(dict, key)) {
      KernelContext.getLog().warn("dict:{}, key:{} is exists.", dict, key);
    } else {
      CachingManager.set(dict, key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(ApplicationSetting item) {
    String prefix = String.format("%1$s$%2$s", item.getApplicationId(), item.getApplicationSettingGroupName());

    String dict = CACHE_DICT_PREFIX + "text";
    String key = String.format("%1$s$text$%2$s", prefix, item.getText());

    if (CachingManager.contains(dict, key)) {
      CachingManager.delete(dict, key);
    }

    dict = CACHE_DICT_PREFIX + "value";
    key = String.format("%1$s$value$%2$s", prefix, item.getValue());

    if (CachingManager.contains(dict, key)) {
      CachingManager.delete(dict, key);
    }
  }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 ApplicationSetting 详细信息
   * @return 实例 ApplicationSetting 详细信息
   */
  @Override
  public int save(ApplicationSetting entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (provider.selectByPrimaryKey(id) == null) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }
      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    // 保存数据后, 更新缓存信息
    entity = provider.selectByPrimaryKey(entity.getId());

    if (entity != null) {
      removeCacheItem(entity);

      addCacheItem(entity);
    }

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    ApplicationSetting entity = provider.selectByPrimaryKey(id);

    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);

      // 删除数据库记录
      int affectedRows = provider.deleteByPrimaryKey(id);

      KernelContext.getLog().debug("delete entity id:'" + id + "', affectedRows:" + affectedRows);
    }

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 相关实例 {@link ApplicationSetting} 的详细信息
   */
  @Override
  public ApplicationSetting findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @return 相关实例 {@link ApplicationSetting} 的详细信息
   */
  @Override
  public List<ApplicationSetting> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @return 相关实例 {@link ApplicationSetting} 的详细信息
   */
  @Override
  public List<ApplicationSetting> findAllByApplicationSettingGroupId(String applicationSettingGroupId) {
    return provider.findAllByApplicationSettingGroupId(applicationSettingGroupId, null);
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @param keyword 文本信息关键字匹配
   * @return 相关实例 {@link ApplicationSetting} 的详细信息
   */
  @Override
  public List<ApplicationSetting> findAllByApplicationSettingGroupId(String applicationSettingGroupId, String keyword) {
    if (!StringUtil.isNullOrEmpty(keyword)) {
      keyword = "%" + keyword + "%";
    }

    return provider.findAllByApplicationSettingGroupId(applicationSettingGroupId, keyword);
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @return 相关实例 {@link ApplicationSetting} 的详细信息
   */
  @Override
  public List<ApplicationSetting> findAllByApplicationSettingGroupName(String applicationSettingGroupName) {
    return findAllByApplicationSettingGroupName(applicationSettingGroupName, null);
  }

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @param keyword 文本信息关键字匹配
   * @return 相关实例 {@link ApplicationSetting}的详细信息
   */
  @Override
  public List<ApplicationSetting> findAllByApplicationSettingGroupName(String applicationSettingGroupName,
    String keyword) {
    List<ApplicationSetting> list = null;

    String key = CACHE_KEY_GROUP_PREFIX + applicationSettingGroupName;

    // 设置 SQL 条件
    String like = null;

    if (!StringUtil.isNullOrEmpty(keyword)) {
      like = "%" + keyword + "%";
    }

    if (CachingManager.contains(key)) {
      list = (List<ApplicationSetting>) CachingManager.get(key);
    } else {
      list = provider.findAllByApplicationSettingGroupName(applicationSettingGroupName, like);

      if (!list.isEmpty()) {
        CachingManager.set(key, list);
      }
    }

    return list;
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 根据配置的文本获取值信息
   */
  @Override
  public String getText(String applicationId, String applicationSettingGroupName, String value) {
    ApplicationSetting entity = getCacheItem(applicationId, applicationSettingGroupName, "value", value);

    // 如果缓存中未找到相关数据，则查找数据库内容
    return entity == null ? provider.getText(applicationId, applicationSettingGroupName, value) : entity.getText();
  }

  /**
   * 根据配置的文本获取值信息
   */
  @Override
  public String getValue(String applicationId, String applicationSettingGroupName, String text) {
    ApplicationSetting entity = getCacheItem(applicationId, applicationSettingGroupName, "text", text);

    // 如果缓存中未找到相关数据，则查找数据库内容
    return entity == null ? provider.getValue(applicationId, applicationSettingGroupName, text) : entity.getValue();
  }
}
