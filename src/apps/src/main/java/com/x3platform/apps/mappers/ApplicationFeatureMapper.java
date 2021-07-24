package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationFeature;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 应用功能管理模块
 */
public interface ApplicationFeatureMapper {

  // -------------------------------------------------------
  // 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param ApplicationFeature 实例的详细信息
   */
  int insert(ApplicationFeature param);

  /**
   * 修改记录
   *
   * @param param Application 实例的详细信息
   */
  int updateByPrimaryKey(ApplicationFeature param);

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
   * @param id Application Id号
   * @return 一个 Application 实例的详细信息
   */
  ApplicationFeature selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 Application 实例的详细信息
   */
  List<ApplicationFeature> findAll(Map params);

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param accountId 帐号标识
   * @return 所有 {@link ApplicationFeature} 实例的详细信息
   */
  List<ApplicationFeature> findAllByAccountId(@Param("accountId") String accountId);

  /**
   * 根据应用标识查询所有可用的树节点信息
   *
   * @param applicationId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationFeature> findTreeNodesByApplicationId(@Param("application_id") String applicationId);

  /**
   * 根据应用标识查询所有可用的树节点信息
   *
   * @param applicationId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationFeature> findTreeNodesByApplicationIdAndType(@Param("application_id") String applicationId,
    @Param("type") String type);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationFeature> findTreeNodesByParentId(@Param("parent_id") String parentId);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationFeature> findTreeNodesByParentIdAndType(@Param("parent_id") String parentId,
    @Param("type") String type);

  /**
   * 获取授权对象允许的功能列表
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityId 权限标识
   * @return 允许的功能列表
   */
  List<ApplicationFeature> findAllAllowedByAuthorizationObjectIds(
    @Param("authorization_object_type") String authorizationObjectType,
    @Param("authorization_object_id") String authorizationObjectId,
    @Param("authority_id") String authorityId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务编码
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 应用名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 获取允许的功能列表
   *
   * @param applicationId 所属应用标识 如果为空则查询遍历应用
   * @param authorizationScopeSql 授权范围条件
   * @return 允许的功能列表
   */
  List<String> getAllowedFeatures(@Param("application_id") String applicationId,
    @Param("authorization_scope_sql") String authorizationScopeSql);

  /**
   *
   * @param applicationId
   * @param applicationMembersType
   * @return
   */
  // List<ApplicationFeature> findAllAccountByApplicationId(@Param("applicationId") String applicationId, @Param("applicationMembersType") String applicationMembersType);

  /**
   * 解除应用功能权限的对应关系
   * @param entityId
   * @param entityClassName
   * @param authorityId
   * @param authorizationObjectType
   * @param authorizationObjectId
   */
//  void unBindFeature(@Param("entityId") String entityId,@Param("entityClassName") String entityClassName,
//                     @Param("authorityId") String authorityId,@Param("authorizationObjectType") String authorizationObjectType,
//                     @Param("authorizationObjectId") String authorizationObjectId);

  /**
   * 解除应用功能权限的对应关系
   * @param entityId
   * @param entityClassName
   * @param authorityId
   * @param authorizationObjectType
   * @param authorizationObjectId
   */
//  void bindFeature(@Param("entityId") String entityId,@Param("entityClassName") String entityClassName,
//                     @Param("authorityId") String authorityId,@Param("authorizationObjectType") String authorizationObjectType,
//                     @Param("authorizationObjectId") String authorizationObjectId);

}
