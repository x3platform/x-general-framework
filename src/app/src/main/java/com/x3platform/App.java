package com.x3platform;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.x3platform.config.AppConfig;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.data.DynamicDataSourceRegister;
import com.x3platform.security.jasypt.CustomEncryptablePropertyDetector;
import com.x3platform.security.jasypt.CustomEncryptablePropertyResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Spring Boot Application
 */
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
@ComponentScan(basePackages = {"com.x3platform.configuration", "com.x3platform"})
@MapperScan({"com.x3platform.*.mappers", "com.x3platform.*.*.mappers", "com.x3platform.*.*.*.mappers"})
@Import({AppConfig.class, DynamicDataSourceRegister.class})
public class App extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(App.class);
  }

  public static void main(String[] args) {
    // 设置开始时间
    long startTime = System.currentTimeMillis();

    // 启用应用
    ApplicationContext context = SpringApplication.run(App.class, args);

    // 设置结束时间
    long endTime = System.currentTimeMillis();

    KernelContext.getLog().info(
      "Application {} 启动成功, 应用端口 {}, 耗时 {} 秒, 加载 Spring 组件 {} 个.\nApplication Path: {}.\nApplication Version: {}.",
      App.class.getName(),
      context.getEnvironment().getProperty("server.port"),
      (endTime - startTime) / 1000.00,
      context.getBeanDefinitionNames().length,
      KernelConfigurationView.getInstance().getApplicationPathRoot(),
      "1.0.0-build");
  }
}
