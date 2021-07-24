package com.x3platform.attachmentstorage.util;

import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.configuration.*;
import com.x3platform.util.*;

import java.io.File;

/**
 * 上传路径管理
 */
public final class UploadPathUtil {
  /**
   * 创建上传文件夹物理路径
   * @return 物理路径
   */
  public static String createPhysicalUploadFolder() {
    File physicalFolderPath = new File(AttachmentStorageConfigurationView.getInstance().getPhysicalUploadFolder());
    if(!physicalFolderPath.exists()){
      physicalFolderPath.mkdirs();
    }
    return DirectoryUtil.formatLocalPath(physicalFolderPath.getAbsolutePath() + PathUtil.getFileSeparator());
  }

  /**
   * 获取上传文件夹物理路径
   * @return 物理路径
   */
  public static String getPhysicalUploadFolder() {
    return DirectoryUtil.formatLocalPath(new File(AttachmentStorageConfigurationView.getInstance().getPhysicalUploadFolder()).getAbsolutePath() + PathUtil.getFileSeparator());
  }

  /**
   * 获取上传文件虚拟路径
   *
   * @return 虚拟路径
   */
  public static String getVirtualUploadFolder() {
    return AttachmentStorageConfigurationView.getInstance().getVirtualUploadFolder();
  }

  /**
   * 组合物理路径
   *
   * @param attachmentFolder 附件目录名称
   * @param fileName         文件名称
   * @return
   */
  public static String combinePhysicalPath(String attachmentFolder, String fileName) {
    String path = getPhysicalUploadFolder() + parseRule(attachmentFolder, java.time.LocalDateTime.now()).replace('/', java.io.File.separatorChar) + fileName;

    return path = DirectoryUtil.formatLocalPath(path);
  }

  public static String combineVirtualPath(String attachmentFolder, String fileName) {
    return getVirtualUploadFolder() + parseRule(attachmentFolder, java.time.LocalDateTime.now()).replace(java.io.File.separatorChar, '/') + fileName;
  }

  public static String getVirtualPathFormat(String attachmentFolder, AttachmentFile attachment) {
    return "{uploads}"
      + parseRule(attachmentFolder, attachment.getCreatedDate()).replace(java.io.File.separatorChar, '/') + attachment.getId() + attachment.getFileType();
  }

  public static String getAttachmentFolder(String virtualPath, String folderRule) {
    String[] keys = folderRule.split("\\|/");

    String[] results = virtualPath.replace("{uploads}", "").split("[\\\\/]", -1);

    if (keys.length == results.length - 1) {
      for (int i = 0; i < keys.length; i++) {
        if (keys[i].equals("folder")) {
          return results[i];
        }
      }
    }

    return "";
  }

  public static void tryCreateDirectory(String path) {
    path = (new java.io.File(path)).getParent();

    if (!(new java.io.File(path)).isDirectory()) {
      (new java.io.File(path)).mkdirs();
    }
  }

  public static String parseRule(String folder, java.time.LocalDateTime datetime) {
    // 路径规则示例
    // folder\year\quarter\month\
    // year\quarter\month\folder\

    String text = AttachmentStorageConfigurationView.getInstance().getPhysicalUploadFolderRule();

    return text.replace("folder", folder.toLowerCase()).replace("year", String.valueOf(datetime.getYear())).replace("quarter", ((((datetime.getMonthValue() - 1) / 3) + 1) + "Q")).replace("month", String.valueOf(datetime.getMonthValue())).replace("day", String.valueOf(datetime.getDayOfMonth())).replace("hour", String.valueOf(datetime.getHour()));
  }
}
