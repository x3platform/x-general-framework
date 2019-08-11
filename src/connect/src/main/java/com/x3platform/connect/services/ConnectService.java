package com.x3platform.connect.services;

import com.x3platform.connect.models.Connect;
import com.x3platform.data.DataQuery;

import java.util.List;

/**
 */
public interface ConnectService {

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id Connect Id号
   * @return 返回一个 Connect 实例的详细信息
   */
  Connect findOne(String id);

  /**
   * 查询某条记录
   *
   * @param appKey AppKey
   * @return 返回一个实例 Connect 的详细信息
   */
  Connect findOneByAppKey(String appKey);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 Connect 实例的详细信息
   */
  List<Connect> findAll(DataQuery query);

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param Connect 实例详细信息
   * @return 消息代码
   */
  int save(Connect param);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码
   */
  int delete(String id);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param appKey AppKey
   * @return 布尔值
   */
  boolean isExistAppKey(String appKey);

  /**
   * 重置应用链接器的密钥
   *
   * @param appKey AppKey
   * @return
   */
  int resetAppSecret(String appKey);

  /**
   * 重置应用链接器的密钥
   *
   * @param appKey    App Key
   * @param appSecret App Secret
   * @return
   */
  int resetAppSecret(String appKey, String appSecret);
}
