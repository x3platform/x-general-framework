package com.x3platform.util;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class PathUtilTests {
  // 测试获取程序路径
  @Test
  public void testGetProgramPath() {
    String path = PathUtil.getProgramPath();

    String path1 = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    // assertEquals("\"中文\" should return \"\\u4e2d\\u6587\"", "\\u4e2d\\u6587", text);
    assertNotNull(path);
  }
}
