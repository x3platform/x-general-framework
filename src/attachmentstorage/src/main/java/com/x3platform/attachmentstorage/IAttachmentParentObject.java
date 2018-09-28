package com.x3platform.attachmentstorage;

/**
 * 附件父级对象
 */
public interface IAttachmentParentObject {
  /**
   * 实体标识
   */
  String getEntityId();

  void setEntityId(String value);

  /**
   * 实体类名称
   */
  String getEntityClassName();

  void setEntityClassName(String value);

  /**
   * 附件的实体类名称
   */
  String getAttachmentEntityClassName();

  void setAttachmentEntityClassName(String value);

  /**
   * 附件的文件夹名称
   */
  String getAttachmentFolder();

  void setAttachmentFolder(String value);

  // void find(String id);
}
