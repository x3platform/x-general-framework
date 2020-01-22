package com.x3platform.membership.mappers;

import com.x3platform.membership.AccountGroupRelation;
import com.x3platform.membership.Group;

import java.time.LocalDateTime;
import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface GroupMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link Group} 实例的详细信息
   * @return 受影响的行数
   */
  int insert(Group record);

  /**
   * 修改记录
   *
   * @param record {@link Group} 实例的详细信息
   * @return 受影响的行数
   */
  int updateByPrimaryKey(Group record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 受影响的行数
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link Group} 实例的详细信息
   */
  Group selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link Group} 实例的详细信息
   */
  List<Group> findAll(Map params);

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
   * @return 所有实例{@link Group}的详细信息
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
  boolean isExist(@Param("id") String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 名称
   * @return 布尔值
   */
  boolean isExistName(@Param("name") String name);

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
   * @return 受影响的行数
   */
  int rename(String id, String name);

  /**
   * 设置全局名称
   *
   * @param id 群组标识
   * @param globalName 全局名称
   * @return 受影响的行数
   */
  int setGlobalName(String id, String globalName);

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 受影响的行数
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
  List<AccountGroupRelation> findAllRelationByAccountId(@Param("account_id") String accountId);

  /**
   * 根据群组查询相关帐号的关系
   *
   * @param groupId 群组标识
   * @return Table Columns：AccountId, GroupId, IsDefault, BeginDate, EndDate
   */
  List<AccountGroupRelation> findAllRelationByGroupId(@Param("group_id") String groupId);

  /**
   * 添加帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @param beginDate 启用时间
   * @param endDate 停用时间
   * @return 受影响的行数
   */
  int addRelation(@Param("account_id") String accountId,
    @Param("group_id") String groupId,
    @Param("begin_date") LocalDateTime beginDate,
    @Param("end_date") LocalDateTime endDate);

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @param endDate 新的截止时间
   * @return 受影响的行数
   */
  int extendRelation(@Param("account_id") String accountId,
    @Param("group_id") String groupId,
    @Param("end_date") LocalDateTime endDate);

  /**
   * 移除帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @return 受影响的行数
   */
  int removeRelation(@Param("account_id") String accountId, @Param("group_id") String groupId);

  /**
   * 移除帐号已过期的群组关系
   *
   * @param accountId 帐号标识
   * @return 受影响的行数
   */
  int removeExpiredRelation(@Param("account_id") String accountId);

  /**
   * 移除帐号所有相关群组的关系
   *
   * @param accountId 帐号标识
   * @return 受影响的行数
   */
  int removeAllRelation(@Param("account_id") String accountId);

  /**
   * 检测帐号是否有与群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   */
  boolean hasRelation(@Param("account_id") String accountId, @Param("group_id") String groupId);

  /**
   * 清理群组与帐号的关系
   *
   * @param groupId 群组标识
   * @return 受影响的行数
   */
  int clearupRelation(@Param("group_id") String groupId);
}
