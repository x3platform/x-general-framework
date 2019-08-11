package com.x3platform.sessions.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.membership.Account;
import com.x3platform.sessions.Ticket;
import com.x3platform.sessions.TicketStorageStrategy;
import com.x3platform.sessions.configuration.SessionsConfigurationView;
import com.x3platform.sessions.mappers.TicketMapper;
import com.x3platform.sessions.services.TicketService;
import com.x3platform.util.StringUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class TicketServiceImpl implements TicketService {

  /**
   * 日志记录器
   */
  private Logger logger = KernelContext.getLog();

  @Autowired
  private TicketMapper provider = null;

  private Map<String, Ticket> cacheStorage = new HashMap<String, Ticket>();

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
   * @param appKey App Key
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

    if (cacheStorage.containsKey(ticketId)) {
      return cacheStorage.get(ticketId);
    } else {
      Ticket accountCache = findByAccountIdentity(ticketId);

      if (accountCache != null && !cacheStorage.containsKey(ticketId)) {
        cacheStorage.put(ticketId, accountCache);
      }

      return accountCache;
    }
  }

  /**
   * 根据帐号缓存值读取帐号缓存信息
   *
   * @param ticketValue 帐号缓存值
   */
  @Override
  public Ticket readWithTicketValue(String ticketValue) {
    return findByTicketValue(ticketValue);
  }

  /**
   * 写入信息
   *
   * @param strategy 策略
   * @param appKey AppKey
   * @param ticketId 帐号会话唯一标识
   * @param account 帐号信息
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  @Override
  public void write(TicketStorageStrategy strategy, String appKey, String ticketId,
    Account account) {
    // 过滤空值
    if (StringUtil.isNullOrEmpty(ticketId)) {
      return;
    }

    Ticket param = strategy.serialize(appKey, ticketId, account);

    param.setCreatedDate(java.time.LocalDateTime.now());

    // 更新字典信息
    if (cacheStorage.containsKey(param.getTicketId())) {
      cacheStorage.put(param.getTicketId(), param);
    } else {
      cacheStorage.put(param.getTicketId(), param);
    }

    // 更新数据库信息
    if (isExist(param.getTicketId())) {
      update(param);
    } else {
      param.setValidFrom(java.time.LocalDateTime.now());
      param.setValidTo(LocalDateTime.now()
        .plusHours(SessionsConfigurationView.getInstance().getSessionTimeLimit()));

      insert(param);
    }
  }

  /**
   * 根据查找某条记录
   *
   * @param ticketId 帐号会话唯一标识
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  @Override
  public Ticket findByAccountIdentity(String ticketId) {
    return provider.findByTicketId(ticketId);
  }

  /**
   * 根据缓存的值查找某条记录
   *
   * @param ticketValue 缓存的值
   * @return 返回一个 Ticket 实例的详细信息
   */
  @Override
  public Ticket findByTicketValue(String ticketValue) {
    return provider.findByTicketValue(ticketValue);
  }

  /**
   * 转储所有记录信息
   *
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  @Override
  public List<Ticket> dump() {
    return provider.dump();
  }

  /**
   * 转储所有记录信息
   *
   * @param ticketValue 帐号缓存的值
   * @return 返回一个 {@link Ticket} 列表
   */
  @Override
  public List<Ticket> dump(String ticketValue) {
    return provider.dump(ticketValue);
  }

  /**
   * 添加记录
   *
   * @param param 实例{@link Ticket}的详细信息
   */
  @Override
  public void insert(Ticket param) {
    provider.insert(param);
  }

  /**
   * 修改记录
   *
   * @param param 实例 {@link Ticket} 的详细信息
   */
  @Override
  public void update(Ticket param) {
    provider.updateByPrimaryKey(param);
  }

  /**
   * 删除记录
   *
   * @param ticketId 帐号标识
   */
  @Override
  public int delete(String ticketId) {
    return provider.delete(ticketId);
  }

  /**
   * 检测记录是否存在
   */
  @Override
  public boolean isExist(String ticketId) {
    return provider.isExist(ticketId);
  }

  /**
   * 检测记录是否存在
   */
  @Override
  public boolean isExistTicketValue(String ticketId) {
    return provider.isExistTicketValue(ticketId);
  }

  /**
   * 清理过期时间之前的缓存记录
   *
   * @param expiryTime 过期时间
   */
  @Override
  public int clear(java.time.LocalDateTime expiryTime) {
    return provider.clear(expiryTime);
  }

  /**
   * 清空缓存记录
   */
  @Override
  public int clear() {
    return provider.clear(java.time.LocalDateTime.now());
  }
}
