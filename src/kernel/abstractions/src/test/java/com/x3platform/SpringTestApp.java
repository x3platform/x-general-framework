package com.x3platform;

import com.x3platform.data.DynamicDataSourceRegister;
import com.x3platform.mybatis.MybatisAnnotationBeanNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.x3platform.configuration", "com.x3platform"})
@MapperScan(nameGenerator = MybatisAnnotationBeanNameGenerator.class,
  basePackages = {"com.x3platform.*.mappers", "com.x3platform.*.*.mappers"})
@Import(DynamicDataSourceRegister.class)
public class SpringTestApp {

}
