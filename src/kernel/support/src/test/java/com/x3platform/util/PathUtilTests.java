package com.x3platform.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PathUtilTests {
  // 测试获取程序路径
  @Test
  public void testGetProgramPath() {
    String path = PathUtil.getProgramPath();

    String path1 = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    assertNotNull(path);
  }
}
