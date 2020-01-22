package com.x3platform.membership.mappers;

import com.x3platform.membership.AccountBinding;

import java.util.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author ruanyu
 */
public interface AccountBindingMapper {

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
  AccountBinding findOne(@Param("account_id") String accountId, @Param("binding_type") String bindingType);

  /**
   * 查询某个用户的所有相关记录
   *
   * @param accountId 帐号唯一标识
   * @return 返回所有实例{@link AccountBinding}的详细信息
   */
  List<AccountBinding> findAllByAccountId(@Param("account_id") String accountId);

  /**
   * 查询相关用户的所有对象标识记录
   *
   * @param accountIds 帐号唯一标识, 多个以逗号隔开
   * @param bindingType 绑定类型
   * @return 对象标识列表
   */
  List<String> findAllBindingObjectIds(@Param("account_ids") List<String> accountIds,
    @Param("binding_type") String bindingType);

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
  boolean isExist(@Param("account_id") String accountId, @Param("binding_type") String bindingType);

  /**
   * 绑定第三方帐号绑定关系
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @param bindingObjectId 绑定对象唯一标识
   * @param bindingOptions 绑定的参数信息
   */
  int bind(@Param("account_id") String accountId,
    @Param("binding_type") String bindingType,
    @Param("binding_object_id") String bindingObjectId,
    @Param("binding_options") String bindingOptions);

  /**
   * 解除第三方帐号绑定关系
   *
   * @param accountId 帐号唯一标识
   * @param bindingType 绑定类型
   * @param bindingObjectId 绑定对象唯一标识
   */
  int unbind(@Param("account_id") String accountId,
    @Param("binding_type") String bindingType,
    @Param("binding_object_id") String bindingObjectId);
}
