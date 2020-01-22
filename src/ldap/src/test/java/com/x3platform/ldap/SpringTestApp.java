package com.x3platform.ldap;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.x3platform.data.DynamicDataSourceRegister;
import com.x3platform.security.jasypt.CustomEncryptablePropertyDetector;
import com.x3platform.security.jasypt.CustomEncryptablePropertyResolver;
import java.util.Properties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
@ComponentScan(basePackages = {"com.x3platform.configuration", "com.x3platform.*.configuration", "com.x3platform"})
@MapperScan({"com.x3platform"})
@EnableEncryptableProperties
public class SpringTestApp {

  /**
   * 自动识别使用的数据库类型 在 mapper.xml 中设置 databaseId 的值对应这里的配置，如果没有 databaseId 选择则说明该 sql 适用所有数据库
   */
  @Bean
  public DatabaseIdProvider getDatabaseIdProvider() {
    DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

    Properties properties = new Properties();

    properties.setProperty("MySQL", "mysql");
    properties.setProperty("Oracle", "oracle");

    databaseIdProvider.setProperties(properties);

    return databaseIdProvider;
  }

  @Bean(name = "encryptablePropertyDetector")
  public EncryptablePropertyDetector encryptablePropertyDetector() {
    return new CustomEncryptablePropertyDetector();
  }

  @Bean(name = "encryptablePropertyResolver")
  public EncryptablePropertyResolver encryptablePropertyResolver() {
    // 生成密文的方法
    // POST /api/sys/security/encrypter/encryptAes
    // data:{ "text":"123456", "key":"[KEY]", "iv":"[IV]", "format":"HEX_STRING_WHITOUT_HYPHEN" }
    CustomEncryptablePropertyResolver resolver = new CustomEncryptablePropertyResolver();
    // 密钥 长度 16 位
    resolver.setKey("4bafcd70761b465d");
    // 初始向量 长度 16 位
    resolver.setIv("a56674fb72fa434f");
    return resolver;
  }
}
