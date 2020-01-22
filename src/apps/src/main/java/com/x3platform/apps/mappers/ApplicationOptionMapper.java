package com.x3platform.apps.mappers;
import com.x3platform.apps.models.ApplicationOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 */
@Mapper
public interface ApplicationOptionMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param 实例{@link ApplicationOption}详细信息
   */
  int insert(ApplicationOption param);

  /**
   * 修改记录
   *
   * @param param 实例{@link ApplicationOption}详细信息
   */
  int updateByPrimaryKey(ApplicationOption param);

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
   * @return 返回实例 ApplicationSetting 的详细信息
   */
  ApplicationOption selectByPrimaryKey(String id);

  /**
   * 根据应用 和 名称 查询对应数据
   * @param name
   * @param name
   */
  ApplicationOption findApplicationOptionByName(@Param("name") String name);

  /**
   * 根据应用 和 名称 查询对应数据
   * @param applicationId
   * @param name
   */
  ApplicationOption findApplicationOption(@Param("applicationId") String applicationId,@Param("name") String name);
  /**
   * 查询所有相关记录
   *
   * @param params 查询条件
   * @return 返回所有实例 ApplicationSetting 的详细信息
   */
  List<ApplicationOption> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有实例 ApplicationMenuLite 的详细信息
   */
  // List<ApplicationSettingLite> findAllQueryObject(String whereClause, int length);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @param keyword                   文本信息关键字匹配
   * @return 返回所有实例 ApplicationSetting 的详细信息
   */
  List<ApplicationOption> findAllByApplicationSettingGroupId(@Param("applicationSettingGroupId") String applicationSettingGroupId, @Param("keyword") String keyword);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @param keyword                     文本信息关键字匹配
   * @return 返回所有实例 ApplicationSetting 的详细信息
   */
  List<ApplicationOption> findAllByApplicationSettingGroupName(@Param("applicationSettingGroupName") String applicationSettingGroupName, @Param("keyword") String keyword);

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
