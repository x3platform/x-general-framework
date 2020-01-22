package com.x3platform.security.passwords;

/**
 * 密码加密管理器
 *
 * @author ruanyu
 */
public interface PasswordEncryptionManagement {
  /**
   * 获取名称
   *
   * @return 管理器名称
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
