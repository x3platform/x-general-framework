package com.x3platform.apps.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfiguration;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.mappers.ApplicationMenuMapper;
import com.x3platform.apps.models.ApplicationMenuInfo;
import com.x3platform.apps.models.ApplicationMenuQueryInfo;
import com.x3platform.apps.services.IApplicationMenuService;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 */
public class ApplicationMenuService implements IApplicationMenuService {
  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMenuMapper provider = null;

  /**
   * 构造函数
   */
  // public ApplicationMenuService() {
  //   this.configuration = AppsConfigurationView.Instance.Configuration;
  //
  //   // 创建对象构建器(Spring.NET)
  // String springObjectFile = this.configuration.keySet()["SpringObjectFile"].Value;
  //
  // SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(AppsConfiguration.ApplicationName, springObjectFile);

  //  // 创建数据提供器
  // this.provider = objectBuilder.<IApplicationMenuProvider>GetObject(IApplicationMenuProvider.class);
  // }

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例<see cref="ApplicationMenuInfo"/>详细信息
   * @return
   */
  public final ApplicationMenuInfo save(ApplicationMenuInfo param) {
    param.setFullPath(this.combineFullPath(param));

    return this.provider.save(param);
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  public final void delete(String id) {
    provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return
   */
  public final ApplicationMenuInfo findOne(String id) {
    return this.provider.findOne(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return
   */
  public final List<ApplicationMenuInfo> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return
   */
  public final List<ApplicationMenuQueryInfo> findAllQueryObject(DataQuery query) {
//     // 验证管理员身份
//    if (AppsSecurity.IsAdministrator(KernelContext.Current.User, AppsConfiguration.ApplicationName)) {
//      return this.provider.findAllQueryObject(whereClause, length);
//    } else {
//      return this.provider.findAllQueryObject(this.BindAuthorizationScopeSQL(whereClause), length);
//    }
    return this.provider.findAllQueryObject(query.getMap());
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
  public final boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 组合菜单全路径
   *
   * @param param 菜单信息
   * @return
   */
  public final String combineFullPath(ApplicationMenuInfo param) {
    String fullPath = "";

    fullPath = param.getName();

    ApplicationMenuInfo parent = param.getParent();

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

  // -------------------------------------------------------
  // 授权范围管理
  // -------------------------------------------------------

  ///#region 函数:HasAuthority(string entityId, string authorityName, IAccountInfo account)

  /**
   * 判断用户是否拥应用菜单有权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param account       帐号
   * @return 布尔值
   */
  public final boolean hasAuthority(String entityId, String authorityName, IAccountInfo account) {
    return provider.hasAuthority(entityId, authorityName, account);
  }

  /**
   * 配置应用菜单的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  public final void bindAuthorizationScopeObjects(String entityId, String authorityName, String scopeText) {
    provider.bindAuthorizationScopeObjects(entityId, authorityName, scopeText);
  }
  ///#endregion

  ///#region 函数:GetAuthorizationScopeObjects(string entityId, string authorityName)

  /**
   * 查询应用菜单的权限信息
   *
   * @param entityId      实体标识
   * @param authorityName 权限名称
   * @return
   */
  // public final List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String entityId, String authorityName) {
  //  return provider.GetAuthorizationScopeObjects(entityId, authorityName);
  // }
  ///#endregion

  // -------------------------------------------------------
  // 权限
  // -------------------------------------------------------

  ///#region 私有函数:GetAuthorizationReadObject(ApplicationMenuInfo param)

  /**
   * 验证对象的权限
   *
   * @param param 需验证的对象
   * @return 对象
   */
  private ApplicationMenuInfo getAuthorizationReadObject(ApplicationMenuInfo param) {
    // FIXME
    /*
    IAccountInfo account = KernelContext.Current.User;

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
   * 绑定SQL查询条件
   *
   * @param whereClause WHERE 查询条件
   * @return
   */
  private String BindAuthorizationScopeSQL(String whereClause) {
    // FIXME
    /*
    String accountId = KernelContext.getCurrent().User == null ? UUIDUtil.emptyString() : KernelContext.Current.User.Id;

    String scope = String.format(" (" + "\r\n" +
      "(   T.Id IN ( " + "\r\n" +
      "        SELECT DISTINCT EntityId FROM view_AuthObject_Account View1, tb_Application_Menu_Scope Scope" + "\r\n" +
      "        WHERE " + "\r\n" +
      "            View1.AccountId = ##%1$s##" + "\r\n" +
      "            AND View1.AuthorizationObjectId = Scope.AuthorizationObjectId" + "\r\n" +
      "            AND View1.AuthorizationObjectType = Scope.AuthorizationObjectType)) " + "\r\n" +
      ") ", accountId);

    if (whereClause.indexOf(scope) == -1) {
      whereClause = StringUtil.isNullOrEmpty(whereClause) ? scope : scope + " AND " + whereClause;
    }

    */
    return whereClause;
  }
}
