package com.x3platform.sync.mappers;

import com.x3platform.sync.models.SyncSetting;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruanyu
 */
public interface SyncSettingMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link SyncSetting} 实例的详细信息
   * @return 受影响的行数
   */
  int insert(SyncSetting record);

  /**
   * 修改记录
   *
   * @param record {@link SyncSetting} 实例的详细信息
   * @return 受影响的行数
   */
  int updateByPrimaryKey(SyncSetting record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 受影响的行数
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link SyncSetting} 实例的详细信息
   */
  SyncSetting selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link SyncSetting} 实例的详细信息
   */
  List<SyncSetting> findAll(Map params);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 获取有效的设置信息
   *
   * @return 所有 {@link SyncSetting} 实例的详细信息
   */
  List<SyncSetting> getSettings();
}
