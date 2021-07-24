package com.x3platform.connect.mappers;

import com.x3platform.connect.models.Connect;

import java.util.*;

/**
 */
public interface ConnectMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param {@link Connect}实例详细信息
   * @return {@link Connect}实例详细信息
   */
  int save(Connect param);

  /**
   * 添加记录
   *
   * @param param {@link Connect}实例的详细信息
   */
  void insert(Connect param);

  /**
   * 修改记录
   *
   * @param param {@link Connect}实例的详细信息
   */
  void update(Connect param);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int delete(String id);

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
   * @param params 查询参数集合
   * @return 返回所有 Connect 实例的详细信息
   */
  List<Connect> findAll(Map params);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例 Connect
   */
  // List<Connect> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例 Connect
   */
  // List<ConnectQueryInfo> GetQueryObjectPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 唯一标识
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
   * @param appKey    App Key
   * @param appSecret App Secret
   * @return
   */
  int resetAppSecret(String appKey, String appSecret);
}
