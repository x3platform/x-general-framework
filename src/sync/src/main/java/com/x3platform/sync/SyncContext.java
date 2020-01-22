package com.x3platform.sync;

import static com.x3platform.sync.configuration.SyncConfiguration.APPLICATION_NAME;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;
import com.x3platform.sync.configuration.SyncConfigurationView;
import com.x3platform.sync.services.SyncPkgService;
import com.x3platform.sync.services.SyncQueueService;
import com.x3platform.sync.services.SyncSettingService;

/**
 * 同步管理上下文对象
 *
 * @author ruanyu
 */
public class SyncContext extends CustomPlugin {

  @Override
  public String getName() {
    return "同步管理";
  }

  private static volatile SyncContext sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   */
  public static SyncContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new SyncContext();
        }
      }
    }

    return sInstance;
  }

  private SyncSettingService syncSettingService = null;

  /**
   * 同步设置服务提供者
   */
  public SyncSettingService getSyncSettingService() {
    return syncSettingService;
  }

  private SyncPkgService syncPkgService = null;

  /**
   * 同步更新包服务提供者
   */
  public SyncPkgService getSyncPkgService() {
    return syncPkgService;
  }

  private SyncQueueService syncQueueService = null;

  /**
   * 同步更新包服务提供者
   */
  public SyncQueueService getSyncQueueService() {
    return syncQueueService;
  }

  private SyncContext() {
    restart();
  }

  /**
   * 重启次数计数器
   */
  private int restartCount = 0;

  /**
   * 重启插件
   *
   * @return 消息代码 0-重启成功, 大于0-重启失败.
   */
  @Override
  public int restart() {
    try {
      reload();
      // 自增重启次数计数器
      restartCount++;
    } catch (RuntimeException ex) {
      KernelContext.getLog().error(ex.toString());
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (restartCount > 0) {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"), APPLICATION_NAME);
      // 重新加载配置信息
      SyncConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"), APPLICATION_NAME);
    }

    // 创建数据服务对象
    syncSettingService = SpringContext.getBean("com.x3platform.sync.services.SyncSettingService",
      SyncSettingService.class);
    syncPkgService = SpringContext.getBean("com.x3platform.sync.services.SyncPkgService",
      SyncPkgService.class);
    syncQueueService = SpringContext.getBean("com.x3platform.sync.services.SyncQueueService",
      SyncQueueService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
