package com.x3platform.security.authority.services;

import com.x3platform.security.authority.Authority;

import com.x3platform.data.DataQuery;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public interface AuthorityService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link Authority} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(Authority entity);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link Authority} 实例的详细信息
   */
  Authority findOne(String id);

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return {@link Authority} 实例的详细信息
   */
  Authority findOneByName(String name);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link Authority} 实例的详细信息
   */
  List<Authority> findAll(DataQuery query);

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
