package com.x3platform.files;

import static com.x3platform.files.configuration.ObjectStorageConfiguration.APPLICATION_NAME;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.files.configuration.ObjectStorageConfigurationView;
import com.x3platform.files.services.ObjectStorageService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 对象存储上下文环境
 *
 * @author ruanyu
 */
public final class ObjectStorageContext extends CustomPlugin {

  private static volatile ObjectStorageContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 附件存储上下文环境
   */
  public static ObjectStorageContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new ObjectStorageContext();
        }
      }
    }

    return sInstance;
  }

  @Override
  public String getName() {
    return "对象存储";
  }

  private ObjectStorageService objectStorageService = null;

  public ObjectStorageService getObjectStorageService() {
    return this.objectStorageService;
  }

  /**
   * 构造函数
   */
  private ObjectStorageContext() {
    this.restart();
  }

  /**
   * 重启次数计数器
   */
  private int restartCount = 0;

  /**
   * 重启
   *
   * @return 消息代码 0-重启成功, 大于0-重启失败.
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
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"), APPLICATION_NAME);

      // 重新加载配置信息
      ObjectStorageConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"), APPLICATION_NAME);
    }

    // 创建数据服务对象
    this.objectStorageService = SpringContext.getBean(
      "com.x3platform.files.services.ObjectStorageService", ObjectStorageService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
