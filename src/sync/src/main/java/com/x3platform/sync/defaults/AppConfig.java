package com.x3platform.sync.defaults;

import com.x3platform.sync.SyncSerializer;
import com.x3platform.sync.serialization.JsonSerializer;
import com.x3platform.sync.services.SyncPkgService;
import com.x3platform.sync.services.SyncQueueService;
import com.x3platform.sync.services.SyncSettingService;
import com.x3platform.sync.services.impl.SyncPkgServiceImpl;
import com.x3platform.sync.services.impl.SyncQueueServiceImpl;
import com.x3platform.sync.services.impl.SyncSettingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 应用配置类
 *
 * @author ruanyu
 */
@Configuration("com.x3platform.sync.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.sync.SyncSerializer")
  public SyncSerializer syncSerializer() {
    return new JsonSerializer();
  }

  @Bean("com.x3platform.sync.services.SyncSettingService")
  public SyncSettingService syncSettingService() {
    return new SyncSettingServiceImpl();
  }

  @Bean("com.x3platform.sync.services.SyncPkgService")
  public SyncPkgService syncPkgService() {
    return new SyncPkgServiceImpl();
  }

  @Bean("com.x3platform.sync.services.SyncQueueService")
  public SyncQueueService syncQueueService() {
    return new SyncQueueServiceImpl();
  }
}
