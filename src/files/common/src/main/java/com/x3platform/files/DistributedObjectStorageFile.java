package com.x3platform.files;

import com.x3platform.files.configuration.ObjectStorageConfigurationView;
import com.x3platform.files.util.ObjectStoragePathUtil;
import com.x3platform.util.ByteUtil;
import com.x3platform.util.DirectoryUtil;
import com.x3platform.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

/**
 * 代理对象存储文件信息
 */
public class DistributedObjectStorageFile implements ObjectStorageFile {

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
  
  private String bucketName;
  
  /**
   * 所属桶名称
   */
  @Override
  public String getBucketName() {
    return bucketName;
  }
  
  @Override
  public void setBucketName(String value) {
    bucketName = value;
  }
  
  private String entityClassName;
  
  /**
   * 所属实体类名称
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
   * 所属实体标识
   */
  @Override
  public String getEntityId() {
    return entityId;
  }

  @Override
  public void setEntityId(String value) {
    entityId = value;
  }
  
  private String fileEntityClassName;
  
  /**
   * 实体类名称
   */
  @Override
  public String getFileEntityClassName() {
    return fileEntityClassName;
  }
  
  @Override
  public void setFileEntityClassName(String value) {
    fileEntityClassName = value;
  }

  private String fileFolder;

  /**
   * 实体标识
   */
  @Override
  public String getFileFolder() {
    return fileFolder;
  }

  @Override
  public void setFileFolder(String value) {
    fileFolder = value;
  }

  private String fileName;

  /**
   * 附件名称
   */
  @Override
  public String getFileName() {
    return fileName;
  }

  @Override
  public void setFileName(String value) {
    fileName = value;
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
      .replace("{uploads}", ObjectStorageConfigurationView.getInstance().getVirtualUploadFolder());
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
        .replace("{uploads}", ObjectStorageConfigurationView.getInstance().getPhysicalUploadFolder());

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
  @Override
  public String getOrderId() {
    return orderId;
  }
  
  @Override
  public void setOrderId(String value) {
    orderId = value;
  }

  private String createdBy;
  
  /**
   * 创建人信息
   */
  @Override
  public String getCreatedBy() {
    return createdBy;
  }

  @Override
  public void setCreatedBy(String value) {
    createdBy = value;
  }

  private LocalDateTime createdDate = LocalDateTime.MIN;

  /**
   * 创建日期
   */
  @Override
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(LocalDateTime value) {
    createdDate = value;
  }

  /**
   * 还原附件信息
   */
  @Override
  public void restore(String id) {
    ObjectStorageFile temp = ObjectStorageContext.getInstance().getObjectStorageService().findOne(id);

    setId(temp.getId());
    setEntityId(temp.getEntityId());
    setEntityClassName(temp.getEntityClassName());

    setFileFolder(
      temp.getVirtualPath().substring(0, temp.getVirtualPath().indexOf("/")).replace("{uploads}", ""));

    setFileName(temp.getFileName());
    setVirtualPath(temp.getVirtualPath());
    setFolderRule(temp.getFolderRule());
    setFileType(temp.getFileType());
    setFileSize(temp.getFileSize());
    setFileData(null);
    setFileStatus(temp.getFileStatus());
    setCreatedBy(temp.getCreatedBy());
    
    setCreatedBy(temp.getCreatedBy());
    setCreatedDate(temp.getCreatedDate());
  }

  /**
   * 保存附件信息
   */
  @Override
  public void save() throws IOException {
    // 本地存储方式

    // 保存 对象信息
    ObjectStorageContext.getInstance().getObjectStorageService().save(this);
  }
}
