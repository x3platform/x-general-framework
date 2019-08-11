package com.x3platform.apps.services.impl;

import com.x3platform.apps.mappers.ApplicationFeatureMapper;
import com.x3platform.apps.models.ApplicationFeature;
import com.x3platform.apps.models.ApplicationScopeInfo;
import com.x3platform.apps.services.ApplicationFeatureService;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用功能服务
 */
public class ApplicationFeatureServiceImpl implements ApplicationFeatureService {

  @Autowired(required = false)
  ApplicationFeatureMapper featureMapper;

  /**
   * 保存数据问题
   *
   * @param params
   */
  @Override
  public void save(ApplicationFeature params) {
    String id = params.getId();
    if (this.featureMapper.selectByPrimaryKey(id) == null) {
      featureMapper.insert(params);
    } else {
      featureMapper.updateByPrimaryKey(params);
    }
  }

  /**
   * @param query
   * @return
   */
  @Override
  public List<ApplicationFeature> findAll(DataQuery query) {
    return featureMapper.findAll(query.getMap());
  }

  /**
   *
   * @param menuId 根据菜单Id
   * @param roleId 角色Id
   * @return
   */
  @Override
  public Map<String,List<ApplicationFeature>> findAllByMenu(String menuId, String roleId, String applicationId) {
    Map<String,List<ApplicationFeature>> result = new HashMap<>();
    // 1、查询当前菜单所对应的所有应该功能 及对应模块
    // 查询所有的  和 查询单个的
    // function 、button 、cad 、 tab
    List<ApplicationFeature> featureInfos = featureMapper.findAllByMenu(menuId,roleId,applicationId);
    List<ApplicationFeature> featureScopeInfos = featureMapper.findAllAuthorizationScopeByMenu(menuId,roleId,applicationId);
    result.put("featureInfos",featureInfos);
    result.put("featureScopeInfos",featureScopeInfos);
    return result;
  }

  @Override
  public int delete(String id) {
    return featureMapper.deleteByPrimaryKey(id);
  }

  @Override
  public ApplicationFeature findOne(String id) {
    return featureMapper.findOneByApplicationKey(id);
  }

  @Override
  public ApplicationFeature findOneByApplicationFeatureName(String applicationFeatureName) {
    return featureMapper.findOneByApplicationName(applicationFeatureName);
  }

  @Override
  public ApplicationFeature findOneByApplicationKey(String applicationFeatureKey) {
    return null;
  }

  @Override
  public List<ApplicationFeature> findAllByAccountId(String accountId) {
    return featureMapper.findAllByAccountId(accountId);
  }


  @Override
  public boolean isExist(String id) {
    return featureMapper.isExist(id);
  }

  @Override
  public boolean isExistName(String name) {
    return featureMapper.isExistName(name);
  }

  @Override
  public boolean hasAuthority(Account account, String applicationId, String authorityName) {
    return false;
  }

  @Override
  public boolean hasAuthority(String accountId, String applicationId, String authorityName) {
    return false;
  }

  @Override
  public void bindAuthorizationScopeObjects(String applicationId, String authorityName, String scopeText) {
  }

  @Override
  public void bindAuthorizationScopeObjects(String applicationId, List<ApplicationScopeInfo> list) {
  }

  /**
   * @param roleId 解绑当前按钮及角色对应的信息
   * @param bindList 绑定对应关系
   * @param unBindList 解绑对应关系
   */
  @Override
  public void bindFeatureRelation(String roleId, List bindList, List unBindList) {
    String entityClassName="com.x3platform.membership.models.RoleInfo";
    String authorityId="2-0001";
    if( unBindList!=null && unBindList.size() > 0){
      String authorizationObjectType = "Role";
      String authorizationObjectId = roleId ;
      String entityId="";

      for(int i=0 ; i<unBindList.size() ; i++){
        entityId =  unBindList.get(i).toString();
        featureMapper.unBindFeature(entityId,entityClassName,authorityId,authorizationObjectType,authorizationObjectId);
      }
    }
    // 绑定大小问题
    if(bindList!=null && bindList.size() > 0){
      String authorizationObjectType = "Role";
      String authorizationObjectId = roleId ;
      String entityId="";
      for(int i=0 ; i<bindList.size() ; i++){
        entityId = bindList.get(i).toString();
        featureMapper.bindFeature(entityId,entityClassName,authorityId,authorizationObjectType,authorizationObjectId);
      }
    }
  }
}
