package com.x3platform.files;

import java.io.IOException;

/**
 * 对象存储文件接口
 */
public interface ObjectStorageFile {
  /**
   * 文件标识
   */
  String getId();

  void setId(String value);
  
  /**
   * 所属桶名称
   */
  String getBucketName();
  
  void setBucketName(String value);

  /**
   * 所属实体类名称
   */
  String getEntityClassName();

  void setEntityClassName(String value);

  /**
   * 所属实体标识
   */
  String getEntityId();

  void setEntityId(String value);
  
  /**
   * 父级对象实体类名称
   */
  String getFileEntityClassName();
  
  void setFileEntityClassName(String value);

  /**
   * 附件目录
   */
  String getFileFolder();

  void setFileFolder(String value);

  /**
   * 附件名称
   */
  String getFileName();

  void setFileName(String value);

  /**
   * 虚拟路径
   */
  String getVirtualPath();

  void setVirtualPath(String value);

  /**
   * 目录规则
   */
  String getFolderRule();

  void setFolderRule(String value);

  /**
   * 文件类型
   */
  String getFileType();

  void setFileType(String value);

  /**
   * 文件大小
   */
  int getFileSize();

  void setFileSize(int value);

  /**
   * 文件状态
   */
  int getFileStatus();

  void setFileStatus(int value);
  
  /**
   * 排序标识
   */
  String getOrderId();
  
  void setOrderId(String value);

  /**
   * 数据
   */
  byte[] getFileData();

  void setFileData(byte[] value);

  /**
   * 创建者唯一标识
   */
  String getCreatedBy();

  void setCreatedBy(String value);

  /**
   * 创建时间
   */
  java.time.LocalDateTime getCreatedDate();

  void setCreatedDate(java.time.LocalDateTime value);
  
  /**
   * 还原
   */
  void restore(String id);

  /**
   * 保存
   */
  void save() throws IOException;
}
