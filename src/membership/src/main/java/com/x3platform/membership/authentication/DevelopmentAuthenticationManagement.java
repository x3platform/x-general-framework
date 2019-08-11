package com.x3platform.membership.authentication;

import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;

/**
 * 开发环境验证请求管理 登录帐号统一使用系统管理员配置登录密码
 */
public final class DevelopmentAuthenticationManagement extends GenericAuthenticationManagement {

  /**
   * 获取认证的用户信息
   */
  @Override
  public Account getAuthUser() {
    String accountIdentity = getIdentityValue();

    // Http 方式的验证, accountIdentity 不允许为空.
    if (StringUtil.isNullOrEmpty(accountIdentity)) {
      return null;
    }

    // 获取帐号信息
    Account account = getSessionAccount(accountIdentity);

    if (account == null) {
      return null;
    }

    // 写入临时存储
    if (!cacheStorage.containsKey(accountIdentity)) {
      synchronized (cacheSyncRoot) {
        if (!cacheStorage.containsKey(accountIdentity)) {
          addSession(accountIdentity, account);
        }
      }
    }

    return account;
  }
}
