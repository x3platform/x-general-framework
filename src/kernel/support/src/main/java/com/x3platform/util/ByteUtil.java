package com.x3platform.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节处理辅助类
 */
public class ByteUtil {

  /**
   * 将字节数组保存到文件
   */
  public static int toFile(byte[] buffer, String path) throws IOException {
    FileOutputStream stream = new FileOutputStream(path);

    stream.write(buffer);

    stream.close();

    return 0;
  }

  public static InputStream toStream(byte[] buffer) {
    return new ByteArrayInputStream(buffer);
  }

  private static final char[] HEXES = {
    '0', '1', '2', '3',
    '4', '5', '6', '7',
    '8', '9', 'a', 'b',
    'c', 'd', 'e', 'f'
  };

  /**
   * @param data byte[] data
   * @return hex string
   */
  public static String toHexString(byte[] data) {
    if (data == null || data.length <= 0) {
      return null;
    }

    StringBuilder hex = new StringBuilder();

    // for (int i = 0; i < data.length; i++) {
    //  int v = data[i] & 0xFF;
    //      String hv = Integer.toHexString(v);
    //      if (hv.length() < 2) {
    //        stringBuilder.append(0);
    //      }
    //      stringBuilder.append(hv);
    // }

    for (byte b : data) {
      hex.append(HEXES[(b >> 4) & 0x0F]);
      hex.append(HEXES[b & 0x0F]);
    }

    return hex.toString();
  }

  /**
   * Convert hex string to byte[]
   *
   * @param hexString the hex string
   * @return byte[]
   */
  public static byte[] fromHexString(String hexString) {
    if (hexString == null || hexString.equals("")) {
      return null;
    }
    hexString = hexString.toUpperCase();
    int length = hexString.length() / 2;
    char[] hexChars = hexString.toCharArray();
    byte[] d = new byte[length];
    for (int i = 0; i < length; i++) {
      int pos = i * 2;
      d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
    }
    return d;
  }

  /**
   * Convert char to byte
   *
   * @param c char
   * @return byte
   */
  private static byte charToByte(char c) {
    return (byte) "0123456789ABCDEF".indexOf(c);
  }
}
