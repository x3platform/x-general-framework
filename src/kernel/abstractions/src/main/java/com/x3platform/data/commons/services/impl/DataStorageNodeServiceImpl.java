package com.x3platform.data.commons.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.data.DataQuery;
import com.x3platform.data.commons.mappers.DataStorageNodeMapper;
import com.x3platform.data.commons.models.DataStorageNode;
import com.x3platform.data.commons.services.DataStorageNodeService;
import com.x3platform.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class DataStorageNodeServiceImpl implements DataStorageNodeService {
  /**
   * 数据提供器
   */
  @Autowired
  private DataStorageNodeMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 DataStorageNode 详细信息
   * @return 实例 DataStorageNode 详细信息
   */
  @Override
  public int save(DataStorageNode entity) {
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
    int affectedRows = provider.deleteByPrimaryKey(id);

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
   * @return 返回实例 DataStorageNode 的详细信息
   */
  @Override
  public DataStorageNode findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 DataStorageNode 的详细信息
   */
  @Override
  public List<DataStorageNode> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  /**
   * 根据存储架构名称查询所有相关记录
   *
   * @param schemaId 存储架构标识
   * @return 返回所有实例 DataStorageNode 的详细信息
   */
  @Override
  public List<DataStorageNode> findAllBySchemaId(String schemaId) {
    return provider.findAllBySchemaId(schemaId);
  }

  /**
   * 根据应用标识查询所有相关记录
   *
   * @param applicationId 应用标识
   * @return 返回所有实例 {@link DataStorageNode} 的详细信息
   */
  @Override
  public List<DataStorageNode> findAllByApplicationId(String applicationId) {
    return provider.findAllByApplicationId(applicationId);
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
    return isExist(id);
  }

  /**
   * 获取下拉列表信息
   *
   * @return 所有相关 {@link DataStorageNode} 实例的详细信息
   */
  @Override
  public List<DataStorageNode> getCombobox(String applicationId) {
    return provider.getCombobox(applicationId);
  }

  /**
   * 获取默认的存储节点信息
   *
   * @return 返回实例 DataStorageNode 的详细信息
   */
  @Override
  public DataStorageNode getDefault() {
    return findOne("default");
  }

  /**
   * 根据存储架构标识获取默认的存储节点信息
   *
   * @param schemaId 存储架构标识
   * @return 返回 {@link DataStorageNode} 的详细信息
   */
  @Override
  public DataStorageNode getDefaultNodeBySchemaId(String schemaId) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException("此对象未实现方法：boolean isExist(String id)。");
  }

  /**
   * 根据存储架构标识获取默认的存储节点信息
   *
   * @param applicationId 所属应用标识
   * @return 返回 {@link DataStorageNode} 的详细信息
   */
  @Override
  public DataStorageNode getDefaultNodeByApplicationId(String applicationId) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException(
      "此对象未实现方法：DataStorageNode getDefaultNodeByApplicationId(String applicationId)。");
  }

  /**
   * 根据存储架构标识获取默认的存储节点信息
   *
   * @param applicationName 存储架构标识
   * @return 返回 {@link DataStorageNode} 的详细信息
   */
  @Override
  public DataStorageNode getDefaultNodeByApplicationName(String applicationName) {
    // 实现类需要重新实现此方法.
    throw new UnsupportedOperationException(
      "此对象未实现方法：DataStorageNode getDefaultNodeByApplicationId(String applicationId)。");
    // Application application = AppsContext.getInstance().getApplicationService().findOneByApplicationName(applicationName);
    // return this.getDefaultNodeByApplicationId(application.getId());
  }
}
