package com.x3platform.cacheBuffer.configuration;

// import com.x3platform.Configuration.*;
// import com.x3platform.Yaml.RepresentationModel.*;
// import com.x3platform.Util.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存配置信息
 */
@Configuration
public class CacheBufferConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String ApplicationName = "CacheBuffer";

  /**
   * 配置区的名称
   */
  public static final String SectionName = "cache-buffer";

  @Value("${x3platform." + SectionName + ".cache-provider:X3Platform.CacheBuffer.MemoryCacheProvider,X3Platform.Support}")
  public String mCacheProvider;

  public String getCacheProvider() {
    return mCacheProvider;
  }

  @Value("${x3platform." + SectionName + ".cache-default-duration:720}")
  public String mCacheDefaultDuration;

  public String getCacheDefaultDuration() {
    return mCacheDefaultDuration;
  }
}
