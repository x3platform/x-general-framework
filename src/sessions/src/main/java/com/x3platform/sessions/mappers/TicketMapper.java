package com.x3platform.sessions.mappers;

import com.x3platform.sessions.Ticket;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface TicketMapper {

  /**
   * 根据查找某条记录
   *
   * @param ticketId 帐号会话唯一标识
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  Ticket findByTicketId(@Param("ticket_id") String ticketId);

  /**
   * 查找某条记录
   *
   * @param ticketValue 帐号缓存的值
   * @return 返回一个实例 {@link Ticket} 的详细信息
   */
  Ticket findByTicketValue(@Param("ticket_value") String ticketValue);

  /**
   * 转储所有记录信息
   *
   * @return 返回一个 {@link Ticket} 列表
   */
  List<Ticket> dump();

  /**
   * 转储所有记录信息
   *
   * @param ticketValue 帐号缓存的值
   * @return 返回一个{@link Ticket}列表
   */
  List<Ticket> dump(@Param("ticket_") String ticketValue);

  /**
   * 添加记录
   *
   * @param record 实例 {@link Ticket} 的详细信息
   */
  void insert(Ticket record);

  /**
   * 修改记录
   *
   * @param record 实例 {@link Ticket} 的详细信息
   */
  void updateByPrimaryKey(Ticket record);

  /**
   * 删除记录
   *
   * @param ticketId 帐号会话唯一标识
   */
  int delete(@Param("value") String ticketId);

  /**
   * 检测记录是否存在
   *
   * @param ticketId 帐号会话唯一标识
   */
  boolean isExist(@Param("ticket_id") String ticketId);

  /**
   * 检测记录是否存在
   *
   * @param ticketValue 帐号缓存值
   */
  boolean isExistTicketValue(@Param("ticket_value") String ticketValue);

  /**
   * 清理过期时间之前的缓存记录
   *
   * @param expiryTime 过期时间
   */
  int clear(java.time.LocalDateTime expiryTime);
}
