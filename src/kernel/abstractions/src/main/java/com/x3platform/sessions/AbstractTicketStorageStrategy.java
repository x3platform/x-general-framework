package com.x3platform.sessions;

import com.alibaba.fastjson.JSON;
import com.x3platform.KernelContext;
import com.x3platform.membership.Account;
import java.time.LocalDateTime;

/**
 * 会话票据存储策略
 *
 * @author ruanyu
 */
public abstract class AbstractTicketStorageStrategy implements TicketStorageStrategy {

  /**
   *
   */
  public AbstractTicketStorageStrategy() {
  }

  /**
   * 反序列化缓存信息信息
   *
   * @return
   */
  @Override
  public Account deserialize(Ticket ticket) {
    if (ticket == null) {
      return null;
    }

    return deserializeObject(ticket);
  }

  /**
   * 将缓存信息反序列化为帐号信息
   *
   * @return
   */
  protected abstract Account deserializeObject(Ticket ticket);

  /**
   * 序列化缓存信息
   *
   * @return
   */
  @Override
  public Ticket serialize(String ticketId, Account account) {
    return serialize("", ticketId, account);
  }

  /**
   * 序列化缓存信息
   *
   * @return 会话票据信息
   */
  @Override
  public Ticket serialize(String appKey, String ticketId, Account account) {
    Ticket ticket = new Ticket();

    ticket.setTicketId(ticketId);
    ticket.setAppKey(appKey);
    ticket.setTicketValue(account.getLoginName());
    ticket.setAccountObject(serializeObject(account));
    ticket.setAccountObjectType(KernelContext.parseObjectType(account.getClass()));
    ticket.setIP(account.getIP());

    // ticket.HttpUserAgent = HttpContext.Current == null ? "" : HttpContext.Current.Request.UserAgent;

    ticket.setValidFrom(LocalDateTime.now());

    ticket.setValidTo(ticket.getValidFrom().plusDays(90));

    ticket.setCreatedDate(LocalDateTime.now());

    return ticket;
  }

  /**
   * 将帐号信息序列化为字符串信息
   *
   * @return JSON 格式的字符串
   */
  protected String serializeObject(Account account) {
    return JSON.toJSONString(account);
  }
}
