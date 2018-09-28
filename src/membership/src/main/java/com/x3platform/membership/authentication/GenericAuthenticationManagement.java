package com.x3platform.membership.authentication;

import com.x3platform.RefObject;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.security.authentication.IAuthenticationManagement;
import com.x3platform.sessions.IAccountStorageStrategy;
import com.x3platform.util.StringUtil;

import java.util.*;

/**
 * 通用的验证请求管理
 */
public abstract class GenericAuthenticationManagement implements IAuthenticationManagement {
  private Object cacheSyncRootObject;

  /**
   * 锁
   */
  protected Object getCacheSyncRoot() {
    if (this.cacheSyncRootObject == null) {
      // RefObject<Object> tempRef_cacheSyncRootObject = new RefObject<Object>(this.cacheSyncRootObject);
      // Interlocked.CompareExchange(tempRef_cacheSyncRootObject, new Object(), null);
      // this.cacheSyncRootObject = tempRef_cacheSyncRootObject.argValue;
    }

    return this.cacheSyncRootObject;
  }

  /**
   */
  // protected Map<String, IAccountInfo> cacheStorage = new SyncDictionary<String, IAccountInfo>();
  protected Map<String, IAccountInfo> cacheStorage = new HashMap<String, IAccountInfo>();

  /**
   * 帐号存储策略
   */
  protected IAccountStorageStrategy strategy = null;

  /**
   * 默认构造函数
   */
  public GenericAuthenticationManagement() {
    // FIXME
    // this.strategy = new GenericAccountStorageStrategy();
    this.strategy = null;
  }

  /**
   * 获取用户存储策略
   */
  public IAccountStorageStrategy getAccountStorageStrategy() {
    return this.strategy;
  }

  /**
   * 标识名称
   */
  public String getIdentityName() {
    return MembershipConfigurationView.getInstance().getAccountIdentityCookieToken();
  }

  /**
   */
  public String getIdentityValue() {
    String accountIdentityProperty = MembershipConfigurationView.getInstance().getAccountIdentityCookieToken();

    // FIXME 待处理
//    if (HttpContext.Current.Request.Cookies[accountIdentityProperty] == null)
//    {
//      return "";
//    }
//    else
//    {
//      return HttpContext.Current.Request.Cookies[accountIdentityProperty].Value;
//    }

    return "";
  }

  /**
   */
  public String getAccessToken() {
    // FIXME
//    // Headers 权重优先表单内容
//    if (HttpContext.Current.Request.Headers["Authorization"] == null) {
//      // 支持直接的表单
//      return RequestHelper.Fetch("accessToken", "access_token");
//    } else {
//      // 支持 Headers
//      return HttpContext.Current.Request.Headers["Authorization"];
//    }
    return "";
  }

  /**
   * 获取认证的用户信息
   */
  public abstract IAccountInfo getAuthUser();

  /**
   * 登录
   *
   * @return
   */
  public int login(String loginName, String password, boolean isCreatePersistent) {
    return 0;
  }

  /**
   * 注销
   *
   * @return
   */
  public int logout() {
    String accountIdentity = this.getIdentityValue();

    this.removeSession(accountIdentity);

    return 0;
  }

  /**
   * @return
   */
  public Map<String, IAccountInfo> getSessions() {
    return new HashMap<String, IAccountInfo>(this.cacheStorage);
  }

  /**
   * 新增会话
   */
  public void addSession(String sessionId, IAccountInfo account) {
    addSession("", sessionId, account);
  }

  /**
   * 新增会话
   */
  @Override
  public void addSession(String appKey, String sessionId, IAccountInfo account) {
    if (account == null) {
      this.cacheStorage.remove(sessionId);
    } else if (!this.cacheStorage.containsKey(sessionId)) {
      // 记录用户登录的时间
      account.setLoginDate(java.time.LocalDateTime.now());

      this.cacheStorage.put(sessionId, account);

      // FIXME
// if (SessionsConfigurationView.Instance.SessionPersistentMode.equals("ON")) {
//   SessionContext.Instance.Write(this.strategy, appKey, sessionId, account);
// }
    }
  }

  /**
   * 移除会话
   */
  public void removeSession(String sessionId) {
    this.cacheStorage.remove(sessionId);

    // FIXME
    // if (SessionsConfigurationView.Instance.SessionPersistentMode.equals("ON")) {
    //  SessionContext.Instance.AccountCacheService.Delete(sessionId);
    // }
  }

  /**
   * 获取会话帐号信息
   */
  public IAccountInfo getSessionAccount(String sessionId) {
    IAccountInfo account = null;

    if (this.cacheStorage.containsKey(sessionId) && !StringUtil.isNullOrEmpty(sessionId)) {
      // 字典缓存信息
      account = this.cacheStorage.get(sessionId);
    }

// FIXME
//    if (account == null && SessionsConfigurationView.Instance.SessionPersistentMode.equals("ON")) {
//      // 持久化缓存信息
//      account = SessionContext.Instance.<AccountInfo>GetAuthAccount(this.strategy, sessionId);
//    }

    return account;
  }

  /**
   * 重置所有会话
   */
  @Override
  public void resetSessions() {
    this.cacheStorage.clear();
  }
}
