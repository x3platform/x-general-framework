package com.x3platform.data.commons;

import static com.x3platform.data.commons.configuration.DataCommonConfiguration.APPLICATION_NAME;

import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.data.commons.configuration.DataCommonConfigurationView;
import com.x3platform.data.commons.services.DataDictService;
import com.x3platform.data.commons.services.DataSourceService;
import com.x3platform.data.commons.services.DataStorageNodeService;
import com.x3platform.data.commons.services.DataStorageSchemaService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 数据存储管理
 *
 * @author ruanyu
 */
public class DataCommonContext extends CustomPlugin {

  @Override
  public String getName() {
    return "数据基础管理";
  }

  private static volatile DataCommonContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 返回 {@link DataCommonContext} 实例
   */
  public static DataCommonContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new DataCommonContext();
        }
      }
    }

    return sInstance;
  }

  private DataSourceService mDataSourceService = null;

  /**
   * 数据源服务提供者
   *
   * @return 返回 {@link DataSourceService} 实例
   */
  public final DataSourceService getDataSourceService() {
    return mDataSourceService;
  }

  private DataDictService mDataDictService = null;

  /**
   * 数据字典服务提供者
   *
   * @return 返回 {@link DataDictService} 实例
   */
  public final DataDictService getDataDictService() {
    return mDataDictService;
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

  private DataCommonContext() {
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
      InternalLogger.getLogger().info(I18n.getStrings().text("application_is_reloading"), APPLICATION_NAME);
      // 重新加载配置信息
      DataCommonConfigurationView.getInstance().reload();
    } else {
      InternalLogger.getLogger().info(I18n.getStrings().text("application_is_loading"), APPLICATION_NAME);
    }

    // 创建数据服务对象
    mDataSourceService = SpringContext.getBean("com.x3platform.data.commons.services.DataSourceService",
      DataSourceService.class);
    mDataDictService = SpringContext.getBean("com.x3platform.data.commons.services.DataDictService",
      DataDictService.class);
    mDataStorageSchemaService = SpringContext.getBean("com.x3platform.data.commons.services.DataStorageSchemaService",
      DataStorageSchemaService.class);
    mDataStorageNodeService = SpringContext.getBean("com.x3platform.data.commons.services.DataStorageNodeService",
      DataStorageNodeService.class);

    InternalLogger.getLogger().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
