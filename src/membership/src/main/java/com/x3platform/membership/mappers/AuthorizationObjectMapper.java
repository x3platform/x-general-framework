package com.x3platform.membership.mappers;

import com.x3platform.AuthorizationObject;
import com.x3platform.AuthorizationObjectType;
import com.x3platform.AuthorizationScope;
import com.x3platform.membership.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthorizationObjectMapper {

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
   * @param params 数据查询参数
   * @return 一个列表
   */
  List<Map> filter(Map params);


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
  //  List<AuthorizationScope> authorizationScopeObjects);

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityId 权限标识
   * @param accountId 帐号标识
   * @return 布尔值
   */
  boolean hasAuthorityWithAccount(
    @Param("scope_table_name") String scopeTableName,
    @Param("entity_id") String entityId,
    @Param("entity_class_name")  String entityClassName,
    @Param("authority_id")  String authorityId,
    @Param("account_id") String accountId);

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityId 权限标识
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 布尔值
   */
  boolean hasAuthority(
    @Param("scope_table_name") String scopeTableName,
    @Param("entity_id") String entityId,
    @Param("entity_class_name")  String entityClassName,
    @Param("authority_id")  String authorityId,
    @Param("authorization_object_type")  String authorizationObjectType,
    @Param("authorization_object_id")   String authorizationObjectId);

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
   * @param authorityId 权限标识
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   */
  void addAuthorizationScope(
    @Param("scope_table_name") String scopeTableName,
    @Param("entity_id") String entityId,
    @Param("entity_class_name") String entityClassName,
    @Param("authority_id") String authorityId,
    @Param("authorization_object_type") String authorizationObjectType,
    @Param("authorization_object_id") String authorizationObjectId);

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

  /**
   * 移除实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityId 权限标识
   *                    scopeTableName, authorizationObjectType, authorizationObjectId , authority.getId()
   */
  void removeAuthorizationScopeByEntityId(
    @Param("scope_table_name") String scopeTableName,
    @Param("entity_id") String entityId,
    @Param("entity_class_name") String entityClassName,
    @Param("authority_id") String authorityId);

  /**
   * 移除实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  // void removeAuthorizationScope(GenericSqlCommand command, String scopeTableName, String entityId,
  //  String entityClassName, String authorityName);

  /**
   * 移除授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityId 权限标识
   */
  void removeAuthorizationScopeByAuthorizationObjectId(
    @Param("scope_table_name")String scopeTableName,
    @Param("authorization_object_type")String authorizationObjectType,
    @Param("authorization_object_id")String authorizationObjectId,
    @Param("authority_id")String authorityId);

  /**
   * @param scopeTableName          数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param entityId                实体标识
   * @param authorizationObjectId   授权对象标识
   * @param authorityId             权限标识
   */
  void removeAuthorizationScopeByAuthorizationObjectIdAndEntityId(@Param("scope_table_name") String scopeTableName,
                                                                  @Param("authorization_object_type") String authorizationObjectType,
                                                                  @Param("entity_id") String entityId,
                                                                  @Param("authorization_object_id") String authorizationObjectId,
                                                                  @Param("authority_id") String authorityId);

  /**
   * 查询实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityId 权限名称
   */
  List<AuthorizationScope> getAuthorizationScopes(
    @Param("scope_table_name") String scopeTableName,
    @Param("entity_id")  String entityId,
    @Param("entity_class_name")  String entityClassName,
    @Param("authority_id")String authorityId);

  /**
   * 查询实体对象的权限信息
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  // List<AuthorizationScope> getAuthorizationScopes(GenericSqlCommand command,
  //  String scopeTableName, String entityId, String entityClassName, String authorityName);

  /**
   * 查询实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityId 权限标识
   */
  List<Map> getAuthorizationScopeRelationByAuthorizationObjectId(
    @Param("scope_table_name")String scopeTableName,
    @Param("authorization_object_type")String authorizationObjectType,
    @Param("authorization_object_id")String authorizationObjectId,
    @Param("authority_id")String authorityId);

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param authorityIds 权限标识
   * @param accountId 用户标识
   * @param authorizationObjectType 联系人对象
   */
  String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType,
    String authorityIds);

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param accountId 用户标识
   * @param authorizationObjectType 联系人对象
   * @param authorityIds 权限标识
   * @param entityIdDataColumnName 实体类标识的数据列名
   * @param entityClassNameDataColumnName 实体类名称的数据列名
   * @param entityClassName 实体类名称
   */
  String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType, String authorityIds,
    String entityIdDataColumnName, String entityClassNameDataColumnName, String entityClassName);
}
