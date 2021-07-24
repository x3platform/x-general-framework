package com.x3platform.globalization;

import com.x3platform.util.StringUtil;
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
   *
   * @return {@link Localization} 翻译信息
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
   * @return {@link Localization} 字符串信息
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
   *
   * @return {@link Localization} 菜单信息
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

  private static volatile Localization exceptions = null;

  /**
   * 获取本地化的异常信息
   *
   * @return {@link Localization} 异常信息
   */
  public static Localization getExceptions() {
    if (exceptions == null) {
      synchronized (lockObjects.get("exceptions")) {
        if (exceptions == null) {
          exceptions = new Localization("exceptions.xml", "exception");
        }
      }
    }

    return exceptions;
  }

  private static String GENERL_APPLICATION_ERROR_CODE = "1";

  /**
   * 获取本地化的异常信息
   *
   * @return {@link Localization} 异常信息
   */
  public static String getExceptionDescription(String code, String... args) {
    // 当 code == 1 表示通用应用错误，快速处理，不做遍历。
    if (GENERL_APPLICATION_ERROR_CODE.equals(code)) {
      return "Application Error";
    }

    String description = "";
    Localization localization = getExceptions();
    String name = localization.name(code);
    if (StringUtil.isNullOrEmpty(name)) {
      return "";
    } else {
      description = localization.text("text_" + name.substring(5));
      return args.length == 0 ? description : StringUtil.format(description, args);
    }
  }


  /**
   * 构造函数
   */
  private I18n() {
  }
}
