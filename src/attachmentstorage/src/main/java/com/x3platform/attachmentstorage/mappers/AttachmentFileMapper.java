package com.x3platform.attachmentstorage.mappers;

import com.x3platform.attachmentstorage.AttachmentFile;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * 附件存储
 */
public interface AttachmentFileMapper {
  // -------------------------------------------------------
  // 保存 添加 修改 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 AttachmentFile 详细信息
   * @return 实例 AttachmentFile 详细信息
   */
  int save(AttachmentFile param);

  /**
   * 删除记录
   *
   * @param id 标识
   */
  void delete(String id);

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id AttachmentFile Id号
   * @return 返回一个 实例 AttachmentFile 的详细信息
   */
  AttachmentFile findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 实例 AttachmentFile 的详细信息
   */
  List<AttachmentFile> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param entityClassName 实体类名称
   * @param entityId 实体类标识
   * @param fileStatus 文件状态
   * @return 返回所有 实例 AttachmentFile 的详细信息
   */
  List<AttachmentFile> findAllByEntityId(
    @Param("entity_class_name") String entityClassName,
    @Param("entity_id") String entityId,
    @Param("file_status") int fileStatus);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 对象唯一标识
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 重命名
   *
   * @param id 附件标识
   * @param name 新的附件名称
   */
  void rename(String id, String name);

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName 实体类名称
   * @param entityId 实体标识
   * @param attachmentFileIds 附件唯一标识
   * @param fileStatus 文件状态
   */
  void setFileStatus(@Param("entityClassName") String entityClassName, @Param("entityId") String entityId,
    @Param("attachmentFileIds") String[] attachmentFileIds, @Param("fileStatus") int fileStatus);
}
