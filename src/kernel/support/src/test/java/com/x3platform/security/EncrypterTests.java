package com.x3platform.security;

import static com.x3platform.security.CiphertextFormat.BASE64_STRING;
import static com.x3platform.security.CiphertextFormat.HEX_STRING_WHITOUT_HYPHEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.security.configuration.SecurityConfigurationView;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncrypterTests {

  @Test
  public void testEncryptSha1() {
    // 验证数字
    String ciphertext = Encrypter.encryptSha1("12345");

    assertEquals("8cb2237d0679ca88db6464eac60da96345513964", ciphertext);

    // 验证英文
    ciphertext = Encrypter.encryptSha1("abcde");

    assertEquals("03de6c570bfe24bfc328ccd7ca46b76eadaf4334", ciphertext);

    // 验证中文字符
    ciphertext = Encrypter.encryptSha1("中文数据校验");

    assertEquals("10b5eda71a6464ff3135076856311e0248224f4b", ciphertext);

    // 验证混合
    ciphertext = Encrypter.encryptSha1("12345abcde中文数据校验");

    assertEquals("51367648c0adce27cb2dd50bf79fe54a9e64702c", ciphertext);
  }

  @Test
  public void testEncryptAes() {
    try {
      byte[] key = "1234567812345678".getBytes("UTF-8");
      byte[] iv = "1234567812345678".getBytes("UTF-8");

      String text = "123456";

      // 加密 BASE64_STRING
      String ciphertext = Encrypter.encryptAes(text, key, iv, BASE64_STRING);

      assertEquals("2eDiseYiSX62qk/WS/ZDmg==", ciphertext);

      // 加密 HEX_STRING_WHITOUT_HYPHEN
      ciphertext = Encrypter.encryptAes(text, key, iv, HEX_STRING_WHITOUT_HYPHEN);

      assertEquals("d9e0e2b1e622497eb6aa4fd64bf6439a", ciphertext);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDecryptAes() {
    try {
      byte[] key = "1234567812345678".getBytes("UTF-8");
      byte[] iv = "1234567812345678".getBytes("UTF-8");

      String text = "123456";

      // 解密 BASE64_STRING
      String result = Encrypter.decryptAes("2eDiseYiSX62qk/WS/ZDmg==", key, iv, BASE64_STRING);

      assertEquals(text, result);

      // 解密 HEX_STRING_WHITOUT_HYPHEN
      result = Encrypter.decryptAes("d9e0e2b1e622497eb6aa4fd64bf6439a", key, iv, HEX_STRING_WHITOUT_HYPHEN);

      assertEquals(text, result);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEncryptRsa() {
    String publicKey =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1tIhUugC4FbiOfhVjvHGMGOqnVUfPQtyg8LFUaJaUwmiDo/OsOk/rsAKtf7oO4rocxknUxRicuxWOTfqjl3IqM+538H+t6A7GmJIA332+NgfEV5pSEMR9Ww+rQKxRJsHSMKbMN/DKFp0RQ1Hg2JwrEu2WKyKnquJVNJEDdJKJCQIDAQAB";
    String privateKey =
      "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALW0iFS6ALgVuI5+FWO8cYwY6qdVR89C3KDwsVRolpTCaIOj86w6T+uwAq1/ug7iuhzGSdTFGJy7FY5N+qOXcioz7nfwf63oDsaYkgDffb42B8RXmlIQxH1bD6tArFEmwdIwpsw38MoWnRFDUeDYnCsS7ZYrIqeq4lU0kQN0kokJAgMBAAECgYEAgSpLO0dZmYI/RnOUSui7IqbXr4ms2UWjRniy5djPKgIkf2d9oTF2fIkK7kB8h3ZebHP8KdbNzyQih50hDUdr7CVOLl55MJNzg+ab0rw9ZIukBPTN/zhobi7O+gwpA7xLU0iTPJ5A112Ehw9CnUq0f/nS/nvxFPOuylbKy5JRae0CQQDzVqCF8aqYrBzNb9+M4VSPT8Ac70y9B4tNxFv4yN0CpUxcvcCQ3vFAFZTydfpHT6DMp55lTW17CgP+4TXkzl/DAkEAvyjsvTV5xnxP+yEGFUpfQoSlJJxqKlWeNNo854rq0oq5Rf27xfGyg2OYDmcxWqPyZjdqR6mUkOaZTsKWtXwTQwJAKl5z9r58WLbnEPIgt3PoeEeHiMI111f/7lt5NBktOi+z2xYC+HaJBQJ8+7aFDHOV6OxqjPLtVBYLWg62ho9UZwJBAKpCBhFDxtFtOUNgF23xDsVNJVVlZv2LwlwqoKJNzXZ2jivzoUHdUvTJSM5TRNcJPMWjx6pKNQOWD6fhkp/UAccCQGTabdIUAcyN+rEzplaf+ZExDa90i2EWsT6UPConsauN62a1nNTlNjNPx5PPywuBGuksTJ7sJRNKv6D8p7Xsr0M=";

    String text = "123456";

    // 加密
    // RSA 加密方式相同的内容每次密文不一样，所以不做密文一致性检测。
    String ciphertext = Encrypter.encryptRsa(text, publicKey);

    assertNotNull(ciphertext);
    assertNotEquals(text, ciphertext);

    // 解密
    String result = Encrypter.decryptRsa(ciphertext, privateKey);

    assertEquals(text, result);
  }
}
