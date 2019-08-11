package com.x3platform.attachmentstorage.util;

import com.x3platform.SpringContext;
import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.util.DateUtil;
import com.x3platform.util.PathUtil;
import com.x3platform.util.StringUtil;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件工具函数
 */
public final class UploadFileUtil {

  /**
   * 生成新的附件标识
   */
  public static String newIdentity() {
    return newIdentity(AttachmentStorageConfigurationView.getInstance().getIdentityFormat().toLowerCase());
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
   * @param attachmentEntityClassName 附件实体类名称
   * @param attachmentFolder 附件文件夹
   * @param file 文件对象
   */
  public static AttachmentFile createAttachmentFile(String entityId, String entityClassName,
    String attachmentEntityClassName, String attachmentFolder, MultipartFile file) throws IOException {
    return createAttachmentFile(entityId, entityClassName, attachmentEntityClassName, attachmentFolder,
      file.getOriginalFilename(), file.getBytes());
  }

  /**
   * 创建附件
   */
  public static AttachmentFile createAttachmentFile(String entityId, String entityClassName,
    String attachmentEntityClassName, String attachmentFolder, String fileName, byte[] fileData) {
    return createAttachmentFile(entityId, entityClassName, attachmentEntityClassName, attachmentFolder, fileName,
      PathUtil.getExtension(fileName), fileData.length, fileData);
  }

  /**
   * 创建附件
   */
  public static AttachmentFile createAttachmentFile(String entityId, String entityClassName,
    String attachmentEntityClassName, String attachmentFolder, String attachmentName, String fileType, int fileSize,
    byte[] fileData) {
    return createAttachmentFile(entityId, entityClassName, attachmentEntityClassName, attachmentFolder, newIdentity(),
      attachmentName, fileType, fileSize, fileData);
  }

  /**
   * 创建附件
   */
  public static AttachmentFile createAttachmentFile(String entityId, String entityClassName,
    String attachmentEntityClassName, String attachmentFolder, String attachmentId, String attachmentName,
    String fileType, int fileSize, byte[] fileData) {
    // 创建对象
    AttachmentFile param = SpringContext
      .getBean("com.x3platform.attachmentstorage.AttachmentFile", AttachmentFile.class);

    param.setId(StringUtil.isNullOrEmpty(attachmentId) ? newIdentity() : attachmentId);
    param.setEntityId(entityId);
    param.setEntityClassName(entityClassName);
    param.setAttachmentName(attachmentName);
    param.setFileType(fileType);
    param.setFileSize(fileSize);
    param.setFileData(fileData);
    param.setCreatedDate(java.time.LocalDateTime.now());

    param.setAttachmentFolder(attachmentFolder);

    // 虚拟路径需要创建时间和文件类型参数
    param.setVirtualPath(UploadPathUtil.getVirtualPathFormat(attachmentFolder, param));

    param.setFolderRule(AttachmentStorageConfigurationView.getInstance().getPhysicalUploadFolderRule());

    return param;
  }
}
