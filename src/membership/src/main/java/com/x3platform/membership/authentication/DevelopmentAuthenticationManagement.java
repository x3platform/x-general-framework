package com.x3platform.membership.authentication;

import com.x3platform.membership.IAccountInfo;
import com.x3platform.util.StringUtil;

/**
 * 开发环境验证请求管理
 * 登录帐号统一使用系统管理员配置登录密码
 */
public final class DevelopmentAuthenticationManagement extends GenericAuthenticationManagement {
  /**
   * 获取认证的用户信息
   */
  @Override
  public IAccountInfo getAuthUser() {
    String accountIdentity = this.getIdentityValue();

    // Http 方式的验证, accountIdentity 不允许为空.
    if (StringUtil.isNullOrEmpty(accountIdentity)) {
      return null;
    }

    // 获取帐号信息
    IAccountInfo account = this.getSessionAccount(accountIdentity);

    if (account == null) {
      return null;
    }

    // 写入临时存储
    if (!this.cacheStorage.containsKey(accountIdentity)) {
      synchronized (this.getCacheSyncRoot()) {
        if (!this.cacheStorage.containsKey(accountIdentity)) {
          this.addSession(accountIdentity, account);
        }
      }
    }

    return account;
  }
}
