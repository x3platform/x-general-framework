package com.x3platform.membership.services.impl;

import com.x3platform.AuthorizationObject;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.AccountOrganizationUnitRelation;
import com.x3platform.membership.mappers.OrganizationUnitMapper;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class OrganizationUnitServiceImpl implements OrganizationUnitService {

  @Autowired(required = false)
  private OrganizationUnitMapper provider = null;

  private HashMap<String, OrganizationUnitInfo> dictionary = new HashMap<String, OrganizationUnitInfo>();

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

  ///#region 属性:Save(Organization param)

  /**
   * 保存记录
   *
   * @param param OrganizationUnitInfo 实例详细信息
   * @return OrganizationUnitInfo 实例详细信息
   */
  @Override
  public final int save(OrganizationUnitInfo param) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
    {
      OrganizationUnit originalObject = findOne(param.Id);

      if (originalObject == null)
      {
        originalObject = param;
      }
      SyncToLDAP(param, originalObject.Name, originalObject.GlobalName, originalObject.ParentId);
    }
    */
    boolean isExist  = provider.isExist(param.getId());
    if(!isExist){
     // param.setId(DigitalNumberContext.generate("Key_32DigitGuid"));
      OrganizationUnitInfo organizationUnitInfo = provider.findMaxCode();
      if(organizationUnitInfo!=null && organizationUnitInfo.getCode()!=null){
         String code =  StringUtil.numberToStr(Integer.valueOf(organizationUnitInfo.getCode())+1,"00000");
         param.setCode(code);
      }else{
        param.setCode("00001");
      }
      param.setModifiedDate(new Date());
      // 设置组织全路径
      param.setFullPath(combineFullPath(param.getName(), param.getParentId()));
      // 设置唯一识别名称
      param.setDistinguishedName(combineDistinguishedName(param.getGlobalName(), param.getId()));
      return provider.save(param);
    }else{
      provider.update(param);
      return 1 ;
    }
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public final void delete(String id) {
    provider.delete(id);
  }


  @Override
  public final OrganizationUnitInfo findMaxCode(){
    return provider.findMaxCode();
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id AccountInfo Id号
   * @return 返回一个 Organization 实例的详细信息
   */
  @Override
  public final OrganizationUnitInfo findOne(String id) {
    OrganizationUnitInfo param = null;

    if (dictionary.containsKey(id)) {
      param = dictionary.get(id);
    }

    if (param == null) {
      param = provider.findOne(id);

      if (param != null) {
        dictionary.put(id, param);
      }
    }

    return provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @param globalName 组织的全局名称
   * @return 返回一个<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final OrganizationUnitInfo findOneByGlobalName(String globalName) {
    return provider.findOneByGlobalName(globalName);
  }

  /**
   * 查询某个角色所属的组织信息
   *
   * @param roleId 角色标识
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final OrganizationUnitInfo findOneByRoleId(String roleId) {
    return provider.findOneByRoleId(roleId);
  }

  /**
   * 查询某个角色所属的某一级次的组织信息
   *
   * @param roleId 角色标识
   * @param level  层次
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final OrganizationUnitInfo findOneByRoleId(String roleId, int level) {
    return provider.findOneByRoleId(roleId, level);
  }

  /**
   * 查询某个组织所属的公司信息
   *
   * @param id 组织标识
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final OrganizationUnitInfo findCorporationByOrganizationUnitId(String id) {
    return provider.findCorporationByOrganizationUnitId(id);
  }
  ///#endregion

  ///#region 函数:FindDepartmentByOrganizationUnitId(string organizationId, int level)

  /**
   * 查询某个组织的所属某个上级部门信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final OrganizationUnitInfo findDepartmentByOrganizationUnitId(String organizationId, int level) {
    return provider.findDepartmentByOrganizationUnitId(organizationId, level);
  }

  @Override
  public List<OrganizationUnitInfo> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  ///#endregion

  ///#region 函数:FindAll()

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 Organization 实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAll() {
    return provider.findAll(new HashMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有 Organization 实例的详细信息
   */
  // public final List<OrganizationUnit> findAll(String whereClause) {
  //  return this.provider.findAll(whereClause, 0);
  // }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有 Organization 实例的详细信息
   */
  // public final List<OrganizationUnit> findAll(String whereClause, int length) {
  //  return this.provider.findAll(whereClause, length);
  // }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 返回所有实例<see cref="Organization"/>的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByParentId(String parentId) {
    return findAllByParentId(parentId, 0);
  }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @param depth    深入获取的层次，0表示只获取本层次，-1表示全部获取
   * @return 返回所有实例<see cref="Organization"/>的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByParentId(String parentId, int depth) {
    // 结果列表
    ArrayList<OrganizationUnitInfo> list = new ArrayList<OrganizationUnitInfo>();

    //
    // 查找组织子部门信息
    //

    List<OrganizationUnitInfo> organizations = provider.findAllByParentId(parentId);

    list.addAll(organizations);

    if (depth == -1) {
      depth = Integer.MAX_VALUE;
    }

    if (!organizations.isEmpty() && depth > 0) {
      for (OrganizationUnitInfo organization : organizations) {
        list.addAll(findAllByParentId(organization.getId(), (depth - 1)));
      }
    }

    return list;
  }

  /**
   * 查询某条记录
   *
   * @param accountId 帐号标识
   * @return 返回一个 Organization 实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByAccountId(String accountId) {
    // 过滤 非法的内容信息
    if (StringUtil.isNullOrEmpty(accountId) || accountId.equals("0")) {
      return new ArrayList<OrganizationUnitInfo>();
    }

    return provider.findAllByAccountId(accountId);
  }

  /**
   * 查询某个角色的所属相关组织
   *
   * @param roleIds 角色标识
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByRoleIds(String roleIds) {
    List<OrganizationUnitInfo> list = new ArrayList<OrganizationUnitInfo>();

    String[] ids = roleIds.split(",");

    for (String id : ids) {
      OrganizationUnitInfo organization = findOneByRoleId(id);

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
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByRoleIds(String roleIds, int level) {
    List<OrganizationUnitInfo> list = new ArrayList<OrganizationUnitInfo>();

    String[] ids = roleIds.split(",");

    for (String id : ids) {
      OrganizationUnitInfo organization = findOneByRoleId(id, level);

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
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByCorporationId(String corporationId) {
    // 结果列表
    ArrayList<OrganizationUnitInfo> list = new ArrayList<OrganizationUnitInfo>();

    // 查找部门(公司下一级组织架构)
    List<OrganizationUnitInfo> organizations = provider.findAllByParentId(corporationId);

    for (OrganizationUnitInfo organization : organizations) {
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
   * @return 返回一个<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findAllByProjectId(String projectId) {
    //
    // 项目团队的标识 和 项目标识 保存一致
    //

    String organizationId = projectId;

    List<OrganizationUnitInfo> list = findAllByParentId(organizationId, 1);

    list.add(findOne(organizationId));

    return list;
  }

  /**
   * 查询某个帐户所属的所有公司信息
   *
   * @param accountId 帐号标识
   * @return 返回所有<see cref="Organization"/>实例的详细信息
   */
  @Override
  public final List<OrganizationUnitInfo> findCorporationsByAccountId(String accountId) {
    List<OrganizationUnitInfo> corporations = new ArrayList<OrganizationUnitInfo>();

    // TODO 待处理
    /*
    IMemberInfo member = MembershipManagement.getInstance().getMemberService().findOne(accountId);

    if (member != null && member.Corporation != null) {
      corporations.add((OrganizationUnitInfo) member.Corporation);

      List<OrganizationUnit> list = this.provider.findCorporationsByAccountId(accountId);

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
  // public final List<OrganizationUnit> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 检测是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public final boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param name 组织单位名称
   * @return 布尔值
   */
  @Override
  public final boolean isExistName(String name) {
    return provider.isExistName(name);
  }
  ///#endregion

  ///#region 函数:IsExistGlobalName(string globalName)

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  @Override
  public final boolean isExistGlobalName(String globalName) {
    return provider.isExistGlobalName(globalName);
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
  @Override
  public final int rename(String id, String name) {
    // 检测是否存在对象
    if (!isExist(id)) {
      // 不存在对象
      return 1;
    }

    return provider.rename(id, name);
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
  @Override
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
  @Override
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

    OrganizationUnitInfo param = findOne(id);

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
  @Override
  public final String combineDistinguishedName(String globalName, String id) {
    String path = getLDAPOUPathByOrganizationUnitId(id);

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
  @Override
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

    OrganizationUnitInfo param = findOne(id);

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
  @Override
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
      OrganizationUnit originalObject = findOne(id);

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
    return provider.setGlobalName(id, globalName);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param id       组织标识
   * @param parentId 父级组织标识
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setParentId(String id, String parentId) {
    return provider.setGlobalName(id, parentId);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param id     组织标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public final int setEmailStatus(String id, int status) {
    return provider.setEmailStatus(id, status);
  }

  /**
   * 获取组织的子成员
   *
   * @param organizationId 组织单位标识
   */
  @Override
  public final List<AuthorizationObject> getChildNodes(String organizationId) {
    List<AuthorizationObject> list = new ArrayList<AuthorizationObject>();
/*
    List<OrganizationUnitInfo> listA = this.findAllByParentId(organizationId);

    List<Account> listB = MembershipManagement.getInstance().getAccountService().findAllByOrganizationUnitId(organizationId);

    for (AuthorizationObject item : listA) {
      list.add(item);
    }

    for (AuthorizationObject item : listB) {
      list.add(item);
    }*/

    return list;
  }

  /**
   * 查询当前节点 下所有的组织机构 包含级别
   * @param organizationUnitId
   */
  @Override
  public List<OrganizationUnitInfo> getChildOrganizationByOrganizationUnitId(String organizationUnitId) {
   // List<Organization> child = new ArrayList<>();
    List<OrganizationUnitInfo> child = provider.getChildOrganizationByOrganizationUnitId(organizationUnitId);
    return child;
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 组织信息
   */
  @Override
  public final int syncToLDAP(OrganizationUnitInfo param) {
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
  public final int syncToLDAP(OrganizationUnitInfo param, String originalName, String originalGlobalName, String originalParentId) {
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
  // public final int syncFromPackPage(OrganizationUnit param) {
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
  @Override
  public final List<AccountOrganizationUnitRelation> findAllRelationByAccountId(String accountId) {
    return provider.findAllRelationByAccountId(accountId);
  }

  /**
   * 根据组织查询相关帐号的关系
   *
   * @param organizationId 组织标识
   * @return Table Columns：AccountId, OrganizationId, IsDefault, BeginDate, EndDate
   */
  @Override
  public final List<AccountOrganizationUnitRelation> findAllRelationByRoleId(String organizationId) {
    return provider.findAllRelationByRoleId(organizationId);
  }

  /**
   * 添加帐号与相关组织的关系
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  @Override
  public final int addRelation(String accountId, String organizationId) {

    return addRelation(accountId, organizationId, false, new Date(), null);
  }

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   * @param isDefault      是否是默认组织
   * @param beginDate      启用时间
   * @param endDate        停用时间 , mysql 最大能存9999 ； 169104628-12-10 19:08:16.999999
   */
  @Override
  public final int addRelation(String accountId, String organizationId, boolean isDefault, Date beginDate, Date endDate) {
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
      Account account = MembershipManagement.Instance.AccountService[accountId];

      OrganizationUnit organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      // 帐号对象、帐号的全局名称、帐号的登录名、组织对象、组织的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && organization != null && !StringUtil.isNullOrEmpty(organization.GlobalName)) {
        LDAPManagement.Instance.Group.AddRelation(account.LoginName, LDAPSchemaClassType.User, organization.GlobalName);
      }
    }
    */
    return provider.addRelation(accountId, organizationId, isDefault, beginDate, endDate);
  }

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountIds     帐号标识，多个以逗号隔开
   * @param organizationId 组织标识
   */
  @Override
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
  @Override
  public final int addParentRelations(String accountId, String organizationId) {
    OrganizationUnitInfo organization = provider.findOne(organizationId);

    // [容错]如果角色信息为空，中止相关组织设置
    if (organization != null && !StringUtil.isNullOrEmpty(organization.getParentId())) {
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
  @Override
  public final int extendRelation(String accountId, String organizationId, Date endDate) {
    return provider.extendRelation(accountId, organizationId, endDate);
  }

  /**
   * 移除帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  @Override
  public final int removeRelation(String accountId, String organizationId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      Account account = MembershipManagement.Instance.AccountService[accountId];

      OrganizationUnit organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      // 帐号对象、帐号的全局名称、帐号的登录名、组织对象、组织的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && organization != null && !StringUtil.isNullOrEmpty(organization.GlobalName)) {
        LDAPManagement.Instance.Group.RemoveRelation(account.LoginName, LDAPSchemaClassType.User, organization.GlobalName);
      }
    }
    */

    return provider.removeRelation(accountId, organizationId);
  }

  /**
   * 移除帐号相关组织的默认关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public final int removeDefaultRelation(String accountId) {
    return provider.removeDefaultRelation(accountId);
  }

  /**
   * 移除帐号相关组织的非默认关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public final int removeNondefaultRelation(String accountId) {
    return provider.removeNondefaultRelation(accountId);
  }

  /**
   * 移除帐号已过期的组织关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public final int removeExpiredRelation(String accountId) {
    return provider.removeExpiredRelation(accountId);
  }

  /**
   * 移除帐号相关组织的所有关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public final int removeAllRelation(String accountId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<AccountOrganizationUnitRelation> list = findAllRelationByAccountId(accountId);

      for (AccountOrganizationUnitRelation item : list) {
        removeRelation(item.AccountId, item.OrganizationUnitId);
      }

      return 0;
    } else {
      return this.provider.removeAllRelation(accountId);
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
  public final boolean hasDefaultRelation(String accountId) {
    return provider.hasDefaultRelation(accountId);
  }

  /**
   * 设置帐号的默认组织
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  @Override
  public final int setDefaultRelation(String accountId, String organizationId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      Account account = MembershipManagement.Instance.AccountService[accountId];

      OrganizationUnit organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      if (account != null && organization != null) {
        LDAPManagement.Instance.Group.AddRelation(account.GlobalName, LDAPSchemaClassType.User, organization.Name);
      }
    }
    */
    return provider.setDefaultRelation(accountId, organizationId);
  }

  /**
   * 清理组织与帐号的关系
   *
   * @param organizationId 组织标识
   */
  @Override
  public final int clearupRelation(String organizationId) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      List<AccountOrganizationUnitRelation> list = findAllRelationByRoleId(organizationId);

      for (AccountOrganizationUnitRelation item : list) {
        removeRelation(item.AccountId, item.OrganizationUnitId);
      }

      return 0;
    } else {
      return this.provider.clearupRelation(organizationId);
    }
    */
    return provider.clearupRelation(organizationId);
  }

}
