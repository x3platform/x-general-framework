package com.x3platform.digitalNumber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DigitalNumberContextTests {
  @Test
  public void testLoad() {
    assertNotNull("DigitalNumberServer is not null.",
      DigitalNumberContext.getInstance().getDigitalNumberService());
  }

  @Test
  public void testGenerate() {
    String result = DigitalNumberContext.generate("Key_Guid");

    assertNotNull("result is not null.", result);
  }
}
