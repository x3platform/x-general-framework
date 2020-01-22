package com.x3platform.security.verificationcode;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.security.verificationcode.services.VerificationCodeService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

/**
 * 验证码管理上下文环境
 *
 * @author ruanyu
 */
public class VerificationCodeContext extends CustomPlugin {

  @Override
  public String getName() {
    return "验证码管理";
  }

  private static final String APPLICATION_NAME = "security-verification-code";

  private static volatile VerificationCodeContext sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 流水号上下文环境
   */
  public static VerificationCodeContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new VerificationCodeContext();
        }
      }
    }

    return sInstance;
  }

  private VerificationCodeService mVerificationCodeService = null;

  /**
   * 流水号服务提供者
   */
  public final VerificationCodeService getVerificationCodeService() {
    return mVerificationCodeService;
  }

  private VerificationCodeContext() {
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
    mVerificationCodeService = SpringContext
      .getBean("com.x3platform.security.verificationcode.services.VerificationCodeService",
        VerificationCodeService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"), APPLICATION_NAME);
  }
}
