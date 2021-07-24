package com.x3platform.attachmentstorage.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.attachmentstorage.GeneralAttachmentFile;
import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfiguration;
import com.x3platform.attachmentstorage.mappers.AttachmentFileMapper;
import com.x3platform.attachmentstorage.services.AttachmentFileService;
import com.x3platform.attachmentstorage.util.UploadFileUtil;
import com.x3platform.attachmentstorage.util.UploadPathUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public final class AttachmentFileServiceImpl implements AttachmentFileService {

  /**
   * 数据提供器
   */
  @Autowired
  private AttachmentFileMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param param 实例 AttachmentFile 详细信息
   * @return 实例 AttachmentFile 详细信息
   */
  @Override
  public int save(AttachmentFile param) {
    if (StringUtil.isNullOrEmpty(param.getAttachmentName())) {
      throw new IllegalArgumentException("附件名称必填。");
    }

    param.setFileType(param.getFileType().toLowerCase());

    provider.save(param);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 标识
   */
  @Override
  public int delete(String id) {
    Account account = KernelContext.getCurrent().getUser();

    if (account == null) {
      // account == null 一般是在测试环境中使用.
      this.provider.delete(id);
    } else {
      if (AppsSecurity.isAdministrator(account, AttachmentStorageConfiguration.APPLICATION_NAME)) {
        this.provider.delete(id);
      } else {
        AttachmentFile file = this.findOne(id);

        if (file.getCreatedBy().equals(account.getId())) {
          this.provider.delete(id);
        }
      }
    }

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id AttachmentFile 唯一标识
   * @return 返回一个 实例 AttachmentFile 的详细信息
   */
  @Override
  public AttachmentFile findOne(String id) {
    GeneralAttachmentFile param = (GeneralAttachmentFile) provider.findOne(id);

    param.setAttachmentFolder(UploadPathUtil.getAttachmentFolder(param.getVirtualPath(), param.getFolderRule()));

    return param;
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 AttachmentFile 的详细信息
   */
  @Override
  public List<AttachmentFile> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param entityClassName 实体类名称
   * @param entityId 实体类标识
   * @return 返回所有实例 AttachmentFile 的详细信息
   */
  @Override
  public List<AttachmentFile> findAllByEntityId(String entityClassName, String entityId) {
    return provider.findAllByEntityId(entityClassName, entityId, -1);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

  /**
   * 分页函数
   *
   * @param startIndex 开始行索引数,由0开始统计
   * @param pageSize   页面大小
   * @param query      数据查询参数
   * @param rowCount   行数
   * @return 返回一个列表实例
   */
  // public List<AttachmentFile> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
  //   return provider.GetPaging(startIndex, pageSize, query, rowCount);
  // }

  /**
   * 查询是否存在相关的记录
   *
   * @param id 会员标识
   * @return 布尔值
   */
  @Override
  public boolean isExist(String id) {
    return provider.isExist(id);
  }

  /**
   * 重命名
   *
   * @param id 附件标识
   * @param name 新的附件名称
   */
  @Override
  public void rename(String id, String name) {
    provider.rename(id, name);
  }

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName 实体类名称
   * @param entityId 实体标识
   * @param attachmentFileIds 附件唯一标识，多个附件以逗号隔开
   */
  @Override
  public void setValid(String entityClassName, String entityId, String attachmentFileIds) {
    setValid(entityClassName, entityId, attachmentFileIds, false);
  }

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName 实体类名称
   * @param entityId 实体标识
   * @param attachmentFileIds 附件唯一标识，多个附件以逗号隔开
   * @param append 附加文件
   */
  @Override
  public void setValid(String entityClassName, String entityId, String attachmentFileIds, boolean append) {
    if (append) {
      // 所有文件设置为失效
      this.provider.setFileStatus(entityClassName, entityId, null, 0);
    }

    // 指定文件设置为有效
    this.provider.setFileStatus(entityClassName, entityId, attachmentFileIds.split(","), 1);
  }

  /**
   * 物理复制全部附件信息到实体类
   *
   * @param param AttachmentFile 实例详细信息
   * @param entityId 实体标识
   * @param entityClassName 实体类名称
   * @return 新的 AttachmentFile 实例详细信息
   */
  @Override
  public AttachmentFile copy(AttachmentFile param, String entityClassName, String entityId) {

    AttachmentFile attachment = UploadFileUtil.createAttachmentFile(
      entityId,
      entityClassName,
      KernelContext.parseObjectType(GeneralAttachmentFile.class),
      UploadPathUtil.getAttachmentFolder(param.getVirtualPath(), param.getFolderRule()),
      param.getAttachmentName(),
      param.getFileType(),
      param.getFileSize(),
      param.getFileData());

    try {
      attachment.save();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return attachment;
  }

  /**
   * 物理移动附件路径
   *
   * @param param 实例 AttachmentFile 详细信息
   * @param path 文件目标路径
   * @return 新的 实例 AttachmentFile 详细信息
   */
  @Override
  public AttachmentFile move(AttachmentFile param, String path) {
    throw new UnsupportedOperationException();
  }
}
