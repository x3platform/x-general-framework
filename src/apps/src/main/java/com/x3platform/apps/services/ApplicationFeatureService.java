package com.x3platform.apps.services;

import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationScopeInfo;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;

import java.util.List;
import java.util.Map;

/**
 * 应用功能服务器接口
 *
 * @author ruanyu
 */
public interface ApplicationFeatureService {


  /**
   * 保存
   * @throws Exception
   */
  public void save(ApplicationFeature params) ;


  /**
   *  查询
   *  @throws Exception
   */
  public List<ApplicationFeature> findAll(DataQuery dataQuery) ;

  /**
   * @param menuId 根据菜单Id
   * @param roleId 角色Id
   * @param applicationId 应用id
   * @return  根据菜单id 和 角色 id 进行查询 问题
   */
  public Map<String,List<ApplicationFeature>> findAllByMenu(String menuId, String roleId,String applicationId);


  /**
   * 删除记录
   *
   * @param id 实例的标识信息
   */
  int delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ApplicationFeature Id号
   * @return 返回一个 ApplicationFeature 实例的详细信息
   */
  ApplicationFeature findOne(String id);

  /**
   * 查询某条记录
   *
   * @param applicationFeatureName
   * @return 返回一个 ApplicationFeature 实例的详细信息
   */
  ApplicationFeature findOneByApplicationFeatureName(String applicationFeatureName);


  /**
   * 查询某条记录
   *
   * @param applicationFeatureKey applicationName
   * @return 返回一个 Application 实例的详细信息
   */
  ApplicationFeature findOneByApplicationKey(String applicationFeatureKey);



  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   * @param accountId 帐号标识
   * @return 返回所有 ApplicationFeature 实例的详细信息
   */
  List<ApplicationFeature> findAllByAccountId(String accountId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   记录行数
   * @return 返回一个列表 Application
   */
  // List<Application> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

  /**
   * 查询是否存在相关的记录
   *
   * @param id 应用标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 查询是否存在相关的记录
   *
   * @param name 应用名称
   * @return 布尔值
   */
  boolean isExistName(String name);

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  boolean hasAuthority(Account account, String applicationId, String authorityName);

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param accountId     帐号标识
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  boolean hasAuthority(String accountId, String applicationId, String authorityName);



  /**
   * 配置应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  void bindAuthorizationScopeObjects(String applicationId, String authorityName, String scopeText);
  /**
   * 配置应用的权限信息
   * @param applicationId 应用标识
   * @param list     权限范围的所有文本对象
   */
  void bindAuthorizationScopeObjects(String applicationId, List<ApplicationScopeInfo> list);


  /**
   * @param roleId 解绑当前按钮及角色对应的信息
   * @param bindList 绑定对应关系
   * @param unBindList 解绑对应关系
   */
  void bindFeatureRelation(String roleId,List bindList,List unBindList);

}
