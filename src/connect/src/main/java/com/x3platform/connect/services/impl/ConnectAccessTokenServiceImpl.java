package com.x3platform.connect.services.impl;

import com.x3platform.connect.configuration.*;
import com.x3platform.connect.mappers.ConnectAccessTokenMapper;
import com.x3platform.connect.models.ConnectAccessToken;
import com.x3platform.connect.services.ConnectAccessTokenService;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public final class ConnectAccessTokenServiceImpl implements ConnectAccessTokenService {

  @Autowired
  private ConnectAccessTokenMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param  entity 实例详细信息
   * @return ConnectAccessToken 实例详细信息
   */
  @Override
  public int save(ConnectAccessToken entity) {
    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new NullPointerException("实例标识不能为空。");
    }

    if (this.provider.findOne(id) == null) {
      return this.provider.insert(entity);
    } else {
      return this.provider.updateByPrimaryKey(entity);
    }
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public int delete(String id) {
    return this.provider.delete(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 连接器标识
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  @Override
  public ConnectAccessToken findOne(String id) {
    return this.provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  @Override
  public ConnectAccessToken findOneByAccountId(String appKey, String accountId) {
    return this.provider.findOneByAccountId(appKey, accountId);
  }

  /**
   * 查询某条记录
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @return 返回一个实例 ConnectAccessToken 的详细信息
   */
  @Override
  public ConnectAccessToken findOneByRefreshToken(String appKey, String refreshToken) {
    return this.provider.findOneByRefreshToken(appKey, refreshToken);
  }

  @Override
  public List<ConnectAccessToken> findAllByRefreshToken(String appKey, String refreshToken) {
    return this.provider.findAllByRefreshToken(appKey, refreshToken);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 ConnectAccessToken 的详细信息
   */
  @Override
  public List<ConnectAccessToken> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
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
  //public List<ConnectAccessToken> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //  return this.provider.GetPaging(startIndex, pageSize, query, rowCount);
  //}

  /**
   * 查询是否存在相关的记录
   *
   * @param id 会员标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String appKey, String accountId) {
    return this.provider.isExistAccountId(appKey, accountId);
  }

  /**
   * 写入的帐号的访问令牌信息
   *
   * @param appKey    应用标识
   * @param accountId 帐号标识
   * @return
   */
  @Override
  public int write(String appKey, String accountId) {
    ConnectAccessToken param = this.findOneByAccountId(appKey, accountId);

    if (param == null) {
      param = new ConnectAccessToken();

      param.setId(DigitalNumberContext.generate("Key_32DigitGuid"));
      param.setAppKey(appKey);
      param.setAccountId(accountId);
      param.setExpireDate(java.time.LocalDateTime.now().plusSeconds(ConnectConfigurationView.getInstance().getSessionTimeLimit()));
      param.setRefreshToken(DigitalNumberContext.generate("Key_32DigitGuid"));

      this.save(param);
    } else {
      this.refresh(appKey, param.getRefreshToken(), new Date((new Date()).getTime() + ConnectConfigurationView.getInstance().getSessionTimeLimit()));
    }

    return 0;
  }

  /**
   * 刷新帐号的访问令牌
   *
   * @param appKey       应用标识
   * @param refreshToken 刷新令牌
   * @param expireDate   过期时间
   * @return
   */
  @Override
  public int refresh(String appKey, String refreshToken, Date expireDate) {
    String nextRefreshToken = DigitalNumberContext.generate("Key_32DigitGuid");
    return this.provider.refresh(appKey, refreshToken, expireDate, nextRefreshToken);
  }

  /**
   * 清理过期时间之前的缓存记录
   *
   * @param expiryTime 过期时间
   */
  @Override
  public int clear(Date expiryTime) {
    return this.provider.clear(expiryTime);
  }

  /**
   * 清空缓存记录
   */
  @Override
  public int clear() {
    return this.provider.clear(new Date());
  }
}
