package com.x3platform.apps.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 人员及权限的配置信息
 */
@Component("com.x3platform.apps.configuration.AppsConfiguration")
public class AppsConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "apps";

  /**
   * 配置区的名称
   */
  // public static final String SectionName = "apps";

  @Value("${x3platform." + APPLICATION_NAME + ".administrators:#}")
  private String administrators;

  /**
   * 内置超级管理员帐号,多个人以逗号隔开
   */
  public String getAdministrators() {
    return administrators;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".hidden-start-menu:Off}")
  private String mHiddenStartMenu;

  /**
   * 隐藏开始菜单 : 可选的值 1.On 2.Off(默认)
   */
  public String getHiddenStartMenu() {
    return mHiddenStartMenu;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".hidden-top-menu:Off}")
  private String mHiddenTopMenu;

  /**
   * 隐藏顶部菜单 : 可选的值 1.On 2.Off(默认)
   */
  public String getHiddenTopMenu() {
    return mHiddenTopMenu;
  }

  @Value("${x3platform." + APPLICATION_NAME + ".hidden-shortcut-menu:Off}")
  private String mHiddenShortcutMenu;

  /**
   * # 隐藏快捷方式 : 可选的值 1.On 2.Off(默认)
   */
  public String getHiddenShortcutMenu() {
    return mHiddenShortcutMenu;
  }
}
