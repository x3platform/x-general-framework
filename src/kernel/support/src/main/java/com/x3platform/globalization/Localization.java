package com.x3platform.globalization;

import java.io.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Locale;

import com.x3platform.util.DirectoryUtil;
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

    String file = KernelConfigurationView.getInstance().getApplicationPathRoot() + "/locales/" + KernelConfigurationView
      .getInstance().getCultureName() + "/" + fileName;

    // 初始化默认翻译
    File fileInfo = new File(file);

    if (fileInfo.exists()) {
      this.defaultLocalizer = new Localizer(file, nodeName);
    } else {
      // 写入默认文件
      try {
        String localePath =
          KernelConfigurationView.getInstance().getApplicationPathRoot() + "/locales/" + KernelConfigurationView
            .getInstance().getCultureName() + "/";

        // 判断目标目录是否存在如果不存在则新建之
        if (!(new File(localePath)).isDirectory()) {
          (new File(localePath)).mkdirs();
        }

        InputStream inputStream = KernelConfigurationView.class
          .getResourceAsStream("/locales/" + KernelConfigurationView.getInstance().getCultureName() + "/" + fileName);

        writeFile(inputStream, file);
        this.defaultLocalizer = new Localizer(file, nodeName);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // 初始化默认翻译
    dictionary = new HashMap<String, Localizer>();
  }

  public static void writeFile(InputStream is, String fileName) throws IOException {
    BufferedInputStream in = null;
    BufferedOutputStream out = null;
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(fileName);
      in = new BufferedInputStream(is);
      out = new BufferedOutputStream(fileOutputStream);
      int len = -1;
      byte[] b = new byte[1024];
      while ((len = in.read(b)) != -1) {
        out.write(b, 0, len);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      in.close();
      out.close();
    }

  }

  /**
   * 获取名称
   *
   * @param text 文本
   * @return 名称
   */
  public String name(String text) {
    return this.name(text, StringCase.Default);
  }

  /**
   * 获取名称
   *
   * @param text 名称
   * @param stringCase 字符串大小写格式
   * @return 名称
   */
  public String name(String text, StringCase stringCase) {
    String name = this.getLocalizer().getName(text);

    name = name == null ? this.defaultLocalizer.getName(text) : name;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s text %2$s is null", this.nodeName, text));
    }

    return toStringCase(name, stringCase);
  }

  /**
   * @param name 名称
   * @return 字符串内容
   */
  public String text(String name) {
    return this.text(name, StringCase.Default);
  }

  /**
   * @param name 名称
   * @param stringCase 字符串大小写格式
   * @return 字符串内容
   */
  public String text(String name, StringCase stringCase) {
    String text = this.getLocalizer().getText(name);

    text = text == null ? this.defaultLocalizer.getText(name) : text;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s name %2$s is null", this.nodeName, name));
    }

    return toStringCase(text, stringCase);
  }

  /**
   * 获取文本
   *
   * @param applicationName 应用名称
   * @param name 名称
   */
  public String text(String applicationName, String name) {
    return this.text(applicationName, name, StringCase.Default);
  }

  /**
   * 获取文本
   *
   * @param applicationName 应用名称
   * @param name 名称
   * @param stringCase 字符串大小写格式
   * @return 字符串内容
   */
  public String text(String applicationName, String name, StringCase stringCase) {
    String text = this.getLocalizer().getText(applicationName, name);

    text = text == null ? this.defaultLocalizer.getText(applicationName, name) : text;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s name %2$s is null", this.nodeName, name));
    }

    return toStringCase(text, stringCase);
  }

  /**
   * 格式化文本输出
   *
   * @param name 文本名称
   * @param args 字符参数
   * @return 字符串内容
   */
  public String format(String name, String... args) {
    return this.format(name, StringCase.Default, args);
  }

  /**
   * 格式化文本输出
   *
   * @param name 文本名称
   * @param stringCase 字符串大小写格式
   * @return 字符串内容
   */
  public String format(String name, StringCase stringCase, String... args) {
    String text = this.getLocalizer().getText(name);

    text = text == null ? this.defaultLocalizer.getText(name) : text;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s name %2$s is null", this.nodeName, name));
    }

    if (args.length > 0) {
      text = StringUtil.format(text, args);
    }

    return toStringCase(text, stringCase);
  }

  /**
   * 格式化文本输出
   *
   * @param applicationName 应用名称
   * @param name 名称
   * @param stringCase 字符串大小写格式
   * @param args 字符参数
   * @return 字符串内容
   */
  public String format(String applicationName, String name, StringCase stringCase, String... args) {
    String text = this.getLocalizer().getText(applicationName, name);

    text = text == null ? this.defaultLocalizer.getText(applicationName, name) : text;

    if (this.warnNullValue && text == null) {
      logger.warn(String.format("locale file node %1$s name %2$s is null", this.nodeName, name));
    }

    if (args.length > 0) {
      text = StringUtil.format(text, args);
    }

    return toStringCase(text, stringCase);
  }

  /**
   * 获取本土化信息
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
