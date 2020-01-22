package com.x3platform.security.authority;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.security.authority.services.AuthorityService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 权限配置上下文环境
 *
 * @author ruanyu
 */
public class AuthorityContext extends CustomPlugin {

  @Override
  public String getName() {
    return "权限配置";
  }

  private static final String APPLICATION_NAME = "security-authority";

  private static volatile AuthorityContext sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 流水号上下文环境
   */
  public static AuthorityContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new AuthorityContext();
        }
      }
    }

    return sInstance;
  }

  private AuthorityService mAuthorityService = null;

  /**
   * 流水号服务提供者
   */
  public final AuthorityService getAuthorityService() {
    return mAuthorityService;
  }

  private AuthorityContext() {
    restart();
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
      reload();
      // 自增重启次数计数器
      restartCount++;
    } catch (RuntimeException ex) {
      KernelContext.getLog().error(ex.getMessage(), ex);
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (restartCount > 0) {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"), APPLICATION_NAME);
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"), APPLICATION_NAME);
    }

    // 创建数据服务对象
    mAuthorityService = SpringContext.getBean("com.x3platform.security.authority.services.AuthorityService",
      AuthorityService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
