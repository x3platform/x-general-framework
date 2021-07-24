package com.x3platform.membership.storages;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.x3platform.InternalLogger;
import com.x3platform.membership.Account;
import com.x3platform.membership.models.AccountInfo;
import com.x3platform.sessions.AbstractTicketStorageStrategy;
import com.x3platform.sessions.Ticket;

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
  protected Account deserializeObject(Ticket ticket) {
    Account account = null;
    try {
      account = JSON.parseObject(ticket.getAccountObject(), Account.class);
    } catch (JSONException e) {
      InternalLogger.getLogger().error("JSONException", e);
    }

    return account;
  }
}
