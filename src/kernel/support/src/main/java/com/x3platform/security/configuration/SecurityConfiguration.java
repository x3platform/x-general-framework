package com.x3platform.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 安全配置信息
 */
@Lazy
@Component("com.x3platform.security.configuration.SecurityConfiguration")
public class SecurityConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "security";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "security";

  @Value("${x3platform." + SECTION_NAME + ".sha1-crypto-ciphertext-format:HEX_STRING_WHITOUT_HYPHEN}")
  public String sha1CryptoCiphertextFormat;

  /**
   * SHA1 加密方式的默认密文格式
   */
  public String getSha1CryptoCiphertextFormat() {
    return sha1CryptoCiphertextFormat;
  }

  @Value("${x3platform." + SECTION_NAME + ".aes-crypto-key:1234567812345678}")
  public String aesCryptoKey;

  /**
   * AES 加密方式的默认密钥 长度为 16 位内容
   */
  public String getAesCryptoKey() {
    return aesCryptoKey;
  }

  @Value("${x3platform." + SECTION_NAME + ".aes-crypto-iv:1234567812345678}")
  public String aesCryptoIv;

  /**
   * AES 加密方式的默认初始化向量 长度为 16 位内容
   */
  public String getAesCryptoIv() {
    return aesCryptoIv;
  }

  @Value("${x3platform." + SECTION_NAME + ".aes-crypto-ciphertext-format:BASE64_STRING}")
  public String aesCryptoCiphertextFormat;

  /**
   * AES 加密方式的默认密文格式
   */
  public String getAesCryptoCiphertextFormat() {
    return aesCryptoCiphertextFormat;
  }

  @Value("${x3platform." + SECTION_NAME + ".rsa-crypto-public-key:auto}")
  public String rsaCryptoPublicKey;

  /**
   * RSA 加密方式的公钥信息
   */
  public String getRsaCryptoPublicKey() {
    return rsaCryptoPublicKey;
  }

  @Value("${x3platform." + SECTION_NAME + ".rsa-crypto-private-key:auto}")
  public String rsaCryptoPrivateKey;

  /**
   * RSA 加密方式的私钥信息
   */
  public String getRsaCryptoPrivateKey() {
    return rsaCryptoPrivateKey;
  }

  @Value("${x3platform." + SECTION_NAME + ".rsa-crypto-ciphertext-format:BASE64_STRING}")
  public String rsaCryptoCiphertextFormat;

  /**
   * RSA 加密方式的默认密文格式
   */
  public String getRsaCryptoCiphertextFormat() {
    return rsaCryptoCiphertextFormat;
  }
}
