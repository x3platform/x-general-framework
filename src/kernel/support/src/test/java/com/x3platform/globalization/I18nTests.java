package com.x3platform.globalization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.util.PathUtil;
import com.x3platform.util.StringUtil;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class I18nTests {

  @Test
  public void testInit() {
    I18nScript.getInstance().Init();
  }

  @Test
  public void testLocalizer() throws URISyntaxException {
    String path = PathUtil.getProgramPath() + "/locales/zh-cn/exceptions.xml";

    File file = new File(path);

    assertTrue("file is exist. path:" + path, file.exists());

    Localizer localizer = new Localizer(path, "exception");

    assertNotNull("localizer is not null.", localizer);

    String result = localizer.getText("code_general_param_is_null");
    assertEquals("100001", result);

    result = localizer.getName("100001");
    assertEquals("code_general_param_is_null", result);
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

    text = I18n.getExceptionDescription("100001");
    assertEquals("参数 {} 为空", text);

    text = I18n.getExceptionDescription("100001", "id");
    assertEquals("参数 id 为空", text);

    // 未找到匹配信息则返回空值
    text = I18n.getExceptionDescription(StringUtil.toRandom(8));
    assertEquals("", text);
  }

  @Test
  public void testGetExceptions() {
    String text ;
    // 参数不能为空
    text = I18n.getExceptions().text("code_general_param_is_null");
    assertNotNull("code is not null.", text);
    assertEquals("code is 100001.", "100001", text);

    // 参数必填
    text = I18n.getExceptions().text("code_general_param_is_required");
    assertNotNull("text is not null.", text);
    assertEquals("code is 100002.", "100002", text);

    text = I18n.getExceptionDescription("100001");
    assertEquals("参数 {} 为空", text);

    text = I18n.getExceptionDescription("100001", "id");
    assertEquals("参数 id 为空", text);

    // 未找到匹配信息则返回空值
    text = I18n.getExceptionDescription(StringUtil.toRandom(8));
    assertEquals("", text);
  }
}
