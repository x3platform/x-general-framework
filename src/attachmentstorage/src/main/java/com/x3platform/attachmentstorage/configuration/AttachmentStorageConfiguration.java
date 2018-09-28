package com.x3platform.attachmentstorage.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 附件存储的配置信息
 */
@Component("com.x3platform.attachmentstorage.configuration.AttachmentStorageConfiguration")
public class AttachmentStorageConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String ApplicationName = "AttachmentStorage";

  /**
   * 配置区的名称
   */
  public static final String SectionName = "attachmentstorage";

  @Value("${x3platform." + SectionName + ".physical-upload-folder:uploads/}")
  private String mPhysicalUploadFolder;

  /**
   * 上传文件夹物理路径
   */
  public String getPhysicalUploadFolder() {
    return mPhysicalUploadFolder;
  }

  @Value("${x3platform." + SectionName + ".physical-upload-folder-rule:folder/year/quarter/month/}")
  private String mPhysicalUploadFolderRule;

  /**
   * 上传文件夹物理路径规则
   */
  public String getPhysicalUploadFolderRule() {
    return mPhysicalUploadFolderRule;
  }

  @Value("${x3platform." + SectionName + ".virtual-upload-folder:/uploads/}")
  private String mVirtualUploadFolder;

  /**
   * 上传文件夹虚拟路径
   */
  public String getVirtualUploadFolder() {
    return mVirtualUploadFolder;
  }

  @Value("${x3platform." + SectionName + ".identity-format:guid}")
  private String mIdentityFormat;

  /**
   * 生成文件名称的格式
   */
  public String getIdentityFormat() {
    return mIdentityFormat;
  }

  @Value("${x3platform." + SectionName + ".allow-max-file-size:guid}")
  private String mAllowMaxFileSize;

  /**
   * 允许上传的最大文件大小 单位(B)
   */
  public String getAllowMaxFileSize() {
    return mAllowMaxFileSize;
  }

  @Value("${x3platform." + SectionName + ".allow-min-file-size:guid}")
  private String mAllowMinFileSize;

  /**
   * 允许上传的最小文件大小 单位(B)
   */
  public String getAllowMinFileSize() {
    return mAllowMinFileSize;
  }

  @Value("${x3platform." + SectionName + ".allow-file-types:jpg,png}")
  private String mAllowFileTypes;

  /**
   * 允许文件的类型
   */
  public String getAllowFileTypes() {
    return mAllowFileTypes;
  }

  @Value("${x3platform." + SectionName + ".distributed-file-storage-mode:off}")
  private String mDistributedFileStorageMode;

  /**
   * 分布式文件存储
   */
  public String getDistributedFileStorageMode() {
    return mDistributedFileStorageMode;
  }


  @Value("${x3platform." + SectionName + ".default-thumbnails:default-thumbnails.png}")
  private String mDefaultThumbnails;

  /**
   * 默认缩略图
   */
  public String getDefaultThumbnails() {
    return mDefaultThumbnails;
  }
}
