package com.x3platform.security.authentication;

import com.x3platform.membership.Account;
import com.x3platform.sessions.TicketStorageStrategy;
import com.x3platform.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地验证请求管理
 *
 * @author ruanyu
 */
public class LocalAuthenticationManagement implements AuthenticationManagement {
  /**
   * 缓存存储
   */
  protected Map<String, Account> cacheStorage = new HashMap<String, Account>();
  
  /**
   * 帐号存储策略
   */
  protected TicketStorageStrategy strategy = null;
  
  private Object cacheSyncRootObject;
  
  /**
   * 默认构造函数
   */
  public LocalAuthenticationManagement() {
    strategy = null;
  }
  
  /**
   * 锁
   */
  protected Object getCacheSyncRoot() {
    if (cacheSyncRootObject == null) {
      // RefObject<Object> tempRef_cacheSyncRootObject = new RefObject<Object>(this.cacheSyncRootObject);
      // Interlocked.CompareExchange(tempRef_cacheSyncRootObject, new Object(), null);
      // this.cacheSyncRootObject = tempRef_cacheSyncRootObject.argValue;
    }
    
    return cacheSyncRootObject;
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
    return "token";
  }
  
  /**
   *
   */
  @Override
  public String getIdentityValue() {
    
    return "";
  }
  
  /**
   *
   */
  public String getAccessToken() {
    
    return "";
  }
  
  /**
   * 获取认证的用户信息
   */
  @Override
  public Account getAuthUser() {
    return null;
  }
  
  /**
   * 登录
   *
   * @return 帐号信息
   */
  @Override
  public Account login(String loginName, String password, boolean isCreatePersistent) {
    return null;
  }
  
  /**
   * 登录
   *
   * @return 帐号信息
   */
  @Override
  public Account login(LoginType loginType, String objectIdentity, String password, boolean isCreatePersistent) {
    return null;
  }
  
  /**
   * 注销
   *
   * @return 消息代码
   */
  @Override
  public int logout() {
    String accountIdentity = getIdentityValue();
    
    removeSession(accountIdentity);
    
    return 0;
  }
  
  /**
   * @return
   */
  @Override
  public Map<String, Account> getSessions() {
    return new HashMap<String, Account>(cacheStorage);
  }
  
  /**
   * 新增会话
   */
  public void addSession(String sessionId, Account account) {
    addSession("", sessionId, account);
  }
  
  /**
   * 新增会话
   */
  @Override
  public void addSession(String appKey, String sessionId, Account account) {
    if (account == null) {
      cacheStorage.remove(sessionId);
    } else if (!cacheStorage.containsKey(sessionId)) {
      // 记录用户登录的时间
      account.setLoginDate(new Date());
      
      cacheStorage.put(sessionId, account);
    }
  }
  
  /**
   * 移除会话
   */
  @Override
  public void removeSession(String sessionId) {
    cacheStorage.remove(sessionId);
  }
  
  /**
   * 获取会话帐号信息
   */
  public Account getSessionAccount(String sessionId) {
    Account account = null;
    
    if (cacheStorage.containsKey(sessionId) && !StringUtil.isNullOrEmpty(sessionId)) {
      // 字典缓存信息
      account = cacheStorage.get(sessionId);
    }
    
    return account;
  }
  
  /**
   * 重置所有会话
   */
  @Override
  public void resetSessions() {
    cacheStorage.clear();
  }
}
