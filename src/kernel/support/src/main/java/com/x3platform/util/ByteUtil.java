package com.x3platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节处理辅助类
 */
public class ByteUtil {

  /**
   * 将字节数组保存到文件
   */
  public static long toFile(byte[] buffer, String path) throws IOException {
    FileOutputStream stream = new FileOutputStream(path);

    stream.write(buffer);

    stream.close();

    return 0;

    /*
    Stream stream = new MemoryStream(buffer);

    long length = stream.Length;

    using(BinaryReader binaryReader = new BinaryReader(stream))
    {
      using(FileStream fileStream = File.Create(path))
      {
        fileStream.Write(binaryReader.ReadBytes((int) length), 0, (int) length);

        fileStream.Close();
      }

      binaryReader.Close();
    }

    return length;
  */
  }
}
