package com.x3platform.security.authority.mappers;

import com.x3platform.security.authority.Authority;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface AuthorityMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link Authority} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int insert(Authority record);

  /**
   * 修改记录
   *
   * @param record {@link Authority} 实例的详细信息
   * @return 消息代码 0-成功
   */
  int updateByPrimaryKey(Authority record);

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
   * @return 一个 {@link Authority} 实例的详细信息
   */
  Authority selectByPrimaryKey(String id);

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return {@link Authority} 实例的详细信息
   */
  Authority findOneByName(@Param("name") String name);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link Authority} 实例的详细信息
   */
  List<Authority> findAll(Map params);

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
