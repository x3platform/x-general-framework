package com.x3platform.apps.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.mappers.ApplicationMapper;
import com.x3platform.apps.models.ApplicationInfo;
import com.x3platform.apps.services.IApplicationService;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 应用配置服务
 */
public class ApplicationService implements IApplicationService {
  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMapper provider = null;

  /**
   * 缓存存储
   */
  private Map<String, ApplicationInfo> dictionary = new HashMap<String, ApplicationInfo>();

  /**
   * 构造函数
   */
  // public ApplicationService() {
  // 创建对象构建器(Spring.NET)
  //  String springObjectFile = AppsConfigurationView.Instance.Configuration.keySet()["SpringObjectFile"].Value;

  //  SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(AppsConfiguration.ApplicationName, springObjectFile);

  // 创建数据提供器
  // this.provider = objectBuilder.<IApplicationProvider>GetObject(IApplicationProvider.class);
  // }

  private Object lockObject = new Object();

  /**
   * 同步本地数据信息
   */
  private void SyncLocalDb() {
    // 初始化缓存
    if (this.dictionary.isEmpty()) {
      synchronized (lockObject) {
        if (this.dictionary.isEmpty()) {
          List<ApplicationInfo> list = this.findAll();

          for (ApplicationInfo item : list) {
            this.dictionary.put(item.getId(), item);
          }
        }
      }
    }
  }

  // -------------------------------------------------------
  // 添加 删除 修改
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 ApplicationInfo 详细信息
   * @return ApplicationInfo 实例详细信息
   */
  @Override
  public int save(ApplicationInfo entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (this.provider.selectByPrimaryKey(id) == null) {
      affectedRows = this.provider.insert(entity);
    } else {
      affectedRows = this.provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'" + id + "', affectedRows:" + affectedRows);

    // 将应用信息设置到应用交互连接器

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识信息
   */
  @Override
  public int delete(String id) {
    int affectedRows = this.provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'" + id + "', affectedRows:" + affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id ApplicationInfo Id号
   * @return 返回一个 ApplicationInfo 实例的详细信息
   */
  @Override
  public ApplicationInfo findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 ApplicationInfo 实例的详细信息
   */
  @Override
  public ApplicationInfo findOneByApplicationName(String applicationName) {
    ApplicationInfo param = null;

    // 初始化缓存
    SyncLocalDb();

    // 查找缓存数据
    if (this.dictionary.containsKey(applicationName)) {
      param = this.dictionary.get(applicationName);
    }

    // 如果缓存中未找到相关数据，则查找数据库内容
    return param == null ? provider.findOneByApplicationName(applicationName) : param;
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 ApplicationInfo 实例的详细信息
   */
  public List<ApplicationInfo> findAll() {
    return provider.findAll(new HashMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 ApplicationInfo 实例的详细信息
   */
  @Override
  public List<ApplicationInfo> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param accountId 帐号标识
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               ApplicationInfo                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public List<ApplicationInfo> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               ApplicationInfo                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public List<ApplicationInfo> findAllByRoleIds(String roleIds) {
    return provider.findAllByRoleIds(roleIds);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例
   */
  // public List<ApplicationInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 任务编码
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param name 应用名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(IAccountInfo account, String applicationId, String authorityName) {
    return hasAuthority(account.getId(), applicationId, authorityName);
  }

  /**
   * 判断用户是否拥应用有权限信息
   *
   * @param accountId     帐号
   * @param applicationId 应用的标识
   * @param authorityName 权限名称
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(String accountId, String applicationId, String authorityName) {
    return provider.hasAuthority(accountId, applicationId, authorityName);
  }
  ///#endregion

  ///#region 函数:BindAuthorizationScopeObjects(string applicationId, string authorityName, string scopeText)

  /**
   * 配置应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @param scopeText     权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeObjects(String applicationId, String authorityName, String scopeText) {
    provider.bindAuthorizationScopeObjects(applicationId, authorityName, scopeText);

    // [重点]
    // 设置完管理员后, 重置缓存数据.
    ApplicationInfo configration = findOne(applicationId);

    if (configration != null) {
      AppsSecurity.resetApplicationAdministrators(configration.getApplicationName());
    }
  }

  /**
   * 查询应用的权限信息
   *
   * @param applicationId 应用标识
   * @param authorityName 权限名称
   * @return
   */
  // public List<MembershipAuthorizationScopeObject> GetAuthorizationScopeObjects(String applicationId, String authorityName) {
  //  return provider.getAuthorizationScopeObjects(applicationId, authorityName);
  // }

  /**
   * 判断用户是否是应用的默认管理员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isAdministrator(IAccountInfo account, String applicationId) {
    if (account == null) {
      return false;
    }

    String loginName = account.getLoginName();

    String administrators = AppsConfigurationView.getInstance().getAdministrators();

    administrators = "[" + administrators.replace(",", "],[") + "]";

    // 如果为内置超级管理员则返回 True。
    if (administrators.indexOf(loginName) > -1) {
      return true;
    }

    return hasAuthority(account, applicationId, "应用_默认_管理员");
  }

  /**
   * 判断用户是否是应用的默认审查员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isReviewer(IAccountInfo account, String applicationId) {
    if (isAdministrator(account, applicationId)) {
      return true;
    }

    return hasAuthority(account, applicationId, "应用_默认_审查员");
  }

  /**
   * 判断用户是否是应用的默认可访问成员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isMember(IAccountInfo account, String applicationId) {
    if (isAdministrator(account, applicationId)) {
      return true;
    }

    if (isReviewer(account, applicationId)) {
      return true;
    }

    return hasAuthority(account, applicationId, "应用_默认_可访问成员");
  }
}
