package com.x3platform.security.jasypt;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.x3platform.security.CiphertextFormat;
import com.x3platform.security.Encrypter;

public class CustomEncryptablePropertyResolver implements EncryptablePropertyResolver {

  private String key = "1234567812345678";

  public void setKey(String value) {
    key = value;
  }

  private String iv = "1234567812345678";

  public void setIv(String value) {
    iv = value;
  }

  private CiphertextFormat format = CiphertextFormat.HEX_STRING_WHITOUT_HYPHEN;

  public void setFormat(CiphertextFormat value) {
    format = value;
  }

  /**
   * 自定义解密方法
   */
  @Override
  public String resolvePropertyValue(String s) {
    if (null != s && s.startsWith(CustomEncryptablePropertyDetector.ENCODED_PASSWORD_HINT)) {
      // 解密
      // return Encrypter.decryptAes(s.substring(CustomEncryptablePropertyDetector.ENCODED_PASSWORD_HINT.length()));
      return Encrypter.decryptAes(s.substring(CustomEncryptablePropertyDetector.ENCODED_PASSWORD_HINT.length()),
        key,iv , format);
    }
    return s;
  }
}
