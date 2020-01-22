package com.x3platform.cachebuffer.redis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CachingManagerTests {

  @Test
  public void testContains() {
    String name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, "test object");
    boolean result = CachingManager.contains(name);
    assertTrue(result);
    CachingManager.delete(name);

    name = "test:value:" + StringUtil.toRandom(6);
    result = CachingManager.contains(name);
    assertFalse(result);
  }

  @Test
  public void testSet() {
    String name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, "test object", 5);
    boolean result = CachingManager.contains(name);
    assertTrue(result);

    // 测试五秒后删除
    name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, DateUtil.getTimestamp(), 5);
    result = CachingManager.contains(name);
    assertTrue(result);
  }

  @Test
  public void testDelete() {
    String name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, "test object (delete)");
    CachingManager.delete(name);
    boolean result = CachingManager.contains(name);
    assertFalse(result);
  }
}
