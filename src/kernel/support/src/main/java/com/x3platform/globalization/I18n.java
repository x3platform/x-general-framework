package com.x3platform.globalization;

import java.util.*;

/**
 * 本地化设置对象
 */
public class I18n {
  private static HashMap<String, Object> lockObjects = new HashMap<String, Object>();
  static {
    lockObjects.put("translates", new Object());
    lockObjects.put("strings", new Object());
    lockObjects.put("menu", new Object());
    lockObjects.put("exceptions", new Object());
  }

  private static volatile Localization values = null;

  /**
   * 本地化的翻译信息
   * @return 返回 {@link Localization} 翻译信息
   */
  public static Localization getTranslates() {
    if (values == null) {
      synchronized (lockObjects.get("translates")) {
        if (values == null) {
          values = new Localization("translates.xml", "translate");
        }
      }
    }

    return values;
  }

  private static volatile Localization strings = null;

  /**
   * 本地化的文本信息 系统提示信息 警告信息 错误信息
   *
   * @return 返回 {@link Localization} 字符串信息
   */
  public static Localization getStrings() {
    if (strings == null) {
      synchronized (lockObjects.get("strings")) {
        if (strings == null) {
          strings = new Localization("strings.xml", "string");
        }
      }
    }

    return strings;
  }

  private static volatile Localization menu = null;

  /**
   * 获取本地化的菜单信息
   * @return 返回 {@link Localization} 菜单信息
   */
  public static Localization getMenu() {
    if (menu == null) {
      synchronized (lockObjects.get("menu")) {
        if (menu == null) {
          menu = new Localization("menu.xml", "menu", false);
        }
      }
    }

    return menu;
  }

  private static volatile Localization m_Exceptions = null;

  /**
   * 获取本地化的异常信息
   *
   * @return 返回 {@link Localization} 异常信息
   */
  public static Localization getExceptions() {
    if (m_Exceptions == null) {
      synchronized (lockObjects.get("exceptions")) {
        if (m_Exceptions == null) {
          m_Exceptions = new Localization("exceptions.xml", "exception");
        }
      }
    }

    return m_Exceptions;
  }

  /**
   * 构造函数
   */
  private I18n() {
  }
}
