package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationScopeInfo;
import com.x3platform.membership.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
   * @return 返回一个 Application 实例的详细信息
   */
  ApplicationFeature selectByPrimaryKey(String id);

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 Application 实例的详细信息
   */
  ApplicationFeature findOneByApplicationName(String applicationName);

  /**
   * 查询某条记录
   * @param applicationFeatureKey 应用唯一标识
   * @return 返回一个 Application 实例的详细信息
   */
  ApplicationFeature findOneByApplicationKey(String applicationFeatureKey);


  /**
   * 查询所有相关记录
   * @param params 查询参数集合
   * @return 返回所有 Application 实例的详细信息
   */
  List<ApplicationFeature> findAll(Map params);

  /**
   * @param menuId 菜单Id
   * @param roleId 角色id
   */
  List<ApplicationFeature> findAllByMenu(@Param("menuId") String menuId,@Param("roleId") String roleId,@Param("applicationId") String applicationId);

  /**
   * 查询已经分配了的应用管理功能
   * @param menuId 菜单id
   * @param roleId 角色id
   * @param applicationId 应用id
   * @return 应用管理功能列表
   */
  List<ApplicationFeature> findAllAuthorizationScopeByMenu(@Param("menuId") String menuId,@Param("roleId") String roleId,@Param("applicationId") String applicationId);

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   * @param accountId 帐号标识
   * @return 返回所有<see cref="ApplicationFeature"/>实例的详细信息
   */
  List<ApplicationFeature> findAllByAccountId(@Param("accountId") String accountId);

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 返回所有<see cref="Application"/>实例的详细信息
   */
  List<ApplicationFeature> findAllByRoleIds(String roleIds);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex  开始行索引数,由0开始统计
   * @param pageSize    页面大小
   * @param whereClause WHERE 查询条件
   * @param orderBy     ORDER BY 排序条件.
   * @param rowCount    记录行数
   * @return 返回一个列表
   */
  // List<Application> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

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
   * 判断用户是否拥应用有权限信息
   *
   * @param accountId     帐号标识
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  boolean hasAuthority(@Param("accountId") String accountId, @Param("applicationId") String applicationId, @Param("authorityName")
    String authorityName);
  /**
   * 配置应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  void bindAuthorizationScopeObjects(String applicationId, String authorityName, String scopeText);

  /**
   *
   * @param scopeInfo
   */
  int bindAuthorizationScopeObjects(ApplicationScopeInfo scopeInfo);

  /**
   * 查询应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return
   */
  // List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String applicationId, String authorityName);

  /**
   * 判断当前 应用权限是否存在
   * @param scopeInfo
   * @return
   */
   boolean isScopeInfoExist(ApplicationScopeInfo scopeInfo);


  /**
   *
   * @param applicationId
   * @param applicationMembersType
   * @return
   */
   List<ApplicationFeature> findAllAccountByApplicationId(@Param("applicationId") String applicationId, @Param("applicationMembersType") String applicationMembersType);

  /**
   * 解除应用功能权限的对应关系
   * @param entityId
   * @param entityClassName
   * @param authorityId
   * @param authorizationObjectType
   * @param authorizationObjectId
   */
  void unBindFeature(@Param("entityId") String entityId,@Param("entityClassName") String entityClassName,
                     @Param("authorityId") String authorityId,@Param("authorizationObjectType") String authorizationObjectType,
                     @Param("authorizationObjectId") String authorizationObjectId);

  /**
   * 解除应用功能权限的对应关系
   * @param entityId
   * @param entityClassName
   * @param authorityId
   * @param authorizationObjectType
   * @param authorizationObjectId
   */
  void bindFeature(@Param("entityId") String entityId,@Param("entityClassName") String entityClassName,
                     @Param("authorityId") String authorityId,@Param("authorizationObjectType") String authorizationObjectType,
                     @Param("authorizationObjectId") String authorizationObjectId);

}
