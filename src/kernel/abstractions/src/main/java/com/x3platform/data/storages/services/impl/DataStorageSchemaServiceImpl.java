package com.x3platform.data.storages.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.data.DataQuery;
import com.x3platform.data.storages.mappers.DataStorageSchemaMapper;
import com.x3platform.data.storages.models.DataStorageSchema;
import com.x3platform.data.storages.services.DataStorageSchemaService;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class DataStorageSchemaServiceImpl implements DataStorageSchemaService {
  /**
   * 数据提供器
   */
  @Autowired
  private DataStorageSchemaMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 DataStorageSchema 详细信息
   * @return 实例 DataStorageSchema 详细信息
   */
  @Override
  public int save(DataStorageSchema entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (provider.selectByPrimaryKey(id) == null) {
      affectedRows = provider.insert(entity);
    } else {
      affectedRows = provider.updateByPrimaryKey(entity);
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
    int affectedRows = provider.deleteByPrimaryKey(id);

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
   * @return 返回实例 DataStorageSchema 的详细信息
   */
  @Override
  public DataStorageSchema findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询某条记录
   *
   * @param applicationId 所属应用标识
   * @return 返回实例 DataStorageSchema 的详细信息
   */
  @Override
  public DataStorageSchema findOneByApplicationId(String applicationId){
    return provider.findOneByApplicationId(applicationId);
  }

  /**
   * 查询某条记录
   *
   * @param applicationName 所属应用名称
   * @return 返回实例 DataStorageSchema 的详细信息
   */
  @Override
  public DataStorageSchema findOneByApplicationName(String applicationName){
    return provider.findOneByApplicationName(applicationName);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 DataStorageSchema 的详细信息
   */
  @Override
  public List<DataStorageSchema> findAll(DataQuery query)
  {
    return provider.findAll(query.getMap());
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
