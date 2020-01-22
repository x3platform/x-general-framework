package com.x3platform.apps.mappers;

import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationLite;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author ruanyu
 */
public interface ApplicationMapper {

  // -------------------------------------------------------
  // 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param {@link Application} 实例的详细信息
   * @return 受影响的行数
   */
  int insert(Application param);

  /**
   * 修改记录
   *
   * @param param {@link Application} 实例的详细信息
   * @return 受影响的行数
   */
  int updateByPrimaryKey(Application param);

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
   * @param id Application Id号
   * @return 一个 {@link Application} 实例的详细信息
   */
  Application selectByPrimaryKey(String id);

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 一个 {@link Application} 实例的详细信息
   */
  Application findOneByApplicationName(String applicationName);

  /**
   * 查询某条记录
   * @param applicationKey 应用唯一标识
   * @return 一个 {@link Application} 实例的详细信息
   */
  Application findOneByApplicationKey(String applicationKey);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link Application} 实例的详细信息
   */
  List<Application> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link Application} 实例的详细信息
   */
  List<ApplicationLite> findAllLites(Map params);

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   * @param accountId 帐号标识
   * @return 所有 {@link Application} 实例的详细信息
   */
  List<Application> findAllByAccountId(@Param("accountId") String accountId);

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 所有{@link Application}实例的详细信息
   */
  List<Application> findAllByRoleIds(String roleIds);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @return 所有实例的详细信息
   */
  List<Application> findTreeNodesByParentId(@Param("parent_id") String parentId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务编码
   * @return 布尔值
   */
  boolean isExist(@Param("id") String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param applicationName 应用名称
   * @return 布尔值
   */
  boolean isExistApplicationName(@Param("application_name") String applicationName);
}
