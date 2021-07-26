package com.x3platform.files.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "x3platform.files.s3")
public class S3Properties {
  
  private String endpoint = "s3.files.x3platform.com";
  
  public void setEndpoint(String value) {
    this.endpoint = value;
  }
  
  public String getEndpoint() {
    return this.endpoint;
  }
  
  private String accessKey = "";
  
  public void setAccessKey(String value) {
    this.accessKey = value;
  }
  
  public String getAccessKey() {
    return this.accessKey;
  }
  
  private String secretKey = "";
  
  public void setSecretKey(String value) {
    this.secretKey = value;
  }
  
  public String getSecretKey() {
    return this.secretKey;
  }
  
  private String defaultBucketName = "files";
  
  public void setDefaultBucketName(String value) {
    this.defaultBucketName = value;
  }
  
  public String getDefaultBucketName() {
    return this.defaultBucketName;
  }
}
