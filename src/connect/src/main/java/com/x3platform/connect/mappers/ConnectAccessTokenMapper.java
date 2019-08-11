package com.x3platform.connect.mappers;

import com.x3platform.connect.models.ConnectAccessToken;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 */
public interface ConnectAccessTokenMapper {

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ConnectAccessToken Id号
   * @return 返回一个  ConnectAccessToken 实例的详细信息
   */
  ConnectAccessToken findOne(String id);

  /**
   * 查询某条记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  ConnectAccessToken findOneByAccountId(@Param("app_key") String appKey, @Param("account_id") String accountId);

  /**
   * 查询某条记录
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  ConnectAccessToken findOneByRefreshToken(@Param("app_key") String appKey, @Param("refresh_token") String refreshToken);

  /**
   * 查询多端用户登录情况
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回多个实例 ConnectAccessToken 的详细信息
   */
  List<ConnectAccessToken> findAllByRefreshToken(@Param("app_key") String appKey, @Param("refresh_token") String refreshToken);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有  ConnectAccessToken 实例的详细信息
   */
  List<ConnectAccessToken> findAll(Map params);

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  int insert(ConnectAccessToken record);

  int updateByPrimaryKey(ConnectAccessToken record);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int delete(String id);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  ///#region 函数:GetPaging(int startIndex, int pageSize, DataQuery query, out int rowCount)
  /** 分页函数
   @param startIndex 开始行索引数,由0开始统计
   @param pageSize 页面大小
   @param query 数据查询参数
   @param rowCount 行数
   @return 返回一个列表实例
   */
  // List<ConnectAccessToken> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id ConnectAccessToken 实例详细信息
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 布尔值
   */
  boolean isExistAccountId(@Param("app_key") String appKey, @Param("account_id") String accountId);

  /**
   * 刷新帐号的访问令牌
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @param expireDate   过期时间
   * @return 消息代码
   */
  int refresh(@Param("app_key") String appKey, @Param("refresh_token") String refreshToken, @Param("expire_date") Date expireDate, @Param("next_refresh_token") String nextRefreshToken);

  /**
   * 清理过期时间之前的缓存记录
   *
   * @param expiryTime 过期时间
   */
  int clear(Date expiryTime);
}
