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

  @Value("${x3platform." + SECTION_NAME + ".avatar-virtual-folder:/uploads/avatar}")
  private String mAvatarVirtualFolder;

  /**
   * 帐号头像文件夹虚拟路径
   */
  public String getAvatarVirtualFolder() {
    return mAvatarVirtualFolder;
  }

  @Value("${x3platform." + SECTION_NAME + ".account-identity-cookie-token:X_SESSION_TOKEN}")
  private String mAccountIdentityCookieToken;

  /**
   * 成员标识
   */
  public String getAccountIdentityCookieToken() {
    return mAccountIdentityCookieToken;
  }

  @Value("${x3platform." + SECTION_NAME + ".single-sign-on:off}")
  private String mSingleSignOn;

  /**
   * 单点登录
   */
  public String getSingleSignOn() {
    return mSingleSignOn;
  }

  /**
   * 设置二级缓存 redis
   */
  @Value("${x3platform." + SECTION_NAME + ".cache-redis:off}")
  public String mCacheRedis;

  public String getCacheRedis() {
    return mCacheRedis;
  }

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
