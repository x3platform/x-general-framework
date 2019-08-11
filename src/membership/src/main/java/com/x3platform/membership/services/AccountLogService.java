package com.x3platform.membership.services;

import com.x3platform.membership.Account;
import com.x3platform.membership.AccountLog;

import com.x3platform.data.DataQuery;

import java.util.*;

/**
 * 帐号日志服务接口
 *
 * @author ruanyu
 */
public interface AccountLogService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link AccountLog} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(AccountLog entity);

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
   * @return {@link AccountLog} 实例的详细信息
   */
  AccountLog findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link AccountLog} 实例的详细信息
   */
  List<AccountLog> findAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param accountId 所属帐号标识
   * @return 所有相关 {@link AccountLog} 实例的详细信息
   */
  List<AccountLog> findAllByAccountId(String accountId);

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
   * 记录日志
   *
   * @param accountId 所属帐号标识
   * @param operationName 操作名称
   * @param description 描述信息
   * @return 消息代码 0 保存成功 | 1 保存失败
   */
  int log(String accountId, String operationName, String description);

  /**
   * 记录日志
   *
   * @param accountId 所属帐号标识
   * @param operationName 操作名称
   * @param description 描述信息
   * @param operationBy 操作者标识
   * @return 消息代码 0 保存成功 | 1 保存失败
   */
  int log(String accountId, String operationName, String description, String operationBy);

  /**
   * 记录日志
   *
   * @param accountId 所属帐号标识
   * @param operationName 操作名称
   * @param description 描述信息
   * @param operationBy 操作者标识
   * @param snapshotObject 原始对象
   * @return 消息代码 0 保存成功 | 1 保存失败
   */
  int log(String accountId, String operationName, String description, String operationBy, Account snapshotObject);
}
