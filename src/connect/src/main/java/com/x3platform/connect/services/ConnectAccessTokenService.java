package com.x3platform.connect.services;

import com.x3platform.connect.models.ConnectAccessToken;
import com.x3platform.data.DataQuery;

import java.util.*;

/**
 *
 */
public interface ConnectAccessTokenService {

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ConnectAccessToken Id号
   * @return 返回一个 ConnectAccessToken 实例的详细信息
   */
  ConnectAccessToken findOne(String id);

  /**
   * 查询某条记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  ConnectAccessToken findOneByAccountId(String appKey, String accountId);

  /**
   * 查询某条记录
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  ConnectAccessToken findOneByRefreshToken(String appKey, String refreshToken);

  /**
   * 查询账户所有登录情况， 包含多端应用
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回多个实例 ConnectAccessToken 的详细信息
   */
  List<ConnectAccessToken> findAllByRefreshToken(String appKey, String refreshToken);


  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 ConnectAccessToken 实例的详细信息
   */
  List<ConnectAccessToken> findAll(DataQuery query);

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param ConnectAccessToken 实例详细信息
   * @return ConnectAccessToken 实例详细信息
   */
  int save(ConnectAccessToken param);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int delete(String id);

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
   * 查询是否存在相关的记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 布尔值
   */
  boolean isExist(String appKey, String accountId);

  /**
   * 写入的帐号的访问令牌信息
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return
   */
  int write(String appKey, String accountId);

  /**
   * 刷新帐号的访问令牌
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @param expireDate   过期时间
   * @return
   */
  int refresh(String appKey, String refreshToken, Date expireDate);

  /**
   * 清理过期时间之前的缓存记录
   *
   * @param expiryTime 过期时间
   */
  int clear(Date expiryTime);

  /**
   * 清空缓存记录
   */
  int clear();
}
