package com.x3platform.tasks.defaults;

import com.x3platform.tasks.models.TaskWaitingItem;
import com.x3platform.tasks.services.*;
import com.x3platform.tasks.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 应用配置
 *
 * @author ruanyu
 */
@Configuration("com.x3platform.tasks.defaults.AppConfig")
public class AppConfig {
  @Bean("com.x3platform.tasks.services.TaskWorkItemService")
  public TaskWorkItemService taskWorkItemService() {
    return new TaskWorkItemServiceImpl();
  }

  @Bean("com.x3platform.tasks.services.TaskWaitingItemService")
  public TaskWaitingItemService taskWaitingItemService() {
    return new TaskWaitingItemServiceImpl();
  }

  @Bean("com.x3platform.tasks.services.TaskHistoryItemService")
  public TaskHistoryItemService taskHistoryItemService() {
    return new TaskHistoryItemServiceImpl();
  }
}
