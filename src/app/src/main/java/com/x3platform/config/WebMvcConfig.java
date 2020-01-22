package com.x3platform.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.x3platform.apps.filters.ApplicationMethodFilter;
import com.x3platform.apps.interceptors.ApplicationMethodAccessInterceptor;
import com.x3platform.security.interceptors.HttpAuthenticationInterceptor;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Web MVC 配置
 *
 * @author ruanyu
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Bean
  public FilterRegistrationBean applicationMethodFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new ApplicationMethodFilter());
    registration.setName("applicationMethodFilter");
    registration.addUrlPatterns("/api/*");
    registration.setOrder(1);
    return registration;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 多个拦截器组成一个拦截器链
    // addPathPatterns() 用于添加拦截规则 | excludePathPatterns() 用户排除拦截

    // 设置 HTTP 身份验证拦截器
    registry.addInterceptor(new HttpAuthenticationInterceptor())
      .addPathPatterns("/**")
      .excludePathPatterns("/api/sys/security/encrypter/**", "/api/connect/auth/**");

    // 设置应用方法访问拦截器
    registry.addInterceptor(new ApplicationMethodAccessInterceptor())
      .addPathPatterns("/api/**");

    super.addInterceptors(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    super.addResourceHandlers(registry);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // 跨域配置方法
    // addMapping         设置允许跨域的路径
    registry.addMapping("/**")
      // allowedOrigins   设置允许跨域请求的域名
      .allowedOrigins("*")
      // allowCredentials 是否允许证书 不再默认开启
      .allowCredentials(true)
      // allowedMethods   跨域允许的方法
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .maxAge(3600);
  }

  /**
   * 支持中文输出
   */
  @Bean
  public HttpMessageConverter<String> stringHttpMessageConverter() {
    StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    // 移除 AcceptCharset 信息
    converter.setWriteAcceptCharset(false);
    // 解决中文乱码的问题
    List<MediaType> mediaTypes = new ArrayList<>();
    mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

    converter.setSupportedMediaTypes(mediaTypes);

    return converter;
  }

  @Bean
  public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
    // 定义一个convert转换消息的对象
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    // 添加 fastjson 的配置信息
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    // 处理中文乱码问题
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    converter.setSupportedMediaTypes(fastMediaTypes);
    // 在 converter 中添加配置信息
    converter.setFastJsonConfig(fastJsonConfig);
    // 将 converter 添加到 converters 中
    return converter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    super.configureMessageConverters(converters);
    // 优先处理二进制和字符串类型转换
    converters.add(stringHttpMessageConverter());
    converters.add(new ByteArrayHttpMessageConverter());
    converters.add(fastJsonHttpMessageConverter());
  }
}
