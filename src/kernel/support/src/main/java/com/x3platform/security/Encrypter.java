package com.x3platform.security;

import static com.x3platform.security.CiphertextFormat.*;

import com.x3platform.InternalLogger;
import com.x3platform.security.configuration.SecurityConfigurationView;
import com.x3platform.util.ByteUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;

/**
 * 加密器
 *
 * @author ruanyu
 */
public class Encrypter {

  /**
   * 日志
   */
  private static final Logger logger = InternalLogger.getLogger();

  private static final String CHARSET_NAME = "UTF-8";

  private static final String AES_NAME = "AES";
  private static final String AES_ALGORITHM = "AES/CBC/PKCS7Padding";

  private static final String RSA_NAME = "RSA";
  private static final String RSA_ALGORITHM = "RSA";

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  /**
   * 将字符串数组排序后拼接成一个文本信息
   *
   * @param values 任意多个文本信息
   * @return 拼接后的文本
   */
//  public static String SortAndConcat(String... values) {
//    ArrayList list = new ArrayList(values);
//
//    Collections.sort(list);
//
//    return String.Concat(list.toArray(new Object[0]));
//  }

  /**
   * 将加密后的二进制数据转为某种格式的文本信息
   *
   * @param result 加密后的二进制结果数据
   * @param format 密文格式
   * @return 格式化后的的加密文本
   */
  public static String toCiphertext(byte[] result, CiphertextFormat format) {
    switch (format) {
      case BASE64_STRING:
        return Base64.getEncoder().encodeToString(result);

      case HEX_STRING:
        return ByteUtil.toHexString(result).toLowerCase();

      case HEX_STRING_WHITOUT_HYPHEN:
        return ByteUtil.toHexString(result).replace("-", "").toLowerCase();

      default:
        return "";
    }
  }

  /**
   * 将某种格式的密文转为加密后的二进制数据
   *
   * @param ciphertext 密文
   * @param format 密文格式
   * @return 加密后的二进制数据
   */
  public static byte[] fromCiphertext(String ciphertext, CiphertextFormat format) {
    byte[] result = null;

    if (format == BASE64_STRING) {
      return Base64.getDecoder().decode(ciphertext);
    } else if (format == HEX_STRING) {
      result = ByteUtil.fromHexString(ciphertext);
//      String[] data = ciphertext.split("[-]", -1);
//
//      result = new byte[data.length];
//
//      Int32Converter converter = new Int32Converter();
//
//      for (int i = 0; i < data.length; i++) {
//        result[i] = Byte.parseByte(converter.ConvertFromInvariantString(String.format("0x%1$s", data[i])).toString());
//      }
    } else if (format == HEX_STRING_WHITOUT_HYPHEN) {
      result = ByteUtil.fromHexString(ciphertext);
//      result = new byte[ciphertext.length() / 2];

//      Int32Converter converter = new Int32Converter();
//
//      for (int i = 0; i < ciphertext.length(); i = i + 2) {
//        result[i / 2] = (Byte) converter
//          .ConvertFromInvariantString(String.format("0x%1$s", ciphertext.substring(i, i + 2)).toString());
//      }
    }

    return result;
  }

  //-------------------------------------------------------
  // SHA1 - Secure Hash Standard 1
  // https://zh.wikipedia.org/wiki/SHA-1
  //-------------------------------------------------------

  /**
   * 加密-SHA1方式
   *
   * @param text 文本
   * @return 加密后的文本
   */
  public static String encryptSha1(String text) {
    CiphertextFormat format = Enum.valueOf(CiphertextFormat.class,
      SecurityConfigurationView.getInstance().getSha1CryptoCiphertextFormat());

    return encryptSha1(text, format);
  }

  /**
   * 加密-SHA1方式
   *
   * @param text 文本
   * @param format 密文格式
   * @return 加密后的文本
   */
  public static String encryptSha1(String text, CiphertextFormat format) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");

      byte[] byteArray = text.getBytes(CHARSET_NAME);

      byte[] result = sha.digest(byteArray);

      return toCiphertext(result, format);

    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      logger.error(e.toString());
    }

    return null;
  }

  //-------------------------------------------------------
  // ASE - The Advanced encryption Standard
  // http://en.wikipedia.org/wiki/Advanced_encryption_Standard
  //-------------------------------------------------------
  //
  // 默认的密钥 长度
  //
  // 16位的字符串 => 128位 (1900 01 01 00 00 00 00)
  // 24位的字符串 => 192位
  // 32位的字符串 => 256位 (默认)
  //
  //-------------------------------------------------------
  // CryptoJS ASE 加密方式
  // CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(text),
  //   CryptoJS.enc.Utf8.parse(key),
  //   {
  //     keySize: 128 / 8,
  //     iv: CryptoJS.enc.Utf8.parse(iv),
  //     mode: CryptoJS.mode.CBC,
  //     padding: CryptoJS.pad.Pkcs7
  //   }).toString();
  //-------------------------------------------------------

  /**
   * 加密-AES方式
   *
   * @param text 文本
   * @return 加密后的文本
   */
  public static String encryptAes(String text) {
    return encryptAes(text, SecurityConfigurationView.getInstance().getAesCryptoKey());
  }

  /**
   * 加密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @return 加密后的文本
   */
  public static String encryptAes(String text, String key) {
    CiphertextFormat format = Enum.valueOf(CiphertextFormat.class,
      SecurityConfigurationView.getInstance().getAesCryptoCiphertextFormat());

    return encryptAes(text, key, format);
  }

  /**
   * 加密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @param format 密文格式
   * @return 加密后的文本
   */
  public static String encryptAes(String text, String key, CiphertextFormat format) {
    String iv = SecurityConfigurationView.getInstance().getAesCryptoIv();

    return encryptAes(text, key, iv, format);
  }

  /**
   * 加密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @param iv 初始化向量
   * @return 加密后的文本
   */
  public static String encryptAes(String text, String key, String iv, CiphertextFormat format) {
    try {
      return encryptAes(text, key.getBytes(CHARSET_NAME), iv.getBytes(CHARSET_NAME), format);
    } catch (UnsupportedEncodingException e) {
      logger.error(e.toString());
    }
    return null;
  }

  /**
   * 加密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @param iv 初始化向量
   * @param format 密文格式
   * @return 加密后的文本
   */
  public static String encryptAes(String text, byte[] key, byte[] iv, CiphertextFormat format) {
    try {
      // 设置密文生成器 算法/模式/补码方式
      Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
      // 设置 Key
      Key secretKeySpec = new SecretKeySpec(key, AES_NAME);
      // 设置 IV
      AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      // 初始化
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
      // 执行
      byte[] buffer = text.getBytes(CHARSET_NAME);
      byte[] result = cipher.doFinal(buffer);
      // 输出密文
      return toCiphertext(result, format);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException
      | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
      logger.error(e.toString());
    }

    return null;
  }

  /**
   * 解密-AES方式
   *
   * @param text 文本
   */
  public static String decryptAes(String text) {
    return decryptAes(text, SecurityConfigurationView.getInstance().getAesCryptoKey());
  }

  /**
   * 解密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @return 解密后的文本
   */
  public static String decryptAes(String text, String key) {
    String iv = SecurityConfigurationView.getInstance().getAesCryptoIv();

    CiphertextFormat format = Enum.valueOf(CiphertextFormat.class,
      SecurityConfigurationView.getInstance().getAesCryptoCiphertextFormat());

    return decryptAes(text, key, iv, format);
  }

  /**
   * 解密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @param iv 初始化向量
   * @return 解密后的文本
   */
  public static String decryptAes(String text, String key, String iv, CiphertextFormat format) {
    try {
      return decryptAes(text, key.getBytes(CHARSET_NAME), iv.getBytes(CHARSET_NAME), format);
    } catch (UnsupportedEncodingException e) {
      logger.error(e.toString());
    }

    return null;
  }

  /**
   * 解密-AES方式
   *
   * @param text 文本
   * @param key 密钥
   * @param iv 初始化向量
   * @return 解密后的文本
   */
  public static String decryptAes(String text, byte[] key, byte[] iv, CiphertextFormat format) {
    try {
      // 设置密文生成器 算法/模式/补码方式
      Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
      // 设置 Key
      Key secretKeySpec = new SecretKeySpec(key, AES_NAME);
      // 设置 IV
      AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      // 初始化密文生成器
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
      // 计算结果
      byte[] result = cipher.doFinal(fromCiphertext(text, format));
      // 输出密文
      return new String(result);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
      | BadPaddingException | InvalidAlgorithmParameterException e) {
      logger.error(e.toString());
    }

    return null;
  }

  // -------------------------------------------------------
  // SHA1 - Secure Hash Standard 1
  // https://en.wikipedia.org/wiki/RSA_(cryptosystem)
  // -------------------------------------------------------

  /**
   * 内置的随机密钥对
   */
  private static Map<String, String> rsaKeyPair = new HashMap<String, String>();

  /**
   * 随机生成密钥对
   */
  public static Map<String, String> generateRsaKeyPair() {
    try {
      //用于封装随机产生的公钥与私钥
      Map<String, String> keyMap = new HashMap<String, String>();
      // KeyPairGenerator 类用于生成公钥和私钥对，基于 RSA 算法生成对象。
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_NAME);
      // 初始化密钥对生成器，密钥大小为96-1024 位。
      keyPairGen.initialize(1024, new SecureRandom());
      // 生成一个密钥对，保存在 KeyPair 中。
      KeyPair keyPair = keyPairGen.generateKeyPair();
      // 得到公钥
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      // 得到私钥
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      // 得到公钥字符串
      String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
      // 得到私钥字符串
      String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
      // 将公钥和私钥保存到 Map
      // publicKey 表示公钥
      keyMap.put("publicKey", publicKeyString);
      // privateKey 表示私钥
      keyMap.put("privateKey", privateKeyString);

      return keyMap;
    } catch (NoSuchAlgorithmException e) {
      logger.error("Encrypter.generateKeyPair()", e);
    }

    return null;
  }

  public static String getDefaultRsaPublicKey() {
    String publicKey = SecurityConfigurationView.getInstance().getRsaCryptoPublicKey();

    if ("auto".equalsIgnoreCase(publicKey)) {
      if (rsaKeyPair.size() == 0) {
        rsaKeyPair = generateRsaKeyPair();
      }
      // 如果生成成功输出公钥
      if (rsaKeyPair != null) {
        publicKey = rsaKeyPair.get("publicKey");
      }
    }

    return publicKey;
  }

  public static String encryptRsa(String text) {
    return encryptRsa(text, getDefaultRsaPublicKey());
  }

  public static String encryptRsa(String text, String publicKey) {
    CiphertextFormat format = Enum.valueOf(CiphertextFormat.class,
      SecurityConfigurationView.getInstance().getRsaCryptoCiphertextFormat());

    return encryptRsa(text, publicKey, format);
  }

  /**
   * 加密-RSA 公钥加密
   *
   * @param text 文本
   * @param publicKey 公钥
   * @param format 密文格式
   * @return 加密后的文本
   */
  public static String encryptRsa(String text, String publicKey, CiphertextFormat format) {
    try {
      // 设置密文生成器
      Cipher cipher = Cipher.getInstance(RSA_NAME);
      // 设置公钥
      KeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
      // Key Type: RSAPublicKey
      Key key = KeyFactory.getInstance(RSA_NAME).generatePublic(keySpec);
      // 初始化密文生成器
      cipher.init(Cipher.ENCRYPT_MODE, key);
      // 计算结果
      byte[] result = cipher.doFinal(text.getBytes(CHARSET_NAME));
      // 输出加密后的文本
      return toCiphertext(result, format);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
      | BadPaddingException | InvalidKeySpecException | UnsupportedEncodingException e) {
      logger.error(e.toString());
    }

    return null;
  }

  public static String decryptRsa(String text) {
    String privateKey = SecurityConfigurationView.getInstance().getRsaCryptoPrivateKey();

    if ("auto".equalsIgnoreCase(privateKey)) {
      if (rsaKeyPair.size() == 0) {
        rsaKeyPair = generateRsaKeyPair();
      }
      // 如果生成成功输出私钥
      if (rsaKeyPair != null) {
        privateKey = rsaKeyPair.get("privateKey");
      }
    }

    return decryptRsa(text, privateKey);
  }

  public static String decryptRsa(String text, String privateKey) {
    CiphertextFormat format = Enum.valueOf(CiphertextFormat.class,
      SecurityConfigurationView.getInstance().getRsaCryptoCiphertextFormat());

    return decryptRsa(text, privateKey, format);
  }

  /**
   * 解密-RSA 私钥解密
   *
   * @param text 文本
   * @param privateKey 私钥
   * @param format 密文格式
   * @return 解密后的文本
   */
  public static String decryptRsa(String text, String privateKey, CiphertextFormat format) {
    try {
      // 设置密文生成器
      Cipher cipher = Cipher.getInstance(RSA_NAME);
      // 设置私钥
      KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
      // Key Type: RSAPrivateKey
      Key key = KeyFactory.getInstance(RSA_NAME).generatePrivate(keySpec);
      // 初始化密文生成器
      cipher.init(Cipher.DECRYPT_MODE, key);
      // 计算结果
      byte[] result = cipher.doFinal(fromCiphertext(text, format));
      // 输出解密后的文本
      return new String(result);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
      | BadPaddingException | InvalidKeySpecException e) {
      logger.error(e.toString());
    }

    return null;
  }
}
