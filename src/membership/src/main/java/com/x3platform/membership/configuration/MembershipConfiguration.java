package com.x3platform.membership.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 人员及权限的配置信息
 *
 * @author ruanyu
 */
@Component("com.x3platform.membership.configuration.MembershipConfiguration")
public class MembershipConfiguration {

  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "membership";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "membership";

  // -------------------------------------------------------
  // 单点登录
  // -------------------------------------------------------

  @Value("${x3platform." + SECTION_NAME + ".single-sign-on:off}")
  private String singleSignOn;

  /**
   * 单点登录
   */
  public String getSingleSignOn() {
    return singleSignOn;
  }

  // -------------------------------------------------------
  // 密码
  // -------------------------------------------------------

  @Value("${x3platform." + SECTION_NAME + ".password-encryption:None}")
  private String mPasswordEncryption;

  public String getPasswordEncryption() {
    return mPasswordEncryption;
  }

  @Value("${x3platform." + SECTION_NAME + ".password-encryption-secret:000000000000000000000000000000}")
  private String mPasswordEncryptionSecret;

  public String getPasswordEncryptionKey() {
    return mPasswordEncryption;
  }

  @Value("${x3platform." + SECTION_NAME + ".password-encryption-iv:00000000}")
  private String mPasswordEncryptionIV;

  public String getPasswordEncryptionIV() {
    return mPasswordEncryptionIV;
  }

  @Value("${x3platform." + SECTION_NAME + ".password-policy-rules:None}")
  private String mPasswordPolicyRules;

  public String getPasswordPolicyRules() {
    return mPasswordPolicyRules;
  }

  @Value("${x3platform." + SECTION_NAME + ".password-policy-minimum-length:0}")
  private String mPasswordPolicyMinimumLength;

  /**
   * 密码最小长度规则
   */
  public String getPasswordPolicyMinimumLength() {
    return mPasswordPolicyMinimumLength;
  }

  @Value("${x3platform." + SECTION_NAME + ".password-policy-character-repeated-times:0}")
  private String mPasswordPolicyCharacterRepeatedTimes;

  /**
   * 密码相邻字符重复次数规则
   */
  public String getPasswordPolicyCharacterRepeatedTimes() {
    return mPasswordPolicyCharacterRepeatedTimes;
  }

  @Value("${x3platform." + SECTION_NAME + ".avatar-physical-folder:uploads/avatar}")
  private String avatarPhysicalFolder;

  /**
   * 帐号头像文件夹物理路径
   */
  public String getAvatarPhysicalFolder() {
    return avatarPhysicalFolder;
  }

  @Value("${x3platform." + SECTION_NAME + ".avatar-virtual-folder:/uploads/avatar}")
  private String avatarVirtualFolder;

  /**
   * 帐号头像文件夹虚拟路径
   */
  public String getAvatarVirtualFolder() {
    return avatarVirtualFolder;
  }

  @Value("${x3platform." + SECTION_NAME + ".account-identity-cookie-token:X_SESSION_TOKEN}")
  private String accountIdentityCookieToken;

  /**
   * 会话票据标识
   */
  public String getAccountIdentityCookieToken() {
    return accountIdentityCookieToken;
  }

  @Value("${x3platform." + SECTION_NAME + ".account-grant-offset-days:-1}")
  private String accountGrantOffsetDays;

  /**
   * 用户委托数据偏移天数
   */
  public String getAccountGrantOffsetDays() {
    return accountGrantOffsetDays;
  }

  // -------------------------------------------------------
  // 默认值
  // -------------------------------------------------------

  @Value("${x3platform." + SECTION_NAME + ".default-registration:mail}")
  private String defaultRegistration;

  /**
   * 默认注册方式
   */
  public String getDefaultRegistration() {
    return defaultRegistration;
  }

  @Value("${x3platform." + SECTION_NAME + ".default-password:{RANDOM}}")
  private String defaultPassword;

  /**
   * 默认初始密码，一般适用于系统新建帐号时初始的密码。
   */
  public String getDefaultPassword() {
    return defaultPassword;
  }

  @Value("${x3platform." + SECTION_NAME + ".default-organization-id:000000000000000000000000000000}")
  private String defaultOrganizationId;

  /**
   * 默认的组织标识
   */
  public String getDefaultOrganizationId() {
    return defaultOrganizationId;
  }

  @Value("${x3platform." + SECTION_NAME + ".default-standard-role-id:000000000000000000000000000000}")
  private String defaultStandardRoleId;

  /**
   * 默认的标准角色标识
   */
  public String getDefaultStandardRoleId() {
    return defaultStandardRoleId;
  }

  @Value("${x3platform." + SECTION_NAME + ".default-role-id:000000000000000000000000000000}")
  private String defaultRoleId;

  /**
   * 默认的角色标识
   */
  public String getDefaultRoleId() {
    return defaultRoleId;
  }

  @Value("${x3platform." + SECTION_NAME + ".default-scope-text:Role#00000000-0000-0000-0000-000000000000#所有人}")
  private String defaultScopeText;

  /**
   * 默认的权限范围
   */
  public String getDefaultScopeText() {
    return defaultScopeText;
  }

  @Value("${x3platform." + SECTION_NAME + ".prohibited-preview-objects:}")
  private String prohibitedPreviewObjects;

  /**
   * 通讯录禁止预览的对象
   */
  public String getProhibitedPreviewObjects() {
    return prohibitedPreviewObjects;
  }

  // -------------------------------------------------------
  // 其他
  // -------------------------------------------------------

  /**
   * 设置默认系统跟人员关系， 默认角色为普通访问者(审查员、管理员需要手动配置 或者系统初始化)
   */
  @Value("${x3platform." + SECTION_NAME + ".default-bind-relationship:off}")
  public String mRelationShip;

  public String getRelationShip() {
    return mRelationShip;
  }

  /**
   * 设置默认系统跟人员关系， 默认角色为普通访问者(审查员、管理员需要手动配置 或者系统初始化)
   */
  @Value("${x3platform." + SECTION_NAME + ".default-bind-relationship-management:ami-ocr-edge}")
  public String mRelationshipManagement;

  public String getRelationshipManagement() {
    return mRelationshipManagement;
  }
}
