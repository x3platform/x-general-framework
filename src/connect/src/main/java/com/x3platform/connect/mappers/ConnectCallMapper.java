package com.x3platform.connect.mappers;

import com.x3platform.connect.models.ConnectCall;

import java.util.*;

/**
 */
public interface ConnectCallMapper {
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
   * @param params 查询参数集合
   * @return 返回所有 ConnectCall 实例的详细信息
   */
  List<ConnectCall> findAll(Map params);

  // -------------------------------------------------------
  // 保存 添加 修改 删除
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
   * @param id 标识,多个以逗号隔开
   */
  int delete(String id);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /** 分页函数
   @param startIndex 开始行索引数,由0开始统计
   @param pageSize 页面大小
   @param query 数据查询参数
   @param rowCount 行数
   @return 返回一个列表实例
   */
  // List<ConnectCall> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 唯一标识
   * @return 布尔值
   */
  boolean isExist(String id);
}
