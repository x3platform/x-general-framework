package com.x3platform.membership.services.impl;

import com.x3platform.membership.*;
import com.x3platform.membership.services.*;
import com.x3platform.membership.mappers.*;

import com.x3platform.KernelContext;
import com.x3platform.data.*;
import com.x3platform.util.*;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author ruanyu
 */
public class GroupServiceImpl implements GroupService {

  /**
   * 数据提供器
   */
  @Autowired
  private GroupMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link Group} 实例的详细信息
   * @return {@link Group} 实例的详细信息
   */
  @Override
  public int save(Group entity) {
    int affectedRows;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (this.provider.selectByPrimaryKey(id) == null) {
      affectedRows = this.provider.insert(entity);
    } else {
      affectedRows = this.provider.updateByPrimaryKey(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

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

    KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link Group} 实例的详细信息
   */
  @Override
  public Group findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link Group} 实例的详细信息
   */
  @Override
  public List<Group> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  /**
   * 查询某个用户所在的所有群组信息
   *
   * @param accountId 帐号标识
   * @return 所有 {@link Group} 实例的详细信息
   */
  @Override
  public List<Group> findAllByAccountId(String accountId) {
    return this.provider.findAllByAccountId(accountId);
  }

  /**
   * 查询所有相关记录
   *
   * @param catalogItemId 分类节点标识
   * @return 所有实例 {@link Group} 的详细信息
   */
  @Override
  public List<Group> findAllByCatalogItemId(String catalogItemId) {
    return this.provider.findAllByCatalogItemId(catalogItemId);
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
    return provider.isExist(id);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param name 名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String name) {
    return provider.isExistName(name);
  }

  /**
   * 查询是否存在相关的记录
   *
   * @param globalName 全局名称
   * @return 布尔值
   */
  @Override
  public boolean isExistGlobalName(String globalName) {
    return provider.isExistGlobalName(globalName);
  }

  /**
   * 检测是否存在相关的记录
   *
   * @param id 群组标识
   * @param name 群组名称
   * @return 0:代表成功 1:代表已存在相同名称
   */
  @Override
  public int rename(String id, String name) {
    return this.provider.rename(id, name);
  }

  /**
   * 角色全路径
   *
   * @param name 通用角色名称
   * @param catalogItemId 所属类别标识
   */
  public String combineFullPath(String name, String catalogItemId) {
    // TODO
    // String path = MembershipManagement.Instance.CatalogItemService.GetCatalogItemPathByCatalogItemId(catalogItemId);
    // return String.format("%1$s%2$s", path, name);
    String path = "";
    return String.format("%1$s%2$s", path, name);
  }

  /**
   * 通用角色唯一名称
   *
   * @param name 通用角色名称
   * @param catalogItemId 所属类别标识
   */
  public String combineDistinguishedName(String name, String catalogItemId) {
    // TODO 待处理
    // String path = MembershipManagement.getInstance.CatalogItemService.GetLDAPOUPathByCatalogItemId(catalogItemId);
    // return String.format("CN=%1$s,%2$s%3$s", name, path, LDAPConfigurationView.Instance.SuffixDistinguishedName);

    String path = "";

    return String.format("CN=%1$s,%2$s%3$s", name, path, "");
  }

  /**
   * 设置全局名称
   *
   * @param id 帐户标识
   * @param globalName 全局名称
   * @return 0 操作成功 | 1 操作失败
   */
  @Override
  public int setGlobalName(String id, String globalName) {
    if (StringUtil.isNullOrEmpty(globalName)) {
      // 对象【${Id}】全局名称不能为空。
      return 1;
    }

    if (isExistGlobalName(globalName)) {
      return 2;
    }

    // 检测是否存在对象
    if (!isExist(id)) {
      // 对象【${Id}】不存在。
      return 3;
    }

    // if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON")) {
			/*
			IGroupInfo originalObject = FindOne(id);

			if (originalObject != null)
			{
			    // 由于外部系统直接同步到人员及权限管理的数据库中，
			    // 所以 Active Directory 上不会直接创建相关对象，需要手工设置全局名称并创建相关对象。
			    if (!string.IsNullOrEmpty(originalObject.GlobalName)
			        && LDAPManagement.Instance.Group.IsExistName(originalObject.GlobalName))
			    {
			        LDAPManagement.Instance.Group.Rename(originalObject.GlobalName, globalName);
			    }
			    else
			    {
			        LDAPManagement.Instance.Group.Add(globalName, MembershipManagement.Instance.OrganizationUnitService.GetLDAPOUPathByOrganizationUnitId(originalObject.Id));
			    }
			}
			 */
    // }

    return this.provider.setGlobalName(id, globalName);
  }

  /**
   * 设置企业邮箱状态
   *
   * @param id 群组标识
   * @param status 状态标识, 1:启用, 0:禁用
   * @return 0 设置成功, 1 设置失败.
   */
  @Override
  public int setEnableEmail(String id, int status) {
    return this.provider.setEnableEmail(id, status);
  }

  // -------------------------------------------------------
  // 设置帐号和群组关系
  // -------------------------------------------------------

  /**
   * 根据帐号查询相关群组的关系
   *
   * @param accountId 帐号标识
   * @return Table Columns：AccountId, GroupId, IsDefault, BeginDate, EndDate
   */
  @Override
  public List<AccountGroupRelation> findAllRelationByAccountId(String accountId) {
    return this.provider.findAllRelationByAccountId(accountId);
  }

  /**
   * 根据群组查询相关帐号的关系
   *
   * @param groupId 群组标识
   * @return Table Columns：AccountId, GroupId, IsDefault, BeginDate, EndDate
   */
  @Override
  public List<AccountGroupRelation> findAllRelationByGroupId(String groupId) {
    return this.provider.findAllRelationByGroupId(groupId);
  }

  /**
   * 添加帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   */
  @Override
  public int addRelation(String accountId, String groupId) {
    return addRelation(accountId, groupId, LocalDateTime.now(), DateUtil.getMaxLocalDateTime());
  }

  /**
   * 添加帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @param beginDate 启用时间
   * @param endDate 停用时间
   */
  @Override
  public int addRelation(String accountId, String groupId, LocalDateTime beginDate, LocalDateTime endDate) {
    if (StringUtil.isNullOrEmpty(accountId)) {
      // 帐号标识不能为空
      return 1;
    }

    if (StringUtil.isNullOrEmpty(groupId)) {
      // 群组标识不能为空
      return 2;
    }

//    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
//    {
//      Account account = MembershipManagement.Instance.AccountService[accountId];
//
//      IGroupInfo group = MembershipManagement.Instance.GroupService[groupId];
//
//      // 帐号对象、帐号的全局名称、帐号的登录名、群组对象、群组的全局名称等不能为空。
//      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && group != null && !StringUtil.isNullOrEmpty(group.GlobalName))
//      {
//        LDAPManagement.Instance.Group.AddRelation(account.LoginName, LDAPSchemaClassType.User, group.Name);
//      }
//    }

    return this.provider.addRelation(accountId, groupId, beginDate, endDate);
  }

  /**
   * 续约帐号与相关角色的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   * @param endDate 新的截止时间
   */
  @Override
  public int extendRelation(String accountId, String groupId, java.time.LocalDateTime endDate) {
    return this.provider.extendRelation(accountId, groupId, endDate);
  }

  /**
   * 移除帐号与相关群组的关系
   *
   * @param accountId 帐号标识
   * @param groupId 群组标识
   */
  @Override
  public int removeRelation(String accountId, String groupId) {
//    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
//    {
//      Account account = MembershipManagement.Instance.AccountService[accountId];
//
//      IGroupInfo group = MembershipManagement.Instance.GroupService[groupId];
//
//      // 帐号对象、帐号的全局名称、帐号的登录名、群组对象、群组的全局名称等不能为空。
//      if (account != null && !StringUtil.isNullOrEmpty(account.GlobalName) && !StringUtil.isNullOrEmpty(account.LoginName) && group != null && !StringUtil.isNullOrEmpty(group.GlobalName))
//      {
//        LDAPManagement.Instance.Group.RemoveRelation(account.LoginName, LDAPSchemaClassType.User, group.GlobalName);
//      }
//    }

    return this.provider.removeRelation(accountId, groupId);
  }

  /**
   * 移除帐号已过期的群组关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeExpiredRelation(String accountId) {
//    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
//    {
//      List<AccountGroupRelation> list = FindAllRelationByAccountId(accountId);
//
//      for (AccountGroupRelation item : list)
//      {
//        RemoveRelation(item.AccountId, item.GroupId);
//      }
//
//      return 0;
//    }
//    else
//    {
//      return this.provider.RemoveExpiredRelation(accountId);
//    }

    return this.provider.removeExpiredRelation(accountId);
  }

  /**
   * 移除帐号所有相关群组的关系
   *
   * @param accountId 帐号标识
   */
  @Override
  public int removeAllRelation(String accountId) {
//    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
//    {
//      List<AccountGroupRelation> list = findAllRelationByAccountId(accountId);
//
//      for (AccountGroupRelation item : list)
//      {
//        RemoveRelation(item.AccountId, item.GroupId);
//      }
//
//      return 0;
//    }
//    else
//    {
//      return this.provider.removeAllRelation(accountId);
//    }

    return this.provider.removeAllRelation(accountId);
  }

  /**
   * 清理群组与帐号的关系
   *
   * @param groupId 群组标识
   */
  @Override
  public int clearupRelation(String groupId) {
//    if (LDAPConfigurationView.Instance.IntegratedMode.equals("ON"))
//    {
//      List<AccountGroupRelation> list = this.FindAllRelationByGroupId(groupId);
//
//      for (AccountGroupRelation item : list)
//      {
//        RemoveRelation(item.AccountId, item.GroupId);
//      }
//
//      return 0;
//    }
//    else
//    {
//      return this.provider.clearupRelation(groupId);
//    }
    return this.provider.clearupRelation(groupId);
  }
}
