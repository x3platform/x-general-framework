package com.x3platform.cachebuffer;

import static com.x3platform.tests.TestConstants.TEST_CACHE_PREFIX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CachingManagerTests {

  @Test
  public void testSet() {
    String name = TEST_CACHE_PREFIX + ":test-object-1";
    CachingManager.set(name, "abc");
    boolean result = CachingManager.contains(name);
    assertTrue(result);

    CachingManager.delete(name);
  }

  @Test
  public void testDelete() {
    String name = TEST_CACHE_PREFIX + ":test-object-3";
    CachingManager.set(name, "remove-object");

    CachingManager.delete(name);
    boolean result = CachingManager.contains(name);

    assertFalse(result);
  }
}
