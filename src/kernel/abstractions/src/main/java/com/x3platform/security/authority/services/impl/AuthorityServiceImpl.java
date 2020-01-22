package com.x3platform.security.authority.services.impl;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.membership.OrganizationUnit;
import com.x3platform.security.authority.*;
import com.x3platform.security.authority.services.*;
import com.x3platform.security.authority.mappers.*;

import com.x3platform.KernelContext;
import com.x3platform.data.*;
import com.x3platform.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author ruanyu
 */
public class AuthorityServiceImpl implements AuthorityService {

  private static final String CACHE_KEY_NAME_PREFIX = "x3platform:security:authority:name:";

  /**
   * 数据提供器
   */
  @Autowired
  private AuthorityMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link Authority} 实例的详细信息
   * @return {@link Authority} 实例的详细信息
   */
  @Override
  public int save(Authority entity) {
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
   * @return {@link Authority} 实例的详细信息
   */
  @Override
  public Authority findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return {@link Authority} 实例的详细信息
   */
  @Override
  public Authority findOneByName(String name) {
    Authority entity = null;

    String key = CACHE_KEY_NAME_PREFIX + name;

    CachingManager.contains(key);

    if (CachingManager.contains(key)) {
      entity = (Authority) CachingManager.get(key);
    }

    if (entity == null) {
      entity = provider.findOneByName(name);

      if (entity != null) {
        CachingManager.set(key, entity);
      }
    }

    return entity;
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link Authority} 实例的详细信息
   */
  @Override
  public List<Authority> findAll(DataQuery query) {
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
}
