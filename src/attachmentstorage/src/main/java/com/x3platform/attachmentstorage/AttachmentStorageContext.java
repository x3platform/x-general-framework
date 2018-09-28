package com.x3platform.attachmentstorage;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfiguration;
import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.services.IAttachmentFileService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 附件上下文环境
 */
public final class AttachmentStorageContext extends CustomPlugin {
  ///#region 静态属性:Instance
  private static volatile AttachmentStorageContext instance = null;

  private static Object lockObject = new Object();

  public static AttachmentStorageContext getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = new AttachmentStorageContext();
        }
      }
    }

    return instance;
  }

  @Override
  public String getName() {
    return "附件存储";
  }

  private IAttachmentFileService mAttachmentFileService = null;

  public IAttachmentFileService getAttachmentFileService() {
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
   * 重启插件
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
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_reloading"), AttachmentStorageConfiguration.ApplicationName));

      // 重新加载配置信息
      AttachmentStorageConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_loading"), AttachmentStorageConfiguration.ApplicationName));
    }

    // 创建数据服务对象
    this.mAttachmentFileService = SpringContext.getBean("com.x3platform.attachmentstorage.services.IAttachmentFileService", IAttachmentFileService.class);

    KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_successfully_loaded"), AttachmentStorageConfiguration.ApplicationName));
  }
}
