package com.x3platform.files.util;

import com.x3platform.SpringContext;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.configuration.ObjectStorageConfigurationView;
import com.x3platform.util.DateUtil;
import com.x3platform.util.PathUtil;
import com.x3platform.util.StringUtil;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件工具函数
 */
public final class ObjectStorageFileUtil {

  /**
   * 生成新的附件标识
   */
  public static String newIdentity() {
    return newIdentity(ObjectStorageConfigurationView.getInstance().getIdentityFormat().toLowerCase());
  }

  public static String newIdentity(String identityFormat) {
    // 注:StringUtil.ToRandom("0123456789", 6)
    // 随机数生成函数在产生随机数时, 系统内部机制线程会暂停1毫秒钟以等待定时器的推进, 避免在时间极短的情况下生成相同的随机数

    switch (identityFormat) {
      case "yyyymmddhhmmssfff":
        // datetime(17) + randomtext(6) = 唯一标识长度(23)
        return StringUtil.toDateFormat(java.time.LocalDateTime.now(), "yyyyMMddHHmmssSSS")
          + StringUtil.toRandom("0123456789", 6);
      case "timestamp":
        // timestamp(13) + randomtext(6) = 唯一标识长度(19)
        return (DateUtil.getTimestamp() + StringUtil.toRandom("0123456789", 6));
      case "guid":
      default:
        // 唯一标识长度 = 36
        return StringUtil.toUuid();
    }
  }

  /**
   * 创建附件
   *
   * @param entityId 所属实体唯一标识
   * @param entityClassName 所属实体类名称
   * @param fileEntityClassName 附件实体类名称
   * @param fileFolder 附件文件夹
   * @param file 文件对象
   */
  public static ObjectStorageFile createObjectStorageFile(String entityId, String entityClassName,
    String fileEntityClassName, String fileFolder, MultipartFile file) throws IOException {
    return createObjectStorageFile(entityId, entityClassName, fileEntityClassName, fileFolder,
      file.getOriginalFilename(), file.getBytes());
  }

  /**
   * 创建附件
   */
  public static ObjectStorageFile createObjectStorageFile(String entityId, String entityClassName,
    String fileEntityClassName, String fileFolder, String fileName, byte[] fileData) {
    return createObjectStorageFile(entityId, entityClassName, fileEntityClassName, fileFolder, fileName,
      PathUtil.getExtension(fileName), fileData.length, fileData);
  }

  /**
   * 创建附件
   */
  public static ObjectStorageFile createObjectStorageFile(String entityId, String entityClassName,
    String fileEntityClassName, String fileFolder, String fileName, String fileType, int fileSize,
    byte[] fileData) {
    return createObjectStorageFile(entityId, entityClassName, fileEntityClassName, fileFolder, newIdentity(),
      fileName, fileType, fileSize, fileData);
  }

  /**
   * 创建附件
   */
  public static ObjectStorageFile createObjectStorageFile(String entityId, String entityClassName,
    String fileEntityClassName, String fileFolder, String fileId, String fileName,
    String fileType, int fileSize, byte[] fileData) {
    // 创建对象
    ObjectStorageFile param = SpringContext.getBean("com.x3platform.files.ObjectStorageFile", ObjectStorageFile.class);

    param.setId(StringUtil.isNullOrEmpty(fileId) ? newIdentity() : fileId);
    param.setEntityId(entityId);
    param.setEntityClassName(entityClassName);
    param.setFileName(fileName);
    param.setFileType(fileType);
    param.setFileSize(fileSize);
    param.setFileData(fileData);
    param.setCreatedDate(java.time.LocalDateTime.now());

    param.setFileFolder(fileFolder);

    // 虚拟路径需要创建时间和文件类型参数
    param.setVirtualPath(ObjectStoragePathUtil.getVirtualPathFormat(fileFolder, param));

    param.setFolderRule(ObjectStorageConfigurationView.getInstance().getPhysicalUploadFolderRule());

    return param;
  }
}
