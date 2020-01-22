package com.x3platform;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

  private Logger logger = InternalLogger.getLogger();

  private static ApplicationContext sApplicationContext = null;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    sApplicationContext = applicationContext;
    logger.info("Spring ApplicationContext registered");
  }

  public static ApplicationContext getApplicationContext() {
    return sApplicationContext;
  }

  public static Object getBean(String name) {
    return getApplicationContext().getBean(name);
  }

  public static <T> T getBean(Class<T> requiredType, Object... args) {
    return getApplicationContext().getBean(requiredType, args);
  }

  public static <T> T getBean(Class<T> requiredType) {
    return getApplicationContext().getBean(requiredType);
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    return getApplicationContext().getBean(name, requiredType);
  }
}
