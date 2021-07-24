package com.x3platform.membership.authentication;

import com.x3platform.InternalLogger;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.IPQueryUtil;
import com.x3platform.util.StringUtil;

import java.util.Date;

/**
 * 开发环境验证请求管理 登录帐号统一使用系统管理员配置登录密码
 */
public final class HttpAuthenticationManagement extends GenericAuthenticationManagement {
  
  /**
   * 获取认证的用户信息
   */
  @Override
  public Account getAuthUser() {
    String sessionId = getIdentityValue();
    // Http 方式的验证, sessionId 不允许为空.
    if (StringUtil.isNullOrEmpty(sessionId)) {
      return null;
    }
    
    // 获取帐号信息
    Account account = getSessionAccount(sessionId);
    
    return account;
  }
  
  /**
   * 登录
   */
  @Override
  public Account login(String loginName, String password, boolean isCreatePersistent) {
    // 登录验证
    Account account = MembershipManagement.getInstance().getAccountService().loginCheck(loginName, password);
    if (account == null) {
      return null;
    }
    
    // 记录日志
    String ip = IPQueryUtil.getClientIP();
    // 记录最近一次登录地址和时间
    MembershipManagement.getInstance().getAccountService().setIPAndLoginDate(account.getId(), ip, new Date());
    // 记录帐号日志信息
    MembershipManagement.getInstance().getAccountLogService().log(account.getId(), "基础平台.人员及权限管理.验证管理.登录",
      String.format("【%1$s】在 %2$s 登录了系统。【IP:%3$s】", account.getName(),
        StringUtil.toDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"), ip));
    
    return account;
  }
  
  /**
   * 新增会话
   */
  @Override
  public void addSession(String appKey, String sessionId, Account account) {
    super.addSession(appKey, sessionId, account);
    // 设置 Cookie 信息
    HttpAuthenticationCookieSetter.setUserCookies(sessionId);
  }
  
  /**
   * 移除会话
   */
  @Override
  public void removeSession(String sessionId) {
    super.removeSession(sessionId);
    // 清除 Cookie 信息
    HttpAuthenticationCookieSetter.clearUserCookies();
  }
}
