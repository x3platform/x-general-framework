package com.x3platform.files.s3;

import com.x3platform.InternalLogger;
import com.x3platform.files.s3.S3Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.Protocol;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

import static software.amazon.awssdk.regions.Region.US_EAST_1;

@Configuration("com.x3platform.files.s3.S3AutoConfiguration")
@EnableConfigurationProperties({S3Properties.class})
public class S3AutoConfiguration {

  private S3Properties properties;
  
  public S3AutoConfiguration(S3Properties properties) {
    this.properties = properties;
  }
  
  @Bean
  public S3Client s3Client() {
    // 输出配置信息
    InternalLogger.getLogger().debug("amazon s3 client endpoint:{}, access-key:{}, secret-key:{}",
      properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
    
    AwsBasicCredentials credentials = AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey());
    
    return S3Client.builder().endpointOverride(URI.create(properties.getEndpoint())).region(US_EAST_1)
      .credentialsProvider(StaticCredentialsProvider.create(credentials))
      .build();
  }
}
