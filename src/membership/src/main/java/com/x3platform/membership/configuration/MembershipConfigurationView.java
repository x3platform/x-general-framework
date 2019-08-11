package com.x3platform.membership.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 人员及权限的配置信息视图
 */

@Configuration
public class MembershipConfigurationView {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static volatile MembershipConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static MembershipConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          // instance = new DigitalNumberConfigurationView();
          instance = SpringContext.getBean(MembershipConfigurationView.class);
        }
      }
    }

    return instance;
  }

  public void reload() {
    instance = null;
  }

  @Autowired
  @Qualifier("com.x3platform.membership.configuration.MembershipConfiguration")
  MembershipConfiguration configuration;

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

  public boolean getSingleSignOn() {
    return BooleanUtil.bool(configuration.getSingleSignOn());
  }

  public String getCacheRedis() {  return configuration.getCacheRedis(); }

  public String getRelationShip() {  return configuration.getRelationShip(); }

  public String getRelationshipManagement() {  return configuration.getRelationshipManagement(); }
}
