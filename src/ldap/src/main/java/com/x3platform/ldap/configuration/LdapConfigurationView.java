package com.x3platform.ldap.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 会话的配置信息视图
 */

@Component
public class LdapConfigurationView {

  private static volatile LdapConfigurationView sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static LdapConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(LdapConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  public void reload() {
    sInstance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.ldap.configuration.LdapConfiguration")
  LdapConfiguration configuration;

  /**
   * LDAP 集成模式
   */
  public boolean getIntegratedMode() {
    return BooleanUtil.bool(configuration.getIntegratedMode());
  }

  /**
   * 服务器
   */
  public String getHost() {
    return  configuration.getHost();
  }

  /**
   * LDAP Base
   */
  public String getBase() {
    return  configuration.getBase();
  }

  /**
   * LDAP Username
   */
  public String getUsername() {
    return  configuration.getUsername();
  }

  /**
   * LDAP Password
   */
  public String getPassword() {
    return  configuration.getPassword();
  }

  /**
   * LDAP 域
   */
  public String getDomain() {
    return  configuration.getDomain();
  }

  /**
   * LDAP 邮箱的后缀域名
   */
  public String getSuffixEmailDomain() {
    return  configuration.getSuffixEmailDomain();
  }

  /**
   * LDAP 对象唯一名称的后缀
   */
  public String getSuffixDistinguishedName() {
    return  configuration.getSuffixDistinguishedName();
  }

  /**
   * LDAP 公司组织存放的根目录 (包括角色)
   */
  public String getCorporationOrganizationUnitFolderRoot() {
    return  configuration.getCorporationOrganizationUnitFolderRoot();
  }

  /**
   * LDAP 公司用户存放的根目录
   */
  public String getCorporationUserFolderRoot() {
    return  configuration.getCorporationUserFolderRoot();
  }

  /**
   * LDAP 公司群组存放的根目录
   */
  public String getCorporationGroupFolderRoot() {
    return  configuration.getCorporationGroupFolderRoot();
  }

  /**
   * LDAP 公司角色存放的根目录 (非组织结构中的角色数据)
   */
  public String getCorporationRoleFolderRoot() {
    return  configuration.getCorporationRoleFolderRoot();
  }

  /**
   * LDAP 新建帐号的默认密码
   */
  public String getNewlyCreatedAccountPassword() {
    return  configuration.getNewlyCreatedAccountPassword();
  }
}
