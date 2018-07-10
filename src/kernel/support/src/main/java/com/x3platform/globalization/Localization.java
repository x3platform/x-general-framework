package com.x3platform.globalization;

import java.util.*;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.util.StringUtil;

/**
 * 本地化信息
 */
public class Localization {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 默认本土化缓存信息
   */
  private Localizer defaultLocalizer = null;

  /**
   * 本土化缓存信息
   */
  private HashMap<String, Localizer> dictionary = null;

  private String fileName;
  private String nodeName;

  /**
   * 空值警告
   */
  private boolean warnNullValue;

  /**
   * @param fileName
   * @param nodeName
   */
  public Localization(String fileName, String nodeName) {
    this(fileName, nodeName, true);
  }

  /**
   * @param fileName
   * @param nodeName
   */
  public Localization(String fileName, String nodeName, boolean warnNullValue) {
    // 设置文件名称和节点名称
    this.fileName = fileName;
    this.nodeName = nodeName;

    this.warnNullValue = warnNullValue;

    String file = KernelConfigurationView.getInstance().getApplicationPathRoot() + "locales/" + KernelConfigurationView.getInstance().getCultureName() + "/" + fileName;

    // 初始化默认翻译
    this.defaultLocalizer = new Localizer(file, nodeName);

    // 初始化默认翻译
    dictionary = new HashMap<String, Localizer>();
  }

  /**
   * @param name
   * @return
   */
  public final String text(String name) {
    return this.text(name, StringCase.Default);
  }

  /**
   * @param name
   * @param stringCase
   * @return
   */
  public final String text(String name, StringCase stringCase) {
    String text = this.getLocalizer().getText(name);

    text = text == null ? this.defaultLocalizer.getText(name) : text;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s name %2$s is null", this.nodeName, name));
    }

    return toStringCase(text, stringCase);
  }

  /**
   * @param applicationName
   * @param name
   * @return
   */
  public final String text(String applicationName, String name) {
    return this.text(applicationName, name, StringCase.Default);
  }

  /**
   * @param applicationName
   * @param name
   * @param stringCase
   * @return
   */
  public final String text(String applicationName, String name, StringCase stringCase) {
    String text = this.getLocalizer().getText(applicationName, name);

    text = text == null ? this.defaultLocalizer.getText(applicationName, name) : text;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s name %2$s is null", this.nodeName, name));
    }

    return toStringCase(text, stringCase);
  }

  /**
   * 获取本土化信息
   *
   * @return
   */
  private Localizer getLocalizer() {
    Localizer localizer = null;

    Locale locale = Locale.getDefault();

    /* TODO 需要修改 源码
    CultureInfo culture = Thread.currentThread().CurrentCulture;

    String languageFolder = KernelConfigurationView.Instance.ApplicationPathRoot + "locales/" + culture.Name + "/" + fileName;

    if (dictionary.containsKey(culture.Name))
    {
      localizer = dictionary.get(culture.Name);
    }
    else
    {
      if ((new java.io.File(languageFolder)).isFile())
      {
        localizer = new Localizer(languageFolder, nodeName);

        if (!dictionary.containsKey(culture.Name))
        {
          dictionary.put(culture.Name, localizer);
        }
      }
    }
    */
    return localizer == null ? this.defaultLocalizer : localizer;
  }

  private String toStringCase(String text, StringCase stringCase) {
    switch (stringCase) {
      case Lower:
        return text.toLowerCase();
      case FirstLower:
        return StringUtil.toFirstLower(text);
      case Upper:
        return text.toUpperCase();
      case FirstUpper:
        return StringUtil.toFirstUpper(text);
      // case Title:
      //  return Thread.currentThread().CurrentCulture.TextInfo.ToTitleCase(text.toLowerCase());
      default:
        return text;
    }
  }
}
