package com.x3platform.membership.mappers;

import com.x3platform.membership.AccountOrganizationUnitRelation;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.models.OrganizationInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 */
public interface OrganizationMapper {

  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param OrganizationUnit 实例详细信息
   * @return OrganizationUnit 实例详细信息
   */
  int save(OrganizationInfo param);



  /**
   * 删除记录
   *
   * @param id 标识
   */
  void delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id OrganizationUnit id号
   * @return 返回一个<see cref="OrganizationUnit" />实例的详细信息
   */
  OrganizationUnit findOne(String id);



  /**
   * 检测是否存在相关的记录.
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 检测是否存在相关的记录.
   *
   * @param name 名称
   * @return 布尔值
   */
  boolean isExistName(String name);


  /**
   * 检测是否存在相关的记录
   *
   * @param id   组织标识
   * @param name 组织名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int rename(String id, String name);


  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setEmailStatus(String accountId, int status);

  /**
   * 同步信息
   * @param param 组织信息
   */
  // -------------------------------------------------------
  // 设置帐号和组织关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关组织的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, OrganizationUnitId, isDefault, BeginDate, EndDate
   */
  List<AccountOrganizationUnitRelation> findAllRelationByAccountId(String accountId);

  /**
   * 根据组织查询相关帐号的关系
   *
   * @param organizationId 组织标识
   * @return Table Columns：AccountId, OrganizationUnitId, isDefault, BeginDate, EndDate
   */
  List<AccountOrganizationUnitRelation> findAllRelationByRoleId(String organizationId);

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   * @param isDefault      是否是默认组织
   * @param beginDate      启用时间
   * @param endDate        停用时间
   */
  int addRelation(@Param("accountId") String accountId,
                  @Param("organizationId") String organizationId,
                  @Param("isDefault") boolean isDefault,
                  @Param("beginDate") Date beginDate,
                  @Param("endDate") Date endDate);

  /**
   * 续约帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   * @param endDate        新的截止时间
   */
  int extendRelation(String accountId, String organizationId, Date endDate);

  /**
   * 移除帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  int removeRelation(String accountId, String organizationId);

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
  int removeNondefaultRelation(String accountId);

  /**
   * 移除帐号已过期的组织关系
   *
   * @param accountId 帐号标识
   */
  int removeExpiredRelation(String accountId);

  /**
   * 移除帐号相关组织的所有关系
   * @param accountId 帐号标识
   */
  int removeAllRelation(@Param("accountId") String accountId);

  /**
   * 检测帐号是否有默认组织
   *
   * @param accountId 帐号标识
   */
  boolean hasDefaultRelation(@Param("accountId") String accountId);

  /**
   * 设置帐号的默认岗位
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  int setDefaultRelation(String accountId, String organizationId);

  /**
   * 清理组织与帐号的关系
   * @param organizationId 组织标识
   */
  int clearupRelation(@Param("organizationId") String organizationId);
}
