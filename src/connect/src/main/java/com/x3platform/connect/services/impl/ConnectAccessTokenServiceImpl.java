package com.x3platform.connect.services.impl;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.connect.configuration.ConnectConfigurationView;
import com.x3platform.connect.mappers.ConnectAccessTokenMapper;
import com.x3platform.connect.models.ConnectAccessToken;
import com.x3platform.connect.services.ConnectAccessTokenService;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public final class ConnectAccessTokenServiceImpl implements ConnectAccessTokenService {
  
  private static final String CACHE_KEY_ACCESS_TOKEN_ID_PREFIX = "x3platform:connect:access-token:id:";
  
  // private static final String CACHE_KEY_ACCESS_TOKEN_ACCOUNT_ID_PREFIX = "x3platform:connect:access-token:account-id:";
  
  private static final String CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX = "x3platform:connect:access-token:refresh-token:";
  
  // @Autowired
  // private ConnectAccessTokenMapper provider = null;
  
  /**
   * 写入的帐号的访问令牌信息
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return
   */
  @Override
  public ConnectAccessToken write(String appKey, String accountId) {
    if (StringUtil.isNullOrEmpty(accountId)) {
      return null;
    }
    
    String id = DigitalNumberContext.generate("Key_32DigitGuid");
    
    ConnectAccessToken entity = new ConnectAccessToken();
    
    entity.setId(id);
    entity.setAppKey(appKey);
    entity.setAccountId(accountId);
    entity.setExpireDate(LocalDateTime.now().plusSeconds(ConnectConfigurationView.getInstance().getSessionTimeLimit()));
    entity.setRefreshToken(DigitalNumberContext.generate("Key_32DigitGuid"));
    entity.setModifiedDate(LocalDateTime.now());
    entity.setCreatedDate(LocalDateTime.now());
    
    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new NullPointerException("实例标识不能为空。");
    }
    
    // 添加缓存项
    if (!StringUtil.isNullOrEmpty(id)) {
      String key = CACHE_KEY_ACCESS_TOKEN_ID_PREFIX + id;
      CachingManager.set(key, entity, entity.getExpiresIn() / 60);
    }
    
    if (!StringUtil.isNullOrEmpty(entity.getRefreshToken())) {
      String key = CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX + entity.getRefreshToken() + "_" + entity.getAppKey();
      CachingManager.set(key, id, entity.getExpiresIn() / 60);
    }
    
    return entity;
  }
  
  /**
   * 刷新帐号的访问令牌
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @param expireDate   过期时间
   * @return 消息代码 0=表示成功
   */
  @Override
  public int refresh(String appKey, String refreshToken, LocalDateTime expireDate) {
    if (StringUtil.isNullOrEmpty(refreshToken)) {
      return 0;
    }
    
    ConnectAccessToken entity = findOneByRefreshToken(appKey, refreshToken);
    
    if (entity != null) {
      String id = entity.getId();
      String nextRefreshToken = DigitalNumberContext.generate("Key_32DigitGuid");
      entity.setExpireDate(expireDate);
      entity.setRefreshToken(nextRefreshToken);
      // 添加缓存项
      if (!StringUtil.isNullOrEmpty(id)) {
        String key = CACHE_KEY_ACCESS_TOKEN_ID_PREFIX + id;
        CachingManager.set(key, entity, entity.getExpiresIn() / 60);
      }
      
      if (!StringUtil.isNullOrEmpty(refreshToken)) {
        String key = CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX + refreshToken + "_" + appKey;
        CachingManager.delete(key);
      }
      
      if (!StringUtil.isNullOrEmpty(nextRefreshToken)) {
        String key = CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX + nextRefreshToken + "_" + appKey;
        CachingManager.set(key, id, entity.getExpiresIn() / 60);
      }
    }
    
    return 0;
  }
  
  /**
   * 删除记录
   *
   * @param id 标识
   * @return 消息代码 0=表示成功
   */
  @Override
  public int delete(String id) {
    ConnectAccessToken entity = findOne(id);
    
    if (entity != null) {
      // 删除缓存项
      if (!StringUtil.isNullOrEmpty(entity.getId())) {
        String key = CACHE_KEY_ACCESS_TOKEN_ID_PREFIX + entity.getId();
        CachingManager.delete(key);
      }
      
      if (!StringUtil.isNullOrEmpty(entity.getRefreshToken())) {
        String key = CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX + entity.getRefreshToken() + "_" + entity.getAppKey();
        CachingManager.delete(key);
      }
    }
    
    return 0;
  }
  
  /**
   * 根据访问令牌查询某条记录
   *
   * @param id 访问令牌标识
   * @return 返回一个 {@link ConnectAccessToken} 实例的详细信息
   */
  @Override
  public ConnectAccessToken findOne(String id) {
    if (!StringUtil.isNullOrEmpty(id)) {
      String key = CACHE_KEY_ACCESS_TOKEN_ID_PREFIX + id;
      if (CachingManager.contains(key)) {
        return (ConnectAccessToken) CachingManager.get(key);
      }
    }
    
    return null;
  }
  
  /**
   * 根据刷新令牌查询某条记录
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回一个 {@link ConnectAccessToken} 实例的详细信息
   */
  @Override
  public ConnectAccessToken findOneByRefreshToken(String appKey, String refreshToken) {
    if (!StringUtil.isNullOrEmpty(refreshToken)) {
      String key = CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX + refreshToken + "_" + appKey;
      if (CachingManager.contains(key)) {
        // 查找过程 refreshToken + appKey -> id -> token
        String id = (String) CachingManager.get(key);
        return findOne(id);
      }
    }
    
    return null;
  }
  
  /**
   * 清空缓存记录
   * @return 消息代码 0=表示成功
   */
  @Override
  public int clear() {
    // 删除缓存项
    CachingManager.deleteByPattern(CACHE_KEY_ACCESS_TOKEN_ID_PREFIX + "*");
    CachingManager.deleteByPattern(CACHE_KEY_ACCESS_TOKEN_REFRESH_TOKEN_PREFIX + "*");
    
    return 0;
  }
}
