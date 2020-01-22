package com.x3platform.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

/**
 * 目录处理辅助类
 */
public class DirectoryUtil {

  /**
   * 创建目录
   *
   * @param path 路径
   */
  public static void create(String path) {
    // 检查目标目录是否以目录分割字符结束如果不是则添加之
    if (path.charAt(path.length() - 1) != File.separatorChar) {
      path += File.separatorChar;
    }

    // 判断目标目录是否存在如果不存在则新建之
    if (!(new File(path)).isDirectory()) {
      (new File(path)).mkdirs();
    }
  }

  public static void copy(String fromPath, String toPath) throws IOException {
    // 实现一个静态方法将指定文件夹下面的所有内容copy到目标文件夹下面
    // 如果目标文件夹为只读属性就会报错。

    // 检查目标目录是否以目录分割字符结束如果不是则添加之
    if (toPath.charAt(toPath.length() - 1) != java.io.File.separatorChar) {
      toPath += java.io.File.separatorChar;
    }

    // 判断目标目录是否存在如果不存在则新建之
    if (!(new java.io.File(toPath)).isDirectory()) {
      (new java.io.File(toPath)).mkdirs();
    }

    File fromPathObject = new File(fromPath);

    // 得到源目录的文件列表，该里面是包含文件以及目录路径的一个数组
    // 如果你指向 copy 目标文件下面的文件而不包含目录请使用下面的方法
    // string[] fileList = System.IO.Directory.GetFiles(fromPath);

    File[] files = fromPathObject.listFiles();

    // 遍历所有的文件和目录
    for (File file : files) {
      // 先当作目录处理如果存在这个目录就递归Copy该目录下面的文件
      if (file.isDirectory()) {
        copy(file.getPath(), toPath + file.getName());
      } else {
        // 否则直接Copy文件
        Files.copy(file.toPath(), Paths.get(toPath, file.getName()),
          StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
      }
    }
  }

  /**
   * 删除目录下的所有文件内容
   *
   * @param path 文件目录
   */
  public static void delete(String path) {
    // 实现一个静态方法将指定文件夹下面的所有内容删除.
    // 测试的时候要小心操作，删除之后无法恢复。

    // 检查目标目录是否以目录分割字符结束如果不是则添加之

    if (path.charAt(path.length() - 1) != File.separatorChar) {
      path += File.separatorChar;
    }

    File pathObject = new File(path);

    // 得到源目录的文件列表，该里面是包含文件以及目录路径的一个数组
    // 如果你指向Delete目标文件下面的文件而不包含目录请使用下面的方法
    if (pathObject.exists()) {
      File[] files = pathObject.listFiles();

      // 遍历所有的文件和目录
      for (File file : files) {
        // 先当作目录处理如果存在这个目录就递归Delete该目录下面的文件
        if (file.isDirectory()) {
          delete(path + file.getName());
        } else {
          // 否则直接Delete文件
          file.delete();
        }
      }

      //删除文件夹
      pathObject.delete();
    }
  }

  /**
   * 格式化本地路径
   *
   * @param path 路径
   * @return 格式化后的路径
   */
  public static String formatLocalPath(String path) {
    return path.replace("\\", "/").replace("//", "/");
  }

  /**
   * @return
   */
  public static String formatTimePath() {
    return formatTimePath(LocalDateTime.now());
  }

  /**
   * @return
   */
  public static String formatTimePath(LocalDateTime datetime) {
    return datetime.getYear() + "/" + (((datetime.getMonthValue() - 1) / 3) + 1) + "Q/" 
      + datetime.getMonthValue() + "/";
  }
}
