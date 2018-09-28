package com.x3platform.sessions;

import com.x3platform.membership.*;

/**
 * 帐号存储策略接口
 */
public interface IAccountStorageStrategy {

  /**
   * 反序列化缓存信息信息
   *
   * @return 帐号信息
   */
  IAccountInfo deserialize(AccountCacheInfo accountCache);

  /**
   * 序列化缓存信息信息
   *
   * @param accountIdentity 帐号会话唯一标识
   * @param account         帐号信息
   * @return 帐号缓存信息
   */
  AccountCacheInfo serialize(String accountIdentity, IAccountInfo account);

  /**
   * 序列化缓存信息信息
   *
   * @param accountIdentity 帐号会话唯一标识
   * @param account         帐号信息
   * @return 帐号缓存信息
   */
  AccountCacheInfo serialize(String appKey, String accountIdentity, IAccountInfo account);
}
