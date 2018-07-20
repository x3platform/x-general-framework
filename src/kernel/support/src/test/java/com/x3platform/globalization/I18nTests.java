package com.x3platform.globalization;

import com.x3platform.SpringContext;

import com.x3platform.configuration.KernelConfiguration;
import com.x3platform.data.DataQuery;
import com.x3platform.util.ApplicationContextUtil;
import com.x3platform.util.PathUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.x3platform.configuration.KernelConfigurationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class I18nTests {
  @Test
  public void testLocalizer() {
    String path = PathUtil.getProgramPath() + "/locales/zh-cn/exceptions.xml";

    File file = new File(path);

    assertTrue("file is exist. path:" + path, file.exists());

    Localizer localizer = new Localizer(path, "exception");

    assertNotNull("localizer is not null.", localizer);
  }

  @Test
  public void testLocation() {
    assertNotNull("I18n.getTranslates() is not null.", I18n.getTranslates());
    assertNotNull("I18n.getStrings() is not null.", I18n.getStrings());
    assertNotNull("I18n.getExceptions() is not null.", I18n.getExceptions());

    String text = I18n.getStrings().text("text_all");

    assertNotNull("text is not null.", text);

    text = I18n.getExceptions().text("code_parameter_is_null");
    // String text = I18n.getExceptions().text("code_parameter_is_null");
    assertNotNull("text is not null.", text);
    assertEquals("text is 1000.", "1000", text);
  }

  @Test
  public void TestInit() {
    I18nScript.getInstance().Init();
  }
}
