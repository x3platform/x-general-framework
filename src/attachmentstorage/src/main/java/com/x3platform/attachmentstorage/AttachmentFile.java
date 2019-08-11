package com.x3platform.attachmentstorage;

import java.io.IOException;

/**
 * 附件信息接口
 */
public interface AttachmentFile {
  /**
   * 文件标识
   */
  String getId();

  void setId(String value);

  /**
   * 父级对象实体类名称
   */
  String getEntityClassName();

  void setEntityClassName(String value);

  /**
   * 父级对象实体标识
   */
  String getEntityId();

  void setEntityId(String value);

  /**
   * 附件名称
   */
  String getAttachmentFolder();

  void setAttachmentFolder(String value);

  /**
   * 附件名称
   */
  String getAttachmentName();

  void setAttachmentName(String value);

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
