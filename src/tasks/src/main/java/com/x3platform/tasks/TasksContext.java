package com.x3platform.tasks;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.configuration.MembershipConfiguration;
import com.x3platform.plugins.CustomPlugin;
import com.x3platform.tasks.configuration.TasksConfiguration;
import com.x3platform.tasks.configuration.TasksConfigurationView;
import com.x3platform.tasks.services.ITaskWorkItemService;
import com.x3platform.tasks.services.ITaskWorkService;

public class TasksContext extends CustomPlugin {
  @Override
  public String getName() {
    return "人员及权限管理";
  }

  private static volatile TasksContext sInstance = null;

  private static Object lockObject = new Object();

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

  private ITaskWorkService mTaskWorkService = null;

  /**
   * 任务服务提供者
   */
  public final ITaskWorkService getTaskWorkService() {
    return mTaskWorkService;
  }

  private ITaskWorkItemService mTaskWorkItemService = null;

  /**
   * 任务工作项服务提供者
   */
  public final ITaskWorkItemService getTaskWorkItemService() {
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

  private void reload() {
    if (this.restartCount > 0) {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_reloading"), TasksConfiguration.ApplicationName))
      ;

      // 重新加载配置信息
      TasksConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_loading"), TasksConfiguration.ApplicationName));
    }

    // 创建数据服务对象
    // this.mTaskWorkService = SpringContext.getBean("com.x3platform.tasks.services.ITaskWorkService", ITaskWorkService.class);
    this.mTaskWorkItemService = SpringContext.getBean("com.x3platform.tasks.services.ITaskWorkItemService", ITaskWorkItemService.class);

    KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_successfully_loaded"), TasksConfiguration.ApplicationName));
  }
}
