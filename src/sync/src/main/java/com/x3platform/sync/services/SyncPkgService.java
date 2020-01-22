package com.x3platform.sync.services;

import com.x3platform.data.DataQuery;
import com.x3platform.sync.models.SyncPkg;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 同步数据包管理
 *
 * @author ruanyu
 */
public interface SyncPkgService {

  // -------------------------------------------------------
  // 新增 删除
  // -------------------------------------------------------

  /**
   * 新增记录
   *
   * @param entity {@link SyncPkg} 实例 详细信息
   * @return 消息代码 0=表示成功
   */
  int add(SyncPkg entity);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  int remove(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link SyncPkg} 实例的详细信息
   */
  SyncPkg findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有 {@link SyncPkg} 实例的详细信息
   */
  List<SyncPkg> findAll(DataQuery query);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 获取最后一个更新包的结束时间
   *
   * @return 时间
   */
  LocalDateTime getLastPkgEndDate();

  /**
   * 创建和发送更新包
   *
   * @return 消息代码
   */
  int createAndSend();

  /**
   * 创建更新包
   *
   * @param beginDate 开始时间
   * @param endDate 结束时间
   * @return 消息代码
   */
  int createPackages(LocalDateTime beginDate, LocalDateTime endDate) throws FileNotFoundException;

  /**
   * 获取需要更新的数据
   *
   * @param tableName 数据表名
   * @param fields 数据字段名称
   * @param beginDate 开始时间
   * @param endDate 结束时间
   * @return 数据列表
   */
  List<Map> fetchData(String tableName, String fields, LocalDateTime beginDate, LocalDateTime endDate);

  /**
   * 获取需要更新的数据
   *
   * @param tableName 数据表名
   * @param fields 数据字段名称
   * @param modifiedDateField 修改时间的字段名称
   * @param beginDate 开始时间
   * @param endDate 结束时间
   * @return 数据列表
   */
  List<Map> fetchData(String tableName, String fields, String modifiedDateField,
    LocalDateTime beginDate, LocalDateTime endDate);

  /**
   * 获取需要更新的数据
   *
   * @param applicationId 应用标识
   * @param tableName 数据表名
   * @param fields 数据字段名称
   * @param modifiedDateField 修改时间的字段名称
   * @param applicationIdField 应用标识的字段名称
   * @param beginDate 开始时间
   * @param endDate 结束时间
   * @return 数据列表
   */
  List<Map> fetchData(String applicationId, String tableName, String fields,
    String modifiedDateField, String applicationIdField, LocalDateTime beginDate, LocalDateTime endDate);

  /**
   * 发送当天的更新更新包
   *
   * @return 消息代码
   */
  int send();

  /**
   * 发送某个时间之后的更新包
   *
   * @param date 更新包的起始时间
   * @return 消息代码
   */
  int send(LocalDateTime date);

  /**
   * 发送某个更新包之后的更新包
   *
   * @param queueId 队列标识
   * @param pkgId 更新包的起始标识
   * @return 消息代码
   */
  int send(String queueId, String pkgId);

  /**
   * 发送数据
   *
   * @param queueName 队列名称
   * @param filePath 文件路径
   */
  int sendData(String queueName, String filePath);

  /**
   * 接收更新包
   *
   * @return 消息代码
   */
  int receive();

  /**
   * 同步包数据
   *
   * @return 消息代码
   */
  int syncFromPackPage(InputStream stream);
}
