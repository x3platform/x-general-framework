package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationMenuInfo;
import com.x3platform.apps.models.ApplicationMenuQueryInfo;
import com.x3platform.membership.IAccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 *
 */
public interface ApplicationMenuMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 ApplicationMenuInfo 详细信息
   * @return ApplicationMenuInfo 实例详细信息
   */
  ApplicationMenuInfo save(ApplicationMenuInfo param);

  /**
   * 添加记录
   *
   * @param param ApplicationMenuInfo 实例的详细信息
   */
  void insert(ApplicationMenuInfo param);

  /**
   * 修改记录
   *
   * @param param ApplicationMenuInfo 实例的详细信息
   */
  void update(ApplicationMenuInfo param);

  /**
   * 删除记录
   *
   * @param id 实例的标识信息
   */
  void delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ApplicationMenuInfo Id号
   * @return 返回一个 ApplicationMenuInfo 实例的详细信息
   */
  ApplicationMenuInfo findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 ApplicationMenuInfo 实例的详细信息
   */
  List<ApplicationMenuInfo> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param params 查询条件
   * @return 返回所有 ApplicationMenuQueryInfo 实例的详细信息
   */
  List<ApplicationMenuQueryInfo> findAllQueryObject(Map params);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex  开始行索引数,由0开始统计
   * @param pageSize    页面大小
   * @param whereClause WHERE 查询条件
   * @param orderBy     ORDER BY 排序条件.
   * @param rowCount    记录行数
   * @return 返回一个列表
   */
  // List<ApplicationMenuInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务编码
   * @return 布尔值
   */
  boolean isExist(String id);

  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  /**
   * 判断用户是否拥有实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param account       帐号信息
   * @return 布尔值
   */
  boolean hasAuthority(String entityId, String authorityName, IAccountInfo account);

  /**
   * 绑定实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  void bindAuthorizationScopeObjects(String entityId, String authorityName, String scopeText);

  /**
   * 查询实体对象的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @return
   */
  // List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String entityId, String authorityName);
}
