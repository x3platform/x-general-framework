package com.x3platform.membership.services;

import com.x3platform.AuthorizationObject;
import com.x3platform.AuthorizationObjectType;
import com.x3platform.AuthorizationScope;
import com.x3platform.membership.Account;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AuthorizationObjectService {

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条授权对象信息
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 返回一个 {@link AuthorizationObject} 实例的详细信息
   */
  AuthorizationObject findOne(String authorizationObjectType, String authorizationObjectId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询授权对象信息
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize 页面大小
   * @param query 数据查询参数
   * @param rowCount 记录行数
   * @return 返回一个列表
   */
  // DataTable Filter(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);


  /**
   * 检测是否存在相关的授权对象，授权对象名称不能重复。
   *
   * @param authorizationObjectName 授权对象名称
   * @return 布尔值
   */
  boolean isExistName(String authorizationObjectName);

  /**
   * 检测是否存在相关的授权对象，授权对象名称不能重复。
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectName 授权对象名称
   * @return 布尔值
   */
  boolean isExistName(String authorizationObjectType, String authorizationObjectName);

  /**
   * 获取实例化的授权对象
   *
   * @param corporationId 公司标识
   * @param authorizationScopeObjects 授权范围对象
   */
  // List<AuthorizationObject> getInstantiatedAuthorizationObjects(String corporationId,
  //  List<MembershipAuthorizationScopeObject> authorizationScopeObjects);

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param account 帐号信息
   * @return 布尔值
   */
  boolean hasAuthority(String scopeTableName, String entityId, String entityClassName, String authorityName,
    Account account);

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 布尔值
   */
  boolean hasAuthority(String scopeTableName, String entityId, String entityClassName, String authorityName,
    String authorizationObjectType, String authorizationObjectId);

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param account 帐号信息
   * @return 布尔值
   */
  // boolean hasAuthority(GenericSqlCommand command, String scopeTableName, String entityId, String entityClassName,
  //   String authorityName, Account account);

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 布尔值
   */
  // boolean hasAuthority(GenericSqlCommand command, String scopeTableName, String entityId, String entityClassName,
  //  String authorityName, String authorizationObjectType, String authorizationObjectId);

  /**
   * 添加实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  void addAuthorizationScopeByEntityId(String scopeTableName, String entityId, String entityClassName,
    String authorityName, String scopeText);

  /**
   * 添加实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  // void addAuthorizationScopeByEntityId(GenericSqlCommand command, String scopeTableName, String entityId,
  //  String entityClassName, String authorityName, String scopeText);

  /**
   * 添加授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识
   * @param entityClassName 实体类名称
   */
  void addAuthorizationScopeByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds, String entityClassName);

  /**
   * 移除实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  void removeAuthorizationScopeByEntityId(String scopeTableName, String entityId, String entityClassName,
    String authorityName);

  /**
   * 移除实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  // void removeAuthorizationScopeByEntityId(GenericSqlCommand command, String scopeTableName, String entityId,
  //  String entityClassName, String authorityName);

  /**
   * 移除授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   */
  void removeAuthorizationScopeByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName);

  /**
   * 配置实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  void bindAuthorizationScopeByEntityId(String scopeTableName, String entityId, String entityClassName,
    String authorityName, String scopeText);

  /**
   * 配置实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  // void bindAuthorizationScopeByEntityId(GenericSqlCommand command, String scopeTableName, String entityId,
  //  String entityClassName, String authorityName, String scopeText);

  /**
   * 配置授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识 多个对象以逗号隔开
   * @param entityClassName 实体类名称
   */
  void bindAuthorizationScopeByAuthorizationObjectIds(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds, String entityClassName);

  /**
   * 配置授权对象的权限信息 仅仅支持单个实体
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityId 仅支持单个实体
   * @param entityClassName 实体类名称
   */
  void bindAuthorizationScopeByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityId, String entityClassName);

  /**
   * 查询实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @return 授权列表信息
   */
  List<AuthorizationScope> getAuthorizationScopes(String scopeTableName, String entityId, String entityClassName,
    String authorityName);

  /**
   * 查询实体对象的授权信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  // List<AuthorizationScope> getAuthorizationScopes(GenericSqlCommand command,
  //  String scopeTableName, String entityId, String entityClassName, String authorityName);

  /**
   * 查询实体对象的权限范围的文本
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  String getAuthorizationScopeText(String scopeTableName, String entityId, String entityClassName,
    String authorityName);

  /**
   * 查询实体对象的权限范围的文本
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  // String getAuthorizationScopeText(GenericSqlCommand command, String scopeTableName, String entityId,
  //  String entityClassName, String authorityName);

  /**
   * 查询实体对象的权限范围的视图
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  String getAuthorizationScopeView(String scopeTableName, String entityId, String entityClassName,
    String authorityName);

  /**
   * 查询实体对象的权限范围的视图
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  // String getAuthorizationScopeView(GenericSqlCommand command, String scopeTableName, String entityId,
  //  String entityClassName, String authorityName);

  /**
   * 获取实体对象和授权对象的授权关系
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   */
  List<Map> getAuthorizationScopeRelationByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName);

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param authorityIds 权限标识
   * @param accountId 用户标识
   * @param authorizationObjectType 授权对象类型
   */
  String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType, String authorityIds);

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param accountId 用户标识
   * @param authorizationObjectType 授权对象类型
   * @param authorityIds 权限标识
   * @param entityIdDataColumnName 实体类标识的数据列名
   * @param entityClassNameDataColumnName 实体类名称的数据列名
   * @param entityClassName 实体类名称
   */
  String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType, String authorityIds,
    String entityIdDataColumnName, String entityClassNameDataColumnName, String entityClassName);
}
