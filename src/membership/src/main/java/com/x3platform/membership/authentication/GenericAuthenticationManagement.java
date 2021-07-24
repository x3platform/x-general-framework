package com.x3platform.membership.authentication;

import static com.x3platform.Constants.HTTP_HEADER_AUTHORIZATION;
import static com.x3platform.Constants.TEXT_EMPTY;

import com.x3platform.InternalLogger;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.security.authentication.AuthenticationManagement;
import com.x3platform.security.authentication.LoginType;
import com.x3platform.membership.storages.GenericTicketStorageStrategy;
import com.x3platform.sessions.SessionsContext;
import com.x3platform.sessions.TicketStorageStrategy;
import com.x3platform.sessions.configuration.SessionsConfigurationView;
import com.x3platform.util.HttpContextUtil;
import com.x3platform.util.IPQueryUtil;
import com.x3platform.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;

import org.springframework.stereotype.Component;

/**
 * 通用的验证请求管理
 */
@Component
public abstract class GenericAuthenticationManagement implements AuthenticationManagement {
  
  /**
   * 帐号存储策略
   */
  protected TicketStorageStrategy strategy = null;
  
  /**
   * 默认构造函数
   */
  public GenericAuthenticationManagement() {
    strategy = new GenericTicketStorageStrategy();
  }
  
  /**
   * 获取用户存储策略
   */
  public TicketStorageStrategy getTicketStorageStrategy() {
    return strategy;
  }
  
  /**
   * 标识名称
   */
  @Override
  public String getIdentityName() {
    return MembershipConfigurationView.getInstance().getAccountIdentityCookieToken();
  }
  
  /**
   * 获取标识值
   */
  @Override
  public String getIdentityValue() {
    String identityValue = "";
    
    // 设置 首先以 Authorization 为准
    Map<String, Cookie> map = HttpContextUtil.getCookieMap();
    
    String accountIdentityProperty = MembershipConfigurationView.getInstance().getAccountIdentityCookieToken();
    
    boolean isCookie = map.containsKey(accountIdentityProperty);
    
    if (isCookie) {
      identityValue = map.get(accountIdentityProperty).getValue();
    }
    
    if (StringUtil.isNullOrEmpty(identityValue)) {
      // 默认获取 AccessToken
      identityValue = getAccessToken();
    }
    
    if (StringUtil.isNullOrEmpty(identityValue)) {
      InternalLogger.getLogger().warn("session ticket is null.");
    } else {
      // 调试信息
      // InternalLogger.getLogger().debug("session ticket:{}", identityValue);
    }
    
    return identityValue;
  }
  
  /**
   * 获取访问令牌
   */
  public String getAccessToken() {
    String accessToken = null;
    
    // Headers 权重优先表单内容
    if (HttpContextUtil.getRequest().getHeader(HTTP_HEADER_AUTHORIZATION) == null) {
      // 支持直接的表单
      accessToken = HttpContextUtil.getRequest().getParameter("accessToken");
      
      if (StringUtil.isNullOrEmpty(accessToken)) {
        accessToken = HttpContextUtil.getRequest().getParameter("access_token");
      }
      return accessToken;
    } else {
      // 支持 Headers Authorization
      String authorization = HttpContextUtil.getRequest().getHeader(HTTP_HEADER_AUTHORIZATION);
      
      if (authorization.startsWith("Bearer ")) {
        accessToken = authorization.replace("Bearer ", "");
      }
      
      if (StringUtil.isNullOrEmpty(accessToken)) {
        accessToken = authorization;
      }
      
      return accessToken;
    }
  }
  
  /**
   * 登录
   *
   * @return {@link Account} 实例
   */
  @Override
  public Account login(String loginName, String password, boolean isCreatePersistent) {
    return login(LoginType.LOGIN_NAME, loginName, password, isCreatePersistent);
  }
  
  /**
   * 登录
   *
   * @param loginOjbetId 登录对象标识
   * @param password     密码
   * @param loginType    登录类型 LoginName | Mobile | Email
   * @return {@link Account} 实例
   */
  @Override
  public Account login(LoginType loginType, String loginOjbetId, String password, boolean isCreatePersistent) {
    Account account = null;
    
    if (loginType == LoginType.LOGIN_NAME) {
      account = MembershipManagement.getInstance().getAccountService().loginCheck(loginOjbetId, password);
    } else {
      account =  MembershipManagement.getInstance().getAccountService().loginCheck(loginType, loginOjbetId, password );
    }
    
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
   * 退出
   */
  @Override
  public int logout() {
    String accountIdentity = getIdentityValue();
    
    removeSession(accountIdentity);
    
    return 0;
  }
  
  /**
   * 修改成 Redis 控制
   */
  @Override
  public Map<String, Account> getSessions() {
    HashMap hashMap = new HashMap<String, Account>();
    
    return hashMap;
  }
  
  /**
   * 新增会话
   */
  public void addSession(String sessionId, Account account) {
    addSession(TEXT_EMPTY, sessionId, account);
  }
  
  /**
   * 新增会话
   */
  @Override
  public void addSession(String appKey, String sessionId, Account account) {
    if (account == null) {
      SessionsContext.getInstance().getTicketService().delete(sessionId);
    } else if (!SessionsContext.getInstance().contains(sessionId)) {
      // 记录用户登录的时间
      account.setLoginDate(new Date());
      SessionsContext.getInstance().write(strategy, appKey, sessionId, account);
    }
  }
  
  /**
   * 移除会话
   */
  @Override
  public void removeSession(String sessionId) {
    SessionsContext.getInstance().getTicketService().delete(sessionId);
  }
  
  /**
   * 获取会话帐号信息
   */
  public Account getSessionAccount(String sessionId) {
    Account account = null;
    
    if (SessionsContext.getInstance().getTicketService().isExist(sessionId)) {
      // 持久化缓存信息
      account = SessionsContext.getInstance().getAuthAccount(strategy, sessionId);
    }
    
    if (account == null) {
      InternalLogger.getLogger().warn("session ticket:{}, but account is null.", sessionId);
      return null;
    }
    
    return account;
  }
  
  /**
   * 重置所有会话
   */
  @Override
  public void resetSessions() {
    SessionsContext.getInstance().getTicketService().clear();
  }
}
