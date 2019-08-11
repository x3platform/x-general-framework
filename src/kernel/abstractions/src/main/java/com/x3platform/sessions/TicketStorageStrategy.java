package com.x3platform.sessions;

import com.x3platform.membership.Account;

/**
 * 会话票据存储策略接口
 */
public interface TicketStorageStrategy {

  /**
   * 反序列化缓存信息信息
   *
   * @param ticket 会话票据信息
   * @return 帐号信息
   */
  Account deserialize(Ticket ticket);

  /**
   * 序列化缓存信息信息
   *
   * @param ticketId 帐号会话票据标识
   * @param account 帐号信息
   * @return 帐号缓存信息
   */
  Ticket serialize(String ticketId, Account account);

  /**
   * 序列化缓存信息信息
   *
   * @param appKey App Key
   * @param ticketId 帐号会话票据标识
   * @param account 帐号信息
   * @return 帐号缓存信息
   */
  Ticket serialize(String appKey, String ticketId, Account account);
}
