package com.x3platform.membership.services;

import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.security.authentication.LoginType;

import java.util.Date;
import java.util.List;

/**
 * 帐号服务接口
 */
public interface AccountService {
  
  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------
  
  /**
   * 新版方法保存， 未使用接口方式， 暂不了解具体情况
   */
  Account save(Account entity) throws Exception;
  
  /**
   * 删除记录
   *
   * @param id 帐号标识
   */
  void delete(String id);
  
  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------
  
  /**
   * 查询某条记录
   *
   * @param id {@link Account} id号
   * @return 一个 {@link Account} 实例的详细信息
   */
  Account findOne(String id);
  
  /**
   * 查询某条记录
   *
   * @param globalName 帐号的全局名称
   * @return 一个 {@link Account} 实例的详细信息
   */
  Account findOneByGlobalName(String globalName);
  
  /**
   * 根据登录名查询某条记录
   *
   * @param loginName 登录名
   * @return 一个 {@link Account} 实例的详细信息
   */
  Account findOneByLoginName(String loginName);
  
  /**
   * 根据已验证的手机号查询某条记录
   *
   * @param certifiedMobile 已验证的手机号
   * @return 一个 {@link Account} 实例的详细信息
   */
  Account findOneByCertifiedMobile(String certifiedMobile);
  
  /**
   * 根据已验证的邮箱地址查询某条记录
   *
   * @param certifiedEmail 已验证的邮箱地址
   * @return 一个 {@link Account} 实例的详细信息
   */
  Account findOneByCertifiedEmail(String certifiedEmail);
  
  /**
   * 查询所有相关记录
   *
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findAll();
  
  /**
   * 查询所有相关记录
   * 新增 采用 mybatis 分页 和 新版写法 ， 在用状态 根据条件查询出列表
   * 新版查询人员接口，带有组织结构查询，和其他接口查询
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link Account} 实例的详细信息
   */
  List<Account> findAll(DataQuery query);
  
  /**
   * 根据组织机构标识 获取当前组织机构 或者当前组织机构下 及机构人员问题
   * 可能 findAllByOrganizationUnitId 与重合
   *
   * @param organizationUnitId 组织标识
   * @return 一个 {@link Account} 实例的详细信息
   */
  List<Account> findAllAccountsByOrganization(String organizationUnitId);
  
  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationUnitId 组织标识
   * @return 一个 {@link Account} 实例的详细信息
   */
  List<Account> findAllByOrganizationUnitId(String organizationUnitId);
  
  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationId                  组织标识
   * @param defaultOrganizationUnitRelation 默认组织关系
   * @return 一个 {@link Account} 实例的详细信息
   */
  List<Account> findAllByOrganizationUnitId(String organizationId, boolean defaultOrganizationUnitRelation);
  
  /**
   * 查询某个角色下的所有相关帐号
   *
   * @param roleId 角色标识
   * @return 一个 {@link Account} 实例的详细信息
   */
  List<Account> findAllByRoleId(String roleId);
  
  /**
   * 查询某个群组下的所有相关帐号
   *
   * @param groupId 群组标识
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findAllByGroupId(String groupId);
  
  /**
   * 所有没有成员信息的帐号信息
   *
   * @param length 条数, 0表示全部
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findAllWithoutMemberInfo(int length);
  
  /**
   * 所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findForwardLeaderAccountsByOrganizationUnitId(String organizationId);
  
  /**
   * 所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findForwardLeaderAccountsByOrganizationUnitId(String organizationId, int level);
  
  /**
   * 所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId);
  
  /**
   * 所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 所有 {@link Account} 实例的详细信息
   */
  List<Account> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId, int level);
  
  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------
  
  /**
   * 查询是否存在相关的记录
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  boolean isExist(String id);
  
  /**
   * 查询是否存在相关的记录 登录名和姓名两者都不能重复.
   *
   * @param loginName 登录名
   * @param name      姓名
   * @return 布尔值
   */
  boolean isExistLoginNameAndGlobalName(String loginName, String name);
  
  /**
   * 查询是否存在相关的记录 用户中心, 登录名不能重复. [添加帐号]
   *
   * @param loginName 登录名
   * @return 布尔值
   */
  boolean isExistLoginName(String accountId, String loginName);
  
  /**
   * 查询是否存在相关的记录 用户中心, 登录名不能重复. [添加帐号]
   *
   * @param displayName 显示名称
   * @return 布尔值
   */
  boolean isExistDisplayName(String accountId, String displayName);
  
  /**
   * 查询是否存在相关的记录 用户中心, 身份证不能重复 [添加帐号]
   *
   * @param identityCard 身份证
   * @return 布尔值
   */
  boolean isExistIdentityCard(String accountId, String identityCard);
  
  /**
   * 查询是否存在相关的记录 由于在同一个OU下面,所以姓名不能重复. 修改姓名时
   *
   * @param nickName 姓名
   * @return 布尔值
   */
  boolean isExistName(String nickName);
  
  /**
   * 查询是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(String globalName);
  
  /**
   * 检测是否存在相关的手机号
   *
   * @param certifiedMobile 已验证的手机号
   * @return 布尔值
   */
  boolean isExistCertifiedMobile(String accountId, String certifiedMobile);
  
  /**
   * 检测是否存在相关的邮箱
   *
   * @param certifiedEmail 已验证的邮箱
   * @return 布尔值
   */
  boolean isExistCertifiedEmail(String certifiedEmail);
  
  /**
   * 查询是否存在相关的记录 用户中心, 登录名不能重复. [添加帐号]
   *
   * @param certifiedEmail 验证邮箱是否存在
   * @return 布尔值
   */
  boolean isExistCertifiedEmail(String accountId, String certifiedEmail);
  
  /**
   * 查询是否存在相关的记录
   *
   * @param id   帐号标识
   * @param name 帐号名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  int rename(String id, String name);
  
  /**
   * 创建空的帐号信息
   *
   * @param accountId 帐号标识
   */
  Account createEmptyAccount(String accountId);
  
  /**
   * 组合唯一名称
   *
   * @param globalName 帐号全局名称
   */
  String combineDistinguishedName(String globalName);
  
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
  int setGlobalName(String accountId, String globalName);
  
  /**
   * 获取密码
   *
   * @param loginName 帐号
   */
  String getPassword(String loginName);
  
  /**
   * 获取密码强度
   *
   * @param loginName 帐号
   * @return 0 密码强度正常 | 1 密码强度低 | 2 密码长度不符合规范 | 3 密码为存数字 | 9 密码为默认密码
   */
  int getPasswordStrength(String loginName);
  
  /**
   * 获取密码更新时间
   *
   * @param loginName 帐号
   */
  Date getPasswordChangedDate(String loginName);
  
  /**
   * 设置帐号密码.(管理员)
   *
   * @param accountId 编号
   * @param password  密码
   * @return 修改成功,  0, 旧密码不匹配,  1.
   */
  int setPassword(String accountId, String password);
  
  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param loginName 登录名
   * @return 0 操作成功 | 1 操作失败
   */
  int setLoginName(String accountId, String loginName);
  
  /**
   * 设置已验证的联系电话
   *
   * @param accountId 帐户标识
   * @param telephone 联系电话
   * @return 0 操作成功 | 1 操作失败
   */
  int setCertifiedMobile(String accountId, String telephone);
  
  /**
   * 设置已验证的邮箱
   *
   * @param accountId 帐户标识
   * @param email     邮箱
   * @return 0 操作成功 | 1 操作失败
   */
  int setCertifiedEmail(String accountId, String email);
  
  /**
   * 设置已验证的头像
   *
   * @param accountId         帐户标识
   * @param avatarVirtualPath 头像的虚拟路径
   * @return 0 操作成功 | 1 操作失败
   */
  int setCertifiedAvatar(String accountId, String avatarVirtualPath);
  
  /**
   * 设置企业邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  int setEnableEmail(String accountId, int status);
  
  /**
   * 设置帐号解锁
   *
   * @param accountId 账户Id
   * @return {@link Account} 实例详细信息
   */
  int setLocking(String accountId, int unlocking);
  
  /**
   * 设置帐号状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识 0-禁用 1-启用
   * @return 0-操作成功 | 1-操作失败
   */
  int setStatus(String accountId, int status);
  
  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param ip        登录名
   * @param loginDate 登录时间
   * @return 0 操作成功 | 1 操作失败
   */
  int setIPAndLoginDate(String accountId, String ip, Date loginDate);
  
  // -------------------------------------------------------
  // 普通用户功能
  // -------------------------------------------------------
  
  /**
   * 校验密码是否符合密码策略
   *
   * @param password 密码
   * @return 0 表示成功 1
   */
  int validatePasswordPolicy(String password);
  
  /**
   * 确认密码
   *
   * @param accountId    帐号唯一标识
   * @param passwordType 密码类型: default 默认, two-factor 查询密码
   * @param password     密码
   * @return 值: 0 成功 | 1 失败
   */
  int confirmPassword(String accountId, String passwordType, String password);
  
  /**
   * 登陆检测
   *
   * @param loginName 帐号
   * @param password  密码
   * @return {@link Account} 实例
   */
  Account loginCheck(String loginName, String password);
  
  /**
   * 登陆检测
   *
   * @param loginType    登录类型 LoginName | Mobile | Email
   * @param objectIdentity 登录对象标识
   * @param password  密码
   * @return {@link Account} 实例
   */
  Account loginCheck(LoginType loginType, String objectIdentity, String password);
  
  /**
   * 修改基本信息
   *
   * @param param {@link Account} 实例的详细信息
   */
  void changeBasicInfo(Account param);
  
  /**
   * 修改密码
   *
   * @param loginName        登录名
   * @param password         新密码
   * @param originalPassword 原始密码
   * @return 修改成功,  0, 旧密码不匹配,  1.
   */
  int changePassword(String loginName, String password, String originalPassword);
  
  /**
   * 刷新帐号的更新时间
   *
   * @param accountId 帐户标识
   * @return 0 设置成功, 1 设置失败.
   */
  int refreshModifiedDate(String accountId);
  
  /**
   * 获取帐号相关的权限对象
   *
   * @param account {@link Account} 实例的详细信息
   */
  // List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(Account account);
  
  /**
   * 同步信息至 Active Directory
   *
   * @param param 帐号信息
   */
  int syncToLDAP(Account param);
  
  /**
   * @param authType        isAdministrator;isReviewer;isMember
   * @param applicationName '配置应用名称'
   * @param selectKey       ‘搜索框输入的值’
   */
  List<Account> findAllAccountByAccountName(String authType, String applicationName, String selectKey);
}
