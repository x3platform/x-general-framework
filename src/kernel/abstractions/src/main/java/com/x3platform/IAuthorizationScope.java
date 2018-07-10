package com.x3platform;

import com.x3platform.security.authority.*;

/**
 * 权限范围接口
 */
public interface IAuthorizationScope {

  /**
   * 权限对象信息
   */
  IAuthorizationObject getAuthorizationObject();

  void setAuthorizationObject(IAuthorizationObject value);

  /**
   * 权限信息
   */
  AuthorityInfo getAuthority();

  void setAuthority(AuthorityInfo value);

  /**
   * 实体信息
   */
  EntityClass getEntityClass();

  void setEntityClass(EntityClass value);
}
