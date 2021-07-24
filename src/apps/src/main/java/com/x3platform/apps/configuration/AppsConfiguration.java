package com.x3platform.apps.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 应用管理的配置信息
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
  public static final String SECTION_NAME = "apps";

  @Value("${x3platform." + SECTION_NAME + ".administrators:root}")
  private String administrators;

  /**
   * 系统超级管理员帐号,多个人以逗号隔开
   */
  public String getAdministrators() {
    return administrators;
  }

  @Value("${x3platform." + SECTION_NAME + ".hidden-start-menu:Off}")
  private String hiddenStartMenu;

  /**
   * 隐藏开始菜单 : 可选的值 1.On 2.Off(默认)
   */
  public String getHiddenStartMenu() {
    return hiddenStartMenu;
  }

  @Value("${x3platform." + SECTION_NAME + ".hidden-top-menu:Off}")
  private String hiddenTopMenu;

  /**
   * 隐藏顶部菜单 : 可选的值 1.On 2.Off(默认)
   */
  public String getHiddenTopMenu() {
    return hiddenTopMenu;
  }

  @Value("${x3platform." + SECTION_NAME + ".hidden-shortcut-menu:Off}")
  private String hiddenShortcutMenu;

  /**
   * 隐藏快捷方式 : 可选的值 1.On 2.Off(默认)
   */
  public String getHiddenShortcutMenu() {
    return hiddenShortcutMenu;
  }


  @Value("${x3platform." + SECTION_NAME + ".sync-feature-to-menu:Off}")
  private String  syncFeatureToMenu;

  /**
   * 同步功能信息至菜单信息 : 可选的值 1.On 2.Off(默认)
   */
  public String getSyncFeatureToMenu() {
    return syncFeatureToMenu;
  }
}
