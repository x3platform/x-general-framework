package com.x3platform.membership.mappers;

import com.x3platform.membership.AccountLog;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public interface AccountLogMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link {@link AccountLog} 实例的详细信息
   * @return 消息代码 0=表示成功
   */
  int insert(AccountLog record);

  /**
   * 修改记录
   *
   * @param record {@link AccountLog} 实例的详细信息
   * @return 消息代码 0=表示成功
   */
  int updateByPrimaryKey(AccountLog record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link AccountLog} 实例的详细信息
   */
  AccountLog selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link AccountLog} 实例的详细信息
   */
  List<AccountLog> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param accountId 所属帐号标识
   * @return 所有相关 {@link AccountLog} 实例的详细信息
   */
  List<AccountLog> findAllByAccountId(@Param("account_id") String accountId);

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
