package com.x3platform.files.configuration;

import com.x3platform.SpringContext;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.util.BooleanUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 附件存储的配置信息视图
 */
@Configuration
public class ObjectStorageConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile ObjectStorageConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static ObjectStorageConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = SpringContext.getBean(ObjectStorageConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.files.configuration.ObjectStorageConfiguration")
  ObjectStorageConfiguration configuration;

  public String getPhysicalUploadFolder() {
    return configuration.getPhysicalUploadFolder();
  }

  public String getPhysicalUploadFolderRule() {
    return configuration.getPhysicalUploadFolderRule();
  }

  public String getVirtualUploadFolder() {
    return configuration.getVirtualUploadFolder();
  }

  public String getIdentityFormat() {
    return configuration.getIdentityFormat();
  }

  public String getPathSeparator() {
    return configuration.getPathSeparator();
  }

  public int getAllowMaxFileSize() {
    return Integer.valueOf(configuration.getAllowMaxFileSize());
  }

  public int getAllowMinFileSize() {
    return Integer.valueOf(configuration.getAllowMinFileSize());
  }

  public String getAllowFileTypes() {
    return configuration.getAllowFileTypes();
  }

  public String getDefaultFileHeight() {
    return configuration.getDefaultFileHeight();
  }

  public String getDefaultFileWidth() {
    return configuration.getDefaultFileWidth();
  }

  public String getDefaultFileAuthor() {
    return configuration.getDefaultFileAuthor();
  }

  public String getDefaultThumbnails() {
    return configuration.getDefaultThumbnails();
  }
}
