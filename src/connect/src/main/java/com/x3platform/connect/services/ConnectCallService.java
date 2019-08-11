package com.x3platform.connect.services;

import com.x3platform.connect.models.ConnectCall;
import com.x3platform.data.DataQuery;

import java.util.*;

/**
 */
public interface ConnectCallService {
  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ConnectCall Id号
   * @return 返回一个 ConnectCall 实例的详细信息
   */
  ConnectCall findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 ConnectCall 实例的详细信息
   */
  List<ConnectCall> findAll(DataQuery query);

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param ConnectCall 实例详细信息
   * @return ConnectCall 实例详细信息
   */
  int save(ConnectCall param);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  int delete(String id);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id ConnectCall 实例详细信息
   * @return 布尔值
   */
  boolean isExist(String id);
}
