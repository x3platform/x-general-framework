package com.x3platform.plugins;

import java.util.*;

/**
 * 自定义插件接口
 */
public interface ICustomPlugin {
  /**
   * 标识
   */
  String getId();

  void setId(String value);

  /**
   * 名称
   */
  String getName();

  /**
   * 版本
   */
  String getVersion();

  /**
   * 作者
   */
  String getAuthor();

  /**
   * 版权
   */
  String getCopyright();

  /**
   * 插件获取地址
   */
  String getUrl();

  /**
   * 缩略图
   */
  String getThumbnailUrl();

  /**
   * 描述信息
   */
  String getDescription();

  /**
   * 状态: 0 关闭 | 1 开启
   */
  int getStatus();

  void setStatus(int value);

  /**
   * 菜单支持: 0 不支持 | 1 支持
   */
  int getSupportMenu();

  void setSupportMenu(int value);

  /**
   * 安装插件
   *
   * @return 返回信息. =0代表安装成功, >0代表安装失败.
   */
  int Install();

  /**
   * 卸载插件
   *
   * @return 返回信息. =0代表卸载成功, >0代表卸载失败.
   */
  int Uninstall();

  /**
   * 重启插件
   *
   * @return 返回信息. =0代表卸载成功, >0代表卸载失败.
   */
  int Restart();

  /**
   * 执行命令
   *
   * @return 返回信息. =0代表执行成功, >0代表执行失败.
   */
  int Command(Hashtable agrs);
}
