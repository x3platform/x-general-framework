package com.x3platform.util;

import com.x3platform.configuration.KernelConfigurationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 路径处理辅助类
 */
public class PathUtil {

  /**
   * 获取文件扩展名
   *
   * @param path 文件路径
   */
  public static String getExtension(String path) {
    path = new File(path).getName();

    if ((path != null) && (path.length() > 0)) {
      int dot = path.lastIndexOf('.');
      if ((dot > -1) && (dot < (path.length() - 1))) {
        return path.substring(dot);
      }
    }
    return path;
  }

  /**
   * 获取不带扩展名的文件名
   *
   * @param path 文件路径
   */
  public static String getFileNameWithoutExtension(String path) {
    path = new File(path).getName();

    if ((path != null) && (path.length() > 0)) {
      int dot = path.lastIndexOf('.');
      if ((dot > -1) && (dot < (path.length()))) {
        return path.substring(0, dot);
      }
    }
    return path;
  }

  /**
   * Get file separator, such as "/" on unix.
   *
   * @return the separator of file.
   */
  public static String getFileSeparator() {
    return System.getProperty("file.separator");
  }

  /**
   * 获取换行符, unix - "\n".
   *
   * @return 换行符.
   */
  public static String getLineSeparator() {
    return System.getProperty("line.separator");
  }

  /**
   * 获取程序路径
   *
   * @return programPath
   */
  public static String getClassPath(Class cls) {

    // Class cls = obj.getClass();

    ClassLoader loader = cls.getClassLoader();
    //
    // Get the full name of the class.
    //
    String clsName = cls.getName() + ".class";
    //
    // Get the package that include the class.
    //
    Package pack = cls.getPackage();

    String path = "";
    //
    // Transform package name to path.
    //
    if (pack != null) {
      String packName = pack.getName();
      //
      // Get the class's file name.
      //
      clsName = clsName.substring(packName.length() + 1);
      //
      // If package is simple transform package name to path directly,
      // else transform package name to path by package name's
      // constituent.
      //
      path = packName;
      if (path.indexOf(".") > 0) {
        path = path.replace(".", "/");
      }
      path = path + "/";
    }

    URL url = loader.getResource(path + clsName);
    //
    // Get path information form the instance of URL.
    //
    String retPath = url.getPath();
    //
    // Delete protocol name "file:" form path information.
    //
    try {
      int pos = retPath.indexOf("file:");
      if (pos > -1) {
        retPath = retPath.substring(pos + 5);
      }
      //
      // Delete the information of class file from the information of
      // path.
      //
      pos = retPath.indexOf(path + clsName);
      retPath = retPath.substring(0, pos - 1);
      //
      // If the class file was packageed into JAR e.g. file, delete the
      // file name of the corresponding JAR e.g..
      //
      if (retPath.endsWith("!")) {
        retPath = retPath.substring(0, retPath.lastIndexOf("/"));
      }
      retPath = URLDecoder.decode(retPath, "utf-8");
    } catch (Exception e) {
      retPath = null;
      e.printStackTrace();
    }

    return retPath;
  }

  /**
   * 获取程序启动路径
   *
   * @return programPath
   */
  public static String getProgramPath() {
    return Thread.currentThread().getContextClassLoader().getResource("").getPath();
  }
}
