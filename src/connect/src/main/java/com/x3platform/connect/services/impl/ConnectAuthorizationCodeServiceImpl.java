package com.x3platform.connect.services.impl;

import com.x3platform.Constants;
import com.x3platform.connect.configuration.ConnectConfiguration;
import com.x3platform.connect.configuration.ConnectConfigurationView;
import com.x3platform.connect.mappers.ConnectAuthorizationCodeMapper;
import com.x3platform.connect.models.ConnectAuthorizationCode;
import com.x3platform.connect.services.ConnectAuthorizationCodeService;
import com.x3platform.membership.Account;
import org.springframework.beans.factory.annotation.Autowired;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static com.x3platform.Constants.TEXT_EMPTY;

public class ConnectAuthorizationCodeServiceImpl implements ConnectAuthorizationCodeService {

  @Autowired
  private ConnectAuthorizationCodeMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return ConnectAuthorizationCode 实例详细信息
   * @param entity ConnectAuthorizationCode 实例详细信息
   */
  @Override
  public int save(ConnectAuthorizationCode entity) {
    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(entity.getId())) {
      throw new NullPointerException("实例标识不能为空。");
    }
    // 过滤 Cross Site Script
    // entity = StringHelper.<ConnectAuthorizationCode>ToSafeXSS(entity);

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
    return this.provider.deleteByPrimaryKey(id);
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @return 返回一个实例 ConnectAuthorizationCode 的详细信息
   * @param id 连接器标识
   */
  @Override
  public ConnectAuthorizationCode findOne(String id) {
    return this.provider.findOne(id);
  }

  /**
   * 查询某条记录
   *
   * @return 返回一个实例 ConnectAuthorizationCode 的详细信息
   * @param appKey    应用标识
   * @param accountId 帐号标识
   */
  @Override
  public ConnectAuthorizationCode findOneByAccountId(String appKey, String accountId) {
    return this.provider.findOneByAccountId(appKey, accountId);
  }

  /**
   * 查询所有相关记录
   *
   * @return 返回所有实例 ConnectAuthorizationCode 的详细信息
   * @param query  数据查询参数
   */
  @Override
  public List<ConnectAuthorizationCode> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @return 布尔值
   * @param id 会员标识
   */
  @Override
  public boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @return 布尔值
   * @param appKey    应用标识
   * @param accountId 帐号标识
   */
  @Override
  public boolean isExist(String appKey, String accountId) {
    return this.provider.isExistAccountId(appKey, accountId);
  }

  /**
   * 获取帐号的授权码
   *
   * @return 授权码
   * @param appKey  应用标识
   * @param account 帐号信息
   */
  @Override
  public String getAuthorizationCode(String appKey, Account account) {
    ConnectAuthorizationCode code = this.findOneByAccountId(appKey, account.getId());

    return code == null ? TEXT_EMPTY : code.getId();
  }
}
