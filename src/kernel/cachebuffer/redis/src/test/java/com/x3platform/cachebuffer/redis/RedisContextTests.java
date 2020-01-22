package com.x3platform.cachebuffer.redis;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.x3platform.cachebuffer.redis.services.RedisTemplateService;
import com.x3platform.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisContextTests {

  @Test
  public void testLoad() {
    RedisContext context = RedisContext.getInstance();
    assertNotNull("RedisContext is not null.", context.getRedisTemplateService());
  }

  @Test
  public void testHashOps() {
    String key = "test:hash:" + StringUtil.to8DigitUuid();
    int len = 5;
    RedisTemplateService service = RedisContext.getInstance().getRedisTemplateService();

    for (int i = 1; i < len; i++) {
      service.hashPut(key, String.valueOf(i), "value" + i);
    }

    assertNotNull(service.hashGet(key, "1"));
    assertEquals("value1", service.hashGet(key, "1"));

    service.hashDelete(key, "1");

    assertNull(service.hashGet(key, "1"));

    service.delete(key);

    assertNull(service.hashGet(key, String.valueOf(len - 1)));
  }
}
