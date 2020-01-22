package com.x3platform.membership.mappers;

import com.x3platform.membership.Organization;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public interface OrganizationMapper {
    
  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link Organization} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int insert(Organization record);
  
  /**
   * 修改记录
   *
   * @param record {@link Organization} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int updateByPrimaryKey(Organization record);
    
  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0-成功
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------
    
  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link Organization} 实例的详细信息
   */
  Organization selectByPrimaryKey(String id);
    
  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link Organization} 实例的详细信息
   */
  List<Organization> findAll(Map params);
  
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
