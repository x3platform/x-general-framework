package com.x3platform;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.x3platform.data.DynamicDataSourceRegister;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
@ComponentScan(basePackages = {"com.x3platform.configuration", "com.x3platform"})
@MapperScan({"com.x3platform.*.mappers", "com.x3platform.*.*.mappers"})
public class SpringTestApp {

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

  public static void main(String[] args) {
    SpringApplication.run(SpringTestApp.class);
  }

}
