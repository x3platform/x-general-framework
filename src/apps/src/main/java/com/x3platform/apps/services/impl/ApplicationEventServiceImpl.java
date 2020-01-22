package com.x3platform.apps.services.impl;

import com.x3platform.apps.models.*;
import com.x3platform.apps.services.*;
import com.x3platform.apps.mappers.*;

import com.x3platform.KernelContext;
import com.x3platform.data.*;
import com.x3platform.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 *
 * @author ruanyu
 */
public class ApplicationEventServiceImpl implements ApplicationEventService {
  /**
   * 数据提供器
   */
  @Autowired
  private ApplicationEventMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------
  
  /**
   * 保存记录
   *
   * @param entity {@link ApplicationEvent} 实例的详细信息
   * @return {@link ApplicationEvent} 实例的详细信息
   */
  @Override
  public int save(ApplicationEvent entity) {
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
   * @return {@link ApplicationEvent} 实例的详细信息
   */
  @Override
  public ApplicationEvent findOne(String id) {
    return this.provider.selectByPrimaryKey(id) ;
  }
  
  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link ApplicationEvent} 实例的详细信息
   */
  @Override
  public List<ApplicationEvent> findAll(DataQuery query)
  {
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
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：boolean isExist(String id)。");
  }
}
