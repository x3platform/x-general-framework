package com.x3platform.cachebuffer;

import com.x3platform.data.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.x3platform.configuration", "com.x3platform"})
@MapperScan({"com.x3platform.*.mappers", "com.x3platform.*.*.mappers"})
public class SpringTestApp {

}