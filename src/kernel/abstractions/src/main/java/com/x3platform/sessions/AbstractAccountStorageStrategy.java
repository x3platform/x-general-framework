package com.x3platform.sessions;

import com.x3platform.KernelContext;
import com.x3platform.membership.IAccountInfo;

/**
 */
public abstract class AbstractAccountStorageStrategy implements IAccountStorageStrategy {
  /**
   */
  public AbstractAccountStorageStrategy() {
  }

  /**
   * 反序列化缓存信息信息
   *
   * @return
   */
  @Override
  public IAccountInfo deserialize(AccountCacheInfo accountCache) {
    if (accountCache == null) {
      return null;
    }

    return this.deserializeObject(accountCache);
  }

  /**
   * 将缓存信息反序列化为帐号信息
   *
   * @return
   */
  protected abstract IAccountInfo deserializeObject(AccountCacheInfo accountCache);

  /**
   * 序列化缓存信息
   *
   * @return
   */
  @Override
  public AccountCacheInfo serialize(String sessionId, IAccountInfo account) {
    return serialize("", sessionId, account);
  }

  /**
   * 序列化缓存信息
   *
   * @return
   */
  @Override
  public AccountCacheInfo serialize(String appKey, String sessionId, IAccountInfo account) {
    AccountCacheInfo accountCache = new AccountCacheInfo();

    accountCache.setAccountIdentity(sessionId);
    accountCache.setAppKey(appKey);
    accountCache.setAccountCacheValue(account.getLoginName());
    accountCache.setAccountObject(this.serializeObject(account));
    accountCache.setAccountObjectType(KernelContext.parseObjectType(account.getClass()));
    accountCache.setIP(account.getIP());

    // accountCache.HttpUserAgent = HttpContext.Current == null ? "" : HttpContext.Current.Request.UserAgent;

    accountCache.setValidFrom(java.time.LocalDateTime.now());

    // accountCache.setValidTo (accountCache.getValidFrom().addMonths(3));

    accountCache.setDate(java.time.LocalDateTime.now());

    return accountCache;
  }

  /**
   * 将帐号信息序列化为字符串信息
   *
   * @return
   */
  protected String serializeObject(IAccountInfo account) {
    StringBuilder outString = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n");

    outString.append("<accountObject>");
    outString.append(String.format("<id><![CDATA[%1$s]]></id>", account.getId()));
    outString.append(String.format("<code><![CDATA[%1$s]]></code>", account.getCode()));
    outString.append(String.format("<name><![CDATA[%1$s]]></name>", account.getName()));
    outString.append(String.format("<loginName><![CDATA[%1$s]]></loginName>", account.getLoginName()));
    outString.append(String.format("<globalName><![CDATA[%1$s]]></globalName>", account.getGlobalName()));
    outString.append(String.format("<displayName><![CDATA[%1$s]]></displayName>", account.getDisplayName()));
    outString.append(String.format("<type><![CDATA[%1$s]]></type>", account.getType()));
    outString.append(String.format("<certifiedAvatar><![CDATA[%1$s]]></certifiedAvatar>", account.getCertifiedAvatar()));
    outString.append(String.format("<certifiedEmail><![CDATA[%1$s]]></certifiedEmail>", account.getCertifiedEmail()));
    outString.append(String.format("<certifiedMobile><![CDATA[%1$s]]></certifiedMobile>", account.getCertifiedMobile()));
    outString.append(String.format("<ip><![CDATA[%1$s]]></ip>", account.getIP()));
    outString.append("</accountObject>");

    return outString.toString();
  }
}
