package com.x3platform.attachmentstorage.defaults;

import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.GeneralAttachmentFile;
import com.x3platform.attachmentstorage.services.AttachmentFileService;
import com.x3platform.attachmentstorage.services.impl.AttachmentFileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.attachmentstorage.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.attachmentstorage.AttachmentFile")
  public AttachmentFile iGeneralAttachmentFile() {
    return new GeneralAttachmentFile();
  }

  @Bean("com.x3platform.attachmentstorage.services.AttachmentFileService")
  public AttachmentFileService iAttachmentFileService() {
    return new AttachmentFileServiceImpl();
  }
}
