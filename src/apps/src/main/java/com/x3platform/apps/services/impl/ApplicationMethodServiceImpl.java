package com.x3platform.apps.services.impl;

import static com.x3platform.apps.Constants.APPLICATION_METHOD_NOOP_DESCRIPTION;
import static com.x3platform.apps.Constants.APPLICATION_METHOD_NOOP_OPTIONS;

import com.x3platform.KernelContext;
import com.x3platform.apps.mappers.ApplicationMethodMapper;
import com.x3platform.apps.models.ApplicationMethod;
import com.x3platform.apps.services.ApplicationMethodService;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.data.DataQuery;
import com.x3platform.util.StringUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruanyu
 */
public class ApplicationMethodServiceImpl implements ApplicationMethodService {
  
  private static final String CACHE_KEY_NAME_PREFIX = "x3platform:apps:application-method:name:";
  
  /**
   * 数据提供器
   */
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ApplicationMethodMapper provider = null;
  
  // -------------------------------------------------------
  // 缓存管理
  // -------------------------------------------------------
  
  /**
   * 添加缓存项
   */
  private void addCacheItem(ApplicationMethod item) {
    if (!StringUtil.isNullOrEmpty(item.getName())) {
      String key = CACHE_KEY_NAME_PREFIX + item.getName();
      CachingManager.set(key, item, CachingManager.getRandomMinutes());
    }
  }
  
  /**
   * 移除缓存项
   */
  private void removeCacheItem(ApplicationMethod item) {
    if (!StringUtil.isNullOrEmpty(item.getName())) {
      String key = CACHE_KEY_NAME_PREFIX + item.getName();
      if (CachingManager.contains(key)) {
        CachingManager.delete(key);
      }
    }
  }
  
  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------
  
  /**
   * 保存记录
   *
   * @param entity {@link ApplicationMethod} 实例的详细信息
   * @return {@link ApplicationMethod} 实例的详细信息
   */
  @Override
  public int save(ApplicationMethod entity) {
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
    
    // 保存数据后, 更新缓存信息
    entity = provider.selectByPrimaryKey(entity.getId());
    
    if (entity != null) {
      removeCacheItem(entity);
      
      addCacheItem(entity);
    }
    
    return 0;
  }
  
  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int delete(String id) {
    ApplicationMethod entity = provider.selectByPrimaryKey(id);
    
    if (entity != null) {
      // 删除缓存记录
      removeCacheItem(entity);
      
      // 删除数据库记录
      int affectedRows = provider.deleteByPrimaryKey(id);
      
      KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);
    }
    
    return 0;
  }
  
  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------
  
  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link ApplicationMethod} 实例的详细信息
   */
  @Override
  public ApplicationMethod findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }
  
  /**
   * 查询某条记录
   *
   * @param name 名称
   * @return {@link ApplicationMethod} 实例的详细信息
   */
  @Override
  public ApplicationMethod findOneByName(String name) {
    ApplicationMethod entity = null;
    
    String key = CACHE_KEY_NAME_PREFIX + name;
    
    if (CachingManager.contains(key)) {
      try {
        entity = (ApplicationMethod) CachingManager.get(key);
      } catch (Exception ex) {
        com.x3platform.InternalLogger.getLogger().error("FindOneByName Cache Exception", ex);
        entity = null;
      }
    }
    
    // 如果缓存中未找到相关数据，则查找数据库内容。
    if (entity == null) {
      entity = provider.findOneByName(name);
      
      // 如果数据库中没有记录，构造空操作方法，避免每次查询数据库信息。
      if (entity == null) {
        entity = new ApplicationMethod();
        entity.setName(name);
        entity.setOptions(APPLICATION_METHOD_NOOP_OPTIONS);
        entity.setDescription(APPLICATION_METHOD_NOOP_DESCRIPTION);
      }
      
      addCacheItem(entity);
    }
    
    return entity;
  }
  
  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link ApplicationMethod} 实例的详细信息
   */
  @Override
  public List<ApplicationMethod> findAll(DataQuery query) {
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
    return provider.isExist(id);
  }
}
