package com.x3platform;

/**
 * 上下文接口
 *
 * @author ruanyu
 */
public interface Context {
  /**
   * 名称
   *
   * @return 名称
   */
  String getName();

  /**
   * 重新加载
   */
  void reload();
}
