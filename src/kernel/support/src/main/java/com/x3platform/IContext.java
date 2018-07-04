package com.x3platform;

/**
 * 上下文接口
 */
public interface IContext {
  /**
   * 名称
   *
   * @return 名称
   */
  String getName();

  /**
   * 重新加载
   */
  void Reload();
}
