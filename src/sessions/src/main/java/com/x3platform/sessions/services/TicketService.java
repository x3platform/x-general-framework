package com.x3platform.sessions.services;

import com.x3platform.membership.Account;
import com.x3platform.sessions.Ticket;
import com.x3platform.sessions.TicketStorageStrategy;
import java.util.List;

/**
 * 帐号缓存服务接口
 *
 * @author ruanyu
 */
public interface TicketService {

  /**
   * 获取当前帐号缓存信息
   *
   * @param strategy 帐号存储策略
   * @param ticketId 帐号会话唯一标识
   */
  Account getAuthAccount(TicketStorageStrategy strategy, String ticketId);

  /**
   * 查找授权信息
   *
   * @param ticketId 帐号会话唯一标识
   */
  boolean authorize(String ticketId);

  /**
   * 查找授权信息
   *
   * @param ticketId 帐号会话唯一标识
   * @param appKey App Key
   */
  boolean authorize(String ticketId, String appKey);

  /**
   * 查找缓存记录
   *
   * @param ticketId 帐号会话唯一标识
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  Ticket read(String ticketId);

  /**
   * 查找缓存记录
   *
   * @param accountTicketValue 缓存的值
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  Ticket readWithTicketValue(String accountTicketValue);

  /**
   * 写入信息
   *
   * @param strategy 策略
   * @param appKey AppKey
   * @param ticketId 帐号会话唯一标识
   * @param account 帐号信息
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  void write(TicketStorageStrategy strategy, String appKey, String ticketId, Account account);

  /**
   * 根据查找某条记录
   *
   * @param ticketId 帐号会话唯一标识
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  Ticket findByAccountIdentity(String ticketId);

  /**
   * 查找某条记录
   *
   * @param accountTicketValue 帐号缓存的值
   * @return 实例 {@link Ticket} 的详细信息
   */
  Ticket findByTicketValue(String accountTicketValue);

  /**
   * 转储所有记录信息
   *
   * @return 返回一个 {@link Ticket} 列表
   */
  List<Ticket> dump();

  /**
   * 转储所有记录信息
   *
   * @param accountTicketValue 帐号缓存的值
   * @return 返回一个 {@link Ticket} 列表
   */
  List<Ticket> dump(String accountTicketValue);

  /**
   * 添加记录
   *
   * @param param 实例 {@link Ticket} 的详细信息
   */
  void insert(Ticket param);

  /**
   * 更新记录
   *
   * @param param 实例 {@link Ticket} 的详细信息
   */
  void update(Ticket param);

  /**
   * 删除记录
   *
   * @param ticketId 帐号会话唯一标识
   */
  int delete(String ticketId);

  /**
   * 检测记录是否存在
   *
   * @param ticketId 帐号会话唯一标识
   */
  boolean isExist(String ticketId);

  /**
   * 检测记录是否存在
   *
   * @param accountTicketValue 帐号缓存值
   */
  boolean isExistTicketValue(String accountTicketValue);

  /**
   * 清理过期时间之前的缓存记录
   *
   * @param expiryTime 过期时间
   */
  int clear(java.time.LocalDateTime expiryTime);

  /**
   * 清空缓存记录
   */
  int clear();
}
