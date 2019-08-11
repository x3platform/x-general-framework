package com.x3platform.sessions;

import com.alibaba.fastjson.JSON;
import com.x3platform.membership.Account;
import com.x3platform.membership.models.AccountInfo;

/**
 */
public class GenericTicketStorageStrategy extends AbstractTicketStorageStrategy {

  /**
   */
  public GenericTicketStorageStrategy() {
  }

  /**
   * 将缓存信息反序列化为帐号信息
   */
  @Override
  protected Account deserializeObject(Ticket accountCache) {
    return JSON.parseObject(accountCache.getAccountObject(), AccountInfo.class);
  }
}
