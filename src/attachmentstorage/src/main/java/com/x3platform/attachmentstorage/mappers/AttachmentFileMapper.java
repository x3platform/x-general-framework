package com.x3platform.attachmentstorage.mappers;

import com.x3platform.attachmentstorage.IAttachmentFileInfo;

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
   * @param param 实例 IAttachmentFileInfo 详细信息
   * @return 实例 IAttachmentFileInfo 详细信息
   */
  int save(IAttachmentFileInfo param);

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
   * @param id IAttachmentFileInfo Id号
   * @return 返回一个 实例 IAttachmentFileInfo 的详细信息
   */
  IAttachmentFileInfo findOne(String id);

  /**
   * 查询所有相关记录
   *
   * @param params 查询参数集合
   * @return 返回所有 实例 IAttachmentFileInfo 的详细信息
   */
  List<IAttachmentFileInfo> findAll(Map params);

  /**
   * 查询所有相关记录
   *
   * @param entityClassName 实体类名称
   * @param entityId        实体类标识
   * @return 返回所有 实例 IAttachmentFileInfo 的详细信息
   */
  List<IAttachmentFileInfo> findAllByEntityId(String entityClassName, String entityId);

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  ///#region 属性:GetPaging(int startIndex, int pageSize, DataQuery query, out int rowCount)

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例
   */
  // List<IAttachmentFileInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount);

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
   * @param append            附加文件
   */
  void setValid(String entityClassName, String entityId, String attachmentFileIds, boolean append);
}
