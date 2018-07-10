package com.x3platform.globalization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dom4j.Document;
import org.dom4j.Node;


import java.util.Locale;

import com.x3platform.util.*;
import com.x3platform.configuration.KernelConfigurationView;

/**
 * 本地化脚本文件
 */
public class I18nScript {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  ///#region 属性:Instance
  private static volatile I18nScript instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static I18nScript getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = new I18nScript();
        }
      }
    }

    return instance;
  }
  ///#endregion

  private I18nScript() {
  }

  private String[] ignoreFiles = new String[]{"menu", "exceptions"};

  /**
   * 初始化多语言脚本
   */
  public final void Init() {
    /*
    // 默认目录
    String defaultDirectory = KernelConfigurationView.getInstance().ApplicationPathRoot + "locales/" + KernelConfigurationView.Instance.CultureName;
    // 本地化设置目录
    String[] directories = (new java.io.File(KernelConfigurationView.Instance.ApplicationPathRoot + "locales")).list(java.io.File::isDirectory);

    XmlDocument doc = new XmlDocument();

    XmlDocument i18nDoc = new XmlDocument();

    String ignoreFileFilter = tangible.DotNetToJavaStringHelper.join(",", ignoreFiles);

    for (String directory : directories) {
      // Console.WriteLine(directory);

      StringBuilder outString = new StringBuilder();

      String[] files = Directory.GetFiles(defaultDirectory, "*.xml");

      outString.append("{");

      for (String file : files) {
        //
        String text = Path.GetFileNameWithoutExtension(file);
        text = text.indexOf(".") == -1 ? text : text.substring(0, text.indexOf("."));

        // 过滤忽略的文件
        if (ignoreFileFilter.indexOf(text) > -1) {
          continue;
        }

        outString.append(Path.GetFileNameWithoutExtension(file) + ":");

        outString.append("{");

        doc.Load(file);

        i18nDoc.Load(java.nio.file.Paths.get(directory).resolve((new java.io.File(file)).getName()).toString());

        XmlNodeList nodes = doc.DocumentElement.ChildNodes;

        for (XmlNode node : nodes) {
          // 忽略注释信息
          if (node.NodeType == XmlNodeType.Comment) {
            continue;
          }

          if (node.ChildNodes.size() > 1) {
            XmlNodeList childNodes = node.ChildNodes;

            outString.append("\"" + node.Attributes["name"].Value + "\":{");

            for (XmlNode childNode : childNodes) {
              outString.append("\"" + childNode.Attributes["name"].Value + "\":\"" + GetInnerText(doc, i18nDoc, String.Concat("resources/", node.Name, "[@name='" + node.Attributes["name"].Value + "']/", childNode.Name, "[@name='" + childNode.Attributes["name"].Value + "']")) + "\",");
            }

            StringHelper.TrimEnd(outString, ",");

            outString.append("},");
          } else {
            outString.append("\"" + node.Attributes["name"].Value + "\":\"" + GetInnerText(doc, i18nDoc, String.Concat("resources/", node.Name, "[@name='" + node.Attributes["name"].Value + "']")) + "\",");
          }
        }

        StringHelper.TrimEnd(outString, ",");

        outString.append("},");
      }

      StringHelper.TrimEnd(outString, ",");

      outString.append("}" + "\r\n");

      String localFile = DirectoryHelper.FormatLocalPath(directory + "/i18n.js");

      try {
        File.WriteAllText(localFile, String.Concat("(function(i18n){ var init = function(destination, source) { for(var property in source) { destination[property] = source[property]; } return destination; }; i18n = init(i18n, ", outString.toString(), "); window.i18n = i18n; return i18n; })(typeof i18n !== 'undefined' ? i18n : {});"), Encoding.UTF8);

        logger.Info("本地化语言文件 " + localFile + " 生成成功.");
      } catch (RuntimeException ex) {
        logger.Error("Failed to write to " + localFile);

        logger.Error(ex.getMessage());
      }

      // 生成支持 Node.js 的本地化语言文件
      localFile = DirectoryHelper.FormatLocalPath(directory + "/i18n-node.js");

      try {
        File.WriteAllText(localFile, "module.exports = " + outString.toString(), Encoding.UTF8);

        logger.info("本地化语言文件 " + localFile + " 生成成功.");
      } catch (RuntimeException ex) {
        logger.error("Failed to write to " + localFile);

        logger.error(ex.getMessage());
      }
    }
    */
  }

  /**
   * 获取文本信息
   *
   * @param doc
   * @param i18nDoc
   * @param xpath
   * @return
   */
  private String GetInnerText(Document doc, Document i18nDoc, String xpath) {
    Node node = i18nDoc.selectSingleNode(xpath);

    if (node == null) {
      node = doc.selectSingleNode(xpath);
    }

    return node.getText();
  }

  /**
   * 获取语言文件
   *
   * @return
   */
  public final String GetFile() {
    Locale locale = Locale.getDefault();

    String defaultFile = KernelConfigurationView.getInstance().getApplicationPathRoot() + "locales/" + KernelConfigurationView.getInstance().getCultureName() + "/i18n.js";
    String i18nFile = KernelConfigurationView.getInstance().getApplicationPathRoot() + "locales/" + locale.getISO3Language() + "/i18n.js";

    if ((new java.io.File(i18nFile)).isFile()) {
      return "/locales/" + locale.getISO3Language() + "/i18n.js";
    } else {
      return "/locales/" + KernelConfigurationView.getInstance().getCultureName() + "/i18n.js";
    }
  }
}
