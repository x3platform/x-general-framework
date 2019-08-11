package com.x3platform.attachmentstorage.defaults;

import com.x3platform.attachmentstorage.GeneralAttachmentFile;
import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.services.*;
import com.x3platform.attachmentstorage.services.impl.*;
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

  @Bean("com.x3platform.attachmentstorage.services.FastDFSFileTransferService")
  public FastDFSFileTransferService fastDFSFileTransferService() {
    return new FastDFSFileTransferServiceImpl();
  }
}
