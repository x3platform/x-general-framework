package com.x3platform.membership.authentication;

import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;

/**
 * 单点登录验证请求管理
 * 登录帐号统一使用系统管理员配置登录密码
 *
 * @author ruanyu
 */
public final class SSOAuthenticationManagement extends GenericAuthenticationManagement {

  /**
   * 获取认证的用户信息
   */
  @Override
  public Account getAuthUser() {
    String sessionId = getIdentityValue();
    // Http 方式的验证, accountIdentity 不允许为空.
    if (StringUtil.isNullOrEmpty(sessionId)) {
      return null;
    }
    
    // 获取帐号信息
    Account account = getSessionAccount(sessionId);
    if (account == null) {
      return null;
    }
    
    return account;
  }
}
