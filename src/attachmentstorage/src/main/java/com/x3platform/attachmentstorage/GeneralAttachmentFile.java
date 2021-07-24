package com.x3platform.attachmentstorage;

import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.util.UploadPathUtil;
import com.x3platform.util.ByteUtil;
import com.x3platform.util.DirectoryUtil;
import com.x3platform.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 一般的附件文件信息
 */
public class GeneralAttachmentFile implements AttachmentFile {

  public GeneralAttachmentFile() {
  }

  private String id;

  /**
   * 标识
   */
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String value) {
    id = value;
  }

  private String entityClassName;

  /**
   * 实体类名称
   */
  @Override
  public String getEntityClassName() {
    return entityClassName;
  }

  @Override
  public void setEntityClassName(String value) {
    entityClassName = value;
  }

  private String entityId;

  /**
   * 实体标识
   */
  @Override
  public String getEntityId() {
    return entityId;
  }

  @Override
  public void setEntityId(String value) {
    entityId = value;
  }

  private String attachmentFolder;

  /**
   * 实体标识
   */
  @Override
  public String getAttachmentFolder() {
    return attachmentFolder;
  }

  @Override
  public void setAttachmentFolder(String value) {
    attachmentFolder = value;
  }

  private String attachmentName;

  /**
   * 附件名称
   */
  @Override
  public String getAttachmentName() {
    return attachmentName;
  }

  @Override
  public void setAttachmentName(String value) {
    attachmentName = value;
  }

  private String virtualPath;

  /**
   * 虚拟路径
   */
  @Override
  public String getVirtualPath() {
    return virtualPath;
  }

  @Override
  public void setVirtualPath(String value) {
    virtualPath = value;
  }

  /**
   * 虚拟路径
   */
  public String getVirtualPathView() {
    return getVirtualPath()
      .replace("{uploads}", AttachmentStorageConfigurationView.getInstance().getVirtualUploadFolder());
  }

  private String folderRule = "";

  /**
   * 文件夹规则
   */
  @Override
  public String getFolderRule() {
    return folderRule;
  }

  @Override
  public void setFolderRule(String value) {
    folderRule = value;
  }

  private String fileType;

  /**
   * 文件类型
   */
  @Override
  public String getFileType() {
    return fileType;
  }

  @Override
  public void setFileType(String value) {
    fileType = value;
  }

  private int fileSize;

  /**
   * 文件大小
   */
  @Override
  public int getFileSize() {
    return fileSize;
  }

  @Override
  public void setFileSize(int value) {
    fileSize = value;
  }

  private byte[] fileData = null;

  private boolean fileDataLoaded = false;

  /**
   * 数据
   */
  @Override
  public byte[] getFileData() {
    if (!fileDataLoaded && fileData == null && !StringUtil.isNullOrEmpty(getVirtualPath())) {
      fileDataLoaded = true;

      // -------------------------------------------------------
      // 读取 二进制数据
      // -------------------------------------------------------

      String path = getVirtualPath()
        .replace("{uploads}", AttachmentStorageConfigurationView.getInstance().getPhysicalUploadFolder());

      // if (path.indexOf("~/") == 0) {
      //  path = VirtualPathUtil.getPhysicalPath(path);
      // }

      path = DirectoryUtil.formatLocalPath(path);

      File file = new File(path);

      if (file.isFile()) {
        try {
          fileData = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return fileData;
  }

  @Override
  public void setFileData(byte[] value) {
    fileData = value;
  }

  private int fileStatus;

  /**
   * 文件状态:1 默认 2 回收(虚拟删除) 4 普通(确认上传数据)  8 归档 256 原始文件不存在 512 父级对象信息不存在
   */
  @Override
  public int getFileStatus() {
    return fileStatus;
  }

  @Override
  public void setFileStatus(int value) {
    fileStatus = value;
  }

  private String orderId = "";

  /**
   * 排序
   */
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String value) {
    orderId = value;
  }

  /**
   * 创建人信息
   */
  private String createdBy;

  @Override
  public String getCreatedBy() {
    return createdBy;
  }

  @Override
  public void setCreatedBy(String value) {
    createdBy = value;
  }

  private java.time.LocalDateTime createdDate = java.time.LocalDateTime.MIN;

  /**
   * 创建日期
   */
  @Override
  public java.time.LocalDateTime getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(java.time.LocalDateTime value) {
    createdDate = value;
  }

  /**
   * 还原附件信息
   */
  @Override
  public void restore(String id) {
    AttachmentFile temp = AttachmentStorageContext.getInstance().getAttachmentFileService().findOne(id);

    setId(temp.getId());
    setEntityId(temp.getEntityId());
    setEntityClassName(temp.getEntityClassName());

    setAttachmentFolder(
      temp.getVirtualPath().substring(0, temp.getVirtualPath().indexOf("/")).replace("{uploads}", ""));

    setAttachmentName(temp.getAttachmentName());
    setVirtualPath(temp.getVirtualPath());
    setFolderRule(temp.getFolderRule());
    setFileType(temp.getFileType());
    setFileSize(temp.getFileSize());
    setFileData(null);
    setFileStatus(temp.getFileStatus());

    setCreatedBy(temp.getCreatedBy());
    setCreatedDate(temp.getCreatedDate());
  }

  /**
   * 保存附件信息
   */
  @Override
  public void save() throws IOException {
    // 本地存储方式
    String path = UploadPathUtil.combinePhysicalPath(
      getAttachmentFolder(), String.format("%1$s%2$s", getId(), getFileType()));

    UploadPathUtil.tryCreateDirectory(path);

    ByteUtil.toFile(getFileData(), path);

    // 保存 对象信息
    AttachmentStorageContext.getInstance().getAttachmentFileService().save(this);
  }
}
