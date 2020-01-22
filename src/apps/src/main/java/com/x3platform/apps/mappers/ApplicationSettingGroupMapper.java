package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationSettingGroup;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author ruanyu
 */
public interface ApplicationSettingGroupMapper {

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param record {@link {@link ApplicationSettingGroup} 实例的详细信息
   * @return 返回消息代码 0=表示成功
   */
  int insert(ApplicationSettingGroup record);

  /**
   * 修改记录
   *
   * @param record {@link ApplicationSettingGroup} 实例的详细信息
   * @return 返回消息代码 0=表示成功
   */
  int updateByPrimaryKey(ApplicationSettingGroup record);

  /**
   * 删除记录
   *
   * @param id 标识
   * @return 返回消息代码 0=表示成功
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回一个 {@link ApplicationSettingGroup} 实例的详细信息
   */
  ApplicationSettingGroup selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 {@link ApplicationSettingGroup} 实例的详细信息
   */
  List<ApplicationSettingGroup> findAll(Map params);

  /**
   * 根据应用标识查询所有可用的树节点信息
   *
   * @param applicationId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationSettingGroup> findTreeNodesByApplicationId(@Param("application_id") String applicationId);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationSettingGroup> findTreeNodesByParentId(@Param("parent_id") String parentId);

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
}
