package com.x3platform.sessions.services.impl;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.membership.Account;
import com.x3platform.sessions.Ticket;
import com.x3platform.sessions.TicketStorageStrategy;
import com.x3platform.sessions.configuration.SessionsConfigurationView;
import com.x3platform.sessions.mappers.TicketMapper;
import com.x3platform.sessions.services.TicketService;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class TicketServiceImpl implements TicketService {
  
  private static final String CACHE_KEY_TICKET_ID_PREFIX = "x3platform:sessions:ticket:";
  private static final String CACHE_KEY_TICKET_VALUE_PREFIX = "x3platform:sessions:ticket-value:";
  
  // @Autowired
  // private TicketMapper provider = null;
  
  /**
   * 获取当前帐号缓存信息
   *
   * @param strategy 帐号存储策略
   */
  @Override
  public Account getAuthAccount(TicketStorageStrategy strategy, String ticketId) {
    Ticket param = read(ticketId);
    
    return strategy.deserialize(param);
  }
  
  /**
   * 查找授权信息
   *
   * @param ticketId 帐号会话唯一标识
   */
  @Override
  public boolean authorize(String ticketId) {
    return false;
  }
  
  /**
   * 查找授权信息
   *
   * @param ticketId 帐号会话唯一标识
   * @param appKey   App Key
   */
  @Override
  public boolean authorize(String ticketId, String appKey) {
    return false;
  }
  
  /**
   * 根据帐号标识读取帐号缓存信息
   *
   * @param ticketId 帐号标识
   */
  @Override
  public Ticket read(String ticketId) {
    // 过滤空值
    if (StringUtil.isNullOrEmpty(ticketId)) {
      return null;
    }
    
    String key = CACHE_KEY_TICKET_ID_PREFIX + ticketId;
    
    if (CachingManager.contains(key)) {
      return (Ticket) CachingManager.get(key);
    } else {
      Ticket ticket = findByTicketId(ticketId);
      
      if (ticket != null && !CachingManager.contains(key)) {
        CachingManager.set(key, ticket);
      }
      
      return ticket;
    }
  }
  
  /**
   * 根据帐号缓存值读取帐号缓存信息
   *
   * @param ticketValue 帐号缓存值
   */
  @Override
  public Ticket readWithTicketValue(String ticketValue) {
    // 过滤空值
    if (StringUtil.isNullOrEmpty(ticketValue)) {
      return null;
    }
    
    String key = CACHE_KEY_TICKET_VALUE_PREFIX + ticketValue;
    
    if (CachingManager.contains(key)) {
      return (Ticket) CachingManager.get(key);
    } else {
      Ticket ticket = findByTicketValue(ticketValue);
      
      if (ticket != null && !CachingManager.contains(key)) {
        CachingManager.set(key, ticket);
      }
      
      return ticket;
    }
  }
  
  /**
   * 写入信息
   *
   * @param strategy 策略
   * @param appKey   AppKey
   * @param ticketId 帐号会话唯一标识
   * @param account  帐号信息
   */
  @Override
  public void write(TicketStorageStrategy strategy, String appKey, String ticketId, Account account) {
    // 过滤空值
    if (StringUtil.isNullOrEmpty(ticketId)) {
      return;
    }
    
    Ticket ticket = strategy.serialize(appKey, ticketId, account);
    
    // 添加缓存项
    if (!StringUtil.isNullOrEmpty(ticket.getTicketId())) {
      String key = CACHE_KEY_TICKET_ID_PREFIX + ticket.getTicketId();
      CachingManager.set(key, ticket, SessionsConfigurationView.getInstance().getSessionTimeLimit());
    }
    
    if (!StringUtil.isNullOrEmpty(ticket.getTicketValue())) {
      String key = CACHE_KEY_TICKET_VALUE_PREFIX + ticket.getTicketValue();
      CachingManager.set(key, ticket.getTicketId(), SessionsConfigurationView.getInstance().getSessionTimeLimit());
    }
    
    // 更新数据库信息
//    if (isExist(ticket.getTicketId())) {
//      ticket.setValidFrom(java.time.LocalDateTime.now());
//      ticket.setValidTo(LocalDateTime.now().plusHours(SessionsConfigurationView.getInstance().getSessionTimeLimit()));
//
//      update(ticket);
//    } else {
//      ticket.setValidFrom(java.time.LocalDateTime.now());
//      ticket.setValidTo(LocalDateTime.now().plusHours(SessionsConfigurationView.getInstance().getSessionTimeLimit()));
//      ticket.setCreatedDate(java.time.LocalDateTime.now());
//
//      insert(ticket);
//    }
  }
  
  /**
   * 根据查找某条记录
   *
   * @param ticketId 帐号会话唯一标识
   * @return 一个实例 {@link Ticket} 的详细信息
   */
  @Override
  public Ticket findByTicketId(String ticketId) {
    if (!StringUtil.isNullOrEmpty(ticketId)) {
      String key = CACHE_KEY_TICKET_ID_PREFIX + ticketId;
      if (CachingManager.contains(key)) {
        return (Ticket) CachingManager.get(key);
      }
    }
    
    return null;
  }
  
  /**
   * 根据缓存的值查找某条记录
   *
   * @param ticketValue 缓存的值
   * @return 一个 {@link Ticket} 实例的详细信息
   */
  @Override
  public Ticket findByTicketValue(String ticketValue) {
    if (!StringUtil.isNullOrEmpty(ticketValue)) {
      String key = CACHE_KEY_TICKET_VALUE_PREFIX + ticketValue;
      if (CachingManager.contains(key)) {
        // 查找过程 ticketValue -> ticketId -> ticket
        String ticketId = (String) CachingManager.get(key);
        return findByTicketId(ticketId);
      }
    }
    
    return null;
  }
  
  /**
   * 转储所有记录信息
   *
   * @return 一个实例 {@link Ticket} 的详细信息
   */
  @Override
  public List<Ticket> dump() {
    List<Ticket> list = new ArrayList<Ticket>();
    Set<String> keys = CachingManager.keys(CACHE_KEY_TICKET_VALUE_PREFIX + "*");
    for (String key : keys) {
      Ticket ticket = findByTicketValue(key);
      list.add(ticket);
    }
    return list;
  }
  
  /**
   * 转储所有记录信息
   *
   * @param ticketValue 帐号缓存的值
   * @return 一个 {@link Ticket} 列表
   */
  @Override
  public List<Ticket> dump(String ticketValue) {
    List<Ticket> list = new ArrayList<Ticket>();
    Ticket ticket = findByTicketValue(ticketValue);
    if (ticket != null) {
      list.add(ticket);
    }
    return list;
  }
  
  /**
   * 删除记录
   *
   * @param ticketId 帐号标识
   */
  @Override
  public int delete(String ticketId) {
    Ticket ticket = findByTicketId(ticketId);
    
    if (ticket != null) {
      // 删除缓存项
      if (!StringUtil.isNullOrEmpty(ticket.getTicketId())) {
        String key = CACHE_KEY_TICKET_ID_PREFIX + ticket.getTicketId();
        CachingManager.delete(key);
      }
      
      if (!StringUtil.isNullOrEmpty(ticket.getTicketValue())) {
        String key = CACHE_KEY_TICKET_VALUE_PREFIX + ticket.getTicketValue();
        CachingManager.delete(key);
      }
    }
    
    return 0;
  }
  
  /**
   * 检测记录是否存在
   */
  @Override
  public boolean isExist(String ticketId) {
    if (StringUtil.isNullOrEmpty(ticketId)) {
      return false;
    } else {
      return CachingManager.contains(CACHE_KEY_TICKET_ID_PREFIX + ticketId);
    }
  }
  
  /**
   * 检测记录是否存在
   */
  @Override
  public boolean isExistTicketValue(String ticketValue) {
    if (StringUtil.isNullOrEmpty(ticketValue)) {
      return false;
    } else {
      return CachingManager.contains(CACHE_KEY_TICKET_VALUE_PREFIX + ticketValue);
    }
  }
  
  /**
   * 清空缓存记录
   */
  @Override
  public int clear() {
    // 删除缓存项
    CachingManager.deleteByPattern(CACHE_KEY_TICKET_ID_PREFIX + "*");
    CachingManager.deleteByPattern(CACHE_KEY_TICKET_VALUE_PREFIX + "*");
    
    return 0;
  }
}
