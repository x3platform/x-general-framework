package com.x3platform.mybatis;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.StringUtils;

/**
 * Unique Mapper Annotation BeanName Generator
 *
 * @author ruanyu
 */
public class MybatisAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

  @Override
  public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {

    String beanName = definition.getBeanClassName();
    // 如果是 Mapper 结尾的对象，则返回完整的类名。
    if (beanName.endsWith("Mapper")) {
      return beanName;
    } else {
      return super.generateBeanName(definition, registry);
    }
  }

  @Override
  protected String buildDefaultBeanName(BeanDefinition definition) {
    if (definition instanceof AnnotatedBeanDefinition) {
      String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
      if (StringUtils.hasText(beanName)) {
        // Explicit bean name found.
        return beanName;
      }
    }

    return definition.getBeanClassName();
  }
}
