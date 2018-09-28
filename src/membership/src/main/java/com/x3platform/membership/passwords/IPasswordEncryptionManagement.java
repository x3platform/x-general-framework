package com.x3platform.membership.passwords;

/**
 * 密码加密类型
 */
public interface IPasswordEncryptionManagement {
  /**
   * 名称
   */
  String getName();

  /**
   * 解密密码
   *
   * @param encryptedPassword 加密的密码
   * @return 密码
   */
  String decrypt(String encryptedPassword);

  /**
   * 加密密码
   *
   * @param unencryptedPassword 未加密的密码
   * @return 加密的密码
   */
  String encrypt(String unencryptedPassword);
}
