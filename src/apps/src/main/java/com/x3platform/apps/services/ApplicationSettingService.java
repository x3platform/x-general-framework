package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationSetting;
import com.x3platform.data.DataQuery;
import java.util.List;

/**
 */
public interface ApplicationSettingService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例{@link ApplicationSetting}详细信息
   * @return 实例  ApplicationSetting 详细信息
   */
  int save(ApplicationSetting param);

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 实例  ApplicationSetting 的详细信息
   */
  ApplicationSetting findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @return 相关实例  ApplicationSetting 的详细信息
   */
  // List<ApplicationSetting> findAll();

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 相关实例 {@link ApplicationSetting} 的详细信息
   */
  List<ApplicationSetting> findAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 相关实例 ApplicationMenuLite 的详细信息
   */
  // List<ApplicationSettingLite> findAllQueryObject(String whereClause, int length);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @return 相关实例  ApplicationSetting 的详细信息
   */
  List<ApplicationSetting> findAllByApplicationSettingGroupId(String applicationSettingGroupId);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @param keyword                   文本信息关键字匹配
   * @return 相关实例  ApplicationSetting 的详细信息
   */
  List<ApplicationSetting> findAllByApplicationSettingGroupId(String applicationSettingGroupId, String keyword);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @return 相关实例  ApplicationSetting 的详细信息
   */
  List<ApplicationSetting> findAllByApplicationSettingGroupName(String applicationSettingGroupName);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @param keyword                     文本信息关键字匹配
   * @return 相关实例  ApplicationSetting 的详细信息
   */
  List<ApplicationSetting> findAllByApplicationSettingGroupName(String applicationSettingGroupName, String keyword);

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
   * 根据配置的文本获取值信息
   *
   * @param applicationId 应用标识
   * @param applicationSettingGroupName 应用设置分组名称
   * @param value 值
   * @return 文本
   */
  String getText(String applicationId, String applicationSettingGroupName, String value);

  /**
   * 根据配置的文本获取值信息
   *
   * @param applicationId 应用标识
   * @param applicationSettingGroupName 应用设置分组名称
   * @param text 文本
   * @return 值
   */
  String getValue(String applicationId, String applicationSettingGroupName, String text);
}
