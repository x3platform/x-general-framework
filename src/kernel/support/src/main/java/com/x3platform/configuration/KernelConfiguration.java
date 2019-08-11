package com.x3platform.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 核心的配置信息
 */
@Configuration
public class KernelConfiguration {

  @Value("${x3platform.system-name:x3platform}")
  private String SystemName;

  /***
   * 系统名称
   */
  public String getSystemName() {
    return SystemName;
  }

  @Value("${x3platform.culture-name:zh-cn}")
  private String CultureName;

  /***
   * 默认区域性名称
   */
  public String getCultureName() {
    return CultureName;
  }

  @Value("${x3platform.host:@host}")
  private String Host;

  /***
   * 应用服务器名称
   */
  public String getHost() {
    return Host;
  }

  @Value("${x3platform.file-host:@host}")
  private String FileHost;

  /***
   * 文件服务器
   */
  public String getFileHost() {
    return FileHost;
  }

  @Value("${x3platform.static-file-host:@host}")
  private String StaticFileHost;

  /***
   * 静态文件服务器
   */
  public String getStaticFileHost() {
    return StaticFileHost;
  }

  @Value("${x3platform.domain:x3platform.com}")
  private String Domain;

  /***
   * 默认域名
   */
  public String getDomain() {
    return Domain;
  }

  @Value("${x3platform.messages.message-object-formatter:com.x3platform.messages.MessageObjectFormatter}")
  private String MessageObjectFormatter;

  public String getMessageObjectFormatter() {
    return MessageObjectFormatter;
  }

  @Value("${x3platform.application-path-root:.}")
  private String ApplicationPathRoot;

  public String getApplicationPathRoot() {
    return ApplicationPathRoot;
  }

  @Value("${x3platform.authentication-management-type:com.x3platform.security.authentication.LocalAuthenticationManagement}")
  private String AuthenticationManagementType;

  /***
   * 验证管理类型
   */
  public String getAuthenticationManagementType() {
    return AuthenticationManagementType;
  }
}
