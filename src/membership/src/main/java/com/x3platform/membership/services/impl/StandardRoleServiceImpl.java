package com.x3platform.membership.services.impl;

import com.x3platform.digitalnumber.DigitalNumberContext;
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
public class StandardRoleServiceImpl implements StandardRoleService {

  private static String CACHE_KEY_ID_PREFIX = "x3platform:membership:standard-role:id:";

  private static String DIGITAL_NUMBER_KEY_CODE = "Table_StandardOrganizationUnit_Key_Code";

  /**
   * 数据提供器
   */
  @Autowired
  private StandardRoleMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link StandardRole} 实例的详细信息
   * @return {@link StandardRole} 实例的详细信息
   */
  @Override
  public int save(StandardRole entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (this.provider.selectByPrimaryKey(id) == null) {
      if (StringUtil.isNullOrEmpty(entity.getCode())) {
        entity.setCode(DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE));
      }

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
   * @return {@link StandardRole} 实例的详细信息
   */
  @Override
  public StandardRole findOne(String id) {
    return this.provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link StandardRole} 实例的详细信息
   */
  @Override
  public List<StandardRole> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  /**
   * 根据标准组织单元标识查询所有相关记录
   *
   * @param standardOrganizationUnitId 标准组织单元节点标识
   * @return 所有 {@link StandardRole} 实例的详细信息
   */
  @Override
  public List<StandardRole> findAllByStandardOrganizationUnitId(String standardOrganizationUnitId) {
    return this.provider.findAllByStandardOrganizationUnitId(standardOrganizationUnitId);
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
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：boolean isExist(String id)。");
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
}
