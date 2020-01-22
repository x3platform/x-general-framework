package com.x3platform.cachebuffer.redis.defaults;

import com.x3platform.cachebuffer.redis.services.RedisTemplateService;
import com.x3platform.cachebuffer.redis.services.impl.RedisTemplateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.cachebuffer.redis.defaults.AppConfig")
public class AppConfig {

  @Bean("com.x3platform.cachebuffer.redis.services.RedisTemplateService")
  public RedisTemplateService redisTemplateService() {
    return new RedisTemplateServiceImpl();
  }
}
