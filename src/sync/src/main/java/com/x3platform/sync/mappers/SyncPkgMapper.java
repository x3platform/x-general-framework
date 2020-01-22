package com.x3platform.sync.mappers;

import com.x3platform.sync.models.SyncPkg;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 识别配置
 */
public interface SyncPkgMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link SyncPkg} 实例的详细信息
   * @return 受影响的行数
   */
  int insert(SyncPkg record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 受影响的行数
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 一个 {@link SyncPkg} 实例的详细信息
   */
  SyncPkg selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 所有 {@link SyncPkg} 实例的详细信息
   */
  List<SyncPkg> findAll(Map params);

  /**
   * 查询所有需要发送的更新包
   *
   * @param startPkgId 起始数据包标识
   * @return 所有 {@link SyncPkg} 实例的详细信息
   */
  List<SyncPkg> findAllByStartPkgId(@Param("id") String startPkgId);

  /**
   * 查询所有需要发送的更新包
   *
   * @param date 起始时间
   * @return 所有 {@link SyncPkg} 实例的详细信息
   */
  List<SyncPkg> findAllByStartDate(@Param("created_date") LocalDateTime date);

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
   * 获取需要更新的数据
   *
   * @param tableName 数据表名
   * @param fields 数据字段名称
   * @param modifiedDateField 修改时间的字段名称
   * @param beginDate 开始时间
   * @param endDate 结束时间
   * @return 需要更新的数据列表
   */
  List<Map> fetchData(
    @Param("table_name") String tableName,
    @Param("fields") String fields,
    @Param("modified_date_field") String modifiedDateField,
    @Param("begin_date") LocalDateTime beginDate,
    @Param("end_date") LocalDateTime endDate);

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
   * @return 需要更新的数据列表
   */
  List<Map> fetchDataByApplicationId(
    @Param("application_id") String applicationId,
    @Param("table_name") String tableName,
    @Param("fields") String fields,
    @Param("modified_date_field") String modifiedDateField,
    @Param("application_id_field") String applicationIdField,
    @Param("begin_date") LocalDateTime beginDate,
    @Param("end_date") LocalDateTime endDate);

  /**
   * 设置包的发送状态
   *
   * @param id 标识
   * @param isSend 是否发送 0 未发送 | 1 已发送
   * @return 受影响的行数
   */
  int setSend(@Param("id") String id, @Param("is_send") int isSend);

  /**
   * 获取需要更新的数据
   *
   * @param tableName 数据表名
   * @param id 唯一标识
   * @param data 参数
   * @return 需要更新的数据列表
   */
  int syncData(@Param("table_name") String tableName, @Param("id") String id, @Param("data") Map<String, Object> data);
}
