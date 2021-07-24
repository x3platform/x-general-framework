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

  /**
   * 系统名称
   */
  public String getSystemName() {
    return SystemName;
  }

  @Value("${x3platform.culture-name:zh-cn}")
  private String CultureName;

  /**
   * 默认区域性名称
   */
  public String getCultureName() {
    return CultureName;
  }

  @Value("${x3platform.host:@host}")
  private String Host;

  /**
   * 应用服务器名称
   */
  public String getHost() {
    return Host;
  }

  @Value("${x3platform.file-host:@host}")
  private String FileHost;

  /**
   * 文件服务器
   */
  public String getFileHost() {
    return FileHost;
  }

  @Value("${x3platform.static-file-host:@host}")
  private String StaticFileHost;

  /**
   * 静态文件服务器
   */
  public String getStaticFileHost() {
    return StaticFileHost;
  }

  @Value("${x3platform.domain:x3platform.com}")
  private String Domain;

  /**
   * 默认域名
   */
  public String getDomain() {
    return Domain;
  }

  @Value("${x3platform.messages.message-object-formatter:com.x3platform.messages.MessageObjectFormatter}")
  private String MessageObjectFormatter;

  /**
   * 消息对象格式器
   */
  public String getMessageObjectFormatter() {
    return MessageObjectFormatter;
  }

  @Value("${x3platform.application-path-root:.}")
  private String ApplicationPathRoot;

  /**
   * 应用目录
   */
  public String getApplicationPathRoot() {
    return ApplicationPathRoot;
  }

  @Value("${x3platform.application-temp-path-root:./temp}")
  private String ApplicationTempPathRoot;

  /**
   * 应用临时目录
   */
  public String getApplicationTempPathRoot() {
    return ApplicationTempPathRoot;
  }

  @Value("${x3platform.application-temp-path-root:-1}")
  private String ApplicationTempFileRemoveTimerInterval;

  /**
   * 应用临时文件清理时间间隔(单位:天数)
   */
  public String getApplicationTempFileRemoveTimerInterval() {
    return ApplicationTempFileRemoveTimerInterval;
  }

  @Value("${x3platform.attachmentstorage.physical-upload-folder:./uploads/}")
  private String physicalUploadFolder;

  /**
   * 上传文件夹物理路径
   */
  public String getPhysicalUploadFolder() {
    return physicalUploadFolder;
  }

  @Value("${x3platform.attachmentstorage.virtual-upload-folder:/uploads}")
  private String virtualUploadFolder;

  /**
   * 上传文件夹相对路径
   */
  public String getVirtualUploadFolder() {
    return virtualUploadFolder;
  }

  @Value("${x3platform.authentication-management-type:com.x3platform.security.authentication.LocalAuthenticationManagement}")
  private String authenticationManagementType;

  /**
   * 验证管理类型
   */
  public String getAuthenticationManagementType() {
    return authenticationManagementType;
  }

  @Value("${x3platform.data-query-underline-case:yes}")
  private String dataQueryUnderlineCase;

  /**
   * 数据查询参数自动转换为下划线格式
   */
  public String getDataQueryUnderlineCase() {
    return dataQueryUnderlineCase;
  }
}
