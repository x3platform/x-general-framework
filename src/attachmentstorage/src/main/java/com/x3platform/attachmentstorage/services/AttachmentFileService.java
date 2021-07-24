package com.x3platform.attachmentstorage.services;

import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.data.DataQuery;

import java.util.*;

public interface AttachmentFileService {

  // -------------------------------------------------------
  // 保存 删除
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
  int delete(String id);

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
   * @param query  数据查询参数
   * @return 返回所有 实例 AttachmentFile 的详细信息
   */
  List<AttachmentFile> findAll(DataQuery query);

  /**
   * 查询所有相关记录
   *
   * @param entityClassName 实体类名称
   * @param entityId        实体类标识
   * @return 返回所有 实例 AttachmentFile 的详细信息
   */
  List<AttachmentFile> findAllByEntityId(String entityClassName, String entityId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 查询是否存在相关的记录
   *
   * @param id 实例 AttachmentFile 详细信息
   * @return 布尔值
   */
  boolean isExist(String id);

  /**
   * 重命名
   *
   * @param id   附件标识
   * @param name 新的附件名称
   */
  void rename(String id, String name);

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName   实体类名称
   * @param entityId          实体标识
   * @param attachmentFileIds 附件唯一标识，多个附件以逗号隔开
   */
  void setValid(String entityClassName, String entityId, String attachmentFileIds);

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName   实体类名称
   * @param entityId          实体标识
   * @param attachmentFileIds 附件唯一标识，多个附件以逗号隔开
   * @param append            附加文件
   */
  void setValid(String entityClassName, String entityId, String attachmentFileIds, boolean append);

  /**
   * 物理复制全部附件信息到实体类
   *
   * @param param           实例 AttachmentFile 详细信息
   * @param entityId        实体标识
   * @param entityClassName 实体类名称
   * @return 新的 实例 AttachmentFile 详细信息
   */
  AttachmentFile copy(AttachmentFile param, String entityClassName, String entityId);

  /**
   * 物理移动附件路径
   *
   * @param param 实例 AttachmentFile 详细信息
   * @param path  文件目标路径
   * @return 新的 实例 AttachmentFile 详细信息
   */
  AttachmentFile move(AttachmentFile param, String path);
}
