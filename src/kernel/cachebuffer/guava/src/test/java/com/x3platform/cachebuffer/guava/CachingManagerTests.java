package com.x3platform.cachebuffer.guava;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CachingManagerTests {
  @Test
  public void testInit() throws Exception {
    LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
      // 设置缓存的最大容量
      .maximumSize(1000)
      // 设置缓存在写入 30 分钟后失效
      .expireAfterWrite(30, TimeUnit.MINUTES)
      // 缓存被访问后,一定时间后失效
      .expireAfterAccess(30, TimeUnit.MINUTES)
      // 设置并发级别为10
      .concurrencyLevel(10)
      // 开启缓存统计
      .recordStats()
      .build(new CacheLoader<String, Object>() {
        @Override
        public Object load(String key) throws Exception {
          return key;
        }
      });
    
    cache.put("abc", "cba");
    
    Object value = cache.get("abc");
    
    assertNotNull(value);
    
    value = cache.getIfPresent("a");
    
    assertNull(value);
    
    Cache<String, String> cache1 = CacheBuilder.newBuilder().maximumSize(1000).build();
    cache1.put("a", "");
    String resultVal = cache1.get("jerry", new Callable<String>() {
      public String call() {
        String strProValue = "hello " + "jerry" + "!";
        return strProValue;
      }
    });
    System.out.println("jerry value : " + resultVal);
    
    resultVal = cache1.get("peida", new Callable<String>() {
      public String call() {
        String strProValue = "hello " + "peida" + "!";
        return strProValue;
      }
    });
    
    System.out.println("peida value : " + resultVal);

//    Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
//    CacheBuilder<Object, Object> cachebuilder =   CacheBuilder.newBuilder().
//        expireAfterWrite(10, TimeUnit.SECONDS).
//        maximumSize(1000);
//
//    Cache<Object, Object>  cache =  cachebuilder.build();
    
    // return cacheManager;
  }
  
  @Test
  public void testContains() {
    String name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, "test object");
    boolean result = CachingManager.contains(name);
    assertTrue(result);
    CachingManager.delete(name);
    
    name = "test:value:" + StringUtil.toRandom(6);
    result = CachingManager.contains(name);
    assertFalse(result);
  }
  
  @Test
  public void testSet() {
    String name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, "test object", 5);
    boolean result = CachingManager.contains(name);
    assertTrue(result);
    
    // 测试五秒后删除
    name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, DateUtil.getTimestamp(), 5);
    result = CachingManager.contains(name);
    assertTrue(result);
  }
  
  @Test
  public void testDelete() {
    String name = "test:value:" + StringUtil.toRandom(6);
    CachingManager.set(name, "test object (delete)");
    CachingManager.delete(name);
    boolean result = CachingManager.contains(name);
    assertFalse(result);
  }
}
