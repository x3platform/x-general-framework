package com.x3platform.security.authentication;

import com.x3platform.membership.Account;
import java.util.Map;

/**
 * 验证请求
 */
public interface AuthenticationManagement {

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
  Account getAuthUser();

  /**
   * 登录
   *
   * @return 帐号信息
   */
  Account login(String loginName, String password, boolean isCreatePresistent);

  /**
   * 注销
   *
   * @return 消息代码
   */
  int logout();

  /**
   * 获取当前会话集合
   *
   * @return
   */
  Map<String, Account> getSessions();

  /**
   * 新增会话
   */
  void addSession(String appKey, String sessionId, Account account);

  /**
   * 移除会话
   */
  void removeSession(String sessionId);

  /**
   * 重置所有会话
   */
  void resetSessions();
}
