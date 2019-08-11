package com.x3platform.membership.services.impl;

import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.Role;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.membership.mappers.AccountMapper;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.models.OrganizationUnitInfo;
import com.x3platform.membership.services.AccountService;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.membership.services.RoleService;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 帐号服务
 *
 * @author ruanyu
 */
public class AccountServiceImpl implements AccountService {

  /**
   * 数据提供器
   */
  @Autowired(required = false)
  private AccountMapper provider;

  @Autowired(required = false)
  @Qualifier("com.x3platform.membership.services.OrganizationUnitService")
  private OrganizationUnitService organizationUnitService;

  @Autowired(required = false)
  @Qualifier("com.x3platform.membership.services.RoleService")
  private RoleService roleService;

  /**
   * @param organizationUnitId 根据组织机构Id 获取当前组织机构 或者当前组织机构下 及机构人员问题
   * @return 所有人员信息
   */
  @Override
  public List<Account> findAllAccountsByOrganization(String organizationUnitId) {
    List<Account> result = new ArrayList<>();

    List<OrganizationUnitInfo> organizationUnitInfos = MembershipManagement.getInstance().getOrganizationUnitService().getChildOrganizationByOrganizationUnitId(organizationUnitId);
    if (organizationUnitInfos != null) {
      for (int oInt = 0; oInt < organizationUnitInfos.size(); oInt++) {
        OrganizationUnitInfo organizationUnitInfo = organizationUnitInfos.get(oInt);
        List<Account> list = provider.findAllByOrganizationUnitId(organizationUnitInfo.getId());
        result.addAll(list);
      }
    }

    return result;
  }

  /**
   * 缓存存储
   */
  private Map<String, Map<String, Account>> dictionary = new HashMap<String, Map<String, Account>>();
  // private Map<String, Map<String, Account>> dictionary = new HashMap<String, Map<String, Account>>() {
  //  {
  //    "id", new SyncDictionary<String, Account>()
  //  },
  //
  //  {
  //    "loginName", new SyncDictionary<String, Account>()
  //  }
  // };

  /**
   * 构造函数
   */
  public AccountServiceImpl() {
    dictionary.put("id", new HashMap<String, Account>());
    dictionary.put("loginName", new HashMap<String, Account>());

    // 创建对象构建器(Spring.NET)
    // String springObjectFile = this.configuration.keySet()["SpringObjectFile"].Value;

    // SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(MembershipConfiguration.ApplicationName, springObjectFile);

    // 创建数据提供器
    // this.provider = objectBuilder.<IAccountProvider>GetObject(IAccountProvider.class);
  }

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * @return
   */
  public int createCache() {
    dictionary = new HashMap<String, Map<String, Account>>();
    dictionary.put("id", new HashMap<String, Account>());
    dictionary.put("loginName", new HashMap<String, Account>());

    return 0;
  }

  /**
   * @return
   */
  public int clearCache() {
    dictionary.get("id").clear();
    dictionary.get("loginName").clear();

    return 0;
  }

  /**
   * @return
   */
  public void addCacheItem(Object item) {
    if (item instanceof Account) {
      addCacheItem((Account) item);
    }
  }

  private void addCacheItem(Account item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      if (dictionary.get("id").containsKey(item.getId())) {
        dictionary.get("id").replace(item.getId(), item);
      } else {
        dictionary.get("id").put(item.getId(), item);
      }
    }

    if (!StringUtil.isNullOrEmpty(item.getLoginName())) {
      if (dictionary.get("loginName").containsKey(item.getLoginName())) {
        dictionary.get("loginName").replace(item.getLoginName(), item);
      } else {
        dictionary.get("loginName").put(item.getLoginName(), item);
      }
    }
  }

  /**
   * @return
   */
  public final void RemoveCacheItem(Object item) {
    if (item instanceof Account) {
      RemoveCacheItem((Account) item);
    }
  }

  private void RemoveCacheItem(Account item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      if (dictionary.get("id").containsKey(item.getId())) {
        dictionary.get("id").remove(item.getId());
      }
    }

    if (!StringUtil.isNullOrEmpty(item.getLoginName())) {
      if (dictionary.get("loginName").containsKey(item.getLoginName())) {
        dictionary.get("loginName").remove(item.getLoginName());
      }
    }
  }

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------


  /**
   * @param params 根据相关参数， 查询最大的人员编码信息
   * @return 返回逻辑相关信息
   */
  @Override
  public Account findMaxCode(Map params) {
    return provider.findMaxCode(params);
  }

  /**
   * 人员保存
   *
   * @param accountInfo
   * @return
   */
  @Override
  public AccountInfo save(AccountInfo accountInfo) {

    accountInfo.setDistinguishedName(combineDistinguishedName(accountInfo.getDisplayName()));
    if (!provider.isExist(accountInfo.getId())) {
      // 编号不用默认设置；手写，避免不同场景应用情况数据问题 ；
      provider.insert(accountInfo);
    } else {
      provider.update(accountInfo);
    }
    String accountId = accountInfo.getId();
    String[] roleIds = accountInfo.getRoleText();
    // -------------------------------------------------------
    // 设置角色关系
    // -------------------------------------------------------
    if (roleIds.length > 0) {
      roleService.removeAllRelation(accountId);
      organizationUnitService.removeAllRelation(accountId);
      for (int i = 0; i < roleIds.length; i++) {
        roleService.addRelation(accountId, roleIds[i]);
        Role role = roleService.findOne(roleIds[i]);
        String organizationUnitText = role.getOrganizationUnitId();
        String[] organizationUnit = accountInfo.getOrganizationUnitText();
        boolean result = false;
        for (int j = 0; j < organizationUnit.length; j++) {
          // organizationUnitService.addRelation( accountId, organizationUnit[j], true, LocalDateTime.now(), LocalDateTime.MAX);
          if (organizationUnit[j].equals(organizationUnitText)) {
            result = true;
          }
        }
        if (!result) {
          organizationUnitService.addRelation(accountId, role.getOrganizationUnitId());
        }
      }
    }
    // 设置组织 ， 默认组织新增及修改
    String[] organizationUnit = accountInfo.getOrganizationUnitText();
    if (organizationUnit.length > 0) {
      organizationUnitService.removeAllRelation(accountId);
      for (int i = 0; i < organizationUnit.length; i++) {
        organizationUnitService.addRelation(accountId, organizationUnit[i], true, new Date(), null);
      }
    }

    return accountInfo;
  }

  /**
   * 删除记录
   *
   * @param id 帐号标识
   */
  @Override
  public final void delete(String id) {
    Account originalObject = findOne(id);

    // 删除缓存
    if (originalObject != null) {
      RemoveCacheItem(originalObject);
    }

    // 删除数据库记录
    provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id AccountInfo Id号
   * @return 返回一个 Account 实例的详细信息
   */
  @Override
  public final Account findOne(String id) {
    Account param = null;
    // 查找缓存数据
    if (dictionary.get("id").containsKey(id)) {
      param = dictionary.get("id").get(id);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (param == null) {
      param = provider.findOne(id);

      if (param != null) {
        addCacheItem(param);
      }
    }
    return param;
  }


  /**
   * 根据id, 查询当前操作问题；
   *
   * @param id
   * @return
   */
  @Override
  public AccountInfo findOneById(String id) {
    AccountInfo info = provider.findOneById(id);
    // 设置 角色
    List<Role> roleInfos = roleService.findAllByAccountId(id);
    if (roleInfos != null && roleInfos.size() > 0) {
      String[] roleText = new String[roleInfos.size()];
      String roleView = "";
      int size = roleInfos.size();
      for (int i = 0; i < size; i++) {
        if (i < roleInfos.size() - 1) {
          roleText[i] = roleInfos.get(i).getId() + ",";
          roleView = roleView + roleInfos.get(i).getName();
        } else {
          roleText[i] = roleInfos.get(i).getId();
          roleView = roleView + roleInfos.get(i).getName();
        }
      }
      info.setRoleText(roleText);
      info.setRoleView(roleView);
    }
    // 设置组织
    List<OrganizationUnitInfo> organizationUnitInfos = organizationUnitService.findAllByAccountId(id);
    if (organizationUnitInfos != null && organizationUnitInfos.size() > 0) {
      String[] organizationUnitText = new String[organizationUnitInfos.size()];
      String organizationUnitView = "";
      int size = organizationUnitInfos.size();
      for (int i = 0; i < size; i++) {
        organizationUnitText[i] = organizationUnitInfos.get(i).getId();
        if (i < organizationUnitInfos.size() - 1) {
          organizationUnitView = organizationUnitView + organizationUnitInfos.get(i).getName() + ",";
        } else {
          organizationUnitView = organizationUnitView + organizationUnitInfos.get(i).getName();
        }
      }
      info.setOrganizationUnitText(organizationUnitText);
      info.setOrganizationUnitView(organizationUnitView);
    }
    return info;
  }

  /**
   * 查询某条记录
   *
   * @param globalName 帐号的全局名称
   * @return 返回一个<see                                               cref                                                               =                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final Account findOneByGlobalName(String globalName) {
    return provider.findOneByGlobalName(globalName);
  }

  /**
   * 查询某条记录
   *
   * @param loginName 登陆名
   * @return 返回一个 Account 实例的详细信息
   */
  @Override
  public final Account findOneByLoginName(String loginName) {
    Account param = null;

    // 查找缓存数据
    if (dictionary.get("loginName").containsKey(loginName)) {
      param = dictionary.get("loginName").get(loginName);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (param == null) {
      param = provider.findOneByLoginName(loginName);

      if (param != null) {
        addCacheItem(param);
      }
    }

    return param;
  }

  /**
   * 根据已验证的手机号查询某条记录
   *
   * @param certifiedMobile 已验证的手机号
   * @return 返回一个<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final Account findOneByCertifiedMobile(String certifiedMobile) {
    return provider.findOneByCertifiedMobile(certifiedMobile);
  }

  /**
   * 根据已验证的邮箱地址查询某条记录
   *
   * @param certifiedEmail 已验证的邮箱地址
   * @return 返回一个<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final Account findOneByCertifiedEmail(String certifiedEmail) {
    return provider.findOneByCertifiedEmail(certifiedEmail);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 Account 实例的详细信息
   */
  @Override
  public final List<Account> findAll() {
    return provider.findAll(new HashMap());
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 Account 实例的详细信息
   */
/*  public final List<Account> findAll(String whereClause) {
    return this.provider.findAll(whereClause, 0);
  }*/
  @Override
  public List<Account> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  @Override
  public List<AccountInfo> queryAll(DataQuery query) {
    return provider.queryAll(query.getMap());
  }


  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有 Account 实例的详细信息
   */
  @Override
  public final List<Account> findAll(String whereClause, int length) {
    return provider.findAll(whereClause, length);
  }

  /**
   * 查询某个用户所在的所有组织单位
   *
   * @param organizationId 组织标识
   * @return 返回一个 IAccount 实例的详细信息
   */
  @Override
  public final List<Account> findAllByOrganizationUnitId(String organizationId) {
    return provider.findAllByOrganizationUnitId(organizationId);
  }

  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationId                  组织标识
   * @param defaultOrganizationUnitRelation 默认组织关系
   * @return 返回一个 IAccount 实例的详细信息
   */
  @Override
  public final List<Account> findAllByOrganizationUnitId(String organizationId, boolean defaultOrganizationUnitRelation) {
    return provider.findAllByOrganizationUnitId(organizationId, defaultOrganizationUnitRelation);
  }

  /**
   * 查询某个角色下的所有相关帐号
   *
   * @param roleId 角色标识
   * @return 返回一个 IAccount 实例的详细信息
   */
  @Override
  public final List<Account> findAllByRoleId(String roleId) {
    return provider.findAllByRoleId(roleId);
  }

  /**
   * ��ѯĳ��Ⱥ���µ����������ʺ�
   *
   * @param groupId Ⱥ����ʶ
   * @return ����һ�� IAccount ʵ������ϸ��Ϣ
   */
  @Override
  public final List<Account> findAllByGroupId(String groupId) {
    return provider.findAllByGroupId(groupId);
  }
  ///#endregion

  ///#region 函数:findAllWithoutMemberInfo(int length)

  /**
   * 返回所有没有成员信息的帐号信息
   *
   * @param length 条数, 0表示全部
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final List<Account> findAllWithoutMemberInfo(int length) {
    return provider.findAllWithoutMemberInfo(length);
  }
  ///#endregion

  ///#region 函数:findForwardLeaderAccountsByOrganizationUnitId(string organizationId)

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final List<Account> findForwardLeaderAccountsByOrganizationUnitId(String organizationId) {
    return provider.findForwardLeaderAccountsByOrganizationUnitId(organizationId, 1);
  }
  ///#endregion

  ///#region 函数:findForwardLeaderAccountsByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final List<Account> findForwardLeaderAccountsByOrganizationUnitId(String organizationId, int level) {
    return provider.findForwardLeaderAccountsByOrganizationUnitId(organizationId, level);
  }
  ///#endregion

  ///#region 函数:findBackwardLeaderAccountsByOrganizationUnitId(string organizationId)

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final List<Account> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId) {
    return provider.findBackwardLeaderAccountsByOrganizationUnitId(organizationId, 1);
  }
  ///#endregion

  ///#region 函数:findBackwardLeaderAccountsByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Account                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public final List<Account> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId, int level) {
    return provider.findBackwardLeaderAccountsByOrganizationUnitId(organizationId, level);
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
   * @param rowCount   记录行数
   * @return 返回一个列表
   */
  // public final List<Account> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 检测是否存在相关的记录.
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  @Override
  public final boolean isExist(String id) {
    return provider.isExist(id);
  }
  ///#endregion

  ///#region 函数:isExistLoginNameAndGlobalName(string loginName, string globalName)

  /**
   * 检测是否存在相关的记录
   *
   * @param loginName  登录名
   * @param globalName 姓名
   * @return 布尔值
   */
  @Override
  public final boolean isExistLoginNameAndGlobalName(String loginName, String globalName) {
    boolean result = provider.isExistLoginNameAndGlobalName(loginName, globalName);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("LoginName", loginName));

      if (!result) {
        result = Boolean.parseBoolean(isExistFieldValue("GlobalName", globalName));
      }
    }

    return result;
  }
  ///#endregion

  ///#region 函数:isExistLoginName(string loginName)

  /**
   * 检测是否存在相关的记录
   *
   * @param loginName 登录名
   * @return 布尔值
   */
  @Override
  public final boolean isExistLoginName(String accountId, String loginName) {
    boolean result = provider.isExistLoginName(loginName);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("LoginName", loginName));
    }
    if (result) {
      AccountInfo info = provider.findOneById(accountId);
      if (info != null && loginName.equals(info.getLoginName())) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 显示名称重复校验，不存在重复的校验 ；
   *
   * @param displayName 显示名称
   * @return
   */
  @Override
  public boolean isExistDisplayName(String accountId, String displayName) {
    boolean result = provider.isExistDisplayName(displayName);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("DisplayName", displayName));
    }
    if (result) {
      AccountInfo info = provider.findOneById(accountId);
      if (info != null && displayName.equals(info.getDisplayName())) {
        result = false;
      }
    }
    return result;
  }

  @Override
  public boolean isExistIdentityCard(String accountId, String identityCard) {
    boolean result = provider.isExistIdentityCard(identityCard);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("identityCard", identityCard));
    }
    if (result) {
      AccountInfo info = provider.findOneById(accountId);
      if (info != null && identityCard.equals(info.getIdentityCard())) {
        result = false;
      }
    }
    return result;
  }

  ///#endregion

  ///#region 函数:isExistName(string name)

  /**
   * 检测是否存在相关的记录
   *
   * @param name 姓名
   * @return 布尔值
   */
  @Override
  public final boolean isExistName(String name) {
    boolean result = provider.isExistName(name);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("Name", name));
    }

    return result;
  }
  ///#endregion

  ///#region 函数:isExistGlobalName(string globalName)

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  @Override
  public final boolean isExistGlobalName(String globalName) {
    boolean result = provider.isExistGlobalName(globalName);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("GlobalName", globalName));
    }

    return result;
  }
  ///#endregion

  ///#region 函数:isExistCertifiedMobile(string certifiedMobile)

  /**
   * 检测是否存在相关的手机号
   *
   * @param certifiedMobile 已验证的手机号
   * @return 布尔值
   */
  @Override
  public final boolean isExistCertifiedMobile(String accountId, String certifiedMobile) {
    boolean result = provider.isExistCertifiedMobile(certifiedMobile);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("CertifiedMobile", certifiedMobile));
    }
    if (result) {
      AccountInfo info = provider.findOneById(accountId);
      if (info != null && certifiedMobile.equals(info.getCertifiedMobile())) {
        result = false;
      }
    }
    return result;
  }
  ///#endregion

  ///#region 函数:isExistCertifiedEmail(string certifiedEmail)

  /**
   * 检测是否存在相关的邮箱
   *
   * @param certifiedEmail 已验证的邮箱
   * @return 布尔值
   */
  @Override
  public final boolean isExistCertifiedEmail(String certifiedEmail) {
    boolean result = provider.isExistCertifiedEmail(certifiedEmail);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("CertifiedEmail", certifiedEmail));
    }

    return result;
  }
  ///#endregion

  /**
   * @param certifiedEmail 验证邮箱是否存在
   * @return
   */
  @Override
  public boolean isExistCertifiedEmail(String accountId, String certifiedEmail) {
    boolean result = provider.existCertifiedEmail(certifiedEmail);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("DisplayName", certifiedEmail));
    }
    if (result && !StringUtil.isNullOrEmpty(accountId)) {
      result = Boolean.parseBoolean(isExistFieldValue("DisplayName", certifiedEmail));
    }
    if (result) {
      AccountInfo info = provider.findOneById(accountId);
      if (info != null && certifiedEmail.equals(info.getCertifiedEmail())) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 检测是否存在相关的字段的值
   *
   * @param fieldName  字段的名称
   * @param fieldValue 字段的值
   */
  public String isExistFieldValue(String fieldName, String fieldValue) {
    return "False";
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param id   帐号标识
   * @param name 帐号名称
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

  /**
   * 创建空的帐号信息
   *
   * @param accountId 帐号标识
   * @return
   */
  @Override
  public final Account createEmptyAccount(String accountId) {
    AccountInfo param = new AccountInfo();
    param.setId(accountId);
    param.setLocking(0);
    param.setStatus(-1);
    param.setModifiedDate(new Date());
    param.setCreatedDate(new Date());

    return param;
  }

  /**
   * 组合唯一名称
   *
   * @param name 帐号标识
   * @return
   */
  @Override
  public final String combineDistinguishedName(String name) {
    //CN=${姓名},OU=组织用户,DC=x3platform,DC=com

    // return String.format("CN=%1$s,OU=%2$s%3$s", name, LDAPConfigurationView.Instance.CorporationUserFolderRoot, LDAPConfigurationView.Instance.SuffixDistinguishedName);
    return String.format("CN=%1$s,OU=%2$s%3$s", name, "users", ",dc=x3platform,dc=com");
  }

  // -------------------------------------------------------
  // 管理员功能
  // -------------------------------------------------------

  /**
   * 设置全局名称
   *
   * @param accountId  帐户标识
   * @param globalName 全局名称
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setGlobalName(String accountId, String globalName) {
    if (isExistGlobalName(globalName)) {
      return 1;
    }

    // 检测是否存在对象
    if (!isExist(accountId)) {
      // 对象【${Id}】不存在。
      return 2;
    }
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号全局名称
      Account account = findOne(accountId);

      if (account != null && !StringUtil.isNullOrEmpty(account.LoginName)) {
        // 由于外部系统直接同步到人员及权限管理的数据库中，
        // 所以 Active Directory 上不会直接创建相关对象，需要手工设置全局名称并创建相关对象。

        if (LDAPManagement.Instance.User.isExistLoginName(account.LoginName)) {
          LDAPManagement.Instance.User.Rename(account.LoginName, globalName);
        } else {
          // 如果未创建相关帐号，则创建相关帐号。
          LDAPManagement.Instance.User.Add(account.LoginName, globalName, "", "");

          LDAPManagement.Instance.User.SetStatus(account.LoginName, account.Status == 1 ? true : false);

          // LDAP 添加用户到所有人组和所在的部门组

          LDAPManagement.Instance.Group.AddRelation(account.LoginName, LDAPSchemaClassType.User, "所有人");
        }
      }
    }
    */

    return provider.setGlobalName(accountId, globalName);
  }

  /**
   * 获取密码
   *
   * @param loginName 帐号
   */
  @Override
  public final String getPassword(String loginName) {
    return provider.getPassword(loginName);
  }

  /**
   * 获取密码强度
   *
   * @param loginName 帐号
   * @return 0 正常强度密码 | 1 低强度密码 | 2 小于最小密码长度的密码 | 3 纯数字组成的密码 | 9 默认密码
   */
  @Override
  public final int getPasswordStrength(String loginName) {
    String password = getPassword(loginName);

    // TODO 待处理
    // 如果系统采用的密码加密方式是不可逆的密码, 此方法无效.
    // password = MembershipManagement.getInstance().getPasswordEncryptionManagement().Decrypt(password);

    return validatePasswordPolicy(password);
  }

  /**
   * 获取密码更新时间
   *
   * @param loginName 帐号
   */
  @Override
  public final Date getPasswordChangedDate(String loginName) {
    return provider.getPasswordChangedDate(loginName);
  }

  /**
   * 设置帐号密码.(管理员)
   *
   * @param accountId 编号
   * @param password  密码
   * @return 修改成功, 返回 0, 旧密码不匹配, 返回 1.
   */
  @Override
  public final int setPassword(String accountId, String password) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号状态
      Account account = findOne(accountId);

      if (account != null && !StringUtil.isNullOrEmpty(account.LoginName) && !StringUtil.isNullOrEmpty(password)) {
        LDAPManagement.Instance.User.SetPassword(account.LoginName, password);
      }
    }
    */
    return provider.setPassword(accountId, password);
  }

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param loginName 登录名
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setLoginName(String accountId, String loginName) {
    return provider.setLoginName(accountId, loginName);
  }

  /**
   * 设置已验证的联系电话
   *
   * @param accountId 帐户标识
   * @param telephone 联系电话
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setCertifiedMobile(String accountId, String telephone) {
    return provider.setCertifiedMobile(accountId, telephone);
  }

  /**
   * 设置已验证的邮箱
   *
   * @param accountId 帐户标识
   * @param email     邮箱
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setCertifiedEmail(String accountId, String email) {
    return provider.setCertifiedEmail(accountId, email);
  }

  /**
   * 设置已验证的头像
   *
   * @param accountId         帐户标识
   * @param avatarVirtualPath 头像的虚拟路径
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setCertifiedAvatar(String accountId, String avatarVirtualPath) {
    return provider.setCertifiedAvatar(accountId, avatarVirtualPath);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public final int setExchangeStatus(String accountId, int status) {
    return provider.setExchangeStatus(accountId, status);
  }

  /**
   * 设置帐号锁定状态
   *
   * @param accountId 账户Id
   * @param locking
   */
  @Override
  public int setLocking(String accountId, int locking) {
    return provider.setLocking(accountId, locking);
  }

  /**
   * 设置帐号状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setStatus(String accountId, String CreatedBy, int status) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号状态
      Account account = findOne(accountId);

      if (account != null && !StringUtil.isNullOrEmpty(account.LoginName)) {
        LDAPManagement.Instance.User.SetStatus(account.LoginName, status == 1 ? true : false);
      }
    }
    */
    return provider.setStatus(accountId, CreatedBy, status);
  }

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param ip        登录IP
   * @param loginDate 登录时间
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public final int setIPAndLoginDate(String accountId, String ip, Date loginDate) {
    return provider.setIPAndLoginDate(accountId, ip, loginDate);
  }


  /**
   * 设置修改人
   *
   * @param accountId 帐户标识
   * @param ip        登录IP
   * @return 操作人姓名
   */
  @Override
  public final int setCreatedBy(String accountId, String createdBy) {
    return provider.setCreatedBy(accountId, createdBy);
  }
  // -------------------------------------------------------
  // 普通用户功能
  // -------------------------------------------------------

  /**
   * 验证密码是否符合密码策略
   *
   * @param password 密码
   * @return 0 表示成功 1
   */
  @Override
  public final int validatePasswordPolicy(String password) {
    byte[] buffer = password.getBytes();

    String passwordPolicyRules = MembershipConfigurationView.getInstance().getPasswordPolicyRules();
    int passwordPolicyMinimumLength = MembershipConfigurationView.getInstance().getPasswordPolicyMinimumLength();
    int passwordPolicyCharacterRepeatedTimes = MembershipConfigurationView.getInstance().getPasswordPolicyCharacterRepeatedTimes();

    boolean flag = false;
    int charCode = -1;

    if (passwordPolicyRules.indexOf("[Number]") > -1) {
      flag = false;
      charCode = -1;

      // charCode 48 - 57
      for (int i = 0; i < buffer.length; i++) {
        charCode = buffer[i];

        if (charCode >= 48 && charCode <= 57) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        // 2 必须包含一个【0～9】数字。
        return 2;
      }
    }

    if (passwordPolicyRules.indexOf("[Character]") > -1) {
      flag = false;
      charCode = -1;

      for (int i = 0; i < buffer.length; i++) {
        charCode = buffer[i];

        if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        // 3 必须包含一个【A～Z或a～z】字符。
        return 3;
      }
    }

    if (passwordPolicyRules.indexOf("[SpecialCharacter]") > -1) {
      flag = false;
      charCode = -1;

      // ! 33 # 35 $ 36 @ 64
      for (int i = 0; i < buffer.length; i++) {
        charCode = buffer[i];

        if (charCode == 33 || charCode == 35 || charCode == 36 || charCode == 64) {
          flag = true;
          break;
        }
      }

      if (!flag) {
        // 4 必须包含一个【# $ @ !】特殊字符。
        return 4;
      }
    }

    if (passwordPolicyMinimumLength > 0 && buffer.length < passwordPolicyMinimumLength) {
      // 5 密码长度必须大于【' + passwordPolicyMinimumLength + '】'。
      return 5;
    }

    if (passwordPolicyCharacterRepeatedTimes > 1 && buffer.length > passwordPolicyCharacterRepeatedTimes) {
      // 判断字符连续出现的次数
      int repeatedTimes = 1;

      for (int i = 0; i < buffer.length - passwordPolicyCharacterRepeatedTimes; i++) {
        charCode = buffer[i];

        repeatedTimes = 1;

        for (int j = 1; j < passwordPolicyCharacterRepeatedTimes; j++) {
          if (charCode == buffer[i + j]) {
            repeatedTimes++;
          } else {
            break;
          }
        }

        if (repeatedTimes >= passwordPolicyCharacterRepeatedTimes) {
          // 在密码中相邻字符重复次数不能超过【' + passwordPolicyCharacterRepeatedTimes + '】次。;
          return 6;
        }
      }
    }

    return 0;
  }

  /**
   * 确认密码
   *
   * @param accountId    帐号唯一标识
   * @param passwordType 密码类型: default 默认, query 查询密码, trader 交易密码
   * @param password     密码
   * @return 返回值: 0 成功 | 1 失败
   */
  @Override
  public final int confirmPassword(String accountId, String passwordType, String password) {
    return provider.confirmPassword(accountId, passwordType, password);
  }

  /**
   * 登陆检测
   *
   * @param loginName 帐号
   * @param password  密码
   * @return Account 实例
   */
  @Override
  public final Account loginCheck(String loginName, String password) {
    return provider.loginCheck(loginName, password);
  }

  /**
   * 修改基本信息
   *
   * @param param IAccount 实例的详细信息
   */
  @Override
  public final void changeBasicInfo(Account param) {
    provider.changeBasicInfo(param);
  }

  /**
   * 修改密码
   *
   * @param loginName        编号
   * @param password         新密码
   * @param originalPassword 原始密码
   * @return 旧密码不匹配，返回0.
   */
  @Override
  public final int changePassword(String loginName, String password, String originalPassword) {
    int result = provider.changePassword(loginName, password, originalPassword);
    /*
    if (result == 0 && LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号状态
      Account account = this.findOneByLoginName(loginName);

      if (account != null && !StringUtil.isNullOrEmpty(account.LoginName) && !StringUtil.isNullOrEmpty(password)) {
        LDAPManagement.Instance.User.SetPassword(account.LoginName, password);
      }
    }
    */
    return result;
  }

  /**
   * 刷新帐号的更新时间
   *
   * @param accountId 帐户标识
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public final int refreshModifiedDate(String accountId) {
    return provider.refreshModifiedDate(accountId);
  }

  /**
   * 获取帐号相关的权限对象
   *
   * @param account IAccount 实例的详细信息
   */
  // public final List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(Account account) {
  //  return this.provider.GetAuthorizationScopeObjects(account);
  // }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 帐号信息
   */
  @Override
  public final int syncToLDAP(Account param) {
    return syncToLDAP(param, param.getGlobalName(), param.getStatus());
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param              帐号信息
   * @param originalGlobalName 原始全局名称
   * @param originalStatus     原始状态
   */
  public final int syncToLDAP(Account param, String originalGlobalName, int originalStatus) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      if (StringUtil.isNullOrEmpty(param.LoginName)) {
        // 用户【${Name}(${LoginName})】登录名为空，请配置相关信息。
        return 1;
      } else if (StringUtil.isNullOrEmpty(param.GlobalName)) {
        // 用户【${Name}(${LoginName})】全局名称为空，请配置相关信息。
        return 2;
      } else {
        // 1.原始的全局名称和登录名都不为空。
        // 2.Active Directory 上有相关对象。
        if (!(StringUtil.isNullOrEmpty(originalGlobalName) || StringUtil.isNullOrEmpty(param.LoginName)) && LDAPManagement.Instance.User.isExistLoginName(param.LoginName)) {
          // 如果已存在相关帐号，同步全局名称和帐号状态。

          if (!originalGlobalName.equals(param.GlobalName)) {
            LDAPManagement.Instance.User.Rename(param.LoginName, param.GlobalName);
          }

          LDAPManagement.Instance.User.SetStatus(param.LoginName, param.Status == 1 ? true : false);
        } else {
          if (LDAPManagement.Instance.User.isExist(param.LoginName, param.GlobalName)) {
            // "用户【${Name}(${LoginName})】的全局名称已被其他人创建，请设置相关配置。
            return 3;
          } else if (param.Status == 0) {
            // "用户【${Name}(${LoginName})】的帐号为【禁用】状态，如果需要创建 Active Directory 帐号，请设置相关配置。
            return 4;
          } else {
            // 如果未创建相关帐号，则创建相关帐号。
            LDAPManagement.Instance.User.Add(param.LoginName, param.GlobalName, "", "");

            LDAPManagement.Instance.User.SetStatus(param.LoginName, param.Status == 1 ? true : false);

            // LDAP 添加用户到所有人组和所在的部门组

            LDAPManagement.Instance.Group.AddRelation(param.LoginName, LDAPSchemaClassType.User, "所有人");

            // "用户【${Name}(${LoginName})】创建成功。
            return 0;
          }
        }
      }
    }
    */
    return 0;
  }
  ///#endregion

  // -------------------------------------------------------
  // 同步管理
  // -------------------------------------------------------

  /**
   * 同步信息
   *
   * @param param 帐号信息
   */
  public final int syncFromPackPage(Account param) {
    return provider.syncFromPackPage(param);
  }


  /**
   * @param authType        isAdministrator;isReviewer;isMember
   * @param applicationName 配置应用名称 （必填）
   * @param selectKey       ‘搜索框输入的值’
   * @return
   */
  @Override
  public List<Account> findAllAccountByAccountName(String authType, String applicationName, String selectKey) {
    List<Account> accountInfoList = new ArrayList<>();
    //  isAdministrator;isReviewer;isMember 判断是否选中
    if (authType.equals("isAdministrator") || authType.equals("isReviewer") || authType.equals("isMember")) {
      accountInfoList = provider.findAllAccountByAccountName(authType, applicationName, selectKey);
    }
    return accountInfoList;
  }

  /**
   * 查询当前系统默认密码， 当为空的时候，设置平台设置默认密码 ；
   *
   * @param applicationKey 应用key ,
   */
  @Override
  public String findDefaultPassword(String applicationKey) {
    return provider.findDefaultPassword(applicationKey);
  }
}
