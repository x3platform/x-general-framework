package com.x3platform.attachmentstorage.defaults;

import com.x3platform.attachmentstorage.AttachmentFileInfo;
import com.x3platform.attachmentstorage.IAttachmentFileInfo;
import com.x3platform.attachmentstorage.services.*;
import com.x3platform.attachmentstorage.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.attachmentstorage.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.attachmentstorage.AttachmentFile")
  public IAttachmentFileInfo iAttachmentFileInfo() {
    return new AttachmentFileInfo();
  }

  @Bean("com.x3platform.attachmentstorage.services.IAttachmentFileService")
  public IAttachmentFileService iAttachmentFileService() {
    return new AttachmentFileServiceImpl();
  }
}
