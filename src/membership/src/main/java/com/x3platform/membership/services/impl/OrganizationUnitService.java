package com.x3platform.membership.services.impl;

import com.x3platform.IAuthorizationObject;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.IAccountOrganizationUnitRelationInfo;
import com.x3platform.membership.IOrganizationUnitInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.mappers.OrganizationUnitMapper;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.IOrganizationUnitService;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 */
@Service
public class OrganizationUnitService implements IOrganizationUnitService {

  @Autowired
  private OrganizationUnitMapper provider = null;

  private HashMap<String, IOrganizationUnitInfo> dictionary = new HashMap<String, IOrganizationUnitInfo>();

  ///#region 构造函数:OrganizationUnitService()

  /**
   * 构造函数
   */
  // public OrganizationUnitService() {
  //  this.configuration = MembershipConfigurationView.Instance.Configuration;

  // 创建对象构建器(Spring.NET)
  //  String springObjectFile = this.configuration.keySet()["SpringObjectFile"].Value;

  //  SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(MembershipConfiguration.ApplicationName, springObjectFile);

  // 创建数据提供器
  // this.provider = objectBuilder.<IOrganizationUnitProvider>GetObject(IOrganizationUnitProvider.class);
  //}

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  ///#region 属性:Save(IOrganizationInfo param)

  /**
   * 保存记录
   *
   * @param param IOrganizationInfo 实例详细信息
   * @return IOrganizationInfo 实例详细信息
   */
  public final IOrganizationUnitInfo save(IOrganizationUnitInfo param) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
    {
      IOrganizationUnitInfo originalObject = findOne(param.Id);

      if (originalObject == null)
      {
        originalObject = param;
      }

      SyncToLDAP(param, originalObject.Name, originalObject.GlobalName, originalObject.ParentId);
    }
    */

    // 设置组织全路径
    param.setFullPath(this.combineFullPath(param.getName(), param.getParentId()));

    // 设置唯一识别名称
    param.setDistinguishedName(this.combineDistinguishedName(param.getGlobalName(), param.getId()));

    return this.provider.save(param);
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
   * @return 返回一个 IOrganizationInfo 实例的详细信息
   */
  public final IOrganizationUnitInfo findOne(String id) {
    IOrganizationUnitInfo param = null;

    if (this.dictionary.containsKey(id)) {
      param = dictionary.get(id);
    }

    if (param == null) {
      param = this.provider.findOne(id);

      if (param != null) {
        dictionary.put(id, param);
      }
    }

    return this.provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @param globalName 组织的全局名称
   * @return 返回一个<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final IOrganizationUnitInfo findOneByGlobalName(String globalName) {
    return this.provider.findOneByGlobalName(globalName);
  }

  /**
   * 查询某个角色所属的组织信息
   *
   * @param roleId 角色标识
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final IOrganizationUnitInfo findOneByRoleId(String roleId) {
    return this.provider.findOneByRoleId(roleId);
  }

  /**
   * 查询某个角色所属的某一级次的组织信息
   *
   * @param roleId 角色标识
   * @param level  层次
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final IOrganizationUnitInfo findOneByRoleId(String roleId, int level) {
    return this.provider.findOneByRoleId(roleId, level);
  }

  /**
   * 查询某个组织所属的公司信息
   *
   * @param id 组织标识
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final IOrganizationUnitInfo findCorporationByOrganizationUnitId(String id) {
    return this.provider.findCorporationByOrganizationUnitId(id);
  }
  ///#endregion

  ///#region 函数:FindDepartmentByOrganizationUnitId(string organizationId, int level)

  /**
   * 查询某个组织的所属某个上级部门信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final IOrganizationUnitInfo findDepartmentByOrganizationUnitId(String organizationId, int level) {
    return this.provider.findDepartmentByOrganizationUnitId(organizationId, level);
  }
  ///#endregion

  ///#region 函数:FindAll()

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 IOrganizationInfo 实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAll() {
    return this.provider.findAll("", 0);
  }
  ///#endregion

  ///#region 函数:FindAll(string whereClause)

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有 IOrganizationInfo 实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAll(String whereClause) {
    return this.provider.findAll(whereClause, 0);
  }
  ///#endregion

  ///#region 函数:FindAll(string whereClause,int length)

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有 IOrganizationInfo 实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAll(String whereClause, int length) {
    return this.provider.findAll(whereClause, length);
  }
  ///#endregion

  ///#region 函数:FindAllByParentId(string parentId)

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 返回所有实例<see cref="IOrganizationInfo"/>的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByParentId(String parentId) {
    return findAllByParentId(parentId, 0);
  }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @param depth    深入获取的层次，0表示只获取本层次，-1表示全部获取
   * @return 返回所有实例<see cref="IOrganizationInfo"/>的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByParentId(String parentId, int depth) {
    // 结果列表
    ArrayList<IOrganizationUnitInfo> list = new ArrayList<IOrganizationUnitInfo>();

    //
    // 查找组织子部门信息
    //

    List<IOrganizationUnitInfo> organizations = this.provider.findAllByParentId(parentId);

    list.addAll(organizations);

    if (depth == -1) {
      depth = Integer.MAX_VALUE;
    }

    if (!organizations.isEmpty() && depth > 0) {
      for (IOrganizationUnitInfo organization : organizations) {
        list.addAll(findAllByParentId(organization.getId(), (depth - 1)));
      }
    }

    return list;
  }
  ///#endregion

  ///#region 函数:FindAllByAccountId(string accountId)

  /**
   * 查询某条记录
   *
   * @param accountId 帐号标识
   * @return 返回一个 IOrganizationInfo 实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByAccountId(String accountId) {
    // 过滤 非法的内容信息
    if (StringUtil.isNullOrEmpty(accountId) || accountId.equals("0")) {
      return new ArrayList<IOrganizationUnitInfo>();
    }

    return this.provider.findAllByAccountId(accountId);
  }

  /**
   * 查询某个角色的所属相关组织
   *
   * @param roleIds 角色标识
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByRoleIds(String roleIds) {
    List<IOrganizationUnitInfo> list = new ArrayList<IOrganizationUnitInfo>();

    String[] ids = roleIds.split(",");

    for (String id : ids) {
      IOrganizationUnitInfo organization = findOneByRoleId(id);

      if (organization != null) {
        list.add(organization);
      }
    }

    return list;
  }

  /**
   * 查询某个角色的所属相关组织
   *
   * @param roleIds 角色标识
   * @param level   层次
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByRoleIds(String roleIds, int level) {
    List<IOrganizationUnitInfo> list = new ArrayList<IOrganizationUnitInfo>();

    String[] ids = roleIds.split(",");

    for (String id : ids) {
      IOrganizationUnitInfo organization = findOneByRoleId(id, level);

      if (organization != null) {
        list.add(organization);
      }
    }

    return list;
  }

  /**
   * 递归查询某个公司下面所有的角色
   *
   * @param corporationId 组织标识
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByCorporationId(String corporationId) {
    // 结果列表
    ArrayList<IOrganizationUnitInfo> list = new ArrayList<IOrganizationUnitInfo>();

    // 查找部门(公司下一级组织架构)
    List<IOrganizationUnitInfo> organizations = MembershipManagement.getInstance().getOrganizationUnitService().findAllByParentId(corporationId);

    for (IOrganizationUnitInfo organization : organizations) {
      list.add(organization);

      // 获取项目团队以外的只能部门
      if (organization.getName().indexOf("项目团队") == -1) {
        list.addAll(findAllByParentId(organization.getId(), -1));
      }
    }

    list.add(findOne(corporationId));

    return list;
  }

  /**
   * 递归查询某个项目下面所有的角色
   *
   * @param projectId 组织标识
   * @return 返回一个<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findAllByProjectId(String projectId) {
    //
    // 项目团队的标识 和 项目标识 保存一致
    //

    String organizationId = projectId;

    List<IOrganizationUnitInfo> list = findAllByParentId(organizationId, 1);

    list.add(findOne(organizationId));

    return list;
  }

  /**
   * 查询某个帐户所属的所有公司信息
   *
   * @param accountId 帐号标识
   * @return 返回所有<see cref="IOrganizationInfo"/>实例的详细信息
   */
  public final List<IOrganizationUnitInfo> findCorporationsByAccountId(String accountId) {
    List<IOrganizationUnitInfo> corporations = new ArrayList<IOrganizationUnitInfo>();

    // TODO 待处理
    /*
    IMemberInfo member = MembershipManagement.getInstance().getMemberService().findOne(accountId);

    if (member != null && member.Corporation != null) {
      corporations.add((OrganizationUnitInfo) member.Corporation);

      List<IOrganizationUnitInfo> list = this.provider.findCorporationsByAccountId(accountId);

      for (OrganizationUnitInfo item : list) {
        if (item.getId() != member.getCorporation().Id) {
          corporations.add(item);
        }
      }
    }
    */
    return corporations;
  }

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
   * @return 返回一个列表
   */
  // public final List<IOrganizationUnitInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 检测是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  public final boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param name 组织单位名称
   * @return 布尔值
   */
  public final boolean isExistName(String name) {
    return this.provider.isExistName(name);
  }
  ///#endregion

  ///#region 函数:IsExistGlobalName(string globalName)

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  public final boolean isExistGlobalName(String globalName) {
    return this.provider.isExistGlobalName(globalName);
  }
  ///#endregion

  ///#region 函数:Rename(string id, string name)

  /**
   * 检测是否存在相关的记录
   *
   * @param id   组织标识
   * @param name 组织名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  public final int rename(String id, String name) {
    // 检测是否存在对象
    if (!isExist(id)) {
      // 不存在对象
      return 1;
    }

    return this.provider.rename(id, name);
  }
  ///#endregion

  ///#region 函数:CombineFullPath(string name, string parentId)

  /**
   * 组合全路径
   *
   * @param name     组织名称
   * @param parentId 父级对象标识
   * @return
   */
  public final String combineFullPath(String name, String parentId) {
    String path = getOrganizationPathByOrganizationUnitId(parentId);

    return String.format("%1$s%2$s", path, name);
  }

  /**
   * 根据组织标识计算组织的全路径
   *
   * @param organizationId 组织标识
   * @return
   */
  public final String getOrganizationPathByOrganizationUnitId(String organizationId) {
    String path = formatOrganizationPath(organizationId);

    return String.format("%1$s\\", path);
  }

  /**
   * 格式化组织路径
   *
   * @param id
   * @return
   */
  private String formatOrganizationPath(String id) {
    String path = "";

    String parentId = "";

    String name = null;

    IOrganizationUnitInfo param = findOne(id);

    if (param == null) {
      return "";
    } else {
      if (!StringUtil.isNullOrEmpty(param.getParentId())) {
        parentId = param.getParentId();
      }

      name = param.getName();

      if (!StringUtil.isNullOrEmpty(name)) {
        if (!StringUtil.isNullOrEmpty(parentId) && !UUIDUtil.emptyString().equals(parentId)) {
          path = formatOrganizationPath(parentId);
        }

        path = StringUtil.isNullOrEmpty(path) ? name : String.format("%1$s\\%2$s", path, name);

        return path;
      }
    }

    return "";
  }

  /**
   * 组合唯一名称
   *
   * @param globalName 组织全局名称
   * @param id         对象标识
   * @return
   */
  public final String combineDistinguishedName(String globalName, String id) {
    String path = this.getLDAPOUPathByOrganizationUnitId(id);

    // TODO 待处理
    // return String.format("CN=%1$s,%2$s%3$s", globalName, path, LDAPConfigurationView.Instance.SuffixDistinguishedName);

    return String.format("CN=%1$s,%2$s%3$s", globalName, path, "");
  }

  /**
   * 根据组织标识计算 Active Directory OU 路径
   *
   * @param organizationId 组织标识
   * @return
   */
  public final String getLDAPOUPathByOrganizationUnitId(String organizationId) {
    return formatLDAPPath(organizationId);
  }

  /**
   * 格式化 Active Directory 路径
   *
   * @param id
   * @return
   */
  private String formatLDAPPath(String id) {
    String path = "";

    String parentId = "";

    // OU的名称
    String name = null;

    IOrganizationUnitInfo param = findOne(id);

    if (param == null) {
      return "";
    } else {
      name = param.getName();

      // 组织结构的根节点OU特殊处理 默认为组织结构
      if (id.equals("00000000-0000-0000-0000-000000000001")) {
        // TODO 待处理
        // name = LDAPConfigurationView.Instance.CorporationOrganizationUnitFolderRoot;
      }

      // 1.名称不能为空 2.父级对象标识不能为空
      if (!StringUtil.isNullOrEmpty(name) && !StringUtil.isNullOrEmpty(param.getParentId()) && !UUIDUtil.emptyString().equals(param.getParentId())) {
        parentId = param.getParentId();

        path = formatLDAPPath(parentId);

        path = StringUtil.isNullOrEmpty(path) ? String.format("OU=%1$s", name) : String.format("OU=%1$s", name) + "," + path;

        return path;
      }

      return String.format("OU=%1$s", name);
    }
  }

  /**
   * 设置全局名称
   *
   * @param id         帐户标识
   * @param globalName 全局名称
   * @return 0 操作成功 | 1 操作失败
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
      IOrganizationUnitInfo originalObject = findOne(id);

      if (originalObject != null) {
        // 由于外部系统直接同步到人员及权限管理的数据库中，
        // 所以 Active Directory 上不会直接创建相关对象，需要手工设置全局名称并创建相关对象。
        if (!StringUtil.isNullOrEmpty(originalObject.GlobalName) && LDAPManagement.Instance.Group.IsExistName(originalObject.GlobalName)) {
          LDAPManagement.Instance.Group.Rename(originalObject.GlobalName, globalName);
        } else {
          LDAPManagement.Instance.OrganizationUnit.Add(originalObject.Name, MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(originalObject.ParentId));

          LDAPManagement.Instance.Group.Add(globalName, MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(originalObject.Id));
        }
      }
    }
    */
    return this.provider.setGlobalName(id, globalName);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param id       组织标识
   * @param parentId 父级组织标识
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setParentId(String id, String parentId) {
    return this.provider.setGlobalName(id, parentId);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param id     组织标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  public final int setEmailStatus(String id, int status) {
    return this.provider.setEmailStatus(id, status);
  }

  /**
   * 获取组织的子成员
   *
   * @param organizationId 组织单位标识
   */
  public final List<IAuthorizationObject> getChildNodes(String organizationId) {
    List<IAuthorizationObject> list = new ArrayList<IAuthorizationObject>();

    List<IOrganizationUnitInfo> listA = this.findAllByParentId(organizationId);

    List<IAccountInfo> listB = MembershipManagement.getInstance().getAccountService().findAllByOrganizationUnitId(organizationId);

    for (IAuthorizationObject item : listA) {
      list.add(item);
    }

    for (IAuthorizationObject item : listB) {
      list.add(item);
    }

    return list;
  }

  /**
   * 创建数据包
   *
   * @param beginDate 开始时间
   * @param endDate   结束时间
   */
  public final String CreatePackage(java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate) {

    StringBuilder outString = new StringBuilder();

    List<IOrganizationUnitInfo> list = null;

    // TODO 待处理
    // String whereClause = String.format(" ModifiedDate BETWEEN ##%1$s## AND ##%2$s## ", beginDate, endDate);

    // List<IOrganizationUnitInfo> list = MembershipManagement.getInstance().getOrganizationUnitService().findAll(whereClause);

    outString.append(String.format("<package packageType=\"organization\" beginDate=\"%1$s\" endDate=\"%2$s\">", beginDate, endDate));

    outString.append("<organizations>");

    for (IOrganizationUnitInfo item : list) {
      outString.append(((OrganizationUnitInfo) item).serializable());
    }

    outString.append("</organizations>");

    outString.append("</package>");

    return outString.toString();
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 组织信息
   */
  public final int syncToLDAP(IOrganizationUnitInfo param) {
    return syncToLDAP(param, param.getName(), param.getGlobalName(), param.getParentId());
  }

  /**
   * 同步信息
   *
   * @param param              组织信息
   * @param originalName       原始名称
   * @param originalGlobalName 原始全局名称
   * @param originalParentId   原始父级标识
   */
  public final int syncToLDAP(IOrganizationUnitInfo param, String originalName, String originalGlobalName, String originalParentId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      if (StringUtil.isNullOrEmpty(param.GlobalName)) {
        // 组织【${FullPath}】全局名称为空，请配置相关信息。
        return 1;
      } else if (StringUtil.isNullOrEmpty(param.Name)) {
        // 组织【${FullPath}】名称为空，请配置相关信息。
        return 2;
      } else {
        // 1.原始的全局名称不为空。
        // 2.Active Directory 上有相关对象。
        if (!StringUtil.isNullOrEmpty(originalGlobalName) && LDAPManagement.Instance.Group.IsExistName(originalGlobalName)) {
          if (!originalName.equals(param.Name)) {
            // 组织【${GlobalName}】的名称发生改变。
            LDAPManagement.Instance.OrganizationUnit.Rename(originalName, MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(originalParentId), param.Name);
          }

          if (!originalGlobalName.equals(param.GlobalName)) {
            // 组织【${GlobalName}】的全局名称发生改变。
            LDAPManagement.Instance.Group.Rename(originalGlobalName, param.GlobalName);
          }

          if (!originalParentId.equals(param.ParentId)) {
            // 组织【${GlobalName}】的父级节点发生改变。
            LDAPManagement.Instance.OrganizationUnit.MoveTo(this.GetLDAPOUPathByOrganizationUnitId(param.Id), this.GetLDAPOUPathByOrganizationUnitId(param.ParentId));
          }

          return 0;
        } else {
          LDAPManagement.Instance.OrganizationUnit.Add(param.Name, this.GetLDAPOUPathByOrganizationUnitId(param.ParentId));

          LDAPManagement.Instance.Group.Add(param.GlobalName, this.GetLDAPOUPathByOrganizationUnitId(param.Id));

          // 组织【${GlobalName}】创建成功。
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
   * @param param 组织信息
   */
  // public final int syncFromPackPage(IOrganizationUnitInfo param) {
  //  return this.provider.SyncFromPackPage(param);
  // }

  // -------------------------------------------------------
  // 设置帐号和组织关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关帐号的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, OrganizationId, IsDefault, BeginDate, EndDate
   */
  public final List<IAccountOrganizationUnitRelationInfo> findAllRelationByAccountId(String accountId) {
    return this.provider.findAllRelationByAccountId(accountId);
  }

  /**
   * 根据组织查询相关帐号的关系
   *
   * @param organizationId 组织标识
   * @return Table Columns：AccountId, OrganizationId, IsDefault, BeginDate, EndDate
   */
  public final List<IAccountOrganizationUnitRelationInfo> findAllRelationByRoleId(String organizationId) {
    return this.provider.findAllRelationByRoleId(organizationId);
  }

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  public final int addRelation(String accountId, String organizationId) {
    return addRelation(accountId, organizationId, false, java.time.LocalDateTime.now(), java.time.LocalDateTime.MAX);
  }

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   * @param isDefault      是否是默认组织
   * @param beginDate      启用时间
   * @param endDate        停用时间
   */
  public final int addRelation(String accountId, String organizationId, boolean isDefault, java.time.LocalDateTime beginDate, java.time.LocalDateTime endDate) {
    if (StringUtil.isNullOrEmpty(accountId)) {
      // 帐号标识不能为空
      return 1;
    }

    if (StringUtil.isNullOrEmpty(organizationId)) {
      // 组织标识不能为空
      return 2;
    }
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo account = MembershipManagement.Instance.AccountService[accountId];

      IOrganizationUnitInfo organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      // 帐号对象、帐号的全局名称、帐号的登录名、组织对象、组织的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && organization != null && !StringUtil.isNullOrEmpty(organization.GlobalName)) {
        LDAPManagement.Instance.Group.AddRelation(account.LoginName, LDAPSchemaClassType.User, organization.GlobalName);
      }
    }
    */
    return this.provider.addRelation(accountId, organizationId, isDefault, beginDate, endDate);
  }

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountIds     帐号标识，多个以逗号隔开
   * @param organizationId 组织标识
   */
  public final int addRelationRange(String accountIds, String organizationId) {
    String[] list = accountIds.split(",", -1);

    for (String accountId : list) {
      addRelation(accountId, organizationId);
    }

    return 0;
  }

  /**
   * 添加帐号与相关组织的父级组织关系(递归)
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  public final int addParentRelations(String accountId, String organizationId) {
    IOrganizationUnitInfo organization = MembershipManagement.getInstance().getOrganizationUnitService().findOne(organizationId);

    // [容错]如果角色信息为空，中止相关组织设置
    if (organization != null && !StringUtil.isNullOrEmpty(organization.getParentId()) && organization.getParent() != null) {
      // 添加父级对象关系
      addRelation(accountId, organization.getParentId());

      // 递归查找父级对象关系
      addParentRelations(accountId, organization.getParentId());
    }

    return 0;
  }

  /**
   * 续约帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   * @param endDate        新的截止时间
   */
  public final int extendRelation(String accountId, String organizationId, java.time.LocalDateTime endDate) {
    return this.provider.extendRelation(accountId, organizationId, endDate);
  }

  /**
   * 移除帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  public final int removeRelation(String accountId, String organizationId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo account = MembershipManagement.Instance.AccountService[accountId];

      IOrganizationUnitInfo organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      // 帐号对象、帐号的全局名称、帐号的登录名、组织对象、组织的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && organization != null && !StringUtil.isNullOrEmpty(organization.GlobalName)) {
        LDAPManagement.Instance.Group.RemoveRelation(account.LoginName, LDAPSchemaClassType.User, organization.GlobalName);
      }
    }
    */

    return this.provider.removeRelation(accountId, organizationId);
  }

  /**
   * 移除帐号相关组织的默认关系
   *
   * @param accountId 帐号标识
   */
  public final int removeDefaultRelation(String accountId) {
    return this.provider.removeDefaultRelation(accountId);
  }

  /**
   * 移除帐号相关组织的非默认关系
   *
   * @param accountId 帐号标识
   */
  public final int removeNondefaultRelation(String accountId) {
    return this.provider.removeNondefaultRelation(accountId);
  }

  /**
   * 移除帐号已过期的组织关系
   *
   * @param accountId 帐号标识
   */
  public final int removeExpiredRelation(String accountId) {
    return this.provider.removeExpiredRelation(accountId);
  }

  /**
   * 移除帐号相关组织的所有关系
   *
   * @param accountId 帐号标识
   */
  public final int removeAllRelation(String accountId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<IAccountOrganizationUnitRelationInfo> list = findAllRelationByAccountId(accountId);

      for (IAccountOrganizationUnitRelationInfo item : list) {
        removeRelation(item.AccountId, item.OrganizationUnitId);
      }

      return 0;
    } else {
      return this.provider.removeAllRelation(accountId);
    }
    */
    return this.provider.removeAllRelation(accountId);
  }

  /**
   * 检测帐号的默认组织
   *
   * @param accountId 帐号标识
   */
  public final boolean hasDefaultRelation(String accountId) {
    return this.provider.hasDefaultRelation(accountId);
  }

  /**
   * 设置帐号的默认组织
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  public final int setDefaultRelation(String accountId, String organizationId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo account = MembershipManagement.Instance.AccountService[accountId];

      IOrganizationUnitInfo organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      if (account != null && organization != null) {
        LDAPManagement.Instance.Group.AddRelation(account.GlobalName, LDAPSchemaClassType.User, organization.Name);
      }
    }
    */
    return this.provider.setDefaultRelation(accountId, organizationId);
  }

  /**
   * 清理组织与帐号的关系
   *
   * @param organizationId 组织标识
   */
  public final int clearupRelation(String organizationId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<IAccountOrganizationUnitRelationInfo> list = findAllRelationByRoleId(organizationId);

      for (IAccountOrganizationUnitRelationInfo item : list) {
        removeRelation(item.AccountId, item.OrganizationUnitId);
      }

      return 0;
    } else {
      return this.provider.clearupRelation(organizationId);
    }
    */
    return this.provider.clearupRelation(organizationId);
  }
}
