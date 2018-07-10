package com.x3platform.membership.services;

import java.util.*;

import com.x3platform.membership.models.*;

/**
 */
public interface ISettingService {

  //-------------------------------------------------------
  // 保存 删除
  //-------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例<see cref="SettingInfo"/>详细信息
   * @return 实例<see cref="SettingInfo"/>详细信息
   */
  SettingInfo save(SettingInfo param);

  /**
   * 删除记录
   *
   * @param ids 实例的标识,多条记录以逗号分开
   */
  void delete(String ids);

  //-------------------------------------------------------
  // 查询
  //-------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回实例<see cref="SettingInfo"/>的详细信息
   */
  SettingInfo findOne(String id);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param settingGroupId 参数分组标识
   * @return 返回所有实例<see cref="SettingInfo"/>的详细信息
   */
  List<SettingInfo> findAllBySettingGroupId(String settingGroupId);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param settingGroupId 参数分组标识
   * @param keyword        文本信息关键字匹配
   * @return 返回所有实例<see cref="SettingInfo"/>的详细信息
   */
  List<SettingInfo> findAllBySettingGroupId(String settingGroupId, String keyword);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param settingGroupName 参数分组名称
   * @return 返回所有实例<see cref="SettingInfo"/>的详细信息
   */
  List<SettingInfo> findAllBySettingGroupName(String settingGroupName);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param settingGroupName 参数分组名称
   * @param keyword          文本信息关键字匹配
   * @return 返回所有实例<see cref="SettingInfo"/>的详细信息
   */
  List<SettingInfo> findAllBySettingGroupName(String settingGroupName, String keyword);

  //-------------------------------------------------------
  // 自定义功能
  //-------------------------------------------------------

  /**
   * 查询是否存在相关的记录.
   *
   * @param id 标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 根据配置的文本获取值信息
   *
   * @param settingGroupName
   * @param value
   * @return
   */
  String getText(String settingGroupName, String value);

  /**
   * 根据配置的文本获取值信息
   *
   * @param settingGroupName
   * @param text
   * @return
   */
  String getValue(String settingGroupName, String text);
}
