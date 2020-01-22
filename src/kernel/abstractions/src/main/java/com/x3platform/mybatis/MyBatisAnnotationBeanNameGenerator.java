package com.x3platform.mybatis;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * MyBatis BeanName
 *
 * @author ruanyu
 */
public class MyBatisAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

  @Override
  public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {

    String beanName = definition.getBeanClassName();
    // 如果是 Mapper结尾的对象，则返回完整的类名。
    if (beanName.endsWith("Mapper")) {
      return beanName;
    } else {
      return super.generateBeanName(definition, registry);
    }
  }
}
