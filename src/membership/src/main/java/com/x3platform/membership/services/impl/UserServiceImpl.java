package com.x3platform.membership.services.impl;

import com.x3platform.membership.*;
import com.x3platform.membership.models.UserInfo;
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
public class UserServiceImpl implements UserService {

  /**
   * 数据提供器
   */
  @Autowired
  private UserMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link User} 实例的详细信息
   * @return {@link User} 实例的详细信息
   */
  @Override
  public int save(User entity) {
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
   * @return {@link User} 实例的详细信息
   */
  @Override
  public User findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link User} 实例的详细信息
   */
  @Override
  public List<User> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
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

  @Override
  public User createEmptyUser(String accountId) {
    User param = new UserInfo();

    param.setId(accountId);
    param.setAccountId(accountId);

    param.setAccount(MembershipManagement.getInstance().getAccountService().createEmptyAccount(accountId));

    param.setModifiedDate(LocalDateTime.now());
    param.setCreatedDate(LocalDateTime.now());

    return param;
  }

  @Override
  public int setDefaultCorporationAndDepartments(String accountId, String organizationUnitIds) {
    List<String> list = new ArrayList<String>();

    String[] keys = organizationUnitIds.split(",");

    for (int i = 0; i < 4; i++) {
      if (i < keys.length) {
        list.add(i, keys[i]);
      } else {
        list.add(i, "");
      }
    }

    this.provider.setDefaultCorporationAndDepartments(accountId, list.get(0), list.get(1), list.get(2), list.get(3));
    return 0;
  }

  @Override
  public int setDefaultOrganizationUnit(String accountId, String organizationUnitId) {
    if (!isExist(accountId)) {
      User entity = createEmptyUser(accountId);
      provider.insert(entity);
    }

    this.provider.setDefaultOrganizationUnit(accountId, organizationUnitId);
    return 0;
  }

  @Override
  public int setDefaultRole(String accountId, String roleId) {
    tryCreateUser(accountId);
    this.provider.setDefaultRole(accountId, roleId);

    Role role = MembershipManagement.getInstance().getRoleService().findOne(roleId);

    if (role == null)
    {
      // 未找到角色信息。
      return 1;
    }

    // 设置默认公司、部门信息。

    String organizationUnitIds = "";

    OrganizationUnit organizationUnit = MembershipManagement.getInstance().getOrganizationUnitService()
      .findCorporationByOrganizationUnitId(role.getOrganizationUnitId());

    if (organizationUnit == null)
    {
      // 未找到角色相关的公司信息。
      return 2;
    }

    organizationUnitIds += organizationUnit.getId() + ",";

    int depth = 1;

    organizationUnit = MembershipManagement.getInstance().getOrganizationUnitService()
      .findOneByRoleId(roleId, depth);

    while (organizationUnit != null)
    {
      organizationUnitIds += organizationUnit.getId() + ",";

      depth++;

      organizationUnit = MembershipManagement.getInstance().getOrganizationUnitService().findOneByRoleId(roleId, depth);
    }

    setDefaultCorporationAndDepartments(accountId, organizationUnitIds);

    // 统一交给角色数据服务设置。
    MembershipManagement.getInstance().getRoleService().setDefaultRelation(accountId, roleId);

    return 0;
  }

  private void tryCreateUser(String accountId) {
    if (!isExist(accountId)) {
      User entity = createEmptyUser(accountId);
      provider.insert(entity);
    }
  }
}
