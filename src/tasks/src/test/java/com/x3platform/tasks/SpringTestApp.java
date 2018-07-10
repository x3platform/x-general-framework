package com.x3platform.tasks;

import com.x3platform.data.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
@ComponentScan(basePackages = {"com.amiintellect", "com.x3platform"})
@MapperScan({"com.amiintellect", "com.x3platform"})
public class SpringTestApp {

}
