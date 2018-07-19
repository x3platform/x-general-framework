package com.x3platform.membership.mappers;

import java.util.*;

import com.x3platform.*;
import com.x3platform.membership.IAccountInfo;
// import com.x3platform.Spring.*;
// import com.x3platform.Membership.Scope.*;
// import com.x3platform.Data.*;

/**
 */
public interface AccountMapper {

  // -------------------------------------------------------
  // 插入 删除 更新
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param IAccount 实例详细信息
   * @return IAccount 实例详细信息
   */
  IAccountInfo save(IAccountInfo param);

  /**
   * 添加记录
   *
   * @param param IAccount 实例的详细信息
   */
  void insert(IAccountInfo param);

  /**
   * 修改记录
   *
   * @param param IAccount 实例的详细信息
   */
  void update(IAccountInfo param);

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
   * @return 返回一个实例的详细信息
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
   * @return 返回一个实例的详细信息
   */
  IAccountInfo findOneByCertifiedMobile(String certifiedMobile);

  /**
   * 根据已验证的邮箱地址查询某条记录
   *
   * @param certifiedEmail 已验证的邮箱地址
   * @return 返回一个实例的详细信息
   */
  IAccountInfo findOneByCertifiedEmail(String certifiedEmail);

  /**
   * 查询所有相关记录
   *
   * @return 返回所有实例的详细信息
   */
  List<IAccountInfo> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param whereClause SQL 查询条件
   * @param length      条数
   * @return 返回所有实例的详细信息
   */
  List<IAccountInfo> findAll(String whereClause, int length);

  /**
   * 查询某个用户所在的所有组织单位
   *
   * @param organizationId 组织标识
   * @return 返回一个实例的详细信息
   */
  List<IAccountInfo> findAllByOrganizationUnitId(String organizationId);

  /**
   * 查询某个组织下的所有相关帐号
   *
   * @param organizationId                  组织标识
   * @param defaultOrganizationUnitRelation 默认组织关系
   * @return 返回一个实例的详细信息
   */
  List<IAccountInfo> findAllByOrganizationUnitId(String organizationId, boolean defaultOrganizationUnitRelation);

  /**
   * 查询某个角色下的所有相关帐号
   *
   * @param roleId 组织标识
   * @return 返回一个实例的详细信息
   */
  List<IAccountInfo> findAllByRoleId(String roleId);

  /**
   * 查询某个群组下的所有相关帐号
   *
   * @param groupId 群组标识
   * @return 返回一个实例的详细信息
   */
  List<IAccountInfo> findAllByGroupId(String groupId);

  /**
   * 返回所有没有成员信息的帐号信息
   *
   * @param length 条数, 0表示全部
   * @return 返回所有实例的详细信息
   */
  List<IAccountInfo> findAllWithoutMemberInfo(int length);
  ///#endregion

  ///#region 函数:findForwardLeaderAccountsByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有正向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有实例的详细信息
   */
  List<IAccountInfo> findForwardLeaderAccountsByOrganizationUnitId(String organizationId, int level);
  ///#endregion

  ///#region 函数:findBackwardLeaderAccountsByOrganizationUnitId(string organizationId, int level)

  /**
   * 返回所有反向领导的帐号信息
   *
   * @param organizationId 组织标识
   * @param level          层次
   * @return 返回所有实例的详细信息
   */
  List<IAccountInfo> findBackwardLeaderAccountsByOrganizationUnitId(String organizationId, int level);
  ///#endregion

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
  // List<IAccountInfo> getPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 检测是否存在相关的记录.
   *
   * @param id 帐号标识
   * @return 布尔值
   */
  boolean isExist(String id);
  ///#endregion

  ///#region 函数:isExistLoginNameAndGlobalName(string loginName, string name);

  /**
   * 检测是否存在相关的记录,登录名和姓名两者都不能重复.
   *
   * @param loginName 登录名
   * @param name      姓名
   * @return 布尔值
   */
  boolean isExistLoginNameAndGlobalName(String loginName, String name);
  ///#endregion

  ///#region 函数:isExistLoginName(string loginName)

  /**
   * 检测是否存在相关的记录, 用户中心, 登录名不能重复. [添加帐号]
   *
   * @param loginName 登录名
   * @return 布尔值
   */
  boolean isExistLoginName(String loginName);
  ///#endregion

  ///#region 函数:isExistName(string name)

  /**
   * 检测是否存在相关的记录, 由于在同一个OU下面,所以姓名不能重复. 修改姓名时
   *
   * @param name 姓名
   * @return 布尔值
   */
  boolean isExistName(String name);
  ///#endregion

  ///#region 函数:isExistGlobalName(string globalName)

  /**
   * 检测是否存在相关的记录
   *
   * @param globalName 组织单位全局名称
   * @return 布尔值
   */
  boolean isExistGlobalName(String globalName);
  ///#endregion

  ///#region 函数:isExistCertifiedMobile(string certifiedMobile)

  /**
   * 检测是否存在相关的手机号
   *
   * @param certifiedMobile 已验证的手机号
   * @return 布尔值
   */
  boolean isExistCertifiedMobile(String certifiedMobile);
  ///#endregion

  ///#region 函数:isExistCertifiedEmail(string certifiedEmail)

  /**
   * 检测是否存在相关的邮箱
   *
   * @param certifiedEmail 已验证的邮箱地址
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
   * 获取密码(管理员)
   *
   * @param loginName 帐号
   * @return 密码
   */
  String getPassword(String loginName);
  ///#endregion

  ///#region 函数:GetPasswordChangedDate(string loginName)

  /**
   * 获取密码更新时间
   *
   * @param loginName 帐号
   */
  java.time.LocalDateTime getPasswordChangedDate(String loginName);
  ///#endregion

  ///#region 函数:SetPassword(string accountId, string password)

  /**
   * 设置帐号密码(管理员)
   *
   * @param accountId 编号
   * @param password  密码
   * @return 修改成功, 返回 0, 旧密码不匹配, 返回 1.
   */
  int setPassword(String accountId, String password);
  ///#endregion

  ///#region 函数:SetLoginName(string accountId, string loginName)

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param loginName 登录名
   * @return 0 操作成功 | 1 操作失败
   */
  int setLoginName(String accountId, String loginName);
  ///#endregion

  ///#region 函数:SetCertifiedMobile(string accountId, string telephone)

  /**
   * 设置已验证的联系电话
   *
   * @param accountId 帐户标识
   * @param telephone 联系电话
   * @return 0 操作成功 | 1 操作失败
   */
  int setCertifiedMobile(String accountId, String telephone);
  ///#endregion

  ///#region 函数:SetCertifiedEmail(string accountId, string email)

  /**
   * 设置已验证的邮箱
   *
   * @param accountId 帐户标识
   * @param email     邮箱
   * @return 0 操作成功 | 1 操作失败
   */
  int setCertifiedEmail(String accountId, String email);
  ///#endregion

  ///#region 函数:SetCertifiedAvatar(string accountId, string avatarVirtualPath)

  /**
   * 设置已验证的头像
   *
   * @param accountId         帐户标识
   * @param avatarVirtualPath 头像的虚拟路径
   * @return 0 操作成功 | 1 操作失败
   */
  int setCertifiedAvatar(String accountId, String avatarVirtualPath);
  ///#endregion

  ///#region 函数:SetExchangeStatus(string accountId, int status)

  /**
   * 设置邮箱状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 操作成功 | 1 操作失败
   */
  int setExchangeStatus(String accountId, int status);
  ///#endregion

  ///#region 函数:SetStatus(string accountId, int status)

  /**
   * 设置状态
   *
   * @param accountId 帐户标识
   * @param status    状态标识, 1:启用, 0:禁用
   * @return 0 操作成功 | 1 操作失败
   */
  int setStatus(String accountId, int status);
  ///#endregion

  ///#region 函数:SetIPAndLoginDate(string accountId, string ip, DateTime loginDate)

  /**
   * 设置登录名
   *
   * @param accountId 帐户标识
   * @param ip        登录名
   * @param loginDate 登录时间
   * @return 0 操作成功 | 1 操作失败
   */
  int setIPAndLoginDate(String accountId, String ip, java.time.LocalDateTime loginDate);
  ///#endregion

  // -------------------------------------------------------
  // 会员功能
  // -------------------------------------------------------

  /**
   * 确认密码
   *
   * @param accountId    帐号唯一标识
   * @param passwordType 密码类型: default 默认, query 查询密码, trader 交易密码
   * @param password     密码
   * @return 返回值: 0 成功 | 1 失败
   */
  int confirmPassword(String accountId, String passwordType, String password);

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

  // TODO getAuthorizationScopeObjects
  /**
   * 获取帐号相关的权限对象
   *
   * @param account IAccount 实例的详细信息
   */
  // List<MembershipAuthorizationScopeObject> getAuthorizationScopeObjects(IAccountInfo account);

  /**
   * 同步信息
   *
   * @param param 帐号信息
   */
  int syncFromPackPage(IAccountInfo param);
}
