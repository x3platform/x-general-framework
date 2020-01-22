package com.x3platform;

import com.x3platform.data.DynamicDataSourceRegister;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.x3platform.configuration", "com.x3platform"})
@Import(DynamicDataSourceRegister.class)
public class SpringTestApp {

}
