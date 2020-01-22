package com.x3platform;

import com.alibaba.fastjson.parser.ParserConfig;
import com.x3platform.cachebuffer.redis.FastJsonRedisSerializer;
import com.x3platform.data.DynamicDataSourceRegister;
import com.x3platform.apps.defaults.AppConfig;
import java.util.Properties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@ComponentScan({"com.x3platform.configuration", "com.x3platform"})
@MapperScan({"com.x3platform.*.mappers", "com.x3platform.*.*.mappers", "com.x3platform.*.*.*.mappers"})
@Import({AppConfig.class, DynamicDataSourceRegister.class})
public class SpringTestApp {

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    StringRedisTemplate template = new StringRedisTemplate(factory);

    RedisSerializer stringSerializer = new StringRedisSerializer();
    // 设置键（key）的序列化采用 StringRedisSerializer。
    template.setKeySerializer(stringSerializer);
    template.setHashKeySerializer(stringSerializer);

    FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
    // 建议使用这种方式，小范围指定白名单
    ParserConfig.getGlobalInstance().addAccept("com.x3platform.");

    // 设置值（value）的序列化采用FastJsonRedisSerializer。
    template.setValueSerializer(fastJsonRedisSerializer);
    template.setHashValueSerializer(fastJsonRedisSerializer);

    template.afterPropertiesSet();

    return template;
  }

  /**
   * 自动识别使用的数据库类型
   * 在 mapper.xml 中设置 databaseId 的值对应这里的配置，
   * 如果没有 databaseId 选择则说明该 sql 适用所有数据库
   */
  @Bean
  public DatabaseIdProvider databaseIdProvider() {
    DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

    Properties properties = new Properties();

    properties.setProperty("MySQL", "mysql");
    properties.setProperty("Oracle", "oracle");
    properties.setProperty("SqlServer", "sqlserver");

    databaseIdProvider.setProperties(properties);

    return databaseIdProvider;
  }
}
