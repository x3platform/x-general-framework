package com.x3platform.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 流处理辅助类
 */
public class StreamUtil {

  /**
   * 将流保存到文件
   */
  public static int toFile(InputStream inputStream, String path) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(path);

    int bytesRead = 0;

    byte[] buffer = new byte[1024];

    while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }

    outputStream.close();
    inputStream.close();

    return 0;
  }

  /**
   * 将流转化为字节数组
   */
  public static byte[] toBytes(InputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];

    int len = 0;

    while ((len = inputStream.read(buffer, 0, 1024)) > 0) {
      outputStream.write(buffer, 0, len);
    }

    return outputStream.toByteArray();
  }

  /**
   * 将流转化为字符串
   */
  public static String toString(InputStream inputStream) throws IOException {
    return toString(inputStream, StandardCharsets.UTF_8.name());
  }

  /**
   * 将流转化为字符串
   */
  public static String toString(InputStream inputStream, String charsetName) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];

    int len = 0;

    while ((len = inputStream.read(buffer, 0, 1024)) > 0) {
      outputStream.write(buffer, 0, len);
    }

    return outputStream.toString(charsetName);
  }
}
