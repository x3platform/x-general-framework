package com.x3platform.membership.authentication;

import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.security.authentication.AuthenticationManagement;
import com.x3platform.sessions.GenericTicketStorageStrategy;
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

// import com.x3platform.membership.services.RedisTemplateService;

/**
 * 通用的验证请求管理 分三层， 第一层 本地缓存 ， 第二层 Redis 缓存 ， 第三层 数据库缓存
 */
@Component
public abstract class GenericAuthenticationManagement implements AuthenticationManagement {

  /**
   * 日志打印
   */
  // private static Logger logger = LoggerFactory.getLogger(GenericAuthenticationManagement.class);
  public static String hashKey = "account";
  // public RedisTemplateService redisTemplate = null;
  private Object cacheSyncRootObject;
  // 设置是否启用 redis 缓存，

  /**
   * 锁
   */
  protected Object cacheSyncRoot = new Object();

  /**
   * 内存存储
   */
  protected Map<String, Account> cacheStorage = new HashMap<String, Account>();

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

    return identityValue;
  }

  /**
   * 获取访问令牌
   */
  public String getAccessToken() {
    String accessToken = null;

    // Headers 权重优先表单内容
    if (HttpContextUtil.getRequest().getHeader("Authorization") == null) {
      // 支持直接的表单
      accessToken = HttpContextUtil.getRequest().getParameter("accessToken");

      if (StringUtil.isNullOrEmpty(accessToken)) {
        accessToken = HttpContextUtil.getRequest().getParameter("access_token");
      }
      return accessToken;
    } else {
      // 支持 Headers
      String authorization = HttpContextUtil.getRequest().getHeader("Authorization");

      if (authorization.startsWith("Bearer ")) {
        accessToken = authorization.replace("Bearer ", "");
      }

      // 当接入其他平台 集成的时候使用 AMI_ACCESS_TOKEN 默认使用  Authorization
      if (StringUtil.isNullOrEmpty(accessToken)) {
        accessToken = HttpContextUtil.getRequest().getHeader("AMI_ACCESS_TOKEN");
      }

      if (StringUtil.isNullOrEmpty(accessToken)) {
        accessToken = authorization;
      }

      return accessToken;
    }
  }

  /**
   * 获取认证的用户信息
   */
  @Override
  public abstract Account getAuthUser();

  /**
   * 登录
   *
   * @return {@link Account} 实例
   */
  @Override
  public Account login(String loginName, String password, boolean isCreatePersistent) {
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
   * 注销
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
//    if (redisTemplate == null) {
//      redisTemplate = MembershipManagement.getInstance().getIRedisTemplateService();
//    }
//    List<Object> hashAllValues = redisTemplate.hashGetHashAllValues(hashKey); // hashGetHashAllValues
//    logger.info("------------------->" + JSON.toJSONString(hashAllValues));
    HashMap hashMap = new HashMap<String, Account>();
    // return new HashMap<String, Account>(this.cacheStorage);
    return hashMap;
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

      if (SessionsConfigurationView.getInstance().getSessionPersistentMode()) {
        SessionsContext.getInstance().write(strategy, appKey, sessionId, account);
      }
    }
  }

  /**
   * 移除会话
   */
  @Override
  public void removeSession(String sessionId) {
    cacheStorage.remove(sessionId);

    if (SessionsConfigurationView.getInstance().getSessionPersistentMode()) {
      SessionsContext.getInstance().getTicketService().delete(sessionId);
    }
  }

  /**
   * 获取会话帐号信息
   */
  public Account getSessionAccount(String sessionId) {
    Account account = null;
    // 一级内置缓存
    if (cacheStorage.containsKey(sessionId) && !StringUtil.isNullOrEmpty(sessionId)) {
      // 字典缓存信息
      account = cacheStorage.get(sessionId);
    }

    if (account == null && SessionsConfigurationView.getInstance().getSessionPersistentMode()) {
      // 持久化缓存信息
      account = SessionsContext.getInstance().getAuthAccount(strategy, sessionId);
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
