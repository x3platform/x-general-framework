package com.x3platform.membership.services;

import com.x3platform.membership.AccountGroupRelation;
import com.x3platform.membership.Group;

import com.x3platform.data.DataQuery;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface GroupService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link Group} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int save(Group entity);

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
   * @return {@link Group} 实例的详细信息
   */
  Group findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link Group} 实例的详细信息
   */
  List<Group> findAll(DataQuery query);

  /**
   * 查询某个用户所在的所有群组信息
   *
   * @param accountId 帐号标识
   * @return 所有 {@link Group} 实例的详细信息
   */

  List<Group> findAllByAccountId(String accountId);

  /**
   * 查询所有相关记录
   *
   * @param catalogItemId 分类节点标识
   * @return 返回所有实例 {@link Group} 的详细信息
   */
  List<Group> findAllByCatalogItemId(String catalogItemId);

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
   * @param name 名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 群组全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(String globalName);

  /**
   * 检测是否存在相关的记录
   *
   * @param id 群组标识
   * @param name 群组名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int rename(String id, String name);

  /**
   * 设置全局名称
   *
   * @param id 群组标识
   * @param globalName 全局名称
   * @return 修改成功, 返回 0, 修改失败, 返回 1.
   */
  int setGlobalName(String id, String globalName);

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setEnableEmail(String accountId, int status);

  // -------------------------------------------------------
  // 设置帐号和群组关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关群组的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, GroupId, IsDefault, BeginDate, EndDate
   */
  List<AccountGroupRelation> findAllRelationByAccountId(String accountId);

  /**
   * 根据群组查询相关帐号的关系
   *
   * @param groupId 群组标识
   * @return Table Columns：AccountId, GroupId, IsDefault, BeginDate, EndDate
   */
  List<AccountGroupRelation> findAllRelationByGroupId(String groupId);

  /**
   * 添加帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @return 消息代码
   */
  int addRelation(String accountId, String groupId);

  /**
   * 添加帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @param beginDate 启用时间
   * @param endDate 停用时间
   * @return 消息代码
   */
  int addRelation(String accountId, String groupId, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate);

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @param endDate 新的截止时间
   * @return 消息代码
   */
  int extendRelation(String accountId, String groupId, java.time.LocalDateTime endDate);

  /**
   * 移除帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @return 消息代码
   */
  int removeRelation(String accountId, String groupId);

  /**
   * 移除帐号已过期的群组关系
   *
   * @param accountId 帐号标识
   * @return 消息代码
   */
  int removeExpiredRelation(String accountId);

  /**
   * 移除帐号所有相关群组的关系
   *
   * @param accountId 帐号标识
   * @return 消息代码
   */
  int removeAllRelation(String accountId);

  /**
   * 清理群组与帐号的关系
   *
   * @param groupId 群组标识
   * @return 消息代码
   */
  int clearupRelation(String groupId);
}
