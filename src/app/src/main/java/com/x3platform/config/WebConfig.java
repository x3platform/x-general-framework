package com.x3platform.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {

    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    super.addResourceHandlers(registry);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry.addMapping("/**")            // 设置允许跨域的路径
      .allowedOrigins("*")                             // 设置允许跨域请求的域名
      .allowCredentials(true)                          // 是否允许证书 不再默认开启
      .allowedMethods("GET", "POST", "PUT", "DELETE")  // 设置允许的方法
      .maxAge(3600);                                   // 跨域允许时间
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
    //1、定义一个convert转换消息的对象
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    //2、添加fastjson的配置信息
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    //2-1 处理中文乱码问题
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    converter.setSupportedMediaTypes(fastMediaTypes);
    //3、在convert中添加配置信息
    converter.setFastJsonConfig(fastJsonConfig);
    //4、将convert添加到converters中
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
