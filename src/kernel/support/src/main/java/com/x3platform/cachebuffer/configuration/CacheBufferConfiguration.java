package com.x3platform.cachebuffer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 缓存配置信息
 */
@Component("com.x3platform.cachebuffer.configuration.CacheBufferConfiguration")
public class CacheBufferConfiguration {
  /**
   * 所属应用的名称
   */
  public static final String APPLICATION_NAME = "cachebuffer";

  /**
   * 配置区的名称
   */
  public static final String SECTION_NAME = "cache-buffer";

  @Value("${x3platform." + SECTION_NAME + ".cache-provider:com.x3platform.cachebuffer.memory.MemoryCacheProvider}")
  public String mCacheProvider;

  public String getCacheProvider() {
    return mCacheProvider;
  }

  @Value("${x3platform." + SECTION_NAME + ".cache-default-duration:720}")
  public String mCacheDefaultDuration;

  public String getCacheDefaultDuration() {
    return mCacheDefaultDuration;
  }
}
