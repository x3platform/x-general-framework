package com.x3platform.membership.services;

import com.x3platform.membership.AccountBinding;

import com.x3platform.data.DataQuery;

import java.util.*;

/**
 * @author ruanyu
 */
public interface AccountBindingService {

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
  AccountBinding findOne(String accountId, String bindingType);

  /**
   * 查询某个用户的所有相关记录
   *
   * @param accountId 帐号唯一标识
   * @return 返回所有实例{@link AccountBinding}的详细信息
   */
  List<AccountBinding> findAllByAccountId(String accountId);

  /**
   * 查询相关用户的所有对象标识记录
   *
   * @param accountIds 帐号唯一标识列表
   * @param bindingType 绑定类型
   * @return 对象标识列表
   */
  List<String> findAllBindingObjectIds(List<String> accountIds, String bindingType);

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
  boolean isExist(String accountId, String bindingType);

  /**
   * 绑定第三方帐号绑定关系
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @param bindingObjectId 绑定对象唯一标识
   * @param bindingOptions 绑定的参数信息
   */
  int bind(String accountId, String bindingType, String bindingObjectId, String bindingOptions);

  /**
   * 解除第三方帐号绑定关系
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @param bindingObjectId 绑定对象唯一标识
   */
  int unbind(String accountId, String bindingType, String bindingObjectId);
}
