package com.x3platform.apps.mappers;

import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface ApplicationMenuMapper {
  // -------------------------------------------------------
  // 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 添加记录
   *
   * @param param ApplicationMenu 实例的详细信息
   */
  int insert(ApplicationMenu param);

  /**
   * 修改记录
   *
   * @param param ApplicationMenu 实例的详细信息
   */
  int updateByPrimaryKey(ApplicationMenu param);

  /**
   * 删除记录
   *
   * @param id 实例的标识信息
   */
  int deleteByPrimaryKey(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return 返回一个 ApplicationMenu 实例的详细信息
   */
  ApplicationMenu selectByPrimaryKey(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 ApplicationMenu 实例的详细信息
   */
  List<ApplicationMenu> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param params 查询条件
   * @return 返回所有 {@link ApplicationMenuLite} 实例的详细信息
   */
  List<ApplicationMenuLite> findAllLites(Map params);

  /**
   * 根据应用标识查询所有可用的树节点信息
   *
   * @param applicationId 父节点标识
   * @return 相关实例的详细信息
   */
  List<ApplicationMenuLite> findTreeNodesByApplicationId(@Param("application_id") String applicationId,
    @Param("menu_type") String menuType);

  /**
   * 根据父节点标识查询所有可用的树节点信息
   *
   * @param parentId 父节点标识
   * @param menuType 菜单类型
   * @return 相关实例的详细信息
   */
  List<ApplicationMenuLite> findTreeNodesByParentId(@Param("parent_id") String parentId,
    @Param("menu_type") String menuType);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务编码
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param parentId 所属父级菜单标识
   * @param menuType 菜单类型
   * @return 返回所有实例 ApplicationMenu 的详细信息
   */
  List<ApplicationMenu> getMenusByParentId(
    @Param("application_id") String applicationId,
    @Param("parent_id") String parentId,
    @Param("menu_type") String menuType,
    @Param("authorization_scope_sql") String authorizationScopeSql);

  /**
   * @param applicationId 对应应用
   * @param parentId 父级菜单id
   * @param menuType 菜单类型
   * @param accountId 当前登录人员id
   */
  List<ApplicationMenu> getMenusScopeByParentId(
    @Param("applicationId") String applicationId,
    @Param("parentId") String parentId,
    @Param("menuType") String menuType,
    @Param("accountId") String accountId);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param menuType 菜单类型
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenu> getMenusByFullPath(
    @Param("applicationId") String applicationId,
    @Param("fullPath") String fullPath,
    @Param("menuType") String menuType);

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  List<ApplicationMenu> getMenusByApplicationId(@Param("applicationId") String applicationId);
}
