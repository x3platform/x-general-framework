package com.x3platform.files.minio;

import com.x3platform.InternalLogger;
import com.x3platform.files.minio.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.files.minio.MinioAutoConfiguration")
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {
  
  private MinioProperties properties;
  
  public MinioAutoConfiguration(MinioProperties properties) {
    this.properties = properties;
  }
  
  @Bean
  public MinioClient minioClient() {
    // 输出配置信息
    InternalLogger.getLogger().debug("minio client endpoint:{}, access-key:{}, secret-key:{}",
      properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
    
    return MinioClient.builder().endpoint(properties.getEndpoint())
      .credentials(properties.getAccessKey(), properties.getSecretKey()).build();
  }
}
