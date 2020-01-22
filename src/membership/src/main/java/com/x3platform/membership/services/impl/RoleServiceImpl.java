package com.x3platform.membership.services.impl;

import static com.x3platform.membership.Constants.VIRTUAL_TEAM_NAME;

import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.ldap.configuration.LdapConfigurationView;
import com.x3platform.membership.Account;
import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.Role;
import com.x3platform.membership.mappers.RoleMapper;
import com.x3platform.membership.models.RoleInfo;
import com.x3platform.membership.services.RoleService;
import com.x3platform.security.authority.Authority;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 */
public class RoleServiceImpl implements RoleService {

  private static String CACHE_KEY_ID_PREFIX = "x3platform:membership:role:id:";

  private static String DIGITAL_NUMBER_KEY_CODE = "Table_Role_Key_Code";

  /**
   * 数据提供器
   */
  @Autowired(required = false)
  private RoleMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link Role} 实例详细信息
   * @return {@link Role} 实例详细信息
   */
  @Override
  public Role save(Role entity) {
    int affectedRows;
    String id = entity.getId();

    if (LdapConfigurationView.getInstance().getIntegratedMode()) {
      Role originalObject = findOne(entity.getId());
      if (originalObject == null) {
        originalObject = entity;
      }
      // this.SyncToLDAP(entity, originalObject.getGlobalName(), originalObject.getOrganizationUnitId());
    }

    // 设置组织全路径
    entity.setFullPath(combineFullPath(entity.getName(), entity.getOrganizationUnitId()));

    // 设置唯一识别名称
    entity.setDistinguishedName(combineDistinguishedName(entity.getName(), entity.getOrganizationUnitId()));

    boolean isExist = provider.isExist(entity.getId());

    if (!isExist) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }
      provider.insert(entity);
    } else {
      provider.update(entity);
    }

    if (entity != null) {
      // 绑定新的关系
      if (!StringUtil.isNullOrEmpty(id)) {
        // 1.移除非默认成员关系
        MembershipManagement.getInstance().getRoleService().clearupRelation(id);
        // 2.设置新的关系
        for (Account item : entity.getMembers()) {
          MembershipManagement.getInstance().getRoleService().addRelation(item.getId(), id);
        }
      }
    }

    return entity;
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public void delete(String id) {
    provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link Role} 实例的详细信息
   */
  @Override
  public Role findOne(String id) {
    return provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @param globalName 角色的全局名称
   * @return 一个 {@link Role} 实例的详细信息
   */
  @Override
  public Role findOneByGlobalName(String globalName) {
    return provider.findOneByGlobalName(globalName);
  }

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId 组织标识
   * @param standardRoleId 标准角色标识
   * @return 一个 {@link Role} 实例的详细信息
   */
  @Override
  public Role findOneByCorporationIdAndStandardRoleId(String corporationId, String standardRoleId) {
    return provider.findOneByCorporationIdAndStandardRoleId(corporationId, standardRoleId);
  }

  /**
   * @param parentId 根据父级角色 查询最大编码的角色
   */
//  @Override
//  public Role findMaxCodeByParentId(String parentId) {
//    return provider.findMaxCodeByParentId(parentId);
//  }

  /**
   * @param organizationUnitId 所属组织结构查询最大编码的角色
   */
//  @Override
//  public Role findMaxCodeByOrganizationUnitId(String organizationUnitId) {
//    return provider.findMaxCodeByOrganizationUnitId(organizationUnitId);
//  }

  /**
   * 查询 分页 记录
   *
   * @return 所有 Role 实例的详细信息
   */
  @Override
  public List<Role> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 一个 OrganizationUnit 实例的详细信息
   */
  @Override
  public List<Role> findAllByParentId(String parentId) {
    return findAllByParentId(parentId, 0);
  }

  /**
   * 查询某个组织节点下的所有角色信息
   *
   * @param parentId 父节标识
   * @param depth 深入获取的层次，0 表示只获取本层次，-1 表示全部获取
   * @return 所有实例{@link OrganizationUnit}的详细信息
   */
  @Override
  public List<Role> findAllByParentId(String parentId, int depth) {
    // 结果列表
    ArrayList<Role> list = new ArrayList<Role>();

    //
    // 查找组织子部门的角色信息
    //

    List<Role> roles = provider.findAllByParentId(parentId);

    list.addAll(roles);

    if (depth == -1) {
      depth = Integer.MAX_VALUE;
    }

    if (!roles.isEmpty() && depth > 0) {
      for (Role role : roles) {
        list.addAll(findAllByParentId(role.getId(), (depth - 1)));
      }
    }

    return list;
  }

  /**
   * 查询某条记录
   *
   * @param accountId AccountInfo Id号
   * @return 一个 AccountInfo 实例的详细信息
   */
  @Override
  public List<Role> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * @param organizationUnitId 组织机构id 查询当前组织机构下 及子组织机构所有的角色
   */
//  @Override
//  public List<Role> findAllRolesByOrganization(String organizationUnitId) {
//    List<Role> result = new ArrayList<>();
//
//    List<Role> roles = provider.findAllByOrganizationUnitId(organizationUnitId);
//    if (roles != null && roles.size() > 0) {
//      result.addAll(roles);
//    }
//
//    List<OrganizationUnit> organizationInfoList =  MembershipManagement.getInstance().getOrganizationUnitService()
//      .getChildOrganizationByOrganizationUnitId(organizationUnitId);
//
//    if (organizationInfoList != null && organizationInfoList.size() > 0) {
//      for (int oInt = 0; oInt < organizationInfoList.size(); oInt++) {
//        OrganizationUnit organizationUnit = organizationInfoList.get(oInt);
//        List<Role> roleInfos = provider.findAllByOrganizationUnitId(organizationUnit.getId());
//        result.addAll(roleInfos);
//      }
//    }
//    return result;
//  }

  /**
   * 查询某个组织下的所有角色
   *
   * @param organizationId 组织标识
   * @return 一个 Role 实例的详细信息
   */
  @Override
  public List<Role> findAllByOrganizationUnitId(String organizationId) {
    return findAllByOrganizationUnitId(organizationId, 0);
  }

  /**
   * 查询某个组织节点下的所有角色信息
   *
   * @param organizationUnitId 组织标识
   * @param depth 深入获取的层次，0 表示只获取本层次，-1 表示全部获取
   * @return 所有实例 {@link Role} 的详细信息
   */
  @Override
  public List<Role> findAllByOrganizationUnitId(String organizationUnitId, int depth) {
    // 结果列表
    ArrayList<Role> list = new ArrayList<Role>();

    // -------------------------------------------------------
    // 查找组织子部门的角色信息
    // -------------------------------------------------------
    List<OrganizationUnit> organizationUnits = MembershipManagement.getInstance().getOrganizationUnitService()
      .findAllByParentId(organizationUnitId);

    // -------------------------------------------------------
    // 查找角色信息
    // -------------------------------------------------------

    list.addAll(provider.findAllByOrganizationUnitId(organizationUnitId));

    if (depth == -1) {
      depth = 9;
    }

    if (!organizationUnits.isEmpty() && depth > 0) {
      for (OrganizationUnit organizationUnit : organizationUnits) {
        list.addAll(findAllByOrganizationUnitId(organizationUnit.getId(), (depth - 1)));
      }
    }

    return list;
  }

  /**
   * 递归查询某个标准组织下面所有的角色
   *
   * @param standardOrganizationUnitId 组织标识
   * @return 所有{@link Role}实例的详细信息
   */
  public List<Role> findAllByStandardOrganizationUnitId(String standardOrganizationUnitId) {
    // 结果列表
    ArrayList<Role> list = new ArrayList<Role>();

    // 临时列表
    List<Role> temp = null;

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
   * @return 所有{@link Role}实例的详细信息
   */
  public List<Role> findAllByStandardRoleId(String standardRoleId) {
    return provider.findAllByStandardRoleId(standardRoleId);
  }

  /**
   * 递归查询某个组织下面相关的职位对应的角色信息
   *
   * @param organizationId 组织标识
   * @param jobId 职位标识
   * @return 一个 实例的详细信息
   */
  @Override
  public List<Role> findAllByOrganizationUnitIdAndJobId(String organizationId, String jobId) {
    return provider.findAllByOrganizationUnitIdAndJobId(organizationId, jobId);
  }
  ///#endregion

  ///#region 函数:FindAllByAssignedJobId(string assignedJobId)

  /**
   * 递归查询某个组织下面相关的岗位对应的角色信息
   *
   * @param assignedJobId 岗位标识
   * @return 一个{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findAllByAssignedJobId(String assignedJobId) {
    return provider.findAllByAssignedJobId(assignedJobId);
  }
  ///#endregion

  ///#region 函数:FindAllByCorporationIdAndProjectId(string corporationIds, string projectIds)

  /**
   * 递归查询某个公司下面所有的角色和某个项目下面所有的角色
   *
   * @param corporationIds 公司标识，多个以逗号隔开
   * @param projectIds 项目标识，多个以逗号隔开
   * @return 一个 {@link Role} 实例的详细信息
   */
  @Override
  public List<Role> findAllByCorporationIdAndProjectId(String corporationIds, String projectIds) {
    ArrayList<Role> list = new ArrayList<Role>();

    // 查询相关公司的所有角色
    list.addAll(findAllByCorporationIds(corporationIds));

    // 查询相关项目的所有角色
    list.addAll(findAllByProjectIds(projectIds));

    return list;
  }

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationIds 公司标识，多个以逗号隔开
   * @return 所有{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findAllByCorporationIds(String corporationIds) {
    ArrayList<Role> list = new ArrayList<Role>();

    // 查询公司的所有角色
    if (!StringUtil.isNullOrEmpty(corporationIds)) {
      /// String[] keys = corporationIds.split(new char[]{',', ';'}, StringSplitOptions.RemoveEmptyEntries);
      String[] keys = corporationIds.split(",|;");

      for (String key : keys) {
        list.addAll(findAllByCorporationId(key));
      }
    }

    return list;
  }

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationId 组织标识
   * @return 所有{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findAllByCorporationId(String corporationId) {
    // 结果列表
    ArrayList<Role> list = new ArrayList<Role>();

    //
    // 查找部门(公司下一级组织架构)
    //
    List<OrganizationUnit> organizationUnits = MembershipManagement.getInstance().getOrganizationUnitService()
      .findAllByParentId(corporationId);

    //
    // 查找角色信息
    //

    list.addAll(findAllByOrganizationUnitId(corporationId));

    for (OrganizationUnit organizationUnit : organizationUnits) {
      // 获取项目团队以外的只能部门
      if (organizationUnit.getName().indexOf(VIRTUAL_TEAM_NAME) == -1) {
        list.addAll(findAllByOrganizationUnitId(organizationUnit.getId(), -1));
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
   * @return 所有{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findAllByProjectIds(String projectIds) {
    ArrayList<Role> list = new ArrayList<Role>();

    // 查询公司的所有角色
    if (!StringUtil.isNullOrEmpty(projectIds)) {
      // String[] keys = projectIds.split(new char[]{',', ';'}, StringSplitOptions.RemoveEmptyEntries);
      String[] keys = projectIds.split(",|;");

      for (String key : keys) {
        list.addAll(findAllByProjectId(key));
      }
    }

    return list;
  }

  /**
   * 递归查询某个项目下面所有的角色
   *
   * @param projectId 组织标识
   * @return 一个 Role 实例的详细信息
   */
  @Override
  public List<Role> findAllByProjectId(String projectId) {
    // 项目团队的标识 和 项目标识 保存一致
    String organizationId = projectId;

    return findAllByOrganizationUnitId(organizationId, -1);
  }

  /**
   * 递归查询某个公司下面对应某标准角色相对应的角色
   *
   * @param corporationId 组织标识
   * @param standardRoleIds 标准角色标识
   * @return 所有<see                                                                                                                               cref                               =                                                                                                                               "
               *
               *       Role
               *       "                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Role> findAllByCorporationIdAndStandardRoleIds(String corporationId, String standardRoleIds) {
    return provider.findAllByCorporationIdAndStandardRoleIds(corporationId, standardRoleIds);
  }

  /**
   * 根据某个组织标识查询此组织上下级之间属于某一范围权重值的角色信息
   *
   * @param organizationId 组织标识
   * @param minPriority 最小权重值
   * @param maxPriority 最大权重值
   * @return 所有<see                                                                                                                               cref                               =                                                                                                                               "
               *
               *       Role
               *       "                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Role> findAllBetweenPriority(String organizationId, int minPriority, int maxPriority) {
    return provider.findAllBetweenPriority(organizationId, minPriority, maxPriority);
  }

  /**
   * 所有没有成员的角色信息
   *
   * @param length 条数, 0表示全部
   * @return 所有{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findAllWithoutMember(int length) {
    return provider.findAllWithoutMember(length, false);
  }

  /**
   * 所有没有成员的角色信息
   *
   * @param length 条数, 0表示全部
   * @param includeAllRole 包含全部角色
   * @return 所有<see                                                                                                                               cref                               =                                                                                                                               "
               *
               *       Role
               *       "                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Role> findAllWithoutMember(int length, boolean includeAllRole) {
    return provider.findAllWithoutMember(length, includeAllRole);
  }

  /**
   * 所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @return 所有{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findForwardLeadersByOrganizationUnitId(String organizationId) {
    return provider.findForwardLeadersByOrganizationUnitId(organizationId, 1);
  }

  /**
   * 所有正向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 所有<see                                                                                                                               cref                               =                                                                                                                               "
               *
               *       Role
               *       "                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Role> findForwardLeadersByOrganizationUnitId(String organizationId, int level) {
    return provider.findForwardLeadersByOrganizationUnitId(organizationId, level);
  }

  /**
   * 所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @return 所有{@link Role}实例的详细信息
   */
  @Override
  public List<Role> findBackwardLeadersByOrganizationUnitId(String organizationId) {
    return provider.findBackwardLeadersByOrganizationUnitId(organizationId, 1);
  }

  /**
   * 所有反向领导的角色信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 所有<see                                                                                                                               cref                               =                                                                                                                               "
               *
               *       Role
               *       "                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Role> findBackwardLeadersByOrganizationUnitId(String organizationId, int level) {
    return provider.findBackwardLeadersByOrganizationUnitId(organizationId, level);
  }
  ///#endregion

  ///#region 函数:FindStandardGeneralRolesByOrganizationUnitId(string organizationId, int standardGeneralRoleId)

  /**
   * 所有父级对象为标准通用角色标识【standardGeneralRoleId】的相关角色信息
   *
   * @param organizationId 组织标识
   * @param standardGeneralRoleId 标准通用角色标识
   * @return 所有<see                                                                                                                               cref                               =                                                                                                                               "
               *
               *       Role
               *       "                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Role> findStandardGeneralRolesByOrganizationUnitId(String organizationId,
    String standardGeneralRoleId) {
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
   * @return 一个列表实例{@link Role}
   */
  // public  List<Role> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param name 组织单位名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  /**
   * @param name 角色名称
   * @param organizationUnitId 标准角色
   */
  @Override
  public boolean isExistName(String name, String organizationUnitId) {
    return provider.isExistNameByStandard(name,organizationUnitId);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param globalName 角色全局名称
   * @return 布尔值
   */
  @Override
  public boolean isExistGlobalName(String globalName) {
    return provider.isExistGlobalName(globalName);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 角色标识
   * @param name 角色名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  @Override
  public int rename(String id, String name) {
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
  @Override
  public Role getEveryone() {
    Role everyone = new RoleInfo();

    everyone.setId(UUIDUtil.emptyString("D"));
    everyone.setName("所有人");

    return everyone;
  }

  /**
   * 角色全路径
   *
   * @param name 角色名称
   * @param organizationUnitId 所属组织标识
   */
  @Override
  public String combineFullPath(String name, String organizationUnitId) {
    String path = MembershipManagement.getInstance().getOrganizationUnitService()
      .getOrganizationPathByOrganizationUnitId(organizationUnitId);
    return String.format("%1$s%2$s", path, name);
  }

  /**
   * 角色唯一名称
   *
   * @param name 角色名称
   * @param organizationUnitId 所属组织标识
   */
  @Override
  public String combineDistinguishedName(String name, String organizationUnitId) {
    String path = MembershipManagement.getInstance().getOrganizationUnitService()
      .getLdapOuPathByOrganizationUnitId(organizationUnitId);
    return String.format("cn=%1$s,%2$s%3$s", name, path,
      LdapConfigurationView.getInstance().getSuffixDistinguishedName());
  }

  /**
   * 设置全局名称
   *
   * @param id 帐户标识
   * @param globalName 全局名称
   * @return 修改成功,  0, 修改失败,  1.
   */
  @Override
  public int setGlobalName(String id, String globalName) {
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
      Role originalObject = findOne(id);

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
   * @param id 角色标识
   * @param parentId 父级角色标识
   * @return 0:代表成功
   */
  @Override
  public int setParentId(String id, String parentId) {
    return provider.setParentId(id, parentId);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param id 角色标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public int setEnableEmail(String id, int status) {
    return provider.setEnableEmail(id, status);
  }

  /**
   * 获取角色的权限
   *
   * @param roleId 角色标识
   */
  @Override
  public List<Authority> getAuthorities(String roleId) {
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
  public  DataTable GenerateStandardRoleMappingReport(String organizationId, String standardRoleType) {
    List<IStandardRole> list = MembershipManagement.Instance.StandardRoleService.FindAllByType(Integer.parseInt(standardRoleType));

    String standardRoleIds = null;

    for (IStandardRole item : list) {
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
  public  DataTable GenerateStandardRoleMappingReport(String organizationId, String standardRoleType, String standardRoleIds) {
    DataTable table = new DataTable();

    table.Columns.Add("standardRoleId");
    table.Columns.Add("roleId");
    table.Columns.Add("roleName");
    table.Columns.Add("roleIsCreatedValue");

    List<Role> list = null;

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
        list = new ArrayList<Role>();
        break;
    }

    int count = 0;

    for (int i = 0; i < keys.length; i++) {
      for (Role item : list) {
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
   * @param organizationId 公司或项目标识
   * @param standardRoleId 标准角色标识
   * @param roleName 角色名称
   */
  @Override
  public int quickCreateRole(String standardRoleType, String organizationId, String standardRoleId, String roleName) {
    if (MembershipManagement.getInstance().getRoleService().isExistName(roleName)) {
      return 1;
    }
    /*
    IStandardRole standardRole = MembershipManagement.Instance.StandardRoleService[standardRoleId];

    if (standardRole == null) {
      return 2;
    } else {
      Role param = new Role();

      param.setId(StringUtil.toUuid());
      param.Name = roleName;
      param.OrganizationUnitId = organizationId;
      param.ParentId = UUIDUtil.emptyString();
      param.setStandardRoleId(standardRoleId);
      param.setStatus(1);

      List<OrganizationUnit> organizations = null;

      List<Role> roles = null;

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

      for (OrganizationUnit organization : organizations) {
        // 根据标准组织找上级组织
        if (standardRole.StandardOrganizationUnitId == organization.StandardOrganizationUnitId) {
          param.OrganizationUnitId = organization.Id;
        }
      }

      for (Role role : roles) {
        // 根据标准角色找上级角色
        if (standardRole.ParentId == role.StandardRoleId) {
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
   * @param projectId 项目标识
   * @param standardRoleId 标准角色标识
   * @param roleName 角色名称
   */
  public int CreateRoleWithProjectAndStandardRole(String projectId, String standardRoleId, String roleName) {
    if (MembershipManagement.getInstance().getRoleService().isExistName(roleName)) {
      return 1;
    }
    /*
    IStandardRole standardRole = MembershipManagement.Instance.StandardRoleService[standardRoleId];

    if (standardRole == null) {
      return 2;
    } else {
      Role param = new Role();

      param.Id = StringHelper.ToGuid();
      param.Name = roleName;
      param.GlobalName = roleName;
      param.OrganizationUnitId = projectId;
      param.ParentId = StringHelper.ToGuid(UUID.Empty);
      param.StandardRoleId = standardRoleId;
      param.Status = 1;

      List<OrganizationUnit> organizations = MembershipManagement.Instance.OrganizationUnitService.FindAllByProjectId(projectId);

      for (OrganizationUnit organization : organizations) {
        // 根据标准组织找上级组织
        if (standardRole.StandardOrganizationUnitId == organization.StandardOrganizationUnitId) {
          param.OrganizationUnitId = organization.Id;
        }
      }

      List<Role> roles = MembershipManagement.Instance.RoleService.FindAllByProjectId(projectId);

      for (Role role : roles) {
        // 根据标准角色找上级角色
        if (standardRole.ParentId == role.StandardRoleId) {
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
  public  DataTable SetProjectRoleMapping(String fromProjectId, String toProjectId) {
    DataTable table = new DataTable();

    table.Columns.Add("fromProjectOrganizationUnitId");
    table.Columns.Add("fromProjectRoleId");
    table.Columns.Add("fromProjectRoleName");
    table.Columns.Add("fromProjectRoleAccountValue");
    table.Columns.Add("fromProjectRoleStandardRoleId");
    table.Columns.Add("toProjectRoleId");
    table.Columns.Add("toProjectRoleName");
    table.Columns.Add("toProjectRoleAccountValue");

    List<Role> fromProjectRoles = findAllByProjectId(fromProjectId);

    List<Role> toProjectRoles = findAllByProjectId(toProjectId);

    // 添加来源项目角色数据
    // 到fromProjectOrganizationUnitId，fromProjectRoleId，fromProjectRoleName，fromProjectRoleAccountValue。
    for (Role role : fromProjectRoles) {
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
    for (Role role : toProjectRoles) {
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
  public String SetProjectRoleMappingAccountValue(String roleId) {
    StringBuilder outString = new StringBuilder();

    List<Account> list = MembershipManagement.getInstance().getAccountService().findAllByRoleId(roleId);

    for (Account item : list) {
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
   * @param endDate 结束时间
   */
  public String CreatePackage(Date beginDate, Date endDate) {
    StringBuilder outString = new StringBuilder();

    // String whereClause = String.format(" ModifiedDate BETWEEN ##%1$s## AND ##%2$s## ", beginDate, endDate);

    // List<Role> list = MembershipManagement.getInstance().getRoleService().findAllBetweenModifiedDate(beginDate, endDate);
    List<Role> list = null;

    outString
      .append(String.format("<package packageType=\"role\" beginDate=\"%1$s\" endDate=\"%2$s\">", beginDate, endDate));

    outString.append("<roles>");

    for (Role item : list) {
      outString.append(((Role) item).serializable());
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
  @Override
  public int syncToLDAP(Role param) {
    return syncToLDAP(param, param.getName(), param.getOrganizationUnitId());
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 角色信息
   * @param originalGlobalName 原始的全局名称
   * @param originalOrganizationUnitId 原始的所属组织标识
   */
  public int syncToLDAP(Role param, String originalGlobalName, String originalOrganizationUnitId) {
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
  // public  int SyncFromPackPage(Role param) {
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
  @Override
  public List<AccountRoleRelation> findAllRelationByAccountId(String accountId) {
    return provider.findAllRelationByAccountId(accountId);
  }

  /**
   * 根据角色查询相关帐号的关系
   *
   * @param roleId 角色标识
   * @return Table Columns：AccountId, RoleId, isDefault, BeginDate, EndDate
   */
  @Override
  public List<AccountRoleRelation> findAllRelationByRoleId(String roleId) {
    return provider.findAllRelationByRoleId(roleId);
  }

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   */
  @Override
  public int addRelation(String accountId, String roleId) {
    ZonedDateTime zoneDateTime = ZonedDateTime.of(DateUtil.getMaxLocalDateTime(), ZoneId.systemDefault());
    Date maxLocalDateDate = Date.from(zoneDateTime.toInstant()); // 填补最大时间
    return addRelation(accountId, roleId, false, new Date(), maxLocalDateDate);
  }

  /**
   * 添加帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   * @param isDefault 是否是默认角色
   * @param beginDate 启用时间
   * @param endDate 停用时间
   */
  @Override
  public int addRelation(String accountId, String roleId, boolean isDefault, Date beginDate, Date endDate) {
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
      Account account = MembershipManagement.Instance.AccountService[accountId];

      Role role = MembershipManagement.Instance.RoleService[roleId];

      // 帐号对象、帐号的全局名称、帐号的登录名、角色对象、角色的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && role != null && !StringUtil.isNullOrEmpty(role.GlobalName)) {
        LDAPManagement.Instance.Group.AddRelation(account.LoginName, LDAPSchemaClassType.User, role.GlobalName);
      }
    }
    */

    return provider.addRelation(accountId, roleId, isDefault, beginDate, endDate);
  }

  /**
   * 添加帐号与相关角色的关系(以角色为主)
   *
   * @param accountIds 帐号标识，多个以逗号隔开
   * @param roleId 角色标识
   */
  @Override
  public int addRelationRange(String accountIds, String roleId) {
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
   * @param roleId 角色标识
   * @param endDate 新的截止时间
   */
  @Override
  public int extendRelation(String accountId, String roleId, Date endDate) {
    return provider.extendRelation(accountId, roleId, endDate);
  }
  ///#endregion

  ///#region 函数:RemoveRelation(string accountId, string roleId)

  /**
   * 移除帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   */
  @Override
  public int removeRelation(String accountId, String roleId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      Account account = MembershipManagement.Instance.AccountService[accountId];

      Role role = MembershipManagement.Instance.RoleService[roleId];

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
  @Override
  public int removeDefaultRelation(String accountId) {
    return provider.removeDefaultRelation(accountId);
  }

  /**
   * 移除帐号相关角色的非默认关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeNondefaultRelation(String accountId) {
    return provider.removeNondefaultRelation(accountId);
  }

  /**
   * 移除帐号已过期的角色关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeExpiredRelation(String accountId) {
    return provider.removeExpiredRelation(accountId);
  }

  /**
   * 移除帐号相关角色的所有关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeAllRelation(String accountId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<AccountRoleRelation> list = findAllRelationByAccountId(accountId);

      for (AccountRoleRelation item : list) {
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
  @Override
  public boolean hasDefaultRelation(String accountId) {
    return provider.hasDefaultRelation(accountId);
  }

  @Override
  public boolean hasRelation(String accountId, String roleId) {
    return provider.hasRelation(accountId, roleId);
  }

  /**
   * 设置帐号的默认组织
   *
   * @param accountId 帐号标识
   * @param roleId 角色标识
   */
  @Override
  public int setDefaultRelation(String accountId, String roleId) {

    if (LdapConfigurationView.getInstance().getIntegratedMode()) {
      Account account = MembershipManagement.getInstance().getAccountService().findOne(accountId);

      Role role = MembershipManagement.getInstance().getRoleService().findOne(roleId);

      // if (account != null && role != null) {
      //  LdapManagement.getInstance().getGroup().addRelation(account.getGlobalName(), LdapDAPSchemaClassType.User, role.getName());
      // }
    }

    return provider.setDefaultRelation(accountId, roleId);
  }

  /**
   * 清理角色与帐号的关系
   *
   * @param roleId 角色标识
   */
  @Override
  public int clearupRelation(String roleId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<AccountRoleRelation> list = findAllRelationByRoleId(roleId);

      for (AccountRoleRelation item : list) {
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
