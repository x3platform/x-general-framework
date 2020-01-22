package com.x3platform.messages;

import com.x3platform.SpringContext;

import com.x3platform.configuration.KernelConfiguration;
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
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageObjectTests {

  @Autowired
  private KernelConfiguration config;

  @Test
  public void testCreateMessageObjectFormatter() {
    // System.out.println(configuration.getMessageObjectFormatter());

    KernelConfigurationView configView = null;

    configView = SpringContext.getBean(KernelConfigurationView.class);

    // ApplicationContext context = ApplicationContextUtil.getContext();

    // configView = new KernelConfigurationView();

    // context.getAutowireCapableBeanFactory().autowireBean(configView);

    String formatterClassName = KernelConfigurationView.getInstance().getMessageObjectFormatter();

    // System.out.println(formatterClassName);

    try {
      MessageObjectFormatter formatter = (MessageObjectFormatter) Class.forName(formatterClassName).newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testToString() {
    MessageObject result = new MessageObject();

    result.setCode("0");
    result.setMessage("success");

    String text = result.toString();
    // {"message":{"returnCode":"0","value":"success"},"success":1,"msg":"success"}
    // {"message":{"code":"0","message":"success"}}
    assertTrue("MessageObject.toString() should return {\"message\":{\"code\":\"0\",\"message\":\"success\"}}",
      text.equals("{\"code\":\"0\",\"message\":\"success\"}"));

    text = result.toString(true);
    assertTrue("MessageObject.toString() should return \"message\":{\"code\":\"0\",\"message\":\"success\"}",
      text.equals("\"code\":\"0\",\"message\":\"success\""));
  }
}
