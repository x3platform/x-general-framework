package com.x3platform.connect.services.impl;

import com.x3platform.connect.configuration.ConnectConfigurationView;
import com.x3platform.connect.mappers.ConnectCallMapper;
import com.x3platform.connect.models.ConnectCall;
import com.x3platform.connect.services.ConnectCallService;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public final class ConnectCallServiceImpl implements ConnectCallService {

  @Autowired
  private ConnectCallMapper provider = null;

  /**
   * 队列
   */
  // private IMessageQueueObject queue = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param ConnectCall 实例详细信息
   * @return ConnectCall 实例详细信息
   */
  @Override
  public int save(ConnectCall param) {
    // 如果消息队列可用，则将数据发送到队列。
    // FIXME
//    if (ConnectConfigurationView.getInstance().MessageQueueMode.equals("ON") && queue.Enabled) {
//      queue.Send(param);
//
//      return param;
//    } else {
//      return this.provider.save(param);
//    }

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public int delete(String id) {
    this.provider.delete(id);
    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ConnectCall Id号
   * @return 返回一个 ConnectCall 实例的详细信息
   */
  @Override
  public ConnectCall findOne(String id) {
    return this.provider.findOne(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 ConnectCall 实例的详细信息
   */
  @Override
  public List<ConnectCall> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  ///#region 函数:GetPaging(int startIndex, int pageSize, DataQuery query, out int rowCount)

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例
   */
  // public List<ConnectCall> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //   return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return this.provider.isExist(id);
  }
}
