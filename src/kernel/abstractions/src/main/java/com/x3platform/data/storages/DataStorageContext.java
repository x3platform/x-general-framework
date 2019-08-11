package com.x3platform.data.storages;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.data.storages.configuration.DataStoragesConfiguration;
import com.x3platform.data.storages.configuration.DataStoragesConfigurationView;
import com.x3platform.data.storages.services.DataStorageNodeService;
import com.x3platform.data.storages.services.DataStorageSchemaService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 数据存储管理
 *
 * @author ruanyu
 */
public class DataStorageContext extends CustomPlugin {

  @Override
  public String getName() {
    return "数据存储管理";
  }

  private static volatile DataStorageContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 返回 {@link DataStorageContext} 实例
   */
  public static DataStorageContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new DataStorageContext();
        }
      }
    }

    return sInstance;
  }

  private DataStorageSchemaService mDataStorageSchemaService = null;

  /**
   * 数据存储架构服务提供者
   *
   * @return 返回 {@link DataStorageSchemaService} 实例
   */
  public final DataStorageSchemaService getDataStorageSchemaService() {
    return mDataStorageSchemaService;
  }

  private DataStorageNodeService mDataStorageNodeService = null;

  /**
   * 数据存储节点服务提供者
   *
   * @return 返回 {@link DataStorageNodeService} 实例
   */
  public final DataStorageNodeService getDataStorageNodeService() {
    return mDataStorageNodeService;
  }

  private DataStorageContext() {
    restart();
  }

  /**
   * 重启次数计数器
   */
  private int restartCount = 0;

  /**
   * 重启
   *
   * @return 消息代码. =0代表重启成功, &gt;0代表重启失败.
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
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"),
        DataStoragesConfiguration.APPLICATION_NAME);

      // 重新加载配置信息
      DataStoragesConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"),
        DataStoragesConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mDataStorageSchemaService = SpringContext.getBean("com.x3platform.data.storages.services.DataStorageSchemaService",
      DataStorageSchemaService.class);
    mDataStorageNodeService = SpringContext.getBean("com.x3platform.data.storages.services.DataStorageNodeService",
      DataStorageNodeService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"),
      DataStoragesConfiguration.APPLICATION_NAME);
  }
}
