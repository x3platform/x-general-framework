package com.x3platform.membership.services;

import com.x3platform.data.DataQuery;
import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.Role;
import com.x3platform.security.authority.Authority;
import java.util.Date;
import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param IRole 实例详细信息
   * @return IRole 实例详细信息
   */
  Role save(Role param);

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
   * @param id 组织单位标识
   * @return 一个 {@link Role} 实例的详细信息
   */
  Role findOne(String id);

  /**
   * 查询某条记录
   *
   * @param globalName 角色全局名称
   * @return 一个 {@link Role} 实例的详细信息
   */
  Role findOneByGlobalName(String globalName);

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId 组织标识
   * @param standardRoleId 标准角色标识
   * @return 一个 {@link Role} 实例的详细信息
   */
  Role findOneByCorporationIdAndStandardRoleId(String corporationId, String standardRoleId);

  /**
   * @param parentId 根据父级角色 查询最大编码的角色 return 角色信息
   */
  // Role findMaxCodeByParentId(String parentId);

  /**
   * @param organizationUnitId 所属组织结构查询最大编码的角色 return 角色信息
   */
  // Role findMaxCodeByOrganizationUnitId(String organizationUnitId);

  /**
   * 查询所有相关记录
   *
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAll(DataQuery query);

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 所有实例 {@link Role} 实例的详细信息
   */
  List<Role> findAllByParentId(String parentId);

  /**
   * 查询某个父节点下的所有角色信息
   *
   * @param parentId 父节标识
   * @param depth 深入获取的层次，0表示只获取本层次，-1表示全部获取
   * @return 所有实例 {@link Role} 的详细信息
   */
  List<Role> findAllByParentId(String parentId, int depth);

  /**
   * 查询某个用户所在的所有组织单位
   *
   * @param accountId 帐号标识
   * @return 所有  {@link Role}  实例的详细信息
   */
  List<Role> findAllByAccountId(String accountId);

  /**
   * 查询某个组织下面所有的角色
   *
   * @param organizationUnitId 组织机构i d 查询当前组织机构下及组织机构子机构所有的角色信息
   */
  // List<Role> findAllRolesByOrganization(String organizationUnitId);

  /**
   * 查询某个组织下面所有的角色
   *
   * @param organizationId 组织标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllByOrganizationUnitId(String organizationId);

  /**
   * 查询某个组织节点下的所有角色信息
   *
   * @param organizationId 父节标识
   * @param depth 深入获取的层次，0表示只获取本层次，-1表示全部获取
   * @return 所有实例 {@link Role} 的详细信息
   */
  List<Role> findAllByOrganizationUnitId(String organizationId, int depth);

  /**
   * 递归查询某个通用角色下面所有的角色
   *
   * @param generalRoleId 组织标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  // List<Role> findAllByGeneralRoleId(String generalRoleId);

  /**
   * 递归查询某个标准组织下面所有的角色
   *
   * @param standardOrganizationUnitId 组织标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  // List<Role> findAllByStandardOrganizationUnitId(String standardOrganizationUnitId);

  /**
   * 递归查询某个标准角色下面所有的角色
   *
   * @param standardRoleId 组织标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  // List<Role> findAllByStandardRoleId(String standardRoleId);

  /**
   * 递归查询某个组织下面相关的职位对应的角色信息
   *
   * @param organizationId 组织标识
   * @param jobId 职位标识
   * @return 一个 {@link Role} 实例的详细信息
   */
  List<Role> findAllByOrganizationUnitIdAndJobId(String organizationId, String jobId);

  /**
   * 递归查询某个组织下面相关的岗位对应的角色信息
   *
   * @param assignedJobId 岗位标识
   * @return 一个 {@link Role} 实例的详细信息
   */
  List<Role> findAllByAssignedJobId(String assignedJobId);

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationIds 公司标识，多个以逗号隔开
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllByCorporationIds(String corporationIds);

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationId 公司标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllByCorporationId(String corporationId);

  /**
   * 递归查询某个项目下面所有的角色
   *
   * @param projectIds 项目标识，多个以逗号隔开
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllByProjectIds(String projectIds);

  /**
   * 递归查询某个项目下面所有的角色
   *
   * @param projectId 项目标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllByProjectId(String projectId);

  /**
   * 递归查询某个公司下面所有的角色和某个项目下面所有的角色
   *
   * @param corporationIds 公司标识，多个以逗号隔开
   * @param projectIds 项目标识，多个以逗号隔开
   * @return 一个 {@link Role} 实例的详细信息
   */
  List<Role> findAllByCorporationIdAndProjectId(String corporationIds, String projectIds);

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId 组织标识
   * @param standardRoleIds 标准角色标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllByCorporationIdAndStandardRoleIds(String corporationId, String standardRoleIds);

  /**
   * 根据某个组织标识查询此组织上下级之间属于某一范围权重值的角色信息
   *
   * @param organizationId 组织标识
   * @param minPriority 最小权重值
   * @param maxPriority 最大权重值
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllBetweenPriority(String organizationId, int minPriority, int maxPriority);

  /**
   * 所有没有成员的角色信息
   *
   * @param length 条数, 0表示全部
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllWithoutMember(int length);

  /**
   * 所有没有成员的角色信息
   *
   * @param length 条数, 0表示全部
   * @param includeAllRole 包含全部角色
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findAllWithoutMember(int length, boolean includeAllRole);

  /**
   * 所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findForwardLeadersByOrganizationUnitId(String organizationId);

  /**
   * 所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findForwardLeadersByOrganizationUnitId(String organizationId, int level);

  /**
   * 所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findBackwardLeadersByOrganizationUnitId(String organizationId);

  /**
   * 所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findBackwardLeadersByOrganizationUnitId(String organizationId, int level);

  /**
   * 所有父级对象为标准通用角色标识【standardGeneralRoleId】的相关角色信息
   *
   * @param organizationId 组织标识
   * @param standardGeneralRoleId 标准通用角色标识
   * @return 所有 {@link Role} 实例的详细信息
   */
  List<Role> findStandardGeneralRolesByOrganizationUnitId(String organizationId, String standardGeneralRoleId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   记录行数
   * @return 一个列表 {@link Role}
   */
  // List<Role> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 角色标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 角色名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 角色名称
   * @param standardOrganizationUnit 标准角色
   * @return 布尔值
   */
  boolean isExistName(String name,String standardOrganizationUnit);


  /**
   * 查询是否存在相关的记录
   *
   * @param globalName 角色全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(String globalName);

  /**
   * 重命名
   *
   * @param id 角色标识
   * @param name 角色名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int rename(String id, String name);

  /**
   * 获取所有人角色
   */
  Role getEveryone();

  /**
   * 角色全路径
   *
   * @param name 角色名称
   * @param organizationId 所属组织标识
   */
  String combineFullPath(String name, String organizationId);

  /**
   * 角色唯一名称
   *
   * @param globalName 角色全局名称
   * @param organizationId 所属组织标识
   */
  String combineDistinguishedName(String globalName, String organizationId);

  /**
   * 设置全局名称
   *
   * @param id 角色标识
   * @param globalName 全局名称
   * @return 修改成功,  0, 修改失败,  1.
   */
  int setGlobalName(String id, String globalName);

  /**
   * 设置父级角色标识
   *
   * @param id 角色标识
   * @param parentId 父级角色标识
   * @return 0:代表成功
   */
  int setParentId(String id, String parentId);

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setEnableEmail(String accountId, int status);

  /**
   * 获取角色的权限
   *
   * @param roleId 角色标识
   */
  List<Authority> getAuthorities(String roleId);

  /**
   * 生成标准角色映射报表
   *
   * @param organizationId   组织标识
   * @param standardRoleType 标准角色类型
   */
  // DataTable GenerateStandardRoleMappingReport(String organizationId, String standardRoleType);

  /**
   * 生成标准角色映射报表
   *
   * @param organizationId   组织标识
   * @param standardRoleType 标准角色类型
   * @param standardRoleIds  标准角色标识，多个以逗号隔开
   */
  // DataTable GenerateStandardRoleMappingReport(String organizationId, String standardRoleType, String standardRoleIds);

  /**
   * 快速创建标准角色
   *
   * @param standardRoleType 标准角色类型
   * @param organizationId 公司或项目标识
   * @param standardRoleId 标准角色标识
   * @param roleName 角色名称
   */
  int quickCreateRole(String standardRoleType, String organizationId, String standardRoleId, String roleName);

  /**
   * 新建项目类标准角色
   *
   * @param fromProjectId 来源项目标识
   * @param toProjectId   目标项目标识
   */
  // DataTable setProjectRoleMapping(String fromProjectId, String toProjectId);
  ///#endregion

  ///#region 函数:CreatePackage(DateTime beginDate, DateTime endDate)

  /**
   * 创建数据包
   *
   * @param beginDate 开始时间
   * @param endDate   结束时间
   */
  // String CreatePackage(Date beginDate, Date endDate);

  /**
   * 同步信息至 Active Directory
   *
   * @param param 角色信息
   */
  int syncToLDAP(Role param);

  /**
   * 同步信息
   *
   * @param param 角色信息
   */
  // int SyncFromPackPage(Role param);

  // -------------------------------------------------------
  // 设置帐号和角色关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关角色的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, RoleId, IsDefault, BeginDate, EndDate
   */
  List<AccountRoleRelation> findAllRelationByAccountId(String accountId);

  /**
   * 根据角色查询相关帐号的关系
   *
   * @param roleId 角色标识
   * @return Table Columns：AccountId, RoleId, IsDefault, BeginDate, EndDate
   */
  List<AccountRoleRelation> findAllRelationByRoleId(String roleId);

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   */
  int addRelation(String accountId, String roleId);

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   * @param isDefault 是否是默认角色
   * @param beginDate 启用时间
   * @param endDate 停用时间
   */
  int addRelation(String accountId, String roleId, boolean isDefault, Date beginDate, Date endDate);

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountIds 帐号标识，多个以逗号隔开
   * @param roleId 角色标识
   */
  int addRelationRange(String accountIds, String roleId);

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   * @param endDate 新的截止时间
   */
  int extendRelation(String accountId, String roleId, Date endDate);

  /**
   * 移除帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   */
  int removeRelation(String accountId, String roleId);

  /**
   * 移除帐号相关角色的默认关系
   *
   * @param accountId 帐号标识
   */
  int removeDefaultRelation(String accountId);

  /**
   * 移除帐号相关角色的非默认关系
   *
   * @param accountId 帐号标识
   */
  int removeNondefaultRelation(String accountId);

  /**
   * 移除帐号已过期的角色关系
   *
   * @param accountId 帐号标识
   */
  int removeExpiredRelation(String accountId);

  /**
   * 移除帐号相关角色的所有关系
   *
   * @param accountId 帐号标识
   */
  int removeAllRelation(String accountId);

  /**
   * 检测帐号是否有默认角色
   *
   * @param accountId 帐号标识
   */
  boolean hasDefaultRelation(String accountId);

  /**
   * 检测帐号是否有默认角色
   *
   * @param accountId 帐号标识
   * @param roleId 检测账号是否有标识
   */
  boolean hasRelation(String accountId, String roleId);

  /**
   * 设置帐号的默认岗位
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   */
  int setDefaultRelation(String accountId, String roleId);

  /**
   * 清理角色与帐号的关系
   *
   * @param roleId 角色标识
   * @return 消息代码
   */
  int clearupRelation(String roleId);
}
