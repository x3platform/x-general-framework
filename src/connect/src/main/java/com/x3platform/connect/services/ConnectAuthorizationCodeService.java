package com.x3platform.connect.services;

import com.x3platform.connect.models.ConnectAuthorizationCode;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;

import java.util.*;

/**
 */
public interface ConnectAuthorizationCodeService {
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
  ConnectAuthorizationCode findOneByAccountId(String appKey, String accountId);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 ConnectAuthorizationCode 实例的详细信息
   */
  List<ConnectAuthorizationCode> findAll(DataQuery query);

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param ConnectAuthorizationCode 实例详细信息
   * @return 消息代码
   */
  int save(ConnectAuthorizationCode param);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码
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
   * 获取帐号的授权码
   *
   * @param appKey  应用标识
   * @param account 帐号信息
   * @return 授权码
   */
  String getAuthorizationCode(String appKey, Account account);
}
