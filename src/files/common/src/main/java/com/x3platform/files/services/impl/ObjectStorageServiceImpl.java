package com.x3platform.files.services.impl;

import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;
// import com.x3platform.files;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.configuration.ObjectStorageConfiguration;
import com.x3platform.files.mappers.ObjectStorageFileMapper;
import com.x3platform.files.services.ObjectStorageService;
import com.x3platform.files.util.ObjectStorageFileUtil;
import com.x3platform.files.util.ObjectStoragePathUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.util.ByteUtil;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ObjectStorageServiceImpl implements ObjectStorageService {

  /**
   * 数据提供器
   */
  @Autowired
  protected ObjectStorageFileMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity 实例 ObjectStorageFile 详细信息
   * @return 实例 ObjectStorageFile 详细信息
   */
  @Override
  public int save(ObjectStorageFile entity) {
    if (StringUtil.isNullOrEmpty(entity.getFileName())) {
      throw new IllegalArgumentException("文件件名称必填。");
    }

    entity.setFileType(entity.getFileType().toLowerCase());

    String path = ObjectStoragePathUtil.combinePhysicalPath(
      entity.getFileFolder(), StringUtil.format("{}{}", entity.getId(), entity.getFileType()));

    try {
      ObjectStoragePathUtil.tryCreateDirectory(path);

      if (entity.getFileData() == null) {
        InternalLogger.getLogger().error("file data is null.");
      }
      else {
        ByteUtil.toFile(entity.getFileData(), path);
      }

      provider.save(entity);

      return 0;
    } catch (Exception ex) {
      InternalLogger.getLogger().error("ObjectStorageServiceImpl.save Exception", ex);
    }

    return 1;
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
      if (AppsSecurity.isAdministrator(account, ObjectStorageConfiguration.APPLICATION_NAME)) {
        this.provider.delete(id);
      } else {
        ObjectStorageFile file = this.findOne(id);

        if (account.getId().equals(file.getCreatedBy())) {
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
   * @param id ObjectStorageFile 唯一标识
   * @return 返回一个 实例 ObjectStorageFile 的详细信息
   */
  @Override
  public ObjectStorageFile findOne(String id) {
    ObjectStorageFile entity = provider.findOne(id);

    entity.setFileFolder(ObjectStoragePathUtil.getFileFolder(entity.getVirtualPath(), entity.getFolderRule()));

    return entity;
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 返回所有实例 ObjectStorageFile 的详细信息
   */
  @Override
  public List<ObjectStorageFile> findAll(DataQuery query) {
    return this.provider.findAll(query.getMap());
  }

  /**
   * 查询所有相关记录
   *
   * @param entityClassName 实体类名称
   * @param entityId        实体类标识
   * @return 返回所有实例 ObjectStorageFile 的详细信息
   */
  @Override
  public List<ObjectStorageFile> findAllByEntityId(String entityClassName, String entityId) {
    return provider.findAllByEntityId(entityClassName, entityId, -1);
  }

  // -------------------------------------------------------
  // 自定义功能
  // -------------------------------------------------------

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
   * @param entity          ObjectStorageFile 实例详细信息
   * @param entityId        实体标识
   * @param entityClassName 实体类名称
   * @return 新的 ObjectStorageFile 实例详细信息
   */
  @Override
  public ObjectStorageFile copy(ObjectStorageFile entity, String entityClassName, String entityId) {

    ObjectStorageFile file = ObjectStorageFileUtil.createObjectStorageFile(
      entityId,
      entityClassName,
      entity.getFileEntityClassName(),
      ObjectStoragePathUtil.getFileFolder(entity.getVirtualPath(), entity.getFolderRule()),
      entity.getFileName(),
      entity.getFileType(),
      entity.getFileSize(),
      entity.getFileData());

    try {
      file.save();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return file;
  }

  /**
   * 物理移动附件路径
   *
   * @param entity 实例 ObjectStorageFile 详细信息
   * @param path   文件目标路径
   * @return 新的 实例 ObjectStorageFile 详细信息
   */
  @Override
  public ObjectStorageFile move(ObjectStorageFile entity, String path) {
    throw new UnsupportedOperationException();
  }
}
