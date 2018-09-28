package com.x3platform.attachmentstorage;

/**
 * 附件父级对象
 */
public class AttachmentParentObject implements IAttachmentParentObject {
  public AttachmentParentObject() {
  }

  public AttachmentParentObject(String entityId, String entityClassName, String attachmentEntityClassName, String attachmentFolder) {
    this.setEntityId(entityId);
    this.setEntityClassName(entityClassName);
    this.setAttachmentEntityClassName(attachmentEntityClassName);
    this.setAttachmentFolder(attachmentFolder);
  }

  private String mEntityId;

  /**
   * 实体标识
   */
  public final String getEntityId() {
    return mEntityId;
  }

  public final void setEntityId(String value) {
    mEntityId = value;
  }

  private String mEntityClassName;

  /**
   * 实体类名称
   */
  public final String getEntityClassName() {
    return mEntityClassName;
  }

  public final void setEntityClassName(String value) {
    mEntityClassName = value;
  }

  private String mAttachmentEntityClassName;

  /**
   * 附件实体类名称
   */
  public final String getAttachmentEntityClassName() {
    return this.mAttachmentEntityClassName;
  }

  public final void setAttachmentEntityClassName(String value) {
    if (value == null) {
      this.mAttachmentEntityClassName = "";
    } else {
      if (value.indexOf(",") == -1) {
        this.mAttachmentEntityClassName = value;
      } else {
        this.mAttachmentEntityClassName = value.substring(0, value.indexOf(","));
      }
    }
  }

  private String mAttachmentFolder;

  /**
   * 附件的文件夹名称
   */
  public final String getAttachmentFolder() {
    return mAttachmentFolder;
  }

  public final void setAttachmentFolder(String value) {
    mAttachmentFolder = value;
  }

  /**
   * 查找
   *
   * @param id
   */
  public void find(String id) {

  }
}
