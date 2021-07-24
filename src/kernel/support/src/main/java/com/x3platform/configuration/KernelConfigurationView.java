package com.x3platform.configuration;

import com.x3platform.SpringContext;
import com.x3platform.util.BooleanUtil;
import com.x3platform.util.DirectoryUtil;
import com.x3platform.util.PathUtil;
import com.x3platform.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

/**
 * 核心的配置信息
 */

@Configuration
public class KernelConfigurationView {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static volatile KernelConfigurationView instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static KernelConfigurationView getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = SpringContext.getBean(KernelConfigurationView.class);
        }
      }
    }

    return instance;
  }

  @Autowired
  private Environment env;

  @Autowired
  private KernelConfiguration configuration;

  /**
   * 系统名称
   */
  public String getSystemName() {
    return configuration.getSystemName();
  }

  /**
   * 默认区域性名称
   */
  public String getCultureName() {
    return configuration.getCultureName();
  }

  /**
   * 应用服务器名称
   */
  public String getHost() {
    String host = configuration.getHost();

    if (host.equals("@host")) {
      host = "http://localhost";
      String port = env.getProperty("server.port");
      if (!StringUtil.isNullOrEmpty(port)) {
        host = host + ":" + port;
      }
    }

    return host;
  }

  /**
   * 文件服务器
   */
  public String getFileHost() {
    String host = configuration.getFileHost();

    if (host.equals("@host")) {
      host = getHost();
    }

    return host;
  }

  /**
   * 静态文件服务器
   */
  public String getStaticFileHost() {
    String host = configuration.getStaticFileHost();

    if (host.equals("@host")) {
      host = getHost();
    }

    return host;
  }

  /**
   * 默认域名
   */
  public String getDomain() {
    return configuration.getDomain();
  }

  /**
   * 消息对象格式器
   */
  public String getMessageObjectFormatter() {
    return configuration.getMessageObjectFormatter();
  }

  /**
   * 应用目录
   */
  public String getApplicationPathRoot() {
    String path = configuration.getApplicationPathRoot();

    if (path.equals(".")) {
      path = PathUtil.getProgramPath();
    }

    return path;
  }

  /**
   * 应用临时目录
   */
  public String getApplicationTempPathRoot() {
    String path = configuration.getApplicationTempPathRoot();

    if (path.equals("./temp")) {
      path = getApplicationPathRoot() + "/temp";
    }

    return path;
  }

  /**
   * 应用临时文件清理时间间隔(单位:天数)
   */
  public String getApplicationTempFileRemoveTimerInterval() {
    return configuration.getApplicationTempFileRemoveTimerInterval();
  }

  /**
   * 获取上传文件夹物理路径
   * 这里引用的是本地路径, 如果是分布式程序不建议使用此路径
   *
   * @return 物理路径
   */
  public String getPhysicalUploadFolder() {
    return DirectoryUtil.formatLocalPath(new File(configuration.getPhysicalUploadFolder()).getAbsolutePath() + PathUtil.getFileSeparator());
  }

  /**
   * 获取上传文件虚拟路径
   *
   * @return 虚拟路径
   */
  public String getVirtualUploadFolder() {
    return configuration.getVirtualUploadFolder();
  }

  /**
   * 验证管理类型
   */
  public String getAuthenticationManagementType() {
    return configuration.getAuthenticationManagementType();
  }

  /**
   * 数据查询参数名称自动转换为下划线格式
   */
  public boolean getDataQueryUnderlineCase() {
    return BooleanUtil.bool(configuration.getDataQueryUnderlineCase());
  }
}
