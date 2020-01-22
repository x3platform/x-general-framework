package com.x3platform.security;

/**
 * 加密文本格式
 *
 * @author ruanyu
 */
public enum CiphertextFormat {

  /**
   * Base64 文本信息
   */
  BASE64_STRING,

  /**
   * 十六进制文本信息
   */
  HEX_STRING,

  /**
   * 没有连字符的十六进制文本信息
   */
  HEX_STRING_WHITOUT_HYPHEN
}
