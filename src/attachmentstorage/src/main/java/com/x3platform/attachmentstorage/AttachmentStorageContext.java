package com.x3platform.attachmentstorage;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfiguration;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.services.AttachmentFileService;
import com.x3platform.attachmentstorage.services.FastDFSFileTransferService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 附件存储上下文环境
 *
 * @author ruanyu
 */
public final class AttachmentStorageContext extends CustomPlugin {

  private static volatile AttachmentStorageContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 附件存储上下文环境
   */
  public static AttachmentStorageContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new AttachmentStorageContext();
        }
      }
    }

    return sInstance;
  }

  @Override
  public String getName() {
    return "附件存储";
  }

  private AttachmentFileService mAttachmentFileService = null;

  public AttachmentFileService getAttachmentFileService() {
    return this.mAttachmentFileService;
  }

//  private IAttachmentDistributedFileService m_AttachmentDistributedFileService = null;
//
//  public IAttachmentDistributedFileService getAttachmentDistributedFileService() {
//    return m_AttachmentDistributedFileService;
//  }
//
//  private IAttachmentWarnService m_AttachmentWarnService = null;
//
//  public IAttachmentWarnService getAttachmentWarnService() {
//    return this.m_AttachmentWarnService;
//  }

  private FastDFSFileTransferService fastDFSFileTransferService = null;

  public FastDFSFileTransferService getFastDFSFileTransferService() {
    return this.fastDFSFileTransferService;
  }

  /**
   * 构造函数
   */
  private AttachmentStorageContext() {
    this.restart();
  }

  /**
   * 重启次数计数器
   */
  private int restartCount = 0;

  /**
   * 重启
   *
   * @return 返回信息. =0代表重启成功, >0代表重启失败.
   */
  @Override
  public int restart() {
    try {
      this.reload();
      // 自增重启次数计数器
      this.restartCount++;
    } catch (RuntimeException ex) {
      KernelContext.getLog().error(ex.toString());
      throw ex;
    }

    return 0;
  }

  /**
   * 重新加载
   */
  private void reload() {
    if (this.restartCount > 0) {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_reloading"), AttachmentStorageConfiguration.APPLICATION_NAME));

      // 重新加载配置信息
      AttachmentStorageConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_loading"), AttachmentStorageConfiguration.APPLICATION_NAME));
    }

    // 创建数据服务对象
    this.mAttachmentFileService = SpringContext.getBean("com.x3platform.attachmentstorage.services.AttachmentFileService", AttachmentFileService.class);
    this.fastDFSFileTransferService = SpringContext.getBean("com.x3platform.attachmentstorage.services.FastDFSFileTransferService", FastDFSFileTransferService.class);

    KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_successfully_loaded"), AttachmentStorageConfiguration.APPLICATION_NAME));
  }
}
