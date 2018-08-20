package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationSettingInfo;
import com.x3platform.apps.models.ApplicationSettingQueryInfo;

import java.util.*;

/**
 */
public interface IApplicationSettingService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  ///#region 函数:Save(ApplicationSettingInfo param)

  /**
   * 保存记录
   *
   * @param param 实例<see cref="ApplicationSettingInfo"/>详细信息
   * @return 实例  ApplicationSettingInfo 详细信息
   */
  ApplicationSettingInfo save(ApplicationSettingInfo param);

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  void delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回实例  ApplicationSettingInfo 的详细信息
   */
  ApplicationSettingInfo findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAll();

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  // List<ApplicationSettingInfo> findAll(String whereClause);
  ///#endregion

  ///#region 函数:FindAll(string whereClause, int length)

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  // List<ApplicationSettingInfo> findAll(String whereClause, int length);
  ///#endregion

  ///#region 函数:FindAllQueryObject(string whereClause, int length)

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
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAllByApplicationSettingGroupId(String applicationSettingGroupId);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupId 参数分组标识
   * @param keyword                   文本信息关键字匹配
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAllByApplicationSettingGroupId(String applicationSettingGroupId, String keyword);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAllByApplicationSettingGroupName(String applicationSettingGroupName);

  /**
   * 根据参数分组信息查询所有相关记录
   *
   * @param applicationSettingGroupName 参数分组名称
   * @param keyword                     文本信息关键字匹配
   * @return 返回所有实例  ApplicationSettingInfo 的详细信息
   */
  List<ApplicationSettingInfo> findAllByApplicationSettingGroupName(String applicationSettingGroupName, String keyword);

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
   * @return 返回一个列表实例  ApplicationSettingInfo
   */
  // List<ApplicationSettingInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 分页函数
   *
   * @param startIndex  开始行索引数,由0开始统计
   * @param pageSize    页面大小
   * @param whereClause WHERE 查询条件
   * @param orderBy     ORDER BY 排序条件
   * @param rowCount    行数
   * @return 返回一个列表实例<see       cref   =   "   ApplicationSettingQueryInfo   "   />
   */
  // List<ApplicationSettingQueryInfo> GetQueryObjectPaging(int startIndex, int pageSize, String whereClause, String orderBy, tangible.RefObject<Integer> rowCount);

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
  String getText(String applicationId, String applicationSettingGroupName, String value);

  /**
   * 根据配置的文本获取值信息
   *
   * @param applicationId
   * @param applicationSettingGroupName
   * @param text
   * @return
   */
  String getValue(String applicationId, String applicationSettingGroupName, String text);
}
