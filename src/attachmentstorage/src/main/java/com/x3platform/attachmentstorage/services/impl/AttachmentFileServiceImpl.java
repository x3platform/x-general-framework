package com.x3platform.attachmentstorage.services.impl;

import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.attachmentstorage.AttachmentFileInfo;
import com.x3platform.attachmentstorage.AttachmentParentObject;
import com.x3platform.attachmentstorage.IAttachmentFileInfo;
import com.x3platform.attachmentstorage.IAttachmentParentObject;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfiguration;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.mappers.AttachmentFileMapper;
import com.x3platform.attachmentstorage.services.IAttachmentFileService;
import com.x3platform.attachmentstorage.util.UploadFileUtil;
import com.x3platform.attachmentstorage.util.UploadPathUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.IAccountInfo;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

public final class AttachmentFileServiceImpl implements IAttachmentFileService {
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
   * @param param 实例 IAttachmentFileInfo 详细信息
   * @return 实例 IAttachmentFileInfo 详细信息
   */
  @Override
  public int save(IAttachmentFileInfo param) {
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
    // FIXME 待处理
    // IAccountInfo account = KernelContext.getCurrent().getUser();
    IAccountInfo account = null;

    if (AppsSecurity.isAdministrator(account, AttachmentStorageConfiguration.ApplicationName)) {
      this.provider.delete(id);
    } else {
      IAttachmentFileInfo file = this.findOne(id);

      if (file.getCreatedBy().equals(account.getId())) {
        this.provider.delete(id);
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
   * @param id AccountInfo Id号
   * @return 返回一个 实例 IAttachmentFileInfo 的详细信息
   */
  @Override
  public IAttachmentFileInfo findOne(String id) {
    AttachmentFileInfo param = (AttachmentFileInfo) provider.findOne(id);

    // IAttachmentParentObject parent = new AttachmentParentObject();

    // parent.setEntityId(param.getEntityId());
    // parent.setEntityClassName(param.getEntityClassName());
    // parent.setAttachmentEntityClassName(KernelContext.parseObjectType(AttachmentFileInfo.class));
    // parent.setAttachmentFolder();
    // param.setAttachmentFolder();
    param.setAttachmentFolder(UploadPathUtil.getAttachmentFolder(param.getVirtualPath(), param.getFolderRule()));
    // param.setParent(parent);

    return param;
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 IAttachmentFileInfo 的详细信息
   */
  @Override
  public List<IAttachmentFileInfo> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param entityClassName 实体类名称
   * @param entityId        实体类标识
   * @return 返回所有实例 IAttachmentFileInfo 的详细信息
   */
  @Override
  public List<IAttachmentFileInfo> findAllByEntityId(String entityClassName, String entityId) {
    return provider.findAllByEntityId(entityClassName, entityId);
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
  // public List<IAttachmentFileInfo> GetPaging(int startIndex, int pageSize, DataQuery query, tangible.RefObject<Integer> rowCount) {
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
   * @param id   附件标识
   * @param name 新的附件名称
   */
  @Override
  public void rename(String id, String name) {
    provider.rename(id, name);
  }

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName   实体类名称
   * @param entityId          实体标识
   * @param attachmentFileIds 附件唯一标识，多个附件以逗号隔开
   */
  @Override
  public void setValid(String entityClassName, String entityId, String attachmentFileIds) {
    setValid(entityClassName, entityId, attachmentFileIds, false);
  }

  /**
   * 设置有效的文件信息
   *
   * @param entityClassName   实体类名称
   * @param entityId          实体标识
   * @param attachmentFileIds 附件唯一标识，多个附件以逗号隔开
   * @param append            附加文件
   */
  @Override
  public void setValid(String entityClassName, String entityId, String attachmentFileIds, boolean append) {
    this.provider.setValid(entityClassName, entityId, attachmentFileIds, append);
  }

  /**
   * 物理复制全部附件信息到实体类
   *
   * @param param           IAttachmentFileInfo 实例详细信息
   * @param entityId        实体标识
   * @param entityClassName 实体类名称
   * @return 新的 IAttachmentFileInfo 实例详细信息
   */
  @Override
  public IAttachmentFileInfo copy(IAttachmentFileInfo param, String entityClassName, String entityId) {
    // IAttachmentParentObject parent = new AttachmentParentObject();

    // parent.setEntityId(entityId);
    // parent.setEntityClassName(entityClassName);
    // parent.setAttachmentEntityClassName(KernelContext.parseObjectType(AttachmentFileInfo.class));
    // parent.setAttachmentFolder(UploadPathUtil.getAttachmentFolder(param.getVirtualPath(), param.getFolderRule()));

    IAttachmentFileInfo attachment = UploadFileUtil.createAttachmentFile(
      entityId,
      entityClassName,
      KernelContext.parseObjectType(AttachmentFileInfo.class),
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
   * @param param 实例 IAttachmentFileInfo 详细信息
   * @param path  文件目标路径
   * @return 新的 实例 IAttachmentFileInfo 详细信息
   */
  @Override
  public IAttachmentFileInfo move(IAttachmentFileInfo param, String path) {
    throw new UnsupportedOperationException();
  }
}
