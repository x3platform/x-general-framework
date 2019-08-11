package com.x3platform.digitalnumber;

import static org.junit.Assert.assertNotNull;

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
  public void testGenerateDailyIncrement() {
    String result = DigitalNumberContext.generate("test3");

    assertNotNull("result is not null.", result);
  }
}
