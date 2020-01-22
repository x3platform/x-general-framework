package com.x3platform.membership.services.impl;

import com.x3platform.AuthorizationObject;
import com.x3platform.AuthorizationObjectType;
import com.x3platform.AuthorizationScope;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.membership.mappers.AuthorizationObjectMapper;
import com.x3platform.membership.services.AuthorizationObjectService;
import com.x3platform.security.authority.Authority;
import com.x3platform.security.authority.AuthorityContext;
import com.x3platform.util.StringUtil;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 授权对象服务
 */
public class AuthorizationObjectServiceImpl implements AuthorizationObjectService {

  /**
   * 数据提供器
   */
  @Autowired
  private AuthorizationObjectMapper provider = null;

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条授权对象信息
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 返回一个 {@link AuthorizationObject} 实例的详细信息
   */
  @Override
  public AuthorizationObject findOne(String authorizationObjectType, String authorizationObjectId) {
    AuthorizationObject authorizationObject = null;

    switch (authorizationObjectType.toLowerCase()) {
      case "account":
        authorizationObject = MembershipManagement.getInstance().getAccountService().findOne(authorizationObjectId);
        break;
      case "role":
        authorizationObject = MembershipManagement.getInstance().getRoleService().findOne(authorizationObjectId);
        break;
      case "organization":
        authorizationObject = MembershipManagement.getInstance().getOrganizationUnitService()
          .findOne(authorizationObjectId);
        break;
      case "group":
        authorizationObject = MembershipManagement.getInstance().getGroupService().findOne(authorizationObjectId);
        break;
      case "standardorganization":
        authorizationObject = MembershipManagement.getInstance().getStandardOrganizationUnitService()
          .findOne(authorizationObjectId);
        break;
      case "standardrole":
        authorizationObject = MembershipManagement.getInstance().getStandardRoleService()
          .findOne(authorizationObjectId);
        break;
      default:
        throw new RuntimeException(String.format("未找到相关的授权对象类型：%1$s。", authorizationObjectType));
    }

    return authorizationObject;
  }

  /**
   * 查询授权对象信息
   *
   * @param query 查询参数
   * @return 一个列表
   */
  public List<Map> filter(DataQuery query) {
    return provider.filter(query.getMap());
  }

  /**
   * 查询是否存在相关的记录 名称不能重复
   *
   * @param authorizationObjectName 授权对象名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String authorizationObjectName) {
    return isExistName("", authorizationObjectName);
  }

  /**
   * 查询是否存在相关的记录 名称不能重复
   *
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectName 授权对象名称
   * @return 布尔值
   */
  @Override
  public boolean isExistName(String authorizationObjectType, String authorizationObjectName) {
    boolean isExist = false;

    String[] authorizationObjectTypes = new String[]{
      "account", "role", "organizationunit", "group",
      "standardorganizationunit", "standardrole", "standardgeneralrole"};

    for (String authorizationObjectTypeValue : authorizationObjectTypes) {
      // 如果存在重复的信息返回 true
      if (isExist) {
        return true;
      }

      if (StringUtil.isNullOrEmpty(authorizationObjectType) || authorizationObjectType
        .equals(authorizationObjectTypeValue)) {
        switch (authorizationObjectTypeValue.toLowerCase()) {
          case "account":
            isExist = MembershipManagement.getInstance().getAccountService().isExistName(authorizationObjectName);
            break;
          case "role":
            isExist = MembershipManagement.getInstance().getRoleService().isExistName(authorizationObjectName);
            break;
          case "organization":
            isExist = MembershipManagement.getInstance().getOrganizationUnitService()
              .isExistName(authorizationObjectName);
            break;
          case "group":
            isExist = MembershipManagement.getInstance().getGroupService().isExistName(authorizationObjectName);
            break;
          case "standardorganization":
            isExist = MembershipManagement.getInstance().getStandardOrganizationUnitService()
              .isExistName(authorizationObjectName);
            break;
          case "standardrole":
            isExist = MembershipManagement.getInstance().getStandardRoleService().isExistName(authorizationObjectName);
            break;
          default:
            break;
        }
      }
    }

    return isExist;
  }

  /**
   * 获取实例化的授权对象
   *
   * @param corporationId 公司标识
   * @param authorizationScopes 授权范围对象
   */
//  public List<AuthorizationObject> getInstantiatedAuthorizationObjects(String corporationId,
//    List<AuthorizationScope> authorizationScopes) {
//    List<AuthorizationObject> list = new ArrayList<AuthorizationObject>();
//
//    List<IRoleInfo> roles = MembershipManagement.getInstance().RoleService.FindAllByCorporationId(corporationId);
//
//    for (AuthorizationScope authorizationScope : authorizationScopes) {
//      switch (authorizationScope.AuthorizationObjectType) {
//        // 通用岗位
//        case "GeneralRole":
//
//          MembershipUtil.GetIntersectionRoles(
//            MembershipManagement.getInstance().RoleService
//              .FindAllByGeneralRoleId(authorizationScope.AuthorizationObjectId),
//            roles).ToList().ForEach(item -> list.add((AuthorizationObject) item));
//
//          break;
//
//        // 标准角色
//        case "StandardRole":
//
//          MembershipUtil.GetIntersectionRoles(
//            MembershipManagement.getInstance().RoleService
//              .FindAllByStandardRoleId(authorizationScope.AuthorizationObjectId),
//            roles).ToList().ForEach(item -> list.add((AuthorizationObject) item));
//
//          break;
//
//        // 标准部门
//        case "StandardOrganizationUnit":
//
//          MembershipUtil.GetIntersectionRoles(MembershipManagement.getInstance().RoleService
//            .FindAllByStandardOrganizationUnitId(authorizationScope.AuthorizationObjectId), roles).ToList()
//            .ForEach(item -> list.add((AuthorizationObject) item));
//
//          break;
//
//        default:
//          list.add(authorizationScope.GetAuthorizationObject());
//          break;
//      }
//    }
//
//    return list;
//  }

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param account 帐号信息
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(String scopeTableName, String entityId, String entityClassName,
    String authorityName, Account account) {
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    return provider.hasAuthorityWithAccount(scopeTableName, entityId, entityClassName, authority.getId(),
      account.getId());
  }

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 布尔值
   */
  @Override
  public boolean hasAuthority(String scopeTableName, String entityId, String entityClassName,
    String authorityName, String authorizationObjectType, String authorizationObjectId) {
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);
    if (AuthorizationObjectType.ACCOUNT.getValue().equalsIgnoreCase(authorizationObjectType)) {
      return provider.hasAuthorityWithAccount(scopeTableName, entityId, entityClassName, authority.getId(),
        authorizationObjectId);
    } else {
      return provider.hasAuthority(scopeTableName, entityId, entityClassName, authority.getId(),
        authorizationObjectType, authorizationObjectId);
    }
  }

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param account 帐号信息
   * @return 布尔值
   */
//  public boolean hasAuthority(GenericSqlCommand command, String scopeTableName, String entityId,
//    String entityClassName, String authorityName, Account account) {
//    return provider.hasAuthority(command, scopeTableName, entityId, entityClassName, authorityName, account);
//  }

  /**
   * 判断授权对象是否拥有实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @return 布尔值
   */
//  public boolean hasAuthority(GenericSqlCommand command, String scopeTableName, String entityId,
//    String entityClassName, String authorityName, String authorizationObjectType, String authorizationObjectId) {
//    return provider
//      .hasAuthority(command, scopeTableName, entityId, entityClassName, authorityName, authorizationObjectType,
//        authorizationObjectId);
//  }

  /**
   * 新增实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  @Override
  public void addAuthorizationScopeByEntityId(String scopeTableName, String entityId, String entityClassName,
    String authorityName, String scopeText) {
    // 空值处理
    if (StringUtil.isNullOrEmpty(scopeText)) {
      return;
    }

    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    String[] list = scopeText.split(";|,");

    for (String item : list) {
      if (StringUtil.isNullOrEmpty(item)) {
        continue;
      }

      String[] keys = item.split("#");
      String authorizationObjectType = AuthorizationObjectType.getValue(keys[0]);
      if (!StringUtil.isNullOrEmpty(authorizationObjectType) && !StringUtil.isNullOrEmpty(keys[1])) {
        provider.addAuthorizationScope(scopeTableName, entityId, entityClassName, authority.getId(),
          authorizationObjectType, keys[1]);
      }
    }
  }

  /**
   * 新增实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
//  public void addAuthorizationScope(GenericSqlCommand command, String scopeTableName, String entityId,
//    String entityClassName, String authorityName, String scopeText) {
//    provider.addAuthorizationScope(command, scopeTableName, entityId, entityClassName, authorityName, scopeText);
//  }

  /**
   * 添加授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识
   * @param entityClassName 实体类名称
   */
  @Override
  public void addAuthorizationScopeByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds, String entityClassName) {
    // 空值处理
    if (StringUtil.isNullOrEmpty(entityIds)) {
      return;
    }
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    String[] list = entityIds.split(",|;");

    for (String item : list) {
      if (StringUtil.isNullOrEmpty(item)) {
        continue;
      }
      provider.addAuthorizationScope(scopeTableName, item, entityClassName, authority.getId(),
        authorizationObjectType, authorizationObjectId);
    }
  }

  /**
   * 移除实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  @Override
  public void removeAuthorizationScopeByEntityId(String scopeTableName, String entityId, String entityClassName,
    String authorityName) {
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    provider.removeAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authority.getId());
  }

  /**
   * 移除实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
//  public void removeAuthorizationScope(GenericSqlCommand command, String scopeTableName, String entityId,
//    String entityClassName, String authorityName) {
//    provider.removeAuthorizationScope(command, scopeTableName, entityId, entityClassName, authorityName);
//  }

  /**
   * 移除授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   */
  @Override
  public void removeAuthorizationScopeByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName) {
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);
    provider.removeAuthorizationScopeByAuthorizationObjectId(scopeTableName, authorizationObjectType,
      authorizationObjectId, authority.getId());
  }

  /**
   * 配置实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  @Override
  public void bindAuthorizationScopeByEntityId(String scopeTableName, String entityId, String entityClassName,
    String authorityName, String scopeText) {
    if (!StringUtil.isNullOrEmpty(entityId) && !StringUtil.isNullOrEmpty(authorityName)) {
      // 移除权限信息
      removeAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authorityName);

      // 添加权限信息
      addAuthorizationScopeByEntityId(scopeTableName, entityId, entityClassName, authorityName, scopeText);
    }
  }

  /**
   * 配置实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   * @param scopeText 权限范围的文本
   */
  // public void bindAuthorizationScope(GenericSqlCommand command, String scopeTableName, String entityId,
  //   String entityClassName, String authorityName, String scopeText) {
  //   provider.bindAuthorizationScope(command, scopeTableName, entityId, entityClassName,
  //     authorityName, scopeText);
  // }

  /**
   * 配置授权对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityIds 实体标识 多个标识以半角逗号隔开
   * @param entityClassName 实体类名称
   */
  @Override
  public void bindAuthorizationScopeByAuthorizationObjectIds(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityIds, String entityClassName) {
    if (!StringUtil.isNullOrEmpty(authorizationObjectType) && !StringUtil.isNullOrEmpty(authorizationObjectId)
      && !StringUtil.isNullOrEmpty(authorityName)) {
      // 移除权限信息
      removeAuthorizationScopeByAuthorizationObjectId(scopeTableName, authorizationObjectType,
        authorizationObjectId, authorityName);

      // 添加权限信息
      addAuthorizationScopeByAuthorizationObjectId(scopeTableName, authorizationObjectType,
        authorizationObjectId, authorityName, entityIds, entityClassName);
    }
  }

  /**
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   * @param entityId 实体标识 多个对象以逗号隔开
   * @param entityClassName 实体类名称
   */
  @Override
  public void bindAuthorizationScopeByAuthorizationObjectId(String scopeTableName, String authorizationObjectType,
    String authorizationObjectId, String authorityName, String entityId, String entityClassName) {
    // TODO 这个方法与 bindAuthorizationScopeByAuthorizationObjectIds 的差异是否需要合并
    if (!StringUtil.isNullOrEmpty(authorizationObjectType) && !StringUtil.isNullOrEmpty(authorizationObjectId)
      && !StringUtil.isNullOrEmpty(authorityName)) {
      Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);
      // 移除权限信息
      provider.removeAuthorizationScopeByAuthorizationObjectIdAndEntityId(scopeTableName, authorizationObjectType,
        entityId, authorizationObjectId, authority.getId());
      // 添加权限信息
      addAuthorizationScopeByAuthorizationObjectId(scopeTableName, authorizationObjectType,
        authorizationObjectId, authorityName, entityId, entityClassName);
    }
  }

  /**
   * 查询实体对象的权限信息
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  @Override
  public List<AuthorizationScope> getAuthorizationScopes(
    String scopeTableName,
    String entityId,
    String entityClassName,
    String authorityName) {
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    return provider.getAuthorizationScopes(scopeTableName, entityId, entityClassName, authority.getId());
  }

  /**
   * 查询实体对象的权限信息
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
//  public List<AuthorizationScope> getAuthorizationScope(GenericSqlCommand command,
//    String scopeTableName, String entityId, String entityClassName, String authorityName) {
//    return provider.GetAuthorizationScope(command, scopeTableName, entityId, entityClassName, authorityName);
//  }

  /**
   * 查询实体对象的权限范围的文本
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  @Override
  public String getAuthorizationScopeText(String scopeTableName, String entityId, String entityClassName,
    String authorityName) {
    StringBuilder outString = new StringBuilder();

    List<AuthorizationScope> authorizationScopes = getAuthorizationScopes(scopeTableName,
      entityId, entityClassName, authorityName);

    for (AuthorizationScope authorizationScope : authorizationScopes) {
      if (authorizationScope.getAuthorizationObjectType() != null
        && !StringUtil.isNullOrEmpty(authorizationScope.getAuthorizationObjectId())) {
        outString.append(String
          .format("%1$s#%2$s#%3$s;", authorizationScope.getAuthorizationObjectType(),
            authorizationScope.getAuthorizationObjectId(), authorizationScope.getAuthorizationObjectName()));
      }
    }

    return outString.toString();
  }

  /**
   * 查询实体对象的权限范围的文本
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
//  public String getAuthorizationScopeText(GenericSqlCommand command, String scopeTableName, String entityId,
//    String entityClassName, String authorityName) {
//    StringBuilder outString = new StringBuilder();
//
//    List<AuthorizationScopeObject> authorizationScopes = getAuthorizationScope(command,
//      scopeTableName, entityId, entityClassName, authorityName);
//
//    for (AuthorizationScopeObject authorizationScope : authorizationScopes) {
//      if (!StringUtil.isNullOrEmpty(authorizationScope.AuthorizationObjectType)
//        && !StringUtil.isNullOrEmpty(authorizationScope.AuthorizationObjectId)) {
//        outString.append(String
//          .format("%1$s#%2$s#%3$s;", authorizationScope.AuthorizationObjectType.toLowerCase(),
//            authorizationScope.AuthorizationObjectId, authorizationScope.AuthorizationObjectDescription));
//      }
//    }
//
//    return outString.toString();
//  }

  /**
   * 查询实体对象的权限范围的视图
   *
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
  @Override
  public String getAuthorizationScopeView(String scopeTableName, String entityId, String entityClassName,
    String authorityName) {
    StringBuilder outString = new StringBuilder();

    List<AuthorizationScope> authorizationScopes = getAuthorizationScopes(scopeTableName,
      entityId, entityClassName, authorityName);

    for (AuthorizationScope authorizationScope : authorizationScopes) {
      if (authorizationScope.getAuthorizationObjectType() != null
        && !StringUtil.isNullOrEmpty(authorizationScope.getAuthorizationObjectId())) {
        if (StringUtil.isNullOrEmpty(authorizationScope.getAuthorizationObjectName())) {
          outString.append(String.format("%1$s(ID:%2$s);", authorizationScope.getAuthorizationObjectType(),
            authorizationScope.getAuthorizationObjectId()));
        } else {
          outString.append(String.format("%1$s;", authorizationScope.getAuthorizationObjectName()));
        }
      }
    }

    return outString.toString();
  }

  /**
   * 查询实体对象的权限范围的视图
   *
   * @param command 通用SQL命令对象
   * @param scopeTableName 数据表的名称
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @param authorityName 权限名称
   */
//  public String getAuthorizationScopeView(GenericSqlCommand command, String scopeTableName, String entityId,
//    String entityClassName, String authorityName) {
//    StringBuilder outString = new StringBuilder();
//
//    List<AuthorizationScope> authorizationScopes = getAuthorizationScope(command,
//      scopeTableName, entityId, entityClassName, authorityName);
//
//    for (AuthorizationScope authorizationScope : authorizationScopes) {
//      if (!StringUtil.isNullOrEmpty(authorizationScope.AuthorizationObjectType)
//        && !StringUtil.isNullOrEmpty(authorizationScope.AuthorizationObjectId)) {
//        if (StringUtil.isNullOrEmpty(authorizationScope.AuthorizationObjectDescription)) {
//          outString.append(String.format("%1$s(ID:%2$s);", authorizationScope.AuthorizationObjectType,
//            authorizationScope.AuthorizationObjectId));
//        } else {
//          outString.append(String.format("%1$s;", authorizationScope.AuthorizationObjectDescription));
//        }
//      }
//    }
//
//    return outString.toString();
//  }

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param accountId 用户标识
   * @param authorizationObjectType 授权对象类型
   * @param authorityIds 权限标识
   */
  @Override
  public String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType, String authorityIds) {
    return getAuthorizationScopeEntitySQL(scopeTableName, accountId, authorizationObjectType, authorityIds, "EntityId");
  }

  /**
   * 获取实体对象和授权对象的授权关系
   *
   * @param scopeTableName 数据表的名称
   * @param authorizationObjectType 授权对象类型
   * @param authorizationObjectId 授权对象标识
   * @param authorityName 权限名称
   */
  @Override
  public List<Map> getAuthorizationScopeRelationByAuthorizationObjectId(String scopeTableName,
    String authorizationObjectType, String authorizationObjectId, String authorityName) {
    // 权限信息
    Authority authority = AuthorityContext.getInstance().getAuthorityService().findOneByName(authorityName);

    return provider.getAuthorizationScopeRelationByAuthorizationObjectId(scopeTableName, authorizationObjectType,
      authorizationObjectId, authority.getId());
  }

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param accountId 用户标识
   * @param authorizationObjectType 授权对象类型
   * @param authorityIds 权限标识
   * @param entityIdDataColumnName 实体数据标识
   */
  public String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType, String authorityIds, String entityIdDataColumnName) {
    return getAuthorizationScopeEntitySQL(scopeTableName, accountId, authorizationObjectType, authorityIds,
      entityIdDataColumnName, "", "");
  }

  /**
   * 获取实体对象标识SQL语句
   *
   * @param scopeTableName 数据表的名称
   * @param accountId 用户标识
   * @param authorizationObjectType 授权对象类型
   * @param authorityIds 权限标识
   * @param entityIdDataColumnName 实体类标识的数据列名
   * @param entityClassNameDataColumnName 实体类名称的数据列名
   * @param entityClassName 实体类名称
   */
  @Override
  public String getAuthorizationScopeEntitySQL(String scopeTableName, String accountId,
    AuthorizationObjectType authorizationObjectType, String authorityIds, String entityIdDataColumnName,
    String entityClassNameDataColumnName, String entityClassName) {
    return provider.getAuthorizationScopeEntitySQL(scopeTableName, accountId,
      authorizationObjectType, authorityIds, entityIdDataColumnName, entityClassNameDataColumnName, entityClassName);
  }
}
