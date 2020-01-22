package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_MENU_ROOT_ID;
import static com.x3platform.apps.configuration.AppsConfiguration.APPLICATION_NAME;

import com.x3platform.AuthorizationScope;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.mappers.ApplicationMenuMapper;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import com.x3platform.apps.services.ApplicationMenuService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class ApplicationMenuServiceImpl implements ApplicationMenuService {

  private static final String CACHE_KEY_ID_PREFIX = "x3platform:apps:application-menu:id:";

  private static final String DATA_SCOPE_TABLE_NAME = "application_menu_scope";

  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMenuMapper provider = null;

  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------

  /**
   * 添加缓存项
   */
  private void addCacheItem(ApplicationMenu item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      CachingManager.set(key, item);
    }
  }

  /**
   * 移除缓存项
   */
  private void removeCacheItem(ApplicationMenu item) {
    if (!StringUtil.isNullOrEmpty(item.getId())) {
      String key = CACHE_KEY_ID_PREFIX + item.getId();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }
  }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 ApplicationMenu 详细信息
   */
  @Override
  public int save(ApplicationMenu entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    // 计算完整路径
    entity.setFullPath(combineFullPath(entity));

    if (provider.selectByPrimaryKey(id) == null) {
      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    // 保存数据后, 更新缓存信息
    entity = provider.selectByPrimaryKey(entity.getId());

    if (entity != null) {
      removeCacheItem(entity);

      addCacheItem(entity);
    }

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    ApplicationMenu entity = provider.selectByPrimaryKey(id);

    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);

      // 删除数据库记录
      int affectedRows = provider.deleteByPrimaryKey(id);

      KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);
    }

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   */
  @Override
  public ApplicationMenu findOne(String id) {
    ApplicationMenu entity = null;

    // 根节点快速处理
    if (APPLICATION_MENU_ROOT_ID.equals(id)) {
      return null;
    }

    String key = CACHE_KEY_ID_PREFIX + id;

    if (CachingManager.contains(key)) {
      entity = (ApplicationMenu) CachingManager.get(key);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    if (entity == null) {
      entity = provider.selectByPrimaryKey(id);

      if (entity != null) {
        addCacheItem(entity);
      }
    }

    return entity;
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   */
  @Override
  public List<ApplicationMenu> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   */
  @Override
  public List<ApplicationMenuLite> findAllLites(DataQuery query) {
//     // 验证管理员身份
//    if (AppsSecurity.IsAdministrator(KernelContext.Current.User, AppsConfiguration.ApplicationName)) {
//      return this.provider.findAllQueryObject(whereClause, length);
//    } else {
//      return this.provider.findAllQueryObject(this.BindAuthorizationScopeSQL(whereClause), length);
//    }
    return provider.findAllLites(query.getMap());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 组合菜单全路径
   *
   * @param param 菜单信息
   */
  @Override
  public String combineFullPath(ApplicationMenu param) {
    String fullPath = "";

    fullPath = param.getName();

    ApplicationMenu parent = param.getParent();

    int depthCount = 0;

    while (parent != null) {
      fullPath = String.format("%1$s\\%2$s", parent.getName(), fullPath);

      parent = parent.getParent();

      // 如果深度超过50层，则跳出循环。(可能陷入死循环)
      if (depthCount++ > 50) {
        break;
      }
    }

    if (param.getMenuType().equals("ApplicationMenu")) {
      fullPath = String.format("%1$s\\%2$s", param.getApplicationDisplayName(), fullPath);
    } else {
      fullPath = String.format("%1$s\\%2$s", param.getMenuTypeView(), fullPath);
    }

    return fullPath;
  }

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param parentId 所属父级菜单标识
   * @param menuType 菜单类型
   * @return 所有实例 {@link ApplicationMenu} 的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByParentId(String applicationId, String parentId, String menuType) {
    return provider.getMenusByParentId(applicationId, parentId, menuType, bindAuthorizationScopeSql());
  }

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @param fullPath 所属父级菜单标识
   * @param menuType 菜单类型
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByFullPath(String applicationId, String fullPath,
    String menuType) {
    return provider.getMenusByFullPath(applicationId, fullPath, menuType);
  }

  /**
   * 获取有效的菜单信息
   *
   * @param applicationId 所属应用标识
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByApplicationId(String applicationId) {
    return provider.getMenusByApplicationId(applicationId);
  }

  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  /**
   * 判断用户是否拥应用菜单有权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param account 帐号
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(String entityId, String authorityName, Account account) {
    return MembershipManagement.getInstance().getAuthorizationObjectService().hasAuthority(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationMenu.class),
      authorityName,
      account);
  }

  /**
   * 配置应用菜单的权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeByEntityId(String entityId, String authorityName, String scopeText) {
    MembershipManagement.getInstance().getAuthorizationObjectService().bindAuthorizationScopeByEntityId(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationMenu.class),
      authorityName,
      scopeText);
  }

  /**
   * 配置应用菜单的权限信息
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识 多个对象以逗号隔开
   */
  @Override
  public void bindAuthorizationScopeByAuthorizationObjectIds(String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds) {
    MembershipManagement.getInstance().getAuthorizationObjectService().bindAuthorizationScopeByAuthorizationObjectIds(
      DATA_SCOPE_TABLE_NAME,
      authorizationObjectType,
      authorizationObjectId,
      authorityName,
      entityIds,
      KernelContext.parseObjectType(ApplicationMenu.class));
  }

  /**
   * 查询实体对象的授权信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   */
  @Override
  public List<AuthorizationScope> getAuthorizationScopes(String entityId, String authorityName) {
    return MembershipManagement.getInstance().getAuthorizationObjectService().getAuthorizationScopes(
      DATA_SCOPE_TABLE_NAME,
      entityId,
      KernelContext.parseObjectType(ApplicationMenu.class),
      authorityName);
  }

  // -------------------------------------------------------
  // 权限
  // -------------------------------------------------------

  ///#region 私有函数:GetAuthorizationReadObject(ApplicationMenu param)

  /**
   * 验证对象的权限
   *
   * @param param 需验证的对象
   * @return 对象
   */
  private ApplicationMenu getAuthorizationReadObject(ApplicationMenu param) {
    // FIXME
    /*
    Account account = KernelContext.Current.User;
    if (AppsSecurity.isAdministrator(account, AppsConfiguration.ApplicationName)) {
      return param;
    } else {
      if (MembershipAuthorizationScopeManagement.Authenticate(param.AuthorizationReadScopeObjects, account)) {
        return param;
      }
      return null;
    }
    */
    return null;
  }

  /**
   * 绑定 SQL 查询条件
   */
  private String bindAuthorizationScopeSql() {
    if (AppsSecurity.isAdministrator(KernelContext.getCurrent().getUser(), APPLICATION_NAME)) {
      return "";
    } else {
      String accountId = KernelContext.getCurrent().getUser() == null ?
        UUIDUtil.emptyString() : KernelContext.getCurrent().getUser().getId();

      return String.format(" ("
        + "(t.id IN ( "
        + "   SELECT entity_id "
        + "     FROM view_authobject_account view1, application_menu_scope scope"
        + "    WHERE view1.account_id = '%s'"
        + "      AND view1.authorization_object_id = scope.authorization_object_id"
        + "      AND view1.authorization_object_type = scope.authorization_object_type "
        + " GROUP BY entity_id)) "
        + ") ", accountId);
    }
  }
}
