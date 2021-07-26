package com.x3platform.files.defaults;

import com.x3platform.files.DistributedObjectStorageFile;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.services.ObjectStorageService;
import com.x3platform.files.services.impl.ObjectStorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.files.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.files.ObjectStorageFile")
  public ObjectStorageFile objectStorageFile() {
    return new DistributedObjectStorageFile();
  }

  @Bean("com.x3platform.files.services.ObjectStorageService")
  public ObjectStorageService objectStorageService() {
    return new ObjectStorageServiceImpl();
  }
}
