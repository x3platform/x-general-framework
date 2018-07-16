package com.x3platform.membership.services.impl;

import com.x3platform.membership.*;
import com.x3platform.membership.mappers.RoleMapper;
import com.x3platform.membership.models.RoleInfo;
import com.x3platform.membership.services.IRoleService;
import com.x3platform.security.authority.AuthorityInfo;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 *
 */
@Service
public class RoleService implements IRoleService {

  /**
   * 数据提供器
   */
  @Autowired
  private RoleMapper provider = null;

  /**
   */
  // public RoleService()
  //{
  //  this.configuration = MembershipConfigurationView.Instance.Configuration;

  // 创建对象构建器(Spring.NET)
  //  String springObjectFile = this.configuration.keySet()["SpringObjectFile"].Value;

  //  SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(MembershipConfiguration.ApplicationName, springObjectFile);

  // 创建数据提供器
  //  this.provider = objectBuilder.<IRoleProvider>GetObject(IRoleProvider.class);
  // }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param AccountInfo 实例详细信息
   * @return AccountInfo 实例详细信息
   */
  public final IRoleInfo save(IRoleInfo param) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IRoleInfo originalObject = findOne(param.getId());

      if (originalObject == null) {
        originalObject = param;
      }

      this.SyncToLDAP(param, originalObject.getGlobalName(), originalObject.getOrganizationUnitId());
    }
    */

    // 设置组织全路径
    param.setFullPath(this.combineFullPath(param.getName(), param.getOrganizationUnitId()));

    // 设置唯一识别名称
    param.setDistinguishedName(this.combineDistinguishedName(param.getName(), param.getOrganizationUnitId()));

    param = provider.save(param);

    if (param != null) {
      String roleId = param.getId();

      // 绑定新的关系
      if (!StringUtil.isNullOrEmpty(roleId)) {
        // 1.移除非默认成员关系
        MembershipManagement.getInstance().getRoleService().clearupRelation(roleId);

        // 2.设置新的关系
        for (IAccountInfo item : param.getMembers()) {
          MembershipManagement.getInstance().getRoleService().addRelation(item.getId(), roleId);
        }
      }
    }

    return param;
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  public final void delete(String id) {
    this.provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id AccountInfo Id号
   * @return 返回一个 AccountInfo 实例的详细信息
   */
  public final IRoleInfo findOne(String id) {
    return provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @param globalName 角色的全局名称
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  public final IRoleInfo findOneByGlobalName(String globalName) {
    return provider.findOneByGlobalName(globalName);
  }

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId  组织标识
   * @param standardRoleId 标准角色标识
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  public final IRoleInfo findOneByCorporationIdAndStandardRoleId(String corporationId, String standardRoleId) {
    return provider.findOneByCorporationIdAndStandardRoleId(corporationId, standardRoleId);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 AccountInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAll() {
    return provider.findAll("", 0);
  }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有 AccountInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAll(String whereClause) {
    return provider.findAll(whereClause, 0);
  }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有 AccountInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAll(String whereClause, int length) {
    return provider.findAll(whereClause, length);
  }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 返回一个 IOrganizationUnitInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAllByParentId(String parentId) {
    return findAllByParentId(parentId, 0);
  }

  /**
   * 查询某个组织节点下的所有角色信息
   *
   * @param parentId 父节标识
   * @param depth    深入获取的层次，0表示只获取本层次，-1表示全部获取
   * @return 返回所有实例<see cref="IOrganizationUnitInfo"/>的详细信息
   */
  public final List<IRoleInfo> findAllByParentId(String parentId, int depth) {
    // 结果列表
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    //
    // 查找组织子部门的角色信息
    //

    List<IRoleInfo> roles = provider.findAllByParentId(parentId);

    list.addAll(roles);

    if (depth == -1) {
      depth = Integer.MAX_VALUE;
    }

    if (!roles.isEmpty() && depth > 0) {
      for (IRoleInfo role : roles) {
        list.addAll(findAllByParentId(role.getId(), (depth - 1)));
      }
    }

    return list;
  }

  /**
   * 查询某条记录
   *
   * @param accountId AccountInfo Id号
   * @return 返回一个 AccountInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * 查询某个组织下的所有角色
   *
   * @param organizationId 组织标识
   * @return 返回一个 IRoleInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAllByOrganizationUnitId(String organizationId) {
    return this.findAllByOrganizationUnitId(organizationId, 0);
  }

  /**
   * 查询某个组织节点下的所有角色信息
   *
   * @param organizationId 组织标识
   * @param depth          深入获取的层次，0表示只获取本层次，-1表示全部获取
   * @return 返回所有实例<see cref="IOrganizationUnitInfo"/>的详细信息
   */
  public final List<IRoleInfo> findAllByOrganizationUnitId(String organizationId, int depth) {
    // 结果列表
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    // -------------------------------------------------------
    // 查找组织子部门的角色信息
    // -------------------------------------------------------

    // TODO 待处理
    // List<IOrganizationUnitInfo> organizations = MembershipManagement.getInstance().getOrganizationUnitService().findAllByParentId(organizationId);
    List<IOrganizationUnitInfo> organizations = null;

    // -------------------------------------------------------
    // 查找角色信息
    // -------------------------------------------------------

    list.addAll(this.provider.findAllByOrganizationUnitId(organizationId));

    if (depth == -1) {
      depth = Integer.MAX_VALUE;
    }

    if (!organizations.isEmpty() && depth > 0) {
      for (IOrganizationUnitInfo organization : organizations) {
        list.addAll(findAllByOrganizationUnitId(organization.getId(), (depth - 1)));
      }
    }

    return list;
  }
  ///#endregion

  ///#region 函数:FindAllByGeneralRoleId(string generalRoleId)

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param generalRoleId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByGeneralRoleId(String generalRoleId) {
    return provider.findAllByGeneralRoleId(generalRoleId);
  }
  ///#endregion

  ///#region 函数:FindAllByStandardOrganizationUnitId(string standardOrganizationUnitId)

  /**
   * 递归查询某个标准组织下面所有的角色
   *
   * @param standardOrganizationUnitId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByStandardOrganizationUnitId(String standardOrganizationUnitId) {
    // 结果列表
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    // 临时列表
    List<IRoleInfo> temp = null;

    //
    // 查找角色信息
    //

    temp = provider.findAllByStandardOrganizationUnitId(standardOrganizationUnitId);

    list.addAll(temp);

    //
    // 查找组织子部门的角色信息
    //

    /*
    // TODO 待处理
    List<IStandardOrganizationUnitInfo> organizations = MembershipManagement.Instance.StandardOrganizationUnitService.FindAllByParentId(standardOrganizationUnitId);

    for (IStandardOrganizationUnitInfo organization : organizations) {
      temp = findAllByStandardOrganizationUnitId(organization.getId());

      list.addAll(temp);
    }
    */

    return list;
  }

  /**
   * 递归查询某个标准角色下面所有的角色
   *
   * @param standardRoleId 标准角色标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByStandardRoleId(String standardRoleId) {
    return provider.findAllByStandardRoleId(standardRoleId);
  }
  ///#endregion

  ///#region 函数:FindAllByOrganizationUnitIdAndJobId(string organizationId, string jobId)

  /**
   * 递归查询某个组织下面相关的职位对应的角色信息
   *
   * @param organizationId 组织标识
   * @param jobId          职位标识
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByOrganizationUnitIdAndJobId(String organizationId, String jobId) {
    return provider.findAllByOrganizationUnitIdAndJobId(organizationId, jobId);
  }
  ///#endregion

  ///#region 函数:FindAllByAssignedJobId(string assignedJobId)

  /**
   * 递归查询某个组织下面相关的岗位对应的角色信息
   *
   * @param assignedJobId 岗位标识
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByAssignedJobId(String assignedJobId) {
    return provider.findAllByAssignedJobId(assignedJobId);
  }
  ///#endregion

  ///#region 函数:FindAllByCorporationIdAndProjectId(string corporationIds, string projectIds)

  /**
   * 递归查询某个公司下面所有的角色和某个项目下面所有的角色
   *
   * @param corporationIds 公司标识，多个以逗号隔开
   * @param projectIds     项目标识，多个以逗号隔开
   * @return 返回一个<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByCorporationIdAndProjectId(String corporationIds, String projectIds) {
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    // 查询相关公司的所有角色
    list.addAll(this.findAllByCorporationIds(corporationIds));

    // 查询相关项目的所有角色
    list.addAll(this.findAllByProjectIds(projectIds));

    return list;
  }

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationIds 公司标识，多个以逗号隔开
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByCorporationIds(String corporationIds) {
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    // 查询公司的所有角色
    if (!StringUtil.isNullOrEmpty(corporationIds)) {
      // String[] keys = corporationIds.split(new char[]{',', ';'}, StringSplitOptions.RemoveEmptyEntries);
      String[] keys = corporationIds.split(",|;");

      for (String key : keys) {
        list.addAll(this.findAllByCorporationId(key));
      }
    }

    return list;
  }

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByCorporationId(String corporationId) {
    // 结果列表
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    //
    // 查找部门(公司下一级组织架构)
    //
    // List<IOrganizationUnitInfo> organizations = MembershipManagement.getInstance().getOrganizationUnitService().FindAllByParentId(corporationId);
    List<IOrganizationUnitInfo> organizations = null;

    //
    // 查找角色信息
    //

    list.addAll(findAllByOrganizationUnitId(corporationId));

    for (IOrganizationUnitInfo organization : organizations) {
      // 获取项目团队以外的只能部门
      if (organization.getName().indexOf("项目团队") == -1) {
        list.addAll(findAllByOrganizationUnitId(organization.getId(), -1));
      }
    }

    return list;
  }
  ///#endregion

  ///#region 函数:FindAllByProjectIds(string projectIds)

  /**
   * 递归查询某个项目下面所有的角色
   *
   * @param projectIds 项目标识，多个以逗号隔开
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByProjectIds(String projectIds) {
    ArrayList<IRoleInfo> list = new ArrayList<IRoleInfo>();

    // 查询公司的所有角色
    if (!StringUtil.isNullOrEmpty(projectIds)) {
      // String[] keys = projectIds.split(new char[]{',', ';'}, StringSplitOptions.RemoveEmptyEntries);
      String[] keys = projectIds.split(",|;");

      for (String key : keys) {
        list.addAll(this.findAllByProjectId(key));
      }
    }

    return list;
  }

  /**
   * 递归查询某个项目下面所有的角色
   *
   * @param projectId 组织标识
   * @return 返回一个 IRoleInfo 实例的详细信息
   */
  public final List<IRoleInfo> findAllByProjectId(String projectId) {
    // 项目团队的标识 和 项目标识 保存一致
    String organizationId = projectId;

    return findAllByOrganizationUnitId(organizationId, -1);
  }

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId   组织标识
   * @param standardRoleIds 标准角色标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllByCorporationIdAndStandardRoleIds(String corporationId, String standardRoleIds) {
    return provider.findAllByCorporationIdAndStandardRoleIds(corporationId, standardRoleIds);
  }

  /**
   * 根据某个组织标识查询此组织上下级之间属于某一范围权重值的角色信息
   *
   * @param organizationId 组织标识
   * @param minPriority    最小权重值
   * @param maxPriority    最大权重值
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllBetweenPriority(String organizationId, int minPriority, int maxPriority) {
    return provider.findAllBetweenPriority(organizationId, minPriority, maxPriority);
  }

  /**
   * 返回所有没有成员的角色信息
   *
   * @param length 条数, 0表示全部
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllWithoutMember(int length) {
    return provider.findAllWithoutMember(length, false);
  }

  /**
   * 返回所有没有成员的角色信息
   *
   * @param length         条数, 0表示全部
   * @param includeAllRole 包含全部角色
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findAllWithoutMember(int length, boolean includeAllRole) {
    return provider.findAllWithoutMember(length, includeAllRole);
  }

  /**
   * 返回所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findForwardLeadersByOrganizationUnitId(String organizationId) {
    return provider.findForwardLeadersByOrganizationUnitId(organizationId, 1);
  }

  /**
   * 返回所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findForwardLeadersByOrganizationUnitId(String organizationId, int level) {
    return provider.findForwardLeadersByOrganizationUnitId(organizationId, level);
  }

  /**
   * 返回所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findBackwardLeadersByOrganizationUnitId(String organizationId) {
    return provider.findBackwardLeadersByOrganizationUnitId(organizationId, 1);
  }

  /**
   * 返回所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findBackwardLeadersByOrganizationUnitId(String organizationId, int level) {
    return provider.findBackwardLeadersByOrganizationUnitId(organizationId, level);
  }
  ///#endregion

  ///#region 函数:FindStandardGeneralRolesByOrganizationUnitId(string organizationId, int standardGeneralRoleId)

  /**
   * 返回所有父级对象为标准通用角色标识【standardGeneralRoleId】的相关角色信息
   *
   * @param organizationId        组织标识
   * @param standardGeneralRoleId 标准通用角色标识
   * @return 返回所有<see cref="IRoleInfo"/>实例的详细信息
   */
  public final List<IRoleInfo> findStandardGeneralRolesByOrganizationUnitId(String organizationId, String standardGeneralRoleId) {
    return provider.findStandardGeneralRolesByOrganizationUnitId(organizationId, standardGeneralRoleId);
  }
  ///#endregion

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  ///#region 函数:GetPaging(int startIndex, int pageSize, DataQuery query, out int rowCount)

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例<see cref="IRoleInfo"/>
   */
  // public final List<IRoleInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 检测是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  public final boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param name 组织单位名称
   * @return 布尔值
   */
  public final boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 角色全局名称
   * @return 布尔值
   */
  public final boolean isExistGlobalName(String globalName) {
    return provider.isExistGlobalName(globalName);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param id   角色标识
   * @param name 角色名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  public final int rename(String id, String name) {
    // 检测名称是否已被使用
    if (isExistName(name)) {
      // 已存在相同名称对象。
      return 1;
    }

    // 检测是否存在对象
    if (!isExist(id)) {
      // 对象【${Id}】不存在。
      return 2;
    }

    return provider.rename(id, name);
  }

  /**
   * 获取所有人角色
   */
  public final IRoleInfo getEveryoneObject() {
    IRoleInfo everyone = new RoleInfo();

    everyone.setId(UUIDUtil.emptyString());
    everyone.setName("所有人");

    return everyone;
  }

  /**
   * 角色全路径
   *
   * @param name           角色名称
   * @param organizationId 所属组织标识
   * @return
   */
  public final String combineFullPath(String name, String organizationId) {
    // TODO 待处理
    // String path = MembershipManagement.getInstance().getOrganizationUnitService().getOrganizationPathByOrganizationUnitId(organizationId);
    String path = "";

    return String.format("%1$s%2$s", path, name);
  }

  /**
   * 角色唯一名称
   *
   * @param name           角色名称
   * @param organizationId 所属组织标识
   * @return
   */
  public final String combineDistinguishedName(String name, String organizationId) {
    // TODO 待处理
    // String path = MembershipManagement.getInstance().getOrganizationUnitService().GetLDAPOUPathByOrganizationUnitId(organizationId);
    //   return String.format("CN=%1$s,%2$s%3$s", name, path, LDAPConfigurationView.Instance.SuffixDistinguishedName);

    String path = "";

    return String.format("CN=%1$s,%2$s%3$s", name, path, "");
  }

  /**
   * 设置全局名称
   *
   * @param id         帐户标识
   * @param globalName 全局名称
   * @return 修改成功, 返回 0, 修改失败, 返回 1.
   */
  public final int setGlobalName(String id, String globalName) {
    if (StringUtil.isNullOrEmpty(globalName)) {
      // 对象【${Id}】全局名称不能为空。
      return 1;
    }

    if (isExistGlobalName(globalName)) {
      return 2;
    }

    // 检测是否存在对象
    if (!isExist(id)) {
      // 对象【${Id}】不存在。
      return 3;
    }
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IRoleInfo originalObject = findOne(id);

      if (originalObject != null) {
        // 由于外部系统直接同步到人员及权限管理的数据库中，
        // 所以 Active Directory 上不会直接创建相关对象，需要手工设置全局名称并创建相关对象。
        if (!StringUtil.isNullOrEmpty(originalObject.GlobalName) && LDAPManagement.Instance.Group.IsExistName(originalObject.GlobalName)) {
          LDAPManagement.Instance.Group.Rename(originalObject.GlobalName, globalName);
        } else {
          LDAPManagement.Instance.Group.Add(globalName, MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(originalObject.Id));
        }
      }
    }
    */
    return provider.setGlobalName(id, globalName);
  }

  /**
   * 设置父级角色标识
   *
   * @param id       角色标识
   * @param parentId 父级角色标识
   * @return 0:代表成功
   */
  public final int setParentId(String id, String parentId) {
    return provider.setParentId(id, parentId);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param id     角色标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  public final int setExchangeStatus(String id, int status) {
    return provider.setExchangeStatus(id, status);
  }

  /**
   * 获取角色的权限
   *
   * @param roleId 角色标识
   */
  public final List<AuthorityInfo> getAuthorities(String roleId) {
    return provider.getAuthorities(roleId);
  }
  ///#endregion

  ///#region 函数:GenerateStandardRoleMappingReport(string organizationId, string standardRoleType)

  /**
   * 生成标准角色映射报表
   *
   * @param organizationId   组织标识
   * @param standardRoleType 标准角色类型
   */
  /*
  public final DataTable GenerateStandardRoleMappingReport(String organizationId, String standardRoleType) {
    List<IStandardRoleInfo> list = MembershipManagement.Instance.StandardRoleService.FindAllByType(Integer.parseInt(standardRoleType));

    String standardRoleIds = null;

    for (IStandardRoleInfo item : list) {
      standardRoleIds += item.Id + ",";
    }

    standardRoleIds = tangible.DotNetToJavaStringHelper.trimEnd(standardRoleIds, new char[]{','});

    return GenerateStandardRoleMappingReport(organizationId, standardRoleType, standardRoleIds);
  }
  */

  /**
   * 生成标准角色映射报表
   *
   * @param organizationId   组织标识
   * @param standardRoleType 标准角色类型
   * @param standardRoleIds  标准角色标识，多个以逗号隔开
   */
  /*
  public final DataTable GenerateStandardRoleMappingReport(String organizationId, String standardRoleType, String standardRoleIds) {
    DataTable table = new DataTable();

    table.Columns.Add("standardRoleId");
    table.Columns.Add("roleId");
    table.Columns.Add("roleName");
    table.Columns.Add("roleIsCreatedValue");

    List<IRoleInfo> list = null;

    String[] keys = standardRoleIds.split("[,]", -1);

    // bool isCreated = false;

    switch (standardRoleType) {
      case "0":
      case "1":
      case "11":
      case "21":
        list = findAllByCorporationId(organizationId);
        break;

      case "2":
      case "12":
      case "22":
        list = findAllByProjectId(organizationId);
        break;

      default:
        list = new ArrayList<IRoleInfo>();
        break;
    }

    int count = 0;

    for (int i = 0; i < keys.length; i++) {
      for (IRoleInfo item : list) {
        if (item.StandardRole == null) {
          continue;
        }

        if (keys[i].equals(item.StandardRole.Id)) {
          DataRow row = table.NewRow();

          row.setItem("standardRoleId", keys[i]);
          row.setItem("roleId", item.Id);
          row.setItem("roleName", item.Name);
          row.setItem("roleIsCreatedValue", "1"); // 1:代表已创建

          table.Rows.Add(row);

          break;
        }
      }

      count++;
    }

    return table;
  }
  */

  /**
   * 快速创建标准角色
   *
   * @param standardRoleType 标准角色类型
   * @param organizationId   公司或项目标识
   * @param standardRoleId   标准角色标识
   * @param roleName         角色名称
   */
  public int quickCreateRole(String standardRoleType, String organizationId, String standardRoleId, String roleName) {
    if (MembershipManagement.getInstance().getRoleService().isExistName(roleName)) {
      return 1;
    }
    /*
    IStandardRoleInfo standardRoleInfo = MembershipManagement.Instance.StandardRoleService[standardRoleId];

    if (standardRoleInfo == null) {
      return 2;
    } else {
      RoleInfo param = new RoleInfo();

      param.setId(StringUtil.toUuid());
      param.Name = roleName;
      param.OrganizationUnitId = organizationId;
      param.ParentId = UUIDUtil.emptyString();
      param.setStandardRoleId(standardRoleId);
      param.setStatus(1);

      List<IOrganizationUnitInfo> organizations = null;

      List<IRoleInfo> roles = null;

      switch (standardRoleType) {
        case "0":
        case "1":
        case "11":
        case "21":
          organizations = MembershipManagement.Instance.OrganizationUnitService.FindAllByCorporationId(organizationId);
          roles = MembershipManagement.getInstance.RoleService.FindAllByCorporationId(organizationId);
          break;
        case "2":
        case "12":
        case "22":
          organizations = MembershipManagement.Instance.OrganizationUnitService.FindAllByProjectId(organizationId);
          roles = MembershipManagement.Instance.RoleService.FindAllByCorporationId(organizationId);
          break;
        default:
          return 0;
      }

      for (IOrganizationUnitInfo organization : organizations) {
        // 根据标准组织找上级组织
        if (standardRoleInfo.StandardOrganizationUnitId == organization.StandardOrganizationUnitId) {
          param.OrganizationUnitId = organization.Id;
        }
      }

      for (IRoleInfo role : roles) {
        // 根据标准角色找上级角色
        if (standardRoleInfo.ParentId == role.StandardRoleId) {
          param.ParentId = role.Id;
        }
      }

      MembershipManagement.Instance.RoleService.Save(param);
    }
    */
    return 0;
  }
  ///#endregion

  ///#region 函数:CreateRoleWithProjectAndStandardRole(string projectId, string standardRoleId, string roleName)

  /**
   * 新建项目类标准角色
   *
   * @param projectId      项目标识
   * @param standardRoleId 标准角色标识
   * @param roleName       角色名称
   */
  public int CreateRoleWithProjectAndStandardRole(String projectId, String standardRoleId, String roleName) {
    if (MembershipManagement.getInstance().getRoleService().isExistName(roleName)) {
      return 1;
    }
    /*
    IStandardRoleInfo standardRoleInfo = MembershipManagement.Instance.StandardRoleService[standardRoleId];

    if (standardRoleInfo == null) {
      return 2;
    } else {
      RoleInfo param = new RoleInfo();

      param.Id = StringHelper.ToGuid();
      param.Name = roleName;
      param.GlobalName = roleName;
      param.OrganizationUnitId = projectId;
      param.ParentId = StringHelper.ToGuid(UUID.Empty);
      param.StandardRoleId = standardRoleId;
      param.Status = 1;

      List<IOrganizationUnitInfo> organizations = MembershipManagement.Instance.OrganizationUnitService.FindAllByProjectId(projectId);

      for (IOrganizationUnitInfo organization : organizations) {
        // 根据标准组织找上级组织
        if (standardRoleInfo.StandardOrganizationUnitId == organization.StandardOrganizationUnitId) {
          param.OrganizationUnitId = organization.Id;
        }
      }

      List<IRoleInfo> roles = MembershipManagement.Instance.RoleService.FindAllByProjectId(projectId);

      for (IRoleInfo role : roles) {
        // 根据标准角色找上级角色
        if (standardRoleInfo.ParentId == role.StandardRoleId) {
          param.ParentId = role.Id;
        }
      }

      MembershipManagement.Instance.RoleService.Save(param);
    }
    */
    return 0;
  }

  /**
   * 设置项目角色映射关系
   *
   * @param fromProjectId 来源项目标识
   * @param toProjectId   目标项目标识
   */
  /*
  public final DataTable SetProjectRoleMapping(String fromProjectId, String toProjectId) {
    DataTable table = new DataTable();

    table.Columns.Add("fromProjectOrganizationUnitId");
    table.Columns.Add("fromProjectRoleId");
    table.Columns.Add("fromProjectRoleName");
    table.Columns.Add("fromProjectRoleAccountValue");
    table.Columns.Add("fromProjectRoleStandardRoleId");
    table.Columns.Add("toProjectRoleId");
    table.Columns.Add("toProjectRoleName");
    table.Columns.Add("toProjectRoleAccountValue");

    List<IRoleInfo> fromProjectRoles = findAllByProjectId(fromProjectId);

    List<IRoleInfo> toProjectRoles = findAllByProjectId(toProjectId);

    // 添加来源项目角色数据
    // 到fromProjectOrganizationUnitId，fromProjectRoleId，fromProjectRoleName，fromProjectRoleAccountValue。
    for (IRoleInfo role : fromProjectRoles) {
      // 忽略没有所属标准角色的角色
      if (StringUtil.isNullOrEmpty(role.StandardRoleId)) {
        continue;
      }

      DataRow row = table.NewRow();

      row.setItem("fromProjectOrganizationUnitId", role.OrganizationUnitId);
      row.setItem("fromProjectRoleId", role.Id);
      row.setItem("fromProjectRoleName", role.Name);
      row.setItem("fromProjectRoleAccountValue", SetProjectRoleMappingAccountValue(role.Id));
      row.setItem("fromProjectRoleStandardRoleId", role.StandardRoleId);

      table.Rows.Add(row);
    }

    // 映射目标项目角色数据
    // 到toProjectRoleId，toProjectRoleName，toProjectRoleAccountValue。
    for (IRoleInfo role : toProjectRoles) {
      // 忽略没有所属标准角色的角色
      if (StringUtil.isNullOrEmpty(role.StandardRoleId)) {
        continue;
      }

      for (int i = 0; i < table.Rows.size(); i++) {
        if (table.Rows[i]["fromProjectRoleStandardRoleId"].toString().equals(role.StandardRoleId)) {
          table.Rows[i]["toProjectRoleId"] = role.Id;
          table.Rows[i]["toProjectRoleName"] = role.Name;
          table.Rows[i]["toProjectRoleAccountValue"] = SetProjectRoleMappingAccountValue(role.Id);
          break;
        }
      }
    }

    return table;
  }
  */

  /**
   * 设置项目角色映射关系中的帐号数据
   *
   * @param roleId 角色标识
   */
  public final String SetProjectRoleMappingAccountValue(String roleId) {
    StringBuilder outString = new StringBuilder();

    List<IAccountInfo> list = MembershipManagement.getInstance().getAccountService().findAllByRoleId(roleId);

    for (IAccountInfo item : list) {
      // 过滤禁用的用户
      if (item.getStatus() == 0) {
        continue;
      }

      outString.append(String.format("account#%1$s#%2$s;", item.getId(), item.getName()));
    }

    if (outString.length() > 1 && StringUtil.substring(outString.toString(), outString.length() - 1, 1).equals(";")) {
      outString = outString.deleteCharAt(outString.length() - 1);
    }

    return outString.toString();
  }

  /**
   * 创建数据包
   *
   * @param beginDate 开始时间
   * @param endDate   结束时间
   */
  public final String CreatePackage(java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate) {
    StringBuilder outString = new StringBuilder();

    // String whereClause = String.format(" ModifiedDate BETWEEN ##%1$s## AND ##%2$s## ", beginDate, endDate);

    // List<IRoleInfo> list = MembershipManagement.getInstance().getRoleService().findAllBetweenModifiedDate(beginDate, endDate);
    List<IRoleInfo> list = null;

    outString.append(String.format("<package packageType=\"role\" beginDate=\"%1$s\" endDate=\"%2$s\">", beginDate, endDate));

    outString.append("<roles>");

    for (IRoleInfo item : list) {
      outString.append(((RoleInfo) item).serializable());
    }

    outString.append("</roles>");

    outString.append("</package>");

    return outString.toString();
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 角色信息
   */
  public final int syncToLDAP(IRoleInfo param) {
    return syncToLDAP(param, param.getName(), param.getOrganizationUnitId());
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param                      角色信息
   * @param originalGlobalName         原始的全局名称
   * @param originalOrganizationUnitId 原始的所属组织标识
   */
  public final int syncToLDAP(IRoleInfo param, String originalGlobalName, String originalOrganizationUnitId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      if (StringUtil.isNullOrEmpty(param.Name)) {
        // 角色【${Name}】名称为空，请配置相关信息。
        return 1;
      } else if (StringUtil.isNullOrEmpty(param.GlobalName)) {
        // 角色【${GlobalName}】名称为空，请配置相关信息。
        return 1;
      } else {
        // 1.原始的全局名称不为空。
        // 2.Active Directory 上有相关对象。
        if (!StringUtil.isNullOrEmpty(originalGlobalName) && LDAPManagement.Instance.Group.IsExistName(originalGlobalName)) {
          if (!originalGlobalName.equals(param.GlobalName)) {
            // 角色【${Name}】的名称发生改变。
            LDAPManagement.Instance.Group.Rename(originalGlobalName, param.GlobalName);
          }

          if (!originalOrganizationUnitId.equals(param.OrganizationUnitId)) {
            // 角色【${Name}】所属的组织发生变化。
            LDAPManagement.Instance.Group.MoveTo(param.GlobalName, MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(param.OrganizationUnitId));
          }

          return 0;
        } else {
          String parentPath = MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(param.OrganizationUnitId);

          LDAPManagement.Instance.Group.Add(param.GlobalName, parentPath);

          // 角色【${Name}】创建成功。
          return 0;
        }
      }
    }
    */
    return 0;
  }

  /**
   * 同步信息
   *
   * @param param 角色信息
   */
  // public final int SyncFromPackPage(IRoleInfo param) {
  //  return provider.SyncFromPackPage(param);
  // }

  // -------------------------------------------------------
  // 设置帐号和角色关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关角色的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, RoleId, isDefault, BeginDate, EndDate
   */
  public final List<IAccountRoleRelationInfo> findAllRelationByAccountId(String accountId) {
    return provider.findAllRelationByAccountId(accountId);
  }

  /**
   * 根据角色查询相关帐号的关系
   *
   * @param roleId 角色标识
   * @return Table Columns：AccountId, RoleId, isDefault, BeginDate, EndDate
   */
  public final List<IAccountRoleRelationInfo> findAllRelationByRoleId(String roleId) {
    return provider.findAllRelationByRoleId(roleId);
  }

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   */
  public final int addRelation(String accountId, String roleId) {
    return addRelation(accountId, roleId, false, java.time.LocalDateTime.now(), java.time.LocalDateTime.MAX);
  }

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   * @param isDefault 是否是默认角色
   * @param beginDate 启用时间
   * @param endDate   停用时间
   */
  public final int addRelation(String accountId, String roleId, boolean isDefault, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate) {
    if (StringUtil.isNullOrEmpty(accountId)) {
      // 帐号标识不能为空
      return 1;
    }

    if (StringUtil.isNullOrEmpty(roleId)) {
      // 角色标识不能为空
      return 2;
    }

    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo account = MembershipManagement.Instance.AccountService[accountId];

      IRoleInfo role = MembershipManagement.Instance.RoleService[roleId];

      // 帐号对象、帐号的全局名称、帐号的登录名、角色对象、角色的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && role != null && !StringUtil.isNullOrEmpty(role.GlobalName)) {
        LDAPManagement.Instance.Group.AddRelation(account.LoginName, LDAPSchemaClassType.User, role.GlobalName);
      }
    }
    */

    return provider.addRelation(accountId, roleId, isDefault, beginDate, endDate);
  }

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountIds 帐号标识，多个以逗号隔开
   * @param roleId     角色标识
   */
  public final int addRelationRange(String accountIds, String roleId) {
    String[] list = accountIds.split("[,]", -1);

    for (String accountId : list) {
      addRelation(accountId, roleId);
    }

    return 0;
  }

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   * @param endDate   新的截止时间
   */
  public final int extendRelation(String accountId, String roleId, java.time.LocalDateTime endDate) {
    return provider.extendRelation(accountId, roleId, endDate);
  }
  ///#endregion

  ///#region 函数:RemoveRelation(string accountId, string roleId)

  /**
   * 移除帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   */
  public final int removeRelation(String accountId, String roleId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo account = MembershipManagement.Instance.AccountService[accountId];

      IRoleInfo role = MembershipManagement.Instance.RoleService[roleId];

      // 帐号对象、帐号的全局名称、帐号的登录名、角色对象、角色的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && role != null && !StringUtil.isNullOrEmpty(role.GlobalName)) {
        LDAPManagement.Instance.Group.RemoveRelation(account.LoginName, LDAPSchemaClassType.User, role.GlobalName);
      }
    }
    */

    return provider.removeRelation(accountId, roleId);
  }

  /**
   * 移除帐号相关角色的默认关系
   *
   * @param accountId 帐号标识
   */
  public final int removeDefaultRelation(String accountId) {
    return provider.removeDefaultRelation(accountId);
  }

  /**
   * 移除帐号相关角色的非默认关系
   *
   * @param accountId 帐号标识
   */
  public final int removeNondefaultRelation(String accountId) {
    return provider.removeNondefaultRelation(accountId);
  }

  /**
   * 移除帐号已过期的角色关系
   *
   * @param accountId 帐号标识
   */
  public final int removeExpiredRelation(String accountId) {
    return provider.removeExpiredRelation(accountId);
  }

  /**
   * 移除帐号相关角色的所有关系
   *
   * @param accountId 帐号标识
   */
  public final int removeAllRelation(String accountId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<IAccountRoleRelationInfo> list = findAllRelationByAccountId(accountId);

      for (IAccountRoleRelationInfo item : list) {
        removeRelation(item.getAccountId(), item.getRoleId());
      }

      return 0;
    } else {
      return provider.removeAllRelation(accountId);
    }
    */
    return provider.removeAllRelation(accountId);
  }

  /**
   * 检测帐号的默认组织
   *
   * @param accountId 帐号标识
   */
  public final boolean hasDefaultRelation(String accountId) {
    return provider.hasDefaultRelation(accountId);
  }

  /**
   * 设置帐号的默认组织
   *
   * @param accountId 帐号标识
   * @param roleId    角色标识
   */
  public final int setDefaultRelation(String accountId, String roleId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo account = MembershipManagement.getInstance().getAccountService().findOne(accountId);

      IRoleInfo role = MembershipManagement.getInstance().getRoleService().findOne(roleId);

      if (account != null && role != null) {
        LDAPManagement.Instance.Group.AddRelation(account.getGlobalName(), LDAPSchemaClassType.User, role.getName());
      }
    }
    */
    return provider.setDefaultRelation(accountId, roleId);
  }

  /**
   * 清理角色与帐号的关系
   *
   * @param roleId 角色标识
   */
  public final int clearupRelation(String roleId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<IAccountRoleRelationInfo> list = findAllRelationByRoleId(roleId);

      for (IAccountRoleRelationInfo item : list) {
        removeRelation(item.getAccountId(), item.getRoleId());
      }

      return 0;
    } else {
      return provider.clearupRelation(roleId);
    }
    */

    return provider.clearupRelation(roleId);
  }
}
