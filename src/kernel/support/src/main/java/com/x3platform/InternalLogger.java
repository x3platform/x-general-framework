package com.x3platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内部日志记录器
 *
 * @author ruanyu
 */
public final class InternalLogger {

  /**
   * 日志记录器
   */
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 日志记录
   */
  public static Logger getLogger() {
    return getInstance().logger;
  }

  private static volatile InternalLogger instance = null;

  private static Object lockObject = new Object();

  /**
   * 当前信息
   */
  private static InternalLogger getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = new InternalLogger();
        }
      }
    }

    return instance;
  }
}
