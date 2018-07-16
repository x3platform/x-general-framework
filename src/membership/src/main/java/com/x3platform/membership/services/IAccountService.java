package com.x3platform.membership.services;

import java.util.*;

import com.x3platform.*;
import com.x3platform.membership.*;

/**
 * 帐号服务接口
 */
public interface IAccountService {

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param IAccount 实例详细信息
   * @return IAccount 实例详细信息
   */
  IAccountInfo save(IAccountInfo param);

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
   * @param id IAccount id号
   * @return 返回一个 IAccount 实例的详细信息
   */
  IAccountInfo findOne(String id);

  /**
   * 查询某条记录
   *
   * @param globalName 帐号的全局名称
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  IAccountInfo findOneByGlobalName(String globalName);

  /**
   * 查询某条记录
   *
   * @param loginName 登录名
   * @return 返回一个 IAccount 实例的详细信息
   */
  IAccountInfo findOneByLoginName(String loginName);

  /**
   * 根据已验证的手机号查询某条记录
   *
   * @param certifiedMobile 已验证的手机号
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  IAccountInfo findOneByCertifiedMobile(String certifiedMobile);

  /**
   * 根据已验证的邮箱地址查询某条记录
   *
   * @param certifiedEmail 已验证的邮箱地址
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  IAccountInfo findOneByCertifiedEmail(String certifiedEmail);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAll();

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAll(String whereClause);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAll(String whereClause, int length);

  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationId 组织标识
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAllByOrganizationUnitId(String organizationId);

  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationId                  组织标识
   * @param defaultOrganizationUnitRelation 默认组织关系
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAllByOrganizationUnitId(String organizationId, boolean defaultOrganizationUnitRelation);

  /**
   * 查询某个角色下的所有相关帐号
   *
   * @param roleId 角色标识
   * @return 返回一个<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAllByRoleId(String roleId);

  /**
   * 查询某个群组下的所有相关帐号
   *
   * @param groupId 群组标识
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAllByGroupId(String groupId);

  /**
   * 返回所有没有成员信息的帐号信息
   *
   * @param length 条数, 0表示全部
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findAllWithoutMemberInfo(int length);

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findForwardLeaderAccountsByOrganizationUnitId(String organizationId);

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findForwardLeaderAccountsByOrganizationUnitId(String organizationId, int level);

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId);

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有<see cref="IAccountInfo"/>实例的详细信息
   */
  List<IAccountInfo> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId, int level);

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
   * @return 返回一个列表<see cref="IAccountInfo"/>
   */
  // List<IAccountInfo> getPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 检测是否存在相关的记录.
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 检测是否存在相关的记录,登录名和姓名两者都不能重复.
   *
   * @param loginName 登录名
   * @param name      姓名
   * @return 布尔值
   */
  boolean isExistLoginNameAndGlobalName(String loginName, String name);

  /**
   * 检测是否存在相关的记录, 用户中心, 登录名不能重复. [添加帐号]
   *
   * @param loginName 登录名
   * @return 布尔值
   */
  boolean isExistLoginName(String loginName);

  /**
   * 检测是否存在相关的记录, 由于在同一个OU下面,所以姓名不能重复. 修改姓名时
   *
   * @param nickName 姓名
   * @return 布尔值
   */
  boolean isExistName(String nickName);

  /**
   * 检测是否存在相关的记录
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
  boolean isExistCertifiedMobile(String certifiedMobile);

  /**
   * 检测是否存在相关的邮箱
   *
   * @param certifiedEmail 已验证的邮箱
   * @return 布尔值
   */
  boolean isExistCertifiedEmail(String certifiedEmail);

  /**
   * 检测是否存在相关的记录
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
   * @return
   */
  IAccountInfo createEmptyAccount(String accountId);

  /**
   * 组合唯一名称
   *
   * @param globalName 帐号全局名称
   * @return
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
  java.time.LocalDateTime getPasswordChangedDate(String loginName);

  /**
   * 设置帐号密码.(管理员)
   *
   * @param accountId 编号
   * @param password  密码
   * @return 修改成功, 返回 0, 旧密码不匹配, 返回 1.
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
  int setExchangeStatus(String accountId, int status);

  /**
   * 设置用户状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
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
  int setIPAndLoginDate(String accountId, String ip, java.time.LocalDateTime loginDate);

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
   * @param passwordType 密码类型: default 默认, query 查询密码, trader 交易密码
   * @param password     密码
   * @return 返回值: 0 成功 | 1 失败
   */
  int confirmPassword(String accountId, String passwordType, String password);
  ///#endregion

  ///#region 函数:LoginCheck(string loginName, string password)

  /**
   * 登陆检测
   *
   * @param loginName 帐号
   * @param password  密码
   * @return IAccount 实例
   */
  IAccountInfo loginCheck(String loginName, String password);

  /**
   * 修改基本信息
   *
   * @param param IAccount 实例的详细信息
   */
  void changeBasicInfo(IAccountInfo param);

  /**
   * 修改密码
   *
   * @param loginName        登录名
   * @param password         新密码
   * @param originalPassword 原始密码
   * @return 修改成功, 返回 0, 旧密码不匹配, 返回 1.
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
   * @param account IAccount 实例的详细信息
   */
  // List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(IAccountInfo account);

  /**
   * 同步信息至 Active Directory
   *
   * @param param 帐号信息
   */
  int syncToLDAP(IAccountInfo param);
}
