package com.x3platform;

import com.x3platform.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class SpringContext implements ApplicationContextAware {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private static ApplicationContext sApplicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    sApplicationContext = applicationContext;
    logger.info("Spring ApplicationContext registed");
  }

  public static ApplicationContext getApplicationContext() {
    return sApplicationContext;
  }

  public static Object getBean(String name) {
    return getApplicationContext().getBean(name);
  }

  public static <T> T getBean(Class<T> aClass) {
    return getApplicationContext().getBean(aClass);
  }

  public static <T> T getBean(String name, Class<T> aClass) {
    return getApplicationContext().getBean(name, aClass);
  }
}
