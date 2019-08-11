package com.x3platform;

import com.x3platform.data.DynamicDataSourceRegister;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import(DynamicDataSourceRegister.class)
public class SpringTestApp {

}
