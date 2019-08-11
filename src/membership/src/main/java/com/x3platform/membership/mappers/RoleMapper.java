package com.x3platform.membership.mappers;

import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.Role;
import com.x3platform.security.authority.Authority;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface RoleMapper {

  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param IAccount 实例详细信息
   * @return IAccount 实例详细信息
   */
  int save(Role param);

  /**
   * 添加记录
   *
   * @param param IAccount 实例的详细信息
   */
  void insert(Role param);

  /**
   * 修改记录
   *
   * @param param IAccount 实例的详细信息
   */
  void update(Role param);

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
   * @param id IAccount id号
   * @return 返回一个 IAccount 实例的详细信息
   */
  Role findOne(String id);

  /**
   * 查询某条记录
   *
   * @param globalName 角色的全局名称
   * @return 返回一个<see       cref   =   "   Role   "   />实例的详细信息
   */
  Role findOneByGlobalName(String globalName);

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId  组织标识
   * @param standardRoleId 标准角色标识
   * @return 返回一个<see       cref   =   "   Role   "   />实例的详细信息
   */
  Role findOneByCorporationIdAndStandardRoleId(String corporationId, String standardRoleId);


  /**
   * @param parentId
   * @return
   */
  Role findMaxCodeByParentId(String parentId);

  /**
   * @param organizationUnitId
   * @return
   */
  Role findMaxCodeByOrganizationUnitId(String organizationUnitId);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有<see       cref   =   "   Role   "       />实例的详细信息
   */
  List<Role> findAll(String whereClause, int length);


  /**
   * 查询所有相关记录
   *
   * @return 返回所有实例的详细信息
   */
  List<Role> findAll(Map params);

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 返回一个 OrganizationUnit 实例的详细信息
   */
  List<Role> findAllByParentId(String parentId);

  /**
   * 查询某条记录
   *
   * @param accountId 帐号标识
   * @return 返回一个 Role 实例的详细信息
   */
  List<Role> findAllByAccountId(@Param("accountId") String accountId);

  /**
   * 查询某个组织下面所有的角色
   *
   * @param organizationId 组织标识
   * @return 返回一个 Role 实例的详细信息
   */
  List<Role> findAllByOrganizationUnitId(String organizationId);

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param generalRoleId 组织标识
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllByGeneralRoleId(String generalRoleId);

  /**
   * 递归查询某个标准组织下面所有的角色
   *
   * @param standardOrganizationUnitId 组织标识
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllByStandardOrganizationUnitId(String standardOrganizationUnitId);

  /**
   * 递归查询某个标准角色下面所有的角色
   *
   * @param standardRoleId 组织标识
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllByStandardRoleId(String standardRoleId);

  /**
   * 递归查询某个组织下面相关的职位对应的角色信息
   *
   * @param organizationId 组织标识
   * @param jobId          职位标识
   * @return 返回一个<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllByOrganizationUnitIdAndJobId(String organizationId, String jobId);
  ///#endregion

  ///#region 函数:FindAllByAssignedJobId(string assignedJobId)

  /**
   * 递归查询某个组织下面相关的岗位对应的角色信息
   *
   * @param assignedJobId 岗位标识
   * @return 返回一个<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllByAssignedJobId(String assignedJobId);
  ///#endregion

  ///#region 函数:FindAllByCorporationIdAndStandardRoleIds(string corporationId, string standardRoleIds)

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId   组织标识
   * @param standardRoleIds 标准角色标识
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllByCorporationIdAndStandardRoleIds(String corporationId, String standardRoleIds);
  ///#endregion

  ///#region 函数:FindAllBetweenPriority(string corporationId, string standardRoleIds)

  /**
   * 根据某个组织标识查询此组织上下级之间属于某一范围权重值的角色信息
   *
   * @param organizationId 组织标识
   * @param minPriority    最小权重值
   * @param maxPriority    最大权重值
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllBetweenPriority(String organizationId, int minPriority, int maxPriority);
  ///#endregion

  ///#region 函数:FindAllWithoutMember(int length, bool includeAllRole)

  /**
   * 返回所有没有成员信息的角色信息
   *
   * @param length         条数, 0表示全部
   * @param includeAllRole 包含全部角色
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findAllWithoutMember(int length, boolean includeAllRole);
  ///#endregion

  ///#region 函数:FindForwardLeadersByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findForwardLeadersByOrganizationUnitId(String organizationId, int level);
  ///#endregion

  ///#region 函数:FindBackwardLeadersByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findBackwardLeadersByOrganizationUnitId(String organizationId, int level);
  ///#endregion

  ///#region 函数:FindStandardGeneralRolesByOrganizationUnitId(string organizationId, string standardGeneralRoleId)

  /**
   * 返回所有父级对象为标准通用角色标识【standardGeneralRoleId】的相关角色信息
   *
   * @param organizationId        组织标识
   * @param standardGeneralRoleId 标准通用角色标识
   * @return 返回所有<see       cref   =   "   Role   "   />实例的详细信息
   */
  List<Role> findStandardGeneralRolesByOrganizationUnitId(String organizationId, String standardGeneralRoleId);
  ///#endregion

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  ///#region 函数:GetPaging(int startIndex, int pageSize, DataQuery query, out int rowCount)
  /** 分页函数
   @param startIndex 开始行索引数,由0开始统计
   @param pageSize 页面大小
   @param query 数据查询参数
   @param rowCount 记录行数
   @return 返回一个列表
   */
  // List<Role> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

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
   * @param name 角色名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 角色全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(String globalName);

  /**
   * 检测是否存在相关的记录
   *
   * @param id   角色标识
   * @param name 角色名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int rename(String id, String name);

  /**
   * 设置全局名称
   *
   * @param id         角色标识
   * @param globalName 全局名称
   * @return 修改成功, 返回 0, 修改失败, 返回 1.
   */
  int setGlobalName(String id, String globalName);

  /**
   * 检测是否存在相关的记录
   *
   * @param id       角色标识
   * @param parentId 父级角色标识
   * @return 0:成功 | 1:失败
   */
  int setParentId(String id, String parentId);

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setExchangeStatus(String accountId, int status);

  /**
   * 获取角色的权限
   *
   * @param roleId 角色标识
   */
  List<Authority> getAuthorities(String roleId);

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
   * @param roleId    角色标识
   * @param isDefault 是否是默认角色
   * @param beginDate 启用时间
   * @param endDate   停用时间
   */
  int addRelation(@Param("accountId") String accountId,
                  @Param("roleId") String roleId,
                  @Param("isDefault") boolean isDefault,
                  @Param("beginDate") Date beginDate,
                  @Param("endDate") Date endDate);

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   * @param endDate   新的截止时间
   */
  int extendRelation(String accountId, String roleId, Date endDate);

  /**
   * 移除帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
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
  int removeAllRelation(@Param("accountId") String accountId);

  /**
   * 检测帐号是否有默认角色
   *
   * @param accountId 帐号标识
   */
  boolean hasDefaultRelation(@Param("accountId") String accountId);

  /**
   * 检测帐号是否有默认角色
   *
   * @param accountId 帐号标识
   */
  boolean hasRelation(@Param("accountId") String accountId, @Param("roleId") String roleId);


  /**
   * 设置帐号的默认岗位
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   */
  int setDefaultRelation(String accountId, String roleId);

  /**
   * 清理角色与帐号的关系
   *
   * @param roleId 角色标识
   */
  int clearupRelation(String roleId);
}
