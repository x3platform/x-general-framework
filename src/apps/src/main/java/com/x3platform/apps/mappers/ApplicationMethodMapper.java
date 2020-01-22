package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationMethod;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public interface ApplicationMethodMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link ApplicationMethod} 实例的详细信息
   * @return 返回消息代码 0=表示成功
   */
  int insert(ApplicationMethod record);

  /**
   * 修改记录
   *
   * @param record {@link ApplicationMethod} 实例的详细信息
   * @return 返回消息代码 0=表示成功
   */
  int updateByPrimaryKey(ApplicationMethod record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 返回消息代码 0=表示成功
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回一个 {@link ApplicationMethod} 实例的详细信息
   */
  ApplicationMethod selectByPrimaryKey(String id);

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return {@link ApplicationMethod} 实例的详细信息
   */
  ApplicationMethod findOneByName(String name);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 {@link ApplicationMethod} 实例的详细信息
   */
  List<ApplicationMethod> findAll(Map params);

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
}
