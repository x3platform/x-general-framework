package com.x3platform.attachmentstorage.configuration;

import com.x3platform.SpringContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 附件存储的配置信息视图
 */
@Configuration
public class AttachmentStorageConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile AttachmentStorageConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static AttachmentStorageConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = SpringContext.getBean(AttachmentStorageConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.attachmentstorage.configuration.AttachmentStorageConfiguration")
  AttachmentStorageConfiguration configuration;

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

  public String getAllowMaxFileSize() {
    return configuration.getAllowMaxFileSize();
  }

  public String getAllowMinFileSize() {
    return configuration.getAllowMinFileSize();
  }

  public String getAllowFileTypes() {
    return configuration.getAllowFileTypes();
  }

  public String getDistributedFileStorageMode() {
    return configuration.getDistributedFileStorageMode();
  }

  public String getDefaultThumbnails() {
    return configuration.getDefaultThumbnails();
  }
}
