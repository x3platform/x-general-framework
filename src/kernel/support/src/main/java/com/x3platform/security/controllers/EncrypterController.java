package com.x3platform.security.controllers;

import com.alibaba.fastjson.JSONObject;
import com.x3platform.messages.MessageObject;
import com.x3platform.globalization.I18n;
import com.x3platform.security.CiphertextFormat;
import com.x3platform.security.Encrypter;
import com.x3platform.security.configuration.SecurityConfigurationView;
import com.x3platform.util.*;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author ruanyu
 */
@Lazy
@RestController
@RequestMapping("/api/sys/security/encrypter")
public class EncrypterController {

  /**
   * SHA-1 方式加密
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/encryptSha1")
  public String encryptSha1(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String text = req.getString("text");

    return "{\"data\":" + JSON.toJSONString(Encrypter.encryptSha1(text)) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * AES 方式加密
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/encryptAes")
  public String encryptAes(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String text = req.getString("text");
    String key = req.getString("key");
    String iv = req.getString("iv");
    String formatText = req.getString("format");

    if (StringUtil.isNullOrEmpty(key)) {
      key = SecurityConfigurationView.getInstance().getAesCryptoKey();
    }

    if (StringUtil.isNullOrEmpty(iv)) {
      iv = SecurityConfigurationView.getInstance().getAesCryptoIv();
    }

    if (StringUtil.isNullOrEmpty(formatText)) {
      formatText = SecurityConfigurationView.getInstance().getAesCryptoCiphertextFormat();
    }

    CiphertextFormat format = Enum.valueOf(CiphertextFormat.class, formatText);

    return "{\"data\":" + JSON.toJSONString(Encrypter.encryptAes(text, key, iv, format)) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 生成 RSA 加密的公钥和私钥
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/generateRsaKeyPair")
  public String generateRsaKeyPair(@RequestBody String data) {
    Map<String, String> keyMap = Encrypter.generateRsaKeyPair();

    return "{\"data\":" + JSON.toJSONString(keyMap) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }

  /**
   * 获取默认的 RSA 公钥
   *
   * @param data 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/getRsaPublicKey")
  public String getRsaPublicKey(@RequestBody String data) {
    return "{\"data\":" + JSON.toJSONString(Encrypter.getDefaultRsaPublicKey()) + ","
      + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
