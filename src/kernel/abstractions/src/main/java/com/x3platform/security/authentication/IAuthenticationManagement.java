package com.x3platform.security.authentication;

import com.x3platform.membership.IAccountInfo;

import java.util.*;

/**
 * 验证请求
 */
public interface IAuthenticationManagement {
  /**
   * 获取用户存储策略
   */
  // IAccountStorageStrategy getAccountStorageStrategy();

  /**
   * 标识的名字
   */
  String getIdentityName();

  /**
   * 获取用户标识的值
   */
  String getIdentityValue();

  /**
   * 获取认证的用户信息
   */
  IAccountInfo getAuthUser();

  /**
   * 登录
   *
   * @return
   */
  int login(String loginName, String password, boolean isCreatePresistent);

  /**
   * 注销
   *
   * @return
   */
  int logout();

  /**
   * 获取当前会话集合
   *
   * @return
   */
  Map<String, IAccountInfo> getSessions();

  /**
   * 新增会话
   */
  void addSession(String appKey, String sessionId, IAccountInfo account);

  /**
   * 移除会话
   */
  void removeSession(String sessionId);

  /**
   * 重置所有会话
   */
  void resetSessions();
}
