package com.x3platform.membership.services;

import com.x3platform.AuthorizationObject;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.AccountOrganizationUnitRelation;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ruanyu
 */
public interface OrganizationUnitService {
  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param OrganizationUnit 实例详细信息
   * @return OrganizationUnit 实例详细信息
   */
  int save(OrganizationUnit param);

  /**
   * 虚拟删除记录
   *
   * @param id 标识
   */
  int remove(String id);

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
   * @param id 组织单位标识
   * @return 返回一个 OrganizationUnit 实例的详细信息
   */
  OrganizationUnit findOne(String id);

  /**
   * 查询某条记录
   *
   * @param globalName 组织的全局名称
   * @return 返回一个{@link OrganizationUnit}实例的详细信息
   */
  OrganizationUnit findOneByGlobalName(String globalName);

  /**
   * 查询某个角色所属的组织信息
   *
   * @param roleId 角色标识
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  OrganizationUnit findOneByRoleId(String roleId);

  /**
   * 查询某个角色所属的某一级次的组织信息
   *
   * @param roleId 角色标识
   * @param level 层次
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  OrganizationUnit findOneByRoleId(String roleId, int level);

  /**
   * 查询某个组织所属的公司信息
   *
   * @param organizationId 组织标识
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  OrganizationUnit findCorporationByOrganizationUnitId(String organizationId);

  /**
   * 查询某个组织的所属某个上级部门信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  OrganizationUnit findDepartmentByOrganizationUnitId(String organizationId, int level);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 {@link OrganizationUnit} 实例的详细信息
   */
  List<OrganizationUnit> findAll(DataQuery query);

  /**
   * 查询列表型 数据问题；
   *
   * @param params
   * @return
   */
  List<OrganizationUnit> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  List<OrganizationUnit> findAll();

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 一个 {@link OrganizationUnit} 实例的详细信息
   */
  List<OrganizationUnit> findAllByParentId(String parentId);

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @param depth 深入获取的层次，0 表示只获取本层次，-1 表示全部获取
   * @return 返回所有实例 {@link OrganizationUnit} 的详细信息
   */
  List<OrganizationUnit> findAllByParentId(String parentId, int depth);

  /**
   * @param level 查询等级
   * @param parentId 查询等级
   */
  OrganizationUnit findPhysicalTreeView(String parentId, int level);

  /**
   * 查询某个用户所在的所有组织单位
   *
   * @param accountId 帐号标识
   * @return 返回一个 OrganizationUnit 实例的详细信息
   */
  List<OrganizationUnit> findAllByAccountId(String accountId);

  /**
   * 基于组织关系查询
   * @param accountId
   * @return
   */
  List<OrganizationUnit> findAllRelationOrganizationUnitByAccountId(String accountId);

  /**
   * 查询某个角色的所属相关组织
   *
   * @param roleIds 角色标识
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  List<OrganizationUnit> findAllByRoleIds(String roleIds);

  /**
   * 查询某个角色的所属相关组织
   *
   * @param roleIds 角色标识
   * @param level 层次
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  List<OrganizationUnit> findAllByRoleIds(String roleIds, int level);

  /**
   * 递归查询某个公司下面所有的组织
   *
   * @param corporationId 公司标识
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  List<OrganizationUnit> findAllByCorporationId(String corporationId);

  /**
   * 递归查询某个项目下面所有的组织
   *
   * @param projectId 项目标识
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  List<OrganizationUnit> findAllByProjectId(String projectId);

  /**
   * 查询某个帐户所属的所有公司信息
   *
   * @param accountId 帐号标识
   * @return 返回所有{@link OrganizationUnit}实例的详细信息
   */
  List<OrganizationUnit> findCorporationsByAccountId(String accountId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 组织单位标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 组织单位名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 查询是否存在全局名称的相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(String globalName);

  /**
   * 重命名
   *
   * @param id 组织单元标识
   * @param name 组织名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int rename(String id, String name);

  /**
   * 组合全路径
   *
   * @param name 组织名称
   * @param parentId 父级对象标识
   */
  String combineFullPath(String name, String parentId);

  /**
   * 根据组织标识计算组织的全路径
   *
   * @param organizationId 组织标识
   */
  String getOrganizationPathByOrganizationUnitId(String organizationId);

  /**
   * 组合唯一名称
   *
   * @param globalName 组织全局名称
   * @param id 对象标识
   */
  String combineDistinguishedName(String globalName, String id);

  /**
   * 根据组织标识计算 Active Directory OU 路径
   *
   * @param organizationId 组织标识
   */
  String getLdapOuPathByOrganizationUnitId(String organizationId);

  /**
   * 设置全局名称
   *
   * @param id 组织单元标识
   * @param globalName 全局名称
   * @return 修改成功, 返回 0, 修改失败, 返回 1.
   */
  int setGlobalName(String id, String globalName);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 组织单元标识
   * @param parentId 父级组织标识
   * @return 修改成功, 返回 0, 修改失败, 返回 1.
   */
  int setParentId(String id, String parentId);

  /**
   * 设置企业邮箱状态
   *
   * @param id 组织单元标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setEmailStatus(String id, int status);

  /**
   * 设置状态
   *
   * @param id 组织单元标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setStatus(String id, int status);

  /**
   * 获取组织的子成员
   *
   * @param organizationId 组织单位标识
   */
  List<AuthorizationObject> getChildNodes(String organizationId);

  /**
   * 查询当前组织下， 及 一级所有的组织机构
   */
  List<OrganizationUnit> getChildOrganizationByOrganizationUnitId(String organizationUnitId);

  /**
   *  查询当前组织级删除的组织
   */
  List<OrganizationUnit> getChildOrganizationByDeleteOrganizationUnitId(String organizationUnitId);

  /**
   * 同步信息至 Active Directory
   *
   * @param param 组织信息
   * @return 消息代码
   */
  int syncToLdap(OrganizationUnit param);

  // -------------------------------------------------------
  // 树形视图
  // -------------------------------------------------------

  /**
   * 获取树形数据信息
   *
   * @param treeViewName 树形视图名称
   * @param treeViewRootTreeNodeId 树形视图根节点标识
   * @param commandFormat 命令格式
   * @return {@link TreeView} 对象
   */
  TreeView getTreeView(String treeViewName, String treeViewRootTreeNodeId, String commandFormat);

  /**
   * 根据父级节点标识动态获取下一层级的数据信息
   *
   * @param treeViewName 树形视图名称
   * @param treeViewRootTreeNodeId 树形视图根节点标识
   * @param parentId 父级节点标识
   * @param commandFormat 命令格式
   * @return {@link DynamicTreeView} 对象
   */
  DynamicTreeView getDynamicTreeView(String treeViewName, String treeViewRootTreeNodeId, String parentId,
    String commandFormat);

  /**
   * 获取树形表格数据信息
   *
   * @param id 根节点标识
   * @return {@link List} 对象
   */
  List<OrganizationUnit> getTreeTable(String id);

  // -------------------------------------------------------
  // 设置帐号和组织关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关组织的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, OrganizationUnitId, IsDefault, BeginDate, EndDate
   */
  List<AccountOrganizationUnitRelation> findAllRelationByAccountId(String accountId);

  /**
   * 根据组织查询相关帐号的关系
   *
   * @param organizationUnitId 组织标识
   * @return Table Columns：AccountId, OrganizationUnitId, IsDefault, BeginDate, EndDate
   */
  List<AccountOrganizationUnitRelation> findAllRelationByOrganizationUnitId(String organizationUnitId);

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationId 组织标识
   */
  int addRelation(String accountId, String organizationId);

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationId 组织标识
   * @param isDefault 是否是默认组织
   * @param beginDate 启用时间
   * @param endDate 停用时间
   * @return 消息代码
   */
  int addRelation(String accountId, String organizationId, boolean isDefault, Date beginDate, Date endDate);

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountIds 帐号标识，多个以逗号隔开
   * @param organizationId 组织标识
   */
  int addRelationRange(String accountIds, String organizationId);

  /**
   * 添加帐号与相关组织的父级组织关系(递归)
   *
   * @param accountId 帐号标识
   * @param organizationId 组织标识
   */
  int addParentRelations(String accountId, String organizationId);

  /**
   * 续约帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationId 组织标识
   * @param endDate 新的截止时间
   */
  int extendRelation(String accountId, String organizationId, Date endDate);

  /**
   * 移除帐号与相关组织的关系
   *
   * @param accountId 帐号标识
   * @param organizationId 组织标识
   */
  int removeRelation(String accountId, String organizationId);

  /**
   * 移除帐号相关组织的默认关系
   *
   * @param accountId 帐号标识
   * @return 消息代码
   */
  int removeDefaultRelation(String accountId);

  /**
   * 移除帐号相关组织的非默认关系
   *
   * @param accountId 帐号标识
   * @return 消息代码
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
   *
   * @param accountId 帐号标识
   */
  int removeAllRelation(String accountId);

  /**
   * 检测帐号是否有默认组织
   *
   * @param accountId 帐号标识
   */
  boolean hasDefaultRelation(String accountId);

  /**
   * 设置帐号的默认岗位
   *
   * @param accountId 帐号标识
   * @param organizationUnitId 组织单元标识
   */
  int setDefaultRelation(String accountId, String organizationUnitId);

  /**
   * 清理组织与帐号的关系
   *
   * @param organizationUnitId 组织单元标识
   */
  int clearupRelation(String organizationUnitId);
}
