package com.x3platform.connect.services;

import com.x3platform.connect.models.ConnectAccessToken;
import com.x3platform.data.DataQuery;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 连接访问令牌服务
 *
 * @author ruanyu
 */
public interface ConnectAccessTokenService {
  
  /**
   * 写入的帐号的访问令牌信息
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return {@link ConnectAccessToken} 实例
   */
  ConnectAccessToken write(String appKey, String accountId);
  
  /**
   * 刷新帐号的访问令牌
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @param expireDate   过期时间
   * @return 消息代码 0=表示成功
   */
  int refresh(String appKey, String refreshToken, LocalDateTime expireDate);
  
  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  int delete(String id);

  /**
   * 根据访问令牌查询某条记录
   *
   * @param id 访问令牌标识
   * @return 返回一个 {@link ConnectAccessToken} 实例的详细信息
   */
  ConnectAccessToken findOne(String id);

  /**
   * 根据刷新令牌查询某条记录
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回一个 {@link ConnectAccessToken} 实例的详细信息
   */
  ConnectAccessToken findOneByRefreshToken(String appKey, String refreshToken);

  /**
   * 清空缓存记录
   * @return 消息代码 0=表示成功
   */
  int clear();
}
