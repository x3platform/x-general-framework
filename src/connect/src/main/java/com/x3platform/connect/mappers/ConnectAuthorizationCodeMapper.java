package com.x3platform.connect.mappers;

import com.x3platform.connect.models.ConnectAuthorizationCode;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * @author ruanyu
 */
public interface ConnectAuthorizationCodeMapper {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 插入记录
   *
   * @param record 记录
   */
  int insert(ConnectAuthorizationCode record);

  /**
   * 更新记录
   *
   * @param record 记录
   */
  int updateByPrimaryKey(ConnectAuthorizationCode record);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ConnectAuthorizationCode Id号
   * @return 返回一个 ConnectAuthorizationCode 实例的详细信息
   */
  ConnectAuthorizationCode findOne(String id);

  /**
   * 查询某条记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 返回一个实例 ConnectAuthorizationCode 的详细信息
   */
  ConnectAuthorizationCode findOneByAccountId(@Param("app_key") String appKey, @Param("account_id") String accountId);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 ConnectAuthorizationCode 实例的详细信息
   */
  List<ConnectAuthorizationCode> findAll(Map params);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id ConnectAuthorizationCode 实例详细信息
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 布尔值
   */
  boolean isExistAccountId(@Param("app_key") String appKey, @Param("account_id") String accountId);
}
