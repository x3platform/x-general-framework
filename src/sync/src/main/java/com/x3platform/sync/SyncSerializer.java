package com.x3platform.sync;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 同步序列化工具
 *
 * @author ruanyu
 */
public interface SyncSerializer {

  /**
   * 序列化
   *
   * @param stream 输出流
   * @param obj 对象
   */
  void serialize(OutputStream stream, Object obj);

  /**
   * 反序列化
   *
   * @param stream 输入流
   * @param clazz 类对象
   */
  <T> T deserialize(InputStream stream, Class<T> clazz);
}
