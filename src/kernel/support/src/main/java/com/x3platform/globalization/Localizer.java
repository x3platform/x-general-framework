package com.x3platform.globalization;

import java.io.File;
import java.util.List;

import com.x3platform.util.PathUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地化翻译器
 */
public class Localizer {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private Document doc = null;

  private String nodeName = null;

  /**
   * @param file
   * @param nodeName
   */
  public Localizer(String file, String nodeName) {
    File fileInfo = new File(file);

    if (!fileInfo.exists()) {
      logger.warn(String.format("locale file %1$s is not exist.", file));
    }

    if (fileInfo.isFile()) {
      SAXReader saxReader = new SAXReader();

      try {
        doc = saxReader.read(fileInfo);
      } catch (DocumentException e) {
        e.printStackTrace();
      }

      String parentPath = fileInfo.getParent();
      File parent = new File(parentPath);

      String fileName = PathUtil.getFileNameWithoutExtension(file);

      File[] files = parent.listFiles();

      /*
      for (String tempFile : files) {
        if (!file.equals(tempFile)) {
          // Document tempDoc = new XmlDocument();
          // tempDoc.Load(tempFile);
          Document tempDoc = saxReader.read(new File(tempFile));

          NodeList nodes = tempDoc.getRootElement().ChildNodes;

          for (Node node : nodes) {
            Node importNode = doc.ImportNode(node, true);

            doc.getRootElement().AppendChild(importNode);
          }
        }
      }
      */

      for (int i = 0; i < files.length; i++) {
        if (!files[i].isDirectory()) {
          File tempFile = files[i];
          // 只处理相同名字前缀的文件
          if (tempFile.getName().indexOf(fileName) != 0) {
            continue;
          }

          try {
            Document tempDoc = saxReader.read(tempFile);

            List nodes = tempDoc.getRootElement().elements();

            for (Object node : nodes) {
              // Node importNode = doc.ImportNode(node, true);

              // doc.getRootElement().AppendChild(importNode);
              doc.getRootElement().add((Element) ((Node) node).clone());
            }
          } catch (DocumentException e) {
            e.printStackTrace();
          }

        }
      }

    }

    this.nodeName = nodeName;
  }

  /**
   * 获取文本信息
   *
   * @param name
   * @return
   */
  public String getText(String name) {
    Node node = doc.selectSingleNode("resources/" + nodeName + "[@name='" + name + "']");

    return node == null ? null : node.getText();
  }

  /**
   * 获取节点名称
   *
   * @param text
   * @return 节点名称
   */
  public String getName(String text) {
    Node node = doc.selectSingleNode("resources/" + nodeName + "[text()='" + text + "']");
    return node == null ? null : ((DefaultElement) node).attribute("name").getValue();
  }

  /**
   * 获取文本信息
   *
   * @param applicationName
   * @param name
   * @return
   */
  public String getText(String applicationName, String name) {
    Node node = doc.selectSingleNode("resources/application[@name='" + applicationName + "']/" + nodeName + "[@name='" + name + "']");

    return node == null ? null : node.getText();
  }
}
