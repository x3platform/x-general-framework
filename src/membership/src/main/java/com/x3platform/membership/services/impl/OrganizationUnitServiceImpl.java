package com.x3platform.membership.services.impl;

import com.x3platform.AuthorizationObject;
import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.ldap.configuration.LdapConfigurationView;
import com.x3platform.membership.*;
import com.x3platform.membership.mappers.OrganizationUnitMapper;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.tree.DynamicTreeNode;
import com.x3platform.tree.DynamicTreeView;
import com.x3platform.tree.TreeNode;
import com.x3platform.tree.TreeView;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static com.x3platform.Constants.TEXT_ZERO;
import static com.x3platform.membership.Constants.ORGANIZATION_STRUCTURE_ID;
import static com.x3platform.membership.Constants.ORGANIZATION_UNIT_ROOT_ID;

/**
 * @author ruanyu
 */
public class OrganizationUnitServiceImpl implements OrganizationUnitService {

  private static String CACHE_KEY_ID_PREFIX = "x3platform:membership:organization-unit:id:";

  private static String CACHE_KEY_TREEVIEW_PREFIX = "x3platform:membership:organization-unit:treeview:";

  private static String DIGITAL_NUMBER_KEY_CODE = "Table_OrganizationUnit_Key_Code";

  @Autowired(required = false)
  private OrganizationUnitMapper provider = null;

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(OrganizationUnit item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item, CachingManager.getRandomMinutes());
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(OrganizationUnit item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }
  }

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link OrganizationUnit} 实例详细信息
   * @return 消息代码
   */
  @Override
  public int save(OrganizationUnit entity) throws NullPointerException {
    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new NullPointerException("标识不能为空");
    }

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !provider.isExist(entity.getId());

    int affectedRows;

    String id = entity.getId();

    if (LdapConfigurationView.getInstance().getIntegratedMode()) {

      OrganizationUnit originalObject = findOne(id);
      if (originalObject == null) {
        originalObject = entity;
      }

      // syncToLdap(param, originalObject.Name, originalObject.GlobalName, originalObject.ParentId);
    }

    if (StringUtil.isNullOrEmpty(entity.getId())) {
      entity.setId(StringUtil.toUuid());
    }
    if (StringUtil.isNullOrEmpty(entity.getGlobalName())) {
      entity.setGlobalName(entity.getName());
    }

    if (isNewObject) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }

      // 设置组织全路径
      entity.setFullPath(combineFullPath(entity.getName(), entity.getParentId()));
      // 设置唯一识别名称
      entity.setDistinguishedName(combineDistinguishedName(entity.getGlobalName(), entity.getId()));
      // 设置修改时间
      entity.setModifiedDate(LocalDateTime.now());

      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.update(entity);
    }

    InternalLogger.getLogger().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    // 保存数据后, 更新缓存信息
    entity = provider.findOne(entity.getId());

    if (entity != null) {
      removeCacheItem(entity);

      addCacheItem(entity);

      // 如果单个组织信息缓存信息出现更新，清除所有树节点的缓存。
      CachingManager.deleteByPattern(CACHE_KEY_TREEVIEW_PREFIX + "*");
    }

    return 0;
  }

  /**
   * 虚拟删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int remove(String id) {
    this.setStatus(id, 0);
    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public int delete(String id) {
    OrganizationUnit entity = provider.findOne(id);

    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);

      // 如果单个组织信息缓存信息出现更新，清除所有树节点的缓存。
      CachingManager.deleteByPattern(CACHE_KEY_TREEVIEW_PREFIX + "*");

      // 删除数据库记录
      int affectedRows = provider.delete(id);

      InternalLogger.getLogger().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);
    }

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public OrganizationUnit findOne(String id) {
    OrganizationUnit entity = null;

    // 根节点快速处理
    if (ORGANIZATION_UNIT_ROOT_ID.equals(id)) {
      return null;
    }

    String key = CACHE_KEY_ID_PREFIX + id;

    if (CachingManager.contains(key)) {
      entity = (OrganizationUnit) CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (entity == null) {
      entity = provider.findOne(id);

      if (entity != null) {
        addCacheItem(entity);
      }
    }

    return entity;
  }

  /**
   * 查询某条记录
   *
   * @param globalName 组织的全局名称
   * @return 一个 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public OrganizationUnit findOneByGlobalName(String globalName) {
    return provider.findOneByGlobalName(globalName);
  }

  /**
   * 查询某个角色所属的组织信息
   *
   * @param roleId 角色标识
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public OrganizationUnit findOneByRoleId(String roleId) {
    return provider.findOneByRoleId(roleId);
  }

  /**
   * 查询某个角色所属的某一级次的组织信息
   *
   * @param roleId 角色标识
   * @param level  层次
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public OrganizationUnit findOneByRoleId(String roleId, int level) {
    return provider.findOneByRoleIdAndLevel(roleId, level);
  }

  /**
   * 查询某个组织所属的公司信息
   *
   * @param id 组织单元标识
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public OrganizationUnit findCorporationByOrganizationUnitId(String id) {
    return provider.findCorporationByOrganizationUnitId(id);
  }

  /**
   * 查询某个组织的所属某个上级部门信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public OrganizationUnit findDepartmentByOrganizationUnitId(String organizationId, int level) {
    return provider.findDepartmentByOrganizationUnitId(organizationId, level);
  }

  @Override
  public List<OrganizationUnit> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 列表型数据，都会查询最高id, 获取最高id, 查询类型
   */
  @Override
  public List<OrganizationUnit> findAll(Map params) {
    /**
     * 获取对应的参数，等级 公司，顶级为 0 ，设置父级为什么；
     * 组织类型  9-区域 0-公司 1-部门 2-项目团队 3-项目 4-项目分期
     */
    List<OrganizationUnit> organizationUnits = provider.findAll(params);

    if (organizationUnits != null && organizationUnits.size() > 0) {
      for (OrganizationUnit unitInfo : organizationUnits) {
        List<OrganizationUnit> childrenNodes = provider.getChildOrganizationByOrganizationUnitId(unitInfo.getId());
        if (childrenNodes != null && childrenNodes.size() > 0) {
          // unitInfo.setChildren(childrenNodes);
          for (OrganizationUnit child : childrenNodes) {
            setChildren(child, childrenNodes);
          }
          setChildren(unitInfo, childrenNodes);
        }
      }
    }
    return organizationUnits;
  }

  /**
   * 查询所有相关记录
   *
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findAll() {
    return provider.findAll(new HashMap());
  }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @return 所有实例 {@link OrganizationUnit} 的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByParentId(String parentId) {
    return findAllByParentId(parentId, 0);
  }

  /**
   * 查询某个父节点下的所有组织单位
   *
   * @param parentId 父节标识
   * @param depth    深入获取的层次，0 表示只获取本层次，-1 表示全部获取
   * @return 所有实例 {@link OrganizationUnit} 的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByParentId(String parentId, int depth) {
    // 结果列表
    ArrayList<OrganizationUnit> list = new ArrayList<OrganizationUnit>();

    // 查找组织子部门信息
    List<OrganizationUnit> organizations = provider.findAllByParentId(parentId);

    list.addAll(organizations);

    if (depth == -1) {
      depth = Integer.MAX_VALUE;
    }

    if (!organizations.isEmpty() && depth > 0) {
      for (OrganizationUnit organization : organizations) {
        list.addAll(findAllByParentId(organization.getId(), (depth - 1)));
      }
    }

    return list;
  }

  /**
   * @param parentId 父节点对象
   * @param level    查询等级
   */
  @Override
  public OrganizationUnit findPhysicalTreeView(String parentId, int level) {
    OrganizationUnit parent = provider.findOne(parentId);
    if (StringUtil.isNullOrEmpty(parentId) || TEXT_ZERO.equals(parentId)) {
      //
    } else {
      List<OrganizationUnit> children = provider.getChildOrganizationByOrganizationUnitId(parentId);
      // 取消树节点
      setChildren(parent, children);
    }
    return parent;
  }

  /**
   * 设置子节点
   *
   * @param parent    父级
   * @param labelList 所有子集
   */
  private void setChildren(OrganizationUnit parent, List<OrganizationUnit> labelList) {
    List<OrganizationUnit> labelDataList = new ArrayList<>();
    for (OrganizationUnit label : labelList) {
      if (parent.getId().equals(label.getParentId())) {
        if (!isLastChild(label.getId(), labelList)) {
          setChildren(label, labelList);
        }
        labelDataList.add(label);
      }
    }
    parent.setChildren(labelDataList);
  }

  /**
   * 判断是否是最低级
   */
  public static boolean isLastChild(String id, List<OrganizationUnit> labelList) {
    boolean result = true;
    List<OrganizationUnit> filterResult = labelList.stream().filter(item -> id.equals(item.getParentId()))
      .collect(Collectors.toList());
    if (filterResult.size() > 0) {
      result = false;
    }
    return result;
  }

  /**
   * 查询某条记录
   *
   * @param accountId 帐号标识
   * @return 一个 Organization 实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByAccountId(String accountId) {
    // 过滤 非法的内容信息
    if (StringUtil.isNullOrEmpty(accountId) || accountId.equals("0")) {
      return new ArrayList<OrganizationUnit>();
    }

    return provider.findAllByAccountId(accountId);
  }

  /**
   * @param accountId
   * @return
   */
  @Override
  public List<OrganizationUnit> findAllRelationOrganizationUnitByAccountId(String accountId) {
    // 过滤 非法的内容信息
    if (StringUtil.isNullOrEmpty(accountId) || accountId.equals("0")) {
      return new ArrayList<OrganizationUnit>();
    }
    return provider.findAllRelationOrganizationUnitByAccountId(accountId);
  }

  /**
   * 查询某个角色的所属相关组织
   *
   * @param roleIds 角色标识
   * @return 所有{@link OrganizationUnit}实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByRoleIds(String roleIds) {
    List<OrganizationUnit> list = new ArrayList<OrganizationUnit>();

    String[] ids = roleIds.split(",");

    for (String id : ids) {
      OrganizationUnit organization = findOneByRoleId(id);

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
   * @return 所有{@link OrganizationUnit}实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByRoleIds(String roleIds, int level) {
    List<OrganizationUnit> list = new ArrayList<OrganizationUnit>();

    String[] ids = roleIds.split(",");

    for (String id : ids) {
      OrganizationUnit organization = findOneByRoleId(id, level);

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
   * @return 所有{@link OrganizationUnit}实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByCorporationId(String corporationId) {
    // 结果列表
    ArrayList<OrganizationUnit> list = new ArrayList<OrganizationUnit>();

    // 查找部门(公司下一级组织架构)
    List<OrganizationUnit> organizations = provider.findAllByParentId(corporationId);

    for (OrganizationUnit organization : organizations) {
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
   * @return 一个{@link OrganizationUnit}实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findAllByProjectId(String projectId) {
    //
    // 项目团队的标识 和 项目标识 保存一致
    //

    String organizationId = projectId;

    List<OrganizationUnit> list = findAllByParentId(organizationId, 1);

    list.add(findOne(organizationId));

    return list;
  }

  /**
   * 查询某个帐户所属的所有公司信息
   *
   * @param accountId 帐号标识
   * @return 所有 {@link OrganizationUnit} 实例的详细信息
   */
  @Override
  public List<OrganizationUnit> findCorporationsByAccountId(String accountId) {
    List<OrganizationUnit> corporations = new ArrayList<OrganizationUnit>();

    User user = MembershipManagement.getInstance().getUserService().findOne(accountId);

    if (user != null && user.getCorporationId() != null) {
      OrganizationUnit corporation = findOne(user.getCorporationId());

      if (corporation != null) {
        corporations.add(corporation);
      }

      List<OrganizationUnit> list = provider.findCorporationsByAccountId(accountId);

      for (OrganizationUnit item : list) {
        if (!item.getId().equals(user.getCorporationId())) {
          corporations.add(item);
        }
      }
    }

    return corporations;
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

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
   * 查询是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  @Override
  public boolean isExistGlobalName(String globalName) {
    return provider.isExistGlobalName(globalName);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param id   组织单元标识
   * @param name 组织名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  @Override
  public int rename(String id, String name) {
    // 检测是否存在对象
    if (!isExist(id)) {
      // 不存在对象
      return 1;
    }

    return provider.rename(id, name);
  }

  /**
   * 组合全路径
   *
   * @param name     组织名称
   * @param parentId 父级对象标识
   */
  @Override
  public String combineFullPath(String name, String parentId) {
    String path = getOrganizationPathByOrganizationUnitId(parentId);

    return String.format("%1$s%2$s", path, name);
  }

  /**
   * 根据组织标识计算组织的全路径
   *
   * @param organizationId 组织标识
   */
  @Override
  public String getOrganizationPathByOrganizationUnitId(String organizationId) {
    String path = formatOrganizationPath(organizationId);

    return String.format("%1$s\\", path);
  }

  /**
   * 格式化组织路径
   */
  private String formatOrganizationPath(String id) {
    String path = "";

    String parentId = "";

    String name = null;

    OrganizationUnit param = findOne(id);

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
   */
  @Override
  public String combineDistinguishedName(String globalName, String id) {
    String path = getLdapOuPathByOrganizationUnitId(id);

    return String.format("cn=%1$s,%2$s%3$s", globalName, path,
      LdapConfigurationView.getInstance().getSuffixDistinguishedName());
  }

  /**
   * 根据组织标识计算 Active Directory OU 路径
   *
   * @param organizationId 组织标识
   */
  @Override
  public String getLdapOuPathByOrganizationUnitId(String organizationId) {
    return formatLdapPath(organizationId);
  }

  /**
   * 格式化 Active Directory 路径
   */
  private String formatLdapPath(String id) {
    String path = "";

    String parentId = "";

    // OU的名称
    String name = null;

    OrganizationUnit param = findOne(id);

    if (param == null) {
      return "";
    } else {
      name = param.getName();

      // 组织结构的根节点OU特殊处理 默认为组织结构
      if (id.equals(ORGANIZATION_STRUCTURE_ID)) {
        name = LdapConfigurationView.getInstance().getCorporationOrganizationUnitFolderRoot();
        if (name.startsWith("ou=")) {
          name = name.substring(3, name.length());
        }
      }

      // 1.名称不能为空 2.父级对象标识不能为空
      if (!StringUtil.isNullOrEmpty(name) && !StringUtil.isNullOrEmpty(param.getParentId()) && !UUIDUtil.emptyString()
        .equals(param.getParentId())) {
        parentId = param.getParentId();

        path = formatLdapPath(parentId);

        path =
          StringUtil.isNullOrEmpty(path) ? String.format("ou=%1$s", name) : String.format("ou=%1$s", name) + "," + path;

        return path;
      }

      return String.format("ou=%1$s", name);
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

    if (LdapConfigurationView.getInstance().getIntegratedMode()) {
      OrganizationUnit originalObject = findOne(id);

      if (originalObject != null) {
        // 由于外部系统直接同步到人员及权限管理的数据库中，
        // 所以 Active Directory 上不会直接创建相关对象，需要手工设置全局名称并创建相关对象。
        // if (!StringUtil.isNullOrEmpty(originalObject.getGlobalName())
        //  && LdapManagement.getInstance().getGroup().isExistName(originalObject.getGlobalName())) {
        //   LdapManagement.Instance.Group.Rename(originalObject.GlobalName, globalName);
        // } else {
        //   LdapManagement.Instance.OrganizationUnit.Add(originalObject.Name, MembershipManagement.Instance.OrganizationUnitService.GetLdapOUPathByOrganizationUnitId(originalObject.ParentId));

        //   LdapManagement.Instance.Group.Add(globalName, MembershipManagement.Instance.OrganizationUnitService.GetLdapOUPathByOrganizationUnitId(originalObject.Id));
        // }
      }
    }

    return provider.setGlobalName(id, globalName);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param id       组织单元标识
   * @param parentId 父级组织标识
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setParentId(String id, String parentId) {
    return provider.setGlobalName(id, parentId);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param id     组织单元标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public int setEmailStatus(String id, int status) {
    return provider.setEmailStatus(id, status);
  }

  /**
   * 设置状态
   *
   * @param id     组织单元标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public int setStatus(String id, int status) {
    return provider.setStatus(id, status);
  }

  /**
   * 获取组织的子成员
   *
   * @param organizationUnitId 组织单位标识
   */
  @Override
  public List<AuthorizationObject> getChildNodes(String organizationUnitId) {
    List<AuthorizationObject> list = new ArrayList<AuthorizationObject>();

    List<OrganizationUnit> listA = findAllByParentId(organizationUnitId);

    List<Account> listB = MembershipManagement.getInstance().getAccountService()
      .findAllByOrganizationUnitId(organizationUnitId);

    for (AuthorizationObject item : listA) {
      list.add(item);
    }

    for (AuthorizationObject item : listB) {
      list.add(item);
    }

    return list;
  }

  /**
   * 查询当前节点 下所有的组织机构 包含级别
   */
  @Override
  public List<OrganizationUnit> getChildOrganizationByOrganizationUnitId(String organizationUnitId) {
    List<OrganizationUnit> child = new ArrayList<>();
    // OrganizationUnit parent = provider.findOne(organizationUnitId)
    // child.add(parent);
    List<OrganizationUnit> children = provider.getChildOrganizationByOrganizationUnitId(organizationUnitId);

    if (children != null && children.size() > 0) {
      child.addAll(children);
    }

    return child;
  }

  @Override
  public List<OrganizationUnit> getChildOrganizationByDeleteOrganizationUnitId(String organizationUnitId) {
    List<OrganizationUnit> child = new ArrayList<>();
    // OrganizationUnit parent = provider.findOne(organizationUnitId)
    // child.add(parent);
    List<OrganizationUnit> children = provider.getChildOrganizationByDeleteOrganizationUnitId(organizationUnitId);
    if (children != null && children.size() > 0) {
      child.addAll(children);
    }
    return child;
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 组织信息
   */
  @Override
  public int syncToLdap(OrganizationUnit param) {
    return syncToLdap(param, param.getName(), param.getGlobalName(), param.getParentId());
  }

  /**
   * 同步信息
   *
   * @param param              组织信息
   * @param originalName       原始名称
   * @param originalGlobalName 原始全局名称
   * @param originalParentId   原始父级标识
   */
  public int syncToLdap(OrganizationUnit param, String originalName, String originalGlobalName,
                        String originalParentId) {
    /*
    if (LdapConfigurationView.Instance.IntegratedMode.equals("ON")) {
      if (StringUtil.isNullOrEmpty(param.GlobalName)) {
        // 组织【${FullPath}】全局名称为空，请配置相关信息。
        return 1;
      } else if (StringUtil.isNullOrEmpty(param.Name)) {
        // 组织【${FullPath}】名称为空，请配置相关信息。
        return 2;
      } else {
        // 1.原始的全局名称不为空。
        // 2.Active Directory 上有相关对象。
        if (!StringUtil.isNullOrEmpty(originalGlobalName) && LdapManagement.Instance.Group.IsExistName(originalGlobalName)) {
          if (!originalName.equals(param.Name)) {
            // 组织【${GlobalName}】的名称发生改变。
            LdapManagement.Instance.OrganizationUnit.Rename(originalName, MembershipManagement.Instance.OrganizationUnitService.GetLdapOUPathByOrganizationUnitId(originalParentId), param.Name);
          }

          if (!originalGlobalName.equals(param.GlobalName)) {
            // 组织【${GlobalName}】的全局名称发生改变。
            LdapManagement.Instance.Group.Rename(originalGlobalName, param.GlobalName);
          }

          if (!originalParentId.equals(param.ParentId)) {
            // 组织【${GlobalName}】的父级节点发生改变。
            LdapManagement.Instance.OrganizationUnit.MoveTo(this.GetLdapOUPathByOrganizationUnitId(param.Id), this.GetLdapOUPathByOrganizationUnitId(param.ParentId));
          }

          return 0;
        } else {
          LdapManagement.Instance.OrganizationUnit.Add(param.Name, this.GetLdapOUPathByOrganizationUnitId(param.ParentId));

          LdapManagement.Instance.Group.Add(param.GlobalName, this.GetLdapOUPathByOrganizationUnitId(param.Id));

          // 组织【${GlobalName}】创建成功。
          return 0;
        }
      }
    }
    */
    return 0;
  }

  // -------------------------------------------------------
  // 树形视图
  // -------------------------------------------------------
  @Override
  public TreeView getTreeView(String treeViewName, String treeViewRootTreeNodeId, String commandFormat) {
    String key = CACHE_KEY_TREEVIEW_PREFIX + treeViewRootTreeNodeId;

    // 读取缓存信息
    if (CachingManager.contains(key)) {
      return (TreeView) CachingManager.get(key);
    }

    TreeView treeView = new TreeView(treeViewName, treeViewRootTreeNodeId, commandFormat);

    List<TreeNode> childNodes = getTreeNodes(treeViewRootTreeNodeId, commandFormat);

    if (!childNodes.isEmpty()) {
      treeView.add(childNodes);
      // 设置缓存信息
      CachingManager.set(key, treeView);
    }

    return treeView;
  }

  private List<TreeNode> getTreeNodes(String parentId, String commandFormat) {
    List<OrganizationUnit> list = provider.findTreeNodesByParentId(parentId);

    List<TreeNode> treeNodes = new ArrayList<TreeNode>();

    for (OrganizationUnit item : list) {
      TreeNode treeNode = new TreeNode(item.getId(), item.getParentId(), item.getName(), item.getName(), commandFormat);

      List<TreeNode> childNodes = getTreeNodes(item.getId(), commandFormat);

      if (!childNodes.isEmpty()) {
        treeNode.add(childNodes);
      }

      treeNodes.add(treeNode);
    }

    return treeNodes;
  }

  @Override
  public DynamicTreeView getDynamicTreeView(String treeViewName, String treeViewRootTreeNodeId, String parentId,
                                            String commandFormat) {

    parentId = (StringUtil.isNullOrEmpty(parentId) || "0".equals(parentId)) ? treeViewRootTreeNodeId : parentId;

    List<OrganizationUnit> list = provider.findTreeNodesByParentId(parentId);

    DynamicTreeView treeView = new DynamicTreeView(treeViewName, treeViewRootTreeNodeId, parentId, commandFormat);

    for (OrganizationUnit item : list) {
      DynamicTreeNode treeNode = new DynamicTreeNode(item.getId(), item.getParentId(), item.getName(),
        item.getGlobalName(), commandFormat, true);

      treeView.add(treeNode);
    }

    return treeView;
  }

  @Override
  public List<OrganizationUnit> getTreeTable(String id) {
    List<OrganizationUnit> root = new ArrayList<OrganizationUnit>();

    OrganizationUnit entity = findOne(id);

    List<OrganizationUnit> list = getTreeTableRows(id);

    if (!list.isEmpty()) {
      entity.setChildren(list);
    }

    root.add(entity);

    return root;
  }

  private List<OrganizationUnit> getTreeTableRows(String parentId) {
    List<OrganizationUnit> list = provider.findAllTreeNodesByParentId(parentId);

    for (OrganizationUnit item : list) {
      List<OrganizationUnit> childNodes = getTreeTableRows(item.getId());

      if (!childNodes.isEmpty()) {
        item.setChildren(childNodes);
      }
    }

    return list;
  }

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
  public List<AccountOrganizationUnitRelation> findAllRelationByAccountId(String accountId) {
    return provider.findAllRelationByAccountId(accountId);
  }

  /**
   * 根据组织查询相关帐号的关系
   *
   * @param organizationUnitId 组织标识
   * @return Table Columns：AccountId, OrganizationId, IsDefault, BeginDate, EndDate
   */
  @Override
  public List<AccountOrganizationUnitRelation> findAllRelationByOrganizationUnitId(String organizationUnitId) {
    return provider.findAllRelationByOrganizationUnitId(organizationUnitId);
  }

  /**
   * 添加帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  @Override
  public int addRelation(String accountId, String organizationId) {
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
  public int addRelation(String accountId, String organizationId, boolean isDefault, Date beginDate, Date endDate) {
    if (StringUtil.isNullOrEmpty(accountId)) {
      // 帐号标识不能为空
      return 1;
    }

    if (StringUtil.isNullOrEmpty(organizationId)) {
      // 组织标识不能为空
      return 2;
    }
    /*
    if (LdapConfigurationView.Instance.IntegratedMode.equals("ON")) {
      Account account = MembershipManagement.Instance.AccountService[accountId];

      OrganizationUnit organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      // 帐号对象、帐号的全局名称、帐号的登录名、组织对象、组织的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && organization != null && !StringUtil.isNullOrEmpty(organization.GlobalName)) {
        LdapManagement.Instance.Group.AddRelation(account.LoginName, LdapSchemaClassType.User, organization.GlobalName);
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
  public int addRelationRange(String accountIds, String organizationId) {
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
  public int addParentRelations(String accountId, String organizationId) {
    OrganizationUnit organization = provider.findOne(organizationId);

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
  public int extendRelation(String accountId, String organizationId, Date endDate) {
    return provider.extendRelation(accountId, organizationId, endDate);
  }

  /**
   * 移除帐号与相关组织的关系
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  @Override
  public int removeRelation(String accountId, String organizationId) {
    /*
    if (LdapConfigurationView.Instance.IntegratedMode.equals("ON")) {
      Account account = MembershipManagement.Instance.AccountService[accountId];

      OrganizationUnit organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      // 帐号对象、帐号的全局名称、帐号的登录名、组织对象、组织的全局名称等不能为空。
      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && organization != null && !StringUtil.isNullOrEmpty(organization.GlobalName)) {
        LdapManagement.Instance.Group.RemoveRelation(account.LoginName, LdapSchemaClassType.User, organization.GlobalName);
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
  public int removeDefaultRelation(String accountId) {
    return provider.removeDefaultRelation(accountId);
  }

  /**
   * 移除帐号相关组织的非默认关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeNondefaultRelation(String accountId) {
    return provider.removeNondefaultRelation(accountId);
  }

  /**
   * 移除帐号已过期的组织关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeExpiredRelation(String accountId) {
    return provider.removeExpiredRelation(accountId);
  }

  /**
   * 移除帐号相关组织的所有关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeAllRelation(String accountId) {
    /*
    if (LdapConfigurationView.Instance.IntegratedMode.equals("ON")) {
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
  public boolean hasDefaultRelation(String accountId) {
    return provider.hasDefaultRelation(accountId);
  }

  /**
   * 设置帐号的默认组织
   *
   * @param accountId      帐号标识
   * @param organizationId 组织标识
   */
  @Override
  public int setDefaultRelation(String accountId, String organizationId) {
    /*
    if (LdapConfigurationView.Instance.IntegratedMode.equals("ON")) {
      Account account = MembershipManagement.Instance.AccountService[accountId];

      OrganizationUnit organization = MembershipManagement.Instance.OrganizationUnitService[organizationId];

      if (account != null && organization != null) {
        LdapManagement.Instance.Group.AddRelation(account.GlobalName, LdapSchemaClassType.User, organization.Name);
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
  public int clearupRelation(String organizationId) {
    /*
    if (LdapConfigurationView.Instance.IntegratedMode.equals("ON")) {
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
