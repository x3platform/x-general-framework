package com.x3platform;

import com.x3platform.security.authority.*;

/**
 * 权限范围接口
 *
 * @author ruanyu
 */
public interface AuthorizationScope {

  /**
   * 权限对象信息
   */
  AuthorizationObject getAuthorizationObject();

  void setAuthorizationObject(AuthorizationObject value);

  /**
   * 权限信息
   */
  Authority getAuthority();

  void setAuthority(Authority value);

  /**
   * 实体信息
   */
  EntityClass getEntityClass();

  void setEntityClass(EntityClass value);
}
