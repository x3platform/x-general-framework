package com.x3platform.apps.services.impl;

import com.alibaba.fastjson.JSON;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.mappers.ApplicationMenuMapper;
import com.x3platform.apps.models.ApplicationMenu;
import com.x3platform.apps.models.ApplicationMenuLite;
import com.x3platform.apps.models.ApplicationMenuScopeInfo;
import com.x3platform.apps.models.ApplicationMenuViewInfo;
import com.x3platform.apps.services.ApplicationMenuService;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class ApplicationMenuServiceImpl implements ApplicationMenuService {

  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMenuMapper provider = null;

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

    // [{},{},{},{}]
    String authorazitionScope = entity.getAuthorizationReadScopeObjectText();
    // 保存完整 菜单权限范围
    if (!StringUtil.isNullOrEmpty(authorazitionScope)) {
      List<ApplicationMenuScopeInfo> scopeInfos = JSON
        .parseArray(entity.getAuthorizationReadScopeObjectText(), ApplicationMenuScopeInfo.class);
      if (scopeInfos != null && scopeInfos.size() > 0) {
        for (ApplicationMenuScopeInfo scopeInfo : scopeInfos) {
          scopeInfo.setEntityId(id);
          // authority_id
          String authorityId = scopeInfo.getAuthorityId();
          if (authorityId == null) {
            // 设置默认可访问权限
            scopeInfo.setAuthorityId("1-0003");
          }
          provider.addAuthorizationScopeObject(scopeInfo);
        }
      }
    }

    KernelContext.getLog().debug("save entity id:'" + id + "', affectedRows:" + affectedRows);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    int affectedRows = provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'" + id + "', affectedRows:" + affectedRows);

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
    return provider.selectByPrimaryKey(id);
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

  @Override
  public List<ApplicationMenuViewInfo> findApplicationMenuViewInfoAll(DataQuery query) {
    return provider.findApplicationMenuViewInfoAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   */
  @Override
  public List<ApplicationMenuLite> findAllQueryObject(DataQuery query) {
//     // 验证管理员身份
//    if (AppsSecurity.IsAdministrator(KernelContext.Current.User, AppsConfiguration.ApplicationName)) {
//      return this.provider.findAllQueryObject(whereClause, length);
//    } else {
//      return this.provider.findAllQueryObject(this.BindAuthorizationScopeSQL(whereClause), length);
//    }
    return provider.findAllQueryObject(query.getMap());
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
   * @return 返回所有实例ApplicationMenu的详细信息
   */
  @Override
  public List<ApplicationMenu> getMenusByParentId(
    String applicationId, String parentId, String menuType) {
    return provider.getMenusByParentId(applicationId, parentId, menuType,
      bindAuthorizationScopeSql());
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
    return provider.hasAuthority(entityId, authorityName, account);
  }

  /**
   * 配置应用菜单的权限信息
   *
   * @param entityId 实体标识
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeObjects(String entityId, String authorityName,
    String scopeText) {
    provider.bindAuthorizationScopeObjects(entityId, authorityName, scopeText);
  }

  /**
   * 根据角色绑定菜单
   *
   * @param roleId 角色id
   * @param bindScopeMenuList 字符串组合
   * @param unBindScopeMenuList 解绑字符串组合
   */
  @Override
  public void bindAuthorizationScopeByRole(String roleId, List<String> bindScopeMenuList,
    List<String> unBindScopeMenuList) {
    if (unBindScopeMenuList != null && unBindScopeMenuList.size() > 0) {
      for (String scope : unBindScopeMenuList) {
        String[] scopeKeys = scope.split("#");
        provider.deleteAuthorizationScopeObjects(scopeKeys[4],
          "com.x3platform.membership.models.RoleInfo", "2-0001", "Role", roleId);
      }
    }
    // 判断当前是否存在 ， 如果不存在则存入， 删除的则删除
    if (bindScopeMenuList != null && bindScopeMenuList.size() > 0) {
      for (String scope : bindScopeMenuList) {
        String[] scopeKeys = scope.split("#");
        // 判断当前是否存在 如果存在,则不进行操作
        boolean isExits = provider
          .isExistAuthorizationScope(scopeKeys[4], "com.x3platform.membership.models.RoleInfo",
            "2-0001", "Role", roleId);
        if (!isExits) {
          provider.bindAuthorizationScope(scopeKeys[4], "com.x3platform.membership.models.RoleInfo",
            "2-0001", "Role", roleId);
        }
      }
    }
  }

  /**
   * 移除当前角色所有的菜单 ， 请注意在多个项目的情况下，统一控制和删除 ；
   *
   * @param roleId 角色id
   * @param bindScopeList 新增绑定
   */
  @Override
  public void bindMenuScopeByRole(String roleId, List<String> bindScopeList) {
    // 删除全部 再插入
    if (bindScopeList != null && bindScopeList.size() > 0) {
      // 移除当前角色所有的菜单
      provider.removeMenuScopeByRole(roleId); // 删除全部
      for (int i = 0; i < bindScopeList.size(); i++) {
        provider
          .bindAuthorizationScope(bindScopeList.get(i), "com.x3platform.membership.models.RoleInfo",
            "2-0001", "Role", roleId);
      }
    }
  }

  /**
   * @param value 根据 value 获取 当前应用范围
   */
  @Override
  public List<ApplicationMenuScopeInfo> getApplicationMenuScope(String value) {
    List<ApplicationMenuScopeInfo> applicationMenuScopeInfos = new ArrayList<>();
    if (StringUtil.isNullOrEmpty(value)) {
      applicationMenuScopeInfos = JSON.parseArray(value, ApplicationMenuScopeInfo.class);
    }
    return applicationMenuScopeInfos;
  }

  /**
   * @param applicationId 所属应用
   * @param parentId 父级菜单
   * @param menuType 所属菜单
   */
  @Override
  public List<ApplicationMenu> getMenusScopeByParentId(String applicationId, String parentId,
    String menuType, String accountId) {
    return provider.getMenusScopeByParentId(applicationId, parentId, menuType, accountId);
  }

  /**
   * 根据菜单 和 角色 查询是否存在
   *
   * @param roleId 角色id
   * @param menuId 菜单id
   */
  @Override
  public boolean isExistMenusScopeByRoleIdAndMenuId(String roleId, String menuId) {
    String entityId = menuId;
    String authorizationObjectId = roleId;
    String authorizationObjectType = "Role";
    return provider.isExistScope(authorizationObjectId, authorizationObjectType, entityId);
  }

  ///#endregion

  ///#region 函数:GetAuthorizationScopeObjects(string entityId, string authorityName)

  /**
   * 查询应用菜单的权限信息
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @return
   */
  // public List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String entityId, String authorityName) {
  //  return provider.GetAuthorizationScopeObjects(entityId, authorityName);
  // }
  ///#endregion

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
    if (AppsSecurity.isAdministrator(KernelContext.getCurrent().getUser(),
      AppsConfiguration.APPLICATION_NAME)) {
      return "";
    } else {

      String accountId = KernelContext.getCurrent().getUser() == null ?
        UUIDUtil.emptyString() : KernelContext.getCurrent().getUser().getId();

      return String.format(" ("
        + "(T.id IN ( "
        + "   SELECT entity_id "
        + "     FROM view_authobject_account View1, application_menu_scope Scope"
        + "    WHERE View1.account_id = '%s'"
        + "      AND View1.authorization_object_id = Scope.authorization_object_id"
        + "      AND View1.authorization_object_type = Scope.authorization_object_type "
        + " GROUP BY entity_id)) "
        + ") ", accountId);
    }
  }

  /**
   * @param id 角色
   */
  @Override
  public List<String> getMenusScopeByRoleId(String id) {
    List<String> result = new ArrayList<>();
    List findResult = provider.getMenusScopeByRoleId(id);
    if (findResult != null && findResult.size() > 0) {
      for (Object find : findResult) {
        Map<String, String> map = (Map<String, String>) find;
        String entity_id = map.get("entity_id").toString();
        result.add(entity_id);
      }
    }
    return result;
  }
}
