package com.x3platform.connect;

import com.x3platform.data.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
@ComponentScan(basePackages = {"com.x3platform"})
@MapperScan({"com.x3platform"})
public class SpringTestApp {

}
