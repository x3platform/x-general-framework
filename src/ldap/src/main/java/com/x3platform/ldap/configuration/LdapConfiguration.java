package com.x3platform.ldap.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 会话的配置信息
 */
@Component("com.x3platform.ldap.configuration.LdapConfiguration")
public class LdapConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "ldap";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "ldap";

  @Value("${x3platform." + SECTION_NAME + ".integrated-mode:off}")
  private String integratedMode;

  /**
   * LDAP 集成模式
   */
  public String getIntegratedMode() {
    return integratedMode;
  }

  @Value("${x3platform." + SECTION_NAME + ".host:ldap://localhost:389}")
  private String host;

  /**
   * LDAP 服务器
   */
  public String getHost() {
    return host;
  }

  @Value("${x3platform." + SECTION_NAME + ".base:dc=x3platform,dc=com}")
  private String base;

  /**
   * LDAP Base
   */
  public String getBase() {
    return base;
  }

  @Value("${x3platform." + SECTION_NAME + ".username:cn=root,dc=x3platform,dc=com}")
  private String username;

  /**
   * LDAP Username
   */
  public String getUsername() {
    return username;
  }

  @Value("${x3platform." + SECTION_NAME + ".password:[PASSWORD]}")
  private String password;

  /**
   * LDAP Password
   */
  public String getPassword() {
    return password;
  }

  @Value("${x3platform." + SECTION_NAME + ".domain:x3platform.com}")
  private String domain;

  /**
   * LDAP 域
   */
  public String getDomain() {
    return domain;
  }

  @Value("${x3platform." + SECTION_NAME + ".suffix-email-domain:@x3platform.com}")
  private String suffixEmailDomain;

  /**
   * LDAP 邮箱的后缀域名
   */
  public String getSuffixEmailDomain() {
    return suffixEmailDomain;
  }

  @Value("${x3platform." + SECTION_NAME + ".suffix-distinguished-name:,dc=x3platform,dc=com}")
  private String suffixDistinguishedName;

  /**
   * LDAP 对象唯一名称的后缀
   */
  public String getSuffixDistinguishedName() {
    return suffixDistinguishedName;
  }

  @Value("${x3platform." + SECTION_NAME + ".corporation-organization-unit-folder-root:Organizationals}")
  private String corporationOrganizationUnitFolderRoot;

  /**
   * LDAP 公司组织存放的根目录 (包括角色)
   */
  public String getCorporationOrganizationUnitFolderRoot() {
    return corporationOrganizationUnitFolderRoot;
  }

  @Value("${x3platform." + SECTION_NAME + ".corporation-user-folder-root:Peoples}")
  private String corporationUserFolderRoot;

  /**
   * LDAP 公司用户存放的根目录
   */
  public String getCorporationUserFolderRoot() {
    return corporationUserFolderRoot;
  }

  @Value("${x3platform." + SECTION_NAME + ".corporation-group-folder-root:Groups}")
  private String corporationGroupFolderRoot;

  /**
   * LDAP 公司群组存放的根目录
   */
  public String getCorporationGroupFolderRoot() {
    return corporationGroupFolderRoot;
  }

  @Value("${x3platform." + SECTION_NAME + ".corporation-role-folder-root:Roles}")
  private String corporationRoleFolderRoot;

  /**
   * LDAP 公司角色存放的根目录 (非组织结构中的角色数据)
   */
  public String getCorporationRoleFolderRoot() {
    return corporationRoleFolderRoot;
  }

  @Value("${x3platform." + SECTION_NAME + ".newly-created-account-password:[PASSWORD]}")
  private String newlyCreatedAccountPassword;

  /**
   * LDAP 新建帐号的默认密码
   */
  public String getNewlyCreatedAccountPassword() {
    return newlyCreatedAccountPassword;
  }
}
