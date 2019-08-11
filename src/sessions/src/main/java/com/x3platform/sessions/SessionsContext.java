package com.x3platform.sessions;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.plugins.CustomPlugin;
import com.x3platform.sessions.configuration.SessionsConfiguration;
import com.x3platform.sessions.configuration.SessionsConfigurationView;
import com.x3platform.sessions.services.TicketService;
import org.slf4j.Logger;

/**
 * 会话管理
 *
 * @author ruanyu
 */
public class SessionsContext extends CustomPlugin {

  private Logger logger = KernelContext.getLog();

  @Override
  public String getName() {
    return "会话管理";
  }

  private static volatile SessionsContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static SessionsContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new SessionsContext();
        }
      }
    }

    return sInstance;
  }

  private TicketService mTicketService = null;

  /**
   * 帐号缓存服务提供者
   */
  public final TicketService getTicketService() {
    return mTicketService;
  }

  private SessionsContext() {
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
      reload();

      // 自增重启次数计数器
      restartCount++;
    } catch (RuntimeException ex) {
      logger.error(ex.toString());
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (restartCount > 0) {
      logger.info(I18n.getStrings().text("application_is_reloading"), SessionsConfiguration.APPLICATION_NAME);

      // 重新加载配置信息
      SessionsConfigurationView.getInstance().reload();
    } else {
      logger.info(I18n.getStrings().text("application_is_loading"), SessionsConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mTicketService = SpringContext.getBean("com.x3platform.sessions.services.TicketService",
      TicketService.class);

    logger.info(I18n.getStrings().text("application_is_successfully_loaded"), SessionsConfiguration.APPLICATION_NAME);
  }

  /**
   * 获取当前验证的帐号信息
   *
   * @param strategy 存储策略
   * @param accountIdentity 存储策略
   */
  public <T extends Account> T getAuthAccount(TicketStorageStrategy strategy, String accountIdentity) {
    return (T) getTicketService().getAuthAccount(strategy, accountIdentity);
  }

  /**
   * 检测是否包含当前的键
   *
   * @param accountIdentity 键
   */
  public boolean contains(String accountIdentity) {
    return mTicketService.isExist(accountIdentity);
  }

  /**
   * 读取帐号缓存信息
   */
  public Ticket read(String accountIdentity) {
    return mTicketService.read(accountIdentity);
  }

  /**
   * 写入帐号缓存信息
   *
   * @param strategy 存储策略
   * @param accountIdentity 帐号会话唯一标识
   * @param account 帐号信息
   */
  public void write(TicketStorageStrategy strategy, String accountIdentity, Account account) {
    mTicketService.write(strategy, "", accountIdentity, account);
  }

  /**
   * 写入帐号缓存信息
   *
   * @param strategy 存储策略
   * @param accountIdentity 帐号会话唯一标识
   * @param account 帐号信息
   */
  public void write(TicketStorageStrategy strategy, String appKey, String accountIdentity, Account account) {
    mTicketService.write(strategy, appKey, accountIdentity, account);
  }
}
