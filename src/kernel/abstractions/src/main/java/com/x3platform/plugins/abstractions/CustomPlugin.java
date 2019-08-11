package com.x3platform.plugins.abstractions;

import java.util.*;

/**
 * 自定义插件接口
 */
public interface CustomPlugin {
  /**
   * 获取标识
   * @return 标识
   */
  String getId();

  /**
   * 设置标识
   * @param value 值
   */
  void setId(String value);

  /**
   * 获取名称
   * @return 名称
   */
  String getName();

  /**
   * 获取版本
   * @return 版本
   */
  String getVersion();

  /**
   * 获取作者
   * @return 作者
   */
  String getAuthor();

  /**
   * 获取版权
   */
  String getCopyright();

  /**
   * 获取插件获取地址
   */
  String getUrl();

  /**
   * 获取缩略图
   */
  String getThumbnailUrl();

  /**
   * 获取描述信息
   */
  String getDescription();

  /**
   * 获取状态
   */
  int getStatus();

  /**
   * 设置状态

   * @param value 可选的值 0 关闭 | 1 开启
   */
  void setStatus(int value);

  /**
   * 获取菜单支持
   */
  int getSupportMenu();

  /**
   * 设置菜单支持
   *
   * @param value 可选的值 0 不支持 | 1 支持
   */
  void setSupportMenu(int value);

  /**
   * 安装插件
   *
   * @return 返回信息. 0 代表安装成功, 大于 0 代表安装失败.
   */
  int install();

  /**
   * 卸载插件
   *
   * @return 返回信息. 0 代表卸载成功, 大于 0 代表卸载失败.
   */
  int uninstall();

  /**
   * 重启插件
   *
   * @return 返回信息. 0 代表卸载成功, 大于 0 代表卸载失败.
   */
  int restart();

  /**
   * 执行命令
   *
   * @param args 参数集合
   * @return 返回信息. 0 代表执行成功, 大于 0 代表执行失败.
   */
  int command(Map args);
}
