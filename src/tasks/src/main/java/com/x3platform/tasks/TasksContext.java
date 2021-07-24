package com.x3platform.tasks;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;
import com.x3platform.tasks.configuration.TasksConfiguration;
import com.x3platform.tasks.configuration.TasksConfigurationView;
import com.x3platform.tasks.services.TaskWaitingItemService;
import com.x3platform.tasks.services.TaskWorkItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务管理上下文对象
 *
 * @author ruanyu
 */
public class TasksContext extends CustomPlugin {

  @Override
  public String getName() {
    return "任务管理";
  }

  private static volatile TasksContext sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   */
  public static TasksContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new TasksContext();
        }
      }
    }

    return sInstance;
  }

  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 日志记录器
   */
  public static Logger getLogger() {
    return getInstance().logger;
  }

  private TaskWaitingItemService mTaskWaitingItemService = null;

  /**
   * 任务服务提供者
   */
  public TaskWaitingItemService getTaskWaitingItemService() {
    return mTaskWaitingItemService;
  }

  private TaskWorkItemService mTaskWorkItemService = null;

  /**
   * 任务工作项服务提供者
   */
  public TaskWorkItemService getTaskWorkItemService() {
    return mTaskWorkItemService;
  }

  private TasksContext() {
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
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"), TasksConfiguration.APPLICATION_NAME);
      // 重新加载配置信息
      TasksConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"), TasksConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mTaskWorkItemService = SpringContext.getBean("com.x3platform.tasks.services.TaskWorkItemService",
      TaskWorkItemService.class);
    mTaskWaitingItemService = SpringContext.getBean("com.x3platform.tasks.services.TaskWaitingItemService",
      TaskWaitingItemService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), TasksConfiguration.APPLICATION_NAME);
  }
}
