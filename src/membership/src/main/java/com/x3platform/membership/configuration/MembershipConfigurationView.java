package com.x3platform.membership.configuration;

import com.x3platform.InternalLogger;
import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
import com.x3platform.util.StringUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 人员及权限的配置信息视图
 */

@Component
public class MembershipConfigurationView {

  private final Logger logger = InternalLogger.getLogger();

  private static volatile MembershipConfigurationView sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static MembershipConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(MembershipConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  public void reload() {
    sInstance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.membership.configuration.MembershipConfiguration")
  MembershipConfiguration configuration;

  public boolean getSingleSignOn() {
    return BooleanUtil.bool(configuration.getSingleSignOn());
  }

  public String getPasswordEncryption() {
    return configuration.getPasswordEncryption();
  }


  public String getPasswordPolicyRules() {
    return configuration.getPasswordPolicyRules();
  }

  public int getPasswordPolicyCharacterRepeatedTimes() {
    return Integer.parseInt(configuration.getPasswordPolicyCharacterRepeatedTimes());
  }

  public int getPasswordPolicyMinimumLength() {
    return Integer.parseInt(configuration.getPasswordPolicyMinimumLength());
  }

  public String getAvatarVirtualFolder() {
    return configuration.getAvatarVirtualFolder();
  }

  public String getAccountIdentityCookieToken() {
    return configuration.getAccountIdentityCookieToken();
  }

  /**
   * 默认注册方式
   */
  public String getDefaultRegistration() {
    return configuration.getDefaultRegistration();
  }

  /**
   * 默认的初始密码
   */
  public String getDefaultPassword() {
    if ("{RANDOM}".equals(configuration.getDefaultPassword())) {
      return StringUtil.toRandom("0123456789", 8);
    } else {
      return configuration.getDefaultPassword();
    }
  }

  /**
   * 默认的组织标识
   */
  public String getDefaultOrganizationId() {
    return configuration.getDefaultOrganizationId();
  }

  /**
   * 默认的标准角色标识
   */
  public String getDefaultStandardRoleId() {
    return configuration.getDefaultStandardRoleId();
  }

  /**
   * 默认的角色标识
   */
  public String getDefaultRoleId() {
    return configuration.getDefaultRoleId();
  }

  /**
   * 默认的权限范围
   */
  public String getDefaultScopeText() {
    return configuration.getDefaultScopeText();
  }

  /**
   * 通讯录禁止预览的对象
   */
  public String getProhibitedPreviewObjects() {
    return configuration.getProhibitedPreviewObjects();
  }

  public String getRelationShip() {  return configuration.getRelationShip(); }

  public String getRelationshipManagement() {  return configuration.getRelationshipManagement(); }
}
