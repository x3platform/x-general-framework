package com.x3platform.sync.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 同步管理的配置信息
 *
 * @author ruanyu
 */
@Component("com.x3platform.sync.configuration.SyncConfiguration")
public class SyncConfiguration {

  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "sync";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "sync";

  @Value("${x3platform." + SECTION_NAME + ".data-folder:sync}")
  private String dataFolder;

  /**
   * 数据存储目录
   */
  public String getDataFolder() {
    return dataFolder;
  }

  @Value("${x3platform." + SECTION_NAME + ".general-application-id:00000000-0000-0000-0000-000000000001}")
  private String generalApplicationId;

  /**
   * 通用应用标识
   */
  public String getGeneralApplicationId() {
    return generalApplicationId;
  }

  @Value("${x3platform." + SECTION_NAME + ".receive-queue-name:sync-queue}")
  private String receiveQueueName;

  /**
   * 接收消息队列名称
   */
  public String getReceiveQueueName() {
    return receiveQueueName;
  }

  @Value("${x3platform." + SECTION_NAME + ".receive-queue-receiving-interval:60}")
  private String receiveQueueReceivingInterval;

  /**
   * 接收消息队列接收时间间隔(单位:秒)
   */
  public String getReceiveQueueReceivingInterval() {
    return receiveQueueReceivingInterval;
  }
}
