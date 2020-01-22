package com.x3platform.sync.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.x3platform.KernelContext;
import com.x3platform.sync.SyncSerializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * JSON 序列化工具
 *
 * @author ruanyu
 */
public class JsonSerializer implements SyncSerializer {

  /**
   * 序列化
   *
   * @param stream 流
   * @param obj 对象
   */
  @Override
  public void serialize(OutputStream stream, Object obj) {
    String text = JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);

    byte[] buffer = text.getBytes();

    try {
      // out = new FileOutputStream(file);
      stream.write(buffer, 0, buffer.length);
      stream.close();
    } catch (IOException ex) {
      KernelContext.getLog().error("IOException", ex);
    }
  }

  /**
   * 反序列化
   */
  @Override
  public <T> T deserialize(InputStream stream, Class<T> clazz) {
    // 读取文件中的内容到 b[] 数组
    try {
      int len = 0;
      while (len == 0) {
        len = stream.available();
      }

      // 创建合适文件大小的数组
      byte[] buffer = new byte[len];

      stream.read(buffer);
      stream.close();

      String data = new String(buffer);

      return JSON.parseObject(data, clazz);
    } catch (IOException ex) {
      KernelContext.getLog().error("IOException", ex);
    }

    return null;
  }
}
