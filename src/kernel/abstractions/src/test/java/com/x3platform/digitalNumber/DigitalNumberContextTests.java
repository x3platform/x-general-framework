package com.x3platform.digitalnumber;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DigitalNumberContextTests {
  @Test
  public void testLoad() {
    assertNotNull("DigitalNumberService is not null.",
      DigitalNumberContext.getInstance().getDigitalNumberService());
  }

  @Test
  public void testGenerate() {
    String result = DigitalNumberContext.generate("Key_Guid");

    assertNotNull("result is not null.", result);

    result = DigitalNumberContext.generate("test2");

    assertNotNull("result is not null.", result);

    result = DigitalNumberContext.generate("test3");

    assertNotNull("result is not null.", result);
  }

  @Test
  public void testGenerates() {
    List<String> results = DigitalNumberContext.generates("Key_Guid", 10);

    assertNotNull("result is not null.", results);

    results = DigitalNumberContext.generates("test2", 10);

    assertNotNull("result is not null.", results);

    results = DigitalNumberContext.generates("test3", 10);

    assertNotNull("result is not null.", results);
  }

  @Test
  public void testGenerateDailyIncrement() {
    String result = DigitalNumberContext.generate("test3");

    assertNotNull("result is not null.", result);
  }

  @Test
  public void testDigitalNumberWorker() {
    HashMap<Long, Integer> map = new HashMap<Long, Integer>();
    DigitalNumberWorker worker = new DigitalNumberWorker(1, 1, 1);

    for (int i = 0; i < 1000; i++) {
      long id = worker.nextId();

      assertFalse(map.containsKey(id));
      map.put(id, i);
    }

    String result = DigitalNumberContext.generate("Key_SnowFlake");

    assertNotNull("result is not null.", result);
  }
}
