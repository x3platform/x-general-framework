package com.x3platform.security.configuration;

import com.x3platform.InternalLogger;
import com.x3platform.SpringContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 核心的配置信息
 */
@Component
public class SecurityConfigurationView {

  private final Logger logger = InternalLogger.getLogger();

  private static volatile SecurityConfigurationView sInstance = null;

  private static final Object lockObject = new Object();

  /**
   * 实例
   */
  public static SecurityConfigurationView getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = SpringContext.getBean(SecurityConfigurationView.class);
        }
      }
    }

    return sInstance;
  }

  public void reload() {
    sInstance = null;
    logger.info("SecurityConfigurationView reloaded.");
  }

  @Autowired(required = false)
  @Qualifier("com.x3platform.security.configuration.SecurityConfiguration")
  SecurityConfiguration configuration;

  /**
   * SHA1 加密方式的默认密文格式
   */
  public String getSha1CryptoCiphertextFormat() {
    return configuration.getSha1CryptoCiphertextFormat();
  }

  /**
   * AES 加密方式的默认密钥 长度为 16 24 32 位内容
   */
  public String getAesCryptoKey() {
    return configuration.getAesCryptoKey();
  }

  /**
   * AES 加密方式的默认密钥 长度为 16 24 32 位内容
   */
  public String getAesCryptoIv() {
    return configuration.getAesCryptoIv();
  }

  /**
   * AES 加密方式的默认密文格式
   */
  public String getAesCryptoCiphertextFormat() {
    return configuration.getAesCryptoCiphertextFormat();
  }

  /**
   * RSA 加密方式的公钥信息
   */
  public String getRsaCryptoPublicKey() {
    return configuration.getRsaCryptoPublicKey();
  }

  /**
   * RSA 加密方式的私钥信息
   */
  public String getRsaCryptoPrivateKey() {
    return configuration.getRsaCryptoPrivateKey();
  }

  /**
   * RSA 加密方式的默认密文格式
   */
  public String getRsaCryptoCiphertextFormat() {
    return configuration.getRsaCryptoCiphertextFormat();
  }
}
