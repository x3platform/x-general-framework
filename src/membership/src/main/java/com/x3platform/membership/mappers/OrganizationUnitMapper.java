package com.x3platform.membership.mappers;

import com.x3platform.membership.AccountOrganizationUnitRelation;
import com.x3platform.membership.OrganizationUnit;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ruanyu
 */
public interface OrganizationUnitMapper {

  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param {@link OrganizationUnit} 实例的详细信息
   * @return {@link OrganizationUnit} 实例详细信息
   */
  int insert(OrganizationUnit param);

  /**
   * 修改记录
   *
   * @param param {@link OrganizationUnit} 实例的详细信息
   */
  int update(OrganizationUnit param);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id OrganizationUnit id号
   * @return 一个 {@link OrganizationUnit} 实例的详细信息
   */
  OrganizationUnit findOne(String id);

  // OrganizationUnit findMaxCode();

  /**
   * 查询某条记录
   *
   * @param globalName 组织的全局名称
   * @return 一个 {@link OrganizationUnit} 实例的详细信息
   */
  OrganizationUnit findOneByGlobalName(@Param("globalName") String globalName);

  /**
   * 查询角色所属的组织信息
   *
   * @param roleId 角色标识
   * @return 一个 {@link OrganizationUnit} 实例的详细信息
   */
  OrganizationUnit findOneByRoleId(@Param("role_id") String roleId);

  /**
   * 查询某个角色所属的某一级次的组织信息
   *
   * @param roleId 角色标识
   * @param level 层次
   * @return 所有实例的详细信息
   */
  OrganizationUnit findOneByRoleIdAndLevel(@Param("role_id") String roleId, @Param("level") int level);

  /**
   * 查询某个组织所属的公司信息
   *
   * @param id 组织标识
   * @return 所有实例的详细信息
   */
  OrganizationUnit findCorporationByOrganizationUnitId(String id);

  /**
   * 查询某个组织的所属某个上级部门信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 所有实例的详细信息
   */
  OrganizationUnit findDepartmentByOrganizationUnitId(String organizationId, int level);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 OrganizationUnit 实例的详细信息
   */
  List<OrganizationUnit> findAll(Map params);

  /**
   * 新版， 查询列表化 组织结构展现形式
   *
   * @param params 查询参数集合
   */
  List<OrganizationUnit> queryAll(Map params);

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 所有实例实例的详细信息
   */
  List<OrganizationUnit> findAllByParentId(@Param("parent_id") String parentId);

  /**
   * 查询某条记录
   *
   * @param accountId 帐号标识
   * @return 一个 {@link OrganizationUnit} 实例的列表信息
   */
  List<OrganizationUnit> findAllByAccountId(@Param("account_id") String accountId);

  /**
   * 查询所有的组织机构 包含本组织机构
   */
  List<OrganizationUnit> getChildOrganizationByOrganizationUnitId(String organizationUnitId);

  /**
   * 查询某个帐户所属的所有公司信息
   *
   * @param accountId 帐号标识
   * @return 所有实例的详细信息
   */
  List<OrganizationUnit> findCorporationsByAccountId(String accountId);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @return 所有实例的详细信息
   */
  List<OrganizationUnit> findTreeNodesByParentId(@Param("parent_id") String parentId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 帐号标识
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
   * 查询是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(@Param("global_name") String globalName);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 组织标识
   * @param name 组织名称
   * @return 0-代表成功 1-代表已存在相同名称
   */
  int rename(@Param("id") String id, @Param("name") String name);

  /**
   * 设置全局名称
   *
   * @param id 组织标识
   * @param globalName 全局名称
   * @return 修改成功,  0, 修改失败,  1.
   */
  int setGlobalName(@Param("id") String id, @Param("global_name") String globalName);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 组织标识
   * @param parentId 父级组织标识
   * @return 修改成功,  0, 修改失败,  1.
   */
  int setParentId(@Param("id") String id, @Param("parent_id") String parentId);

  /**
   * 设置企业邮箱状态
   *
   * @param id 组织单元标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setEmailStatus(String id, int status);

  /**
   * 同步信息
   *
   * @param param 组织信息
   */
  // int SyncFromPackPage(OrganizationUnit param);

  // -------------------------------------------------------
  // 设置帐号和组织关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关组织的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, OrganizationUnitId, isDefault, BeginDate, EndDate
   */
  List<AccountOrganizationUnitRelation> findAllRelationByAccountId(@Param("account_id") String accountId);

  /**
   * 根据组织查询相关帐号的关系
   *
   * @param organizationUnitId 组织标识
   * @return Table Columns：AccountId, OrganizationUnitId, isDefault, BeginDate, EndDate
   */
  List<AccountOrganizationUnitRelation> findAllRelationByOrganizationUnitId(
    @Param("organization_unit_id") String organizationUnitId);

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 组织单元标识
   * @param isDefault 是否是默认组织
   * @param beginDate 启用时间
   * @param endDate 停用时间
   */
  int addRelation(@Param("account_id") String accountId,
    @Param("organization_unit_id") String organizationUnitId,
    @Param("is_default") boolean isDefault,
    @Param("begin_date") Date beginDate,
    @Param("end_date") Date endDate);

  /**
   * 续约帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 组织单元标识
   * @param endDate 新的截止时间
   */
  int extendRelation(String accountId, String organizationUnitId, Date endDate);

  /**
   * 移除帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 组织单元标识
   */
  int removeRelation(String accountId, String organizationUnitId);

  /**
   * 移除帐号相关组织的默认关系
   *
   * @param accountId 帐号标识
   */
  int removeDefaultRelation(String accountId);

  /**
   * 移除帐号相关组织的非默认关系
   *
   * @param accountId 帐号标识
   */
  int removeNondefaultRelation(@Param("account_id") String accountId);

  /**
   * 移除帐号已过期的组织关系
   *
   * @param accountId 帐号标识
   */
  int removeExpiredRelation(String accountId);

  /**
   * 移除帐号相关组织的所有关系
   *
   * @param accountId 帐号标识
   */
  int removeAllRelation(@Param("account_id") String accountId);

  /**
   * 检测帐号是否有角色
   *
   * @param accountId 帐号标识
   */
  boolean hasRelation(@Param("account_id") String accountId, @Param("organization_unit_id") String organizationUnitId);

  /**
   * 检测帐号是否有默认组织
   *
   * @param accountId 帐号标识
   */
  boolean hasDefaultRelation(@Param("account_id") String accountId);

  /**
   * 设置帐号的默认组织信息
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 组织单元标识
   */
  int setDefaultRelation(@Param("account_id") String accountId,
    @Param("organization_unit_id") String organizationUnitId);

  /**
   * 清理组织单元与帐号的关系
   *
   * @param organizationUnitId 组织单元标识
   */
  int clearupRelation(@Param("organization_unit_id") String organizationUnitId);
}
