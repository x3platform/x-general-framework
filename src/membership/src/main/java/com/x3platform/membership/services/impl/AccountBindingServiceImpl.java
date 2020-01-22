package com.x3platform.membership.services.impl;

import com.x3platform.membership.*;
import com.x3platform.membership.services.*;
import com.x3platform.membership.mappers.*;

import com.x3platform.KernelContext;
import com.x3platform.data.*;
import com.x3platform.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author ruanyu
 */
public class AccountBindingServiceImpl implements AccountBindingService {

  /**
   * 数据提供器
   */
  @Autowired
  private AccountBindingMapper provider = null;

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @return {@link AccountBinding} 的详细信息
   */
  @Override
  public AccountBinding findOne(String accountId, String bindingType) {
    return provider.findOne(accountId, bindingType);
  }

  /**
   * 查询某个用户的所有相关记录
   *
   * @param accountId 帐号唯一标识
   * @return 返回所有实例{@link AccountBinding}的详细信息
   */
  @Override
  public List<AccountBinding> findAllByAccountId(String accountId) {
    return provider.findAllByAccountId(accountId);
  }

  /**
   * 查询相关用户的所有对象标识记录
   *
   * @param accountIds 帐号唯一标识, 多个以逗号隔开
   * @param bindingType 绑定类型
   * @return 对象标识列表
   */
  @Override
  public List<String> findAllBindingObjectIds(List<String> accountIds, String bindingType) {
    return provider.findAllBindingObjectIds(accountIds, bindingType);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @return 布尔值
   */
  @Override
  public boolean isExist(String accountId, String bindingType) {
    return provider.isExist(accountId, bindingType);
  }

  /**
   * 绑定第三方帐号绑定关系
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @param bindingObjectId 绑定对象唯一标识
   * @param bindingOptions 绑定的参数信息
   */
  @Override
  public int bind(String accountId, String bindingType, String bindingObjectId, String bindingOptions) {
    int affectedRows = provider.bind(accountId, bindingType, bindingObjectId, bindingOptions);
    return affectedRows == 1 ? 0 : 1;
  }

  /**
   * 解除第三方帐号绑定关系
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @param bindingObjectId 绑定对象唯一标识
   */
  @Override
  public int unbind(String accountId, String bindingType, String bindingObjectId) {
    int affectedRows = provider.unbind(accountId, bindingType, bindingObjectId);
    return affectedRows == 1 ? 0 : 1;
  }
}
