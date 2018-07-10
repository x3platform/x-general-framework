package com.x3platform.membership.mappers;

import com.x3platform.membership.IAccountRoleRelationInfo;
import com.x3platform.membership.IRoleInfo;
import com.x3platform.security.authority.AuthorityInfo;

import java.util.*;

//import com.x3platform.Membership.*;
//import com.x3platform.Security.Authority.*;
//import com.x3platform.Spring.*;
//import com.x3platform.Data.*;

/**
 */
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SpringObject("X3Platform.Membership.IDAL.IRoleProvider")] public interface IRoleProvider
public interface RoleMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  ///#region 函数:Save(IAccount param)

  /**
   * 保存记录
   *
   * @param param IAccount 实例详细信息
   * @return IAccount 实例详细信息
   */
  IRoleInfo Save(IRoleInfo param);
  ///#endregion

  ///#region 函数:Insert(IAccount param)

  /**
   * 添加记录
   *
   * @param param IAccount 实例的详细信息
   */
  void Insert(IRoleInfo param);
  ///#endregion

  ///#region 函数:Update(IAccount param)

  /**
   * 修改记录
   *
   * @param param IAccount 实例的详细信息
   */
  void Update(IRoleInfo param);
  ///#endregion

  ///#region 函数:Delete(string id)

  /**
   * 删除记录
   *
   * @param id 标识
   */
  void Delete(String id);
  ///#endregion

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  ///#region 函数:FindOne(string id)

  /**
   * 查询某条记录
   *
   * @param id IAccount id号
   * @return 返回一个 IAccount 实例的详细信息
   */
  IRoleInfo FindOne(String id);
  ///#endregion

  ///#region 函数:FindOneByGlobalName(string globalName)

  /**
   * 查询某条记录
   *
   * @param globalName 角色的全局名称
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  IRoleInfo FindOneByGlobalName(String globalName);
  ///#endregion

  ///#region 函数:FindOneByCorporationIdAndStandardRoleId(string corporationId, string standardRoleId)

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId  组织标识
   * @param standardRoleId 标准角色标识
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  IRoleInfo FindOneByCorporationIdAndStandardRoleId(String corporationId, String standardRoleId);
  ///#endregion

  ///#region 函数:FindAll(string whereClause,int length)

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有<see cref="IRoleInfo" />实例的详细信息
   */
  List<IRoleInfo> FindAll(String whereClause, int length);
  ///#endregion

  ///#region 函数:FindAllByParentId(string parentId)

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 返回一个 IOrganizationUnitInfo 实例的详细信息
   */
  List<IRoleInfo> FindAllByParentId(String parentId);
  ///#endregion

  ///#region 函数:FindAllByAccountId(string accountId)

  /**
   * 查询某条记录
   *
   * @param accountId 帐号标识
   * @return 返回一个 IRoleInfo 实例的详细信息
   */
  List<IRoleInfo> FindAllByAccountId(String accountId);
  ///#endregion

  ///#region 函数:FindAllByOrganizationUnitId(string organizationId)

  /**
   * 查询某个组织下面所有的角色
   *
   * @param organizationId 组织标识
   * @return 返回一个 IRoleInfo 实例的详细信息
   */
  List<IRoleInfo> FindAllByOrganizationUnitId(String organizationId);
  ///#endregion

  ///#region 函数:FindAllByGeneralRoleId(string generalRoleId)

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param generalRoleId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllByGeneralRoleId(String generalRoleId);
  ///#endregion

  ///#region 函数:FindAllByStandardOrganizationUnitId(string standardOrganizationUnitId)

  /**
   * 递归查询某个标准组织下面所有的角色
   *
   * @param standardOrganizationUnitId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllByStandardOrganizationUnitId(String standardOrganizationUnitId);
  ///#endregion

  ///#region 函数:FindAllByStandardRoleId(string standardRoleId)

  /**
   * 递归查询某个标准角色下面所有的角色
   *
   * @param standardRoleId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllByStandardRoleId(String standardRoleId);
  ///#endregion

  ///#region 函数:FindAllByOrganizationUnitIdAndJobId(string organizationId, string jobId)

  /**
   * 递归查询某个组织下面相关的职位对应的角色信息
   *
   * @param organizationId 组织标识
   * @param jobId          职位标识
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllByOrganizationUnitIdAndJobId(String organizationId, String jobId);
  ///#endregion

  ///#region 函数:FindAllByAssignedJobId(string assignedJobId)

  /**
   * 递归查询某个组织下面相关的岗位对应的角色信息
   *
   * @param assignedJobId 岗位标识
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllByAssignedJobId(String assignedJobId);
  ///#endregion

  ///#region 函数:FindAllByCorporationIdAndStandardRoleIds(string corporationId, string standardRoleIds)

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId   组织标识
   * @param standardRoleIds 标准角色标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllByCorporationIdAndStandardRoleIds(String corporationId, String standardRoleIds);
  ///#endregion

  ///#region 函数:FindAllBetweenPriority(string corporationId, string standardRoleIds)

  /**
   * 根据某个组织标识查询此组织上下级之间属于某一范围权重值的角色信息
   *
   * @param organizationId 组织标识
   * @param minPriority    最小权重值
   * @param maxPriority    最大权重值
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllBetweenPriority(String organizationId, int minPriority, int maxPriority);
  ///#endregion

  ///#region 函数:FindAllWithoutMember(int length, bool includeAllRole)

  /**
   * 返回所有没有成员信息的角色信息
   *
   * @param length         条数, 0表示全部
   * @param includeAllRole 包含全部角色
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindAllWithoutMember(int length, boolean includeAllRole);
  ///#endregion

  ///#region 函数:FindForwardLeadersByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindForwardLeadersByOrganizationUnitId(String organizationId, int level);
  ///#endregion

  ///#region 函数:FindBackwardLeadersByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindBackwardLeadersByOrganizationUnitId(String organizationId, int level);
  ///#endregion

  ///#region 函数:FindStandardGeneralRolesByOrganizationUnitId(string organizationId, string standardGeneralRoleId)

  /**
   * 返回所有父级对象为标准通用角色标识【standardGeneralRoleId】的相关角色信息
   *
   * @param organizationId        组织标识
   * @param standardGeneralRoleId 标准通用角色标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  List<IRoleInfo> FindStandardGeneralRolesByOrganizationUnitId(String organizationId, String standardGeneralRoleId);
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
  // List<IRoleInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 检测是否存在相关的记录.
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  boolean IsExist(String id);
  ///#endregion

  ///#region 函数:IsExistName(string name)

  /**
   * 检测是否存在相关的记录.
   *
   * @param name 角色名称
   * @return 布尔值
   */
  boolean IsExistName(String name);
  ///#endregion

  ///#region 函数:IsExistGlobalName(string globalName)

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 角色全局名称
   * @return 布尔值
   */
  boolean IsExistGlobalName(String globalName);
  ///#endregion

  ///#region 函数:Rename(string id, string name)

  /**
   * 检测是否存在相关的记录
   *
   * @param id   角色标识
   * @param name 角色名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int Rename(String id, String name);
  ///#endregion

  ///#region 函数:SetGlobalName(string id, string globalName)

  /**
   * 设置全局名称
   *
   * @param id         角色标识
   * @param globalName 全局名称
   * @return 修改成功, 返回 0, 修改失败, 返回 1.
   */
  int SetGlobalName(String id, String globalName);
  ///#endregion

  ///#region 函数:SetParentId(string id, string parentId)

  /**
   * 检测是否存在相关的记录
   *
   * @param id       角色标识
   * @param parentId 父级角色标识
   * @return 0:成功 | 1:失败
   */
  int SetParentId(String id, String parentId);
  ///#endregion

  ///#region 函数:SetExchangeStatus(string accountId, int status)

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int SetExchangeStatus(String accountId, int status);
  ///#endregion

  ///#region 函数:GetAuthorities(string roleId)

  /**
   * 获取角色的权限
   *
   * @param roleId 角色标识
   */
  List<AuthorityInfo> GetAuthorities(String roleId);
  ///#endregion

  ///#region 函数:SyncFromPackPage(IRoleInfo param)

  /**
   * 同步信息
   *
   * @param param 角色信息
   */
  int SyncFromPackPage(IRoleInfo param);
  ///#endregion

  // -------------------------------------------------------
  // 设置帐号和角色关系
  // -------------------------------------------------------

  ///#region 函数:FindAllRelationByAccountId(string accountId)

  /**
   * 根据帐号查询相关角色的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, RoleId, IsDefault, BeginDate, EndDate
   */
  List<IAccountRoleRelationInfo> FindAllRelationByAccountId(String accountId);
  ///#endregion

  ///#region 函数:FindAllRelationByRoleId(string roleId)

  /**
   * 根据角色查询相关帐号的关系
   *
   * @param roleId 角色标识
   * @return Table Columns：AccountId, RoleId, IsDefault, BeginDate, EndDate
   */
  List<IAccountRoleRelationInfo> FindAllRelationByRoleId(String roleId);
  ///#endregion

  ///#region 函数:AddRelation(string accountId, string roleId, bool isDefault, DateTime beginDate, DateTime endDate)

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   * @param isDefault 是否是默认角色
   * @param beginDate 启用时间
   * @param endDate   停用时间
   */
  int AddRelation(String accountId, String roleId, boolean isDefault, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate);
  ///#endregion

  ///#region 函数:ExtendRelation(string accountId, string roleId, DateTime endDate)

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   * @param endDate   新的截止时间
   */
  int ExtendRelation(String accountId, String roleId, java.time.LocalDateTime endDate);
  ///#endregion

  ///#region 函数:RemoveRelation(string accountId, string roleId)

  /**
   * 移除帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   */
  int RemoveRelation(String accountId, String roleId);
  ///#endregion

  ///#region 函数:RemoveDefaultRelation(string accountId)

  /**
   * 移除帐号相关角色的默认关系
   *
   * @param accountId 帐号标识
   */
  int RemoveDefaultRelation(String accountId);
  ///#endregion

  ///#region 函数:RemoveNondefaultRelation(string accountId)

  /**
   * 移除帐号相关角色的非默认关系
   *
   * @param accountId 帐号标识
   */
  int RemoveNondefaultRelation(String accountId);
  ///#endregion

  ///#region 函数:RemoveExpiredRelation(string accountId)

  /**
   * 移除帐号已过期的角色关系
   *
   * @param accountId 帐号标识
   */
  int RemoveExpiredRelation(String accountId);
  ///#endregion

  ///#region 函数:RemoveAllRelation(string accountId)

  /**
   * 移除帐号相关角色的所有关系
   *
   * @param accountId 帐号标识
   */
  int RemoveAllRelation(String accountId);
  ///#endregion

  ///#region 函数:HasDefaultRelation(string accountId)

  /**
   * 检测帐号是否有默认角色
   *
   * @param accountId 帐号标识
   */
  boolean HasDefaultRelation(String accountId);

  /**
   * 设置帐号的默认岗位
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   */
  int SetDefaultRelation(String accountId, String roleId);

  /**
   * 清理角色与帐号的关系
   *
   * @param roleId 角色标识
   */
  int ClearupRelation(String roleId);
}
