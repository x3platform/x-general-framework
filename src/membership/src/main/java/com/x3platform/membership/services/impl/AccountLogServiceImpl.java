package com.x3platform.membership.services.impl;

import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.membership.*;
import com.x3platform.membership.models.AccountLogInfo;
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
public class AccountLogServiceImpl implements AccountLogService {
  /**
   * 数据提供器
   */
  @Autowired
  private AccountLogMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link AccountLog} 实例的详细信息
   * @return {@link AccountLog} 实例的详细信息
   */
  @Override
  public int save(AccountLog entity) {
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

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
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
   * @param id 标识
   * @return {@link AccountLog} 实例的详细信息
   */
  @Override
  public AccountLog findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return {@link AccountLog} 实例的详细信息
   */
  @Override
  public List<AccountLog> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }



  /**
   * 查询所有相关记录
   *
   * @param accountId 所属帐号标识
   * @return 所有相关 {@link AccountLog} 实例的详细信息
   */
  @Override
  public List<AccountLog> findAllByAccountId(String accountId)
  {
    return this.provider.findAllByAccountId(accountId);
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
  @Override
  public boolean isExist(String id) {
    return this.provider.isExist(id);
  }

  /**
   * 记录日志
   *
   * @param accountId     所属帐号标识
   * @param operationName 操作名称
   * @param description   描述信息
   * @return 消息代码 0 保存成功 | 1 保存失败
   */
  @Override
  public int log(String accountId, String operationName, String description) {
    return this.log(accountId, operationName, description, null);
  }

  /**
   * 记录日志
   *
   * @param accountId          所属帐号标识
   * @param operationName      操作名称
   * @param description        描述信息
   * @param operationBy 操作者标识
   * @return 消息代码 0 保存成功 | 1 保存失败
   */
  @Override
  public int log(String accountId, String operationName, String description, String operationBy) {
    return this.log(accountId, operationName, description, operationBy, null);
  }

  /**
   * 记录日志
   *
   * @param accountId      所属帐号标识
   * @param operationName  操作名称
   * @param description    描述信息
   * @param operationBy    操作者标识
   * @param snapshotObject 原始对象
   * @return 消息代码 0 保存成功 | 1 保存失败
   */
  @Override
  public int log(String accountId, String operationName, String description, String operationBy, Account snapshotObject) {

    Account account = KernelContext.getCurrent().getUser();

    // 保存实体数据操作记录
    AccountLog param = new AccountLogInfo();

    param.setId(DigitalNumberContext.generate("Key_Guid"));
    param.setAccountId(accountId);
    param.setOperationBy(operationBy);
    param.setOperationName(operationName);
    // param.OriginalObjectValue = originalObject == null ? string.Empty : originalObject.Serializable();
    param.setDescription(description);
    param.setCreatedDate(new Date());

    return this.save(param);
  }
}
