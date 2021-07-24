package com.x3platform.apps.services.impl;

import com.x3platform.apps.Constants;
import com.x3platform.apps.models.*;
import com.x3platform.apps.services.*;
import com.x3platform.apps.mappers.*;

import com.x3platform.KernelContext;
import com.x3platform.data.*;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.x3platform.Constants.TEXT_EMPTY;

/**
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
    return this.provider.selectByPrimaryKey(id);
  }
  
  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link ApplicationEvent} 实例的详细信息
   */
  @Override
  public List<ApplicationEvent> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }
  
  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------
  
  /**
   * 写入应用事件信息
   *
   * @param applicationId 所属应用标识
   * @param level         事件级别
   * @param description   描述
   * @param startTime     开始时间
   * @param finishTime    结束时间
   * @return 消息代码 0=表示成功
   */
  @Override
  public int write(String applicationId, String level, String description, LocalDateTime startTime, LocalDateTime finishTime) {
    return this.write(TEXT_EMPTY, TEXT_EMPTY, applicationId, TEXT_EMPTY,
      level, description, startTime, finishTime, TEXT_EMPTY);
  }
  
  /**
   * 写入应用事件信息
   *
   * @param accountId               帐号标识
   * @param accountName             帐号名称
   * @param applicationId           所属应用标识
   * @param applicationFeatureName 所属应用功能索引
   * @param level                   事件级别
   * @param description             描述
   * @param startTime               开始时间
   * @param finishTime              结束时间
   * @return 消息代码 0=表示成功
   */
  @Override
  public int write(String accountId, String accountName, String applicationId, String applicationFeatureName,
                   String level, String description, LocalDateTime startTime, LocalDateTime finishTime, String ip) {
    ApplicationEvent entity = new ApplicationEvent();
    entity.setId(DigitalNumberContext.generate("Key_SnowFlake"));
    entity.setAccountId(accountId);
    entity.setAccountName(accountName);
    entity.setApplicationId(applicationId);
    entity.setApplicationFeatureName(applicationFeatureName);
    entity.setLevel(level);
    entity.setDescription(description);
    entity.setStartTime(startTime);
    entity.setFinishTime(finishTime);
    entity.setIP(ip);
    
    if (startTime != null && finishTime != null) {
      Duration duration = Duration.between(startTime, finishTime);
      BigDecimal timespan = BigDecimal.valueOf(duration.toMillis()).divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
      
      entity.setTimespan(timespan);
    }
    
    save(entity);
    
    return 0;
  }
}
