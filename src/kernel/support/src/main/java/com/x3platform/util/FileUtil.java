package com.x3platform.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件处理辅助类
 *
 * @author ruanyu
 */
public class FileUtil {

  /**
   * 将文件转化为输入流
   */
  public static InputStream toStream(String path) throws IOException {
    File file = new File(path);

    return new FileInputStream(file);
  }

  /**
   * 将文件转化为文本
   */
  public static String toText(String path) throws IOException {
    File file = new File(path);

    InputStream inputStream = new FileInputStream(file);

    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

    StringBuilder buffer = new StringBuilder();

    String line = "";

    while ((line = in.readLine()) != null) {
      buffer.append(line);
    }

    return buffer.toString();
  }

  /**
   * 将文件转化为字节数组
   */
  public static byte[] toBytes(String path) throws IOException {
    byte[] data = null;

    File file = new File(path);

    FileInputStream inputStream = new FileInputStream(file);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    int len;
    byte[] buffer = new byte[1024];

    while ((len = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, len);
    }

    data = outputStream.toByteArray();

    inputStream.close();
    outputStream.close();

    return data;
  }
}
