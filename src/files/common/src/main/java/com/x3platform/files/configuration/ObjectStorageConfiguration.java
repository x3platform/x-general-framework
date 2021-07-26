package com.x3platform.files.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 对象存储的配置信息
 */
@Component("com.x3platform.files.configuration.ObjectStorageConfiguration")
public class ObjectStorageConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "files";

  @Value("${x3platform." + APPLICATION_NAME + ".physical-upload-folder:uploads/}")
  private String physicalUploadFolder;

  /**
   * 上传文件夹物理路径
   */
  public String getPhysicalUploadFolder() {
    return physicalUploadFolder;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".physical-upload-folder-rule:folder/year/quarter/month/}")
  private String physicalUploadFolderRule;

  /**
   * 上传文件夹物理路径规则
   */
  public String getPhysicalUploadFolderRule() {
    return physicalUploadFolderRule;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".virtual-upload-folder:/uploads/}")
  private String virtualUploadFolder;

  /**
   * 上传文件夹相对路径
   */
  public String getVirtualUploadFolder() {
    return virtualUploadFolder;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".identity-format:guid}")
  private String identityFormat;

  /**
   * 生成文件名称的格式
   */
  public String getIdentityFormat() {
    return identityFormat;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".path-separator:/}")
  private String pathSeparator;

  /**
   * 路径分隔符
   */
  public String getPathSeparator() {
    return pathSeparator;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".allow-max-file-size:10485760}")
  private String allowMaxFileSize;

  /**
   * 允许上传的最大文件大小 单位(B)
   */
  public String getAllowMaxFileSize() {
    return allowMaxFileSize;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".allow-min-file-size:0}")
  private String allowMinFileSize;

  /**
   * 允许上传的最小文件大小 单位(B)
   */
  public String getAllowMinFileSize() {
    return allowMinFileSize;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".allow-file-types:jpg,png}")
  private String allowFileTypes;

  /**
   * 允许上传的文件类型
   */
  public String getAllowFileTypes() {
    return allowFileTypes;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".default-file-height:200}")
  private String defaultFileHeight;

  /**
   * 默认文件高度
   */
  public String getDefaultFileHeight() {
    return defaultFileHeight;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".default-file-width:200}")
  private String defaultFileWidth;

  /**
   * 默认文件宽度
   */
  public String getDefaultFileWidth() {
    return defaultFileWidth;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".default-file-author:anonymous}")
  private String defaultFileAuthor;

  /**
   * 默认文件作者
   */
  public String getDefaultFileAuthor() {
    return defaultFileAuthor;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".default-thumbnails:default-thumbnails.png}")
  private String defaultThumbnails;

  /**
   * 默认缩略图
   */
  public String getDefaultThumbnails() {
    return defaultThumbnails;
  }
}
