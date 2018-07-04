package com.x3platform.plugins;

import java.util.*;

/**
 * 自定义插件
 */
public abstract class CustomPlugin implements ICustomPlugin {
  private String m_Id = "";

  /**
   * 标识
   */
  @Override
  public String getId() {
    return m_Id;
  }

  @Override
  public void setId(String value) {
    m_Id = value;
  }

  private String m_Name = "";

  /**
   * 名称
   */
  @Override
  public String getName() {
    return m_Name;
  }

  private String m_Version = "1.0.0.0";

  /**
   * 版本
   */
  @Override
  public final String getVersion() {
    return m_Version;
  }

  private String m_Author = "ruanyu@x3platform.com";

  /**
   * 作者
   */
  @Override
  public String getAuthor() {
    return m_Author;
  }

  private String m_Copyright = "MIT";

  /**
   * 版权
   */
  @Override
  public String getCopyright() {
    return m_Copyright;
  }

  private String m_Url = "";

  /**
   * 插件获取地址
   */
  @Override
  public String getUrl() {
    return m_Url;
  }

  private String m_ThumbnailUrl = "";

  /**
   * 缩略图
   */
  @Override
  public String getThumbnailUrl() {
    return m_ThumbnailUrl;
  }

  private String m_Description = "";

  /**
   * 描述信息
   */
  public String getDescription() {
    return m_Description;
  }

  private int m_Status = 0;

  /**
   * 状态, 0 未激活, 1 已激活.
   */
  public int getStatus() {
    return m_Status;
  }

  public void setStatus(int value) {
    m_Status = value;
  }

  private int m_SupportMenu = 1;

  /**
   * 菜单支持
   */
  public int getSupportMenu() {
    return m_SupportMenu;
  }

  public void setSupportMenu(int value) {
    m_SupportMenu = value;
  }

  /**
   * 安装插件
   *
   * @return 返回信息. =0代表安装成功, >0代表安装失败.
   */
  public int Install() {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }

  /**
   * 卸载插件
   *
   * @return 返回信息. =0代表卸载成功, >0代表卸载失败.
   */
  public int Uninstall() {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }

  /**
   * 重启插件
   *
   * @return 返回信息. =0代表重启成功, >0代表重启失败.
   */
  public int Restart() {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }

  /**
   * 执行命令
   *
   * @return 返回信息. =0代表执行成功, >0代表执行失败.
   */
  public int Command(Hashtable agrs) {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }
}
