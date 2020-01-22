package com.x3platform.sync.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.data.DataQuery;
import com.x3platform.sync.mappers.SyncSettingMapper;
import com.x3platform.sync.models.SyncSetting;
import com.x3platform.sync.services.SyncSettingService;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class SyncSettingServiceImpl implements SyncSettingService {

  /**
   * 数据提供器
   */
  @Autowired
  private SyncSettingMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link SyncSetting} 实例的详细信息
   * @return {@link SyncSetting} 实例的详细信息
   */
  @Override
  public int save(SyncSetting entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (provider.selectByPrimaryKey(id) == null) {
      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    int affectedRows = provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link SyncSetting} 实例的详细信息
   */
  @Override
  public SyncSetting findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link SyncSetting} 实例的详细信息
   */
  @Override
  public List<SyncSetting> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
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
   * 获取有效的设置信息
   *
   * @return 所有 {@link SyncSetting} 实例的详细信息
   */
  @Override
  public List<SyncSetting> getSettings() {
    return provider.getSettings();
  }
}
