package com.x3platform.apps.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.configuration.AppsConfigurationView;
import com.x3platform.apps.mappers.ApplicationMapper;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationScopeInfo;
import com.x3platform.apps.models.ApplicationViewInfo;
import com.x3platform.apps.services.ApplicationService;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用配置服务
 */
public class ApplicationServiceImpl implements ApplicationService {
  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationMapper provider = null;

  /**
   * 缓存存储
   */
  private Map<String, Application> dictionary = new HashMap<String, Application>();

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
          List<Application> list = this.findAll();
          for (Application item : list) {
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
   * @param entity 实例 Application 详细信息
   * @return Application 实例详细信息
   */
  @Override
  public int save(Application entity) {
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

    List<ApplicationScopeInfo> all = new ArrayList<>();
    if(!StringUtil.isNullOrEmpty(entity.getmAdministratorScopeText())){
      JSONArray mAdmin = JSON.parseArray(entity.getmAdministratorScopeText());
      if(mAdmin != null && mAdmin.size() > 0){
        String authorityId = "1-0001"; // 应用_默认_管理员
        // isAdministrator 管理员  isReviewer 审核员   isMember 可访问成员
        String authorizationObjectType="isAdministrator";
        for(int i = 0 ; i<mAdmin.size() ; i++ ){
          JSONObject authObjectString = mAdmin.getJSONObject(i);
          String authorizationObjectId = authObjectString.getString("authorizationObjectId");
          ApplicationScopeInfo scopeInfo = new ApplicationScopeInfo();
          scopeInfo.setEntityId(id);
          scopeInfo.setAuthorityId(authorityId);
          scopeInfo.setAuthorizationObjectId(authorizationObjectId);
          scopeInfo.setAuthorizationObjectType(authorizationObjectType);
          all.add(scopeInfo);
        }

      }
    }
    if(!StringUtil.isNullOrEmpty(entity.getReviewerScopeText())){
      JSONArray mReviewer = JSON.parseArray(entity.getReviewerScopeText());
      if(mReviewer != null && mReviewer.size() > 0){
        String authorityId = "1-0002"; // 应用_默认_管理员
        // isAdministrator 管理员  isReviewer 审核员   isMember 可访问成员
        String authorizationObjectType="isReviewer";
        for(int i = 0 ; i<mReviewer.size() ; i++){
          JSONObject authObjectString = mReviewer.getJSONObject(i);
          String authorizationObjectId = authObjectString.getString("authorizationObjectId");
          ApplicationScopeInfo scopeInfo = new ApplicationScopeInfo();
          scopeInfo.setEntityId(id);
          scopeInfo.setAuthorityId(authorityId);
          scopeInfo.setAuthorizationObjectId(authorizationObjectId);
          scopeInfo.setAuthorizationObjectType(authorizationObjectType);
          all.add(scopeInfo);
        }
      }
    }

    if(!StringUtil.isNullOrEmpty(entity.getMemberScopeText())){
      JSONArray mMember = JSON.parseArray(entity.getReviewerScopeText());
      //String[] mMember = entity.getReviewerScopeText().split(",",-1);
      if(mMember != null && mMember.size() > 0){
        String authorityId = "1-0003"; // 应用_默认_管理员
        // isAdministrator 管理员  isReviewer 审核员   isMember 可访问成员
        String authorizationObjectType="isMember";
        for(int i = 0 ; i<mMember.size() ; i++){
          JSONObject res = mMember.getJSONObject(i);
          String authorizationObjectId = res.getString("authorizationObjectId");
          ApplicationScopeInfo scopeInfo = new ApplicationScopeInfo();
          scopeInfo.setEntityId(id);
          scopeInfo.setAuthorityId(authorityId);
          scopeInfo.setAuthorizationObjectId(authorizationObjectId);
          scopeInfo.setAuthorizationObjectType(authorizationObjectType);
          all.add(scopeInfo);
        }
      }
    }

    bindAuthorizationScopeObjects(id,all);
    KernelContext.getLog().debug("save entity id:'" + id + "', affectedRows:" + affectedRows);
    // 将应用信息设置到应用交互连接器
    return affectedRows;
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
   * @param id Application Id号
   * @return 返回一个 Application 实例的详细信息
   */
  @Override
  public Application findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询某条记录
   *
   * @param applicationName applicationName
   * @return 返回一个 Application 实例的详细信息
   */
  @Override
  public Application findOneByApplicationName(String applicationName) {
    Application param = null;

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
   * 查询某条记录
   * @param applicationKey 应用key
   * @return 返回一个 applicationKey 实例的详细信息
   */
  @Override
  public Application findOneByApplicationKey(String applicationKey) {

    return provider.findOneByApplicationKey(applicationKey);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有 Application 实例的详细信息
   */
  public List<Application> findAll() {
    return provider.findAll(new HashMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有 Application 实例的详细信息
   */
  @Override
  public List<Application> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  @Override
  public List<ApplicationViewInfo> findApplicationViewInfoAll(DataQuery query) {
      return provider.findApplicationViewInfoAll(query.getMap());
  }

  /**
   * 根据帐号所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   * 根据账号，判断是否为EveryOne,然后查询Role，然后查询人
   * @param accountId 帐号标识
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Application                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Application> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * 根据角色所属的标准角色信息对应的应用系统的功能点, 查询此帐号有权限启用的应用系统信息.
   *
   * @param roleIds 角色标识
   * @return 返回所有<see                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               cref                                                                                                                                                                                                                                                               =                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               Application                                                                                                                                                                                                                                                               "                                                                                                                                                                                                                                                               />实例的详细信息
   */
  @Override
  public List<Application> findAllByRoleIds(String roleIds) {
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
  // public List<Application> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
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
  public boolean hasAuthority(Account account, String applicationId, String authorityName) {
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
    Application configration = findOne(applicationId);
    if (configration != null) {
      AppsSecurity.resetApplicationAdministrators(configration.getApplicationName());
    }
  }


  /**
   *
   * @param applicationName 应用名称
   * @param accountInfo     账号信息
   * @param authorityId   权限名称
   */
  @Override
  public void bindAuthorizationScopeObjectByAccount(String applicationName, Account accountInfo, String authorityId) {
    Application Application = provider.findOneByApplicationName(applicationName);

    if(Application!=null){
      ApplicationScopeInfo scopeInfo = new ApplicationScopeInfo();
      scopeInfo.setEntityId(Application.getId());
      scopeInfo.setEntityClassName("com.x3platform.membership.Account");
      scopeInfo.setAuthorityId(authorityId);
      scopeInfo.setAuthorizationObjectType("Account");
      scopeInfo.setAuthorizationObjectId(accountInfo.getId());
      boolean isExit = this.provider.isScopeInfoExist(scopeInfo);
      if(!isExit){
        provider.bindAuthorizationScopeObjects(scopeInfo);
      }
    }


  }

  @Override
  public void bindAuthorizationScopeObjects(String applicationId,List<ApplicationScopeInfo> list) {
    if(list!=null && list.size() > 0){
      for(ApplicationScopeInfo scopeInfo:list){
        // 判断是否存在 ， 如果润在则不处理 ， 如果不存在则进行处理
        boolean isExit = this.provider.isScopeInfoExist(scopeInfo);
        if(!isExit){
          provider.bindAuthorizationScopeObjects(scopeInfo);
        }
      }
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
   * 判断用户是否是应用的默认管理员 和  内置超级管理员
   *
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isAdministrator(Account account, String applicationId) {
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
  public boolean isReviewer(Account account, String applicationId) {
    if (isAdministrator(account, applicationId)) {
      return true;
    }

    return hasAuthority(account, applicationId, "应用_默认_审查员");
  }

  /**
   * 判断是否可以默认默认EveryOne
   */
  @Override
  public boolean isEveryOne(String applicationId){

    return provider.isEveryOne(applicationId) ;
  }

  /**
   * 判断用户是否是应用的默认可访问成员
   * @param account       帐号
   * @param applicationId 应用的标识
   * @return 布尔值
   */
  @Override
  public boolean isMember(Account account, String applicationId) {
    if (isAdministrator(account, applicationId)) {
      return true;
    }
    if (isReviewer(account, applicationId)) {
      return true;
    }
    // 判断是否是 均可访问应用
    if(isEveryOne(applicationId)){
      return true ;
    }
    return hasAuthority(account, applicationId, "应用_默认_可访问成员");
  }

  @Override
  public List<Account> findAllAccountByApplicationId(String applicationId, String applicationMembersType) {
    return this.provider.findAllAccountByApplicationId(applicationId,applicationMembersType);
  }
}
