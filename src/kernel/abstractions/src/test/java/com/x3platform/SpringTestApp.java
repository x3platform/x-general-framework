package com.x3platform;

import com.x3platform.data.DynamicDataSourceRegister;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
// @EnableAutoConfiguration
public class SpringTestApp {

}
