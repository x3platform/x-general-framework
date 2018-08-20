package com.x3platform.apps;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppsContextTests {

  @Test
  public void testLoad() {
    AppsContext instance = AppsContext.getInstance();

    assertNotNull("ApplicationService is not null.",
      instance.getApplicationService());

    assertNotNull("ApplicationMenuService is not null.",
      instance.getApplicationMenuService());

    assertNotNull("ApplicationSettingService is not null.",
      instance.getApplicationSettingService());
  }
}
