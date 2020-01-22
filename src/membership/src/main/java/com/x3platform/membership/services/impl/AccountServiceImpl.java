package com.x3platform.membership.services.impl;

import static com.x3platform.Constants.TEXT_NUMBERS;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.ldap.configuration.LdapConfigurationView;
import com.x3platform.membership.Account;
import com.x3platform.membership.AccountGroupRelation;
import com.x3platform.membership.AccountRoleRelation;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.membership.mappers.AccountMapper;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.membership.services.AccountService;
import com.x3platform.membership.services.OrganizationUnitService;
import com.x3platform.membership.services.RoleService;
import com.x3platform.security.Encrypter;
import com.x3platform.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 帐号服务
 *
 * @author ruanyu
 */
public class AccountServiceImpl implements AccountService {

  private static final String CACHE_KEY_ID_PREFIX = "x3platform:membership:account:id:";
  private static final String CACHE_KEY_LOGIN_NAME_PREFIX = "x3platform:membership:account:loginName:";

  /**
   * 双因素密码 trader 查询类型密码类型 适用于保密信息的查询
   *
   * query 交易类型密码类型 适用与资金处理
   */
  private static final String PASSWORD_TYPE_TWO_FACTOR = "two-factor";

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

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(Account item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item);
    }

    if (!StringUtil.isNullOrEmpty(item.getLoginName())) {
      String key = CACHE_KEY_LOGIN_NAME_PREFIX + item.getLoginName();
      CachingManager.set(key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(Account item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }

    if (!StringUtil.isNullOrEmpty(item.getLoginName())) {
      String key = CACHE_KEY_LOGIN_NAME_PREFIX + item.getLoginName();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }
  }

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 保存帐号信息
   */
  @Override
  public Account save(Account entity) throws Exception {
    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new Exception("标识不能为空");
    }

    // 根据是否存在的对象，判断是否新建对象
    boolean isNewObject = !provider.isExist(entity.getId());

    entity.setDistinguishedName(combineDistinguishedName(entity.getDisplayName()));

    if (isNewObject) {
      entity.setPasswordSalt(StringUtil.toRandom(TEXT_NUMBERS, 6));
      entity.setModifiedDate(new Date());
      entity.setCreatedDate(new Date());
      provider.insert(entity);
    } else {
      entity.setModifiedDate(new Date());
      provider.update(entity);
    }

    if (entity != null) {
      String accountId = entity.getId();

      // 绑定新的关系
      if (!StringUtil.isNullOrEmpty(accountId)) {
        // -------------------------------------------------------
        // 设置角色关系
        // -------------------------------------------------------

        // 1.移除非默认角色关系
        MembershipManagement.getInstance().getRoleService().removeNondefaultRelation(accountId);

        // -------------------------------------------------------
        // 根据角色设置组织关系
        // -------------------------------------------------------

        // 1.移除非默认角色关系
        MembershipManagement.getInstance().getOrganizationUnitService().removeNondefaultRelation(accountId);

        // 2.设置新的关系
        for (AccountRoleRelation item : entity.getRoleRelations()) {
          MembershipManagement.getInstance().getRoleService().addRelation(accountId, item.getRoleId());

          MembershipManagement.getInstance().getOrganizationUnitService()
            .addRelation(accountId, item.getRole().getOrganizationUnitId());

          MembershipManagement.getInstance().getOrganizationUnitService()
            .addParentRelations(accountId, item.getRole().getOrganizationUnitId());
        }

        // -------------------------------------------------------
        // 设置群组关系
        // -------------------------------------------------------

        // 1.移除群组关系
        MembershipManagement.getInstance().getGroupService().removeAllRelation(accountId);

        // 2.设置新的关系
        for (AccountGroupRelation item : entity.getGroupRelations()) {
          MembershipManagement.getInstance().getGroupService().addRelation(accountId, item.getGroupId());
        }
      }
    }

    // 保存数据后, 更新缓存信息
    entity = provider.findOne(entity.getId());

    if (entity != null) {
      removeCacheItem(entity);

      addCacheItem(entity);
    }

    // 设置默认密码
    if (isNewObject) {
      setPassword(entity.getId(), MembershipConfigurationView.getInstance().getDefaultPassword());
    }

    return entity;
  }

  /**
   * 删除记录
   *
   * @param id 帐号标识
   */
  @Override
  public void delete(String id) {
    Account originalObject = findOne(id);

    // 删除缓存
    if (originalObject != null) {
      removeCacheItem(originalObject);
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
   * @param id Account Id号
   * @return 返回一个{@link Account} 实例的详细信息
   */
  @Override
  public Account findOne(String id) {
    Account entity = null;

    // 查找缓存数据
    String key = CACHE_KEY_ID_PREFIX + id;
    if (CachingManager.contains(key)) {
      entity = (Account) CachingManager.get(key);
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
   * @param globalName 帐号的全局名称
   * @return 返回一个 {@link Account} 实例的详细信息
   */
  @Override
  public Account findOneByGlobalName(String globalName) {
    return provider.findOneByGlobalName(globalName);
  }

  /**
   * 查询某条记录
   *
   * @param loginName 登陆名
   * @return 返回一个 {@link Account} 实例的详细信息
   */
  @Override
  public Account findOneByLoginName(String loginName) {
    Account entity = null;

    // 查找缓存数据
    String key = CACHE_KEY_LOGIN_NAME_PREFIX + loginName;
    if (CachingManager.contains(key)) {
      entity = (Account) CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (entity == null) {
      entity = provider.findOneByLoginName(loginName);

      if (entity != null) {
        addCacheItem(entity);
      }
    }

    return entity;
  }

  /**
   * 根据已验证的手机号查询某条记录
   *
   * @param certifiedMobile 已验证的手机号
   * @return 返回一个{@link Account}实例的详细信息
   */
  @Override
  public Account findOneByCertifiedMobile(String certifiedMobile) {
    return provider.findOneByCertifiedMobile(certifiedMobile);
  }

  /**
   * 根据已验证的邮箱地址查询某条记录
   *
   * @param certifiedEmail 已验证的邮箱地址
   * @return 返回一个{@link Account}实例的详细信息
   */
  @Override
  public Account findOneByCertifiedEmail(String certifiedEmail) {
    return provider.findOneByCertifiedEmail(certifiedEmail);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 {@link Account} 实例的详细信息
   */
  @Override
  public List<Account> findAll() {
    return provider.findAll(new HashMap());
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 {@link Account} 实例的详细信息
   */
  @Override
  public List<Account> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 查询某个用户所在的所有组织单位
   *
   * @param organizationUnitId 组织单元标识
   * @return 返回一个 {@link Account} 实例的详细信息
   */
  @Override
  public List<Account> findAllByOrganizationUnitId(String organizationUnitId) {
    return provider.findAllByOrganizationUnitId(organizationUnitId);
  }

  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationUnitId 组织单元标识
   * @param defaultOrganizationUnitRelation 默认组织关系
   * @return 返回一个 {@link Account} 实例的详细信息
   */
  @Override
  public List<Account> findAllByOrganizationUnitId(String organizationUnitId, boolean defaultOrganizationUnitRelation) {
    if (defaultOrganizationUnitRelation) {
      return provider.findAllByOrganizationUnitIdAndDefault(organizationUnitId);
    } else {
      return findAllByOrganizationUnitId(organizationUnitId);
    }
  }

  /**
   * @param organizationUnitId 根据组织机构Id 获取当前组织机构 或者当前组织机构下 及机构人员问题
   * @return 所有人员信息
   */
  @Override
  public List<Account> findAllAccountsByOrganization(String organizationUnitId) {
    List<Account> result = new ArrayList<>();

    List<OrganizationUnit> organizationUnitInfos = MembershipManagement.getInstance().getOrganizationUnitService()
      .getChildOrganizationByOrganizationUnitId(organizationUnitId);
    if (organizationUnitInfos != null) {
      for (int oInt = 0; oInt < organizationUnitInfos.size(); oInt++) {
        OrganizationUnit organizationUnitInfo = organizationUnitInfos.get(oInt);
        List<Account> list = provider.findAllByOrganizationUnitId(organizationUnitInfo.getId());
        result.addAll(list);
      }
    }

    return result;
  }

  /**
   * 查询某个角色下的所有相关帐号
   *
   * @param roleId 角色标识
   * @return 返回一个 IAccount 实例的详细信息
   */
  @Override
  public List<Account> findAllByRoleId(String roleId) {
    return provider.findAllByRoleId(roleId);
  }

  /**
   * ��ѯĳ��Ⱥ���µ����������ʺ�
   *
   * @param groupId Ⱥ����ʶ
   * @return ����һ�� IAccount ʵ������ϸ��Ϣ
   */
  @Override
  public List<Account> findAllByGroupId(String groupId) {
    return provider.findAllByGroupId(groupId);
  }

  /**
   * 返回所有没有成员信息的帐号信息
   *
   * @param length 条数, 0表示全部
   * @return 返回所有{@link Account}实例的详细信息
   */
  @Override
  public List<Account> findAllWithoutMemberInfo(int length) {
    return provider.findAllWithoutMemberInfo(length);
  }

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有{@link Account}实例的详细信息
   */
  @Override
  public List<Account> findForwardLeaderAccountsByOrganizationUnitId(String organizationId) {
    return provider.findForwardLeaderAccountsByOrganizationUnitId(organizationId, 1);
  }

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 返回所有{@link Account}实例的详细信息
   */
  @Override
  public List<Account> findForwardLeaderAccountsByOrganizationUnitId(String organizationId, int level) {
    return provider.findForwardLeaderAccountsByOrganizationUnitId(organizationId, level);
  }

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有{@link Account}实例的详细信息
   */
  @Override
  public List<Account> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId) {
    return provider.findBackwardLeaderAccountsByOrganizationUnitId(organizationId, 1);
  }

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level 层次
   * @return 返回所有{@link Account}实例的详细信息
   */
  @Override
  public List<Account> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId, int level) {
    return provider.findBackwardLeaderAccountsByOrganizationUnitId(organizationId, level);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param loginName 登录名
   * @param globalName 姓名
   * @return 布尔值
   */
  @Override
  public boolean isExistLoginNameAndGlobalName(String loginName, String globalName) {
    boolean result = provider.isExistLoginNameAndGlobalName(loginName, globalName);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("LoginName", loginName));

      if (!result) {
        result = Boolean.parseBoolean(isExistFieldValue("GlobalName", globalName));
      }
    }

    return result;
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param loginName 登录名
   * @return 布尔值
   */
  @Override
  public boolean isExistLoginName(String accountId, String loginName) {
    boolean result = provider.isExistLoginName(loginName);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("LoginName", loginName));
    }
    if (result) {
      Account entity = findOne(accountId);
      if (entity != null && loginName.equals(entity.getLoginName())) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 显示名称重复校验，不存在重复的校验 ；
   *
   * @param displayName 显示名称
   */
  @Override
  public boolean isExistDisplayName(String accountId, String displayName) {
    boolean result = provider.isExistDisplayName(displayName);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("DisplayName", displayName));
    }
    if (result) {
      Account entity = findOne(accountId);
      if (entity != null && displayName.equals(entity.getDisplayName())) {
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
      Account entity = findOne(accountId);
      if (entity != null && identityCard.equals(entity.getIdentityCard())) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param name 姓名
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String name) {
    boolean result = provider.isExistName(name);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("Name", name));
    }

    return result;
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  @Override
  public boolean isExistGlobalName(String globalName) {
    boolean result = provider.isExistGlobalName(globalName);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("GlobalName", globalName));
    }

    return result;
  }

  /**
   * 检测是否存在相关的手机号
   *
   * @param certifiedMobile 已验证的手机号
   * @return 布尔值
   */
  @Override
  public boolean isExistCertifiedMobile(String accountId, String certifiedMobile) {
    boolean result = provider.isExistCertifiedMobile(certifiedMobile);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("CertifiedMobile", certifiedMobile));
    }
    if (result) {
      Account entity = findOne(accountId);
      if (entity != null && certifiedMobile.equals(entity.getCertifiedMobile())) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 检测是否存在相关的邮箱
   *
   * @param certifiedEmail 已验证的邮箱
   * @return 布尔值
   */
  @Override
  public boolean isExistCertifiedEmail(String certifiedEmail) {
    boolean result = provider.isExistCertifiedEmail(certifiedEmail);

    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("CertifiedEmail", certifiedEmail));
    }

    return result;
  }

  /**
   * @param certifiedEmail 验证邮箱是否存在
   */
  @Override
  public boolean isExistCertifiedEmail(String accountId, String certifiedEmail) {
    boolean result = provider.isExistCertifiedEmail(certifiedEmail);
    if (!result) {
      result = Boolean.parseBoolean(isExistFieldValue("DisplayName", certifiedEmail));
    }
    if (result && !StringUtil.isNullOrEmpty(accountId)) {
      result = Boolean.parseBoolean(isExistFieldValue("DisplayName", certifiedEmail));
    }
    if (result) {
      Account entity = findOne(accountId);
      if (entity != null && certifiedEmail.equals(entity.getCertifiedEmail())) {
        result = false;
      }
    }
    return result;
  }

  /**
   * 检测是否存在相关的字段的值
   *
   * @param fieldName 字段的名称
   * @param fieldValue 字段的值
   */
  public String isExistFieldValue(String fieldName, String fieldValue) {
    return "False";
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 帐号标识
   * @param name 帐号名称
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
   * 创建空的帐号信息
   *
   * @param accountId 帐号标识
   */
  @Override
  public Account createEmptyAccount(String accountId) {
    Account param = new AccountInfo();

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
   */
  @Override
  public String combineDistinguishedName(String name) {
    // 输出格式类似如下
    // cn=${姓名},ou=Peoples,dc=x3platform,dc=com

    return StringUtil.format("CN=%1$s,OU=%2$s%3$s", name,
      LdapConfigurationView.getInstance().getCorporationUserFolderRoot(),
      LdapConfigurationView.getInstance().getSuffixDistinguishedName());
  }

  // -------------------------------------------------------
  // 管理员功能
  // -------------------------------------------------------

  /**
   * 设置全局名称
   *
   * @param accountId 帐户标识
   * @param globalName 全局名称
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setGlobalName(String accountId, String globalName) {
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
  public String getPassword(String loginName) {
    return provider.getPassword(loginName);
  }

  /**
   * 获取密码强度
   *
   * @param loginName 帐号
   * @return 0 正常强度密码 | 1 低强度密码 | 2 小于最小密码长度的密码 | 3 纯数字组成的密码 | 9 默认密码
   */
  @Override
  public int getPasswordStrength(String loginName) {
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
  public Date getPasswordChangedDate(String loginName) {
    return provider.getPasswordChangedDate(loginName);
  }

  /**
   * 设置帐号密码.(管理员)
   *
   * @param accountId 编号
   * @param password 密码
   * @return 修改成功, 返回 0, 旧密码不匹配, 返回 1.
   */
  @Override
  public int setPassword(String accountId, String password) {
    Account account = findOne(accountId);
    if (account == null) {
      return 1;
    }

    if (password.startsWith("{SSHA}")) {
      password = password.substring(6);
    } else {
      String salt = account.getPasswordSalt();
      password = Encrypter.encryptSha1(password + salt);
    }

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
  public int setLoginName(String accountId, String loginName) {
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
  public int setCertifiedMobile(String accountId, String telephone) {
    return provider.setCertifiedMobile(accountId, telephone);
  }

  /**
   * 设置已验证的邮箱
   *
   * @param accountId 帐户标识
   * @param email 邮箱
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setCertifiedEmail(String accountId, String email) {
    return provider.setCertifiedEmail(accountId, email);
  }

  /**
   * 设置已验证的头像
   *
   * @param accountId 帐户标识
   * @param avatarVirtualPath 头像的虚拟路径
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setCertifiedAvatar(String accountId, String avatarVirtualPath) {
    return provider.setCertifiedAvatar(accountId, avatarVirtualPath);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public int setEnableEmail(String accountId, int status) {
    return provider.setEnableEmail(accountId, status);
  }

  /**
   * 设置帐号锁定状态
   *
   * @param accountId 账户Id
   */
  @Override
  public int setLocking(String accountId, int locking) {
    Account account = findOne(accountId);

    if (account != null) {
      removeCacheItem(account);
    }

    int affectedRows = provider.setLocking(accountId, locking);

    return affectedRows == 1 ? 0 : 1;
  }

  /**
   * 设置帐号状态
   *
   * @param accountId 帐户标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setStatus(String accountId, int status) {
    Account account = findOne(accountId);

    if (LdapConfigurationView.getInstance().getIntegratedMode()) {
      // 同步 Active Directory 帐号状态

      if (account != null && !StringUtil.isNullOrEmpty(account.getLoginName())) {
        // LdapManagement.getInstance().getUser().setStatus(account.getLoginName(), status == 1 ? true : false);
      }
    }

    if (account != null) {
      removeCacheItem(account);
    }

    int affectedRows = provider.setStatus(accountId, status);

    return affectedRows == 1 ? 0 : 1;
  }

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param ip 登录IP
   * @param loginDate 登录时间
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setIPAndLoginDate(String accountId, String ip, Date loginDate) {
    return provider.setIPAndLoginDate(accountId, ip, loginDate);
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
  public int validatePasswordPolicy(String password) {
    byte[] buffer = password.getBytes();

    String passwordPolicyRules = MembershipConfigurationView.getInstance().getPasswordPolicyRules();
    int passwordPolicyMinimumLength = MembershipConfigurationView.getInstance().getPasswordPolicyMinimumLength();
    int passwordPolicyCharacterRepeatedTimes = MembershipConfigurationView.getInstance()
      .getPasswordPolicyCharacterRepeatedTimes();

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
   * @param accountId 帐号唯一标识
   * @param passwordType 密码类型: default 默认, two-factor 查询密码, trader 交易密码
   * @param password 密码
   * @return 返回值 0-成功 | 1-失败
   */
  @Override
  public int confirmPassword(String accountId, String passwordType, String password) {
    int affectedRows;
    // Two-Factor Authentication
    if (PASSWORD_TYPE_TWO_FACTOR.equalsIgnoreCase(passwordType)) {
      // TODO 待处理
      // 查询密码
      // affectedRows = provider.confirmQueryPassword(accountId, passwordType, password);
      affectedRows = 0;
    } else {
      affectedRows = provider.confirmPassword(accountId, passwordType, password);
    }

    return affectedRows == 0 ? 1 : 0;
  }

  /**
   * 登陆检测
   *
   * @param loginName 帐号
   * @param password 密码
   * @return{@link Account} 实例
   */
  @Override
  public Account loginCheck(String loginName, String password) {
    Account account = findOneByLoginName(loginName);

    if (account == null) {
      return null;
    }

    /// 输入调试信息
    // KernelContext.getLog().debug("login name:{}, password:{}, password salt:{}",
    //  loginName, password, account.getPasswordSalt());

    if (password.startsWith("{SSHA}")) {
      password = password.substring(6);
    } else {
      String salt = account.getPasswordSalt();
      password = Encrypter.encryptSha1(password + salt);
    }

    return provider.loginCheck(loginName, password);
  }

  /**
   * 修改基本信息
   *
   * @param param IAccount 实例的详细信息
   */
  @Override
  public void changeBasicInfo(Account param) {
    provider.changeBasicInfo(param);
  }

  /**
   * 修改密码
   *
   * @param loginName 编号
   * @param password 新密码
   * @param originalPassword 原始密码
   * @return 消息代码
   */
  @Override
  public int changePassword(String loginName, String password, String originalPassword) {
    Account account = findOneByLoginName(loginName);

    if (account == null) {
      return 1;
    }

    if (password.startsWith("{SSHA}")) {
      password = password.substring(6);
    } else {
      String salt = account.getPasswordSalt();
      password = Encrypter.encryptSha1(password + salt);
    }

    if (originalPassword.startsWith("{SSHA}")) {
      originalPassword = originalPassword.substring(6);
    } else {
      String salt = account.getPasswordSalt();
      originalPassword = Encrypter.encryptSha1(originalPassword + salt);
    }

    int affectedRows = provider.changePassword(loginName, password, originalPassword);

    if (affectedRows == 1 && LdapConfigurationView.getInstance().getIntegratedMode()) {
      // 同步 Active Directory 帐号状态
      if (account != null && !StringUtil.isNullOrEmpty(account.getLoginName()) && !StringUtil.isNullOrEmpty(password)) {
        // TODO 设置 Python
        // LdapManagement.getInstance().getUser().setPassword(account.LoginName, password);
      }
    }

    return affectedRows > 0 ? 0 : 1;
  }

  /**
   * 刷新帐号的更新时间
   *
   * @param accountId 帐户标识
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public int refreshModifiedDate(String accountId) {
    return provider.refreshModifiedDate(accountId);
  }

  /**
   * 获取帐号相关的权限对象
   *
   * @param account IAccount 实例的详细信息
   */
  // public List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(Account account) {
  //  return this.provider.GetAuthorizationScopeObjects(account);
  // }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 帐号信息
   */
  @Override
  public int syncToLDAP(Account param) {
    return syncToLDAP(param, param.getGlobalName(), param.getStatus());
  }

  /**
   * 同步信息至 Active Directory
   *
   * @param param 帐号信息
   * @param originalGlobalName 原始全局名称
   * @param originalStatus 原始状态
   */
  public int syncToLDAP(Account param, String originalGlobalName, int originalStatus) {
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

  /**
   * @param authType isAdministrator;isReviewer;isMember
   * @param applicationName 配置应用名称 （必填）
   * @param selectKey ‘搜索框输入的值’
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
}
