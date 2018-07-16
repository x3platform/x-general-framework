package com.x3platform.membership.services.impl;

import com.x3platform.membership.IAccountGroupRelationInfo;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.IAccountRoleRelationInfo;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.configuration.MembershipConfiguration;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.membership.mappers.AccountMapper;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.services.IAccountService;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 帐号服务
 */
@Service
public class AccountService implements IAccountService {

  /**
   * 数据提供器
   */
  @Autowired
  private AccountMapper provider = null;

  /**
   * 缓存存储
   */
  private Map<String, Map<String, IAccountInfo>> dictionary = new HashMap<String, Map<String, IAccountInfo>>();
  // private Map<String, Map<String, IAccountInfo>> dictionary = new HashMap<String, Map<String, IAccountInfo>>() {
  //  {
  //    "id", new SyncDictionary<String, IAccountInfo>()
  //  },
  //
  //  {
  //    "loginName", new SyncDictionary<String, IAccountInfo>()
  //  }
  // };

  ///#region 构造函数:AccountService()

  /**
   * 构造函数
   */
  // public AccountService() {
  //  this.configuration = MembershipConfigurationView.Instance.Configuration;

  // 创建对象构建器(Spring.NET)
  // String springObjectFile = this.configuration.keySet()["SpringObjectFile"].Value;

  // SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(MembershipConfiguration.ApplicationName, springObjectFile);

  // 创建数据提供器
  // this.provider = objectBuilder.<IAccountProvider>GetObject(IAccountProvider.class);
  //}
  ///#endregion

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * @return
   */
  public final int CreateCache() {
    this.dictionary = new HashMap<String, Map<String, IAccountInfo>>();
    this.dictionary.put("id", new HashMap<String, IAccountInfo>());
    this.dictionary.put("loginName", new HashMap<String, IAccountInfo>());

    return 0;
  }

  /**
   * @return
   */
  public final int ClearCache() {
    this.dictionary.get("id").clear();
    this.dictionary.get("loginName").clear();

    return 0;
  }

  /**
   * @return
   */
  public final void AddCacheItem(Object item) {
    if (item instanceof IAccountInfo) {
      this.AddCacheItem((IAccountInfo) item);
    }
  }

  private void AddCacheItem(IAccountInfo item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      if (this.dictionary.get("id").containsKey(item.getId())) {
        this.dictionary.get("id").replace(item.getId(), item);
      } else {
        this.dictionary.get("id").put(item.getId(), item);
      }
    }

    if (!StringUtil.isNullOrEmpty(item.getLoginName())) {
      if (this.dictionary.get("loginName").containsKey(item.getLoginName())) {
        this.dictionary.get("loginName").replace(item.getLoginName(), item);
      } else {
        this.dictionary.get("loginName").put(item.getLoginName(), item);
      }
    }
  }

  /**
   * @return
   */
  public final void RemoveCacheItem(Object item) {
    if (item instanceof IAccountInfo) {
      this.RemoveCacheItem((IAccountInfo) item);
    }
  }

  private void RemoveCacheItem(IAccountInfo item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      if (this.dictionary.get("id").containsKey(item.getId())) {
        this.dictionary.get("id").remove(item.getId());
      }
    }

    if (!StringUtil.isNullOrEmpty(item.getLoginName())) {
      if (this.dictionary.get("loginName").containsKey(item.getLoginName())) {
        this.dictionary.get("loginName").remove(item.getLoginName());
      }
    }
  }

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param IAccountInfo 实例详细信息
   * @return IAccountInfo 实例详细信息
   */
  public final IAccountInfo save(IAccountInfo param) {
    if (StringUtil.isNullOrEmpty(param.getId())) {
      throw new RuntimeException("实例标识不能为空。");
    }
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      IAccountInfo originalObject = this.findOne(param.Id);

      if (originalObject == null) {
        originalObject = param;
      }

      this.SyncToLDAP(param, originalObject.GlobalName, originalObject.Status);
    }
    */
    param.setDistinguishedName(combineDistinguishedName(param.getName()));

    param = this.provider.save(param);
    if (param != null) {
      String accountId = param.getId();

      // 绑定新的关系
      if (!StringUtil.isNullOrEmpty(accountId)) {
        // -------------------------------------------------------
        // 设置角色关系
        // -------------------------------------------------------

        // TODO 待处理
        /*
        // 1.移除非默认角色关系
        MembershipManagement.getInstance().getRoleService().RemoveNondefaultRelation(accountId);

        // -------------------------------------------------------
        // 根据角色设置组织关系
        // -------------------------------------------------------

        // 1.移除非默认角色关系
        MembershipManagement.getInstance().OrganizationUnitService.RemoveNondefaultRelation(accountId);

        // 2.设置新的关系
        for (IAccountRoleRelationInfo item : param.RoleRelations) {
          MembershipManagement.getInstance().RoleService.AddRelation(accountId, item.RoleId);

          MembershipManagement.getInstance().OrganizationUnitService.AddRelation(accountId, item.GetRole().OrganizationUnitId);

          MembershipManagement.getInstance().OrganizationUnitService.AddParentRelations(accountId, item.GetRole().OrganizationUnitId);
        }

        // -------------------------------------------------------
        // 设置群组关系
        // -------------------------------------------------------

        // 1.移除群组关系
        MembershipManagement.getInstance().GroupService.RemoveAllRelation(accountId);

        // 2.设置新的关系
        for (IAccountGroupRelationInfo item : param.GroupRelations) {
          MembershipManagement.getInstance().GroupService.AddRelation(accountId, item.GroupId);
        }
        */
      }
    }

    // 保存数据后, 更新缓存信息
    param = this.provider.findOne(param.getId());

    if (param != null) {
      this.RemoveCacheItem(param);

      this.AddCacheItem(param);
    }

    return param;
  }

  /**
   * 删除记录
   *
   * @param id 帐号标识
   */
  public final void delete(String id) {
    IAccountInfo originalObject = this.findOne(id);

    // 删除缓存
    if (originalObject != null) {
      this.RemoveCacheItem(originalObject);
    }

    // 删除数据库记录
    this.provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id AccountInfo Id号
   * @return 返回一个 IAccountInfo 实例的详细信息
   */
  public final IAccountInfo findOne(String id) {
    IAccountInfo param = null;

    // 查找缓存数据
    if (this.dictionary.get("id").containsKey(id)) {
      param = this.dictionary.get("id").get(id);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (param == null) {
      param = this.provider.findOne(id);

      if (param != null) {
        this.AddCacheItem(param);
      }
    }

    return param;
  }

  /**
   * 查询某条记录
   *
   * @param globalName 帐号的全局名称
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  public final IAccountInfo findOneByGlobalName(String globalName) {
    return this.provider.findOneByGlobalName(globalName);
  }

  /**
   * 查询某条记录
   *
   * @param loginName 登陆名
   * @return 返回一个 IAccountInfo 实例的详细信息
   */
  public final IAccountInfo findOneByLoginName(String loginName) {
    IAccountInfo param = null;

    // 查找缓存数据
    if (this.dictionary.get("loginName").containsKey(loginName)) {
      param = this.dictionary.get("loginName").get(loginName);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (param == null) {
      param = this.provider.findOneByLoginName(loginName);

      if (param != null) {
        this.AddCacheItem(param);
      }
    }

    return param;
  }

  /**
   * 根据已验证的手机号查询某条记录
   *
   * @param certifiedMobile 已验证的手机号
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  public final IAccountInfo findOneByCertifiedMobile(String certifiedMobile) {
    return this.provider.findOneByCertifiedMobile(certifiedMobile);
  }

  /**
   * 根据已验证的邮箱地址查询某条记录
   *
   * @param certifiedEmail 已验证的邮箱地址
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  public final IAccountInfo findOneByCertifiedEmail(String certifiedEmail) {
    return this.provider.findOneByCertifiedEmail(certifiedEmail);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 IAccountInfo 实例的详细信息
   */
  public final List<IAccountInfo> findAll() {
    return this.provider.findAll("", 0);
  }

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有 IAccountInfo 实例的详细信息
   */
  public final List<IAccountInfo> findAll(String whereClause) {
    return this.provider.findAll(whereClause, 0);
  }
  ///#endregion

  ///#region 函数:findAll(string whereClause,int length)

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有 IAccountInfo 实例的详细信息
   */
  public final List<IAccountInfo> findAll(String whereClause, int length) {
    return this.provider.findAll(whereClause, length);
  }
  ///#endregion

  ///#region 函数:findAllByOrganizationUnitId(string organizationId)

  /**
   * 查询某个用户所在的所有组织单位
   *
   * @param organizationId 组织标识
   * @return 返回一个 IIAccountInfo 实例的详细信息
   */
  public final List<IAccountInfo> findAllByOrganizationUnitId(String organizationId) {
    return this.provider.findAllByOrganizationUnitId(organizationId);
  }
  ///#endregion

  ///#region 函数:findAllByOrganizationUnitId(string organizationId, bool defaultOrganizationUnitRelation)

  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationId                  组织标识
   * @param defaultOrganizationUnitRelation 默认组织关系
   * @return 返回一个 IIAccountInfo 实例的详细信息
   */
  public final List<IAccountInfo> findAllByOrganizationUnitId(String organizationId, boolean defaultOrganizationUnitRelation) {
    return this.provider.findAllByOrganizationUnitId(organizationId, defaultOrganizationUnitRelation);
  }
  ///#endregion

  ///#region 函数:findAllByRoleId(string roleId)

  /**
   * 查询某个角色下的所有相关帐号
   *
   * @param roleId 角色标识
   * @return 返回一个 IIAccountInfo 实例的详细信息
   */
  public final List<IAccountInfo> findAllByRoleId(String roleId) {
    return this.provider.findAllByRoleId(roleId);
  }
  ///#endregion

  ///#region 属性:findAllByGroupId(string groupId)

  /**
   * ��ѯĳ��Ⱥ���µ����������ʺ�
   *
   * @param groupId Ⱥ����ʶ
   * @return ����һ�� IIAccountInfo ʵ������ϸ��Ϣ
   */
  public final List<IAccountInfo> findAllByGroupId(String groupId) {
    return this.provider.findAllByGroupId(groupId);
  }
  ///#endregion

  ///#region 函数:findAllWithoutMemberInfo(int length)

  /**
   * 返回所有没有成员信息的帐号信息
   *
   * @param length 条数, 0表示全部
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  public final List<IAccountInfo> findAllWithoutMemberInfo(int length) {
    return this.provider.findAllWithoutMemberInfo(length);
  }
  ///#endregion

  ///#region 函数:findForwardLeaderAccountsByOrganizationUnitId(string organizationId)

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  public final List<IAccountInfo> findForwardLeaderAccountsByOrganizationUnitId(String organizationId) {
    return this.provider.findForwardLeaderAccountsByOrganizationUnitId(organizationId, 1);
  }
  ///#endregion

  ///#region 函数:findForwardLeaderAccountsByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  public final List<IAccountInfo> findForwardLeaderAccountsByOrganizationUnitId(String organizationId, int level) {
    return this.provider.findForwardLeaderAccountsByOrganizationUnitId(organizationId, level);
  }
  ///#endregion

  ///#region 函数:findBackwardLeaderAccountsByOrganizationUnitId(string organizationId)

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  public final List<IAccountInfo> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId) {
    return this.provider.findBackwardLeaderAccountsByOrganizationUnitId(organizationId, 1);
  }
  ///#endregion

  ///#region 函数:findBackwardLeaderAccountsByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  public final List<IAccountInfo> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId, int level) {
    return this.provider.findBackwardLeaderAccountsByOrganizationUnitId(organizationId, level);
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
  // public final List<IAccountInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 检测是否存在相关的记录.
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  public final boolean isExist(String id) {
    return this.provider.isExist(id);
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
  public final boolean isExistLoginNameAndGlobalName(String loginName, String globalName) {
    boolean result = this.provider.isExistLoginNameAndGlobalName(loginName, globalName);

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
  public final boolean isExistLoginName(String loginName) {
    boolean result = this.provider.isExistLoginName(loginName);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("LoginName", loginName));
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
  public final boolean isExistName(String name) {
    boolean result = this.provider.isExistName(name);

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
  public final boolean isExistGlobalName(String globalName) {
    boolean result = this.provider.isExistGlobalName(globalName);

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
  public final boolean isExistCertifiedMobile(String certifiedMobile) {
    boolean result = this.provider.isExistCertifiedMobile(certifiedMobile);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("CertifiedMobile", certifiedMobile));
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
  public final boolean isExistCertifiedEmail(String certifiedEmail) {
    boolean result = this.provider.isExistCertifiedEmail(certifiedEmail);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("CertifiedEmail", certifiedEmail));
    }

    return result;
  }
  ///#endregion

  ///#region 函数:isExistFieldValue(string fieldName, string fieldValue)

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
  public final int rename(String id, String name) {
    // 检测是否存在对象
    if (!isExist(id)) {
      // 不存在对象
      return 1;
    }

    return this.provider.rename(id, name);
  }

  /**
   * 创建空的帐号信息
   *
   * @param accountId 帐号标识
   * @return
   */
  public final IAccountInfo createEmptyAccount(String accountId) {
    AccountInfo param = new AccountInfo();

    param.setId(accountId);
    param.setLocking(0);
    param.setStatus(-1);
    param.setModifiedDate(java.time.LocalDateTime.now());
    param.setCreatedDate(java.time.LocalDateTime.now());

    return param;
  }

  /**
   * 组合唯一名称
   *
   * @param name 帐号标识
   * @return
   */
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
      IAccountInfo account = findOne(accountId);

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

    return this.provider.setGlobalName(accountId, globalName);
  }

  /**
   * 获取密码
   *
   * @param loginName 帐号
   */
  public final String getPassword(String loginName) {
    return this.provider.getPassword(loginName);
  }

  /**
   * 获取密码强度
   *
   * @param loginName 帐号
   * @return 0 正常强度密码 | 1 低强度密码 | 2 小于最小密码长度的密码 | 3 纯数字组成的密码 | 9 默认密码
   */
  public final int getPasswordStrength(String loginName) {
    String password = this.getPassword(loginName);

    // TODO 待处理
    // 如果系统采用的密码加密方式是不可逆的密码, 此方法无效.
    // password = MembershipManagement.getInstance().getPasswordEncryptionManagement().Decrypt(password);

    return this.validatePasswordPolicy(password);
  }

  /**
   * 获取密码更新时间
   *
   * @param loginName 帐号
   */
  public final java.time.LocalDateTime getPasswordChangedDate(String loginName) {
    return this.provider.getPasswordChangedDate(loginName);
  }

  /**
   * 设置帐号密码.(管理员)
   *
   * @param accountId 编号
   * @param password  密码
   * @return 修改成功, 返回 0, 旧密码不匹配, 返回 1.
   */
  public final int setPassword(String accountId, String password) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号状态
      IAccountInfo account = findOne(accountId);

      if (account != null && !StringUtil.isNullOrEmpty(account.LoginName) && !StringUtil.isNullOrEmpty(password)) {
        LDAPManagement.Instance.User.SetPassword(account.LoginName, password);
      }
    }
    */
    return this.provider.setPassword(accountId, password);
  }

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param loginName 登录名
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setLoginName(String accountId, String loginName) {
    return this.provider.setLoginName(accountId, loginName);
  }

  /**
   * 设置已验证的联系电话
   *
   * @param accountId 帐户标识
   * @param telephone 联系电话
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setCertifiedMobile(String accountId, String telephone) {
    return this.provider.setCertifiedMobile(accountId, telephone);
  }

  /**
   * 设置已验证的邮箱
   *
   * @param accountId 帐户标识
   * @param email     邮箱
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setCertifiedEmail(String accountId, String email) {
    return this.provider.setCertifiedEmail(accountId, email);
  }

  /**
   * 设置已验证的头像
   *
   * @param accountId         帐户标识
   * @param avatarVirtualPath 头像的虚拟路径
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setCertifiedAvatar(String accountId, String avatarVirtualPath) {
    return this.provider.setCertifiedAvatar(accountId, avatarVirtualPath);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  public final int setExchangeStatus(String accountId, int status) {
    return this.provider.setExchangeStatus(accountId, status);
  }

  /**
   * 设置帐号状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setStatus(String accountId, int status) {
    /*
    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号状态
      IAccountInfo account = findOne(accountId);

      if (account != null && !StringUtil.isNullOrEmpty(account.LoginName)) {
        LDAPManagement.Instance.User.SetStatus(account.LoginName, status == 1 ? true : false);
      }
    }
    */
    return this.provider.setStatus(accountId, status);
  }

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param ip        登录IP
   * @param loginDate 登录时间
   * @return 0 操作成功 | 1 操作失败
   */
  public final int setIPAndLoginDate(String accountId, String ip, java.time.LocalDateTime loginDate) {
    return this.provider.setIPAndLoginDate(accountId, ip, loginDate);
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
  public final int confirmPassword(String accountId, String passwordType, String password) {
    return this.provider.confirmPassword(accountId, passwordType, password);
  }

  /**
   * 登陆检测
   *
   * @param loginName 帐号
   * @param password  密码
   * @return IAccountInfo 实例
   */
  public final IAccountInfo loginCheck(String loginName, String password) {
    return this.provider.loginCheck(loginName, password);
  }

  /**
   * 修改基本信息
   *
   * @param param IAccount 实例的详细信息
   */
  public final void changeBasicInfo(IAccountInfo param) {
    this.provider.changeBasicInfo(param);
  }

  /**
   * 修改密码
   *
   * @param loginName        编号
   * @param password         新密码
   * @param originalPassword 原始密码
   * @return 旧密码不匹配，返回0.
   */
  public final int changePassword(String loginName, String password, String originalPassword) {
    int result = this.provider.changePassword(loginName, password, originalPassword);
    /*
    if (result == 0 && LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
      // 同步 Active Directory 帐号状态
      IAccountInfo account = this.findOneByLoginName(loginName);

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
  public final int refreshModifiedDate(String accountId) {
    return this.provider.refreshModifiedDate(accountId);
  }

  /**
   * 获取帐号相关的权限对象
   *
   * @param account IAccount 实例的详细信息
   */
  // public final List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(IAccountInfo account) {
  //  return this.provider.GetAuthorizationScopeObjects(account);
  // }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 帐号信息
   */
  public final int syncToLDAP(IAccountInfo param) {
    return syncToLDAP(param, param.getGlobalName(), param.getStatus());
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param              帐号信息
   * @param originalGlobalName 原始全局名称
   * @param originalStatus     原始状态
   */
  public final int syncToLDAP(IAccountInfo param, String originalGlobalName, int originalStatus) {
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
  public final int syncFromPackPage(IAccountInfo param) {
    return this.provider.syncFromPackPage(param);
  }
}
