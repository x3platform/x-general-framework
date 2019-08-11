package com.x3platform.globalization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.util.PathUtil;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class I18nTests {
  @Test
  public void testLocalizer() {
    Class cls = KernelConfigurationView.class;
    // InputStream stream = cls.getResourceAsStream("/locales/zh-cn/exceptions.xml");
    try {
      URI resourceUri = cls.getResource("/locales/zh-cn/exceptions.xml").toURI();

      File file1 = new File(resourceUri);

      assertNotNull(file1);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    // OutputStream output = new FileOutputStream(file);

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

    text = I18n.getExceptions().text("code_general_param_is_null");
    assertNotNull("text is not null.", text);
    assertEquals("text is 100001.", "100001", text);
  }

  @Test
  public void TestInit() {
    I18nScript.getInstance().Init();
  }
}
