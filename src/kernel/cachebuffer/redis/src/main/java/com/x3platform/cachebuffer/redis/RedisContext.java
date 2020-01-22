package com.x3platform.cachebuffer.redis;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.cachebuffer.redis.services.RedisTemplateService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * Redis 上下文管理
 *
 * @author ruanyu
 */
public class RedisContext extends CustomPlugin {

  private static String APPLICATION_NAME = "cachebuffer.redis";

  @Override
  public String getName() {
    return "Redis 上下文管理";
  }

  private static volatile RedisContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static RedisContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new RedisContext();
        }
      }
    }

    return sInstance;
  }

  private RedisTemplateService mRedisTemplateService = null;

  /**
   * Redis 模板服务提供者
   */
  public RedisTemplateService getRedisTemplateService() {
    return mRedisTemplateService;
  }

  private RedisContext() {
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
      //MembershipConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"), APPLICATION_NAME);
    }

    // 创建数据服务对象
    mRedisTemplateService = SpringContext.getBean("com.x3platform.cachebuffer.redis.services.RedisTemplateService",
      RedisTemplateService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
