package com.x3platform.tasks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 人员及权限的配置信息
 */
@Component("com.x3platform.tasks.configuration.TasksConfiguration")
public class TasksConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String ApplicationName = "Tasks";

  /**
   * 配置区的名称
   */
  public static final String SectionName = "tasks";

  @Value("${x3platform." + SectionName + ".password-encryption:None}")
  private String mPasswordEncryption;

  public String getPasswordEncryption() {
    return mPasswordEncryption;
  }

  @Value("${x3platform." + SectionName + ".password-encryption-secret:000000000000000000000000000000}")
  private String mPasswordEncryptionSecret;

  public String getPasswordEncryptionKey() {
    return mPasswordEncryption;
  }

  @Value("${x3platform." + SectionName + ".password-encryption-iv:00000000}")
  private String mPasswordEncryptionIV;

  public String getPasswordEncryptionIV() {
    return mPasswordEncryptionIV;
  }

  @Value("${x3platform." + SectionName + ".password-policy-rules:None}")
  private String mPasswordPolicyRules;

  public String getPasswordPolicyRules() {
    return mPasswordPolicyRules;
  }

  @Value("${x3platform." + SectionName + ".password-policy-minimum-length:0}")
  private String mPasswordPolicyMinimumLength;

  /**
   * 密码最小长度规则
   */
  public String getPasswordPolicyMinimumLength() {
    return mPasswordPolicyMinimumLength;
  }

  @Value("${x3platform." + SectionName + ".password-policy-character-repeated-times:0}")
  private String mPasswordPolicyCharacterRepeatedTimes;

  /**
   * 密码相邻字符重复次数规则
   */
  public String getPasswordPolicyCharacterRepeatedTimes() {
    return mPasswordPolicyCharacterRepeatedTimes;
  }

  @Value("${x3platform." + SectionName + ".avatar-virtual-folder:/uploads/avatar}")
  private String mAvatarVirtualFolder;

  /**
   * 帐号头像文件夹虚拟路径
   */
  public String getAvatarVirtualFolder() {
    return mAvatarVirtualFolder;
  }


}
