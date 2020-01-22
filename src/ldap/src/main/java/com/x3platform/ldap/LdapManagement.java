package com.x3platform.ldap;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.globalization.I18n;
import com.x3platform.ldap.interop.GroupUtil;
import com.x3platform.ldap.interop.OrganizationUnitUtil;
import com.x3platform.plugins.CustomPlugin;
import com.x3platform.ldap.configuration.LdapConfiguration;
import com.x3platform.ldap.configuration.LdapConfigurationView;
import com.x3platform.ldap.interop.UserUtil;
import org.slf4j.Logger;

/**
 * LDAP 管理
 *
 * @author ruanyu
 */
public class LdapManagement extends CustomPlugin {

  private Logger logger = KernelContext.getLog();

  @Override
  public String getName() {
    return "LDAP 管理";
  }

  private static volatile LdapManagement sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static LdapManagement getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new LdapManagement();
        }
      }
    }

    return sInstance;
  }

  private UserUtil mUserUtil = null;

  /**
   * 用户
   */
  public UserUtil getUser() {
    return mUserUtil;
  }

  private OrganizationUnitUtil mOrganizationUnitUtil = null;

  /**
   * 组织
   */
  public OrganizationUnitUtil getOrganizationUnit() {
    return mOrganizationUnitUtil;
  }

  private GroupUtil mGroupUtil = null;

  /**
   * 群组
   */
  public GroupUtil getGroup() {
    return mGroupUtil;
  }

  private LdapManagement() {
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
      logger.error(ex.toString());
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (restartCount > 0) {
      logger.info(I18n.getStrings().text("application_is_reloading"), LdapConfiguration.APPLICATION_NAME);

      // 重新加载配置信息
      LdapConfigurationView.getInstance().reload();
    } else {
      logger.info(I18n.getStrings().text("application_is_loading"), LdapConfiguration.APPLICATION_NAME);
    }

    initialize();

    logger.info(I18n.getStrings().text("application_is_successfully_loaded"), LdapConfiguration.APPLICATION_NAME);
  }

  /**
   * 初始化
   */
  public void initialize(){
    // 创建数据服务对象
    mUserUtil = new UserUtil();

    mOrganizationUnitUtil = new OrganizationUnitUtil();

    mGroupUtil = new GroupUtil();
  }
}
