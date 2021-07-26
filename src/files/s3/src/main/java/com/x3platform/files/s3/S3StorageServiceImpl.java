package com.x3platform.files.s3;

import com.x3platform.GenericException;
import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsSecurity;

import com.x3platform.cachebuffer.redis.FastJsonRedisSerializer;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.configuration.ObjectStorageConfiguration;
import com.x3platform.files.mappers.ObjectStorageFileMapper;
import com.x3platform.files.services.ObjectStorageService;
import com.x3platform.files.services.impl.ObjectStorageServiceImpl;
import com.x3platform.files.util.ObjectStorageFileUtil;
import com.x3platform.files.util.ObjectStoragePathUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.membership.Account;
import com.x3platform.util.ByteUtil;
import com.x3platform.util.StreamUtil;
import com.x3platform.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

public final class S3StorageServiceImpl implements ObjectStorageService {
  @Autowired
  private S3Properties s3Properties;
  
  @Autowired
  private S3Client s3Client;
  
  /**
   * 数据提供器
   */
  @Autowired
  private ObjectStorageFileMapper provider = null;
  
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
    
    // 设置默认的桶
    if (StringUtil.isNullOrEmpty(entity.getBucketName())) {
      entity.setBucketName(s3Properties.getDefaultBucketName());
    }
    
    entity.setFileType(entity.getFileType().toLowerCase());
    
    Account account = KernelContext.getCurrent().getUser();
    
    if (account != null) {
      entity.setCreatedBy(account.getId());
    }
    
    String objectName = ObjectStoragePathUtil.parseRule(entity.getFileFolder(), LocalDateTime.now())
      + StringUtil.format("{}{}", entity.getId(), entity.getFileType());
    
    try {
      s3Client.putObject(PutObjectRequest.builder()
          .bucket(entity.getBucketName())
          .key(objectName)
          .build(),
        RequestBody.fromBytes(entity.getFileData()));
      
      provider.save(entity);
      
      return 0;
    } catch (Exception ex) {
      InternalLogger.getLogger().error("s3 putObject Exception", ex);
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
    ObjectStorageFile entity = this.findOne(id);
    
    Account account = KernelContext.getCurrent().getUser();
    
    if (account == null) {
      // account == null 一般是在测试环境中使用.
      // this.provider.delete(id);
      throw new GenericException("1", "需要登录操作.");
    } else {
      if (entity != null) {
        String objectName = ObjectStoragePathUtil.parseRule(entity.getFileFolder(), entity.getCreatedDate())
          + StringUtil.format("{}{}", entity.getId(), entity.getFileType());
        try {
          if (AppsSecurity.isAdministrator(account, ObjectStorageConfiguration.APPLICATION_NAME)
            || entity.getCreatedBy().equals(account.getId())) {
            // 允许删除文件的角色:
            // 1.应用的管理员
            // 2.文件的创建者
            s3Client.deleteObject(DeleteObjectRequest.builder()
              .bucket(entity.getBucketName())
              .key(objectName)
              .build());
            this.provider.delete(id);
          } else {
            throw new GenericException("1", "没有该文件的删除权限.");
          }
          return 0;
        } catch (Exception ex) {
          InternalLogger.getLogger().error("s3 deleteObject Exception", ex);
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
    
    if (entity == null) {
      return null;
    }
    
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
   * @param param           ObjectStorageFile 实例详细信息
   * @param entityId        实体标识
   * @param entityClassName 实体类名称
   * @return 新的 ObjectStorageFile 实例详细信息
   */
  @Override
  public ObjectStorageFile copy(ObjectStorageFile param, String entityClassName, String entityId) {
    
    ObjectStorageFile file = ObjectStorageFileUtil.createObjectStorageFile(
      entityId,
      entityClassName,
      param.getFileEntityClassName(),
      ObjectStoragePathUtil.getFileFolder(param.getVirtualPath(), param.getFolderRule()),
      param.getFileName(),
      param.getFileType(),
      param.getFileSize(),
      param.getFileData());
    
    try {
      file.save();
    } catch (Exception ex) {
      InternalLogger.getLogger().error("S3StorageServiceImpl.copy Exception", ex);
    }
    
    return file;
  }
  
  /**
   * 物理移动附件路径
   *
   * @param param 实例 ObjectStorageFile 详细信息
   * @param path  文件目标路径
   * @return 新的 实例 ObjectStorageFile 详细信息
   */
  @Override
  public ObjectStorageFile move(ObjectStorageFile param, String path) {
    throw new UnsupportedOperationException();
  }
}
