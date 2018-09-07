package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationSettingInfo;
import com.x3platform.apps.models.ApplicationSettingQueryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 */

public interface ApplicationSettingMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param 实例<see cref="ApplicationSettingInfo"/>详细信息
   */
  int insert(ApplicationSettingInfo param);

  /**
   * 修改记录
   *
   * @param param 实例<see cref="ApplicationSettingInfo"/>详细信息
   */
  int updateByPrimaryKey(ApplicationSettingInfo param);

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回实例 ApplicationSettingInfo 的详细信息
   */
  ApplicationSettingInfo selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询条件
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  // List<ApplicationSettingInfo> findAll(String whereClause, int length);
  List<ApplicationSettingInfo> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有实例 ApplicationMenuQueryInfo 的详细信息
   */
  // List<ApplicationSettingQueryInfo> findAllQueryObject(String whereClause, int length);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @param keyword                   文本信息关键字匹配
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAllByApplicationSettingGroupId(@Param("applicationSettingGroupId") String applicationSettingGroupId, @Param("keyword") String keyword);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @param keyword                     文本信息关键字匹配
   * @return 返回所有实例 ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAllByApplicationSettingGroupName(@Param("applicationSettingGroupName") String applicationSettingGroupName, @Param("keyword") String keyword);

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
   * @param applicationId
   * @param applicationSettingGroupName
   * @param value
   * @return
   */
  String getText(@Param("applicationId") String applicationId, @Param("applicationSettingGroupName") String applicationSettingGroupName, @Param("value") String value);

  /**
   * 根据配置的文本获取值信息
   *
   * @param applicationId
   * @param applicationSettingGroupName
   * @param text
   * @return
   */
  String getValue(@Param("applicationId") String applicationId, @Param("applicationSettingGroupName") String applicationSettingGroupName, @Param("text") String text);
}
