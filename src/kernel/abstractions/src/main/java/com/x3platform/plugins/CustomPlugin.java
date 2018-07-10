package com.x3platform.plugins;

import java.util.*;

/**
 * 自定义插件
 */
public abstract class CustomPlugin implements ICustomPlugin {
  private String mId = "";

  /**
   * 标识
   */
  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String value) {
    mId = value;
  }

  private String mName = "";

  /**
   * 名称
   */
  @Override
  public String getName() {
    return mName;
  }

  private String mVersion = "1.0.0.0";

  /**
   * 版本
   */
  @Override
  public final String getVersion() {
    return mVersion;
  }

  private String mAuthor = "ruanyu@x3platform.com";

  /**
   * 作者
   */
  @Override
  public String getAuthor() {
    return mAuthor;
  }

  private String mCopyright = "MIT";

  /**
   * 版权
   */
  @Override
  public String getCopyright() {
    return mCopyright;
  }

  private String mUrl = "";

  /**
   * 插件获取地址
   */
  @Override
  public String getUrl() {
    return mUrl;
  }

  private String mThumbnailUrl = "";

  /**
   * 缩略图
   */
  @Override
  public String getThumbnailUrl() {
    return mThumbnailUrl;
  }

  private String mDescription = "";

  /**
   * 描述信息
   */
  public String getDescription() {
    return mDescription;
  }

  private int mStatus = 0;

  /**
   * 状态, 0 未激活, 1 已激活.
   */
  public int getStatus() {
    return mStatus;
  }

  public void setStatus(int value) {
    mStatus = value;
  }

  private int mSupportMenu = 1;

  /**
   * 菜单支持
   */
  public int getSupportMenu() {
    return mSupportMenu;
  }

  public void setSupportMenu(int value) {
    mSupportMenu = value;
  }

  /**
   * 安装插件
   *
   * @return 返回信息. =0代表安装成功, >0代表安装失败.
   */
  public int install() {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }

  /**
   * 卸载插件
   *
   * @return 返回信息. =0代表卸载成功, >0代表卸载失败.
   */
  public int uninstall() {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }

  /**
   * 重启插件
   *
   * @return 返回信息. =0代表重启成功, >0代表重启失败.
   */
  public int restart() {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }

  /**
   * 执行命令
   *
   * @return 返回信息. =0代表执行成功, >0代表执行失败.
   */
  public int command(Hashtable agrs) {
    throw new RuntimeException("Oops |-_-||, the method or operation is not implemented.");
  }
}
